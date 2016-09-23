package gcm.play.android.samples.com.gcmquickstart;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class Dash_board extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_appbarout);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        listView=(ListView)findViewById(R.id.eventsAttending);
        empty=(TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(empty);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Your Dashboard");
        Navigation_fragment fragment=(Navigation_fragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigationbar);
        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        fragment.setUp(drawerLayout, toolbar, R.id.fragment_navigationbar);

        final ArrayList<String> event_ids=new ArrayList<String>();

        String from[]={MeFreeProviderContract.Going.NAME,MeFreeProviderContract.Going.LOCATION,MeFreeProviderContract.Going.DATE};
        int [] to ={R.id.event_name,R.id.location,R.id.date};

        ContentResolver resolver=getContentResolver();
        Cursor cursor=resolver.query(MeFreeProviderContract.Going.CONTENT_URI,MeFreeProviderContract.Going.PROJECTION_ALL,null,null,null);

        /////populate the listview

        int event_id=cursor.getColumnIndex(MeFreeProviderContract.Going.EVENT_ID);
        int nameIndex=cursor.getColumnIndex(MeFreeProviderContract.Going.NAME);
        int locationIndex=cursor.getColumnIndex(MeFreeProviderContract.Going.LOCATION);
        int dateIndex=cursor.getColumnIndex(MeFreeProviderContract.Going.DATE);

        while(cursor.moveToNext())
        {
            event_ids.add(cursor.getString(event_id));
        }
        cursor.moveToFirst();

        ListAdapter adapter=new SimpleCursorAdapter(getApplicationContext(),R.layout.dashboard_itemview,cursor,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Event_Description.class);
                intent.putExtra(Util.DESCRIPTION_TYPE,"old");
                intent.putExtra(Util.EVENT_IDS,event_ids.get(i));
                startActivity(intent);
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dash_board, menu);
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
        if(id==R.id.action_logout)
        {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   /* public void getUserListFromServer()
    {

    }



    public void getJSONrequest()
    {
        SharedPreferences preferences=getSharedPreferences(Util.USER_NAME, MODE_PRIVATE);

        user_name=preferences.getString(Util.USER_NAME, "");
        String url=Util.getAllUsersUrl+"?name="+user_name;
        Log.i("SOURAV", url);
        requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }*/


    private void logout(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences=getSharedPreferences(Util.USER_LOGGEDIN,MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Util.USER_LOGGEDIN, "false");
                editor.commit();
                Intent intent = new Intent(Dash_board.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
