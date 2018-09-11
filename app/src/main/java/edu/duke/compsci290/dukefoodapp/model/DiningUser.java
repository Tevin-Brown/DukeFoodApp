package edu.duke.compsci290.dukefoodapp.model;

import java.util.List;

/**
 * Created by tannerjohnson on 4/10/18.
 *
 * UserParent subclass for dining/admin users.
 */


public class DiningUser extends UserParent {


    public DiningUser() {
        makeSettings();
    }

    // only use this constructor for testing. otherwise use parent's getters/setters.
    public DiningUser(String id, String name, String type, String email, String phone, String bio,
                         int points, boolean eligibleForReward, List<String> pendingOrders,
                         List<String> orderHistory) {
        makeSettings();
        this.id = id;
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.email = email;
        this.bio = bio;
        this.points = points;
        this.eligibleForReward = eligibleForReward;
        this.pendingOrders = pendingOrders;
        this.orderHistory = orderHistory;
    }
}
