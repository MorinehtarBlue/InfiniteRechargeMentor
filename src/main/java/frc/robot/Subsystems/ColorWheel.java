/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class ColorWheel extends SubsystemBase {
  
  // Color Sensor
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  public Color detectedColor = m_colorSensor.getColor();

  // Motor to rotate
  public static WPI_TalonSRX colorWheelMotor = new WPI_TalonSRX(5);
  private final double kMaxRotateMotorPower = 0.25;
  private final double kMinRotateMotorPower = 0.15;

  // Motor to raise and lower arm
  public static WPI_TalonSRX colorWheelArm = new WPI_TalonSRX(6);
  private final double kMaxArmMotorPower = 0.25;
  private final double kMinArmMotorPower = 0.15;
  
  // Pneumatics to lift/lower
  
  public ColorWheel() {

  }

  @Override
  public void periodic() {
    // Update color value from sensor to dashboard
    detectedColor = m_colorSensor.getColor();

  }
}
