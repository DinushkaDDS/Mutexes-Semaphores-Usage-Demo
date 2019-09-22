public class Rider extends Thread{

    Bus bus;
    BusStop busStop;

    public Rider(BusStop busStop) {
        this.busStop = busStop;
        this.bus = null;
    }

    @Override
    public void run(){

        try {
            //Arrive to the bus stop and update the queue value
            this.arriveBusStop();

            boolean success = false;
            while(!success){
                Bus bus = this.busStop.getBus();
                this.busStop.busReadLock.acquire();
                this.bus = bus;
                this.busStop.busReadLock.release();
                success = this.enterBus(bus);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    void arriveBusStop() throws InterruptedException {
        this.busStop.getRider();
        //System.out.println(this.getId() + " rider arrived to the bus Stop.");
    }

    boolean enterBus(Bus bus) throws InterruptedException {
        if (bus == null){
            return false;
        }
        else{
            return bus.boardRider(this);
        }
    }

}
