package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

/**
 * Created by ASUS on 25/11/2015.
 */
public class sendEventInviteAdapter extends RecyclerView.Adapter<sendEventInviteAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    ArrayList<String> userList;
    Context context;
    RequestQueue requestQueue;
    String eventid;

    String user_name;
    public sendEventInviteAdapter(ArrayList<String>list,Context con,RequestQueue requestQueue,String eventid)
    {
        userList=new ArrayList<String>();
        userList=list;
        context=con;
        this.eventid=eventid;
        inflater=LayoutInflater.from(context);
        this.requestQueue= requestQueue;
        SharedPreferences preferences=context.getSharedPreferences(Util.USER_NAME,Context.MODE_PRIVATE);
        user_name=preferences.getString(Util.USER_NAME,"");
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.single_user, parent, false);
        MyViewHolder viewHolder=new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int pos=position;
        holder.title.setText(userList.get(position));

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageButton imageButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            imageButton=(ImageButton)(itemView.findViewById(R.id.image_button));
            imageButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            String friend_name=userList.get(getPosition());
            String url=Util.INVITE_FRIENDS_TO_EVENTS+"?name="+user_name+"&friendname="+friend_name+"&eventid="+eventid;
            ////check the url
            Log.i("SOURAV","in send invite: "+url);
            StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    switch (response)
                    {
                        case "success":
                            Toast.makeText(context, "Request sent!", Toast.LENGTH_SHORT).show();
                            imageButton.setBackgroundResource(R.drawable.button_tick);
                            break;
                        case "fail":
                            Toast.makeText(context, "Failed to send request.Try again!", Toast.LENGTH_SHORT).show();
                            break;

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context,"Error occured in sending request",Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(request);
        }
    }
}

