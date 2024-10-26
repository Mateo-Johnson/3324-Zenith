// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake_shooter;

import edu.wpi.first.wpilibj2.command.Command;

public class Shoot extends Command {
  /** Creates a new Shoot. */
  public Shoot() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Intake_Shooter.leftOuttakeMotor.set(1);
    Intake_Shooter.rightOuttakeMotor.set(-1);
    Intake_Shooter.storageMotor.set(0.7);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Intake_Shooter.leftOuttakeMotor.set(0);
    Intake_Shooter.rightOuttakeMotor.set(0);
    Intake_Shooter.storageMotor.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
