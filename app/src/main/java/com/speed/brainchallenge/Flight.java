package com.speed.brainchallenge;

import static com.speed.brainchallenge.SevenGameView.RatioX;
import static com.speed.brainchallenge.SevenGameView.RatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Flight {
    public int isShooting = 0;
    boolean isGoingUp = false;
    int x,y,width,height,wingCounter=0,shootCounter = 1;
    Bitmap flight1,flight2,shoot1,shoot2,shoot3,shoot4,shoot5;
    private final SevenGameView sevenGameView;


    Flight (SevenGameView sevenGameView,int screenY, Resources res){

        this.sevenGameView = sevenGameView;

        flight1 = BitmapFactory.decodeResource(res,R.drawable.filghter1);
        flight2 = BitmapFactory.decodeResource(res,R.drawable.filghter2);
        shoot1 = BitmapFactory.decodeResource(res,R.drawable.shoot1);
        shoot2 = BitmapFactory.decodeResource(res,R.drawable.shoot2);
        shoot3 = BitmapFactory.decodeResource(res,R.drawable.shoot3);
        shoot4 = BitmapFactory.decodeResource(res,R.drawable.shoot4);
        shoot5 = BitmapFactory.decodeResource(res,R.drawable.shoot5);

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
        shoot1 = Bitmap.createScaledBitmap(shoot1,width,height,false);
        shoot2 = Bitmap.createScaledBitmap(shoot2,width,height,false);
        shoot3 = Bitmap.createScaledBitmap(shoot3,width,height,false);
        shoot4 = Bitmap.createScaledBitmap(shoot4,width,height,false);
        shoot5 = Bitmap.createScaledBitmap(shoot5,width,height,false);
        // y for making sure that the flight stay in the middle of the screen
        y = screenY / 2;
        //64 is the value for keeping flight always stay in the left of the screen
        x = (int)(64 * RatioX);
    }
    //getFlight() created for preforming the motivation of the flight
    Bitmap getFlight(){
        //Shooting part
        if (isShooting !=0){
            if(isShooting !=0){
                if (shootCounter == 1){
                    shootCounter++;
                    return shoot1;
                }
                if (shootCounter == 2){
                    shootCounter++;
                    return shoot2;
                }
                if (shootCounter == 3){
                    shootCounter++;
                    return shoot3;
                }
                if (shootCounter == 4){
                    shootCounter++;
                    return shoot4;
                }
                shootCounter = 1;
                isShooting--;
                sevenGameView.newBullet();

                return shoot5;
            }
        }
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
