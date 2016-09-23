package gcm.play.android.samples.com.gcmquickstart;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class Event_Description extends AppCompatActivity {

    Toolbar toolbar;
    String event_id;
    ProgressDialog dialog;
    RequestQueue requestQueue;
    TextView event_name, location, date, description;
    Button btAccept, btDecline, btGoing;
    Context context;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__description);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event Description");

        ////chain to respective views(event_name, location,date, description..textviews)
        event_name = (TextView) findViewById(R.id.event_name);
        location = (TextView) findViewById(R.id.location);
        date = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);
        btAccept = (Button) findViewById(R.id.btAccept);
        btDecline = (Button) findViewById(R.id.btDecline);
        btGoing = (Button) findViewById(R.id.btSeeGoning);

        String type = getIntent().getStringExtra(Util.DESCRIPTION_TYPE);
        if (type.equals("old")) {
            btAccept.setVisibility(View.INVISIBLE);
            btDecline.setVisibility(View.INVISIBLE);
            btAccept.setEnabled(false);
            btDecline.setEnabled(false);
        } else {
            btAccept.setVisibility(View.VISIBLE);
            btDecline.setVisibility(View.VISIBLE);
            btAccept.setEnabled(true);
            btDecline.setEnabled(true);
        }


        //initialise requestqueue
        requestQueue = Volley.newRequestQueue(this);

        ////process the intent here
        event_id = getIntent().getStringExtra(Util.EVENT_IDS);

        context = getApplicationContext();

        SharedPreferences preferences = context.getSharedPreferences(Util.USER_NAME, Context.MODE_PRIVATE);
        user_name = preferences.getString(Util.USER_NAME, "");

    }


    @Override
    protected void onResume() {
        super.onResume();
        getDescription();
    }

    private void getDescription() {
        ////change this line accordingly
        String url = Util.EVENT_DESCIPTION_URL + "?event_id=" + event_id;

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String event_name_received, location_received, date_received, description_received;
                ////parse the JSON object check with braden
                try {
                    event_name_received = response.getString("eventname");
                    location_received = response.getString("location");
                    date_received = response.getString("date");
                    description_received = response.getString("description");

                    ////set the views
                    event_name.setText(event_name_received);
                    location.setText(location_received);
                    date.setText(date_received);
                    description.setText(description_received);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to get description", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        dialog = new ProgressDialog(Event_Description.this, ProgressDialog.THEME_HOLO_DARK);
        dialog.setTitle("Processing");
        dialog.setMessage("Getting description...");
        requestQueue.add(request);
        dialog.show();
        //dialog=ProgressDialog.show(Create_Events.this,"Processing","Creating event...");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event__description, menu);
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
        } else
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    public void onSeeGoing(View view) {
        Intent intent = new Intent(this, Going_listActivity.class);
        intent.putExtra(Util.EVENT_IDS, event_id);
        startActivity(intent);
    }

    public void onAccept(View view) {
        String url = Util.ACCEPT_EVENT_INVITE + "?name=" + user_name + "&eventid=" + event_id;


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (response) {
                    case "success":
                        Toast.makeText(context, "You have joined the event!", Toast.LENGTH_SHORT).show();
                        ///handle internal database;
                        ContentResolver resolver = context.getContentResolver();
                        ContentValues values = new ContentValues();
                        values.put(MeFreeProviderContract.Going.EVENT_ID, event_id);
                        values.put(MeFreeProviderContract.Going.NAME, event_name.getText().toString());
                        values.put(MeFreeProviderContract.Going.LOCATION, location.getText().toString());
                        values.put(MeFreeProviderContract.Going.DATE, date.getText().toString());
                        values.put(MeFreeProviderContract.Going.HOST, "false");

                        resolver.insert(MeFreeProviderContract.Going.CONTENT_URI, values);
                        int i = resolver.delete(MeFreeProviderContract.Invited.CONTENT_URI, MeFreeProviderContract.Invited.EVENT_ID + "=?", new String[]{event_id});

                        Log.i("SOURAV", i + " item deleted");

                        finish();
                        break;
                    case "failure":
                        Toast.makeText(context, "Failed to accept request", Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error occured in accepting request", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }


    public void onDecline(View view) {
        ///handle database internal
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(MeFreeProviderContract.Invited.CONTENT_URI, MeFreeProviderContract.Invited.EVENT_ID + "=?", new String[]{event_id});
        Toast.makeText(context, "Request successfully deleted", Toast.LENGTH_SHORT).show();

        finish();
    }
}

