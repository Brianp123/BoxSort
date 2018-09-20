package com.example.brian.BoxSort;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreen extends AppCompatActivity {

    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

    }
    public void startGame(View v)
    {
        Intent startGame = new Intent(StartScreen.this, MainActivity.class);
        startActivity(startGame);
    }
}
