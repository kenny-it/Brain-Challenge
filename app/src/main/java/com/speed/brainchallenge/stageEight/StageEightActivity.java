package com.speed.brainchallenge.stageEight;

import static java.nio.file.Files.move;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.speed.brainchallenge.GameMenuActivity;
import com.speed.brainchallenge.R;

public class StageEightActivity extends AppCompatActivity {
    ImageButton ib00,ib01,ib02,ib10,ib11,ib12,ib20,ib21,ib22;
    private int[]image = {R.drawable.blackholezero,R.drawable.blackholeone,R.drawable.blackholetwo,
            R.drawable.blackholethree,R.drawable.blackholefour,R.drawable.blackholefive,
            R.drawable.blackholesix,R.drawable.blackholeseven,R.drawable.blackholeeight};
    private int[]imageIndex = new int[image.length];
    private int imageX = 3;
    private int imageY = 3;
    private int imgCount = imageX*imageY;
    private int blankSwap = imgCount-1;
    private int blankImgid = R.id.pt_ib_02x02;



    Button restartBtn;
    TextView timeTv;
    private Handler handler;
    int time = 0;


    @SuppressLint("HandlerLeak")
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_stage_eight);
        initView();
        handler = new Handler() {
            @Override
            public  void  handleMessage(Message msg){
                if (msg.what == 1){
                    time ++;
                    timeTv.setText("Time :" + time + "s");
                    handler.sendEmptyMessageDelayed(1,1000);
                }
            }
        };

    }

    private void initView() {
            ib00 = findViewById(R.id.pt_ib_00x00);
            ib01 = findViewById(R.id.pt_ib_00x01);
            ib02 = findViewById(R.id.pt_ib_00x02);
            ib10 = findViewById(R.id.pt_ib_01x00);
            ib11 = findViewById(R.id.pt_ib_01x01);
            ib12 = findViewById(R.id.pt_ib_01x02);
            ib20 = findViewById(R.id.pt_ib_02x00);
            ib21 = findViewById(R.id.pt_ib_02x01);
            ib22 = findViewById(R.id.pt_ib_02x02);
            timeTv = findViewById(R.id.pt_tv_time);
            restartBtn = findViewById(R.id.pt_btn_restart);

    }

    //Click for images
        public void onClick(View view) {
                int id = view.getId();
            if (id == R.id.pt_ib_00x00) {
                move(R.id.pt_ib_00x00, 0);
            } else if (id == R.id.pt_ib_00x01) {
                move(R.id.pt_ib_00x01, 1);
            } else if (id == R.id.pt_ib_00x02) {
                move(R.id.pt_ib_00x02, 2);
            } else if (id == R.id.pt_ib_01x00) {
                move(R.id.pt_ib_01x00, 3);
            } else if (id == R.id.pt_ib_01x01) {
                move(R.id.pt_ib_01x01, 4);
            } else if (id == R.id.pt_ib_01x02) {
                move(R.id.pt_ib_01x02, 5);
            } else if (id == R.id.pt_ib_02x00) {
                move(R.id.pt_ib_02x00, 6);
            } else if (id == R.id.pt_ib_02x01) {
                move(R.id.pt_ib_02x01, 7);
            } else if (id == R.id.pt_ib_02x02) {
                move(R.id.pt_ib_02x02, 8);
            }
        }

    private void move(int imagebuttonId, int site) {
            int sitex = site / imageX;
            int sitey = site % imageY;
            int blankx = blankSwap / imageX;
            int blanky = blankSwap % imageY;
            int x = Math.abs(sitex-blankx);
            int y = Math.abs(sitey-blanky);
            if ((x==0&&y==1)||(y==0&&x==1)){
                ImageButton clickButton = findViewById(imagebuttonId);
                clickButton.setVisibility(View.INVISIBLE);
                ImageButton blankButton = findViewById(blankImgid);
                blankButton.setImageResource(image[imageIndex[site]]);
                blankButton.setVisibility(View.VISIBLE);
                swap(site,blankSwap);
                blankSwap = site;
                blankImgid = imagebuttonId;
            }
            judgmentGameOver();


    }

    private void judgmentGameOver() {
        boolean loop = true;   //定义标志位loop
        for (int i = 0; i < imageIndex.length; i++) {
            if (imageIndex[i]!=i) {
                loop = false;
                break;
            }
        }

        if (loop) {
            handler.removeMessages(1);
            ib00.setClickable(false);
            ib01.setClickable(false);
            ib02.setClickable(false);
            ib10.setClickable(false);
            ib11.setClickable(false);
            ib12.setClickable(false);
            ib20.setClickable(false);
            ib21.setClickable(false);
            ib22.setClickable(false);
            ib22.setImageResource(image[8]);
            ib22.setVisibility(View.VISIBLE);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Congregation! you have been using"+time+"s").setPositiveButton("确认",null);
            AlertDialog dialog = builder.create();
            dialog.show();

        }


        }

    //Restart
        public void restart(View view) {
                restore();
                disruptRandom();
                handler.removeMessages(1);
                time = 0;
                timeTv.setText("Time: "+time+"s");
                handler.sendEmptyMessageDelayed(1,1000);
        }
    //Disrupt the maze

        public void disruptRandom(){
            for (int i = 0; i < imageIndex.length; i++) {
                imageIndex[i] = i;
                int rand1,rand2;
                for (int j = 0; j < 20; j++) {
                    rand1 = (int)(Math.random()*(imageIndex.length-1));
                    do {
                        rand2 = (int)(Math.random()*(imageIndex.length-1));
                        if (rand1!=rand2) {
                            break;
                        }
                    }while (true);
                    swap(rand1,rand2);
                }
                ib00.setImageResource(image[imageIndex[0]]);
                ib01.setImageResource(image[imageIndex[1]]);
                ib02.setImageResource(image[imageIndex[2]]);
                ib10.setImageResource(image[imageIndex[3]]);
                ib11.setImageResource(image[imageIndex[4]]);
                ib12.setImageResource(image[imageIndex[5]]);
                ib20.setImageResource(image[imageIndex[6]]);
                ib21.setImageResource(image[imageIndex[7]]);
                ib22.setImageResource(image[imageIndex[8]]);
            }

        }

        private void swap(int rand1,int rand2){
                int temp = imageIndex[rand1];
                imageIndex[rand1] = imageIndex[rand2];
                imageIndex[rand2] = temp;
        }

        private void restore(){
                ib00.setClickable(true);
                ib01.setClickable(true);
                ib02.setClickable(true);
                ib10.setClickable(true);
                ib11.setClickable(true);
                ib12.setClickable(true);
                ib20.setClickable(true);
                ib21.setClickable(true);
                ib22.setClickable(true);
                ImageButton clickBtn = findViewById(blankImgid);
                clickBtn.setVisibility(View.VISIBLE);
                ImageButton blankBtn = findViewById(R.id.pt_ib_02x02);
                blankBtn.setVisibility(View.INVISIBLE);
                blankImgid = R.id.pt_ib_02x02;
                blankSwap = imgCount - 1;
            }

    }
