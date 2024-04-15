package com.speed.brainchallenge;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background {
    int x=0, y=0;
    Bitmap background; //for processing motion picture
    Background (int screenX, int screenY, Resources res){
        //import the background and decoded it as Bitmap resource background
        background = BitmapFactory.decodeResource(res,R.drawable.blackhole);
        background = Bitmap.createScaledBitmap(background,screenX,screenY,false);
        //ScreenX: the width after background scaling, ScreenY: the height after background scaling.

    }
}
