package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class testTurretSetSetpoint extends Command {

    public testTurretSetSetpoint() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.getTurret());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	double testSetpoint = Robot.getTurret().m_testSetPoint;
    	while (testSetpoint >= 360)
    	{
    		testSetpoint -= 360;
    	}
    	while (testSetpoint < 0)
    	{
    		testSetpoint += 360;
    	}
    	
    	Robot.getTurret().setSetpoint(testSetpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
