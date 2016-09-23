package gcm.play.android.samples.com.gcmquickstart;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;

public class AddFriend extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private NameAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    ArrayList<String> allUsers;
    String user_name;
    Toolbar toolbar;
    RequestQueue requestQueue;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        allUsers=new ArrayList<String>();

        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Friend");

    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info=connectivityManager.getActiveNetworkInfo();
        if(info!=null)
        {
            getJSONrequest();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Sorry please check network connection", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

/////sent request to server
    public void getJSONrequest()
    {
        SharedPreferences preferences=getSharedPreferences(Util.USER_NAME, MODE_PRIVATE);

        user_name=preferences.getString(Util.USER_NAME, "");
        String url=Util.getAllUsersUrl+"?name="+user_name;
        Log.i("SOURAV",url);
        requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                allUsers=parseJSON(response);
                mRecyclerView = (RecyclerView) findViewById(R.id.friend_recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManger = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLayoutManger);
                mAdapter = new NameAdapter(allUsers,getApplicationContext(),requestQueue);
                mRecyclerView.setAdapter(mAdapter);
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        dialog = new ProgressDialog(AddFriend.this, ProgressDialog.THEME_HOLO_DARK);
        dialog.setTitle("Processing");
        dialog.setMessage("Getting list...");
        requestQueue.add(request);
        dialog.show();
        //dialog=ProgressDialog.show(Create_Events.this,"Processing","Creating event...");

    }

    private ArrayList<String> parseJSON(JSONObject response) {
        ArrayList<String> userList=new ArrayList<String>();
        if(response!=null)
        {
            try {
                JSONArray jsonArray=response.getJSONArray("userlist");
                for(int i=0;i<jsonArray.length();i++)
                {

                    userList.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return userList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }



}
