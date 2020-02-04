/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {

  private final WPI_TalonFX leftFront = new WPI_TalonFX(1);
  private final WPI_TalonFX leftRear = new WPI_TalonFX(2);
  private final WPI_TalonFX rightFront = new WPI_TalonFX(3);
  private final WPI_TalonFX rightRear = new WPI_TalonFX(4);
  private final MecanumDrive m_drive;

  // Encoders on DriveTrain motors
  public double leftFrontEncoder = 0;
  public double leftRearEncoder = 0;
  public double rightFrontEncoder = 0;
  public double rightRearEncoder = 0;

  /**
   * Creates a new DriveTrain.
   */
  public DriveTrain() {
    m_drive = new MecanumDrive(leftFront, leftRear, rightFront, rightRear);
    m_drive.setDeadband(.05);
    leftFront.getSensorCollection().setIntegratedSensorPosition(0, 2);
    leftRear.getSensorCollection().setIntegratedSensorPosition(0, 2);
    rightFront.getSensorCollection().setIntegratedSensorPosition(0, 2);
    rightRear.getSensorCollection().setIntegratedSensorPosition(0, 2);
  }

  public void driveByStick(final double liveX, final double liveY, final double liveZ) {
       m_drive.driveCartesian(liveX, liveY, liveZ);
   }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    leftFrontEncoder = leftFront.getSensorCollection().getIntegratedSensorPosition();
    leftRearEncoder = leftRear.getSensorCollection().getIntegratedSensorPosition();
    rightFrontEncoder = rightFront.getSensorCollection().getIntegratedSensorPosition();
    rightRearEncoder = rightRear.getSensorCollection().getIntegratedSensorPosition();
  
  }
}
