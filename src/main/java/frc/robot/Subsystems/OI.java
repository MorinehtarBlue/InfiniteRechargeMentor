/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class OI extends SubsystemBase {

  public final Joystick m_stick = new Joystick(0);
  public static double liveX;
  public static double liveY;
  public static double liveZ;
  public static double livePow;
  /**
   * Creates a new OI.
   */
  public OI() {

  }

  public void get(){
    liveX = m_stick.getX();
    liveY = m_stick.getY();
    liveZ = m_stick.getZ();
    livePow = m_stick.getThrottle();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
