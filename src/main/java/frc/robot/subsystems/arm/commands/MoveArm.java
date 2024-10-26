// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class MoveArm extends Command {
  /** Creates a new Move. */
  DoubleSolenoid solenoid;
  public static boolean extended;
  public MoveArm() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    solenoid = RobotContainer.solenoid;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    solenoid.set(DoubleSolenoid.Value.kForward);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    solenoid.set(DoubleSolenoid.Value.kOff);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
