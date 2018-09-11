package edu.duke.compsci290.dukefoodapp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import edu.duke.compsci290.dukefoodapp.model.DiningUser;

import static org.junit.Assert.*;

/**
 * Created by maxwesterkam on 4/29/18.
 */

public class DiningUserTest {

    // testing constructor
    @Test
    public void constructor_isCorrect(){
        ArrayList<String> x = new ArrayList<String>();
        ArrayList<String> y = new ArrayList<String>();
        x.add("pendingOrder");
        y.add("orderHistory");
        DiningUser dUser = new DiningUser("id", "name", "type", "email", "phone", "bio", 2, true, x, y);
        assertEquals("id", dUser.getId());
        assertEquals("name", dUser.getName());
        assertEquals("type", dUser.getType());
        assertEquals("email", dUser.getEmail());
        assertEquals("phone", dUser.getPhone());
        assertEquals("bio", dUser.getBio());
        assertEquals(2, dUser.getPoints());
        assertEquals(true, dUser.getEligibleForReward());
        assertEquals(x, dUser.getPendingOrders());
        assertEquals(y, dUser.getOrderHistory());
    }
}
