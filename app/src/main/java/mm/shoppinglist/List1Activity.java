package mm.shoppinglist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.HashSet;
import java.util.List;
import mateusz.mezyk.shoppinglist.backend.productApi.model.Product;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import mateusz.mezyk.shoppinglist.backend.productApi.ProductApi;
import java.io.IOException;
import java.util.Set;

public class List1Activity extends AppCompatActivity {

    List<Product> list;
    Intent intent;
    RadioGroup group;

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list1);
        group=(RadioGroup)findViewById(R.id.radioGroup);
        //Product xProduct=new Product();
        //xProduct.setName("testProduct");
        //new TaskAddOne(xProduct).execute();
    }
    @Override
    protected  void onResume(){
        super.onResume();
        makeUpTheList();
         }

    protected void makeUpTheList(){
        new TaskGetAll().execute();
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
            case R.id.add_option:
                intent=new Intent(this,Popup.class);
                startActivity(intent);
                return true;
            case R.id.edit_option:
//                if(isSelected){
//                    intent=new Intent(this,PopupEdit.class);
//                    intent.putExtra("name",currentButton.getText().toString());
//                    startActivity(intent);}else{
//                    Toast.makeText(getBaseContext(),R.string.select_item,Toast.LENGTH_SHORT);
//                }
                return true;
            case R.id.delete_option:
                int selectedOnes=0;
                int countCTV=group.getChildCount();
                Log.d("group count: ",String.valueOf(countCTV));
                CheckBox ctv;
                for(int i=0;i<countCTV;i++){
                    ctv=(CheckBox)group.getChildAt(i);
                    if(ctv.isChecked()){
                        String s=ctv.getText().toString();
                        for (Product p:list){
                            if (p.getName().equals(s)||p.getName()==s){
                                new TaskRemoveOne(p.getId()).execute();
                                selectedOnes++;
                            }
                        }
                    }
                }
                Toast.makeText(getBaseContext(),"Usunięto "+selectedOnes+" produktów",Toast.LENGTH_SHORT).show();

                return true;
            case R.id.delete_all_option:
//do usuniecia
//                intent=new Intent(this,PopupRemoveAll.class);
//                startActivity(intent);
//                isSelected=false;
                return true;
            case R.id.return_button:
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ///////////////////////////////////////////////////
    private class TaskGetAll extends AsyncTask<Void,Void,List<mateusz.mezyk.shoppinglist.backend.productApi.model.Product>>{

        ProductApi ofyService;

        public TaskGetAll() {
        }

        @Override
        protected List<mateusz.mezyk.shoppinglist.backend.productApi.model.Product> doInBackground(Void... params) {
            if(ofyService==null){
                ProductApi.Builder builder=new ProductApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                        .setRootUrl("http://10.0.2.2:8081/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        });
                ofyService=builder.build();
                Log.d("stepy","TaskGetAll DoInBackground");
            }
            try {
                return ofyService.list().execute().getItems();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            Log.d("stepy", "TaskGetAll onPostExec, products==null?" + (products == null));
            list = products;
            RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

            if ((list != null)) {
            Product p;
            Log.d("size of list: ", String.valueOf(list.size()));
            radioGroup.removeAllViews();//novedad czy dziala?
            for (int i = 0; i < list.size(); i++) {
                p = list.get(i);
                Log.d("IDiki z bazy: ",String.valueOf(p.getId()));
                if (!p.getDone())
                {
                    final CheckBox cb;
                    cb = new CheckBox(getBaseContext());
                    cb.setText(p.getName());
                    cb.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
                    cb.setBackgroundColor(Color.GREEN);

                    radioGroup.addView(cb);
                    Log.d("ile razy loop",String.valueOf(i));
                }
            }
        }else Log.d("błąd", "zmiena 'list' jest null!");

        }
    }
    ////////////////////////////////////////////
    private class TaskRemoveOne extends AsyncTask<Long,Void,Void>{

        ProductApi ofyService;
        Long id;

        public TaskRemoveOne(Long id){
        this.id=id;
        }

        @Override
        protected Void doInBackground(Long... params) {
            if(ofyService==null){
                ProductApi.Builder builder=new ProductApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                        .setRootUrl("http://10.0.2.2:8081/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        });
                ofyService=builder.build();
            }
            try {
                ofyService.remove(id).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onResume();
        }
    }
    /////////////////////////////////////////////////////////
    class TaskAddOne extends AsyncTask<Product,Void,Void> {

        ProductApi ofyService;
        Product p;

        public TaskAddOne(Product p){
            this.p=p;
        }

        @Override
        protected Void doInBackground(Product... params) {
            if(ofyService==null){
                ProductApi.Builder builder=new ProductApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                        .setRootUrl("http://10.0.2.2:8081/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        });
                ofyService=builder.build();
            }
            try {
                ofyService.insert(p).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    ///////////////////////////////////////
    class TaskMarkDone extends AsyncTask<Product,Void,Void> {

        ProductApi ofyService;
        Product p;

        public TaskMarkDone(Product p){
            this.p=p;
        }

        @Override
        protected Void doInBackground(Product... params) {
            if(ofyService==null){
                ProductApi.Builder builder=new ProductApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                        .setRootUrl("http://10.0.2.2:8081/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        });
                ofyService=builder.build();
            }
            p.setDone(true);
            try {
                ofyService.update(p.getId(),p).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onResume();
        }
    }
    public void markDone(View v) {
        int selectedOnes = 0;
        int countCTV = group.getChildCount();
        Log.d("groupCountDoMarkDone): ", String.valueOf(countCTV));
        CheckBox ctv;
        for (int i = 0; i < countCTV; i++) {
            ctv = (CheckBox) group.getChildAt(i);
            if (ctv.isChecked()) {
                String s = ctv.getText().toString();
                for (Product p : list) {
                        new TaskMarkDone(p).execute();
                        selectedOnes++;

                }
            }
        }
        Toast.makeText(getBaseContext(), "Kupiono " + selectedOnes + " produktów", Toast.LENGTH_SHORT).show();
    }
}
