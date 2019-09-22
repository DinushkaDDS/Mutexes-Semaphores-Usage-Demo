import java.util.Random;
import java.util.concurrent.Semaphore;

public class BusGenerator extends Thread {

    private Random random;
    private Semaphore allAboard, bus, mutex;
    private float meanArrivalTime;

    public BusGenerator(Semaphore bus, Semaphore allAboard, Semaphore mutex, float meanArrivalTime) {
        this.bus = bus;
        this.allAboard = allAboard;
        this.mutex = mutex;
        this.meanArrivalTime = meanArrivalTime;
        this.random = new Random();
    }

    @Override
    public void run() {
        int busId = 1;
        while (!Thread.currentThread().isInterrupted()) {
            Bus newBus = new Bus(busId, bus, allAboard, mutex);
            newBus.start();
            busId++;
            try {
                Thread.sleep(getNextBusTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private long getNextBusTime() {
        float lambda = 1 / meanArrivalTime;
        return Math.round(-Math.log(1 - random.nextFloat()) / lambda);
    }
}
