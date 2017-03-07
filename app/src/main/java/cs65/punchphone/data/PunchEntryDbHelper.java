package cs65.punchphone.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by brian on 3/4/17.
 */

public class PunchEntryDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PunchPhoneDb";
    private static final String TABLE_NAME_PUNCHES = "Punches";
    private static final int DATABASE_VERSION = 1;

    public static final String KEY_ROW_ID = "id";
    public static final String KEY_INPUT_TYPE = "it";
    public static final String KEY_IN_DATE_TIME = "inDateTime";
    public static final String KEY_OUT_DATE_TIME = "outDateTime";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_SITE = "site";
    public static final String KEY_NAME = "name";
    public static final String KEY_EARNINGS = "earnings";



    private String[] allColumns = { KEY_ROW_ID, KEY_INPUT_TYPE,
            KEY_IN_DATE_TIME, KEY_OUT_DATE_TIME, KEY_DURATION, KEY_COMPANY,
    KEY_SITE, KEY_NAME, KEY_EARNINGS};


    public PunchEntryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {}




    // SQL query to create the table for the first time
    // Data types are defined below
    private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_PUNCHES
            + " ("
            + KEY_ROW_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_INPUT_TYPE
            + " INTEGER NOT NULL, "
            + KEY_IN_DATE_TIME
            + " INDATETIME NOT NULL, "
            +KEY_OUT_DATE_TIME
            + " OUTDATETIME NOT NULL, "
            + KEY_DURATION
            + " INTEGER NOT NULL, "
            + KEY_COMPANY
            + " TEXT, "
            + KEY_SITE
            + " TEXT, "
            + KEY_NAME
            + " TEXT,"
            + KEY_EARNINGS
            + " FLOAT " +
            ");";



    // Insert a item given each column value
    public long insertEntry(PunchEntry entry) {

        ContentValues value = new ContentValues();

        value.put(KEY_INPUT_TYPE, entry.getInputType());
        Log.d("insert entry", "input type: " + entry.getInputType());
        value.put(KEY_IN_DATE_TIME, entry.getInDateTimeMillis());
        Log.d("insert entry", "date: " + entry.getInDateTimeMillis());
        value.put(KEY_OUT_DATE_TIME, entry.getOutDateTimeMillis());
        value.put(KEY_DURATION, entry.getDuration());
        Log.d("insert entry", "duration: " + entry.getDuration());
        value.put(KEY_COMPANY, entry.getCompany());
        Log.d("insert entry", "company: " + entry.getCompany());
        value.put(KEY_SITE, entry.getSite());
        value.put(KEY_NAME, entry.getName());
        value.put(KEY_EARNINGS, entry.getEarnings());


        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TABLE_NAME_PUNCHES, null, value);
        db.close();
        return id;
    }

    public ArrayList<PunchEntry> fetchEntries(){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<PunchEntry> entries = new ArrayList<PunchEntry>();
        Cursor cursor = db.query(TABLE_NAME_PUNCHES, allColumns, null, null, null, null, null);
        cursor.moveToLast();
        while (!cursor.isBeforeFirst()) {
            PunchEntry entry = cursorToEntry(cursor);
            entries.add(entry);
            cursor.moveToPrevious();
        }
        cursor.close();
        return entries;
    }

    private PunchEntry cursorToEntry(Cursor cursor) {
        PunchEntry entry = new PunchEntry();
        entry.setId(cursor.getLong(0));
        entry.setInputType(cursor.getInt(1));
        entry.setInDateTimeMillis(cursor.getLong(2));
        entry.setOutDateTimeMillis(cursor.getLong(3));
        Log.d("fetch time", "time: " + entry.getInDateTimeMillis());
        entry.setDuration(cursor.getInt(4));
        entry.setCompany(cursor.getString(5));
        entry.setSite(cursor.getString(6));
        entry.setEarnings(cursor.getFloat(8));
        Log.d("fetch time", "earnings: " + entry.getEarnings());
        return entry;
    }

}
