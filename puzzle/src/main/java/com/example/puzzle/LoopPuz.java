package com.example.puzzle;

import java.util.Date;

public class LoopPuz implements Runnable {

    private final float FPS = 60;

    private final float SECOND = 1_000_000_000;

    private final float UPDATE_TIME = SECOND / FPS;

    private boolean running = false;

    Thread gameThread = null;

    //temp - проверка обновления цикла
    float updates = 0;
    float drawing = 0;
    long timer = 0;
    //temp

    @Override
    public void run() {

        float lastTime = System.nanoTime();
        float delta = 0;
        timer = System.currentTimeMillis();

        //Главный цикл игры. Обновляем экран 60 раз в секунду
        while (running) {

            float nowTime = System.nanoTime();
            float elapsedTime = nowTime - lastTime;
            lastTime = nowTime;
            delta += elapsedTime / UPDATE_TIME;

            if (delta >= 1) {
                updateGame();
                drawingGame();
                delta--;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                Date date = new Date();
                System.out.println("updates " + updates + "drawing " + drawing + " " + date.toString());
                updates = 0;
                drawing = 0;
                timer += 1000;
            }
        }

    }

    public void startGame() {
        if (running) {
            return;
        }
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame() {
        if (!running) {
            return;
        }
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateGame() {
        updates++;

    }

    private void drawingGame() {
        drawing++;
    }
}