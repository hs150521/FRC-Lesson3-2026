package frc.robot.subsystems.drive;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
/** 底盘能力层。Command 说要做什么，Subsystem 决定怎样调用 IO。 */
public class DriveSubsystem extends SubsystemBase {
  // 输出是占空比请求；测量和目标均为电机 RPS，不是轮速 m/s。
  private final edu.wpi.first.math.controller.PIDController leftPID = new edu.wpi.first.math.controller.PIDController(0.04,0,0,0.02);
  private final edu.wpi.first.math.controller.PIDController rightPID = new edu.wpi.first.math.controller.PIDController(0.04,0,0,0.02);
  private final DriveIO io;
  private final DriveIO.Inputs inputs = new DriveIO.Inputs();
  public DriveSubsystem(DriveIO io) { this.io=io; }
  public void arcade(double forward, double turn) {
    double l=forward+turn, r=forward-turn; // 本例 turn 正值表示向右转
    double scale=Math.max(1.0, Math.max(Math.abs(l),Math.abs(r)));
    setOutputs(l/scale*Constants.DRIVE_LIMIT, r/scale*Constants.DRIVE_LIMIT);
  }
  public void setOutputs(double left, double right) {
    io.setOutputs(MathUtil.clamp(left,-Constants.DRIVE_LIMIT,Constants.DRIVE_LIMIT),
                  MathUtil.clamp(right,-Constants.DRIVE_LIMIT,Constants.DRIVE_LIMIT));
  }
  public void resetPID(){leftPID.reset();rightPID.reset();}
  /** 闭环：用本轮最新反馈计算误差，再计算输出。每轮调用一次。 */
  public void velocity(double leftRps,double rightRps){
    leftPID.setPID(0.04,0,0); rightPID.setPID(0.04,0,0);
    SmartDashboard.putNumber("Drive/TargetLeftRps",leftRps);
    SmartDashboard.putNumber("Drive/TargetRightRps",rightRps);
    setOutputs(leftPID.calculate(inputs.leftMotorRps,leftRps),rightPID.calculate(inputs.rightMotorRps,rightRps));
  }
  public void stop() { io.stop(); resetPID();
    SmartDashboard.putNumber("Drive/TargetLeftRps",0);
    SmartDashboard.putNumber("Drive/TargetRightRps",0);
  }
  @Override public void periodic() {
    if (!DriverStation.isEnabled()) stop();
    io.updateInputs(inputs);
    SmartDashboard.putNumber("Drive/LeftOutput",inputs.leftOutput);
    SmartDashboard.putNumber("Drive/RightOutput",inputs.rightOutput);
    SmartDashboard.putNumber("Drive/LeftMotorRps",inputs.leftMotorRps);
    SmartDashboard.putNumber("Drive/RightMotorRps",inputs.rightMotorRps);
    SmartDashboard.putBoolean("Robot/Enabled",DriverStation.isEnabled());
  }
}
