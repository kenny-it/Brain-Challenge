package com.speed.brainchallenge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SevenGameView extends SurfaceView implements  Runnable {
    //Create new Thread when the game started
    private Thread thread;
    private boolean isRunning, isGameOver = false;
    private int screenX,screenY,score = 0;
    public static float RatioX,RatioY; //the size for the screen
    public Random random;
    private SharedPreferences prefs;
    private Paint paint;
    //Flight()
    private Flight flight;
    private List<Bullet> bullets;
    //Enemy
    private Enemy[] enemies;
    private SevenGameActivity activity;
    private Background background1, background2;




    public SevenGameView(SevenGameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;
        //record the score.
        prefs = activity.getSharedPreferences("sevenGameScore",Context.MODE_PRIVATE);

        Log.d("screenX", String.valueOf(screenX));
        this.screenX = screenX;
        Log.d("screenY", String.valueOf(screenY));
        this.screenY = screenY;
        //Adaptive part: change the numbers to different size of the screen.
        RatioX = 1920f/ screenX;
        RatioY = 1080f / screenY;

        background1 = new Background(screenX,screenY,getResources());
        background2 = new Background(screenX,screenY,getResources());
        flight = new Flight(this,screenY,getResources());
        bullets = new ArrayList<>();
        enemies = new Enemy[4];  // Totally 4 enemies on the screen
        background2.x = screenX;

        Paint paint = new Paint();



        //Enemies started
        for (int i = 0; i < 4; i++){
            Enemy enemy = new Enemy(getResources());
            enemies[i] = enemy;
        }
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



    //controller of the flight
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //the judgment for checking whether users' touch is on the left of the screen
                if (event.getX()<screenX/2){
                    flight.isGoingUp = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                flight.isGoingUp = false;
                //the judgment for checking whether users' touch is on the right of the screen
                if(event.getX()>screenX/2){
                    flight.isShooting++;
                }
                break;
        }
        return true;
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


    //Update function controlling the motivations of all the function including background, enemy and character etc.
    private void update(){
        //Background
        //moving the background every 10 pixel
        background1.x -= 10;  //* RatioX;
        background2.x -= 10;  //* RatioY;
        //calculating whether the background has moved out of the screen. the left pixel plus with the right width of the background is the judgment.
        if(background1.x + background1.background.getWidth() <0){
            background1.x= screenX;
        }
        if(background2.x + background2.background.getWidth() <0){
            background2.x= screenX;
        }



        //calculating the height of the flight
        if(flight.isGoingUp)
            flight.y -= (int) (30*RatioY);
        else
            flight.y += (int) (30*RatioY);
        if (flight.y <0)
            flight.y = 0;

        if(flight.y > screenY - flight.height)
            flight.y = screenY - flight.height;



        //Bullet
        List <Bullet> trash = new ArrayList<>();
        for (Bullet bullet : bullets){
            //judgment of bullet when it fly out of the screen
            if (bullet.x>screenX)
                trash.add(bullet);

            bullet.x += (int) (50 * RatioX);
            //Judgment hit box
            for (Enemy enemy : enemies){
                if (Rect.intersects(enemy.getEnemyHitBoxShape(),bullet.getBulletHitBoxShape())){
                    //The enemies will out of the screen, and user get one point
                    score++;
                    enemy.x = -500;
                    bullet.x = screenX+500;
                    //The enemies will disappear when bullet hit them
                    enemy.isShooting = true;
                }
            }

        }

        for (Bullet bullet : trash)
            bullets.remove(bullet);


        //Enemies
        for (Enemy enemy : enemies){
            enemy.x -= enemy.enemySpeed;
            //judgment that whether the Enemy stay out of the left screen. When the enemies run out of the screen, it will reset the X locations and speed up.
            if (enemy.x + enemy.width <0){
                //judgment that if enemies were not be shoot and still out of the screen.

                //if (!enemy.isShooting){
                    //isGameOver = true;
                    //return;
               // }

                int max = (int) (30*RatioX); // Considering of Adaptive on each screen, the max number of the random speed use Ratio for the consideration.
                Log.d("max", String.valueOf(max));
                Random random = new Random();
                enemy.enemySpeed = random.nextInt(max);// Set up the area of random values.
                // the minimum speed of the enemy will be 10 pixels
                if (enemy.enemySpeed < 10 * RatioX){
                    enemy.enemySpeed = (int) (10 * RatioX);
                }
                enemy.x = screenX; // put the enemy to the right side.
                enemy.y = random.nextInt(screenY - enemy.height);

                enemy.isShooting = false;
            }
            // Making a hit boxes for both flight and enemies
            if (Rect.intersects(enemy.getEnemyHitBoxShape(),flight.getFlightHitBoxShape())){
                isGameOver = true;
                return;
            }
        }

    }



    private void draw(){
        //canvas initializing
        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            //Background 1
            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            //Background 2
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);

            for (Enemy enemy : enemies){
                canvas.drawBitmap(enemy.getEnemy(),enemy.x,enemy.y,paint);
            }
            //Score
            //Score
            Paint paintForScore = new Paint();
            paintForScore.setTextSize(128);
            paintForScore.setColor(Color.WHITE);
            canvas.drawText(score+"",screenX / 2f,164,paintForScore);

            //GameOver
            if (isGameOver){
                isRunning = false;
                canvas.drawBitmap(flight.getDead(),flight.x,flight.y,paint);
                saveIfHighScore();
                waitBeforeExiting();
                getHolder().unlockCanvasAndPost(canvas);
                return;
            }
            //Enemy
            for (Enemy enemy : enemies){
                canvas.drawBitmap(enemy.getEnemy(),enemy.x,enemy.y,paint);
            }

            //Flight
            canvas.drawBitmap(flight.getFlight(),flight.x,flight.y,paint);

            //Bullet
            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);

            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    private void waitBeforeExiting() {
        try{
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity,StageActivitySeven.class));
            activity.finish();
        }catch (InterruptedException e){
            e.printStackTrace();}
    }

    // Change the score if users get higher score.
    private void saveIfHighScore() {
        if (prefs.getInt("highscore",0)<score){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore",score);
            editor.apply();
        }
    }

    private void sleep(){
        try{
            Thread.sleep(17); //1s/60 ~= 0.0166 ~=17 millis seconds, keep the background for 60 fps
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    public void newBullet() {
        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height/2);
        bullets.add(bullet);
    }
}
