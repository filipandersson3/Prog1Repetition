package BollSimulation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is a class
 * Created 2021-03-19
 *
 * @author Magnus Silverdal
 */
public class grafik extends Canvas implements Runnable{
    private int width = 800;
    private int height = 600;

    private Thread thread;
    int fps = 60;
    private boolean isRunning;

    private BufferStrategy bs;

    private int ballX, ballY;
    private double ballVX, ballVY, ballacc;
    private double deltaTime;
    private long lastTime;


    public grafik() {
        JFrame frame = new JFrame("A simple painting");
        this.setSize(width,height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        isRunning = false;

        ballX = 300;
        ballY = 0;
        ballVX = 1;
        ballVY = 0;
        ballacc = 982;

        lastTime = System.currentTimeMillis();

    }

    public void update() {
        long now = System.currentTimeMillis();
        deltaTime = (now-lastTime)/1000f;
        ballX += ballVX; //x pos update
        bounceWall();
        bounceGround();
        ballVY += ballacc*deltaTime; //speed update
        ballY += ballVY*deltaTime; //y pos update
        lastTime = now;

    }

    private void bounceGround() {
        if (ballY > height) {
            ballVY = -ballVY*0.7;
            ballY = height;
        }
    }

    private void bounceWall() {
        if (ballX > width || ballX < 0){
            ballVX = -ballVX;
        }
    }

    public void draw() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        update();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);
        drawBall(g, ballX, ballY);
        g.dispose();
        bs.show();
    }

    private void drawBall(Graphics g, int x, int y) {
        g.setColor(new Color(0xAA1111));
        g.fillRect(x-9,y-9,18,18);
    }

    public static void main(String[] args) {
        grafik painting = new grafik();
        painting.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double deltaT = 1000.0/fps;
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long now = System.currentTimeMillis();
            if (now-lastTime > deltaT) {
                update();
                draw();
                lastTime = now;
            }

        }
        stop();
    }
}