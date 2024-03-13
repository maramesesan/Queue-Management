import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private Model model;
    private View view;
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        view.makeQueueOp(new QueueOp());

    }

    class QueueOp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           // System.out.println("metge butonu");
           //view.getSimulation();


          model.makeQueuing(view.getSimulation(), view.getMaxArrival(), view.getMinArrival(),view.getMinService(), view.getMaxService(), view.getQ()/2, view.getN());

          view.setAvgService(String.valueOf(model.avgService));
          view.setPeakHour(String.valueOf(model.peakHourTime));
        }
    }
}
