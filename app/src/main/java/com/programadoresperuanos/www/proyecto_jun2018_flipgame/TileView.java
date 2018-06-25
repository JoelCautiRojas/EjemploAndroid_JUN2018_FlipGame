package com.programadoresperuanos.www.proyecto_jun2018_flipgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class TileView extends Button {

    public int x = 0;
    public int y = 0;
    private int index = 0;
    private int topElementos = 0;

    public TileView(Context context, int x, int y, int topElementos, int index,int background) {
        super(context);
        this.x = x;
        this.y = y;
        this.index = index;
        this.topElementos = topElementos;
        this.setBackgroundResource(background);
    }

    public int getNewIndex()
    {
        index++;
        if (index == topElementos)
        {
            index = 0;
        }
        return index;
    }
}
