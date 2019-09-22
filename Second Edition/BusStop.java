import java.util.concurrent.Semaphore;

public class BusStop {


    volatile int queue;
    Bus bus;
    Semaphore queueLock;
    Semaphore busReadLock;
    Semaphore busSetLock;

    public BusStop() throws InterruptedException {
        this.queue = 0;
        this.bus = null;
        queueLock = new Semaphore(1);
        busReadLock = new Semaphore(1);
        busSetLock = new Semaphore(1);
    }

    int getQueueNum() throws InterruptedException {
        int out = 0;
        queueLock.acquire();
        if(queue > 50) {
            out = 50;
            queue =  queue - 50;
        }
        else{
            out = queue;
            queue = 0;
        }
        queueLock.release();
        return out;
    }

    void getRider() throws InterruptedException {
        queueLock.acquire();
        queue++;
        queueLock.release();
    }

    void setBus(Bus bus) throws InterruptedException {
        busSetLock.acquire();
        this.bus = bus;
    }

    Bus getBus() throws InterruptedException {
        Bus out;
        busReadLock.acquire();
        if(this.bus == null){
            out = null;
        }
        else{
            out = this.bus;
        }
        busReadLock.release();
        return out;
    }

}
