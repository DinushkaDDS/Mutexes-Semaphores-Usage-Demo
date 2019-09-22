import java.util.concurrent.Semaphore;

public class Bus extends Thread{

    BusStop busStop;
    volatile int numOfAllowedRiders;
    volatile int currentCount;

    Semaphore countValChange;

    public Bus(BusStop busStop) throws InterruptedException {
        this.busStop = busStop;
        this.numOfAllowedRiders = 0;
        this.currentCount = 0;
        this.countValChange = new Semaphore(1);

    }


    @Override
    public void run(){

        try {
            //Arrive at bus station and get the current queue value to board riders
            this.arriveBusStop();

            this.countValChange.acquire();
            int val = currentCount;
            this.countValChange.release();

            while(currentCount != numOfAllowedRiders){
                this.countValChange.acquire();
                val = currentCount;
                this.countValChange.release();
            }

            this.leaveBusStop();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    void arriveBusStop() throws InterruptedException {
        this.busStop.setBus(this);
        numOfAllowedRiders = this.busStop.getQueueNum();
        System.out.println( this.getId() +  " Bus arrived at bus Stop. Allowed " + numOfAllowedRiders + " riders.");
    }


    boolean boardRider(Rider rider) throws InterruptedException {
        boolean out;
        countValChange.acquire();
        if(currentCount >= numOfAllowedRiders){
            out = false;
        }
        else{
            out = true;
            currentCount++;
            System.out.println(rider.getId() + " rider boarded to the " + this.getId() + "bus." );
        }
        countValChange.release();

        return out;

    }

    void leaveBusStop() throws InterruptedException {
        this.busStop.bus = null;
        System.out.println( this.getId() +  " Bus leaving bus Stop with " + this.currentCount + " riders.\n");
        this.busStop.busSetLock.release();
    }
}
