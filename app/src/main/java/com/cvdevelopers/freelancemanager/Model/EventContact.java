package com.cvdevelopers.freelancemanager.Model;

/**
 * Created by kamiloveg1 on 9/12/15.
 */
public class EventContact extends ContactClass {

    public String eventName;
    public String contactNotes;


    public EventContact(String name, String reference, String number, String email, String address, String imageFilePath, String linkedInAddress, String facebookLink, int userID, String eventName, String contactNotes) {
        super(name, reference, number, email, address, imageFilePath, linkedInAddress, facebookLink, userID);
        this.eventName = eventName;
        this.contactNotes = contactNotes;
    }
}
