package mm.shoppinglist;

import android.app.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class Popup extends Activity{
    SQLiteDatabase db;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width= displayMetrics.widthPixels;
        int hight=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(hight*0.4));
    }
    public void add(View v){

    }
}
