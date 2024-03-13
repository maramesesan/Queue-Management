import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;

import static java.lang.Math.abs;

public class SimulationManager implements Runnable {

    private StringBuilder result = new StringBuilder();

    public AtomicInteger timeLimit = new AtomicInteger(0);
    public AtomicInteger maxArivTime = new AtomicInteger(0);
    public AtomicInteger minArivTime = new AtomicInteger(0);
    public AtomicInteger minServTime = new AtomicInteger(0);
    public AtomicInteger maxServTime = new AtomicInteger(0);
    public AtomicInteger nrServers = new AtomicInteger(0);
    public AtomicInteger nrClients = new AtomicInteger(0);
    private AtomicInteger currentTime = new AtomicInteger(1);
    private static Thread thread;
    private static Scheduler scheduler;
    private static RandomGenerate generatedTask;
    private static View view = new View();
    private static List<Server> generatedServer = new ArrayList<>();
    private int[] nrTaskInServer = new int[]{0};
    public int peekHourTime = 0;
    private int peekHourTask = 0;
    public float avgTime = 0;

    public SimulationManager(int timeLimit, int maxArivTime, int minArivTime, int minServTime, int maxServTime, int nrServers, int nrClients) {
        this.timeLimit.set(timeLimit);
        this.maxArivTime.set(maxArivTime);
        this.minArivTime.set(minArivTime);
        this.minServTime.set(minServTime);
        this.maxServTime.set(maxServTime);
        this.nrServers.set(nrServers);
        this.nrClients.set(nrClients);
    }

    public AtomicInteger getNrServers() {
        return nrServers;
    }

    public void createServer() {
        for (int i = 0; i < this.nrServers.get(); i++) {
            Server newServer = new Server(i);
            generatedServer.add(newServer);
        }

        this.currentTime.set(1);
        thread = new Thread();
        thread.start();
    }


    public void avlServer(Scheduler sc) {

        int i = 0;
        List<Server> servers = sc.getServers();

        for (Server s : servers) {
            i++;
            System.out.println("Queue:" + i + ": ");
            view.setResult(view.getResult() + "Queue:" + i + ": " + "\n");
            if (s.task.isEmpty()) {
                view.setResult(view.getResult() + "closed" + "\n");
                System.out.println("closed");

            } else {
                for (Task t : s.getTask()) {
                    System.out.println("(" + t.getID() + " " + t.getArrivalTime() + " " + t.getServiceTime() + ")");
                    view.setResult(view.getResult() + "(" + t.getID() + " " + t.getArrivalTime() + " " + t.getServiceTime() + ")" + "\n");
                }
                System.out.println("\n");
                view.setResult(view.getResult() + "\n");
            }


        }
    }

    private boolean emptyServer() {
        for (Server s : generatedServer) {
            if (s.getTask().isEmpty()) return false;
        }
        return true;
    }

    private void averageServiceTime() {
        //float avgTime = 0;
        int serversUsed = nrServers.get();
        List<Server> servers = scheduler.getServers();
        for (Server s : servers) {
            // int totalService = s.totalService;
            if (s.nrClients != 0) {
                System.out.println(s.nrClients + " " + s.totalService);
                avgTime = (float) (s.totalService / s.nrClients);
            } else serversUsed--;

        }
        if (serversUsed != 0) {
            avgTime = abs(avgTime) / serversUsed;
        }
        System.out.println("avg time" + avgTime);

    }


    private void peekHour(int currentTime) {

        int totalClients = 0;
        List<Server> servers = scheduler.getServers();
        for (Server s : servers) {
            for (int i = 0; i < nrServers.get(); i++) {
                totalClients = totalClients + s.nrClients;
                if (peekHourTask < s.nrClients) {
                    peekHourTask = s.nrClients;
                    peekHourTime = currentTime;
                }
            }

        }
        System.out.println("clients ant peeak hour" + peekHourTask + " " + peekHourTime);
    }

    @Override
    public void run() {

        scheduler = new Scheduler(nrServers.get());
        generatedTask = new RandomGenerate(nrClients.get(), maxArivTime.get(), minArivTime.get(), minServTime.get(), maxServTime.get());

        FileWriter writeInFile;

        try {
            writeInFile = new FileWriter("Tests.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (currentTime.get() <= timeLimit.get()) {
            System.out.println("Time:" + currentTime);

            try {
                writeInFile.write("Time " + currentTime);
                result.append("Time " + currentTime);

                writeInFile.write("\n");
                result.append("\n");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            Task removeTask;
            BlockingQueue<Task> auxServ = generatedTask.getGeneratedTask();

            for (Iterator<Task> t = auxServ.iterator(); t.hasNext(); ) {
                Task newTask = t.next();
                if (newTask.getArrivalTime() == currentTime.get()) {
                    // totalServiceTime = totalServiceTime + newTask.getServiceTime();
                    scheduler.dispatchTask(newTask);
                    peekHour(currentTime.get());
                    // nrTaskInServer[]
                    t.remove();
                    removeTask = newTask;
                    auxServ.remove(removeTask);
                }
            }
            List<Server> servers = scheduler.getServers();

            int nrServer = 0;
            for (Server s : servers) {
                Task first = s.task.peek();
                if (first != null) {
                    if (first.isFirst() == false) {

                        first.currServTime = currentTime.get();
                        first.setFirst(true);
                    }
                    System.out.println("time" + first.getID() + " " + (first.getCurrServTime() + first.getServiceTime()));
                    if (first.getCurrServTime() + first.getServiceTime() + 1 == currentTime.get()) {
                        s.task.remove(first);
                    }
                }
            }

            for (Iterator<Task> t = auxServ.iterator(); t.hasNext(); ) {
                Task newTask = t.next();
                System.out.println(newTask + "   ");
            }

            for (Server s : scheduler.getServers()) {
                if (!s.emptyServer())
                    s.setWaitingTime();
            }


            if (scheduler.getServers() == null) System.out.println("empty");
            else {
                avlServer(scheduler);
                int i = 0;
                for (Server s : servers) {
                    i++;
                    try {
                        writeInFile.write("Queue:" + i + ": ");
                        result.append("Queue:" + i + ": ");
                        writeInFile.write("\n");
                        result.append("\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if (s.task.isEmpty()) {
                        try {
                            writeInFile.write("closed");
                            result.append("closed");
                            writeInFile.write("\n");
                            result.append("\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else {
                        for (Task t : s.getTask()) {
                            try {
                                writeInFile.write("(" + t.getID() + " " + t.getArrivalTime() + " " + t.getServiceTime() + ")");
                                result.append("(" + t.getID() + " " + t.getArrivalTime() + " " + t.getServiceTime() + ")");

                                writeInFile.write("\n");
                                result.append("\n");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        try {
                            writeInFile.write("\n");
                            result.append("\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            currentTime.getAndIncrement();

            for (Server s : servers) {
                System.out.println("for server" + s.queueNr + " clients " + s.nrClients + " " + s.totalService);
            }
        }
        averageServiceTime();
        System.out.println("peek hour " + peekHourTime);

        try {

            writeInFile.write("Average service time " + avgTime + "\n");
            writeInFile.write("Peak hour " + peekHourTime + "\n");

            result.append("Average service time " + avgTime + "\n");
            result.append("Peak hour " + peekHourTime + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            writeInFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}



