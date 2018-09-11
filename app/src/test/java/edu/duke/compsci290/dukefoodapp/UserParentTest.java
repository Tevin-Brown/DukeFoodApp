package edu.duke.compsci290.dukefoodapp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import edu.duke.compsci290.dukefoodapp.model.DiningUser;
import edu.duke.compsci290.dukefoodapp.model.RecipientUser;
import edu.duke.compsci290.dukefoodapp.model.UserMalformedException;
import edu.duke.compsci290.dukefoodapp.model.UserParent;

/**
 * Created by maxwesterkam on 5/1/18.
 */


public class UserParentTest {
    // get and set functions are tested in child classes (DiningUser, StudentUser, RecipientUser)
    // test getStatistics function
    @Test
    public void getStats_isCorrect() {
        ArrayList<String> x = new ArrayList<String>();
        ArrayList<String> y = new ArrayList<String>();
        x.add("pendingOrder");
        y.add("orderHistory");
        DiningUser dser = new DiningUser("id", "name", "type", "email", "phone", "bio", 2, true, x, y);
        ArrayList<String> allstats = new ArrayList<String>();
        allstats.add("Orders completed: 1");
        allstats.add("Pending Order(s): true");
        allstats.add("Points: 2");
        allstats.add("Reward Eligibility: true");
        try {
            assertEquals(allstats, dser.getStatistics());
        } catch (UserMalformedException e) {
            e.printStackTrace();
        }
    }

    // test updateOrderHistory function
    @Test
    public void updateOrderHistory_isCorrect() {
        DiningUser user = new DiningUser();
        List<String> x = new ArrayList<String>();
        x.add("order1");
        user.setOrderHistory(x);
        user.updateOrderHistory("order2");
        x.add("order2");
        assertEquals(x, user.getOrderHistory());

    }

    // test updatePendingOrders function
    @Test
    public void updatePendingOrders_isCorrect() {
        DiningUser user = new DiningUser();
        List<String> x = new ArrayList<String>();
        x.add("pendingOrder");
        user.setPendingOrders(x);
        user.updatePendingOrders("newOrder");
        x.add("newOrder");
        assertEquals(x, user.getPendingOrders());
    }
}
