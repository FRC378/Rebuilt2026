// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.filter.MedianFilter;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RangeFinder extends SubsystemBase {
  private Ultrasonic m_rangeFinder;
  private MedianFilter m_rangeFilter;
  private double m_rangeRaw;
  private double m_rangeFiltered;
  private boolean m_rangeValid;

  public RangeFinder(int pingCh, int echoCh, int filterSize) {
    m_rangeFinder = new Ultrasonic(pingCh, echoCh);
    m_rangeFilter = new MedianFilter(filterSize);

    m_rangeRaw = 0.0;
    m_rangeFiltered = 0.0;
    m_rangeValid = false;
  }

  @Override
  public void periodic() {
    m_rangeRaw = m_rangeFinder.getRangeInches();
    m_rangeFiltered = m_rangeFilter.calculate(m_rangeRaw);
    m_rangeValid = m_rangeFinder.isRangeValid();

    SmartDashboard.putNumber("Range Raw", m_rangeRaw);
    SmartDashboard.putNumber("Range Filtered", m_rangeFiltered);
    SmartDashboard.putBoolean("Range Valid", m_rangeValid);

    m_rangeFinder.ping();
  }
  public double GetRange() {
    return m_rangeFiltered;
  }

  public boolean GetValid() {
    return m_rangeValid;
  }



}
