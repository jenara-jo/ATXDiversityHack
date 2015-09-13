package com.cvdevelopers.freelancemanager.Model;

/**
 * Created by kamiloveg1 on 9/12/15.
 */
public class ShareProfiles {
    public int userID;
    public int profileID;
    public String profileName;
    public boolean displayName;
    public boolean displayPhone;
    public boolean displayWeb;
    public boolean displayEmail;
    public boolean displayLinkedIn;
    public boolean displayFacebook;

    public ShareProfiles(int userID, int profileID, String profileName, boolean displayName, boolean displayPhone, boolean displayWeb, boolean displayEmail, boolean displayLinkedIn, boolean displayFacebook) {
        this.userID = userID;
        this.profileID = profileID;
        this.profileName = profileName;
        this.displayName = displayName;
        this.displayPhone = displayPhone;
        this.displayWeb = displayWeb;
        this.displayEmail = displayEmail;
        this.displayLinkedIn = displayLinkedIn;
        this.displayFacebook = displayFacebook;
    }
}
