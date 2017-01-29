package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.Robot;
import org.usfirst.frc.team4587.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.RampedSpeedController;
import utility.RampedSpeedController.ControllerType;

/**
 *
 */
public class TurretPID extends PIDSubsystem {

	private SpeedController m_turretMotor;
	private Encoder m_encoder;
	private double m_encodersInTurn;
	private double m_startEncoders;
	public double m_nowDegrees;
	private double m_nowEncoders;
	private static double m_kP = 0.01;
	private static double m_kI = 0.0001;
	private static double m_kD = 0.001;
	public double m_testSetPoint = 0.0;
    // Initialize your subsystem here
    public TurretPID() {
    	super(m_kP,m_kI,m_kD);
    	setSetpoint(0.0);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	getPIDController().setContinuous(true);
    	setInputRange(0,360);
    	setAbsoluteTolerance(2.0);
    	m_turretMotor = new VictorSP(RobotMap.MOTOR_TURRET);
        m_encoder = new Encoder(RobotMap.TURRET_ENCODER_A, RobotMap.TURRET_ENCODER_B);
        m_encodersInTurn = 256;
        m_startEncoders = getEncoder();
        m_nowDegrees = 0;
        m_nowEncoders = getEncoder();
    }
    
    public void initialize()
    {
    	m_encoder.reset();
    	
    }
    
   
	public void setTurretMotorTarget(double x)
    {
    	m_turretMotor.set(-1 * x);
    	System.out.println(x);
    }
    public int getEncoder()
    {
    	return m_encoder.get();
    }
    public double getDegrees()
    {
    	m_nowEncoders = Robot.getTurret().getEncoder();
	    m_nowDegrees = ((m_nowEncoders - m_startEncoders) / m_encodersInTurn) * 360;
	    return m_nowDegrees;
    }
    public double getHeading()
    {
    	m_nowEncoders = Robot.getTurret().getEncoder();
	    m_nowDegrees = ((m_nowEncoders - m_startEncoders) / m_encodersInTurn) * 360;
	    if (m_nowDegrees >= 0)
	    {
	    	m_nowDegrees %= 360;
	    }
	    else
	    {
	    	m_nowDegrees = 360 - (Math.abs(m_nowDegrees));
	    }
	    return m_nowDegrees;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return getHeading();
    }

    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    	m_turretMotor.set(output *-1);
    	SmartDashboard.putNumber("pid output", output);
    	SmartDashboard.putNumber("setpoint", getSetpoint());
    	SmartDashboard.putNumber("pid input", returnPIDInput());
    }
}
