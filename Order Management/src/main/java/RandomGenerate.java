import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RandomGenerate {

    private static BlockingQueue<Task> generatedTask = new LinkedBlockingQueue<>();
    static Random random = new Random();

    public static BlockingQueue<Task> getGeneratedTask() {
        return generatedTask;
    }

    public RandomGenerate(int nrClients, int maxArivTime, int minArivTime, int minServTime, int maxServTime ){
        int clients = nrClients;
        for(int i=1; i<=clients; i++) {
            int tArrival = random.nextInt(maxArivTime-minArivTime+1)+minArivTime;
            int tService = random.nextInt(maxServTime-minServTime+1)+minServTime;

            Task newTask = new Task(i,tArrival,tService);
            generatedTask.add(newTask);
            System.out.println("( " + i + " " + tArrival + " " + tService + ")");
        }
    }

    public boolean emptyTask(){
        if(generatedTask.isEmpty()) return false;
        return true;
    }
}
