
package org.usfirst.frc.team4587.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import utility.LogDataSource;
import utility.ValueLogger;

import org.usfirst.frc.team4587.robot.commands.ExampleCommand;
import org.usfirst.frc.team4587.robot.commands.TurnTurretDegrees;
import org.usfirst.frc.team4587.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team4587.robot.subsystems.Turret;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements LogDataSource {

	private static Robot m_robot;
	public static Robot getInstance()
	{
		return m_robot;
	}
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	private static Turret m_turret;
	public static Turret getTurret()
	{
		return m_turret;
	}
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	private static ValueLogger  logger;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_robot = this;
		m_turret = new Turret();
		
		
		
		oi = new OI();
		
        logger = new ValueLogger("/home/lvuser/dump",10);
        logger.registerDataSource(this);
        logger.registerDataSource(m_turret);
		/*chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);*/
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		initializeNewPhase(ValueLogger.DISABLED_PHASE);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		initializeNewPhase(ValueLogger.AUTONOMOUS_PHASE);
		autonomousCommand = chooser.getSelected();
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		long start = System.nanoTime();
        Scheduler.getInstance().run();
        if ( logger != null ) logger.logValues(start);
	}

	@Override
	public void teleopInit() {
		initializeNewPhase(ValueLogger.TELEOP_PHASE);
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		long start = System.nanoTime();
		boolean on = true;
    
		Scheduler.getInstance().run();
		if ( logger != null ) logger.logValues(start);
		SmartDashboard.putNumber("Turret Encoder", m_turret.getEncoder());
		SmartDashboard.putNumber("Turret Degrees", m_turret.getDegrees());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	private void initializeNewPhase ( String whichPhase )
    {
        if ( autonomousCommand != null ) {
            autonomousCommand.cancel();
            autonomousCommand = null;
        }
    	/*Parameters.readValues();
    	if ( m_iAmARealRobot ) {
            Robot.getDriveBase().initialize();
            Robot.getIntake().initialize();
    	}
        if ( m_iHaveACamera ) {
            CameraThread.initializeForPhase();
        }
        Gyro.reset();*/
        if ( logger != null ) logger.initializePhase(whichPhase);
    }
	
	public void gatherValues ( ValueLogger logger )
    {
    	//logger.logDoubleValue ( "Gyro Yaw", Gyro.getYaw() );
    	//logger.logBooleanValue( "IMU_Connected", Gyro.IMU_Connected() );
    	//logger.logBooleanValue( "IMU_IsCalibrating", Gyro.IMU_IsCalibrating() );
    }
}
