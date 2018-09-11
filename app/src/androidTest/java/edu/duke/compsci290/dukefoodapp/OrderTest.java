package edu.duke.compsci290.dukefoodapp;

/**
 * Created by maxwesterkam on 5/1/18.
 */

import android.os.Parcel;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.ArrayList;

import edu.duke.compsci290.dukefoodapp.model.Day;
import edu.duke.compsci290.dukefoodapp.model.Order;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class OrderTest {

    // test all the set and get functions; instance variables are all private
    @Test
    public void setAndGet_areCorrect(){

        Parcel parcel = Parcel.obtain();
        Order order = new Order();
        order.setId("someid");
        order.setDiningName("west union");
        order.setStatus(0);
        order.setAllergens("a");
        order.setDate("date");
        order.setDateDelivered(new Timestamp((long) 10));
        order.setDatePosted(new Timestamp((long) 10));
        order.setDeliveryTime("time");
        order.setDiningId("dId");
        order.setDropoffLocation("dropoff");
        order.setPickupLocation("");
        order.setPickupTime("time");
        order.setRecipientId("id");
        order.setRecipientName("name");
        order.setRecipientPhone("phonenum");
        order.setStudentId("stud");
        order.setStudentName("name");
        order.setStudentPhone("num");
        order.writeToParcel(parcel, order.describeContents());
        parcel.setDataPosition(0);
        Order orderParcel = Order.CREATOR.createFromParcel(parcel);

        assertEquals("someid", order.getId());
        assertEquals("west union", order.getDiningName());
        assertEquals(0, order.getStatus());
        assertEquals("a", order.getAllergens());
        assertEquals(new Timestamp((long) 10), order.getDateDelivered());
        assertEquals(new Timestamp((long) 10), order.getDatePosted());
        assertEquals("time", order.getDeliveryTime());
        assertEquals("dId", order.getDiningId());
        assertEquals("dropoff", order.getDropoffLocation());
        assertEquals("", order.getPickupLocation());
        assertEquals("time", order.getPickupTime());
        assertEquals("id", order.getRecipientId());
        assertEquals("name", order.getRecipientName());
        assertEquals("phonenum", order.getRecipientPhone());
        assertEquals("stud", order.getStudentId());
        assertEquals("name", order.getStudentName());
        assertEquals("num", order.getStudentPhone());

    }

}
