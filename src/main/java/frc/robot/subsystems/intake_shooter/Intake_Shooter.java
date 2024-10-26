// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake_shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.Constants;

public class Intake_Shooter extends SubsystemBase {
  /** Creates a new subsystem_2. */
  public static CANSparkMax intakeMotor = new CANSparkMax(Constants.DriveConstants.intakeCanID, MotorType.kBrushless);
  public static CANSparkMax leftOuttakeMotor = new CANSparkMax(Constants.DriveConstants.leftOuttakeCanID, MotorType.kBrushless);
  public static CANSparkMax rightOuttakeMotor = new CANSparkMax(Constants.DriveConstants.rightOuttakeCanID, MotorType.kBrushless);
  public static CANSparkMax storageMotor = new CANSparkMax(Constants.DriveConstants.storageCanID, MotorType.kBrushless);
  
  public Intake_Shooter() {
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
