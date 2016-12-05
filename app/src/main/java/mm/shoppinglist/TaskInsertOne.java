//package mm.shoppinglist;
//
///**
// * Created by Mateusz on 2016-12-02.
// */
//
//import android.app.Activity;
//import android.os.AsyncTask;
//
//import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.extensions.android.json.AndroidJsonFactory;
//import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
//import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
//
//import java.io.IOException;
//import java.util.List;
//
//import mateusz.mezyk.shoppinglist.backend.productApi.ProductApi;
//import mateusz.mezyk.shoppinglist.backend.productApi.model.Product;
//
////import mateusz.mezyk.shoppinglist.backend.Product;
//
//public class TaskInsertOne extends AsyncTask<Void,Void,Product>{
//
//    ProductApi ofyService;
//    Activity act;
//    Product product;
//
//    public TaskInsertOne(Activity act, Product p) {
//        this.act = act;
//        this.product=p;
//    }
//
//    @Override
//    protected Product doInBackground(Void... params) {
//        if(ofyService==null){
//            ProductApi.Builder builder=new ProductApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
//                    .setRootUrl("http://10.0.2.2:8081/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
//                            request.setDisableGZipContent(true);
//                        }
//                    });
//            ofyService=builder.build();
//        }
//            try {
//                return ofyService.insert(product).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//return null;
//    }
//}
