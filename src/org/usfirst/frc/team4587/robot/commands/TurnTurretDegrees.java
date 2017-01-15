package org.usfirst.frc.team4587.robot.commands;

import org.usfirst.frc.team4587.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnTurretDegrees extends Command {
	
	public double motorLevel;
	private double m_degreesToTurn;
	private double m_startDegrees;

    public TurnTurretDegrees(double motorLevel, int degrees) {
    	this.motorLevel = motorLevel;
    	m_degreesToTurn = degrees;
    	requires(Robot.getTurret());
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	m_startDegrees = Robot.getTurret().getDegrees();
    	Robot.getTurret().setTurretMotorTarget(motorLevel);
    	//m_startEncoders = 10;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	Robot.getTurret().updateTurretMotor();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (m_degreesToTurn < 0)
        {
        	return m_degreesToTurn >= Robot.getTurret().getDegrees() - m_startDegrees;
        }
        else if (m_degreesToTurn > 0)
        {
        	return m_degreesToTurn <= Robot.getTurret().getDegrees() - m_startDegrees;        	
        }
        else
        {
        	return true;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.getTurret().setTurretMotorTarget(0.0);
    	Robot.getTurret().updateTurretMotor();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
