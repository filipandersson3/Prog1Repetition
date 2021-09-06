package BollSimulation;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        double ballx = 0;
	    double bally = 5;
        double ballv = 0;
        double ballvx = 0;
        double ballacc = -9.82;
        double deltaTime = 0.1;
        for (double time = 0; time < 2; time+=deltaTime) {
            if (ballx > 3) {
                ballvx = -ballvx;
            }
            ballv += ballacc*deltaTime;
            bally += ballv*deltaTime;
            if (bally < 0) {
                ballv = -ballv*0.9;
                bally = ballv*deltaTime;
                ballvx = ballv*Math.sin(Math.PI/6);
            }
            ballx += ballvx*deltaTime;
            System.out.println(ballx + " ");
        }
    }
}
