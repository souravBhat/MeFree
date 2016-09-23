package gcm.play.android.samples.com.gcmquickstart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginScreen extends AppCompatActivity {


    EditText user_name,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        user_name=(EditText)findViewById(R.id.editText_user_name);
        password=(EditText)findViewById(R.id.editText_password);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_screen, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        SharedPreferences sharedPreferences=getSharedPreferences(Util.PROPERTY_REG_ID, MODE_PRIVATE);
        String reg_id=sharedPreferences.getString(Util.PROPERTY_REG_ID,"");
        Log.i("SOURAV", reg_id);
//////check for empty username.....please!
        String user=user_name.getText().toString();
        String user_password=password.getText().toString();
        Log.i("SOURAV", user+" "+user_password);
        if(user!="" && user_password!="")
        {
            String url=Util.register_url+"?name="+user_name.getText().toString()+"&gcm_regid="+reg_id+"&password="+user_password;

            //String url="http://www.google.com";
            Log.i("SOURAV", url);

            RequestQueue requestQueue=Volley.newRequestQueue(this);
            StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("SOURAV", "in response"+response);
                    if(response.equals("exists"))
                    {
                        Toast.makeText(getApplicationContext(),"User id already exists, please choose another", Toast.LENGTH_SHORT).show();
                    }
                    else if(response.equals("success"))
                    {
                        Toast.makeText(getApplicationContext(),"Successfully registered. Have fun!", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences=getSharedPreferences(Util.USER_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString(Util.USER_NAME,user_name.getText().toString());
                        editor.commit();
                        setUserLoggedIn();
                        startActivity(new Intent(LoginScreen.this, Dash_board.class));
                        finish();
                    }
                    else if(response.equals("login_success"))
                    {
                        SharedPreferences preferences=getSharedPreferences(Util.USER_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString(Util.USER_NAME,user_name.getText().toString());
                        editor.commit();
                        setUserLoggedIn();
                        startActivity(new Intent(LoginScreen.this, Dash_board.class));
                        finish();
                    }
                    else if(response.equals("login_fail"))
                    {
                        Toast.makeText(getApplicationContext(),"Login password wrong",Toast.LENGTH_SHORT).show();
                    }
                    else if(response.equals("fails"))
                    {
                        Toast.makeText(getApplicationContext(),"Failed to register",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("SOURAV","Error");
                }
            });
            requestQueue.add(stringRequest);
            Log.i("SOURAV","posted request");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please enter both password and username",Toast.LENGTH_SHORT).show();

        }

    }

    private void setUserLoggedIn() {
        SharedPreferences preferences=getSharedPreferences(Util.USER_LOGGEDIN,MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(Util.USER_LOGGEDIN,"true");
        editor.apply();
    }
}
