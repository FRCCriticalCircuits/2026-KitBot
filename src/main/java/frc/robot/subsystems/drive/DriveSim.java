package frc.robot.subsystems.drive;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class DriveSim implements DriveIO{
    private final DCMotorSim leftSim;
    private final DCMotorSim rightSim;

    private double outputLeft, outputRight;

    public DriveSim(){
        leftSim =
            new DCMotorSim(
                LinearSystemId.createDCMotorSystem(
                    DCMotor.getKrakenX60(1), 1, 1),
                    DCMotor.getKrakenX60(1)
                );
        rightSim =
            new DCMotorSim(
                LinearSystemId.createDCMotorSystem(
                    DCMotor.getKrakenX60(1), 1, 1),
                    DCMotor.getKrakenX60(1)
                );
    }

    @Override
    public void updateInputs(DriveIOInputs inputs) {
        // Update simulation state
        leftSim.setInputVoltage(outputLeft * 12.0);
        rightSim.setInputVoltage(outputRight * 12.0);
        leftSim.update(0.02);
        rightSim.update(0.02);
    }

    @Override
    public void setOutputPercent(double left, double right) {
        this.outputLeft = left;
        this.outputRight = right;
    }
}
