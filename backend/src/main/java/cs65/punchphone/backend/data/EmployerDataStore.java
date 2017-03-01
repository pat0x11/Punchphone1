package cs65.punchphone.backend.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jack on 2/28/17.
 */

//the class that deals with employer information for the backend
    //handles login information
public class EmployerDataStore {
    //the class variables a logger and then a new datastore
    private static final Logger mLogger = Logger
            .getLogger(EmployerDataStore.class.getName());
    private static final DatastoreService mDatastore = DatastoreServiceFactory
            .getDatastoreService();

    //creates a key for each access to the datastore
    private static Key getKey() {
        return KeyFactory.createKey(Employer.PARENT_ENTITY_NAME,
                Employer.PARENT_ENTITY_KEY);
    }

    //a method that checks to see if the username is already taken
    //could signal that an account has already been created or they must use a different username
    public static boolean containsUsername(String username){
        Entity returned=null;
        try{
            returned=mDatastore.get(KeyFactory.createKey(getKey(),
                    Employer.EMPLOYER_ENTITY_NAME, username));
        }catch (Exception e){
        }

        if (returned == null) {
            return false;
        }
        //return true if the entity exists
        return true;
    }

    //a method that adds a new employer to the dataStore
    public static boolean addNewUser(Employer toAdd){

        //check to make sure that the user isn't already in the database
        if (containsUsername(toAdd.mUsername)){
            //then the username is already in the system, so cancel
            mLogger.log(Level.INFO,"Tried to add entry, but username already exists");
            return false;
        }

        //if the user is truly new, then add the employer to the datastore

        //get a key to the database and create an entity
        Key toAddKey=getKey();

        Entity newEntity=new Entity(Employer.EMPLOYER_ENTITY_NAME, toAdd.mUsername, toAddKey);

        //set the properties of the entity...ie everything in the employer object
        newEntity.setProperty(Employer.EMPLOYER_USERNAME,toAdd.mUsername);
        newEntity.setProperty(Employer.EMPLOYER_PASSWORD,toAdd.mPassword);
        newEntity.setProperty(Employer.EMPLOYER_COMPANY,toAdd.mCompany);
        newEntity.setProperty(Employer.EMPLOYER_STREET,toAdd.mStreet);
        newEntity.setProperty(Employer.EMPLOYER_CITY,toAdd.mCity);
        newEntity.setProperty(Employer.EMPLOYER_STATE,toAdd.mState);
        newEntity.setProperty(Employer.EMPLOYER_ZIPCODE,toAdd.mZip);
        newEntity.setProperty(Employer.EMPLOYER_RADIUS,toAdd.mRadius);

        //add the entity to the datastore
        mDatastore.put(newEntity);
        mLogger.log(Level.INFO, "Put in datastore @EntryDatastore");
        return true;
    }

    //a method that verifies the password of a user by searching the password in the database
    public static boolean authenticatePassword(String username,String password){
        //will handle checking if username exists elsewhere, so assume that the user exists

        // query
        Query.Filter filter = new Query.FilterPredicate(Employer.EMPLOYER_USERNAME,
                Query.FilterOperator.EQUAL, username);

        Query query = new Query(Employer.EMPLOYER_ENTITY_NAME);
        query.setFilter(filter);

        // Use PreparedQuery interface to retrieve results
        PreparedQuery pq = mDatastore.prepare(query);

        Entity result = pq.asSingleEntity();

        //attempt to get the password from the username object that exists in the datastore
        String datastorePassword=result.getProperty(Employer.EMPLOYER_PASSWORD).toString();
        mLogger.log(Level.INFO,"In datastore, password ="+datastorePassword);

        //compare the two strings to see if the passwords are equal
        if (password.compareTo(datastorePassword)==0){
            //passwords are the same, so return true
            return true;
        }
        //otherwise the password is incorrect
        return false;
    }

    //a method that returns an entire Employer object
    //used for updating settings
    public static Employer getEmployerByUsername(String username){
        // query
        Query.Filter filter = new Query.FilterPredicate(Employer.EMPLOYER_USERNAME,
                Query.FilterOperator.EQUAL, username);

        Query query = new Query(Employer.EMPLOYER_ENTITY_NAME);
        query.setFilter(filter);

        // Use PreparedQuery interface to retrieve results
        PreparedQuery pq = mDatastore.prepare(query);

        Entity result = pq.asSingleEntity();

        //check to see if the result is null
        if (result==null){
            return null;
        }

        //get all properties from the entity
        String dbUsername=result.getProperty(Employer.EMPLOYER_USERNAME).toString();
        String password=result.getProperty(Employer.EMPLOYER_PASSWORD).toString();
        String company=result.getProperty(Employer.EMPLOYER_COMPANY).toString();
        String street=result.getProperty(Employer.EMPLOYER_STREET).toString();
        String city=result.getProperty(Employer.EMPLOYER_CITY).toString();
        String state=result.getProperty(Employer.EMPLOYER_STATE).toString();
        String zip=result.getProperty(Employer.EMPLOYER_ZIPCODE).toString();
        String radius=result.getProperty(Employer.EMPLOYER_RADIUS).toString();

        //setup a new employer object
        Employer toReturn=new Employer(dbUsername,password,company,street,city,state,zip,radius);
        return toReturn;
    }

    //a method that deletes a single employer from the database
    public static boolean removeEmployer(String username){
        // query
        Query.Filter filter = new Query.FilterPredicate(Employer.EMPLOYER_USERNAME,
                Query.FilterOperator.EQUAL, username);

        Query query = new Query(Employer.EMPLOYER_ENTITY_NAME);
        query.setFilter(filter);

        // Use PreparedQuery interface to retrieve results
        PreparedQuery pq = mDatastore.prepare(query);

        Entity result = pq.asSingleEntity();

        //if result is null, there isn't an entry to delete
        if (result==null){
            return false;
        }

        //otherwise try to delete the current entity
        mDatastore.delete(result.getKey());
        return true;
    }

    //a method that clears all data in the datastore
    public static void clearData(){

        Query query = new Query(Employer.EMPLOYER_ENTITY_NAME);

        // get every record from the class' datastore, don't need a filter
        query.setFilter(null);
        // set query's ancestor to get strong consistency
        query.setAncestor(getKey());

        PreparedQuery pq = mDatastore.prepare(query);
        if (pq!=null) {
            for (Entity entity : pq.asIterable()) {
                mDatastore.delete(entity.getKey());
            }
        }
    }

    //a method that updates an Employer in the Datastore
    public static boolean updateEntry(String username, Employer updatedVersion){
        //remove the entry if it exists in the database and then add a new one
        if (!containsUsername(username)){
            return false;       //false indicates that no update took place
        }
        //otherwise remove the entry and then add the new one
        if (!removeEmployer(username)){
            return false;
        }
        //at this point you can add the new employer into the datastore
        return addNewUser(updatedVersion);
    }

    //a method that returns the company name, given a username
    public static String getCompanyName(String username){
        Employer current=getEmployerByUsername(username);

        //make sure the employer isn't null
        if (current!=null){
            return current.mCompany;
        }
        //if it is null, don't return a string
        return null;
    }
}
