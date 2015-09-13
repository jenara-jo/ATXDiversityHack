package com.cvdevelopers.freelancemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cvdevelopers.freelancemanager.Helper.JSONParser;
import com.cvdevelopers.freelancemanager.Model.ContactManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,  GoogleApiClient.OnConnectionFailedListener {

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = ContactManager.SERVER_IP_ADDRESS+"webservice/select_course.php";
    private static final String GET_CONTACTS_URL = ContactManager.SERVER_IP_ADDRESS+"webservice/retrieve_contacts.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_LOGIN_MESSAGE = "Login Activity";
    protected void onStart() {
        super.onStart();
       // mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();

//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        setContentView(R.layout.activity_login1);

        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);

        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (ContactManager.USE_DATABASE) {
                    new AttemptLogin().execute();
                } else {
//                    WaiterManagerApp.getManager().loginSuccessful(null);
//                    Intent intent = new Intent(LoginActivity.this, TablesTabActivity.class);
//                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.e("MANAGER", "CONNECTION FAILED " + result);
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.
        Log.e("MANAGER", "CONNECTED GOOGLE");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("MANAGER", "CONNECTED SUSPENDED");
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }
    public void signInClick(View v)
    {
//        Intent i=new Intent(this,MainActivity.class);
//            this.startActivity(i);
    }


    class AttemptLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
//            String username = mEmailView.getText().toString();
//            String password = mPasswordView.getText().toString();
            try {
                // Building Parameters

                JSONObject json = sendHTTPRequest(LOGIN_URL, "course_id", ContactManager.USER_ID + "", "", "");

                // check your log for json response
                // Log.d("Login attempt", json.toString());
                Log.d(TAG_LOGIN_MESSAGE, "Login Successful" + json.toString());
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d(TAG_LOGIN_MESSAGE, "Login Successful" + json.toString());
                    ContactManager.getManager().populateCurrentUser(json);

                    JSONObject jsonContacts = sendHTTPRequest(GET_CONTACTS_URL, "course_id", ContactManager.USER_ID + "", "pending", "0");
                    if (jsonContacts.getInt(TAG_SUCCESS) == 1)
                    {
                        Log.e(TAG_LOGIN_MESSAGE, "Contacts Successful" + jsonContacts.toString());
                        ContactManager.getManager().populateContacts(jsonContacts);
                        Intent i=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(i);
                    }



                    // finish();
//                    startActivity(intent);
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d(TAG_LOGIN_MESSAGE, "Login Failed");
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(LoginActivity.this, String.format("Welcome %s", ContactManager.getManager().currentUser.name), Toast.LENGTH_LONG).show();
            }

        }

        private JSONObject sendHTTPRequest (String url, String valueKey, String value, String valueKey1, String value1)
        {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair(valueKey, value));
            params.add(new BasicNameValuePair(valueKey1, value1));

            // Log.d("request!", "starting");
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(
                    url, "POST", params);
            return json;

        }

    }
}
