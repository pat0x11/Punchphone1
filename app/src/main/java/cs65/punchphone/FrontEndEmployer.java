package cs65.punchphone;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jack on 3/5/17.
 */

//the class for employers on the front end...received on app startup
public class FrontEndEmployer {

    //declare the object variables
    private String mCompany;
    private LatLng mLoc;
    private int radius;
    private int normalHrs;
    private int overtimeHrs;

    private Context context;

    //the constructor for the class...NOTE: converts string into objects
    public FrontEndEmployer(String company, String street, String city, String state, String zip,
                            String radius, String normal, String overtimeHrs, Context context) throws IOException {

        this.context = context;

        //first parse the radius and hour fields, along with company name
        this.mCompany = company;
        this.radius = Integer.parseInt(radius);
        this.normalHrs = Integer.parseInt(normal);
        this.overtimeHrs = Integer.parseInt(overtimeHrs);

        //next handle converting the string address to a Location object, using Geocoder
        Geocoder addressConverter = new Geocoder(this.context, Locale.getDefault());

        //convert the address into the default format
        String completeAddress = street + "," + city + "," + state;

        //get the list of addresses, limiting it to one
        List<Address> allAddresses = addressConverter.getFromLocationName(completeAddress, 1);

        //get the first and only address object if there is one
        if (allAddresses.size() > 0) {
            Address returnedAddress = allAddresses.get(0);

            //convert address into LatLng object
            Double lat = returnedAddress.getLatitude();
            Double longitude = returnedAddress.getLongitude();

            LatLng toAdd = new LatLng(lat, longitude);
            this.mLoc = toAdd;
        } else {
            //then there was an error so set the location to null
            this.mLoc = null;
        }

    }

    public String getName() {
        return mCompany;
    }
}