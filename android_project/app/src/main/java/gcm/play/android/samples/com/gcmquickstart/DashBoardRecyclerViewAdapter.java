package gcm.play.android.samples.com.gcmquickstart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ASUS on 23/11/2015.
 */
public class DashBoardRecyclerViewAdapter extends RecyclerView.Adapter<DashBoardRecyclerViewAdapter.MyHolder> {

    LayoutInflater inflater;
    ArrayList<DashBoard_item> items;
    Context context;
    public  DashBoardRecyclerViewAdapter(Context context)
    {
        this.context=context;
        inflater= LayoutInflater.from(context);
        items=new ArrayList<DashBoard_item>();
        items.add(new DashBoard_item("Add a friend",R.drawable.add_friend));
        items.add(new DashBoard_item("Current Friends",R.drawable.current_friends));
        items.add(new DashBoard_item("Create Events",R.drawable.create_event));


    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.drawerlayout_itemview,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.icon.setImageResource(items.get(position).getImageId());
        holder.listText.setText(items.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView listText;
        public MyHolder(View itemView) {
            super(itemView);
            icon=(ImageView)itemView.findViewById(R.id.listIcon);
            listText=(TextView)itemView.findViewById(R.id.listText);
            listText.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int position=getPosition();
            switch (position)
            {
                case 0:
                    context.startActivity(new Intent(context,AddFriend.class));
                    break;
                case 1:
                    context.startActivity(new Intent(context,Current_Friends.class));
                    break;
                case 2:
                    context.startActivity(new Intent(context,Create_Events.class));
                    break;

            }

        }
    }
}
