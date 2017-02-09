package org.usfirst.frc.team4587.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	public static final int MOTOR_RIGHT_DRIVETRAIN = 0;
	public static final int	MOTOR_LEFT_DRIVETRAIN = 1;
	public static final int MOTOR_TURRET = 8;
	public static final int MOTOR_GEAR_INTAKE = 5;
	
	public static final int ENCODER_TURRET_A = 0;
	public static final int ENCODER_TURRET_B = 1;
	public static final int ENCODER_RIGHT_DRIVE_A = 6;
    public static final int ENCODER_RIGHT_DRIVE_B = 7;
    public static final int ENCODER_LEFT_DRIVE_A = 8;
    public static final int ENCODER_LEFT_DRIVE_B = 9;
    public static final int SWITCH_GEAR_INTAKE_LIMIT = 2;
	
	// If are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
