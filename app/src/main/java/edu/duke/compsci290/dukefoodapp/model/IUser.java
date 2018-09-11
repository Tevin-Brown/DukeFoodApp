package edu.duke.compsci290.dukefoodapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 *
 * UserParent implements IUser interface. Minimal set of getters for all user fields.
 */

public interface IUser{
    String getId();
    String getName();
    String getType();
    String getEmail();
    String getBio();
    List<String> getPendingOrders();
    List<String> getOrderHistory();
    boolean getEligibleForReward();
    int getPoints();
    ArrayList<String> getStatistics() throws UserMalformedException;
    ArrayList<String> getSettings();
}
