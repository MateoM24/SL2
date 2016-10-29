package mm.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AllProductsHistoryActivity extends AppCompatActivity {
    Button backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products_history);
        backToMain=(Button)findViewById(R.id.backToMain);
        final Intent intentBackToMain=new Intent(this,MainActivity.class);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentBackToMain);
            }
        });
    }
}
