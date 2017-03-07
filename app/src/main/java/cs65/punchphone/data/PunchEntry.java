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
    private Calendar inDateTime;
    private Calendar outDateTime;
    private int duration;
    private String company;
    private String site;
    private double earnings;
    private int inputType;


    public PunchEntry (){
        this.inputType = -1;
        this.inDateTime = Calendar.getInstance();
        this.outDateTime = Calendar.getInstance();
        this.duration = 0;
        this.company = "";
        this.site = "";
        this.earnings = 0;
        this.name= "";

    }

//    public String toString() {
//        String calin;
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(getInDateTimeMillis());
//        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
//        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
//        String year = Integer.toString(calendar.get(Calendar.YEAR));
//        String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
//        String minute = Integer.toString(calendar.get(Calendar.MINUTE));
//        calin = month + "/" + day + "/" + year + " " + hour + ":" + minute;
//        String calout;
//        calendar.setTimeInMillis(getOutDateTimeMillis());
//        month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
//        day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
//        year = Integer.toString(calendar.get(Calendar.YEAR));
//        hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
//        minute = Integer.toString(calendar.get(Calendar.MINUTE));
//        calout = month + "/" + day + "/" + year + " " + hour + ":" + minute;
//        int hours = duration / 3600;
//        int minutes = duration / 60;
//        int seconds = duration % 60;
//        String retString = Integer.toString(hours) + " hours, " + Integer.toString(minutes) +
//                " minutes, " + Integer.toString(seconds) + " seconds for " + company + " at " + site
//                + " between " + calin + " and " + calout;
//        return retString;
//    }

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
