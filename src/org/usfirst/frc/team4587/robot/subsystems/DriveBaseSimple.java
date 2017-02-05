package org.usfirst.frc.team4587.robot.subsystems;

import org.usfirst.frc.team4587.robot.commands.DriveSimpleWithJoysticks;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveBaseSimple extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.\
	private SpeedController left0;//23
	private SpeedController left1;
	private SpeedController right0;//01
	private SpeedController right1;
	
	private RobotDrive drive0;
	private RobotDrive drive1;
	
	public DriveBaseSimple()
	{
		left0 = new Spark(2);
		left1 = new Spark(3);
		right0 = new Spark(0);
		right1 = new Spark(1);
		
		drive0 = new RobotDrive(left0, right0);
		drive1 = new RobotDrive(left1, right1);
		
	}

	public void arcadeDrive(double drive, double turn)
	{
		drive0.arcadeDrive(drive, turn*-1);
		drive1.arcadeDrive(drive, turn*-1);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveSimpleWithJoysticks());
    }
}

