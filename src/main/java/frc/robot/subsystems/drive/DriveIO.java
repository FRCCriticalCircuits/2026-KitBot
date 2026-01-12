package frc.robot.subsystems.drive;

public interface DriveIO {
    public static class DriveIOInputs {
        public double leftVolts = 0.0;
        public double rightVolts = 0.0;
    }

    public default void updateInputs(DriveIOInputs inputs) {}
    public default void setOutputPercent(double left, double right) {}
}
