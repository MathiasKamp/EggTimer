package com.Egg.EggTimer;


import static com.Egg.EggTimer.R.id.Btn_SoftBoiled;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {


    /**
     * variable declaration
     */
    public Button btnSoftBoiled, btnMediumBoiled, btnHardBoiled, btnStart;
    public TextView tvTimer;
    public String eggToBoil = "";
    boolean timerIsRunning = false;
    public int intervalSeconds;
    final Handler handler = new Handler();


    /**
     * onCreate method gets executed as the application is starting up
     *
     * in this method i set the StartButtons onClickListener and set the elements id from the designer
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTimer = findViewById(R.id.Tv_Timer);
        btnStart = findViewById(R.id.Btn_Start);
        btnSoftBoiled = findViewById(Btn_SoftBoiled);
        btnHardBoiled  = findViewById(R.id.Btn_HardBoiled);
        btnMediumBoiled = findViewById(R.id.Btn_MediumEgg);

        btnStart.setOnClickListener(view -> {

            if (timerIsRunning){
                onStop(view);
            }else {
                onButtonStart(view);
            }
        });

    }

    /**
     * this method is used to determine which egg button that has been pressed
     * @param view
     */
    public void onButtonEggSelected(View view){

        Boolean eggChosen;

        switch (view.getId()){

            case Btn_SoftBoiled:
                eggToBoil = "soft";
                setTimer(getSeconds(5));
                eggChosen = true;
                break;

            case R.id.Btn_MediumEgg:
                eggToBoil = "medium";
                setTimer(getSeconds(7));
                eggChosen = true;
            break;

            case R.id.Btn_HardBoiled:
                eggToBoil = "hard";
                setTimer(getSeconds(10));
                eggChosen = true;
                break;
            default:

                throw new RuntimeException("Unknown button id");
        }

        if (eggChosen){

            btnStart.setVisibility(View.VISIBLE);
        }

    }

    /**
     * this method is used to convert minutes to seconds
     * @param interval
     * @return
     */
    public int getSeconds(int interval)
    {
        intervalSeconds = interval * 60;

        return intervalSeconds;
    }

    /**
     * Method for setting the timer on the user interface
     *
     * this method is used to display the remaining cooking time of the egg
     * @param intervalSeconds
     */
    public void setTimer(long intervalSeconds){

        NumberFormat numberFormat = new DecimalFormat("00");

        long minutes = (intervalSeconds) / 60;

        long seconds = (intervalSeconds) % 60;

        tvTimer.setText(numberFormat.format(minutes)+":"+numberFormat.format(seconds));
    }

    /**
     *Method for enabling or disabling soft medium and hard buttons
     *
     * this method is used to enable or disable the soft, medium and hard buttons on the interface
     * @param shouldBeEnabled
     */
    public void setEggButtons(boolean shouldBeEnabled)
    {
        btnMediumBoiled.setEnabled(shouldBeEnabled);
        btnHardBoiled.setEnabled(shouldBeEnabled);
        btnSoftBoiled.setEnabled(shouldBeEnabled);
    }

    /**
     * Method to stop / reset the timer
     *
     * tihs method is used to stop / reset the timer and renables the soft, medium and hard buttons
     * @param view
     */
    public void onStop(View view)
    {
        //enable egg buttons
        setEggButtons(true);
        intervalSeconds = 0;
        tvTimer.setText("00:00");
        btnStart.setText("Start");
        timerIsRunning = false;
    }

    /**
     * method to start the timer
     *
     * this method is used to start the timer using a handler
     * when the timer is running the start button gets changed to a stop button
     * @param view
     */
    public void onButtonStart(final View view){

        // disable buttons
        setEggButtons(false);
        // start timer

        handler.post(new Runnable() {
            @Override
            public void run() {

                if (intervalSeconds > 0)
                {
                    intervalSeconds --;
                    handler.postDelayed(this,1000);
                    timerIsRunning = true;
                    setTimer(intervalSeconds);
                }
                if (timerIsRunning){
                    btnStart.setText("Stop");
                }

                else
                {
                    setEggButtons(true);
                    tvTimer.setText("00:00");
                    timerIsRunning = false;
                }
            }
        });
    }
}

