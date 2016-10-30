package mm.shoppinglist;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

public class ProductListActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private SQLiteDatabase db;
    private Cursor cursor;
    Button backToMain;
    Set<Button> buttonSet;
    Button currentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);//to niestandardowe podejscie
        buttonSet=new HashSet<Button>();
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
        list.setOnItemClickListener(this);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id){
//        super.onListItemClick(l,v,position,id);
//        String selectedItem = (String) getListView().getItemAtPosition(position);
//        Toast.makeText(getBaseContext(),"cos",Toast.LENGTH_LONG).show();
//
//        System.out.println("niec si enie dziejeeeeeeeeeeeeeeeeeeeee");
//        Log.d("i co","nic");
//        TextView tv=(TextView)getListView().getItemAtPosition(position);
//        tv.getPaintFlags();
//        tv.setPaintFlags(STRIKE_THRU_TEXT_FLAG);
//
//        if ((tv.getPaintFlags() & Paint.STRIKE_THRU_TEXT_FLAG) > 0){
//            tv.setPaintFlags( tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
//        }else tv.setPaintFlags( tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LinearLayout linearLayout= (LinearLayout)view;
        //String s=Integer.toString(linearLayout.getChildCount());
        //String ss =linearLayout.getChildAt(0).getClass().toString();
        //Toast.makeText(getBaseContext(),ss,Toast.LENGTH_SHORT).show();
        CheckBox cb=(CheckBox)linearLayout.getChildAt(0);
        if(!cb.isChecked()){
            cb.setChecked(true);
            currentButton=(Button)linearLayout.getChildAt(1);
            buttonSet.add(currentButton);
            for(Button b:buttonSet){
            Toast.makeText(getBaseContext(),b.getText(),Toast.LENGTH_SHORT).show();}
        }else {
            cb.setChecked(false);
            currentButton=(Button)linearLayout.getChildAt(1);
            buttonSet.remove(currentButton);
            for(Button b:buttonSet){
                Toast.makeText(getBaseContext(),b.getText(),Toast.LENGTH_SHORT).show();}
        }

    }
    public Set<Button> getSelectedButtons(){
        return buttonSet;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu__shopping_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_option:
                Toast.makeText(getBaseContext(),"opcja add",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.edit_option:
                Toast.makeText(getBaseContext(),"opcja edit",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.delete_option:
                Toast.makeText(getBaseContext(),"opcja delete",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
