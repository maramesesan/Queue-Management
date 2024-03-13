import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers;
    private int maxNoServers;
    private int maxTaskPerServer;
    private int time;

    private Strategy strategy = new ConcreteStrategyTime();
    private Thread thread;
    private static List<Server> generatedServer = new ArrayList<>();

    public Scheduler(int maxNoServers){

        for(int i=0; i<maxNoServers; i++){
            Server newServer = new Server(i);
            generatedServer.add(newServer);
            thread= new Thread(newServer);
            thread.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy=new ConcreteStrategyTime();
        }
        if(policy==SelectionPolicy.SHORTEST_TIME){
            strategy=new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t){

        strategy.addTask(generatedServer,t);

    }

    public List<Server> getServers() {

        if (generatedServer != null){
            return generatedServer;
        }
        return null;
    }
}
