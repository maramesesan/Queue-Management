public class Task {

    private int ID;
    private int arrivalTime;
    private int serviceTime;
    public int  currServTime;
    private boolean isFirst;
    public int serviceT2;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.currServTime = serviceTime;
        this.isFirst = false;
        this.serviceT2 = serviceTime;
    }

    public int getServiceT2() {
        return serviceT2;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getCurrServTime() {
        return currServTime;
    }

}
