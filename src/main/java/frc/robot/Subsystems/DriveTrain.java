/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class DriveTrain extends SubsystemBase {

  private final WPI_TalonSRX leftFront = new WPI_TalonSRX(5);
  private final WPI_TalonSRX leftRear = new WPI_TalonSRX(6);
  private final WPI_TalonSRX rightFront = new WPI_TalonSRX(10);
  private final WPI_TalonSRX rightRear = new WPI_TalonSRX(9);
  private final MecanumDrive m_drive;

  /**
   * Creates a new DriveTrain.
   */
  public DriveTrain() {
    m_drive = new MecanumDrive(leftFront, leftRear, rightFront, rightRear);
  }

  public void driveByStick(final double liveX, final double liveY, final double liveZ) {
       m_drive.driveCartesian(liveX, liveY, liveZ);
   }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
