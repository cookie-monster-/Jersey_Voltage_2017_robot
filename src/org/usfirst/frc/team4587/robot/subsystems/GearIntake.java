package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import utility.LogDataSource;
import utility.RampedSpeedController;
import utility.ValueLogger;
import utility.RampedSpeedController.ControllerType;

/**
 *
 */
public class GearIntake extends Subsystem implements LogDataSource {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	private SpeedController m_gearIntakeMotor;
    public void setGearIntakeMotor(double x)
    {
    	m_gearIntakeMotor.set(-1 * x);
    	m_motorOn = !(Math.abs(x) < 0.01);
    	
    }

    private DigitalInput m_gearIntakeSwitch;
    public boolean getGearIntakeSwitch()
    {
    	return m_gearIntakeSwitch.get();
    }
    private boolean m_motorOn;
    public boolean motorOn()
    {
    	return m_motorOn;
    }

    private boolean gearIsLoaded = false;
    public boolean isGearLoaded()
    {
    	if ( gearIsLoaded == false ) {
    		if ( getGearIntakeSwitch() == false ) {
    			gearIsLoaded = true;
    		}
    	}
    	return gearIsLoaded;
    }
    public void setGearIsLoaded ( boolean x )
    {
    	gearIsLoaded = x;
    }

    public GearIntake()
    {    	
        m_gearIntakeMotor = new Spark(RobotMap.MOTOR_GEAR_INTAKE);
        

        m_gearIntakeSwitch = new DigitalInput(RobotMap.SWITCH_GEAR_INTAKE_LIMIT);
        m_motorOn = false;
    }
    
    public void initialize()
    {
    	gearIsLoaded = false;

    }
    
    public void gatherValues( ValueLogger logger)
    {
    	logger.logBooleanValue("Intake Switch", getGearIntakeSwitch());
    	//m_gearIntakeMotor.gatherValues(logger);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

