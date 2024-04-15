package com.speed.brainchallenge;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;

public class SevenGameView extends SurfaceView implements  Runnable {
    //Create new Thread when the game started
    private Thread thread;
    private boolean isRunning;
    private int screenX,screenY;
    public static float RatioX,RatioY; //the size for the screen
    private Paint paint;
    private Background background1, background2;
    public SevenGameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        //Adaptive part: change the numbers to different size of the screen.
        RatioX = 1920f/ screenX;
        RatioY = 1080f / screenY;

        background1 = new Background(screenX,screenY,getResources());
        background2 = new Background(screenX,screenY,getResources());
        background2.x = screenX;

        Paint paint = new Paint();
    }
    //Resume() part aims to control the function using when the activity starts to run
    public void resume(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
        //When the thread started, the system run to the run() function
    }
    public void pause(){
        try {
            // join() part allows the pause function running after thread finishing the job
            isRunning = false;
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        //while will always running when the game running, ensure that the background image will keeping moving.
        while (isRunning){
            update();
            draw();
            sleep();
        }
    }
    private void update(){
        //moving the background every 10 pixel
        background1.x -= 10 * RatioX;
        background2.x -= 10 * RatioY;
        //calculating whether the background has moved out of the screen. the left pixel plus with the right width of the background is the judgment.
        if(background1.x + background1.background.getWidth() <0){
            background1.x= screenX;
        }
        if(background2.x + background2.background.getWidth() <0){
            background2.x= screenX;
        }
    }
    private void draw(){
        //canvas initializing
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);

            getHolder().unlockCanvasAndPost(canvas);

        }
    }
    private void sleep(){
        try{
            Thread.sleep(17); //1s/60 ~= 0.0166 ~=17 millis seconds, keep the background for 60 fps
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

}
