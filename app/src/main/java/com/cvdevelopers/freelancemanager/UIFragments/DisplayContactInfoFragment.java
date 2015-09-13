package com.cvdevelopers.freelancemanager.UIFragments;

import android.app.Activity;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cvdevelopers.freelancemanager.Adapters.ContactDisplayAdapter;
import com.cvdevelopers.freelancemanager.Adapters.ContactListAdapter;
import com.cvdevelopers.freelancemanager.Model.ContactClass;
import com.cvdevelopers.freelancemanager.Model.ContactManager;
import com.cvdevelopers.freelancemanager.Model.EventContact;
import com.cvdevelopers.freelancemanager.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayContactInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayContactInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayContactInfoFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private boolean user;
    private int contactIndex;

    private OnFragmentInteractionListener mListener;
    private ContactDisplayAdapter mAdapter;

    private TextView nameTextView;
    private ImageView imageView;
    private ListView listView;
   // private TextView descriptionTextView;
//    private TextView phoneTextView;
//    private TextView emailTextView;
//    private TextView linkedInTextView;
//    private TextView facebookTextView;
//    private TextView eventNameTextView;
//    private TextView addressTextView;
//    private TextView notesTextView;
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayContactInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayContactInfoFragment newInstance(boolean user, int contactIndex) {
        DisplayContactInfoFragment fragment = new DisplayContactInfoFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, user);
        args.putInt(ARG_PARAM2, contactIndex);
        fragment.setArguments(args);
        return fragment;
    }

    public DisplayContactInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getBoolean(ARG_PARAM1);
            contactIndex = getArguments().getInt(ARG_PARAM2);
        }
        ArrayList tableNumberList = new ArrayList<String>();
        ArrayList <InformationValue>infoList = generateInfoList();
        for ( InformationValue contact : infoList)
        {
//            Resources res = getResources();
            tableNumberList.add(contact.value);

        }
        mAdapter = new ContactDisplayAdapter(getActivity(), tableNumberList, infoList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_contact_info, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("DISPLAY", "onAttach");
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
//        if (!user)
//        {
//            nameTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_name);
//            // descriptionTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_description);
//            phoneTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_phone);
//            addressTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_address);
//            emailTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_email);
//            linkedInTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_linkedin);
//            facebookTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_facebook);
//            eventNameTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_event);
//            notesTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_notes);
//            //displayContactInformation(contactIndex);
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.i("DISPLAY", "onDetach");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nameTextView = (TextView) getView().findViewById(R.id.fragment_display_contact_name);
        imageView = (ImageView) getView().findViewById(R.id.fragment_display_contact_image);
        listView = (ListView) getView().findViewById(R.id.contact_info_listview);
      //  mListView = (AbsListView) view.findViewById(android.R.id.list);
        listView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        listView.setOnItemClickListener(this);
       // descriptionTextView = (TextView) getActivity().findViewById(R.id.fragment_display_contact_description);
//        phoneTextView = (TextView) getView().findViewById(R.id.fragment_display_contact_phone);
//        addressTextView = (TextView) getView().findViewById(R.id.fragment_display_contact_address);
//        emailTextView = (TextView) getView().findViewById(R.id.fragment_display_contact_email);
//        linkedInTextView = (TextView)getView().findViewById(R.id.fragment_display_contact_linkedin);
//        facebookTextView = (TextView) getView().findViewById(R.id.fragment_display_contact_facebook);
//        eventNameTextView = (TextView) getView().findViewById(R.id.fragment_display_contact_event);
//        notesTextView = (TextView) getView().findViewById(R.id.fragment_display_contact_notes);
        //if (!ContactManager.getManager().clientArrayList.isEmpty())
        if (user) {
            displayUserInformation();
        }
        else {
            displayContactInformation(contactIndex);
        }
    }

    public void displayContactInformation (int index)
    {
        EventContact contact = ContactManager.getManager().userContacts.get(index);
        Resources res = getResources();
        nameTextView.setText(contact.name);
        imageView.setImageResource(Integer.parseInt(contact.imageFilePath));
     //   descriptionTextView.setText(res.getString(R.string.fragment_add_contact_reference)+": "+contact.reference);
//        phoneTextView.setText(contact.number);
//        addressTextView.setText(contact.address);
//        emailTextView.setText(contact.email);
//        linkedInTextView.setText(contact.linkedInAddress);
//        facebookTextView.setText(contact.facebookLink);
//        eventNameTextView.setText(contact.eventName);
//        notesTextView.setText(contact.contactNotes);
    }
    public void displayUserInformation ()
    {
        ContactClass contact = ContactManager.getManager().currentUser;
        Resources res = getResources();
        nameTextView.setText(contact.name);
        imageView.setImageResource(Integer.parseInt(contact.imageFilePath));
        //   descriptionTextView.setText(res.getString(R.string.fragment_add_contact_reference)+": "+contact.reference);
//        phoneTextView.setText(contact.number);
//        addressTextView.setText(contact.address);
//        emailTextView.setText(contact.email);
//        linkedInTextView.setText(contact.linkedInAddress);
//        facebookTextView.setText(contact.facebookLink);
//        eventNameTextView.setText(contact.);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public ArrayList <InformationValue> generateInfoList()
    {
        ArrayList<InformationValue> informationValues = new ArrayList<>();

        if (user)
        {
            ContactClass currentUser = ContactManager.getManager().currentUser;
//            informationValues.add(new InformationValue(currentUser.name, R.mipmap.));
            informationValues.add(new InformationValue(currentUser.number, R.mipmap.phone));
            informationValues.add(new InformationValue(currentUser.address, R.mipmap.world));
            informationValues.add(new InformationValue(currentUser.email, R.mipmap.mail));
            informationValues.add(new InformationValue(currentUser.linkedInAddress, R.mipmap.social_linkedin));
            informationValues.add(new InformationValue(currentUser.facebookLink, R.mipmap.social_facebook));
        }
        else
        {
            EventContact eventContact = ContactManager.getManager().userContacts.get(contactIndex);
            if (!eventContact.number.equalsIgnoreCase(""))
                informationValues.add(new InformationValue(eventContact.number, R.mipmap.phone));
            if (!eventContact.address.equalsIgnoreCase(""))
                informationValues.add(new InformationValue(eventContact.address, R.mipmap.world));
            if (!eventContact.email.equalsIgnoreCase(""))
                informationValues.add(new InformationValue(eventContact.email, R.mipmap.mail));
            if (!eventContact.linkedInAddress.equalsIgnoreCase(""))
                informationValues.add(new InformationValue(eventContact.linkedInAddress, R.mipmap.social_linkedin));
            if (!eventContact.facebookLink.equalsIgnoreCase(""))
                informationValues.add(new InformationValue(eventContact.facebookLink, R.mipmap.social_facebook));

        }
        return informationValues;
    }
    public class InformationValue
    {
        public String value;
        public int imageResource;

        public InformationValue(String value, int imageResource) {
            this.value = value;
            this.imageResource = imageResource;
        }
    }
}

