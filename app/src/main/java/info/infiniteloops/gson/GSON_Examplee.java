package info.infiniteloops.gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;

import info.infiniteloops.R;

public class GSON_Examplee extends AppCompatActivity {
    GSONModel[] gsonModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson__examplee);
        GETJSONNDetails();
    }
    private void GETJSONNDetails(){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "http://www.json-generator.com/api/json/get/cqWlUrLVvm?indent=2", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        JsonReader reader = new JsonReader(new StringReader(response.toString()));
                        reader.setLenient(true);
                        Type callDetailsCollection = new TypeToken<Collection<GSON_Examplee>>() {
                        }.getType();
                        Collection<GSONModel> enums = gson.fromJson(reader, callDetailsCollection);
                        gsonModel = enums.toArray(new GSONModel[enums.size()]);
                        System.out.println("Symbol : " + gsonModel[0].getFirstName());
                        Toast.makeText(GSON_Examplee.this,"GSON First name : "+gsonModel[0].getFirstName(),Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }
    static ProgressDialog pDialog;

    public static void showLoadingDialog(Context mContext){
        pDialog = new ProgressDialog(mContext);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading, Please wait...");
        pDialog.show();
    }
    public static void hideLoadingDialog(){
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
