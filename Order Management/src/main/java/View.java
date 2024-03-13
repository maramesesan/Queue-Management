import javax.swing.*;
import java.awt.event.ActionListener;

public class View {

    public static JFrame frame = new JFrame("Calculator");
    public static JButton start;
    public static JTextField n;
    public static JTextField q;
    public static JTextField tSimulation;
    public static JTextField tArrivalMin;
    public static JTextField tArrivalMax;
    public static JTextField tServiceMin;
    public static JTextField tServiceMax;
    public static JTextField averageService;
    public static JTextField peekHour;
    public static JTextArea resultText;
    public static  JScrollPane scrollPane;
    public static JLabel tasksL;
    public static JLabel serverL;
    public static JLabel tSimL;
    public static JLabel tArrivL;
    public static JLabel tServL;
    public static JLabel avgServL;
    public static JLabel peakHourL;


    public View(){

        start = new JButton("START");
        start.setBounds(50,300,150,30);
        frame.add(start);

        tasksL = new JLabel("Number Tasks");
        tasksL.setBounds(300,100,200,30);
        frame.add(tasksL);


        n=new JTextField("");
        n.setBounds(400,100, 100,30);
        frame.add(n);

        serverL = new JLabel("Number Servers");
        serverL.setBounds(300,150,200,30);
        frame.add(serverL);

        q=new JTextField("");
        q.setBounds(400,150, 100,30);
        frame.add(q);

        tSimL = new JLabel("Time simulation");
        tSimL.setBounds(300,200,200,30);
        frame.add(tSimL);

        tSimulation=new JTextField("");
        tSimulation.setBounds(400,200, 100,30);
        frame.add(tSimulation);

        tArrivL = new JLabel("Min-Max Arrival Time");
        tArrivL.setBounds(270,250,200,30);
        frame.add(tArrivL);

        tArrivalMin=new JTextField("");
        tArrivalMin.setBounds(400,250, 50,30);
        frame.add(tArrivalMin);

        tArrivalMax=new JTextField("");
        tArrivalMax.setBounds(450,250, 50,30);
        frame.add(tArrivalMax);

        tServL = new JLabel("Min-Max Service Time");
        tServL.setBounds(270,300,200,30);
        frame.add(tServL);

        tServiceMin=new JTextField("");
        tServiceMin.setBounds(400,300, 50,30);
        frame.add(tServiceMin);

        tServiceMax=new JTextField("");
        tServiceMax.setBounds(450,300, 50,30);
        frame.add(tServiceMax);


        avgServL = new JLabel("Avg service time");
        avgServL.setBounds(300,450,200,30);
        frame.add(avgServL);

        averageService=new JTextField("");
        averageService.setBounds(400,450, 100,30);
        frame.add(averageService);

        peakHourL = new JLabel("Peak Hour");
        peakHourL.setBounds(300,500,200,30);
        frame.add(peakHourL);

        peekHour=new JTextField("");
        peekHour.setBounds(400,500, 100,30);
        frame.add(peekHour);

        resultText=new JTextArea("");
        resultText.setBounds(600,100,200,600);
        //frame.add(resultText);

        scrollPane=new JScrollPane(resultText);
        scrollPane.setBounds(600, 100, 200, 600);
        //scrollPane.setViewportView(resultText);
        frame.add(scrollPane);


        frame.setSize(1000,850);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setResult(String result){

        resultText.setText(result);
    }

    public String getResult(){
        return resultText.getText();
    }

    public int getN(){
        return Integer.parseInt(n.getText());
    }

    public int getQ(){
        return Integer.parseInt(q.getText());
    }

    public int getSimulation(){
        String text = tSimulation.getText();
       int integer =Integer.parseInt(text);
        System.out.println(Integer.parseInt(text));
        return integer;
    }
    public int getMaxArrival(){
        return Integer.parseInt(tArrivalMax.getText());

    }

    public int getMinArrival(){
        return Integer.parseInt(tArrivalMin.getText());
    }

    public int getMaxService(){
        return Integer.parseInt(tServiceMax.getText());
    }

    public int getMinService(){
        return Integer.parseInt(tServiceMin.getText());
    }
        public void makeQueueOp(ActionListener QueueOp) {
        start.addActionListener(QueueOp);
    }

    public void setAvgService(String nr){
            averageService.setText(nr);
    }

    public void setPeakHour(String nr){
        peekHour.setText(nr);
    }

}
