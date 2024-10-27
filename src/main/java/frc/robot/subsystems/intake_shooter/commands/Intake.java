package frc.robot.subsystems.intake_shooter.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.intake_shooter.Intake_Shooter; 

public class Intake extends Command {
  DoubleSolenoid solenoid;
  public Intake() {
  }

  @Override
  public void initialize() {
    solenoid = RobotContainer.solenoid;
  }

  @Override
  public void execute() {
    //MOVE THE ARM UP
    solenoid.set(DoubleSolenoid.Value.kForward);

    // GET THE TRIGGER VALUE (BETWEEN 0 AND 1)
    double triggerValue = RobotContainer.primary.getLeftTriggerAxis(); 

    // SET THE SPEEDS BASED ON THE TRIGGER VALUE
    Intake_Shooter.intakeMotor.set(triggerValue); // SET INTAKE SPEED
    Intake_Shooter.storageMotor.set(0.2); // ADJUST STORAGE MOTOR SPEED AS NEEDED
  }

  @Override
  public void end(boolean interrupted) {
    Intake_Shooter.intakeMotor.set(0);
    Intake_Shooter.storageMotor.set(0);
    solenoid.set(DoubleSolenoid.Value.kOff);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
