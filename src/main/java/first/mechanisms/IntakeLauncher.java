package first.mechanisms;

import org.wpilib.framework.RobotBase;
import static org.wpilib.units.Units.Seconds;

public class intakeLauncher implements Mechanism {

    private final TalonFX motor = new TalonFX(4, new CANBus(CANPort_S0));

    public intakeLauncher() {
        setDefaultCommand(idle());
    }

    private final SingleFlywheelSim sim = SingleFlywheelSim.forIntakeLauncher(motor);

    public void periodic() {
        if (RobotBase.isSimulation()) {
            sim.periodic();
        }
    }

    public Command shoot() {
        return run(coroutine -> {
            coroutine.await(Seconds.of(2));
            while(true) {
                motor.setThrottle(0.9);
                coroutine.yield();
            }
        });
    }

    public Command intake() {
        return run(coroutine -> {
            while(true) {
                motor.setThrottle(0.8);
                coroutine.yield();
            }
        });
    }

    public Command outtake() {
        return run(coroutine -> {
            while(true) {
                motor.setThrottle(-0.8);
                coroutine.yield();
            }
        });
    }

    public Command idle() {
        return run(coroutine -> {
            while(true) {
                motor.setThrottle(0.0);
                coroutine.yield();
            }
        });
    }
    
}
