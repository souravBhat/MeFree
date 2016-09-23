package gcm.play.android.samples.com.gcmquickstart;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by ASUS on 25/11/2015.
 */
public class NewEventInvitesAdap extends RecyclerView.Adapter<NewEventInvitesAdap.MyViewHolder> {

    private LayoutInflater inflater;
    ArrayList<String> event_ids;
    ArrayList<String> event_names;
    ArrayList<String> event_locations;
    ArrayList<String> event_date_times;

    Context context;
    RequestQueue requestQueue;
    String user_name;

    public NewEventInvitesAdap(Context con) {
        event_ids = new ArrayList<String>();
        event_names = new ArrayList<String>();
        event_locations = new ArrayList<String>();
        event_date_times = new ArrayList<String>();

        context = con;
        inflater = LayoutInflater.from(context);
        requestQueue = Volley.newRequestQueue(context);
        SharedPreferences preferences = context.getSharedPreferences(Util.USER_NAME, Context.MODE_PRIVATE);
        user_name = preferences.getString(Util.USER_NAME, "");

        Cursor cursor = con.getContentResolver().query(MeFreeProviderContract.Invited.CONTENT_URI, MeFreeProviderContract.Invited.PROJECTION_ALL, null, null, null);


        ///get the column index
        int event_id_column = cursor.getColumnIndex(MeFreeProviderContract.Invited.EVENT_ID);
        int event_name_col = cursor.getColumnIndex(MeFreeProviderContract.Invited.NAME);
        int event_location_col = cursor.getColumnIndex(MeFreeProviderContract.Invited.LOCATION);
        int event_date_col = cursor.getColumnIndex(MeFreeProviderContract.Invited.DATE);
        if (cursor.getCount() < 1)
            Toast.makeText(context, "Your have no event invites :(", Toast.LENGTH_SHORT).show();
        else {
            while (cursor.moveToNext()) {
                event_ids.add(cursor.getString(event_id_column));
                event_names.add(cursor.getString(event_name_col));
                event_locations.add(cursor.getString(event_location_col));
                event_date_times.add(cursor.getString(event_date_col));
            }

        }


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.newevent_invite_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int pos = position;
        holder.event_name.setText(event_names.get(position));
        holder.location.setText(event_locations.get(position));
        holder.date.setText(event_date_times.get(position));


    }

    @Override
    public int getItemCount() {
        return event_ids.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView event_name;
        TextView location;
        TextView date;
        ImageButton imageButton_yes;
        ImageButton imageButton_no;

        public MyViewHolder(View itemView) {
            super(itemView);
            event_name = (TextView) itemView.findViewById(R.id.event_name);
            location = (TextView) itemView.findViewById(R.id.location);
            date = (TextView) itemView.findViewById(R.id.date);
            imageButton_yes = (ImageButton) (itemView.findViewById(R.id.image_button_yes));
            imageButton_no = (ImageButton) itemView.findViewById(R.id.image_button_no);
            imageButton_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String unique_id = event_ids.get(getPosition());
                    final String unique_name = event_names.get(getPosition());
                    final String unique_location = event_locations.get(getPosition());
                    final String unique_date = event_date_times.get(getPosition());

                    String url = Util.ACCEPT_EVENT_INVITE + "?name=" + user_name + "&eventid=" + unique_id;


                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            switch (response) {
                                case "success":
                                    Toast.makeText(context, "You have joined the event!", Toast.LENGTH_SHORT).show();
                                    ///handle internal database;
                                    ContentResolver resolver = context.getContentResolver();
                                    ContentValues values = new ContentValues();
                                    values.put(MeFreeProviderContract.Going.EVENT_ID, unique_id);
                                    values.put(MeFreeProviderContract.Going.NAME, unique_name);
                                    values.put(MeFreeProviderContract.Going.LOCATION, unique_location);
                                    values.put(MeFreeProviderContract.Going.DATE, unique_date);
                                    values.put(MeFreeProviderContract.Going.HOST,"false");

                                    resolver.insert(MeFreeProviderContract.Going.CONTENT_URI, values);
                                    int i = resolver.delete(MeFreeProviderContract.Invited.CONTENT_URI, MeFreeProviderContract.Invited.EVENT_ID + "=?", new String[]{unique_id});

                                    Log.i("SOURAV", i + " item deleted");

                                    //////
                                    removeItem(getPosition());
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


            });
            imageButton_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ///handle database internal
                    ContentResolver resolver = context.getContentResolver();
                    resolver.delete(MeFreeProviderContract.Invited.CONTENT_URI, MeFreeProviderContract.Invited.EVENT_ID + "=?", new String[]{event_ids.get(getPosition())});
                    Toast.makeText(context, "Request successfully deleted", Toast.LENGTH_SHORT).show();
                    removeItem(getPosition());
                }


            });

        }

        public void removeItem(int position) {
            event_ids.remove(position);
            event_names.remove(position);
            event_locations.remove(position);
            event_date_times.remove(position);
            notifyItemRemoved(position);
        }

    }
}