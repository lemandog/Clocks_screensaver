package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.company.Main.*;

public class Main {
    public static int rSec;
    public static int rMin;
    public static int rHr;

    public static int sec;
    public static int min;
    public static int hr;
    public static Color MyCol = Color.red;

    public static void main(String[] args) {
        JFrame main = new JFrame("Clockwork. INFO - F1");
        main.setBackground(Color.BLACK);
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Clock clock = new Clock();
        main.setLayout(new CardLayout());
        main.setSize(500,500);
        main.add(clock);
        final boolean[] undecorated = {false};
        main.setVisible(true);

        main.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==112){infoScr();}
                if(e.getKeyCode()==67){MyCol = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));}
                if(e.getKeyCode()==70){
                    main.dispose();
                    undecorated[0] = !undecorated[0];

                    if(undecorated[0]){
                        main.setUndecorated(false);
                        main.setExtendedState(JFrame.NORMAL);
                        main.setSize(500,500);
                    }
                    else{
                        main.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        main.setUndecorated(true);
                    }
                    main.setVisible(true);
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        long timer = System.currentTimeMillis();
        while(true){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(System.currentTimeMillis() - timer > 1000){
                clock.repaint();
                timer = System.currentTimeMillis();
            }
        }
    }

    private static void infoScr() {
        JFrame info = new JFrame("INFO");
        info.setVisible(true);
        info.setLayout(new GridLayout(4,1));
        JLabel clockVER = new JLabel("VERSION 0.3");
        info.add(clockVER);
        JLabel clockAUT = new JLabel("author Lemandog & Aldegida");
        info.add(clockAUT);
        JLabel color = new JLabel("To set different color, press C");
        info.add(color);
        JLabel fullscr = new JLabel("To switch fullscreen mode, press F");
        info.add(fullscr);
        info.pack();
    }
}
class Clock extends JPanel{
    public Clock() {
        setBackground(Color.BLACK);
    }

    public void drawCircle(Graphics g, int height, int width) {
        int xCenterOfScreen = width/2;
        int yCenterOfScreen = height/2;
        int dx =30;

        double prevx = xCenterOfScreen + Math.cos(0)*height/2;
        double prevy = yCenterOfScreen + Math.sin(0)*height/2;

        double lenForEach=(2*Math.PI)/dx;

        for (int r = 1; r <= 12; r++) {
            double lenForEach1=(2*Math.PI)/12;
            int newX = (int)(xCenterOfScreen + Math.cos(r*lenForEach1)*height/2.2);
            int newY = (int)(yCenterOfScreen + Math.sin(r*lenForEach1)*height/2.2);
            int prevx1 =(int)(xCenterOfScreen + Math.cos(r*lenForEach1)*height/2);
            int prevy1 =(int)(yCenterOfScreen + Math.sin(r*lenForEach1)*height/2);
            g.drawLine(prevx1,prevy1,newX,newY);

        }

        for (int r = 0; r <= dx; r++) {
            int newX = (int)(xCenterOfScreen + Math.cos(r*lenForEach)*height/2);
            int newY = (int)(yCenterOfScreen + Math.sin(r*lenForEach)*height/2);
            g.drawLine((int) prevx,(int) prevy,newX,newY);
            prevx =newX;
            prevy =newY;
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setForeground(MyCol);

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));

        drawCircle(g,getHeight(),getWidth());

        Calendar mainClock = Calendar.getInstance();

        SimpleDateFormat hoursSdf = new SimpleDateFormat("HH");
        SimpleDateFormat minutesSdf = new SimpleDateFormat("mm");
        SimpleDateFormat secondsSdf = new SimpleDateFormat("ss");

        drawHour(g,hoursSdf.format(mainClock.getTime()),getHeight(),getWidth());
        drawMinutes(g,minutesSdf.format(mainClock.getTime()),getHeight(),getWidth());
        drawSeconds(g,secondsSdf.format(mainClock.getTime()),getHeight(),getWidth());
    }

    private void drawSeconds(Graphics g, String format, int height, int width) {
        sec = (Integer.parseInt(format));
        double angle =(((double)sec/60)*(2*Math.PI)) + 3*Math.PI/2;
        int xCenterOfScreen = width/2;
        int yCenterOfScreen = height/2;
        rSec = (int) (getHeight()/1.9);
        int newX = (int)(xCenterOfScreen + Math.cos(angle)*rSec);
        int newY = (int)(yCenterOfScreen + Math.sin(angle)*rSec);
        g.drawLine(xCenterOfScreen,yCenterOfScreen,newX,newY);
    }

    private void drawMinutes(Graphics g, String format, int height, int width) {
        min = (Integer.parseInt(format));
        double angle =(((double)min/60) + (double)sec/(60*60))*(2*Math.PI) + 3*Math.PI/2;
        int xCenterOfScreen = width/2;
        int yCenterOfScreen = height/2;
        rMin = (int) (getHeight()/2.1);
        int newX = (int)(xCenterOfScreen + Math.cos(angle)*rMin);
        int newY = (int)(yCenterOfScreen + Math.sin(angle)*rMin);
        g.drawLine(xCenterOfScreen,yCenterOfScreen,newX,newY);
    }

    private void drawHour(Graphics g, String format, int height, int width) {
        hr = (Integer.parseInt(format));
        if(hr>12){
            hr=hr-12;
        }
        double angle =(((double)hr/12 + (double)min/(60*12) + (double)sec/(60*60*12))*(2*Math.PI)) + 3*Math.PI/2;
        int xCenterOfScreen = width/2;
        int yCenterOfScreen = height/2;
        rHr = getHeight()/3;
        int newX = (int)(xCenterOfScreen + Math.cos(angle)*rHr);
        int newY = (int)(yCenterOfScreen + Math.sin(angle)*rHr);

        g.drawLine(xCenterOfScreen,yCenterOfScreen,newX,newY);
    }
}