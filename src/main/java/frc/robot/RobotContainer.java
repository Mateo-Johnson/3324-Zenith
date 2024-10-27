package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.arm.MoveArm;
import frc.robot.subsystems.drivetrain.DriveSubsystem;
import frc.robot.subsystems.intake_shooter.Intake;
import frc.robot.subsystems.intake_shooter.Purge;
import frc.robot.subsystems.intake_shooter.Shoot;
import frc.robot.utils.Constants.ControllerConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand; // Import this at the top

public class RobotContainer {

    //SUBSYSTEMS
    private final DriveSubsystem drivetrain = new DriveSubsystem();

    //DRIVER CONTROLLERS
    public static CommandXboxController primary = new CommandXboxController(0);

    public static DoubleSolenoid solenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 5, 4);

    public RobotContainer() {
        //REGISTER THE COMMANDS BEFORE CREATING THE POSE ESTIMATOR
        configureButtonBindings(); //CONFIGURE BINDINGS

        //CONFIGURE DEFAULT COMMANDS
        drivetrain.setDefaultCommand(
            new RunCommand(() -> drivetrain.drive(
                -MathUtil.applyDeadband(primary.getLeftY(), ControllerConstants.driveDeadzone), //CONTROL THE ROBOT X SPEED
                -MathUtil.applyDeadband(primary.getLeftX(), ControllerConstants.driveDeadzone), //CONTROL THE ROBOT Y SPEED
                -MathUtil.applyDeadband(primary.getRightX(), ControllerConstants.driveDeadzone), //CONTROL THE ROBOT ROTATION
                false, true),
            drivetrain)
        );
    }

    private void configureButtonBindings() {
        //DEFINE ALL OF THE BUTTON BINDINGS HERE PLEASE AND THANKS
        primary.a().whileTrue(new MoveArm());
        primary.leftTrigger().whileTrue(new Intake());
        primary.rightTrigger().whileTrue(new Shoot());
        primary.rightBumper().whileTrue(new Purge());

    }

    //THIS IS ALL OF THE AUTO PLEASE DON'T WRITE AUTO ANYWHERE ELSE
    public Command getAutonomousCommand() {
        return new InstantCommand(); //MAKES THE ROBOT DO NOTHING DURING AUTO
    }
}
