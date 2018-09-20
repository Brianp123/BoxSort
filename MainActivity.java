package com.example.brian.BoxSort;

        import android.content.Intent;
        import android.content.res.TypedArray;
        import android.graphics.Color;
        import android.graphics.Point;
        import android.graphics.Rect;
        import android.media.MediaPlayer;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Display;
        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import java.util.Random;
        import java.util.Timer;
        import java.util.TimerTask;
        import android.os.CountDownTimer;

public class MainActivity extends AppCompatActivity {

    Timer timer = new Timer();
    ImageView belt;
    ImageView truck1,truck2,truck3;
    ImageView box1,box2,box3,box4;
    ImageView car;
    int box1X,box1Y,box2X,box2Y,box3X,box3Y,box4X,box4Y;
    int box1speedX = 4;
    int box1speedY = 0;
    int box2speedX = 6;
    int box2speedY = 0;
    int box3speedX = 7;
    int box3speedY = 0;
    int box4speedX = 8;
    int box4speedY = 0;
    int carSpeedX = 6;
    int carX;
    int carY;
    int score = 0;
    final int backOfScreen = 1000;
    int screenX,screenY;
    Rect rect1,rect2,rect3,rect4;
    Rect truckRect1,truckRect2,truckRect3;
    Rect carRect;
    TextView text,text2;
    boolean box1Clicked,box2Clicked,box3Clicked,box4Clicked;
    boolean box1Visible,box2Visible,box3Visible,box4Visible = true;
    boolean complete = false;
    boolean carMovable = false;
    float rawX,rawY,rawX1,rawY1,rawX2,rawY2,rawX3,rawY3;
    int truck1X,truck1Y,truck2X,truck2Y,truck3X,truck3Y;
    int resID1,resID2,resID3,resID4 = 0;
    MediaPlayer sound;



    Integer THIRTEEN = new Integer(13);
    Integer SIX = new Integer(6);
    Integer THREE = new Integer(3);
    Integer SIXTEEN = new Integer(16);
    Integer SEVEN = new Integer(7);
    Integer TWELVE = new Integer(12);
    Integer SEVENTEEN = new Integer(17);
    Integer ZERO = new Integer(0);
    Integer FIVE = new Integer(5);
    Integer TEN = new Integer(10);
    Integer TWENTY = new Integer(20);

    int newBox1 = 0;
    int newBox2 = 0;
    int newBox3 = 0;
    int newBox4 = 0;



    CountDownTimer clock = new CountDownTimer(90000, 1000)
    {
        @Override
        public void onTick(long l)
        {
            text2.setText("Time:"+Long.toString(l/1000));
            if(l < 20000)
            {
                carMovable = true;
            }
            if(l < 10000)
            {
                text2.setTextColor(Color.RED);
            }


        }

        @Override
        public void onFinish()
        {
            text2.setText("Game Over");
            text2.setTextColor(Color.RED);
            complete = true;
        }
    }.start();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound = MediaPlayer.create(this, R.raw.ding);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        belt = (ImageView) this.findViewById(R.id.belt);
        box1 =(ImageView) this.findViewById(R.id.box1);
        box2 =(ImageView) this.findViewById(R.id.box2);
        box3 =(ImageView) this.findViewById(R.id.box3);
        box4 =(ImageView) this.findViewById(R.id.box4);
        truck1 =(ImageView)this.findViewById(R.id.truck1);
        truck2 = (ImageView)this.findViewById(R.id.truck2);
        truck3 = (ImageView)this.findViewById(R.id.truck3);
        car = (ImageView)this.findViewById(R.id.car);
        text = (TextView)this.findViewById(R.id.textView);
        text2= (TextView)this.findViewById(R.id.textView2);


        final int FPS = 40;


        TimerTask updateGame = new UpdateGameTask();
        timer.scheduleAtFixedRate(updateGame,0,1000/FPS);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenX = size.x;
        screenY = size.y;
        car.setVisibility(View.INVISIBLE);







        //samsung S5 Y corrd should be 1275 emulator at 1227 s8 1555
        box1X = screenX - belt.getWidth()-backOfScreen;
        box1Y = 1555;

        box2X = screenX - belt.getWidth()-backOfScreen;
        box2Y = 1555;

        box3X = screenX - belt.getWidth()-backOfScreen;
        box3Y = 1555;

        box4X = screenX - belt.getWidth()-backOfScreen;
        box4Y = 1555;

        carX = screenX - belt.getWidth()+ backOfScreen;

        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);
        final Random rand = new Random();
        final int rndNum1 = rand.nextInt(boxes.length());
         resID1 = boxes.getResourceId(rndNum1,0);
        final int rndNum2 = rand.nextInt(boxes.length());
          resID2 = boxes.getResourceId(rndNum2,0);
        final int rndNum3 = rand.nextInt(boxes.length());
          resID3 = boxes.getResourceId(rndNum3,0);
        final int rndNum4 = rand.nextInt(boxes.length());
          resID4 = boxes.getResourceId(rndNum4,0);


        box1.setImageResource(resID1);
        box2.setImageResource(resID2);
        box3.setImageResource(resID3);
        box4.setImageResource(resID4);

        box1.setTag(resID1);
        box2.setTag(resID2);
        box3.setTag(resID3);
        box4.setTag(resID4);

        newBox1 = (Integer)box1.getTag();
        newBox2 = (Integer)box2.getTag();
        newBox3 = (Integer)box3.getTag();
        newBox4 = (Integer)box4.getTag();




        box1Visible = true;
        box2Visible = true;
        box3Visible = true;
        box4Visible = true;


    }
    class UpdateGameTask extends TimerTask
    {

        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);

        @Override
        public void run()
        {

            MainActivity.this.runOnUiThread(new Runnable()
            {


                @Override
                public void run()
                {

                    text.setText("Score:"+Integer.toString(score));

                    if(score >= 15)
                    {
                        text.setTextColor(Color.GREEN);
                    }
                    else
                        text.setTextColor(Color.RED);

                    if(complete == true)
                    {
                        timer.cancel();
                        timer.purge();
                        Intent goToResult = new Intent(MainActivity.this, ResultScreen.class);
                        goToResult.putExtra("finalScore", score);
                        startActivity(goToResult);
                    }
                    if(carMovable == true)
                    {
                        car.setVisibility(View.VISIBLE);
                        carX -= carSpeedX;
                        car.setX(carX);

                    }



                    box1X += box1speedX;
                    box1Y -=  box1speedY;
                    box1.setX(box1X);
                    box1.setY(box1Y);


                    box2X += box2speedX;
                    box2Y -=  box2speedY;
                    box2.setX(box2X);
                    box2.setY(box2Y);

                    box3X += box3speedX;
                    box3Y -=  box3speedY;
                    box3.setX(box3X);
                    box3.setY(box3Y);

                    box4X += box4speedX;
                    box4Y -=  box4speedY;
                    box4.setX(box4X);
                    box4.setY(box4Y);

                    truck1X = Math.round(truck1.getX());
                    truck1Y = Math.round(truck1.getY());
                    truck2X = Math.round(truck2.getX());
                    truck2Y = Math.round(truck2.getY());
                    truck3X = Math.round(truck3.getX());
                    truck3Y = Math.round(truck3.getY());
                    carY = Math.round(car.getY());



                    rect1 = new Rect(box1X,box1Y,box1X + box1.getWidth(),box1Y + box1.getHeight());
                    rect2 = new Rect(box2X,box2Y,box2X + box2.getWidth(),box2Y + box2.getHeight());
                    rect3 = new Rect(box3X,box3Y,box3X + box3.getWidth(),box3Y + box3.getHeight());
                    rect4 = new Rect(box4X,box4Y,box4X + box4.getWidth(),box4Y + box4.getHeight());
                    carRect = new Rect(carX,carY,carX + car.getWidth(),carY + car.getHeight());

                    truckRect1 = new Rect(truck1X,truck1Y,truck1X + truck1.getWidth(),truck1Y + truck1.getHeight());
                    truckRect2 = new Rect(truck2X,truck2Y,truck2X + truck2.getWidth(),truck2Y + truck2.getHeight());
                    truckRect3 = new Rect(truck3X,truck3Y,truck3X + truck3.getWidth(),truck3Y + truck3.getHeight());

                    if(Rect.intersects(carRect,rect1) && box1Visible)
                    {
                        timer.cancel();
                        timer.purge();
                        score = -1;
                        Intent goToResult = new Intent(MainActivity.this, ResultScreen.class);
                        goToResult.putExtra("finalScore", score);
                        startActivity(goToResult);
                    }
                    else if(Rect.intersects(carRect,rect2) && box2Visible)
                    {
                        timer.cancel();
                        timer.purge();
                        score = -1;
                        Intent goToResult = new Intent(MainActivity.this, ResultScreen.class);
                        goToResult.putExtra("finalScore", score);
                        startActivity(goToResult);
                    }
                    else if(Rect.intersects(carRect,rect3) && box3Visible)
                    {
                        timer.cancel();
                        timer.purge();
                        score = -1;
                        Intent goToResult = new Intent(MainActivity.this, ResultScreen.class);
                        goToResult.putExtra("finalScore", score);
                        startActivity(goToResult);
                    }
                    else if(Rect.intersects(carRect,rect4) && box4Visible)
                    {
                        timer.cancel();
                        timer.purge();
                        score = -1;
                        Intent goToResult = new Intent(MainActivity.this, ResultScreen.class);
                        goToResult.putExtra("finalScore", score);
                        startActivity(goToResult);
                    }

                    if(Rect.intersects(rect1,truckRect1)&& box1Visible)
                    {
                        if(newBox1 == boxes.getResourceId(THREE,0) ||newBox1 == boxes.getResourceId(SIX,0) ||
                                newBox1 == boxes.getResourceId(THIRTEEN,0) || newBox1 == boxes.getResourceId(SIXTEEN,0))
                        {
                            sound.start();
                            score++;
                            box1.setVisibility(View.INVISIBLE);
                            box1Visible = false;
                        }
                        else
                            box1.setVisibility(View.INVISIBLE);
                    }
                    else if(Rect.intersects(rect2,truckRect1)&& box2Visible)
                    {
                        if(newBox2 == boxes.getResourceId(THREE,0) ||newBox2 == boxes.getResourceId(SIX,0) ||
                                newBox2 == boxes.getResourceId(THIRTEEN,0) || newBox2 == boxes.getResourceId(SIXTEEN,0))
                        {
                            sound.start();
                            score++;
                            box2.setVisibility(View.INVISIBLE);
                            box2Visible = false;
                        }
                        else
                            box2.setVisibility(View.INVISIBLE);
                    }
                    else if(Rect.intersects(rect3,truckRect1)&& box3Visible)
                    {
                        if(newBox3 == boxes.getResourceId(THREE,0) ||newBox3 == boxes.getResourceId(SIX,0) ||
                                newBox3 == boxes.getResourceId(THIRTEEN,0) || newBox3 == boxes.getResourceId(SIXTEEN,0))
                        {
                            sound.start();
                            score++;
                            box3.setVisibility(View.INVISIBLE);
                            box3Visible = false;
                        }
                        else
                            box3.setVisibility(View.INVISIBLE);

                    }
                    else if(Rect.intersects(rect4,truckRect1)&& box4Visible)
                    {
                        if(newBox4 == boxes.getResourceId(THREE,0) ||newBox4 == boxes.getResourceId(SIX,0) ||
                                newBox4 == boxes.getResourceId(THIRTEEN,0) || newBox4 == boxes.getResourceId(SIXTEEN,0))
                        {
                            sound.start();
                            score++;
                            box4.setVisibility(View.INVISIBLE);
                            box4Visible = false;
                        }
                        else
                            box4.setVisibility(View.INVISIBLE);
                    }
                    else if(Rect.intersects(rect1,truckRect2)&& box1Visible)
                    {

                        if(newBox1 == boxes.getResourceId(SEVEN,0) ||newBox1 == boxes.getResourceId(TWELVE,0) ||
                                newBox1 == boxes.getResourceId(SEVENTEEN,0))
                        {
                            sound.start();
                            score++;
                            box1.setVisibility(View.INVISIBLE);
                            box1Visible = false;
                        }
                        else
                            box1.setVisibility(View.INVISIBLE);
                    }
                    else if(Rect.intersects(rect2,truckRect2)&& box2Visible)
                    {
                        if(newBox2 == boxes.getResourceId(SEVEN,0) ||newBox2 == boxes.getResourceId(TWELVE,0) ||
                                newBox2 == boxes.getResourceId(SEVENTEEN,0))
                        {
                            sound.start();
                            score++;
                            box2.setVisibility(View.INVISIBLE);
                            box2Visible = false;
                        }
                        else
                            box2.setVisibility(View.INVISIBLE);
                    }
                    else if(Rect.intersects(rect3,truckRect2)&& box3Visible)
                    {
                        if(newBox3 == boxes.getResourceId(SEVEN,0) ||newBox3 == boxes.getResourceId(TWELVE,0) ||
                                newBox3 == boxes.getResourceId(SEVENTEEN,0))
                        {
                            sound.start();
                            score++;
                            box3.setVisibility(View.INVISIBLE);
                            box3Visible = false;
                        }
                        else
                            box3.setVisibility(View.INVISIBLE);
                    }
                    else if(Rect.intersects(rect4,truckRect2)&& box4Visible)
                    {
                        if(newBox4 == boxes.getResourceId(SEVEN,0) ||newBox4 == boxes.getResourceId(TWELVE,0) ||
                                newBox4 == boxes.getResourceId(SEVENTEEN,0))
                        {
                            sound.start();
                            score++;
                            box4.setVisibility(View.INVISIBLE);
                            box4Visible = false;
                        }
                        else
                            box4.setVisibility(View.INVISIBLE);
                    }
                    else if(Rect.intersects(rect1,truckRect3)&& box1Visible)
                    {
                        if(newBox1 == boxes.getResourceId(ZERO,0) ||newBox1 == boxes.getResourceId(FIVE,0) ||
                                newBox1 == boxes.getResourceId(TEN,0) || newBox1 == boxes.getResourceId(TWENTY,0))
                        {
                            sound.start();
                            score++;
                            box1.setVisibility(View.INVISIBLE);
                            box1Visible = false;
                        }
                        else
                            box1.setVisibility(View.INVISIBLE);
                    }
                    else if(Rect.intersects(rect2,truckRect3)&& box2Visible)
                    {
                        if(newBox2 == boxes.getResourceId(ZERO,0) ||newBox2 == boxes.getResourceId(FIVE,0) ||
                                newBox2 == boxes.getResourceId(TEN,0) || newBox2 == boxes.getResourceId(TWENTY,0))
                        {
                            sound.start();
                            score++;
                            box2.setVisibility(View.INVISIBLE);
                            box2Visible = false;
                        }
                        else
                            box2.setVisibility(View.INVISIBLE);

                    }
                    else if(Rect.intersects(rect3,truckRect3)&& box3Visible)
                    {
                        if(newBox3 == boxes.getResourceId(ZERO,0) ||newBox3 == boxes.getResourceId(FIVE,0) ||
                                newBox3 == boxes.getResourceId(TEN,0) || newBox3 == boxes.getResourceId(TWENTY,0))
                        {
                            sound.start();
                            score++;
                            box3.setVisibility(View.INVISIBLE);
                            box3Visible = false;
                        }
                        else
                            box3.setVisibility(View.INVISIBLE);
                    }
                    else if(Rect.intersects(rect4,truckRect3)&& box4Visible)
                    {
                        if(newBox4 == boxes.getResourceId(ZERO,0) ||newBox4 == boxes.getResourceId(FIVE,0) ||
                                newBox4 == boxes.getResourceId(TEN,0) || newBox4 == boxes.getResourceId(TWENTY,0))
                        {
                            sound.start();
                            score++;
                            box4.setVisibility(View.INVISIBLE);
                            box4Visible = false;
                        }
                        else
                            box4.setVisibility(View.INVISIBLE);
                    }

                    if (box1X > screenX) {
                        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);
                        final Random rand = new Random();
                        final int rndNum1 = rand.nextInt(boxes.length());
                        newBox1 = boxes.getResourceId(THREE,0);


                        box1.setImageResource(newBox1);

                        box1X = screenX - belt.getWidth()- backOfScreen;


                    }
                    if (box1Y > screenY || box1Y < 0) {
                        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);
                        final Random rand = new Random();
                        final int rndNum1 = rand.nextInt(boxes.length());
                        newBox1 = boxes.getResourceId(rndNum1,0);


                        box1.setImageResource(newBox1);
                        box1.setVisibility(View.VISIBLE);
                        box1Visible = true;
                        box1speedX = 8;
                        box1speedY = 0;
                        box1.setRotation(0);
                        box1X = screenX - belt.getWidth()- backOfScreen;
                        box1Y = 1555;


                    }

                    if (box2X > screenX) {
                        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);
                        final Random rand = new Random();
                        final int rndNum2 = rand.nextInt(boxes.length());
                        newBox2 = boxes.getResourceId(rndNum2,0);

                        box2.setImageResource(newBox2);

                        box2X = screenX - belt.getWidth()- backOfScreen;


                    }
                    if (box2Y > screenY || box2Y < 0) {
                        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);
                        final Random rand = new Random();
                        final int rndNum2 = rand.nextInt(boxes.length());
                        newBox2 = boxes.getResourceId(rndNum2,0);


                        box2.setImageResource(newBox2);
                        box2.setVisibility(View.VISIBLE);
                        box2Visible = true;
                        box2speedX = 8;
                        box2speedY = 0;
                        box2.setRotation(0);
                        box2X = screenX - belt.getWidth()- backOfScreen;
                        box2Y = 1555;


                    }


                    if (box3X > screenX) {
                        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);
                        final Random rand = new Random();
                        final int rndNum3 = rand.nextInt(boxes.length());
                        newBox3 = boxes.getResourceId(rndNum3,0);

                        box3.setImageResource(newBox3);

                        box3X = screenX - belt.getWidth()-1000;
                    }
                    if (box3Y > screenY || box3Y < 0) {
                        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);
                        final Random rand = new Random();
                        final int rndNum3 = rand.nextInt(boxes.length());
                        newBox3 = boxes.getResourceId(rndNum3,0);


                        box3.setImageResource(newBox3);
                        box3.setVisibility(View.VISIBLE);
                        box3Visible = true;
                        box3speedX = 8;
                        box3speedY = 0;
                        box3.setRotation(0);
                        box3X = screenX - belt.getWidth()- backOfScreen;
                        box3Y = 1555;


                    }
                    if (box4X > screenX) {
                        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);
                        final Random rand = new Random();
                        final int rndNum4 = rand.nextInt(boxes.length());
                        newBox4 = boxes.getResourceId(rndNum4,0);

                        box4.setImageResource(newBox4);

                        box4X = screenX - belt.getWidth()-1000;
                    }
                    if (box4Y > screenY || box4Y < 0) {
                        final TypedArray boxes = getResources().obtainTypedArray(R.array.boxes);
                        final Random rand = new Random();
                        final int rndNum4 = rand.nextInt(boxes.length());
                        newBox4 = boxes.getResourceId(rndNum4,0);


                        box4.setImageResource(newBox4);
                        box4.setVisibility(View.VISIBLE);
                        box4Visible = true;
                        box4speedX = 8;
                        box4speedY = 0;
                        box4.setRotation(0);
                        box4X = screenX - belt.getWidth()- backOfScreen;
                        box4Y = 1555;


                    }
                    if (carX < 0) {

                        carX = screenX - belt.getWidth() + backOfScreen;

                    }


                }
            });


        }

    }
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {

                rawX = event.getRawX() -(box1.getWidth() /2);
                rawY = (event.getRawY() - 108) - (box1.getHeight()/2);

                rawX1 = event.getRawX() -(box2.getWidth() /2);
                rawY1 = (event.getRawY() - 108) - (box2.getHeight()/2);

                rawX2 = event.getRawX() -(box3.getWidth() /2);
                rawY2 = (event.getRawY() - 108) - (box3.getHeight()/2);

                rawX3 = event.getRawX() -(box4.getWidth() /2);
                rawY3 = (event.getRawY() - 108) - (box4.getHeight()/2);

                if(rawX > box1.getX()
                        && rawX < (box1.getX() + box1.getLeft())
                        && rawY > box1.getY()
                        && rawY < (box1.getY() + box1.getTop()))
                {
                    box1Clicked = true;
                }
                else if(rawX1 > box2.getX()
                        && rawX1 < (box2.getX() + box2.getLeft())
                        && rawY1 > box2.getY()
                        && rawY1 < (box2.getY() + box2.getTop()))
                {
                    box2Clicked = true;
                }
                else if(rawX > box3.getX()
                        && rawX2 < (box3.getX() + box3.getLeft())
                        && rawY2 > box3.getY()
                        && rawY2 < (box3.getY() + box3.getTop()))
                {
                    box3Clicked = true;
                }
                if(rawX > box4.getX()
                        && rawX3 < (box4.getX() + box4.getLeft())
                        && rawY3 > box4.getY()
                        && rawY3 < (box4.getY() + box4.getTop()))
                {
                    box4Clicked = true;
                }


            }
            break;
            case MotionEvent.ACTION_UP:
            {
                if(box1Clicked)
                {
                    box1.setRotation(90);
                    box1speedX = 0;
                    box1speedY = 10;
                    box1Clicked = false;
                }
                else if(box2Clicked)
                {
                    box2.setRotation(90);
                    box2speedX = 0;
                    box2speedY = 10;
                    box2Clicked = false;
                }
                else if(box3Clicked)
                {
                    box3.setRotation(90);
                    box3speedX = 0;
                    box3speedY = 10;
                    box3Clicked = false;
                }
                else if(box4Clicked)
                {
                    box4.setRotation(90);
                    box4speedX = 0;
                    box4speedY = 10;
                    box4Clicked = false;
                }


            }
            break;
        }

        return true;
    }
}