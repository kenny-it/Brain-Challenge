package com.speed.brainchallenge;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Flight {
    int x,y,width,height,wingCounter=0;
    Bitmap flight1,flight2;
    Flight (int screenY, Resources res){

        flight1 = BitmapFactory.decodeResource(res,R.drawable.filghter1);
        flight2 = BitmapFactory.decodeResource(res,R.drawable.filghter2);

        width = flight1.getWidth();
        height = flight1.getHeight();

        //scaling the picture to a quarter of its original size to make sure the pictures still suitable in other devices
        width /= 4;
        height /= 4;

        flight1 = Bitmap.createScaledBitmap(flight1,width,height,false);
        flight2 = Bitmap.createScaledBitmap(flight2,width,height,false);

        y = screenY / 2;

    }
    //getFlight() created for preforming the motivation of the flight
    Bitmap getFlight(){
        //At the beginning, if the wingCounter == 0, system return the flight1
        if (wingCounter == 0){
            wingCounter++;
            return flight1;
        }
        //Otherwise, return flight2
        wingCounter --;
        return flight2;
    }
}
