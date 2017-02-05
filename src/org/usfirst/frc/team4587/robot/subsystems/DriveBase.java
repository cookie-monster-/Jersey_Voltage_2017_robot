package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.RobotMap;
import org.usfirst.frc.team4587.robot.commands.DriveWithJoysticks;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import utility.LogDataSource;
import utility.RampedSpeedController;
import utility.RingBuffer;
import utility.ValueLogger;

/**
 *
 */
public class DriveBase extends Subsystem implements LogDataSource {
	private RampedSpeedController m_leftController, m_rightController;
	public RampedSpeedController getLeftController()
	{
		return m_leftController;
	}
	public RampedSpeedController getRightController()
	{
		return m_rightController;
	}
	private RobotDrive m_robotDrive;
	private Encoder m_encoderRight, m_encoderLeft;
	private double m_speedLimiter;
	private double m_turnSpeedLimiter;
	
	private RingBuffer m_leftRingBuffer;
	private RingBuffer m_rightRingBuffer;
	
	private int m_lastLeftEncoderValue;
	private int m_lastRightEncoderValue;
	private double m_leftAdjuster;
	private double m_rightAdjuster;
	
	private int m_tolerance;
	private double m_balance;
	private boolean m_wasStraight;
	private double m_lastTurnInput;
	private double m_lastDriveInput;
	
	private double m_wheelDiameter;
	private int m_encoderDotsPerRev, m_encoderGearTeeth, m_wheelGearTeeth;

	public DriveBase(){
		m_leftController = new RampedSpeedController(RampedSpeedController.ControllerType.VictorSP, RobotMap.MOTOR_LEFT_DRIVETRAIN); //2,3
		m_rightController = new RampedSpeedController(RampedSpeedController.ControllerType.VictorSP, RobotMap.MOTOR_RIGHT_DRIVETRAIN); //0,1
		//m_rightController.setInverted(true);
		
		
		
		m_robotDrive = new RobotDrive(m_leftController, m_rightController);
		
		m_encoderLeft = new Encoder(RobotMap.ENCODER_LEFT_DRIVE_A, 
									RobotMap.ENCODER_LEFT_DRIVE_B);
		
		m_encoderRight = new Encoder(RobotMap.ENCODER_RIGHT_DRIVE_A, 
									 RobotMap.ENCODER_RIGHT_DRIVE_B);
	
		initialize();
	}
	
	public void initialize()
	{
		m_encoderLeft.reset();
		m_encoderRight.reset();
		
		m_speedLimiter = 1.0;
		m_turnSpeedLimiter = 1.0;
		
		m_wasStraight = false;
		
		m_leftRingBuffer = new RingBuffer();
		m_rightRingBuffer = new RingBuffer();
		
		m_lastLeftEncoderValue = getEncoderLeft();
		m_lastRightEncoderValue = getEncoderRight();
		
		m_leftAdjuster = 1.0;
		m_rightAdjuster = 1.0;
		m_tolerance = 5;
		m_balance = .0625;
		
		m_wheelDiameter = 7.65;
		m_encoderDotsPerRev = 256;
		m_encoderGearTeeth = 12;
		m_wheelGearTeeth = 22;
	}
	
	public int getEncoderLeft()
	{
		return m_encoderLeft.get() * -1;
	}
	
	public int getEncoderRight()
	{
		return m_encoderRight.get() * -1;
	}
	
	public void teleopDrive(double driveInput, double turnInput)
	{
		if ((Math.abs(driveInput) < 0.1 && Math.abs(turnInput) < 0.1)) 
		{
			m_leftController.setDesiredSetting(0.0);
			m_rightController.setDesiredSetting(0.0);
			m_wasStraight = false;
		}
		else if (Math.abs(turnInput) < 0.1)
		{
			if(m_wasStraight == false)
			{
				m_rightRingBuffer.clear();
				m_leftRingBuffer.clear();
			}
			m_leftRingBuffer.insertDelta(getEncoderLeft() - m_lastLeftEncoderValue);
			m_rightRingBuffer.insertDelta(getEncoderRight() - m_lastRightEncoderValue);
			if(m_wasStraight == true)
			{
				if(m_rightRingBuffer.isFull())
				{
					int rightDelta = m_rightRingBuffer.getAverage();
					int leftDelta = m_leftRingBuffer.getAverage();
					if((leftDelta - rightDelta) > m_tolerance)
					{
						if(m_leftAdjuster >= 1.0)
						{
							m_rightAdjuster -= m_balance;
							if ( m_rightAdjuster < 0.2 ) m_rightAdjuster = 0.2;
						}
						else
						{
							m_leftAdjuster += m_balance;
							if ( m_leftAdjuster > 1.0 ) m_leftAdjuster = 1.0;
						}
					}
					else if((rightDelta - leftDelta) > m_tolerance)
					{
						if(m_rightAdjuster >= 1.0)
						{
							m_leftAdjuster -= m_balance;
							if ( m_leftAdjuster < 0.2 ) m_leftAdjuster = 0.2;
						}
						else
						{
							m_rightAdjuster += m_balance;
							if ( m_rightAdjuster > 1.0 ) m_rightAdjuster = 1.0;
						}
					}
				}
			}
			m_leftController.setDesiredSetting(driveInput * m_leftAdjuster * m_speedLimiter);
			m_rightController.setDesiredSetting(driveInput * m_rightAdjuster * m_speedLimiter * -1);

			m_wasStraight = true;
		}
		else
		{
			m_robotDrive.arcadeDrive((driveInput * m_turnSpeedLimiter), turnInput, true/*Squared Inputs*/);
			m_wasStraight = false;
		}
		m_leftController.updateMotorLevel();
		m_rightController.updateMotorLevel();
		m_lastLeftEncoderValue = getEncoderLeft();
		m_lastRightEncoderValue = getEncoderRight();
		m_lastTurnInput = turnInput;
		m_lastDriveInput = driveInput;
	}

	public void setSpeedLimiter(double x)
	{
		m_speedLimiter = x;
	}
	
	public double straightDistanceTraveled(int startLeft, int startRight)
	{
		int deltaLeft = getEncoderLeft() - startLeft;
		int deltaRight = getEncoderRight() - startRight;
		double average = deltaLeft + deltaRight / 2.0;
		return average / m_encoderDotsPerRev * m_encoderGearTeeth / m_wheelGearTeeth * Math.PI * m_wheelDiameter;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new DriveWithJoysticks());
    }
    
    public void gatherValues ( ValueLogger logger )
    {
    	logger.logDoubleValue ( "Left Motor Value",            m_leftController.get() );
    	logger.logDoubleValue ( "Right Motor Value",           m_rightController.get() );
    	logger.logIntValue    ( "Left Encoder Value",          getEncoderLeft() );
    	logger.logIntValue    ( "Right Encoder Value",         getEncoderRight() );
    	logger.logIntValue    ( "Left Encoder Delta Average",  m_leftRingBuffer.getAverage() );
    	logger.logIntValue    ( "Right Encoder Delta Average", m_rightRingBuffer.getAverage() );
    	logger.logDoubleValue ( "Drive Speed Limiter",         m_speedLimiter );
    	logger.logDoubleValue ( "Turn Speed Limiter",          m_turnSpeedLimiter );
    	logger.logIntValue    ( "Delta Tolerance Value",       m_tolerance );
    	logger.logDoubleValue ( "Straight Assist Balancer",    m_balance );
    	logger.logDoubleValue ( "Last Drive Input",            m_lastDriveInput );
    	logger.logDoubleValue ( "Last Turn Input",             m_lastTurnInput );
    	logger.logDoubleValue ( "Left Adjuster",               m_leftAdjuster );
    	logger.logDoubleValue ( "Right Adjuster",              m_rightAdjuster );
    	//logger.logDoubleValue ( "Distance Traveled",              straightDistanceTraveled() );
    }
    
    // 2016-03-13: Methods after here should eventually be deleted...

    public String getValues(){
    	StringBuffer sb = new StringBuffer();
    	/*Parameters.displayDouble("Left Motor Value", m_leftController.get(), sb);
    	Parameters.displayDouble("Right Motor Value", m_rightController.get(), sb);
    	Parameters.displayInt("Left Encoder Value", getEncoderLeft(), sb);
    	Parameters.displayInt("Right Encoder Value", getEncoderRight(), sb);
    	Parameters.displayInt("Right Encoder Delta Value", m_rightRingBuffer.getAverage(), sb);
    	Parameters.displayInt("Left Encoder Delta Value", m_leftRingBuffer.getAverage(), sb);*/
    	sb.append(",").append(m_speedLimiter);
    	//Parameters.displayInt("Delta Tolerance Value", m_tolerance, sb);
    	//Parameters.displayDouble("Delta Balance Value", m_balance, sb);
    	sb.append(",").append(m_lastTurnInput);
    	sb.append(",").append(m_lastDriveInput);
    	return sb.toString().substring(1);
    }
    
    public String getHeaders(){
    	StringBuffer sb = new StringBuffer();
    	sb.append("Left Motor Value");
    	sb.append(",Right Motor Value");
    	sb.append(",Left Encoder Value");
    	sb.append(",Right Encoder Value");
    	sb.append(",Right Encoder Delta Value");
    	sb.append(",Left Encoder Delta Value");    	
    	sb.append(",Speed Limiter");
    	sb.append(",Delta Tolerance Value");
    	sb.append(",Delta Balance Value");
    	sb.append(",Last Drive Input");
    	sb.append(",Last Turn Input");
    	return sb.toString();
    }
}