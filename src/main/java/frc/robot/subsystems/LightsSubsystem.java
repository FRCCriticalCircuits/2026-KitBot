package frc.robot.subsystems;

import com.lumynlabs.devices.ConnectorX;
import com.lumynlabs.connection.usb.USBPort;
import com.lumynlabs.domain.led.MatrixTextScrollDirection;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightsSubsystem extends SubsystemBase {
  private static final USBPort USB_PORT = USBPort.kUSB1;
  private static final String MATRIX_ZONE = "matrix-32x8";

  private final ConnectorX device = new ConnectorX();
  private boolean connected = false;

  private double lastReconnectAttempt = 0.0;

  public LightsSubsystem() {
    connected = device.Connect(USB_PORT);
    lastReconnectAttempt = Timer.getFPGATimestamp();
  }

  @Override
  public void periodic() {
    maintainConnection();
    if (!connected) return;

    // apply();
  }

  public void apply() {
    device.leds
        .SetText("text")
        .ForZone(MATRIX_ZONE)
        .WithDirection(MatrixTextScrollDirection.Left)
        .RunOnce(false);
  }

  private void maintainConnection() {
    if (connected) return;

    double now = Timer.getFPGATimestamp();
    if (now - lastReconnectAttempt < 2.0) return;

    lastReconnectAttempt = now;
    connected = device.Connect(USB_PORT);
  }
}
