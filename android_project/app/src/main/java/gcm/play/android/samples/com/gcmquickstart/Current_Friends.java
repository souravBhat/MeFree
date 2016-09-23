package gcm.play.android.samples.com.gcmquickstart;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Current_Friends extends AppCompatActivity {

    Toolbar toolbar ;
    ListView listView;
    TextView empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current__friends);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        listView=(ListView)findViewById(R.id.currentFriends);
        empty=(TextView)findViewById(android.R.id.empty);
        listView.setEmptyView(empty);




    }

    @Override
    protected void onResume() {
        super.onResume();
        String from[]={MeFreeProviderContract.Friends.NAME};
        int [] to ={R.id.friend_name};

        ContentResolver resolver=getContentResolver();
        Cursor cursor=resolver.query(MeFreeProviderContract.Friends.CONTENT_URI,MeFreeProviderContract.Friends.PROJECTION_ALL,null,null,null);
        Log.i("SOURAV", ""+cursor.getCount());

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Current Friends");

        ////populate the list
        ListAdapter adapter=new SimpleCursorAdapter(getApplicationContext(),R.layout.current_friends_item,cursor,from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_current__friends, menu);
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
