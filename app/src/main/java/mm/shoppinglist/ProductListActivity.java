package mm.shoppinglist;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.graphics.Paint.STRIKE_THRU_TEXT_FLAG;

public class ProductListActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private SQLiteDatabase db;
    private Cursor cursor;
    LinearLayout linearLayout;
    TextView currentTV;
    DBHelper dbHelper;
    CheckBox lastCB;
    CheckBox currentCB;
    Button currentButton;
    Intent intent;
    boolean isSelected;
    public static Set<String> buttonNamesSet=new HashSet<String>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);//to niestandardowe podejscie
        dbHelper=new DBHelper(this);
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
                if (isSelected)
                try{currentTV=(TextView)linearLayout.getChildAt(1);
                DBHelper.UpdateRowDoneValue(dbHelper.getWritableDatabase(),currentTV.getText().toString(),true);
                currentTV.setPaintFlags(currentTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                db=dbHelper.getWritableDatabase();
                DBHelper.UpdateRowDoneValue(db,currentTV.getText().toString(),true);
                db.close();
                buttonNamesSet.add(currentTV.getText().toString());
                }catch (NullPointerException e){}finally {
                return true;}
            case R.id.not_bought_button:
                if (isSelected)
                try{currentTV=(TextView)linearLayout.getChildAt(1);
                DBHelper.UpdateRowDoneValue(dbHelper.getWritableDatabase(),currentTV.getText().toString(),false);
                currentTV.setPaintFlags(currentTV.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
                db=dbHelper.getWritableDatabase();
                DBHelper.UpdateRowDoneValue(db,currentTV.getText().toString(),false);
                db.close();
                buttonNamesSet.add(currentTV.getText().toString());
                }catch (NullPointerException e){}finally {
                return true;}
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
                isSelected=false;
                return true;
            case R.id.return_button:
                intent = new Intent(this,MainActivity.class);
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

        ListAdapter listAdapter=new MySimpleCursorAdapter2(this,
                R.layout.single_row,cursor, new String[]{"NAME"},
                new int[]{R.id.productNameTV},0);

//        ListAdapter listAdapter=new SimpleCursorAdapter(this,
//                R.layout.single_row,cursor, new String[]{"NAME"},
//                new int[]{R.id.productNameTV},0);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(this);
    }
    //================================================================================
    public class MySimpleCursorAdapter2 extends android.support.v4.widget.SimpleCursorAdapter {
        SharedPreferences sharedPreferences;

        public MySimpleCursorAdapter2(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);

        }

        @Override
        public void setViewText(TextView v, String text) {
            super.setViewText(v, text);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            //Button b=(Button) findViewById(R.id.productNameTV);
              //      b.setTextSize(9);
            return super.newView(context, cursor, parent);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null)
            {
                LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.single_row, null);
            }
            TextView tv = (TextView)convertView.findViewById(R.id.productNameTV);
            sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
            tv.setTextSize(sharedPreferences.getInt("size",20));
            tv.setTextColor(sharedPreferences.getInt("color",Color.GRAY));
            /*String currViewText=tv.getText().toString();
            Cursor cursorI=DBHelper.getDoneValue(dbHelper.getReadableDatabase(),currViewText);
            if(cursorI!=null && cursorI.moveToFirst()) {
                int i = cursorI.getInt(0);
                if (i == 1) {
                    Log.d("done? ", currViewText + " tak");
                    tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    Log.d("done? ", currViewText + " nie"+String.valueOf(i));
                    tv.setPaintFlags(tv.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }*/

            //return convertView;
           return super.getView(position, convertView, parent);

        }
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);
//            SharedPreferences sharedPreferences= mContext.getSharedPreferences("prefs",MODE_PRIVATE);
//            if (view instanceof Button){
//                int i=sharedPreferences.getInt("size",6);
//                ((Button)view).setTextSize((float)i);
//                int c=sharedPreferences.getInt("color", Color.BLACK);
//                ((Button)view).setTextColor(c);
//                Log.d("jaki text?",((Button) view).getText().toString());
//            }

        }
    }
    //=====================================================
    public void toAddPopup(View v){
        intent=new Intent(this,Popup.class);
        startActivity(intent);
    }
    public int getAllButtons(){
        return linearLayout.getChildCount();
    }
}
