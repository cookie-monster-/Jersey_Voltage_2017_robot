package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
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

	private RampedSpeedController m_gearIntakeMotor;
    public double getGearIntakeMotorTarget()
    {
    	return m_gearIntakeMotor.getDesiredSetting() * -1;
    }
    public void setGearIntakeMotorTarget(double x)
    {
    	m_gearIntakeMotor.set(-1 * x);
    }
    public double getGearIntakeMotorActual()
    {
    	return m_gearIntakeMotor.getMotorLevel() * -1;
    }
    public void updateGearIntakeMotor()
    {
    	m_gearIntakeMotor.updateMotorLevel();
    }

    private DigitalInput m_gearIntakeSwitch;
    public boolean getGearIntakeSwitch()
    {
    	return m_gearIntakeSwitch.get();
    }

    private boolean gearIsLoaded = false;
    public boolean isBallLoaded()
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
        m_gearIntakeMotor = new RampedSpeedController(ControllerType.Talon,RobotMap.MOTOR_GEAR_INTAKE);
        m_gearIntakeMotor.setName("GearIntake");
        

        m_gearIntakeSwitch = new DigitalInput(RobotMap.SWITCH_GEAR_INTAKE_LIMIT);
    }
    
    public void initialize()
    {
    	gearIsLoaded = false;

        m_gearIntakeMotor.setMaxRaisePerInterval (0.2);
        m_gearIntakeMotor.setMaxLowerPerInterval (0.4);
        m_gearIntakeMotor.setPositiveDeadband    (0.0);
        m_gearIntakeMotor.setNegativeDeadband    (0.0);
        m_gearIntakeMotor.setDesiredSetting(0.0);
    }
    
    public void gatherValues( ValueLogger logger)
    {
    	logger.logBooleanValue("Intake Switch", getGearIntakeSwitch());
    	m_gearIntakeMotor.gatherValues(logger);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

