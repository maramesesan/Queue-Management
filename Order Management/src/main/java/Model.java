import java.util.List;

public class Model {
    //View view = new View();
    private static Scheduler scheduler;
    private static RandomGenerate generatedTask;
    public static int peakHourTime = 0;
    public static float avgService = 0;

    public void makeQueuing(int tSimulation, int maxArrivalTime, int minArrivalTime, int minServiceTime, int maxServiceTime, int q, int n){
        SimulationManager simulationManager = new SimulationManager(tSimulation,maxArrivalTime,minArrivalTime,minServiceTime,maxServiceTime,q,n);
        scheduler = new Scheduler(q);
        scheduler.changeStrategy(SelectionPolicy.SHORTEST_TIME);
        List<Server> servers = scheduler.getServers();
        simulationManager.createServer();

        Thread simThread = new Thread(simulationManager);
        simThread.run();

        peakHourTime = simulationManager.peekHourTime;
        avgService = simulationManager.avgTime;
    }


}
