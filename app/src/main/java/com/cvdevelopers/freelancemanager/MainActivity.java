package com.cvdevelopers.freelancemanager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cvdevelopers.freelancemanager.Helper.DatabaseQuery;
import com.cvdevelopers.freelancemanager.Helper.JSONParser;
import com.cvdevelopers.freelancemanager.Model.ContactManager;
import com.cvdevelopers.freelancemanager.UIFragments.AddContactFragment;
import com.cvdevelopers.freelancemanager.UIFragments.BeginConnectionFragment;
import com.cvdevelopers.freelancemanager.UIFragments.DisplayContactInfoFragment;
import com.cvdevelopers.freelancemanager.UIFragments.ItemFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AddContactFragment.OnFragmentInteractionListener, DisplayContactInfoFragment.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener , BeginConnectionFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    public AddContactFragment addContactFragment;
    public DisplayContactInfoFragment displayContactInfoFragment;
    public ItemFragment itemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.addContactFragment = new AddContactFragment();
        this.displayContactInfoFragment = DisplayContactInfoFragment.newInstance(true,0);
        this.itemFragment = new ItemFragment();
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        try {
            JSONObject jsonContacts = new JSONObject(new DatabaseQuery(ContactManager.USER_ID+"", "0").execute().get());
            ContactManager.getManager().populateContacts(jsonContacts);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position){
            case 0 :
                fragmentManager.beginTransaction()
                        .replace(R.id.container, itemFragment)
                        .commit();

                break;
            case 1 :

                fragmentManager.beginTransaction()
                        .replace(R.id.container, displayContactInfoFragment)
                        .commit();
                break;
            case 2 :
                fragmentManager.beginTransaction()
                        .replace(R.id.container, addContactFragment)
                        .commit();

                break;
            default:

                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
                break;
        }

    }

    @Override
    public void onNavigationMenuItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_example:
                new AttemptLogin().execute();
        }
    }

    @Override
    public void onRequestConnection() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container,new BeginConnectionFragment())
//                .addToBackStack("Drawer")
//                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
    public void refreshContactList()
    {
        try {
            JSONObject jsonContacts = new JSONObject(new DatabaseQuery(ContactManager.USER_ID+"", "0").execute().get());
            ContactManager.getManager().populateContacts(jsonContacts);
            itemFragment.refreshList();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(int id) {


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, DisplayContactInfoFragment.newInstance(false, id), "contact_fragment_" + id)
                .addToBackStack("Drawer")
                .commit();
    }

    @Override
    public void onFragmentInteractionRequestConnection(Uri uri) {

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

        }
    }


    private ProgressDialog pDialog;
    private static final String REQUEST_CONNECTION_URL = ContactManager.SERVER_IP_ADDRESS+"webservice/request_connection.php";

    JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_LOGIN_MESSAGE = "Login Activity";

    class AttemptLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Searching Contact...");
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

                JSONObject json = sendHTTPRequest(REQUEST_CONNECTION_URL, "course_id", ContactManager.USER_ID + "");

                // check your log for json response
                // Log.d("Login attempt", json.toString());
                Log.d(TAG_LOGIN_MESSAGE, "Login Successful" + json.toString());
                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d(TAG_LOGIN_MESSAGE, "PAIRING Successful" + json.toString());
//                    ContactManager.getManager().populateCurrentUser(json);
//
//                    JSONObject jsonContacts = sendHTTPRequest(GET_CONTACTS_URL, "course_id", ContactManager.USER_ID + "");
//                    if (jsonContacts.getInt(TAG_SUCCESS) == 1)
//                    {
//                        Log.e(TAG_LOGIN_MESSAGE, "Contacts Successful" + jsonContacts.toString());
//                        ContactManager.getManager().populateContacts(jsonContacts);
//                        Intent i=new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(i);
//                    }
//
//
//
//                    // finish();
////                    startActivity(intent);

                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d(TAG_LOGIN_MESSAGE, "PAIRING Failed");
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
            refreshContactList();
            if (file_url != null){
                Toast.makeText(MainActivity.this, String.format("Welcome %s", ContactManager.getManager().currentUser.name), Toast.LENGTH_LONG).show();
            }

        }

        private JSONObject sendHTTPRequest (String url, String valueKey, String value)
        {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
//                params.add(new BasicNameValuePair("username", username));
            params.add(new BasicNameValuePair(valueKey, value));

            // Log.d("request!", "starting");
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(
                    url, "POST", params);
            return json;

        }

    }

}
