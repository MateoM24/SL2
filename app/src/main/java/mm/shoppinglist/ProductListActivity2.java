package mm.shoppinglist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProductListActivity2 extends AppCompatActivity {
    Button backToMain;
    TextView textView;
    DBHelper dbhelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list2);
        backToMain=(Button)findViewById(R.id.backToMain);
        final Intent intentToMain=new Intent(this,MainActivity.class);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentToMain);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        textView=(TextView)findViewById(R.id.tvLista);
        textView.setText("");
        dbhelper=new DBHelper(this);
        db=dbhelper.getReadableDatabase();
        Cursor cursor=dbhelper.getAllShoppings(db);
            if(cursor.moveToFirst()){
                String name=cursor.getString(0);
                boolean done=cursor.getInt(1)>0;
                textView.append(name+" "+done);
                if(cursor.moveToNext()){
                    String name1=cursor.getString(0);
                    boolean done1=cursor.getInt(1)>0;
                    textView.append("\n"+name1+" "+done1);
                }
            } else textView.setText("no records");
        cursor.close();
        db.close();
    }
    public void addItem(View view){
        db=dbhelper.getReadableDatabase();
        dbhelper.insertRowShoppingList(db,"mleko");
        dbhelper.insertRowShoppingList(db,"maslo");

    }
}
