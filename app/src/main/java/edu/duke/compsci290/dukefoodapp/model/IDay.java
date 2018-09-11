package edu.duke.compsci290.dukefoodapp.model;

import java.util.ArrayList;

/**
 * Created by tevin on 4/11/2018.
 *
 * Day.java implements the IDay interface. A contract for Day methods.
 */

public interface IDay {
    String getDate();
    ArrayList getOrders();
    void setOrders(ArrayList<Order> orders);
    void setDate(String date);

}
