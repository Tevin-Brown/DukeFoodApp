package edu.duke.compsci290.dukefoodapp.UserActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;

import java.util.ArrayList;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.Database.OrderDB;
import edu.duke.compsci290.dukefoodapp.Database.UserDB;
import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.login.GoogleSignInActivity;
import edu.duke.compsci290.dukefoodapp.model.IDay;
import edu.duke.compsci290.dukefoodapp.model.Order;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

/**
 * Created by tannerjohnson on 4/13/18.
 *
 * Adapter subclass for recycler view that holds all orders and their current state.
 * All current information about orders is retrieved from firebase.
 * Actions from dialogue boxes write to database and modify users and orders.
 */

public class DayActivityAdapter extends RecyclerView.Adapter<DayActivityAdapter.ViewHolder> {

    public Context mContext;
    public String mUserType;
    public ArrayList<Order> mOrders;
    public UserParent mUser;
    public String date;
    public OrderDB oDB;
    public UserDB uDB;
    public static final String sTAG = "DayActivityAdapter";

    // constructor
    public DayActivityAdapter(final Context context, ArrayList<Order> orders, UserParent user,String date, OrderDB oDB) {
        this.mContext = context;
        this.mOrders = orders;
        this.mUser = user;
        this.mUserType = user.getType();
        this.date = date;
        this.oDB = oDB;
        this.uDB = UserDB.getInstance();
        Log.d(sTAG,"# of orders(38): "+Integer.toString(mOrders.size()));
        Log.d(sTAG, "setting up DayActivityAdapter");
    }

    @Override
    public int getItemCount() {
        Log.d(sTAG,"# of orders(44): "+Integer.toString(mOrders.size()));
        return mOrders.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(sTAG, "onCreateViewHolder");
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.activity_day_order_holder, parent, false);
        final ViewHolder orderHolder = new ViewHolder(row);
        return orderHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Order order = mOrders.get(position);
        holder.mOrderName.setText(order.getDiningName());
        //create colors for layout background
        // onclick listener for selecting orders
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUserType.equals("recipient")){
                    if (order.getStatus()>=2){
                        CharSequence text = "Order Already Taken!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(mContext, text, duration);
                        toast.show();

                    }
                    else{
                        // open dialogue for the user
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mView = mInflater.inflate(R.layout.recipient_edit_order,null);
                        TextView dining = mView.findViewById(R.id.recipient_dining_name);
                        dining.setText("Dining Name: " + order.getDiningName());
                        TextView allergens = mView.findViewById(R.id.recipient_allergens);
                        allergens.setText("Allergens: " + order.getAllergens());
                        final EditText recipientName = mView.findViewById(R.id.recipient_name);
                        recipientName.setText(mUser.getName());
                        final EditText recipientAddress = mView.findViewById(R.id.recipient_address);
                        recipientAddress.setText(mUser.getAddress());
                        final EditText recipientPickupTime = mView.findViewById(R.id.recipient_pickup_time);
                        recipientPickupTime.setHint("Past " + order.getPickupTime());
                        Button button = mView.findViewById(R.id.recipient_accept);
                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String rname = recipientName.getText().toString();
                                String raddress = recipientAddress.getText().toString();
                                String deliver = recipientPickupTime.getText().toString();
                                order.setDropoffLocation(raddress);
                                order.setRecipientName(rname);
                                order.setDeliveryTime(deliver);
                                order.setRecipientId(mUser.getId());
                                order.setRecipientPhone(mUser.getPhone());
                                order.setStatus(2);
                                oDB.setObject(order);
                                oDB.writeToDatabase();
                                mUser.updatePendingOrders(order.getId());
                                uDB.setObject(mUser);
                                uDB.writeToDatabase();
                                dialog.dismiss();


                                // ALWAYS GO BACK HOME SO WE CAN REFRESH DATA
                                Intent intent = new Intent(mContext, GoogleSignInActivity.class);
                                mContext.startActivity(intent);
                            }
                        });
                    }
                }
                // dialogue for student
                if(mUserType.equals("student")){
                    if (order.getStatus() >= 1){
                        CharSequence text = "Order Already Taken!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(mContext, text, duration);
                        toast.show();
                    }
                    else{
                        // open dialogue for the user
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View mView = mInflater.inflate(R.layout.student_edit_order,null);
                        TextView dining = mView.findViewById(R.id.student_edit_dn);
                        dining.setText("Dining Name: " + order.getDiningName());
                        TextView allergens = mView.findViewById(R.id.student_edit_allergens);
                        allergens.setText("Allergens: " + order.getAllergens());
                        TextView diningPickupLocation = mView.findViewById(R.id.student_edit_dining_location);
                        diningPickupLocation.setText("Pickup Location: " + order.getPickupLocation());
                        TextView diningPickupTime = mView.findViewById(R.id.student_edit_dining_pt);
                        diningPickupTime.setText("Pickup Time: " + order.getPickupTime());
                        Button button = mView.findViewById(R.id.student_accept);
                        mBuilder.setView(mView);
                        final AlertDialog dialog = mBuilder.create();
                        dialog.show();
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                order.setStudentId(mUser.getId());
                                order.setStudentPhone(mUser.getPhone());
                                order.setStudentName(mUser.getName());
                                order.setStatus(1);
                                mUser.updatePendingOrders(order.getId());
//                                mUser.updateOrderHistory(order.getId());
                                oDB.setObject(order);
                                oDB.writeToDatabase();
                                uDB.setObject(mUser);
                                uDB.writeToDatabase();
                                Log.d(sTAG,order.getId());
                                dialog.dismiss();
//                                Intent intent = new Intent(mContext, DayActivity.class);
//                                intent.putExtra("user" ,mUser);
//                                intent.putExtra("date", date);
                                // ALWAYS GO BACK HOME SO WE CAN REFRESH DATA
                                Intent intent = new Intent(mContext, GoogleSignInActivity.class);
                                mContext.startActivity(intent);
                            }
                        });
                    }
                }
            }
        });
    }

    // view holder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mOrderName;
        public LinearLayout mLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mOrderName = itemView.findViewById(R.id.tv_order_name);
            this.mLinearLayout = itemView.findViewById(R.id.order_holder_linear_layout);
        }
    }


}
