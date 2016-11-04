package mm.shoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {
    Button button_return;
    Spinner spinnerSize;
    Spinner spinnerColor;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    SharedPreferences sharedPreferences;
    int size;
    String color;
    int c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        button_return=(Button) findViewById(R.id.return_button);
        final Intent backToMain=new Intent(this,MainActivity.class);
        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(backToMain);
            }
        });
        spinnerSize=(Spinner)findViewById(R.id.spinner1);
        spinnerColor=(Spinner)findViewById(R.id.spinner2);
        adapter1=ArrayAdapter.createFromResource(this,R.array.font_sizes,android.R.layout.simple_spinner_item);
        adapter2=ArrayAdapter.createFromResource(this,R.array.font_colors,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSize.setAdapter(adapter1);
        spinnerColor.setAdapter(adapter2);
        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("wartosc int: ",((String)adapterView.getItemAtPosition(i)));
                size=Integer.valueOf((String)adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                color=(String)adapterView.getItemAtPosition(i);
                Log.d("onclick kolor ",color);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
        public void accept(View v){
            sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("size",size);
            switch(color){
                case "black" : c= Color.BLACK;
                    break;
                case "red" : c=Color.RED;
                    break;
                case "blue" : c=Color.BLUE;
                   break;

            }

            editor.putInt("color",c);
            editor.apply();
            editor.commit();
            finish();
        }
        public void cancel(View v){finish();}

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
        int i=sharedPreferences.getInt("size",20);
        adapter1.getPosition(String.valueOf(i));
        Log.d("size onResume ",String.valueOf(i));
        int s=sharedPreferences.getInt("color",Color.BLACK);
        String posS;
        switch (s){
            case Color.BLACK: posS="black";
                break;
            case Color.RED: posS="red";
                break;
            case Color.BLUE: posS="blue";
                break;
            default: posS="black";
        }
        Log.d("kolor posS onResume ",posS);
        adapter2.getPosition(posS);
        Log.d("resume,kolor: ",posS);
    }
}
