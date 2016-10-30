package mm.shoppinglist;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

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
                R.layout.single_row,cursor, new String[]{"NAME"},
                new int[]{R.id.productNameTV},0);
        list.setAdapter(listAdapter);
        ///////kombinuj
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);
        System.out.println("niec si enie dziejeeeeeeeeeeeeeeeeeeeee");
        Log.d("i co","nic");
        TextView tv=(TextView)getListView().getItemAtPosition(position);
        tv.getPaintFlags();
        tv.setPaintFlags(STRIKE_THRU_TEXT_FLAG);

        if ((tv.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0){
            tv.setPaintFlags( tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }else tv.setPaintFlags( tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

}
