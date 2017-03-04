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

    private String name;
    private Long id;
    private Calendar dateTime;
    private int duration;
    private String company;
    private String site;
    private double earnings;
    private int inputType;


    public PunchEntry (){
        this.inputType = -1;
        this.dateTime = Calendar.getInstance();
        this.duration = 0;
        this.company = "";
        this.site = "";
        this.earnings = 0;
        this.name= "";

    }

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public Calendar getDateTime (){
        return dateTime;
    }

    public long getDateTimeMillis() {
        return dateTime.getTimeInMillis();
    }

    public void setDateTime(Calendar newDateTime){
        this.dateTime = newDateTime;
    }

    public void setDateTimeMillis(Long newDateTime){
        this.dateTime.setTimeInMillis(newDateTime);
    }

    public int getDuration(){
        return duration;
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

    public void setEarnings(double newEarnigns){
        this.earnings = newEarnigns;
    }

    public int getInputType(){
        return inputType;
    }

    public void setInputType(int newInputType){
        this.inputType = newInputType;
    }
}
