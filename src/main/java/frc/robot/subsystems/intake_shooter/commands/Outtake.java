// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake_shooter.commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake_shooter.Intake_Shooter;

public class Outtake extends Command {
  private final CANSparkMax leftOuttakeMotor = Intake_Shooter.leftOuttakeMotor;
  private final CANSparkMax rightOuttakeMotor = Intake_Shooter.rightOuttakeMotor;
  private final RelativeEncoder leftEncoder = leftOuttakeMotor.getEncoder();
  private final RelativeEncoder rightEncoder = rightOuttakeMotor.getEncoder();

  private static final double VELOCITY_THRESHOLD = 0.34; // ADJUST THIS THRESHOLD AS NEEDED

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

    SmartDashboard.putNumber("Left Outtake Velocity", leftVelocity);
    SmartDashboard.putNumber("Right Outtake Velocity", rightVelocity);

    // CHECK IF BOTH MOTORS ARE ABOVE THE THRESHOLD
    if (Math.abs(leftVelocity) >= VELOCITY_THRESHOLD && Math.abs(rightVelocity) >= VELOCITY_THRESHOLD) {
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
