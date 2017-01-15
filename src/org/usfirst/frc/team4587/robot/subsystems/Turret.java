package org.usfirst.frc.team4587.robot.subsystems;

import utility.LogDataSource;
import org.usfirst.frc.team4587.robot.Robot;
import org.usfirst.frc.team4587.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.RampedSpeedController;
import utility.ValueLogger;
import utility.RampedSpeedController.ControllerType;

/**
 *
 */
public class Turret extends Subsystem implements LogDataSource {

	private RampedSpeedController m_turretMotor;
	private Encoder m_encoder;
	private double m_encodersInTurn;
	private double m_startEncoders;
	public double m_nowDegrees;
	private double m_nowEncoders;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	

    /*private boolean ball_is_loaded = false;
    public boolean isBallLoaded()
    {
    	if ( ball_is_loaded == false ) {
    		if ( getIntakeSwitch() == false ) {
    			ball_is_loaded = true;
    		}
    	}
    	return ball_is_loaded;
    }
    public void setBallIsLoaded ( boolean x )
    {
    	ball_is_loaded = x;
    }
*/
    public Turret()
    {    	
        m_turretMotor = new RampedSpeedController(ControllerType.Talon,RobotMap.MOTOR_TURRET);
        m_encoder = new Encoder(RobotMap.TURRET_ENCODER_A, RobotMap.TURRET_ENCODER_B);
        m_encodersInTurn = 256;
        m_startEncoders = getEncoder();
        m_nowDegrees = 0;
        m_nowEncoders = getEncoder();
    }
    
    public void initialize()
    {
    	m_encoder.reset();
    	
        m_turretMotor.setMaxRaisePerInterval (0.2);
        m_turretMotor.setMaxLowerPerInterval (0.4);
        m_turretMotor.setPositiveDeadband    (0.0);
        m_turretMotor.setNegativeDeadband    (0.0);
        m_turretMotor.setDesiredSetting(0.0);
    }
    
    public double getTurretMotorTarget()
	{
		return m_turretMotor.getDesiredSetting() * -1;
	}
	public void setTurretMotorTarget(double x)
    {
    	m_turretMotor.set(-1 * x);
    }
    public double getTurretMotorActual()
    {
    	return m_turretMotor.getMotorLevel() * -1;
    }
    public void updateTurretMotor()
    {
    	m_turretMotor.updateMotorLevel();
    }
    public int getEncoder()
    {
    	return m_encoder.get();
    }
    public double getDegrees()
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
        //setDefaultCommand(new IntakeIdle());
    }
    
    public void gatherValues( ValueLogger logger)
    {
    	//m_turretMotor.gatherValues(logger);
    	logger.logDoubleValue("Turret Motor Value", m_turretMotor.get());
    }

}