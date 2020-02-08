/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.lang.module.ModuleDescriptor.Requires;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;


public class Autonomous extends SubsystemBase {
/**
 * @author USX27182
 *
 */
	//Requires Robot;
  
	double circumferenceInInches = 25.0;
	int pulsesPerRotation = 21934;
	double distanceToTravel = 0;
	double startPosition = 0;
	double currentAngle = 0;
	double currentPosition = 0;
	double targetPulseCount = 0;
	double targetPosition = 0;
	double drivePower = 0;
	double AUTO_DRIVE_POWER = 0.5;

  	private DriveTrain a_drive = Robot.m_driveTrain;
	
	public Autonomous() {
  
  }

	
    protected boolean hasDrivenFarEnough(double startPos, double distance) {
		//currentPosition = ((Robot.rm.lift.getSensorCollection().getQuadraturePosition() + Robot.rm.climb.getSensorCollection().getQuadraturePosition()) / 2) ;
		currentPosition = ((a_drive.leftFrontEncoder + a_drive.rightRearEncoder) / 2) ;
		targetPulseCount = (distance / circumferenceInInches) * pulsesPerRotation ;
		targetPosition = startPos + targetPulseCount;
		//System.out.println("Current Position: " + String.valueOf(currentPosition));
		//System.out.println("Target Position: " + String.valueOf(targetPulseCount));
		if (RobotState.isAutonomous() == true) {
			if (distance > 0) { // Driving FORWARD
				if (currentPosition >= targetPosition) {
					return true;
				}
				else {
					return false;
				}
			}
			else { // Driving REVERSE
				if (currentPosition <= targetPosition) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		else {
			return true;
		}
	}

   
    protected boolean strafeFarEnough(double startPos, double distance) {
    	//currentPosition = ((Robot.rm.lift.getSensorCollection().getQuadraturePosition() + Robot.rm.climb.getSensorCollection().getQuadraturePosition()) / 2) ;
    	currentPosition = ((Math.abs(a_drive.leftRearEncoder) + Math.abs(a_drive.rightRearEncoder)) / 2);
		targetPulseCount = distance / circumferenceInInches * pulsesPerRotation *  1.34;		targetPosition = startPos + targetPulseCount;
		//System.out.println("Current Position: " + String.valueOf(currentPosition));
		//System.out.println("Target Position: " + String.valueOf(targetPulseCount));
		if (distance > 0) { // Driving RIGHT
			//currentPosition = ((Math.abs(Robot.rm.lift.getSensorCollection().getQuadraturePosition() ) + Math.abs(Robot.rm.climb.getSensorCollection().getQuadraturePosition() )) / 2);
			currentPosition = ((Math.abs(a_drive.leftRearEncoder) + Math.abs(a_drive.rightRearEncoder)) / 2);
			if (currentPosition >= targetPosition) {
				return true;
			}
			else{
				return false;
			}
		}
		else { // Driving LEFT
			//currentPosition = -((Math.abs(Robot.rm.lift.getSensorCollection().getQuadraturePosition() ) + Math.abs(Robot.rm.climb.getSensorCollection().getQuadraturePosition() )) / 2);
			currentPosition = - ((Math.abs(a_drive.leftRearEncoder) + Math.abs(a_drive.rightRearEncoder)) / 2);
			if (currentPosition <= targetPosition) {
				return true;
			}
			else {
				return false;
			}
		}
	}    

    protected boolean gyroTurn(double targetAngle) {
		a_drive.rioGyro.reset();
		while ((RobotState.isAutonomous() == true) && (Math.abs(readGyro()) < Math.abs(targetAngle)) && (Math.abs(calcP(targetAngle)) > 0.25)) {
			a_drive.driveByStick(0, 0, calcP(targetAngle));//(0, calcP(targetAngle));
		}
		stop();	
		return true;
	}
    
	protected boolean gyroDrive(double distance) {
    a_drive.rioGyro.reset();
    a_drive.resetLeftRearEncoder();
    a_drive.resetRightRearEncoder();
		startPosition = ((a_drive.leftRearEncoder + a_drive.rightRearEncoder) / 2) ;
		//double targetPosition = (distance / circumferenceInInches * pulsesPerRotation);
		while (hasDrivenFarEnough(startPosition, distance) == false) {
			double drift = readGyro() / 10;
			if (distance > 0) {
				a_drive.driveByStick(0, AUTO_DRIVE_POWER, -drift);  // FORWARD
			}
			
			else {
				a_drive.driveByStick(0, -AUTO_DRIVE_POWER, -drift);  // REVERSE
			}
			
			//System.out.println("Gyro Heading: " + drift);
		}
		
		stop();
		return true;
	}
	
	protected boolean strafeDrive(double distance) {
		a_drive.rioGyro.reset();
    a_drive.resetLeftRearEncoder();
    a_drive.resetRightRearEncoder();
		//startPosition = ((Robot.rm.lift.getSensorCollection().getQuadraturePosition() + Robot.rm.climb.getSensorCollection().getQuadraturePosition()) / 2) ;
		startPosition = ((a_drive.leftRearEncoder + a_drive.rightRearEncoder) / 2);
		while (strafeFarEnough(startPosition, distance) == false) {
			double drift = readGyro() / 10;
			if (distance > 0) {
				a_drive.driveByStick(0.65, 0, -drift);  // RIGHT
			}
			
			else {
				a_drive.driveByStick(-0.65, 0, -drift);  // LEFT
			}
			
			//System.out.println("Gyro Heading: " + drift);
		}
		
		stop();
		return true;
	}
  
  /*
		//Terms For Pneumatics
	public void openClaw() {
		Robot.rm.solenoid2.set(false);
		Robot.rm.solenoid3.set(true);
	}
	
	public void closeClaw() {
		Robot.rm.solenoid2.set(true);
		Robot.rm.solenoid3.set(false);
	}
	
	public void rotateIn() {
		Robot.rm.solenoid0.set(true);
		Robot.rm.solenoid1.set(false);
	}
	
	public void rotateOut() {
		Robot.rm.solenoid0.set(false);
		Robot.rm.solenoid1.set(true);
	}
	
	public void driveAndLift(int travel, int height) {
		boolean isDone = false;
		boolean highEnough = false;
		boolean farEnough = false;
		
		//set distance to travel and lift
		//travel = 305;
		//height = 52;
		
		//set initial encoder position and destination count
		double currentPosition = ((Robot.rm.encoderLRear.get())+ (Robot.rm.encoderRRear.get()) /2);
		double targetDrvPosition = currentPosition + (travel / circumferenceInInches * pulsesPerRotation);
		double liftTime = (height/liftInPerSec) + Timer.getFPGATimestamp();
		//turn on drive motors and lift motor
		Robot.m_drive.driveCartesian(0,AUTO_DRIVE_POWER, 0);
		Robot.rm.lift.set(.75);
		
		while (isDone == false) {
			currentPosition = (Robot.rm.encoderLRear.get() + Robot.rm.encoderRRear.get()) /2;
			if (currentPosition >= targetDrvPosition) {
				farEnough = true;
				Robot.m_drive.driveCartesian(0, 0, 0);
			} 
			else {
				Robot.m_drive.driveCartesian(0, AUTO_DRIVE_POWER, 0);
			}
			// check lift far enough
			if (Timer.getFPGATimestamp() >= liftTime) {
				highEnough = true;
				Robot.rm.lift.set(0);
			}
			isDone = highEnough && farEnough ? true : false;
		}
		
	}
		// Terms for Lift
		// Without encoder on lift assembly, measurement is based on time
		// To measure based on time, a given rate must be known - inches traveled per second
		// Set the rate in liftInPerSec constant at top of class
	public void liftUp(double height) {
		
		double liftTime = (height/liftInPerSec) * 1000;
		double motorTime = 0;
			while (motorTime <= liftTime) {
				Robot.rm.lift.set(1.0);
				Timer.delay(0.050);
				motorTime = motorTime + 50;
			}
			
			Robot.rm.lift.set(0);
	}
	
	public void liftDown(double height) {
	
		double liftTime = (Math.abs(height)/liftInPerSec) * 1000;
		double motorTime = 0;
			while (motorTime <= liftTime) {
				Robot.rm.lift.set(-1.0);
				Timer.delay(0.050);
				motorTime = motorTime + 50;
			}
			
			Robot.rm.lift.set(0);
	}
  */
  
		//Drive Directions
	public void driveforward(double distance) {
		gyroDrive(distance);
	}
	
	public void drivebackward(double distance) {
		gyroDrive(-Math.abs(distance));
	}
	
	public void strafeleft(double distance) {
		strafeDrive(-distance);
	}
	
	public void straferight(double distance) {
		strafeDrive(distance);
	}
	
	public void turnleft(double degrees) {
		gyroTurn(-degrees);
	}
	
	public void turnright(double degrees) {
		gyroTurn(degrees);
	}
	
	//--------------------------------------

	protected double readGyro() {
		double angle = a_drive.rioGyro.getAngle();
		return angle;
	}
	
	protected double calcP(double tAngle) {
		double p = 1 * ((1-(Math.abs(readGyro()) / Math.abs(tAngle))) - 0.05);	
		if (tAngle > 0) {
			return p;
		}
		
		else {
			return (p * -1);
		}
		
	}
	
	public void stop() {

		a_drive.driveByStick(0, 0, 0);
    	//taskDone = true;
    	
    }

  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
