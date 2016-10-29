package mm.shoppinglist;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ProductListActivity extends ListActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    Button backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);//to niestandardowe podejscie
        backToMain=(Button)findViewById(R.id.backToMain);
        final Intent intentToMain=new Intent(this,MainActivity.class);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentToMain);
            }
        });

        ListView list=getListView();
        //ListView list=(ListView)findViewById(R.id.list);
        DBHelper dbHelper=new DBHelper(this);
        db=dbHelper.getWritableDatabase();
        cursor=dbHelper.GetAllShoppings(db);
        ListAdapter listAdapter=new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,cursor, new String[]{"NAME","DONE"},
                new int[]{android.R.id.text1,android.R.id.toggle},0);
        list.setAdapter(listAdapter);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
    }

}
