package cs65.punchphone.backend.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Patrick on 2/26/17.
 */

public class PunchDataStore {

    public static final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

    //add punch to datastore
    public static boolean add(Punch p) {
        if (getPunchById(p.mUserId, null) != null) {
            return false;
        } else {
            Entity entity = new Entity(Punch.PUNCH_ENTITY_NAME, p.mPunchId, getKey());
            entity.setProperty(Punch.FIELD_PUNCHID, p.mPunchId);
            entity.setProperty(Punch.FIELD_USERID, p.mUserId);
            entity.setProperty(Punch.FIELD_COMPANY, p.mCompany);
            entity.setProperty(Punch.FIELD_PUNCH_IN, p.mPunchIn);
            entity.setProperty(Punch.FIELD_PUNCH_OUT, p.mPunchOut);
            entity.setProperty(Punch.FIELD_SITE, p.mSite);
            datastoreService.put(entity);
            return true;
        }
    }

    //delete punch from datastore
    public static boolean delete(String punchid) {
        Filter f = new FilterPredicate(Punch.FIELD_PUNCHID, FilterOperator.EQUAL, punchid);
        Query q = new Query(Punch.PUNCH_ENTITY_NAME);
        q.setFilter(f);
        PreparedQuery pq = datastoreService.prepare(q);
        Entity e = pq.asSingleEntity();
        boolean b;
        if (e != null) {
            datastoreService.delete(e.getKey());
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    public static ArrayList<Punch> query(String id) {
        ArrayList<Punch> pList = new ArrayList<>();
        Query q = new Query(Punch.PUNCH_ENTITY_NAME);
        q.setFilter(null);
        q.setAncestor(getKey());
        PreparedQuery pq = datastoreService.prepare(q);
        Iterable<Entity> i = pq.asIterable();
        for (Entity entity : i) {
            Punch p = getPunchFromEntity(entity);
            if (p != null) {
                pList.add(p);
            }
        }
        return pList;
    }

    private static Key getKey() {
        return KeyFactory.createKey(Punch.PUNCH_PARENT_ENTITY_NAME, Punch.PUNCH_PARENT_KEY_NAME);
    }

    private static Punch getPunchById (String id, Transaction t) {
        Entity e = null;
        try {
            e = datastoreService.get(KeyFactory.createKey(getKey(), Punch.PUNCH_ENTITY_NAME, id));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return getPunchFromEntity(e);
    }

    private static Punch getPunchFromEntity (Entity e) {
        if (e == null) {
            return null;
        } else {
            return new Punch(
                    (String) e.getProperty(Punch.FIELD_PUNCHID),
                    (String) e.getProperty(Punch.FIELD_USERID),
                    (String) e.getProperty(Punch.FIELD_COMPANY),
                    (String) e.getProperty(Punch.FIELD_PUNCH_IN),
                    (String) e.getProperty(Punch.FIELD_PUNCH_OUT),
                    (String) e.getProperty(Punch.FIELD_SITE));
        }
    }
}