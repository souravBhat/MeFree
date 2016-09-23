package gcm.play.android.samples.com.gcmquickstart;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Going_listActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    TextView empty;
    String id_of_event;
    RequestQueue requestQueue;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_going_list);

        ////initialise toolbar listview and empty textview
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        listView=(ListView)findViewById(R.id.current_going);
        empty=(TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(empty);

        requestQueue= Volley.newRequestQueue(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Going List");

        ///get the unique event id
        id_of_event=getIntent().getStringExtra(Util.EVENT_IDS);
//        dialog=new ProgressDialog(Going_listActivity.this,R.style.NewDialog);
        getGoingList();


    }

    private void getGoingList() {
        final ArrayList<String> going_list=new ArrayList<String>();
        String url=Util.GET_GOING_LIST_URL+"?event_id="+id_of_event;////ask braden

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray list=response.getJSONArray("participants");
                    for(int i=0;i<list.length();i++)
                    {
                        going_list.add(list.getString(i));
                    }
                    ListAdapter adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.goinglist_item,going_list);
                    listView.setAdapter(adapter);
                    //dialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Failed to load going list",Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        requestQueue.add(request);
        //dialog.setTitle("Loading");
        //dialog.setMessage( "Getting list");
        //dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_going_list, menu);
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
