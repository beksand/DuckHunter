package com.beksand;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame{

    private static GameWindow gameWindow;
    private static long lastFrameTime;
    private static Image background;
    private static Image gameOwer;
    private static Image duck;
    private static float duckUp = 290;
    private static float duckTop = -100;
    private static float duckSpeed = 70;
    private static int score=0;

    public static void main(String[] args) throws IOException {
        background = ImageIO.read(GameWindow.class.getResourceAsStream("background.png"));
        gameOwer = ImageIO.read(GameWindow.class.getResourceAsStream("gameover.png"));
        duck = ImageIO.read(GameWindow.class.getResourceAsStream("duck.png"));
        gameWindow = new GameWindow();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setLocation(200,100);
        gameWindow.setSize(928, 580);
        gameWindow.setResizable(false);
        lastFrameTime = System.nanoTime();
        GameField gameField = new GameField();
        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();
                float duckDown = duckUp+duck.getHeight(null);
                float duckRight= duckTop+duck.getWidth(null);
                boolean isDuck = x <= duckRight && x>= duckTop && y <=duckDown && y >= duckUp;
                if (isDuck) {
                    duckTop=-100;
                    duckUp = (int) Math.random()*(gameField.getHeight()-duck.getHeight(null));
                    duckSpeed= duckSpeed+10;
                    score+=5;
                    gameWindow.setTitle("Score: "+score);
                }
            }
        });
        gameWindow.add(gameField);
        gameWindow.setVisible(true);
    }
    private static void onRepaint(Graphics graphics){
        long curentTime = System.nanoTime();
        float deltaTime = (curentTime-lastFrameTime)*0.000000001f;
        lastFrameTime = curentTime;
        duckTop = duckTop+duckSpeed*deltaTime;
//        duckUp = (int) (Math.random()*duckUp);
        graphics.drawImage(background, 0,0, null);
        graphics.drawImage(duck,(int)duckTop,(int)duckUp, null);
        if (duckTop>gameWindow.getWidth()){graphics.drawImage(gameOwer,235,180, null);}
    }
    private static class GameField extends JPanel{

        @Override
        protected void paintComponent(Graphics graphics){
            super.paintComponent(graphics);
            onRepaint(graphics);
            repaint();
        }
    }
}
