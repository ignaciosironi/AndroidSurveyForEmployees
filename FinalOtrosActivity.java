package com.example.nacho.encuestadepuntaje;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class FinalOtrosActivity extends AppCompatActivity {

    Timer timer;

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_otros);

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(FinalOtrosActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },60000);

        relativeLayout=(RelativeLayout) findViewById(R.id.LayoutFinalOtros);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer.cancel();
                startActivity(new Intent(FinalOtrosActivity.this,MainActivity.class));

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MainActivity.SHOULD_FINISH, true);
        startActivity(intent);

    }

}
