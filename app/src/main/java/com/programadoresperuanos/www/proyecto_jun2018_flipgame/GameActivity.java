package com.programadoresperuanos.www.proyecto_jun2018_flipgame;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private static final int[] colors = new int[]{
            R.drawable.ic_1c,R.drawable.ic_2c,R.drawable.ic_3c,
            R.drawable.ic_4c,R.drawable.ic_5c,R.drawable.ic_6c
    };
    private static final int[] numbers = new int[]{
            R.drawable.ic_1n,R.drawable.ic_2n,R.drawable.ic_3n,
            R.drawable.ic_4n,R.drawable.ic_5n,R.drawable.ic_6n
    };
    private int[] picture = null;
    private int topTileX = 3;
    private int topTileY = 3;
    private int topElements = 2;
    private boolean hasSound = false;
    private boolean hasVibration = false;
    private int ids[][] = null;
    private int values[][] = null;
    private int numberOfClicks = 0;
    private MediaPlayer mp = null;
    private Vibrator vibratorService = null;
    private TextView tvNumberOfClicks = null;
    private LinearLayout l1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        vibratorService = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this,R.raw.voice_alert);
        tvNumberOfClicks = findViewById(R.id.clicksTxt);
        l1 = findViewById(R.id.fieldLandscape);
        Bundle extras = getIntent().getExtras();
        if(extras != null)
        {
            topTileX = extras.getInt("sb_x")+3;
            topTileY = extras.getInt("sb_y")+3;
            topElements = extras.getInt("sb_tile")+2;
            if ("C".equals(extras.getString("tile"))){
                picture = colors;
            }else{
                picture = numbers;
            }
            hasSound = extras.getBoolean("hasSound");
            hasVibration = extras.getBoolean("hasVibration");
        }
        l1.removeAllViews();
        l1.setWeightSum(topTileY);

        //DisplayMetrics dm = new DisplayMetrics();
         /*getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = (dm.heightPixels-180)/topTileY;*/

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) l1.getLayoutParams();
        int h = params.height;
        int w = params.width;

        ids = new int[topTileX][topTileY];
        values = new int[topTileX][topTileY];
        Random r = new Random(System.currentTimeMillis());
        int tilePictureToShow = r.nextInt(topElements);
        int ident = 0;
        for(int i = 0; i < topTileY; i++){
            LinearLayout l2 = new LinearLayout(this);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l2.setWeightSum(topTileX);
            l2.setLayoutParams(new LinearLayout.LayoutParams(w,h/topTileY,1));
            for (int j = 0; j < topTileX; j++){
                tilePictureToShow = r.nextInt(topElements);
                values[j][i] = tilePictureToShow;
                TileView tv = new TileView(this,j,i,topElements,tilePictureToShow,picture[tilePictureToShow]);
                ident++;
                tv.setId(ident);
                ids[j][i] = ident;
                tv.setLayoutParams(new LinearLayout.LayoutParams(w/topTileX,h/topTileY,1));
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        hasClick(((TileView) view).x,((TileView) view).y);
                    }
                });
                l2.addView(tv);
            }
            l1.addView(l2);
        }
        Chronometer t = findViewById(R.id.chronometer);
        t.start();
    }

    @SuppressLint("SetTextI18n")
    private void hasClick(int x, int y) {
        if(hasVibration){
            vibratorService.vibrate(100);
        }
        if(hasSound){
            mp.start();
        }
        changeView(x,y);
        if(x == 0 && y == 0){
            changeView(0,1);
            changeView(1,0);
            changeView(1,1);
        }else if(x == 0 && y == topTileY-1){
            changeView(0,topTileY-2);
            changeView(1,topTileY-2);
            changeView(1,topTileY-1);
        }else if(x == topTileX-1 && y == 0){
            changeView(topTileX-2,0);
            changeView(topTileX-2,1);
            changeView(topTileX-1,1);
        }else if(x == topTileX-1 && y == topTileX-1){
            changeView(topTileX-2,topTileX-1);
            changeView(topTileX-2,topTileX-2);
            changeView(topTileX-1,topTileX-2);
        }else if(x == 0){
            changeView(x,y-1);
            changeView(x,y+1);
            changeView(x+1,y);
        }else if(y == 0){
            changeView(x-1,y);
            changeView(x+1,y);
            changeView(x,y+1);
        }else if(x == topTileX-1){
            changeView(x,y-1);
            changeView(x,y+1);
            changeView(x-1,y);
        }else if(y == topTileY-1){
            changeView(x-1,y);
            changeView(x+1,y);
            changeView(x,y-1);
        }else{
            changeView(x-1,y);
            changeView(x+1,y);
            changeView(x,y-1);
            changeView(x,y+1);
        }
        numberOfClicks++;
        tvNumberOfClicks.setText(getString(R.string.score_clicks)+String.valueOf(numberOfClicks));
        checkIfFinished();
    }

    private void checkIfFinished() {
        int targetValue = values[0][0];
        for(int i = 0; i < topTileY; i++ ){
            for(int j = 0; j < topTileX; j++ ){
                if(values[j][i] != targetValue){
                    return;
                }
            }
        }
        Intent resultIntent = new Intent((String)null);
        resultIntent.putExtra("clicks",numberOfClicks);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    private void changeView(int x, int y) {
        TileView tt = findViewById(ids[x][y]);
        int newIndex = tt.getNewIndex();
        values[x][y] = newIndex;
        tt.setBackgroundResource(picture[newIndex]);
        tt.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_uno,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.opcion1:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                return true;
            case R.id.opcion2:
                startActivity(new Intent(getApplicationContext(),Main2Activity.class));
                return true;
            case R.id.opcion3:
                startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
