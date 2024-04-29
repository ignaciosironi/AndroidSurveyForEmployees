package com.example.nacho.encuestadepuntaje;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class FinalActivity extends AppCompatActivity {

    Timer timer;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        timer=new Timer();
        timer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               Intent intent = new Intent(FinalActivity.this, MainActivity.class);
                               startActivity(intent);
                               finish();
                           }
                       },60000);

        relativeLayout=(RelativeLayout) findViewById(R.id.LayoutFinal);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                timer.cancel();
                startActivity(new Intent(FinalActivity.this,MainActivity.class));

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

