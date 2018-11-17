package ca.bcit.snaydon_dai_assign2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Child {
    private int ID;
    private String FirstName;
    private String LastName;
    private String BirthDate;
    private String Street;
    private String City;
    private String Province;
    private String PostalCode;
    private String Country;
    private double Latitude;
    private double Longitude;
    private Boolean IsNaughty;
    private String DateCreated;

    public static Child[] CHILDREN = {
            new Child("Eric", "Last", "2011-11-11","3700 Willingdon Ave", "Burnaby", "BC","V5G 3H2","Canada",49,-123,true),
            new Child("Timothy", "Last", "2011-11-11","3700 Willingdon Ave", "Burnaby", "BC","V5G 3H2","Canada",49.12,-123.59,false),
            new Child("Sarah", "Last", "2011-11-11","3700 Willingdon Ave", "Burnaby", "BC","V5G 3H2","Canada",49,-123,true),
            new Child("Tom", "Last", "2011-11-11","3700 Willingdon Ave", "Burnaby", "BC","V5G 3H2","Canada",49,-123,false),
            new Child("Jeff", "Last", "2011-11-11","3700 Willingdon Ave", "Burnaby", "BC","V5G 3H2","Canada",49,-123,true),

    };

    public Child(String firstName, String lastName, String birthDate, String street, String city, String province, String postalCode, String country, double latitude, double longitude, Boolean isNaughty) {
        FirstName = firstName;
        LastName = lastName;
        BirthDate = birthDate;
        Street = street;
        City = city;

        Province = province;
        PostalCode = postalCode;
        Country = country;
        Latitude = latitude;
        Longitude = longitude;
        IsNaughty = isNaughty;
        String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DateCreated = currentDateandTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(int latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(int longitude) {
        Longitude = longitude;
    }

    public Boolean getNaughty() {
        return IsNaughty;
    }

    public void setNaughty(Boolean naughty) {
        IsNaughty = naughty;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }
}
