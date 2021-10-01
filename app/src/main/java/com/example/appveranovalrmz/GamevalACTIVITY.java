package com.example.appveranovalrmz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.appveranovalrmz.beans.Grid;
import com.example.appveranovalrmz.beans.OnSwipeTouchListener;
import com.example.appveranovalrmz.beans.Square;


public class GamevalACTIVITY extends AppCompatActivity implements GestureDetector.OnGestureListener {
    public static final String PREF_NAME ="Preferences";
    public static final  int SWIPE_TRESHOLD=100;
    public static final  int VELOCITY_TRESHOLD=100;

    Grid grid;
    SharedPreferences settings;
    Button scoreBtn;
    Button bestBtn;
    Button restart;
    GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameval_activity);
        gestureDetector= new GestureDetector(this);

        final View[] viewsId = new View[16];
        settings=getApplicationContext().getSharedPreferences(PREF_NAME,0);
        int best=settings.getInt("best",0);
        grid = new Grid(4,this,best);
        scoreBtn=findViewById(R.id.game_activity_score);
        bestBtn=findViewById(R.id.game_activity_best);
        restart=findViewById(R.id.main_activity_restart);


        //obtener todos los views
        viewsId[0] = findViewById(R.id.game_0_0);
        viewsId[1] = findViewById(R.id.game_0_1);
        viewsId[2] = findViewById(R.id.game_0_2);
        viewsId[3] = findViewById(R.id.game_0_3);
        viewsId[4] = findViewById(R.id.game_1_0);
        viewsId[5] = findViewById(R.id.game_1_1);
        viewsId[6] = findViewById(R.id.game_1_2);
        viewsId[7] = findViewById(R.id.game_1_3);
        viewsId[8] = findViewById(R.id.game_2_0);
        viewsId[9] = findViewById(R.id.game_2_1);
        viewsId[10] = findViewById(R.id.game_2_2);
        viewsId[11] = findViewById(R.id.game_2_3);
        viewsId[12] = findViewById(R.id.game_3_0);
        viewsId[13] = findViewById(R.id.game_3_1);
        viewsId[14] = findViewById(R.id.game_3_2);
        viewsId[15] = findViewById(R.id.game_3_3);
        //obtener resto de botones
        //TODO
        //Crear movimientos de los botones
        for (View v:viewsId){
            v.setOnTouchListener(new OnSwipeTouchListener(GamevalACTIVITY.this){
                public void onSwipeTop() {
                    grid.swipeOnGrid(Grid.UP);
                    setScoreAndBest();
                }
                public void onSwipeRight(){
                    grid.swipeOnGrid(Grid.RIGHT);
                    setScoreAndBest();
                }
                public void onSwipeBottom(){
                    grid.swipeOnGrid(Grid.DOWN);
                    setScoreAndBest();
                }
                public void onSwipeLeft(){
                    grid.swipeOnGrid(Grid.LEFT);
                    setScoreAndBest();
                }


            });

        }
        grid.setGridSquares(viewsId);
        grid.restartGrid();
        grid.addRandomNumber();
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
    @Override
    public  boolean onDown(MotionEvent motionEvent ){
        return false;
    }
    @Override
    public void onShowPress(MotionEvent motionEvent){

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent){
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent,MotionEvent motionEvent1, float v,float v1){
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent){

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);

    }

    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float yx, float xy){
        boolean result= false;
        float diffY= moveEvent.getY() - downEvent.getY();
        float diffX= moveEvent.getX() - downEvent.getX();

        if(Math.abs(diffX)> Math.abs(diffY)){
            if (Math.abs(diffX)> SWIPE_TRESHOLD && Math.abs(yx)> VELOCITY_TRESHOLD){
                if(diffX>0) {
                    grid.swipeOnGrid(Grid.RIGHT);
                } else {
                    grid.swipeOnGrid(Grid.LEFT);
                }
                result= true;
                setScoreAndBest();
            }
        } else{
            if (Math.abs(diffY)> SWIPE_TRESHOLD && Math.abs(xy)> VELOCITY_TRESHOLD){
                if (diffY>0) {
                    grid.swipeOnGrid(Grid.DOWN);
                } else {
                    grid.swipeOnGrid(Grid.UP);
                }
                result= true;
                setScoreAndBest();
            }
        }

        return result;
    }

    private void setScoreAndBest(){
        int score=grid.getScore();
        int best= grid.getBest();
        SharedPreferences.Editor editor= settings.edit();
        editor.putInt("beast",best);
        editor.apply();
        scoreBtn.setText("SCORE\n"+score);
        bestBtn.setText("BEST\n"+best);
    }
}
