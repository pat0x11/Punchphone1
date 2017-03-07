package cs65.punchphone.data;

import java.util.Calendar;

/**
 * Created by brian on 3/4/17.
 */

public class PunchEntry {
    // date
    // time start
    // duration
    // company
    // site
    // earnings

    //the varaiables that will be stored for every single punch on the local database
    private String name;
    private Long id;
    private Calendar inDateTime;
    private Calendar outDateTime;
    private int duration;
    private String company;
    private String site;
    private double earnings;
    private int inputType;

    //the constructor for the class
    public PunchEntry (){
        this.inputType = -1;
        this.inDateTime = Calendar.getInstance();   //get the current time when punched in
        this.outDateTime = Calendar.getInstance();  //NOTE: this will be changed elsewhere on punchOut
        this.duration = 0;
        this.company = "";
        this.site = "";
        this.earnings = 0;
        this.name= "";

    }

    //GETTERS and SETTERS for all the variables
    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public Calendar getInDateTime(){
        return inDateTime;
    }

    public long getInDateTimeMillis() {
        return inDateTime.getTimeInMillis();
    }

    public void setInDateTime(Calendar newDateTime){
        this.inDateTime = newDateTime;
    }

    public void setInDateTimeMillis(Long newDateTime){
        this.inDateTime.setTimeInMillis(newDateTime);
    }

    public void setOutDateTimeMillis(Long newDateTime){
        this.outDateTime.setTimeInMillis(newDateTime);
    }

    public int getDuration(){
        return duration;
    }

    public void setOutDateTime(Calendar outDateTime) {
        this.outDateTime = outDateTime;
    }

    public long getOutDateTimeMillis(){
        return outDateTime.getTimeInMillis();
    }

    public void setDuration(int newDuration){
        this.duration = newDuration;
    }

    public String getCompany(){
        return company;
    }

    public void setCompany(String newCompany){
        this.company = newCompany;
    }

    public String getSite(){
        return site;
    }

    public void setSite(String newSite){
        this.site = newSite;
    }

    public String getName(){
        return name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public double getEarnings(){
        return earnings;
    }

    public void setEarnings(double newEarnings){
        this.earnings = newEarnings;
    }

    public int getInputType(){
        return inputType;
    }

    public void setInputType(int newInputType){
        this.inputType = newInputType;
    }
}
