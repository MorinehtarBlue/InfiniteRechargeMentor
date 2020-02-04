/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

  // Turret Aiming System
  public static WPI_TalonSRX turretPivot = new WPI_TalonSRX(8);
  public static CANSparkMax turretElevate = new CANSparkMax(13, MotorType.kBrushless);
  private final double kMaxPivotPower = .75;
  private final double kMaxElevatePower = .5;

  // Turret Firing System
  public static CANSparkMax thrower1 = new CANSparkMax(11, MotorType.kBrushless);
  public static CANSparkMax thrower2 = new CANSparkMax(12, MotorType.kBrushless);
  public static SpeedControllerGroup thrower = new SpeedControllerGroup(thrower1, thrower2);
  private final double kMaxThrowerPower = .75;


  public Turret() {
  
  }

  public void init() {
    thrower2.setInverted(true);
    thrower.set(0);
    turretPivot.set(0);
    turretElevate.set(0);
  }

  @Override
  public void periodic() {
    // Add code here to update driver station with turret info (bearing and elevation)

  }
}
