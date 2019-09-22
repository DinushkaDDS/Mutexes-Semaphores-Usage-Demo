import java.util.Scanner;
import java.util.concurrent.*;

public class Simulator {


    public static void main(String[] args) throws InterruptedException {

        Simulator simulator = new Simulator();

        //For Convenience Rider mean time has converted to 30 seconds to 3 milliseconds
        Time_Generator timerRider = new Time_Generator(3);

        //For Convenience Bus mean time has converted to 1200 seconds to 120 milliseconds
        Time_Generator timerBus = new Time_Generator(120);
        BusStop busStop = new BusStop();

        String input1;
        String input2;

        System.out.println("The Senate Bus Problem \n");

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Input number of Riders : ");
            input2 = scanner.nextLine();  // Read user input
            System.out.print("Input number of Buses : ");
            input1 = scanner.nextLine();  // Read user input
        }
        System.out.println();

        //Change the numofBuses and numofRiders parameters as required
        simulator.executeThreads(busStop, Integer.parseInt(input1),
                Integer.parseInt(input2), timerBus, timerRider);

    }

    void executeThreads(BusStop busStop, int numofBuses, int numOfRiders, Time_Generator timerBus, Time_Generator timerRider) throws InterruptedException {

        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(numofBuses + numOfRiders);
        long time = 0;
        for (int i = 0; i < numOfRiders; i++) {
            time = time + (long) timerRider.getTimeOfArrival() + 1;
            threadPool.schedule(new Rider(busStop), time , TimeUnit.MILLISECONDS);
        }

        time = 0;
        for (int i = 0; i < numofBuses; i++) {
            time = time + (long) timerBus.getTimeOfArrival() + 1;
            threadPool.schedule(new Bus(busStop), time, TimeUnit.MILLISECONDS);
        }

        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return;


    }

}
