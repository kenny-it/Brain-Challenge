package com.speed.brainchallenge;

import static com.speed.brainchallenge.SevenGameView.RatioX;
import static com.speed.brainchallenge.SevenGameView.RatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet {

    int x,y,width,height;
    Bitmap bullet;
    Bullet (Resources res){

        bullet = BitmapFactory.decodeResource(res,R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width = width/4;
        height = height/4;

        width = (int) (width*RatioX);
        height = (int) (height*RatioY);

        bullet = Bitmap.createScaledBitmap(bullet,width,height,false);
    }

    // Create the hit box for the Bullet
    Rect getBulletHitBoxShape(){
        //left up corner : (x,y); right down corner: (x+width,y+height)
        return new Rect(x,y,x+width,y+height);
    }

}
