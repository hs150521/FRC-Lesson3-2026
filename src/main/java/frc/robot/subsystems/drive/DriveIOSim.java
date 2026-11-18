package frc.robot.subsystems.drive;
import edu.wpi.first.wpilibj.DriverStation;
/** 一阶教学模型：有惯性；第 2-4 秒模拟负载，开环与闭环可比较。不是实车参数。 */
public class DriveIOSim implements DriveIO {
 private double left,right,lv,rv,elapsed;
 private boolean wasEnabled;
 public void setOutputs(double l,double r){left=l;right=r;}
 public void updateInputs(Inputs inputs){
  boolean enabled=DriverStation.isEnabled();
  if(enabled&&!wasEnabled){elapsed=0;lv=0;rv=0;}
  wasEnabled=enabled;
  if(!enabled){stop();elapsed=0;lv=0;rv=0;}else{
   elapsed+=0.02;
   double gain=(elapsed>=2&&elapsed<4)?50:80;
   lv+=(left*gain-lv)*0.02/0.25;
   rv+=(right*gain-rv)*0.02/0.25;
  }
  inputs.leftOutput=left;inputs.rightOutput=right;
  inputs.leftMotorRps=lv;inputs.rightMotorRps=rv;
  edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.putBoolean("Drive/SimLoad",elapsed>=2&&elapsed<4);
 }
}
