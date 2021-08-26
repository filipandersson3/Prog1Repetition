package BollSimulation;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
	    //int bally = 5;
        //double ballacc = 9.82;
        double time = 0;
        double lasttime = 0;
        while(true) {
            time = System.currentTimeMillis()-lasttime;
            //double ballv = ballacc*time;
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lasttime = time;
            System.out.println(time);
        }
    }
}
