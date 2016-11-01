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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

public class ProductListActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private SQLiteDatabase db;
    private Cursor cursor;
    Button backToMain;
    LinearLayout linearLayout;
    TextView currentTV;
    DBHelper dbHelper;
    CheckBox lastCB;
    CheckBox currentCB;
    Button currentButton;
    Map<CheckBox,Button> map;
    Intent intent;
    boolean isSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);//to niestandardowe podejscie
        //map=new HashMap<>(1,2);
       // buttonSet=new HashSet<Button>();don't need it
        dbHelper=new DBHelper(this);
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
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        isSelected=true;
        linearLayout= (LinearLayout)view;
        if(currentCB==null){
        currentCB=(CheckBox) linearLayout.getChildAt(0);
        currentCB.toggle();
            currentButton=(Button)linearLayout.getChildAt(1);
            //map.clear();
            //map.put(currentCB,currentButton);


        }else{
            lastCB=currentCB;
            lastCB.toggle();
            currentCB=(CheckBox) linearLayout.getChildAt(0);
            currentCB.toggle();
            currentButton=(Button)linearLayout.getChildAt(1);
            //map.clear();
            //map.put(currentCB,currentButton);
            }
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
        switch (item.getItemId()) {
            case R.id.bought_button:
                if (isSelected){
                currentTV=(TextView)linearLayout.getChildAt(1);
                DBHelper.UpdateRowDoneValue(dbHelper.getWritableDatabase(),currentTV.getText().toString(),true);
                currentTV.setPaintFlags(currentTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);}
                return true;
            case R.id.not_bought_button:
                if (isSelected){
                currentTV=(TextView)linearLayout.getChildAt(1);
                DBHelper.UpdateRowDoneValue(dbHelper.getWritableDatabase(),currentTV.getText().toString(),false);
                currentTV.setPaintFlags(currentTV.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);}
                return true;
            case R.id.add_option:
                intent=new Intent(this,Popup.class);
                startActivity(intent);
                return true;
            case R.id.edit_option:
                intent=new Intent(this,PopupEdit.class);
                intent.putExtra("name",currentButton.getText().toString());
                startActivity(intent);
                return true;
            case R.id.delete_option:
                intent=new Intent(this,PopupRemove.class);
                intent.putExtra("name",currentButton.getText().toString());
                startActivity(intent);
                return true;
            case R.id.delete_all_option:
                intent=new Intent(this,PopupRemoveAll.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView list=getListView();
        DBHelper dbHelper=new DBHelper(this);
        db=dbHelper.getWritableDatabase();
        cursor=dbHelper.GetAllShoppings(db);
        ListAdapter listAdapter=new SimpleCursorAdapter(this,
                R.layout.single_row,cursor, new String[]{"NAME"},
                new int[]{R.id.productNameTV},0);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(this);


    }
    public void toAddPopup(View v){
        intent=new Intent(this,Popup.class);
        startActivity(intent);
    }
}
