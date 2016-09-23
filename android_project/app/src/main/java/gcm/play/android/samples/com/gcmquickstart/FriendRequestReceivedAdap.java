package gcm.play.android.samples.com.gcmquickstart;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
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
 * Created by ASUS on 23/11/2015.
 */
public class FriendRequestReceivedAdap extends RecyclerView.Adapter<FriendRequestReceivedAdap.MyViewHolder> {

    private LayoutInflater inflater;
    ArrayList<String> userList;
    Context context;
    RequestQueue requestQueue;
    String user_name;

    public FriendRequestReceivedAdap(Context con) {
        userList = new ArrayList<String>();
        context = con;
        inflater = LayoutInflater.from(context);
        requestQueue = Volley.newRequestQueue(context);
        SharedPreferences preferences = context.getSharedPreferences(Util.USER_NAME, Context.MODE_PRIVATE);
        user_name = preferences.getString(Util.USER_NAME, "");

        Cursor cursor = con.getContentResolver().query(MeFreeProviderContract.FriendRequests.CONTENT_URI, MeFreeProviderContract.FriendRequests.PROJECTION_ALL, null, null, null);
        ArrayList<String> list = new ArrayList<String>();
        int name_column = cursor.getColumnIndex(MeFreeProviderContract.FriendRequests.NAME);
        if (cursor.getCount() < 1)
            Toast.makeText(context, "Your have no requests :(", Toast.LENGTH_SHORT).show();
        else {
            while (cursor.moveToNext()) {
                list.add(cursor.getString(name_column));
            }
            userList = list;
        }


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.friend_request_received_singleitem, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int pos = position;
        holder.title.setText(userList.get(position));

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageButton imageButton_yes;
        ImageButton imageButton_no;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.friend_name);
            imageButton_yes = (ImageButton) (itemView.findViewById(R.id.image_button_yes));
            imageButton_no = (ImageButton) itemView.findViewById(R.id.image_button_no);
            imageButton_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String friend_name = userList.get(getPosition());
                    String url = Util.ACCEPT_FRIEND_REQUEST + "?name=" + user_name + "&friendname=" + friend_name;
                    Log.i("SOURAV", "clicked accept");


                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("SOURAV", "clicked accept:" +response);
                            switch (response) {
                                case "success":
                                    Toast.makeText(context, "You two are now friends!", Toast.LENGTH_SHORT).show();
                                    ///handle internal database;
                                    ContentResolver resolver = context.getContentResolver();
                                    ContentValues values = new ContentValues();
                                    values.put(MeFreeProviderContract.Friends.NAME, friend_name);
                                    resolver.insert(MeFreeProviderContract.Friends.CONTENT_URI, values);
                                    int i= resolver.delete(MeFreeProviderContract.FriendRequests.CONTENT_URI, MeFreeProviderContract.FriendRequests.NAME + "=?", new String[]{friend_name});
                                    Log.i("SOURAV", i+" item deleted");

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
                    final String friend_name = userList.get(getPosition());
                    ///handle database internal
                    ContentResolver resolver = context.getContentResolver();
                    resolver.delete(MeFreeProviderContract.FriendRequests.CONTENT_URI, MeFreeProviderContract.FriendRequests.NAME + "=?", new String[]{friend_name});
                    Toast.makeText(context, "Request successfully deleted", Toast.LENGTH_SHORT).show();
                    removeItem(getPosition());
                }


            });

        }

        public void removeItem(int position)
        {
            userList.remove(position);
            notifyItemRemoved(position);
        }

    }
}