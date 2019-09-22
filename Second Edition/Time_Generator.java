import java.sql.Time;
import java.util.Random;

public class Time_Generator {

    Random rand;
    double meanTime;

    public Time_Generator(int mean){
        super();
        meanTime = mean;
        rand = new Random();
    }

    //Generating values in a exponential distribution
    double getTimeOfArrival() {
        double d = Math.log(1-rand.nextDouble())/(-1/meanTime);
        return  d;
    }

    /*public static void main(String[] args) {
        Time_Generator g = new Time_Generator(3);
        for (int i = 0; i <20; i++){
            System.out.println("" + g.getTimeOfArrival());
        }

    }*/

}
