package mm.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.textSize;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mateusz on 2016-11-01. unused
 */

public class MySimpleCursorAdapter extends SimpleCursorAdapter {
    SharedPreferences sharedPreferences;

    public MySimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);

    }

    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, text);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
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
        //sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
        tv.setTextSize(30);
        //return convertView;
        return super.getView(position, convertView, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        SharedPreferences sharedPreferences= mContext.getSharedPreferences("prefs",MODE_PRIVATE);
        if (view instanceof Button){
            int i=sharedPreferences.getInt("size",6);
            ((Button)view).setTextSize((float)i);
            Log.d("sh prefs val:",Integer.toString(i));
        }

    }
}
