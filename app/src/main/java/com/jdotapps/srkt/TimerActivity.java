package com.jdotapps.srkt;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;


public class TimerActivity extends ActionBarActivity {

    private TextView mTimerView;
    private CountDownTimer timer;
    private Button mStartButton;
    private Button mStopButton;
    private Button mResetButton;
    private long timerStart = 15000;
    private Chronometer stopWatch;
    private long timeWhenStopped = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        stopWatch = (Chronometer) findViewById(R.id.chronometer);
        mTimerView = (TextView) findViewById(R.id.minutesview);
        mStartButton = (Button) findViewById(R.id.btnStart);
        mStopButton = (Button) findViewById(R.id.btnStop);
        mResetButton = (Button) findViewById(R.id.btnReset);

       // mTimerView.setText((timerStart/1000)- (timeWhenStopped/1000) + "");

        final Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTimerView.setText((timerStart/1000) + (timeWhenStopped/1000) + "");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };



        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timeWhenStopped == 0){
                    stopWatch.start();
                    t.start();
                }else {
                    stopWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    stopWatch.start();
                    t.start();
                }

            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeWhenStopped = stopWatch.getBase() - SystemClock.elapsedRealtime();
                stopWatch.stop();
         //       Toast.makeText(TimerActivity.this,timeWhenStopped + "",Toast.LENGTH_SHORT).show();

            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopWatch.setBase(SystemClock.elapsedRealtime());
                stopWatch.stop();
                timeWhenStopped = 0;

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Toast.makeText(TimerActivity.this, timeWhenStopped / 1000 + "", Toast.LENGTH_SHORT).show();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
