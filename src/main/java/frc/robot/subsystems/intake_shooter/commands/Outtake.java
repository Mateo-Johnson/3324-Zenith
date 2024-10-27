// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake_shooter.commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake_shooter.Intake_Shooter;
import edu.wpi.first.wpilibj.PowerDistribution; // IMPORT POWER DISTRIBUTION FOR BATTERY VOLTAGE

public class Outtake extends Command {
  private final CANSparkMax leftOuttakeMotor = Intake_Shooter.leftOuttakeMotor;
  private final CANSparkMax rightOuttakeMotor = Intake_Shooter.rightOuttakeMotor;
  private final RelativeEncoder leftEncoder = leftOuttakeMotor.getEncoder();
  private final RelativeEncoder rightEncoder = rightOuttakeMotor.getEncoder();

  private static final double baseThreshold = 0.34; // BASE THRESHOLD

  private final PowerDistribution pdp = new PowerDistribution(); // CREATE POWER DISTRIBUTION OBJECT

  public Outtake() {
  }

  @Override
  public void initialize() {
    // START THE OUTTAKE MOTORS
    leftOuttakeMotor.set(1);
    rightOuttakeMotor.set(-1);
  }

  @Override
  public void execute() {
    double leftVelocity = leftEncoder.getVelocity();
    double rightVelocity = rightEncoder.getVelocity();

    // GET THE CURRENT BATTERY VOLTAGE
    double batteryVoltage = pdp.getVoltage();

    // SCALE THE VELOCITY THRESHOLD BASED ON BATTERY VOLTAGE
    double scaledThreshold = baseThreshold * ((batteryVoltage / 12.0) * 0.7); // ASSUMING 12V IS NOMINAL

    SmartDashboard.putNumber("Left Outtake Velocity", leftVelocity);
    SmartDashboard.putNumber("Right Outtake Velocity", rightVelocity);
    SmartDashboard.putNumber("Battery Voltage", batteryVoltage);
    SmartDashboard.putNumber("Scaled Velocity Threshold", scaledThreshold);

    // CHECK IF BOTH MOTORS ARE ABOVE THE SCALED THRESHOLD
    if (Math.abs(leftVelocity) >= scaledThreshold && Math.abs(rightVelocity) >= scaledThreshold) {
      Intake_Shooter.storageMotor.set(0.7); // FEED THE GAME PIECE INTO THE SHOOTER
    } else {
      Intake_Shooter.storageMotor.set(0); // STOP THE STORAGE MOTOR IF NOT AT SPEED
    }
  }

  @Override
  public void end(boolean interrupted) {
    leftOuttakeMotor.set(0);
    rightOuttakeMotor.set(0);
    Intake_Shooter.storageMotor.set(0);
  }

  @Override
  public boolean isFinished() {
    return false; 
  }
}
