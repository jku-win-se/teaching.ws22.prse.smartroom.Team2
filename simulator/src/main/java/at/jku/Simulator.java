package at.jku;

public class Simulator extends APIClient {

    public static void main(String[] args) {
        // TODO implement Simulator

        Simulator simulator = new Simulator();

        simulator.print(simulator.postRoom("SimulatorTest1"));
        simulator.print(simulator.postRoom("SimulatorTest2"));
        simulator.print(simulator.postRoom("SimulatorTest3"));
        simulator.print(simulator.getRooms());
        simulator.print(simulator.putRoom(2, "UpdateTest2", 30));
        simulator.print(simulator.getRooms());

    }
}