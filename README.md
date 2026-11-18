# 第三课 基本底盘控制
仅桌面模拟，无需手柄或日志文件；沿用 WPILib 2026.2.1 与 Phoenix 26.3.0。
打开此目录，Build，再 Simulate Robot Code。Elastic 连接 localhost，添加 AutoChooser。
每次先 Disabled，选择实验，再点击模拟 GUI 的 Autonomous；实验 6 秒自动停止，结束后回 Disabled。
1 开环：左右固定 0.15。2 Arcade：前进与转向混合。3 速度闭环：目标左右 12 电机 RPS，P=0.04，I=D=0。
AdvantageScope 连接 Simulator 的普通 NT4：画 Drive/LeftOutput、LeftMotorRps、TargetLeftRps，SimLoad 放布尔区域。
模型第 2-4 秒增加负载：开环速度下降；P 控制可补偿一部分，但存在稳态误差。不要承诺纯 P 会达到目标。
实验后改 P：在 DriveSubsystem.velocity 中两处 0.04 同步修改，重新构建并重启模拟；先用 0.01、0.04 对比。I、D 先保持零，不要求现场整定。
输出限幅仍为 0.25，目标过大时不可能达到；模型不是实车调参依据。真实硬件启动明确拒绝，避免把未经核对的反馈方向用于闭环。

## 一小时建议
10 分钟：占空比、正反转、stop；15 分钟：左右控制与 arcade；15 分钟：目标、反馈、误差，负载对比；10 分钟：P/I/D 直觉，重点 P；10 分钟：视频与复述。
P 看当前误差；I 累积过去误差，可减少稳态误差但可能饱和；D 关注变化，可改善阻尼但对噪声敏感。不是所有系统都需要三项。
阅读顺序：RobotContainer 的三个实验 → DriveSubsystem.setOutputs/arcade → velocity → DriveIOSim（老师解释模型即可）。
滚轮文件保留用于兼容前课，但第三课不实例化，不作为教学重点。

## 自学视频和官方资料
WPI 教授 Dmitry Berenson 的 PID 入门视频（WPILib 官方收录，概念仍适用，旧版 API 不照抄）：
https://docs.wpilib.org/en/2022/docs/software/advanced-controls/introduction/pid-video.html
看完回答：开环为什么受负载影响？反馈是什么？P 太小会怎样？为何不一开始就增加 I？
WPILib PIDController：
https://docs.wpilib.org/en/stable/docs/software/advanced-controls/controllers/pidcontroller.html

本次验证结果另见 VALIDATION.txt。不把构建通过当作实车验证。
