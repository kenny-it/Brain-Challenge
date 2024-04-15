package com.speed.brainchallenge;

import static com.speed.brainchallenge.SevenGameView.RatioX;
import static com.speed.brainchallenge.SevenGameView.RatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bullet {

    int x,y;
    Bitmap bullet;
    Bullet (Resources res){

        bullet = BitmapFactory.decodeResource(res,R.drawable.bullet);

        int width = bullet.getWidth();
        int height = bullet.getHeight();

        width = width/4;
        height = height/4;

        width = (int) (width*RatioX);
        height = (int) (height*RatioY);

        bullet = Bitmap.createScaledBitmap(bullet,width,height,false);
    }
}
