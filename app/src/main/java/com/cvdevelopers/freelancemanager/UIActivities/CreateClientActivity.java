package com.cvdevelopers.freelancemanager.UIActivities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.cvdevelopers.freelancemanager.Model.ContactManager;
import com.cvdevelopers.freelancemanager.R;

public class CreateClientActivity extends AppCompatActivity {


    private static final int CONTACT_PICKER_RESULT = 1001;
    EditText name;
    EditText reference;
    EditText phone;
    EditText email;
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client);

//        name = (EditText) findViewById(R.id.create_client_name);
//        reference = (EditText) findViewById(R.id.create_client_reference);
//        phone = (EditText) findViewById(R.id.create_client_phone);
//        address = (EditText) findViewById(R.id.create_client_address);
//        email = (EditText) findViewById(R.id.create_client_email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.create_client_contact_list) {
            onPickContact();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPickContact() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor c=null;
                    try {
                        Uri result = data.getData();
                        // get the contact id from the Uri
                        String id = result.getLastPathSegment();
                        c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { id }, null);
                        if (c.moveToFirst()) {
                            String nombre1 = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            name.setText(nombre1.trim());
                            String number1=c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            number1.replaceAll(" ", "");
                            phone.setText(number1.trim());
                        }
                        c = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[] { id }, null);
                        if (c.moveToFirst()) {
                            String mail1 = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DATA)).trim();
                            email.setText(mail1);
                        }
                        c = getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + "=?", new String[] { id }, null);
                        if (c.moveToFirst()) {
                            String address1 = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.DATA)).trim();
                            address.setText(address1);
                        }
                    }
                    catch (Exception e) {
                        Log.e(ContactManager.DEBUG_TAG, "Failed to get email data", e);
                    }
                    finally {
                        if (c != null) {
                            c.close();
                        }
                    }

                    break;
            }

        } else {
            // Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }
    }
}
