package com.speed.brainchallenge;

import static com.speed.brainchallenge.SevenGameView.RatioX;
import static com.speed.brainchallenge.SevenGameView.RatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Enemy {
    public int enemySpeed = 20;
    public boolean isShooting = true;
    int x = 0, y, width, height,enemyCounter = 1;
    Bitmap enemy1,enemy2,enemy3,enemy4,enemy5;

    Enemy (Resources res){

        enemy1 = BitmapFactory.decodeResource(res,R.drawable.enemy1);
        enemy2 = BitmapFactory.decodeResource(res,R.drawable.enemy2);
        enemy3 = BitmapFactory.decodeResource(res,R.drawable.enemy3);
        enemy4 = BitmapFactory.decodeResource(res,R.drawable.enemy4);
        enemy5 = BitmapFactory.decodeResource(res,R.drawable.enemy5);

        width = enemy1.getWidth();
        height = enemy1.getHeight();

        width /= 6;
        height /= 6;

        width = (int) (width*RatioX);
        height = (int)(height*RatioY);

        enemy1 = Bitmap.createScaledBitmap(enemy1,width,height,false);
        enemy2 = Bitmap.createScaledBitmap(enemy2,width,height,false);
        enemy3 = Bitmap.createScaledBitmap(enemy3,width,height,false);
        enemy4 = Bitmap.createScaledBitmap(enemy4,width,height,false);
        enemy5 = Bitmap.createScaledBitmap(enemy5,width,height,false);
        //y = -height promise that the enemy started on out of the right of the screen
        y = -height;
    }
    Bitmap getEnemy(){
        if (enemyCounter == 1){
            enemyCounter++;
            return enemy1;
        }
        if (enemyCounter == 2){
            enemyCounter++;
            return enemy2;
        }
        if (enemyCounter == 3){
            enemyCounter++;
            return enemy3;
        }
        if (enemyCounter == 4){
            enemyCounter++;
            return enemy4;
        }
        enemyCounter = 1;
        return enemy5;
    }

    // the hit box of the enemy
    Rect getEnemyHitBoxShape(){
        //left up corner : (x,y); right down corner: (x+width,y+height)
        return new Rect(x,y,x+width,y+height);
    }

}
