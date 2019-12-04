package com.example.b51_projectthucte_gamebubble;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    static int score=0;
   static  int kt=0;
    Random rd;
    TextView txtScore;
    ActionBar.LayoutParams params;
    LinearLayout layoutBubble;
    Button btnCreateBubble;
    ObjectAnimator objectAnimator;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();


    }

    private void addEvents() {
        btnCreateBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {

                        btnCreateBubble.setEnabled(false);
                        xulyTuDongChayHinh();

                    }
                }).run();
                }
            }
        );

    }
    private int flag = 1;
    private void ProcessAnim() {
        final ImageView img=getImageView();
        img.setBackground(getDrawable());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutBubble.removeView(v);
                if(flag==0)
                {
                    txtScore.setText("Điểm của bạn là "+(score+=1));
                }
                else if(flag==3){
                    txtScore.setText("Điểm của bạn là " + (score += 3));
                }
                else if(flag==5){
                    txtScore.setText("Điểm của bạn là " + (score += 5));
                }
                else if(flag==4){
                    txtScore.setText("Điểm của bạn là " + (score += 7));
                }
                else
                    txtScore.setText("Điểm của bạn là " + (score -= 5));


            }
        });
        objectAnimator= (ObjectAnimator) AnimatorInflater.loadAnimator(MainActivity.this,R.animator.bubbleanimation);
        objectAnimator.setDuration(rd.nextInt(1000)+2000);
        objectAnimator.setTarget(img);
        layoutBubble.addView(img,params);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                layoutBubble.removeView((View)
                        ((ObjectAnimator)animation).getTarget());

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();

    }

    private Drawable getDrawable() {
        Drawable draw;
        int i = rd.nextInt(9);
        switch (i) {
            case 0:
            case 1:
                flag = 0;
                draw = getResources().
                        getDrawable(R.drawable.a);
                break;
            case 2:
            case 3:
                flag = 3;
                draw = getResources().
                        getDrawable(R.drawable.b);
                break;
            case 4:
            case 5:
                flag =5;
                draw = getResources().
                        getDrawable(R.drawable.c);
                break;
            case 6:
            case 7:
                flag =4;
                draw = getResources().
                        getDrawable(R.drawable.d);
                break;
            default:
                flag =99;
                draw = getResources().
                        getDrawable(R.drawable.bom);
                break;
        }
        return draw;
    }

    private ImageView getImageView() {
        ImageView imageView=new ImageView(MainActivity.this);
        imageView.setX(rd.nextInt(500));
        return  imageView;
    }

    private void addControls() {
        txtScore= this.<TextView>findViewById(R.id.txtScore);
        rd=new Random();
        layoutBubble= this.<LinearLayout>findViewById(R.id.layoutBubble);
        params=new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        btnCreateBubble= this.<Button>findViewById(R.id.btnCreateBubble);

    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnuMauXanh)
            layoutBubble.setBackgroundColor(Color.BLUE);
        else if (item.getItemId()==R.id.mnuMauDo)
            layoutBubble.setBackgroundColor(Color.GREEN);
        else if(item.getItemId()==R.id.mnuMauVang)
            layoutBubble.setBackgroundColor(Color.YELLOW);

        return super.onOptionsItemSelected(item);
    }
    private void xulyTuDongChayHinh() {

        final Timer timer=new Timer();
         final TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {

                //update UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        kt+=1;
                        for (int i = 0; i <= rd.nextInt(5); i++) {
                            if(kt==11)
                            {//ket thuc game
                                kt=0;score=0;
                                timer.cancel();
                                btnCreateBubble.setEnabled(true);
                                break;
                            }
                                ProcessAnim();


                        }


                    }
                });
            }
        };

        //delay, mở lên bao lâu thì chạy.    0: là chạy ngay
        timer.schedule(timerTask,0,3000);



    }

}
