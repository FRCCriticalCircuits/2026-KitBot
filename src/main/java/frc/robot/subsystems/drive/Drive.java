package frc.robot.subsystems.drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drive.DriveIO.DriveIOInputs;

public class Drive extends SubsystemBase{
    private DriveIO io;
    private DriveIOInputs driveInputs;

    public Drive(
        DriveIO io
    ){
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(driveInputs);
    }

    public Command tankDrive(DoubleSupplier forward, DoubleSupplier turn){
        double left = forward.getAsDouble() + turn.getAsDouble();
        double right = forward.getAsDouble() - turn.getAsDouble();

        return Commands.run(
            () -> {
                io.setOutputPercent(left, right);
            },
            this
        );
    }   
}
