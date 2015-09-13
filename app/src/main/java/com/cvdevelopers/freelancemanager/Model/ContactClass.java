package com.cvdevelopers.freelancemanager.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kamiloveg1 on 8/31/15.
 */
public class ContactClass implements Serializable{

    private static final long serialVersionUID = 100L;
//   public static enum ContactType
//    {
//        CUSTOMER,
//        PROVIDER
//
//    }
    public String name;
    public String reference;
    public String number;
    public String email;
    public String address;
    public String imageFilePath;
    public String linkedInAddress;
    public String facebookLink;
    public int userID;
    public ArrayList<ShareProfiles> shareProfiles;
//    public ContactType contactType;

    public ContactClass(String name, String reference, String number, String email, String address, String imageFilePath, String linkedInAddress, String facebookLink, int userID) {
        this.name = name;
        this.reference = reference;
        this.number = number;
        this.email = email;
        this.address = address;
        this.imageFilePath = imageFilePath;
        this.linkedInAddress = linkedInAddress;
        this.facebookLink = facebookLink;
        this.userID = userID;
    }


    //TODO public ArrayList<> clientAppointments;

}
