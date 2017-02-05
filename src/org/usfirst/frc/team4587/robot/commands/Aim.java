package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Aim extends Command {

	private double m_centerline;
    public Aim() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getTurret());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.getTurret().aiming())
    	{
	    	m_centerline = SmartDashboard.getNumber("pi.centerline", -1);
	    	if (m_centerline > 0)
	    	{
	    		if(Math.abs(m_centerline - 320) > 20)
	    		{
	    			double error = (m_centerline - 320) / 16.0;
	    			Robot.getTurret().setSetpoint(Robot.getTurret().getSetpoint() + error);
	    			SmartDashboard.putNumber("Desired Setpoint", Robot.getTurret().getSetpoint() + error);
	    		}
	    	}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
