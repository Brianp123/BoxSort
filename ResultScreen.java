package com.example.brian.BoxSort;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultScreen extends AppCompatActivity {

    TextView result;
    Button rtnButton;
    String finalScore;
    int resultInt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        result = (TextView)findViewById(R.id.result);
        rtnButton = (Button)findViewById(R.id.returnButton);


        Intent mIntent = getIntent();
        resultInt = mIntent.getExtras().getInt("finalScore",0);

        if(resultInt >= 15)
        {
            result.setText("Great Job you win!");
        }
        else if(resultInt == -1)
        {
            result.setText("You lose! Dont hit the car");
        }
        else
            result.setText("You lose! Try again!");
    }
    public void playAgain(View v)
    {
        Intent backToGame = new Intent(ResultScreen.this, MainActivity.class);
        startActivity(backToGame);
    }
}
