import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

//import static sun.jvm.hotspot.runtime.PerfMemory.start;

public class Server implements Runnable{

    public BlockingQueue<Task> task = new ArrayBlockingQueue<>(100);
    public int nrClients;
    public int totalService = 0;
    public AtomicInteger  waitingTime;
    public int queueNr;
    private boolean running;

    public int[] nrClientsVect ={0};

    // public int[] nrClientsQueue = {0};

    private Thread thread;

    public AtomicInteger getWaitingTime() {
        for(Task t: task){
            //System.out.println("service time" + t.getServiceTime());
            waitingTime.addAndGet(t.getServiceTime());
        }
        return waitingTime;
    }

    public void setWaitingTime(){
        waitingTime.decrementAndGet();
    }

    public void start(){
        running=true;
        thread.start();
    }

    public BlockingQueue<Task> getTask() {
        return task;
    }

    public Server(int queueNr) {
        this.queueNr = queueNr;
        nrClients = 0;
        waitingTime = new AtomicInteger(0);
        running = false;
        thread=new Thread();
        start();
    }

    public void addTask(Task newTask){

        nrClients++;
        totalService = totalService + newTask.getServiceT2();
//        if(!newTask.visited){
//            totalService += newTask.getServiceTime();;
//            newTask.visited = true;
//        }
        //waitingTime.addAndGet(newTask.getServiceTime());
//        System.out.println("  555" + newTask.getServiceTime());
//        System.out.println("5333335" + totalService);
        //totalService = totalService + newTask.getServiceTime();



        try{
            task.put(newTask);
        } catch (InterruptedException e){

            e.printStackTrace();
        }

    }

    public boolean emptyServer(){
        if(!task.isEmpty()) return false;
        else return true;
    }
    @Override
    public void run() {
        while (running || !task.isEmpty()){
            if(!task.isEmpty()) {
                Task currentCustomer;
                currentCustomer = task.peek();

                try {
                    Thread.sleep(currentCustomer.getServiceTime());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // task.remove(currentCustomer);
            }

            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
