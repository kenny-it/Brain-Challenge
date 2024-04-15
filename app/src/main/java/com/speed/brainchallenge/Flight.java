package com.speed.brainchallenge;

import static com.speed.brainchallenge.SevenGameView.RatioX;
import static com.speed.brainchallenge.SevenGameView.RatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Flight {
    boolean isGoingUp = false;
    int x,y,width,height,wingCounter=0;
    Bitmap flight1,flight2;
    Flight (int screenY, Resources res){

        flight1 = BitmapFactory.decodeResource(res,R.drawable.filghter1);
        flight2 = BitmapFactory.decodeResource(res,R.drawable.filghter2);

        width = flight1.getWidth();
        //Log.d("width", String.valueOf(width));
        height = flight1.getHeight();
       // Log.d("height", String.valueOf(height));

        //scaling the picture to a quarter of its original size to make sure the pictures still suitable in other devices
        width /= 4;
        //Log.d("width", String.valueOf(width));
        height /= 4;
        //Log.d("Ratio", String.valueOf(RatioX));
        width = (int) (width * RatioX);
        //Log.d("width", String.valueOf(width));
        height *= (int) RatioY;
        //Log.d("height", String.valueOf(height));

        flight1 = Bitmap.createScaledBitmap(flight1,width,height,false);
        flight2 = Bitmap.createScaledBitmap(flight2,width,height,false);
        // y for making sure that the flight stay in the middle of the screen
        y = screenY / 2;
        //64 is the value for keeping flight always stay in the left of the screen
        x = (int)(64 * RatioX);
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
