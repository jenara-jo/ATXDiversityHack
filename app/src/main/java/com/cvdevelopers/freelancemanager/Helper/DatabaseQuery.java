package com.cvdevelopers.freelancemanager.Helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cvdevelopers.freelancemanager.Model.ContactManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamiloveg1 on 5/2/15.
 */
public class DatabaseQuery extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread Show Progress Dialog
     * */
    boolean failure = false;
    private Context mContext;
    private String query;
    private String pending;
    private boolean connected;
    JSONParser jsonParser = new JSONParser();
    private static final String QUERY_URL = ContactManager.SERVER_IP_ADDRESS+"webservice/retrieve_contacts.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_QUERY_MESSAGE = "Query Attempt";

    public DatabaseQuery( String value, String pending) {
//        this.mContext = mContext;
        this.query = value;
        this.pending = pending;
        this.connected = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... args) {
        // TODO Auto-generated method stub
        // Check for success tag
        int success;
        try {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("course_id", query));
            params.add(new BasicNameValuePair("pending", pending));
            Log.e("request!", "starting");
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(
                    QUERY_URL, "POST", params);

            // check your log for json response
          //  Log.d("Query attempt", json.toString());

            // json success tag
            if (json != null) {
                connected = true;
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d(TAG_QUERY_MESSAGE, "Success");
                    //TODO LO QUE HAY QUE HACER CUANDO ESTUVO BN
                    if (pending.equals("0"))
                    {

                    }
                    return json.toString();
                }else{
                    Log.d(TAG_QUERY_MESSAGE, "Failure");
                    //TODO LO QUE HAY QUE HACER CUANDO ESTUVO MAL
                    return json.toString();

                }
            }
            else
            {
                connected = false;
                //WaiterManagerApp.getManager().showConnectionError();
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
        if (!connected) {
           // WaiterManagerApp.getManager().showConnectionError();
        }
        if (file_url != null)
        {
            //Toast.makeText(mContext, file_url, Toast.LENGTH_LONG).show();
        }

    }

}