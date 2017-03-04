package cs65.punchphone.backend.data;

/**
 * Created by Patrick on 2/26/17.
 */

public class Punch {
    public static final String PUNCH_PARENT_ENTITY_NAME = "PunchParent";
    public static final String PUNCH_PARENT_KEY_NAME = "PunchParent";

    public static final String PUNCH_ENTITY_NAME = "Punch";

    public static final String FIELD_PUNCHID = "punchid";
    public static final String FIELD_USERID = "userid";
    public static final String FIELD_COMPANY = "company";
    public static final String FIELD_PUNCH_IN = "id";
    public static final String FIELD_PUNCH_OUT = "id";
    public static final String FIELD_LOC_LAT = "lat";
    public static final String FIELD_LOC_LONG = "long";

    public String mPunchId;
    public String mUserId;
    public String mCompany;
    public String mPunchIn;
    public String mPunchOut;
    public String mLatitude;
    public String mLongitude;

    public Punch(String punchid, String userid, String company,  String punchIn, String punchOut,
                 String latitude, String longitude) {
        mPunchId = punchid;
        mUserId = userid;
        mCompany = company;
        mPunchIn = punchIn;
        mPunchOut = punchOut;
        mLatitude = latitude;
        mLongitude = longitude;
    }
}
