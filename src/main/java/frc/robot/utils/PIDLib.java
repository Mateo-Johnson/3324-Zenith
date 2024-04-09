package frc.robot.utils;



import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.controller.PIDController;

public class PIDLib {

    private static final String KP_KEY = "kp";
    private static final String KI_KEY = "ki";
    private static final String KD_KEY = "kd";
    private static final double STORE_THRESHOLD = 0.1; //ERROR THRESHOLD FOR STORING TUNE CONSTANTS

    private boolean parametersStored = false;
    private double storedKp;
    private double storedKi;
    private double storedKd;
    private double maxIntegral = 1.0; //MAX INTEGRAL ACCUMULATION

    //INITIALIZE PID CONTROLLER
    private PIDController pidController;

    public double tune(double measurement, double setpoint) {
        double defaultKp = 0.00001; //DEFAULT PID CONSTANTS
        double defaultKi = 0.0;
        double defaultKd = 0.0;

        double kp = Preferences.getDouble(KP_KEY, defaultKp);
        double ki = Preferences.getDouble(KI_KEY, defaultKi);
        double kd = Preferences.getDouble(KD_KEY, defaultKd);

        // INITIALIZE PID CONTROLLER IF IT'S NULL OR IF PID CONSTANTS CHANGE
        if (pidController == null || kp != pidController.getP() || ki != pidController.getI() || kd != pidController.getD()) {
            pidController = new PIDController(kp, ki, kd);
        }

        //CALCULATE ERROR 
        double error = setpoint - measurement;

        //CLAMP ERROR
        error = Math.max(Math.min(error, 1.0), -1.0);

        //PROPORTIONAL TUNING
        kp += 0.01 * error; //ADJUST PROPORTIONALLY
        ki += 0.001 * error; //ADJUST PROPORTIONALLY (SMALLER FACTOR)
        kd -= 0.005 * error; //ADJUST INVERSE PROPORTIONALLY

        //UPDATE PID CONSTANTS
        pidController.setPID(kp, ki, kd);

        //CLAMP INTEGRAL TERM
        double integral = pidController.getI();
        if (integral > maxIntegral) {
            pidController.setI(maxIntegral);
        } else if (integral < -maxIntegral) {
            pidController.setI(-maxIntegral);
        }

        //CHECK IF ERROR FALLS BELOW THRESHOLD AND STORE CONSTANTS
        if (!parametersStored && Math.abs(error) < STORE_THRESHOLD) {
            storeParameters(kp, ki, kd);
            parametersStored = true;
        }

        //PERFORM PID CALCULATIONS 
        return pidController.calculate(measurement, setpoint);
    }

    private void storeParameters(double kp, double ki, double kd) {
        //STORE PID TERMS TO PREFERENCES
        Preferences.setDouble(KP_KEY, kp);
        Preferences.setDouble(KI_KEY, ki);
        Preferences.setDouble(KD_KEY, kd);

        //STORE CONSTANTS INTERNALLY 
        storedKp = kp;
        storedKi = ki;
        storedKd = kd;
    }

    public double getStoredKp() {
        SmartDashboard.putNumber("KP", storedKd);
        return storedKp;
    }

    public double getStoredKi() {
        SmartDashboard.putNumber("KI", storedKi);
        return storedKi;
    }

    public double getStoredKd() {
        SmartDashboard.putNumber("KD", storedKd);
        return storedKd;
    }

    public void reset() {
        parametersStored = false;
        pidController.reset();
    }

    public void setMaxIntegral(double maxIntegral) {
        this.maxIntegral = maxIntegral;
    }
}