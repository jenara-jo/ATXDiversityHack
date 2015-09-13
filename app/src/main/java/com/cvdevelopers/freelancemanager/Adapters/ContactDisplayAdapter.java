package com.cvdevelopers.freelancemanager.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvdevelopers.freelancemanager.R;
import com.cvdevelopers.freelancemanager.UIFragments.DisplayContactInfoFragment;

import java.util.ArrayList;

/**
 * Created by kamiloveg1 on 9/13/15.
 */
public class ContactDisplayAdapter extends ArrayAdapter<String> {
    private final Context context;
    public ArrayList<String> values;
    private ArrayList<DisplayContactInfoFragment.InformationValue> contactInfoArray;



    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public ContactDisplayAdapter(Context context, ArrayList<String> values, ArrayList<DisplayContactInfoFragment.InformationValue> informationValues) {
        super(context, R.layout.contact_display_segment, values);
        this.context = context;
        this.values = values;
        this.contactInfoArray = informationValues;
    }
    @Override
    public int getCount() {
        return contactInfoArray.size();
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
        DisplayContactInfoFragment.InformationValue contact = contactInfoArray.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        if (convertView == null) {
            // grid = new View(mContext);
            rowView = inflater.inflate(R.layout.contact_display_segment, parent, false);

        } else {
            rowView = (View) convertView;
        }

        TextView text = (TextView) rowView.findViewById(R.id.contact_display_segment_text);
        ImageView image = (ImageView) rowView.findViewById(R.id.contact_display_segment_image);
//        TextView eventText = (TextView) rowView.findViewById(R.id.contact_adapter_item_event);
////        TextView statusLabel = (TextView) rowView.findViewById(R.id.table_adapter_statuslabel);
////        TextView waiterNameLabel = (TextView) rowView.findViewById(R.id.table_adapter_waiter);
//
//
//        nameText.setText(contact.name);
//        eventText.setText(contact.eventName);
        text.setText(contact.value);
        image.setImageResource(contact.imageResource);
        // Change the icon for Windows and iPhone
        //TODO last checked label and updates.

        return rowView;
    }
}