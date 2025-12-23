// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.EnumSet;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;

public class ExampleSubsystem extends SubsystemBase {

  private TalonFX leverMotor = new TalonFX(1, "can");
  
  public ExampleSubsystem() {
    configNT();
  }

  public void configPID(double p, double i, double d, double ff) {

        Slot0Configs slot0Configs = new Slot0Configs(); 
        slot0Configs.kP = p;
        slot0Configs.kI = i;
        slot0Configs.kD = d;
        slot0Configs.kG = ff;
        
        leverMotor.getConfigurator().apply(slot0Configs);
    }


  private void configNT(){

    NetworkTableInstance.getDefault().getTable("values")
            .getEntry("PIDF")
            .setDoubleArray(new double[] {5, 0, 0, 0});

    NetworkTableInstance.getDefault().getTable("values").addListener("PIDF", EnumSet.of(NetworkTableEvent.Kind.kValueAll),
            (table, key, event) -> {
                double[] pidf = event.valueData.value.getDoubleArray();
                configPID(pidf[0], pidf[1], pidf[2], pidf[3]);
            }
        );
  }

}
