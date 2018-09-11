package edu.duke.compsci290.dukefoodapp;

import org.junit.Test;

import java.util.ArrayList;

import edu.duke.compsci290.dukefoodapp.model.DiningUser;
import edu.duke.compsci290.dukefoodapp.model.Statistics;

import static org.junit.Assert.*;

/**
 * Created by maxwesterkam on 4/29/18.
 */

public class StatisticsTest {

    //test constructor/buildStatistics function
    @Test
    public void buildStats_isCorrect() {
        ArrayList<String> x = new ArrayList<String>();
        ArrayList<String> y = new ArrayList<String>();
        x.add("pendingOrder");
        y.add("orderHistory");
        ArrayList<String> allstats = new ArrayList<String>();
        allstats.add("Orders completed: 1");
        allstats.add("Pending Order(s): true");
        allstats.add("Points: 2");
        allstats.add("Reward Eligibility: true");
        DiningUser dUser = new DiningUser("id", "name", "type", "email", "phone", "bio", 2, true, x, y);
        Statistics stats = new Statistics(dUser);
        assertEquals("Orders completed: 1", stats.getOrdersDone());
        assertEquals("Reward Eligibility: true", stats.getRewardEligibility());
        assertEquals("Pending Order(s): true", stats.getPendingOrderBoolean());
        assertEquals("Points: 2", stats.getPointTotal());
        assertEquals(allstats, stats.getAllStats());

    }
}
