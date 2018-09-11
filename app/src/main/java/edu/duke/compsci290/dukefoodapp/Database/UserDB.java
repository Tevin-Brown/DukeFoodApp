package edu.duke.compsci290.dukefoodapp.Database;


import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;
import edu.duke.compsci290.dukefoodapp.model.DiningUser;
import edu.duke.compsci290.dukefoodapp.model.RecipientUser;
import edu.duke.compsci290.dukefoodapp.model.StudentUser;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

/**
 * Created by tevin on 4/22/2018.
 *
 * Singleton class to perform reads/writes on user objects in firebase realtime db
 */

public class UserDB implements IDatabase {
    private static UserDB mInstance;
    private UserParent mUser;
    private DatabaseReference mDatabase;
    private final String Tag = "UserDB";
    private IOnDatabaseRead mListener;



    public static UserDB getInstance() {
        if (mInstance == null) {
            mInstance = new UserDB();
        }
        return mInstance;
    }

    public void setCustomEventListener(IOnDatabaseRead eventListener) {
        this.mListener=eventListener;
    }

    public  UserDB(){
        mDatabase  = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void writeToDatabase() {
        mDatabase.child("users").child(this.mUser.getId()).setValue(this.mUser);
    }

    @Override
    public void readFromDatabase(String id) {
        Query query = mDatabase.child("users").orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(Tag, "snapshot does exist");
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (HashMap<String, Object>)issue.getValue();
                        String userType = map.get("type").toString();
                        Log.d(Tag, "user's type is: " +userType);
                        if (userType.equals("student")) {
                            mUser = (StudentUser)issue.getValue(StudentUser.class);
                        } else if (userType.equals("recipient")) {
                            mUser = (RecipientUser)issue.getValue(RecipientUser.class);
                        } else if (userType.equals("admin")) {
                            mUser = (DiningUser)issue.getValue(DiningUser.class);
                        } else {
                            Log.d(Tag, "user does not have valid type field!");
                        }
                    }
                    if(mListener!=null){
                        mListener.onEvent();
                    }
                } else { // does not exist
                    Log.d(Tag, "snapshot does NOT exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(Tag, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public void setObject(Object object) {
        //get user object
        this.mUser = (UserParent) object;
//        Log.d(Tag,mUser.getId().toString());
    }

    @Override
    public Object getObject() {
        return this.mUser;
    }
}
