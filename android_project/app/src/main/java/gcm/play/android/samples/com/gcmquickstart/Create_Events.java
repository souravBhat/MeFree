package gcm.play.android.samples.com.gcmquickstart;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.Dialog;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;

public class Create_Events extends AppCompatActivity {

    Toolbar toolbar;

    EditText eTName,eTDesc,eTLocation;
    RequestQueue requestQueue;
    ProgressDialog dialog;

    TextView tvDisplayTime, tvDisplayDate;
    int hour, minute, year, month, day;
    static final int TIME_DIALOG_ID = 999;
  static final int DATE_DIALOG_ID = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__events);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Events");


        ///get requestqueue
        requestQueue= Volley.newRequestQueue(this);
        ////chain to the views

        eTName=(EditText)findViewById(R.id.eTName);
        eTLocation=(EditText)findViewById(R.id.eTLocation);
        eTDesc=(EditText)findViewById(R.id.eTDesc);
       // ListView createeventlistview = (ListView) findViewById(R.id.createeventlistview);
        tvDisplayDate = (TextView) findViewById(R.id.tvDisplayDate);
        tvDisplayTime = (TextView) findViewById(R.id.tvDisplayTime);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        addListenerOnDateTextView();
        addListenerOnTimeTextView();
    }

    public void addListenerOnTimeTextView() {
        tvDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
    }
    public void addListenerOnDateTextView() {
        tvDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this, TimePickerDialog.THEME_HOLO_DARK,
                        timePickerListener, hour, minute,false);
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_DARK, datePickerListener,
                        year, month,day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            // set selected date into textview
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year));
        }
    };
    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    // set current time into textview
                    tvDisplayTime.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));
                }
            };
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create__events, menu);
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

    public void createEvent(View view) {
        final String dateAndTime=tvDisplayDate.getText().toString()+","+tvDisplayTime.getText().toString();
        final String name=eTName.getText().toString();
        final String description=eTDesc.getText().toString();
        final String location=eTLocation.getText().toString();
        SharedPreferences preferences=getSharedPreferences(Util.USER_NAME, MODE_PRIVATE);
        String username=preferences.getString(Util.USER_NAME,"");

        String url=Util.CREATE_EVENTS_URL+"?event_name="+name+"&username="+username+"&description="+description+"&time="+dateAndTime+"&location="+location;///ask braden
       //////////////////////////check the url
        url=url.replace(" ","%20%");
        Log.i("SOURAV",url);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String successOrFail=response.getString("SuccessOrFail");
                    switch(successOrFail)
                    {
                        case "success":
                            String eventid=response.getString("id");///braden

                            ContentResolver resolver=getContentResolver();
                            ContentValues values=new ContentValues();
                            values.put(MeFreeProviderContract.Going.NAME,name);
                            values.put(MeFreeProviderContract.Going.EVENT_ID,eventid);
                            values.put(MeFreeProviderContract.Going.LOCATION,location);
                            values.put(MeFreeProviderContract.Going.DATE, dateAndTime);
                            values.put(MeFreeProviderContract.Going.HOST, "true");

                            resolver.insert(MeFreeProviderContract.Going.CONTENT_URI,values);

                            tvDisplayDate.setText("DATE");
                            tvDisplayTime.setText("TIME");
                            eTDesc.setText("");
                            eTName.setText("");
                            eTLocation.setText("");
                            dialog.dismiss();
                            Toast.makeText(Create_Events.this, "Event successfully created. Add Friends to the event!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),sendInviteToFriends.class);
                            intent.putExtra(Util.EVENT_IDS,eventid);
                            startActivity(intent);
                            finish();
                            break;
                        case "failure":
                            dialog.dismiss();
                            Toast.makeText(Create_Events.this, "Event failed to be created", Toast.LENGTH_SHORT).show();
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(Create_Events.this, "Event could not be created. Error!", Toast.LENGTH_SHORT).show();

            }
        });



        dialog = new ProgressDialog(Create_Events.this, ProgressDialog.THEME_HOLO_DARK);
        dialog.setTitle("Processing");
        dialog.setMessage("Creating event...");
        requestQueue.add(request);
        dialog.show();
        //dialog=ProgressDialog.show(Create_Events.this,"Processing","Creating event...");

    }
}
