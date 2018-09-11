package edu.duke.compsci290.dukefoodapp.model;

import java.util.List;

/**
 * Created by tannerjohnson on 4/10/18.
 *
 * Statistics.java implements the IStats interface. A contract for Statistics methods.
 */

public interface IStats {

    // interface to convert User information to String values for UI compatibility
    String getOrdersDone();
    String getRewardEligibility();
    String getPendingOrderBoolean();
    String getPointTotal();
    List<String> getAllStats();

}
