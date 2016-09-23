package gcm.play.android.samples.com.gcmquickstart;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class New_Invites extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    ListView listView;
    TextView empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__invites);

       // recyclerView=(RecyclerView)findViewById(R.id.eventinvites_recyclerview);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
       // recyclerView.setAdapter(new NewEventInvitesAdap(this));

        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event Invites");

        listView=(ListView)findViewById(R.id.eventsInvitedTo);
        empty=(TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(empty);


        ///////

        //////
    }


    @Override
    protected void onResume() {
        super.onResume();
        ContentResolver resolver=getContentResolver();
        Cursor cursor=resolver.query(MeFreeProviderContract.Invited.CONTENT_URI,MeFreeProviderContract.Invited.PROJECTION_ALL,null,null,null);

        /////populate the listview

        int event_id=cursor.getColumnIndex(MeFreeProviderContract.Invited.EVENT_ID);
        int nameIndex=cursor.getColumnIndex(MeFreeProviderContract.Invited.NAME);
        int locationIndex=cursor.getColumnIndex(MeFreeProviderContract.Invited.LOCATION);
        int dateIndex=cursor.getColumnIndex(MeFreeProviderContract.Invited.DATE);

        /////get the array of event_ids
        final ArrayList<String> event_ids=new ArrayList<String>();
        String from[]={MeFreeProviderContract.Invited.NAME,MeFreeProviderContract.Invited.LOCATION,MeFreeProviderContract.Invited.DATE};
        int [] to ={R.id.event_name,R.id.location,R.id.date};


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
                intent.putExtra(Util.DESCRIPTION_TYPE,"new");
                intent.putExtra(Util.EVENT_IDS,event_ids.get(i));
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new__invites, menu);
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
        {
            startActivity(new Intent(this,Dash_board.class));
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
