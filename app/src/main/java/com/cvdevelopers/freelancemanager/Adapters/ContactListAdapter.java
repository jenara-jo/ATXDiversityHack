package com.cvdevelopers.freelancemanager.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvdevelopers.freelancemanager.Model.ContactManager;
import com.cvdevelopers.freelancemanager.Model.EventContact;
import com.cvdevelopers.freelancemanager.R;

import java.util.ArrayList;

/**
 * Created by kamiloveg1 on 9/12/15.
 */
public class ContactListAdapter extends ArrayAdapter<String> {
    private final Context context;
    public ArrayList<String> values;
    private ArrayList<String> contactArray;
    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public ContactListAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.contact_adapter_layout, values);
        this.context = context;
        this.values = values;
    }
    @Override
    public int getCount() {
       return ContactManager.getManager().userContacts.size();
    }

    @Override
    public String getItem(int position) {
//        ContactManager.getManager().userContacts.get(position);
        return "";
    }


    //    @Override
//    public long getItemId(int position) {
//        return values.get(position);
//    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Resources res = this.context.getResources();
        EventContact contact = ContactManager.getManager().userContacts.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        if (convertView == null) {
            // grid = new View(mContext);
            rowView = inflater.inflate(R.layout.contact_adapter_layout, parent, false);

        } else {
            rowView = (View) convertView;
        }

        TextView nameText = (TextView) rowView.findViewById(R.id.contact_adapter_item_name);
        TextView eventText = (TextView) rowView.findViewById(R.id.contact_adapter_item_event);
        ImageView image = (ImageView) rowView.findViewById(R.id.contact_adapter_item_image);
//        TextView statusLabel = (TextView) rowView.findViewById(R.id.table_adapter_statuslabel);
//        TextView waiterNameLabel = (TextView) rowView.findViewById(R.id.table_adapter_waiter);


      nameText.setText(contact.name);
        eventText.setText(contact.eventName);
        image.setImageResource(Integer.parseInt(contact.imageFilePath));
        // Change the icon for Windows and iPhone
        //TODO last checked label and updates.

        return rowView;
    }
}