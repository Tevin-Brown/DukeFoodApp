package edu.duke.compsci290.dukefoodapp.UserActivities;

/**
 * Day is launched from CalendarActivity.
 * All orders registered by admins are visible to each user on this page.
 * Specific users can access and make specific actions on orders.
 */

import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.duke.compsci290.dukefoodapp.Database.IOnDatabaseRead;
import edu.duke.compsci290.dukefoodapp.Database.OrderDB;
import edu.duke.compsci290.dukefoodapp.Database.UserDB;
import edu.duke.compsci290.dukefoodapp.R;
import edu.duke.compsci290.dukefoodapp.login.ChooserActivity;
import edu.duke.compsci290.dukefoodapp.model.IDay;
import edu.duke.compsci290.dukefoodapp.model.IUser;
import edu.duke.compsci290.dukefoodapp.model.Order;
import edu.duke.compsci290.dukefoodapp.model.SampleDayFactory;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

import static edu.duke.compsci290.dukefoodapp.UserActivities.DayActivityAdapter.sTAG;

public class DayActivity extends AppCompatActivity {
    //this activity is used for the user to sign up for items on the specified day
    private TextView dateTextView;
    private RecyclerView rv;
    public UserParent user;
    public ArrayList<Order> orderHistory;
    public String date;
    private LinearLayout dynamicLL;
    private LinearLayout orderListLL;
    private RelativeLayout dynamicRL;
    private OrderDB oDB;
    private UserDB uDB;
    public static final String mTAG = "DAY";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    // for sign out
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
        //Grab Intent information passed from CalendarActivity
        final Intent receivedIntent = this.getIntent();
        user = (UserParent) receivedIntent.getSerializableExtra("user");

        date = receivedIntent.getStringExtra("date");
        uDB = UserDB.getInstance();



        //set navigation
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setup clickables to navigation view

        NavigationView nv = (NavigationView)findViewById(R.id.navigation_view_ad);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Log.d(mTAG,"item selected");
                Intent intent;
                switch (menuItem.getItemId()) {
                    case(R.id.home):
                        Log.d(mTAG,"Home");
                        intent = new Intent(DayActivity.this, UserActivity.class);
                        intent.putExtra("type",user.getType());
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                    case (R.id.logout):
                        Log.d(mTAG, "Log Out");
                        signOut();
                        intent = new Intent(DayActivity.this, ChooserActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case(R.id.my_orders):
                        if (user.getPendingOrders() == null){
                            CharSequence text = "You Have No Pending Orders!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(DayActivity.this, text, duration);
                            toast.show();
                            break;
                        }
                        else{
                            intent = new Intent(DayActivity.this, MyOrdersActivity.class);
                            intent.putExtra("type",user.getType());
                            intent.putExtra("user",user);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    case(R.id.calendar):
                        intent = new Intent(DayActivity.this, CalendarActivity.class);
                        intent.putExtra("type",user.getType());
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });


        //grab views
        orderListLL = findViewById(R.id.day_activity_order_list_ll);
        dateTextView = findViewById(R.id.day_textview);
        dateTextView.setText(makeDate(date));
        oDB = OrderDB.getInstance();
        oDB.setCustomEventListener(new IOnDatabaseRead() {
            @Override
            public void onEvent() {
                orderHistory = oDB.getOrdersByDate();
                Log.d(mTAG,"# of orders: " + Integer.toString(orderHistory.size()));
                Log.d(mTAG,"Database Read!");
                setupRV();
            }
        });
        oDB.setOrdersByDate(date);


        //Make dialogue for admin
        //dialogues for other users are in the dayactivityadapter
        dynamicRL = findViewById(R.id.day_activity_RL);
        Button button = new Button(this);
        if(user.getType().equals("admin")){
            button.setText("New Order");
            button.setTextSize(10);
            button.setWidth(40);
            button.setHeight(10);
            button.setGravity(Gravity.RIGHT);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // creates a dialogue
                    final Order order = new Order();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(DayActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.create_order,null);
                    Button submit = mView.findViewById(R.id.button_create_order);
                    final EditText diningName = mView.findViewById(R.id.create_order_input_location);
                    diningName.setText(user.getName());
                    final EditText allergens = mView.findViewById(R.id.create_order_input_allergens);
                    final EditText pickup = mView.findViewById(R.id.create_order_input_pickuptime);
//                    pickup.setText(user.getAddress());
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    submit.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            // submits the info
                            order.setDiningName(diningName.getText().toString());
                            order.setAllergens(allergens.getText().toString());
                            order.setPickupTime(pickup.getText().toString());
                            order.setDate(date);
                            order.setPickupLocation(user.getAddress());
                            //create unique order id
                            String id = UUID.randomUUID().toString();
                            order.setId(id);
                            order.setStatus(0);
                            oDB.setObject(order);
                            oDB.writeToDatabase();
                            user.updatePendingOrders(order.getId());
                            user.updateOrderHistory(order.getId());
                            uDB.setObject(user);
                            uDB.writeToDatabase();
                            //toast order sumbitted
                            Log.d(mTAG,"order submitted");
                            dialog.dismiss();
                            Intent intent = new Intent(DayActivity.this, DayActivity.class);
                            intent.putExtra("user" ,user);
                            intent.putExtra("date", date);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
            dynamicRL.addView(button);
        }
    }

    private void signOut() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut();
    }

    public void setupRV(){
        rv = new RecyclerView(this);
        DayActivity.this.orderListLL.addView(rv);
        rv.setAdapter(new DayActivityAdapter(this,this.orderHistory,user,date,oDB));
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public String makeDate(String date){
        String newDate = "";
        String[] months = new String[]{"January","February","March","April","May","June","July","August","September","October", "November", "December"};
        String year = date.substring(0,4);
        String numMonth = date.substring(4,5);
        String day = date.substring(5,date.length());
        newDate = months[Integer.parseInt(numMonth)] + " " + day + ", " + year;
        return newDate;
    }

    @Override
    protected void onStop(){
        super.onStop();
        finish();
    }


}
