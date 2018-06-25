package com.programadoresperuanos.www.proyecto_jun2018_flipgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int ACTION_PLAY = 1;
    Button boton_inicio;
    SeekBar sb_x,sb_y,sb_colors;
    TextView tv_x,tv_y,tv_colors;
    RadioButton rb1;
    CheckBox ch1,ch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton_inicio = findViewById(R.id.button);
        sb_x        = findViewById(R.id.seekBar);
        sb_y        = findViewById(R.id.seekBar2);
        sb_colors   = findViewById(R.id.seekBar3);
        tv_x        = findViewById(R.id.textView);
        tv_y        = findViewById(R.id.textView2);
        tv_colors   = findViewById(R.id.textView3);
        rb1         = findViewById(R.id.radioButton);
        ch1         = findViewById(R.id.checkBox);
        ch2         = findViewById(R.id.checkBox2);

        boton_inicio.setText("Jugar");
        updateX(sb_x.getProgress());
        updateY(sb_y.getProgress());
        updateColors(sb_colors.getProgress());
        boton_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlay();
            }
        });
        sb_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateX(seekBar.getProgress());
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
                updateY(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_colors.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateColors(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void updateColors(int progress) {
        tv_colors.setText(getString(R.string.num_colors)+String.valueOf(progress+2));
    }

    @SuppressLint("SetTextI18n")
    private void updateY(int progress) {
        tv_y.setText(getString(R.string.num_element_Y)+String.valueOf(progress+3));
    }

    @SuppressLint("SetTextI18n")
    private void updateX(int progress) {
        tv_x.setText(getString(R.string.num_element_X)+String.valueOf(progress+3));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_uno,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.opcion1:
                showPlayer();
                return true;
            case R.id.opcion2:
                showHowTo();
                return true;
            case R.id.opcion3:
                showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAbout() {
        startActivity(new Intent(MainActivity.this,AboutActivity.class));
    }

    private void showHowTo() {
        startActivity(new Intent(MainActivity.this,HowToActivity.class));
    }

    private void showPlayer() {
        startActivity(new Intent(MainActivity.this,PlayerActivity.class));
    }

    private void startPlay() {
        Intent i = new Intent(MainActivity.this,GameActivity.class);
        i.putExtra("sb_x",sb_x.getProgress());
        i.putExtra("sb_y",sb_y.getProgress());
        i.putExtra("sb_colors",sb_colors.getProgress());
        i.putExtra("tile",rb1.isChecked()?"C":"N");
        i.putExtra("hasSound",ch1.isChecked());
        i.putExtra("hasVibration",ch2.isChecked());
        startActivityForResult(i,ACTION_PLAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            switch(requestCode)
            {
                case ACTION_PLAY:
                    new AlertDialog.Builder(this)
                    .setMessage(String.format(getResources().getString(R.string.game_end),data.getIntExtra("clicks",0)))
                    .setPositiveButton(android.R.string.ok,null).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
