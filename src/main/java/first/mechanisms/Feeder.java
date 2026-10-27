package first.mechanisms;

public class Feeder implements Mechanism {

    private final TalonFX motor = new TalonFX(5, new CANBus(CANPort.CAN_S0));

    public Feeder() {
        setDefaultCommand(idle());
    }

    private final SingleFlywheelSim sim = SingleFlywheelSim.forFeeder(motor);

    public void periodic() { // Update the simulation
        if (RobotBase.isSimulation()) {
            sim.periodic();
        }
    }

    public Command feed() {
        return run(coroutine -> {
            while(true) {
                motor.setThrottle(0.75);
                coroutine.yield();
            }
        });
    }

    public Command intake() {
        return run(coroutine -> {
            while(true) {
                motor.setThrottle(-1.0);
                coroutine.yield();
            }
        });
    }

    public Command outtake() {
        return run(coroutine -> {
            while(true) {
                motor.setThrottle(1.0);
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
