package com.vincentvibe3.fencingscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int player1_score;
    private int player2_score;
    private int timer_seconds;
    private boolean timer_running;
    private int timer_radio_id;
    private int max_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            player1_score = savedInstanceState.getInt("player1_score");
            player2_score = savedInstanceState.getInt("player2_score");
            TextView player1_text = findViewById(R.id.player1score);
            player1_text.setText(String.valueOf(player1_score));
            TextView player2_text = findViewById(R.id.player2score);
            player2_text.setText(String.valueOf(player2_score));
        }
        if (player1_score == 0){
            Button remove1 = findViewById(R.id.player1minus);
            remove1.setEnabled(false);
            Button remove2 = findViewById(R.id.player2minus);
            remove2.setEnabled(false);
        }
        set_defaults();
        run_timer();
    }

    public void set_defaults(){
        player1_score = player2_score = 0;
        timer_seconds = 180;
        timer_running = false;
        timer_radio_id = 3;
        max_score = 15;
        RadioButton default_timer_radio = findViewById(R.id.timer_radio3);
        default_timer_radio.setChecked(true);
        RadioButton default_score_radio = findViewById(R.id.score_radio3);
        default_score_radio.setChecked(true);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("player1_score", player1_score);
        savedInstanceState.putInt("player2_score", player2_score);
    }

    public void player1_add(View view){
        TextView player1_text = findViewById(R.id.player1score);
        if (player1_score < max_score){
            ++player1_score;
            player1_text.setText(String.valueOf(player1_score));
        }
        if (player1_score > 0){
            Button remove1 = findViewById(R.id.player1minus);
            remove1.setEnabled(true);
        }
    }

    public void player2_add(View view){
        TextView player2_text = findViewById(R.id.player2score);
        if (player2_score < max_score){
            ++player2_score;
            player2_text.setText(String.valueOf(player2_score));
        }
        if (player2_score > 0){
            Button remove2 = findViewById(R.id.player2minus);
            remove2.setEnabled(true);
        }
    }

    public void player1_remove(View view){
        TextView player1_text = findViewById(R.id.player1score);
        if (player1_score > 0) {
            --player1_score;
            player1_text.setText(String.valueOf(player1_score));
        }

        Button remove1 = findViewById(R.id.player1minus);
        remove1.setEnabled(player1_score > 0);
    }

    public void player2_remove(View view){
        TextView player2_text = findViewById(R.id.player2score);
        if (player2_score > 0) {
            --player2_score;
            player2_text.setText(String.valueOf(player2_score));
        }
        Button remove2 = findViewById(R.id.player2minus);
        remove2.setEnabled(player2_score > 0);
    }

    public void  double_points(View view){
        player1_add(view);
        player2_add(view);
    }

    public void reset_points(View view){
        player1_score = 0;
        player2_score = 0;
        TextView player1_text = findViewById(R.id.player1score);
        TextView player2_text = findViewById(R.id.player2score);
        player1_text.setText(String.valueOf(player1_score));
        player2_text.setText(String.valueOf(player2_score));
    }

    public void redcard1(View view){
        player2_add(view);
        Intent intent = new Intent(this, redcard.class);
        startActivity(intent);
    }

    public void redcard2(View view){
        player1_add(view);
        Intent intent = new Intent(this, redcard.class);
        startActivity(intent);
    }

    public void yellowcard(View view){
        Intent intent = new Intent(this, yellowcard.class);
        startActivity(intent);
    }

    public void blackcard(View view){
        Intent intent = new Intent(this, blackcard.class);
        startActivity(intent);
    }

    public void start_timer(View view){
        timer_running = true ;
    }

    public void pause_timer(View view){
        timer_running = false;
    }

    public void reset_timer(View view){
        timer_running = false;
        switch (timer_radio_id) {
            case 1:
                timer_seconds = 60;
                break;
            case 2:
                timer_seconds = 120;
                break;
            case 3:
                timer_seconds = 180;
                break;
        }
    }

    private void run_timer(){
        TextView timer = findViewById(R.id.timer);
        final Handler timer_handler = new Handler();
        timer_handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (timer_seconds % 3600) / 60;
                int seconds = timer_seconds % 60;

                String formatted_time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

                timer.setText(formatted_time);

                if (timer_running && timer_seconds > 0){
                    --timer_seconds;
                } else if (timer_seconds<=0) {
                    timer_running = false;
                }

                timer_handler.postDelayed(this,1000);
            }
        });
    }

    public void timer_radio_check(View view){
        boolean timer_checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.timer_radio1:
                if (timer_checked){
                    timer_seconds = 60;
                    timer_radio_id = 1;
                }
                break;
            case R.id.timer_radio2:
                if (timer_checked){
                    timer_seconds = 120;
                    timer_radio_id = 2;
                }
                break;
            case R.id.timer_radio3:
                if (timer_checked){
                    timer_seconds = 180;
                    timer_radio_id = 3;
                }
                break;
        }
    }

    public void score_radio_check(View view){
        System.out.println("Radio chosen");
        boolean score_checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.score_radio1:
                if (score_checked){
                    max_score = 5;
                }
                break;
            case R.id.score_radio2:
                if (score_checked){
                    max_score = 10;
                }
                break;
            case R.id.score_radio3:
                if (score_checked){
                    max_score = 15;
                }
                break;
        }
    }

}