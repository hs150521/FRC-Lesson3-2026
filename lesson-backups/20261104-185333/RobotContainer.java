package frc.robot;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.subsystems.drive.*;
/** 第三课：在 Disabled 时选择实验，再在模拟 GUI 点击 Autonomous。 */
public class RobotContainer {
 private final DriveSubsystem drive;
 private final SendableChooser<Command> chooser=new SendableChooser<>();
 public RobotContainer(){
  if(RobotBase.isReal()) throw new IllegalStateException("第三课为桌面实验，实车需单独适配反馈方向");
  drive=new DriveSubsystem(new DriveIOSim());
  chooser.setDefaultOption("0 Stop",Commands.none());
  chooser.addOption("1 Open loop 0.15",drive.runEnd(()->drive.setOutputs(0.15,0.15),drive::stop).withTimeout(6));
  chooser.addOption("2 Arcade forward and turn",drive.runEnd(()->drive.arcade(0.5,0.2),drive::stop).withTimeout(6));
  chooser.addOption("3 Closed loop 12 motor RPS",Commands.runOnce(drive::resetPID,drive).andThen(drive.runEnd(()->drive.velocity(12,12),drive::stop).withTimeout(6)));
  SmartDashboard.putData("AutoChooser",chooser);
 }
 public Command getAutonomousCommand(){return chooser.getSelected();}
 public void stopAll(){drive.stop();}
}
