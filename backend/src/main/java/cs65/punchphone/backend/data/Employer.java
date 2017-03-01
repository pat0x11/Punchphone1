package cs65.punchphone.backend.data;

/**
 * Created by Jack on 2/28/17.
 */

//the class of Employer Objects
public class Employer {

    //used for accessing the datastore
    public static final String PARENT_ENTITY_NAME="employerParent";
    public static final String PARENT_ENTITY_KEY="employerParent";

    //for the entry itself
    public static final String EMPLOYER_ENTITY_NAME="employerEntry";
    public static final String EMPLOYER_USERNAME="employerUsername";
    public static final String EMPLOYER_PASSWORD="employerPassword";
    public static final String EMPLOYER_COMPANY="employerCompany";
    public static final String EMPLOYER_STREET="employerStreet";
    public static final String EMPLOYER_CITY="employerCity";
    public static final String EMPLOYER_STATE="employerState";
    public static final String EMPLOYER_ZIPCODE="employerZipcode";
    public static final String EMPLOYER_RADIUS="employerRadius";

    //the object variables
    public String mUsername;
    public String mPassword;
    public String mCompany;
    public String mStreet;
    public String mCity;
    public String mState;
    public String mZip;
    public String mRadius;

    //the constructor for the class
    public Employer(String username,String password,String company,String street,
                    String city, String state, String zip, String radius){
        this.mUsername=username;
        this.mPassword=password;
        this.mCompany=company;
        this.mStreet=street;
        this.mCity=city;
        this.mState=state;
        this.mZip=zip;
        this.mRadius=radius;
    }



}
