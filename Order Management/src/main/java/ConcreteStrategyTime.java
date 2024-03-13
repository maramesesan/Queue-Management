import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteStrategyTime implements Strategy {
    public int totalTime = 0;

    @Override
    public void addTask(List<Server> servers, Task t) {

        AtomicInteger auxWaiting = new AtomicInteger(100000);
        Server auxServer = null;

        for(Server s: servers){

            int waiting = s.getWaitingTime().get()/2;
            // System.out.println("getWitingTime" + s.getWaitingTime().get() + " " + "getServiceTime " + t.getServiceTime());
            // System.out.println(s.queueNr);
            //System.out.println("daaa " + s.queueNr + " " + waiting);
            if(auxWaiting.get() > waiting) {
                auxWaiting.set(waiting);
                auxServer = s;
            }
        }

        //auxServer.setWaitingTime();
        System.out.println("asteptare minima " + auxWaiting.get());
        auxWaiting.set(0);

        if(auxServer!=null){
            auxServer.addTask(t);
            //totalTime += t.getServiceTime();
//            if(!t.visited){
//                auxServer.totalService = auxServer.totalService + t.getServiceTime();
//                t.visited = true;
//            }

            System.out.println("the total service" + auxServer.totalService);
            System.out.println("nr clients " + auxServer.queueNr + " " + auxServer.nrClients);
        }

    }
}
