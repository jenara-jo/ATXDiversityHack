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
public class InsertValueDatabase extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread Show Progress Dialog
     * */
    Context mContext;
    private String query;
    private boolean connected;

    JSONParser jsonParser = new JSONParser();
    private static final String QUERY_URL = ContactManager.SERVER_IP_ADDRESS+"restaurant/insertDB.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private boolean sentPrintKitchen;
    public InsertValueDatabase(Context mContext, String query) {
        this.mContext = mContext;
        this.query = query;
    }
    public InsertValueDatabase( String query) {
        this.query = query;
        this.sentPrintKitchen = false;
        connected = false;
    }
    public InsertValueDatabase( String query, boolean sentPrintKitchen) {
        this.query = query;
        this.sentPrintKitchen = sentPrintKitchen;
        connected = false;
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
            params.add(new BasicNameValuePair("query", query));
            if (sentPrintKitchen)
            {
                params.add(new BasicNameValuePair("print", "1"));
            }
            Log.d("request!", "starting");
            // getting product details by making HTTP request
            JSONObject json = jsonParser.makeHttpRequest(
                    QUERY_URL, "POST", params);

            // check your log for json response
            Log.d("Query attempt", json.toString());

            // json success tag
            success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                Log.d("Sending Successful!", json.toString());
                //TODO LO QUE HAY QUE HACER CUANDO ESTUVO BN
                return json.getString(TAG_MESSAGE);
            }else{
                Log.d("Sending Failure!", json.getString(TAG_MESSAGE));
                //TODO LO QUE HAY QUE HACER CUANDO ESTUVO MAL
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
        if (sentPrintKitchen)
        {
            //WaiterManagerApp.getManager().showToastWithMessage("Ticket Printed");
        }
        if (file_url != null)
        {
//            Toast.makeText(mContext, file_url, Toast.LENGTH_LONG).show();
        }

    }
}