package mm.shoppinglist;

import android.app.Activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Popup extends Activity{
    SQLiteDatabase db;
    DBHelper dbHelper;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width= displayMetrics.widthPixels;
        int hight=displayMetrics.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(hight*0.4));
        editText=(EditText)findViewById(R.id.edit_tv);
    }
    public void add(View v){
        dbHelper=new DBHelper(this);
        String newProduct=editText.getText().toString();
        DBHelper.InsertRowShoppingList(dbHelper.getWritableDatabase(),newProduct);
        editText.setText(null);
        Toast.makeText(getBaseContext(),newProduct+R.string.added,Toast.LENGTH_LONG);
    }
}
