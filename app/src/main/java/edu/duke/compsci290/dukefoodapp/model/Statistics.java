package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;

/**
 * Created by tannerjohnson on 4/10/18.
 *
 * Separate class for storing user statistics. Isolated from User classes.
 */

public class Statistics implements IStats {

    private String mOrdersDone;
    private String mRewardEligibility;
    private String mPendingOrderBoolean;
    private String mPointTotal;
    private String mBio;
    private ArrayList<String> mAllStats;

    public Statistics(IUser user) {
        buildStatistics(user);
    }

    // user constructor passes this to buildStatistics, which updates stats based on current user state
    private void buildStatistics(IUser user) {
        // if user.instanceOf()
        assert user instanceof IUser;
        assert user instanceof UserParent;
        System.out.println("sample user is instance of IUser and UserParent");
        int totalOrdersToDate;
        if (user.getOrderHistory() == null) {
            totalOrdersToDate = 0;
        } else {
            totalOrdersToDate = user.getOrderHistory().size();
        }

        mOrdersDone = "Orders completed: " + Integer.toString(totalOrdersToDate);
        mRewardEligibility = "Reward Eligibility: " + String.valueOf(user.getEligibleForReward());
        mBio = "Bio: " + user.getBio();
        boolean pendingOrder = true;
        if (user.getPendingOrders() == null) {
            pendingOrder = false;
        } else if (user.getPendingOrders().size() == 0) {
            pendingOrder = false;
        }
        mPendingOrderBoolean = "Pending Order(s): " + String.valueOf(pendingOrder);
        mPointTotal = "Points: " + Integer.toString(user.getPoints());
        mAllStats = new ArrayList<>();
        mAllStats.add(mOrdersDone);
        mAllStats.add(mPendingOrderBoolean);
        mAllStats.add(mPointTotal);
        mAllStats.add(mRewardEligibility);
        mAllStats.add(mBio);
    }

    public String getOrdersDone(){
        return this.mOrdersDone;
    }
    public String getRewardEligibility(){
        return this.mRewardEligibility;
    }
    public String getPendingOrderBoolean(){
        return this.mPendingOrderBoolean;
    }
    public String getPointTotal(){
        return this.mPointTotal;
    }

    // use this getter to construct the user's statistics field
    public ArrayList<String> getAllStats() { return this.mAllStats; }

}
