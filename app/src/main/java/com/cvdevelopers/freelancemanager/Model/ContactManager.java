package com.cvdevelopers.freelancemanager.Model;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.cvdevelopers.freelancemanager.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by kamiloveg1 on 8/31/15.
 */
public class ContactManager extends Application {

    public ArrayList<ContactClass> clientArrayList;
    protected static final String FILE_PATH = "/sdcard/FreelancerManagerApp/Clients.cvd";
    public static final String SERVER_IP_ADDRESS = "http://192.168.1.123:1234/";
    public static boolean USE_DATABASE = true;
    public static final String DEBUG_TAG = "Freelance Tag";
    public static final int USER_ID = 1;

    private static ContactManager instance;
    private static Context context;
    public ContactClass currentUser;
    public ArrayList <EventContact> userContacts;
    public ArrayList <EventContact> pendingUserContacts;
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        this.context = getApplicationContext();
        this.loadDataFromFile();
        this.currentUser = new ContactClass("", "","", "", "", "","", "", 1);
        this.userContacts = new ArrayList<>();
        this.pendingUserContacts = new ArrayList<>();
        //createDummyContacts();
    }

    public static ContactManager getManager() {
        return instance;
    }

    public void loadDataFromFile() {
       //TODO load appointments// appointments = new ArrayList<Appointments>( );
        String [] f= FILE_PATH.split("/");
        String path="";
        for (int i=1;i<f.length-1;i++){
            path+="/"+f[i];
        }
        File fileClient = new File( FILE_PATH );
        if( fileClient.exists( ) ){
            try {
                ObjectInputStream ois = new ObjectInputStream( new FileInputStream( FILE_PATH ) );
                clientArrayList = ( ArrayList<ContactClass> )ois.readObject( );
                //TODO load appointments appointments = ( ArrayList<Appointments> )ois.readObject( );
                ois.close( );
                Log.i("CONTACT", "Client List Loaded");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else{
            // El archivo no existe: es la primera vez que se ejecuta el programa
            File directorios = new File( path );
            directorios.mkdirs();
            clientArrayList = new ArrayList<ContactClass>();
            this.saveClientManager();
           //TODO load Appointments appointments = new ArrayList<Appointments>( );

        }
    }
    public void saveClientManager( )
    {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream( new FileOutputStream( FILE_PATH ));
            oos.writeObject( clientArrayList );
            //TODO save appointments oos.writeObject( appointments );
            oos.close( );
            Log.i("CONTACT", "Client List Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addClient(String name, String reference,String number, String email, String address,  String imageFilePath){
        //TODO check if the client does not exist

    }
    private void createDummyContacts ()
    {
        for (int i = 0; i < 5; i++) {
            Resources res = getResources();
            EventContact dummy = new EventContact(res.getStringArray(R.array.dummy_contact_names)[i], "",""+i,"sldjfa@asldfj.com", "700 Brazos", "","linkedin.com/in/alsdjf","facebook.com/asdhf", i,res.getStringArray(R.array.dummy_contact_events)[i],"Highschool student intersted in JAVA");
            userContacts.add(dummy);
        }
    }
    public void populateCurrentUser(JSONObject json)
    {

        try {
            JSONObject jsonObject = (JSONObject) json.getJSONArray("message").get(0);

            currentUser.name = jsonObject.getString("user_name");
            currentUser.number =  jsonObject.getString("user_phone_num");
            currentUser.address = jsonObject.getString("user_web_url");
            currentUser.email = jsonObject.getString("user_email");
            currentUser.linkedInAddress = jsonObject.getString("user_linkedin");
            currentUser.facebookLink = jsonObject.getString("user_facebook");
            currentUser.imageFilePath = R.mipmap.profile_5+"";
            Log.e("MANAGER", "name is :"+currentUser.name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populateContacts(JSONObject json) {
        userContacts.clear();
        try {
            JSONArray array = json.getJSONArray("message");
            for (int i = 0; i < array.length() && i < 5; i++) {
                JSONObject contact = (JSONObject) array.get(i);
                EventContact dummy = new EventContact(contact.getString("user_name"),"", contact.getString("user_phone_num"), contact.getString("user_email"), contact.getString("user_web_url"), (R.mipmap.profile_1+i)+"",
                contact.getString("user_linkedin"), contact.getString("user_facebook"), contact.getInt("user_id"), contact.getString("connection_event"), contact.getString("connection_notes"));
                //new EventContact(contact.getString(""), "",""+i,"sldjfa@asldfj.com", "700 Brazos", "","linkedin.com/in/alsdjf","facebook.com/asdhf", i,res.getStringArray(R.array.dummy_contact_events)[i],"Highschool student intersted in JAVA");
                userContacts.add(dummy);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void populatePendingContacts(JSONObject json) {
        try {
            JSONArray array = json.getJSONArray("message");
            for (int i = 0; i < array.length() && i < 5; i++) {
                JSONObject contact = (JSONObject) array.get(i);
                EventContact dummy = new EventContact(contact.getString("user_name"),"", contact.getString("user_phone_num"), contact.getString("user_email"), contact.getString("user_web_url"), (R.mipmap.profile_1+i)+"",
                        contact.getString("user_linkedin"), contact.getString("user_facebook"), contact.getInt("user_id"), contact.getString("connection_event"), contact.getString("connection_notes"));
                //new EventContact(contact.getString(""), "",""+i,"sldjfa@asldfj.com", "700 Brazos", "","linkedin.com/in/alsdjf","facebook.com/asdhf", i,res.getStringArray(R.array.dummy_contact_events)[i],"Highschool student intersted in JAVA");
                pendingUserContacts.add(dummy);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void refreshUserList()
    {

    }
}
