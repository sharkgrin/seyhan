package models;

/**
 * Created by cangulse on 4.07.2017.
 */
public class ContactModelApi extends Contact {

    public static Finder<Integer, Contact> find = new Contact.Finder<>(Integer.class, Contact.class);

}
