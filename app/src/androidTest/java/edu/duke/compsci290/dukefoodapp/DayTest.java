package edu.duke.compsci290.dukefoodapp;

/**
 * Created by maxwesterkam on 5/1/18.
 */

import android.os.Parcel;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Timestamp;
import java.util.ArrayList;

import edu.duke.compsci290.dukefoodapp.model.Day;
import edu.duke.compsci290.dukefoodapp.model.Order;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DayTest {

    // test the getDate function
    @Test
    public void getDate_isCorrect() {
        Day day = new Day();
        day.mDate="4/3/14";
        assertEquals("4/3/14", day.getDate());
    }

    // test the getOrders function
    @Test
    public void getOrders_isCorrect() {
        Day day = new Day();
        ArrayList<Order> orders = new ArrayList<Order>();

        for(int i=0; i<3; i++) {
            Parcel parcel = Parcel.obtain();
            Order order = new Order();
            order.setId("id");
            order.setDiningName("west union");
            order.setStatus(i);
            order.setAllergens("");
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
            orders.add(orderParcel);
        }
        day.mOrders=orders;
        assertEquals(orders, day.getOrders());
    }

    // test the setDate function
    @Test
    public void setDate_isCorrect() {
        Day day = new Day();
        day.setDate("4/3/14");
        assertEquals("4/3/14", day.mDate);
    }

    // test the setOrders function
    @Test
    public void setOrders_isCorrect() {
        Day day = new Day();
        ArrayList<Order> orders = new ArrayList<Order>();

        for(int i=0; i<3; i++) {
            Parcel parcel = Parcel.obtain();
            Order order = new Order();
            order.setId("id");
            order.setDiningName("west union");
            order.setStatus(i);
            order.setAllergens("");
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
            orders.add(orderParcel);
        }
        day.setOrders(orders);
        assertEquals(orders, day.mOrders);
    }

}
