package edu.duke.compsci290.dukefoodapp.Database;


import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import edu.duke.compsci290.dukefoodapp.model.Order;

/**
 * Created by tevin on 4/22/2018.
 *
 * Singleton class to perform reads/writes on order objects in firebase realtime db
 */

public class OrderDB implements IDatabase, Serializable {
    private static OrderDB mInstance;
    public ArrayList<Order> mOrdersByDate;
    private Order mOrder;
    private DatabaseReference mDatabase;
    private static String TAG = "OrderDB";
    private IOnDatabaseRead mListener;


    public void setCustomEventListener(IOnDatabaseRead eventListener) {
        this.mListener=eventListener;
    }

    // create singleton instance
    public static OrderDB getInstance() {
        if (mInstance == null) {
            mInstance = new OrderDB();
        }
        return mInstance;
    }

    public OrderDB(){
        mOrdersByDate = new ArrayList<>();
        mDatabase  = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void writeToDatabase() {
        mDatabase.child("orders").child(mOrder.getId()).setValue(mOrder);
    }

    @Override
    public void readFromDatabase(String id) {
        Query query = mDatabase.child("orders").orderByChild("id").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "snapshot does exist");
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (HashMap<String, Object>)issue.getValue();
                        String userType = map.get("id").toString();
                        Log.d(TAG, "user's type is: " +userType);
                        mOrder = (Order) issue.getValue(Order.class);
                    }
                    if(mListener!=null){
                        mListener.onEvent();
                    }
                } else { // does not exist
                    Log.d(TAG, "snapshot does NOT exist");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public Object getObject() {
        return mOrder;
    }

    @Override
    public void setObject(Object object) {
        //pass the order object
        this.mOrder = (Order) object;
    }


    public ArrayList<Order> getOrdersByDate(){
        return this.mOrdersByDate;
    }

    public void setOrdersByDate(final String date){
        //Queries DB to get orders on that date
        this.mOrdersByDate = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Query query = mDatabase.child("orders").orderByChild("date").equalTo(date);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Log.d(TAG, "snapshot does exist");
                            for (DataSnapshot issue : dataSnapshot.getChildren()) {
                                Order order = (Order) issue.getValue(Order.class);
                                Log.d(TAG, "order id is: " + order.getId());
                                mOrdersByDate.add(order);
                            }
                            Log.d(TAG, "# of orders: " + Integer.toString(mOrdersByDate.size()));
                            if(mListener!=null){
                                mListener.onEvent();
                            }
                        } else { // does not exist
                            Log.d(TAG, "snapshot does NOT exist");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }

                });
            }
        }).start();
    }





}
