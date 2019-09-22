import java.util.concurrent.Semaphore;

public class Bus extends Thread {

    private int id;
    private Semaphore bus, allAboard, mutex;

    public Bus(int id, Semaphore bus, Semaphore allAboard, Semaphore mutex) {
        this.id = id;
        this.bus = bus;
        this.mutex = mutex;
        this.allAboard = allAboard;
    }


    @Override
    public void run() {
        try {
            mutex.acquire();
            arrive();
            if (Rider.riders > 0) {
                bus.release();
                allAboard.acquire();
            }
            mutex.release();
            depart();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void arrive(){
        System.out.println("\nBus " + id + " arrived!");
        System.out.println("Number of riders waiting: " + Rider.riders);
    }

    private void depart() {
        System.out.println("Bus " + id + " is leaving!\n");
    }
}
