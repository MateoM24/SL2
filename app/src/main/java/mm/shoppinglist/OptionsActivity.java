package mm.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+"is seleted!!!! tu zmien obsluge.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getBaseContext(),"nie nie zaznaczone",Toast.LENGTH_SHORT);
            }
        });
        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+"jest zaznaczony! zien obsluge!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getBaseContext(),"nie nie zaznaczone",Toast.LENGTH_SHORT);
            }
        });
    }
}
