package com.programadoresperuanos.www.proyecto_jun2018_flipgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    private static final int ACTION_PLAY = 1;

    Button boton_inicio;
    SeekBar sb_x,sb_y,sb_tile;
    TextView tv_x,tv_y, tv_tile;
    RadioButton rb1;
    CheckBox ch1,ch2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        boton_inicio    = findViewById(R.id.button);
        sb_x            = findViewById(R.id.seekBar);
        sb_y            = findViewById(R.id.seekBar2);
        sb_tile         = findViewById(R.id.seekBar3);
        tv_x            = findViewById(R.id.textView);
        tv_y            = findViewById(R.id.textView2);
        tv_tile         = findViewById(R.id.textView3);
        rb1             = findViewById(R.id.radioButton);
        ch1             = findViewById(R.id.checkBox);
        ch2             = findViewById(R.id.checkBox2);

        tv_x.setText(getString(R.string.num_element_X)+" "+String.valueOf(sb_x.getProgress()+3));
        tv_y.setText(getString(R.string.num_element_Y)+" "+String.valueOf(sb_y.getProgress()+3));
        tv_tile.setText(getString(R.string.num_colors)+" "+String.valueOf(sb_tile.getProgress()+2));

        boton_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main2Activity.this,GameActivity.class);
                i.putExtra("sb_x",sb_x.getProgress());
                i.putExtra("sb_y",sb_y.getProgress());
                i.putExtra("sb_tile",sb_tile.getProgress());
                i.putExtra("tile",rb1.isChecked()?"C":"N");
                i.putExtra("hasSound",ch1.isChecked());
                i.putExtra("hasVibration",ch2.isChecked());
                startActivityForResult(i,ACTION_PLAY);
            }
        });
        sb_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_x.setText(getString(R.string.num_element_X)+String.valueOf(seekBar.getProgress()+3));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        sb_y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_y.setText(getString(R.string.num_element_Y)+String.valueOf(seekBar.getProgress()+3));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        sb_tile.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_tile.setText(getString(R.string.num_colors)+String.valueOf(seekBar.getProgress()+2));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case ACTION_PLAY:
                    new AlertDialog.Builder(this)
                            .setMessage(String.format(getResources().getString(R.string.game_end),data.getIntExtra("clicks",0)))
                            .setPositiveButton(android.R.string.ok,null).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
