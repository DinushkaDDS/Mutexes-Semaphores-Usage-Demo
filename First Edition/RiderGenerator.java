import java.util.Random;
import java.util.concurrent.Semaphore;

public class RiderGenerator extends Thread {

    private float meanArrivalTime;
    private Random random;
    private Semaphore busSlots, allAboard, bus, mutex;

    public RiderGenerator(Semaphore busSlots, Semaphore allAboard, Semaphore bus, Semaphore mutex, float meanArrivalTime) {
        this.busSlots = busSlots;
        this.allAboard = allAboard;
        this.bus = bus;
        this.mutex = mutex;
        this.meanArrivalTime = meanArrivalTime;
        this.random = new Random();
    }

    @Override
    public void run() {
        int riderId = 1;
        while (!Thread.currentThread().isInterrupted()){
            Rider newRider = new Rider(riderId, busSlots, allAboard, bus, mutex);
            newRider.start();
            riderId++;
            try {
                Thread.sleep(getNextRiderTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long getNextRiderTime() {
        float lambda = 1 / meanArrivalTime;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }
}
