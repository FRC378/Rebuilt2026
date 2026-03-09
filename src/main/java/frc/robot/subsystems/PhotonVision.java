// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotonVision extends SubsystemBase {


  private PhotonCamera m_camera;
  private String       m_cameraName;

  private int    m_targetId;
  private boolean m_targetValid;
  private double m_targetYaw;
  private double m_targetDistance;


  /** Creates a new PhotonVision. */
  public PhotonVision( String cameraName ) {

    m_camera = new PhotonCamera(cameraName);
    m_cameraName = cameraName;

    m_targetId = 4; //Initial target value

    SmartDashboard.putNumber(m_cameraName+"-MT Id", m_targetId);

    SmartDashboard.putString(m_cameraName+"-CameraName", m_cameraName);

  }



  @Override
  public void periodic() {



    // "results" is a list of all the camera results that have not yet been read
    //  i.e.  A "list of lists of targets"
    var results = m_camera.getAllUnreadResults();

    //Make sure we have at least one list in results
    if (!results.isEmpty()) {

      // Assume the worst
      m_targetValid = false;
      m_targetYaw = 0.0;
      m_targetDistance = 0.0;


      //Ok, now go get the latest list of targets
      PhotonPipelineResult result = results.get(results.size() - 1);
      boolean hasTargets = result.hasTargets();



      if (hasTargets) {

        //---- All Target List --------------------------
        List<PhotonTrackedTarget> targetList = result.getTargets();

        String targetListString = "";
        for (PhotonTrackedTarget target : targetList) {
          targetListString += target.getFiducialId() + " ";
        }
        SmartDashboard.putString(m_cameraName+"-Tlist", targetListString);

        //---- Specific Target --------------------------
        m_targetId = (int) SmartDashboard.getNumber(m_cameraName+"-MT Id", m_targetId);
        int myTargetIndex = -1;

        // Look for Target Id in list
        for (int i = 0; i < targetList.size(); i++) {
          if (targetList.get(i).getFiducialId() == m_targetId) {
            m_targetValid = true;
            myTargetIndex = i;
            break;
          }
        }

        // Is specific target found?
        if (m_targetValid && myTargetIndex >= 0) {
          PhotonTrackedTarget myTarget = targetList.get(myTargetIndex);
          m_targetYaw = myTarget.getYaw();

          final double cameraHeightMeters = Units.inchesToMeters(8.2);
          final double targetHeightMeters = Units.inchesToMeters(57.0);
          final double cameraPitchRadians = Units.degreesToRadians(36.7);

          double rangeMeters = PhotonUtils.calculateDistanceToTargetMeters(
              cameraHeightMeters,
              targetHeightMeters,
              cameraPitchRadians,
              Units.degreesToRadians(myTarget.getPitch()));

          m_targetDistance = Units.metersToInches(rangeMeters);
        }


        // Status Update
        SmartDashboard.putBoolean(m_cameraName+"-MT Target", m_targetValid);
        SmartDashboard.putNumber(m_cameraName+"-MT Yaw", m_targetYaw);
        SmartDashboard.putNumber(m_cameraName+"-MT Range", m_targetDistance);

      } else {
        // NO TARGETS DETECTED
        SmartDashboard.putString(m_cameraName+"-Tlist", "");

        SmartDashboard.putBoolean(m_cameraName+"-MT Target", false);
        SmartDashboard.putNumber(m_cameraName+"-MT Yaw", 0.0);
        SmartDashboard.putNumber(m_cameraName+"-MT Range", 0.0);

      }

    }

  }


  public void setTargetId(int id) {
    // Using smartdashboard to pick target ID
    SmartDashboard.putNumber(m_cameraName+"-MT Id", id);
  }

  public int getTargetId() {
    return m_targetId;
  }

  public boolean isTargetValid() {
    return m_targetValid;
  }

  public boolean isTargetValidAndInRange() {
    final double maxInterpolationRange = 110.0;
    return m_targetValid && (m_targetDistance < maxInterpolationRange);
  }

  public double getTargetYaw() {
    return m_targetValid ? m_targetYaw : 0.0;
  }

  public double getTargetDistance() {
    return m_targetValid ? m_targetDistance : 0.0;
  }
}
