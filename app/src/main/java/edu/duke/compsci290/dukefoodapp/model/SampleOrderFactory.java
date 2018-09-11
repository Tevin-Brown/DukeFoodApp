package edu.duke.compsci290.dukefoodapp.model;

import android.location.Location;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 *
 * Singleton instance of Order object used for testing.
 */

public class SampleOrderFactory {

    private static SampleOrderFactory mInstance;
    private Order mOrder;

    public static SampleOrderFactory getInstance() {
        if (mInstance == null) {
            mInstance = new SampleOrderFactory();
        }
        return mInstance;
    }

    private SampleOrderFactory() {
        doConstruct();
    }

    // constructs our one instance of SampleUserFactory mInstance
    // makes mOrder
    private void doConstruct() {
        mOrder = new Order();
        mOrder.setId("1oiwh1092he");
        Timestamp today = new Timestamp(System.currentTimeMillis());
        mOrder.setDatePosted(today);
        Timestamp tomorrow = new Timestamp(System.currentTimeMillis());
        tomorrow.setDate(tomorrow.getDay()+1);
        mOrder.setDateDelivered(tomorrow);
        mOrder.setRecipientId("3ulvzYImhjV8eMwCsG6keo6wgTs1");
        mOrder.setStudentId("rqXb2oZizsQLy6mXM2lkCuHU7UH3");
        mOrder.setDiningId("lasndSPROUT12312");
        mOrder.setStatus(3);
        mOrder.setPickupLocation("1324+Campus+Dr,+Durham+NC+27705");
        mOrder.setDropoffLocation("300+Swift+Ave,+Durham+NC+27705");
        mOrder.setAllergens("Peanuts");
        mOrder.setStudentName("Tanner");
        mOrder.setStudentPhone("9998887777");
        mOrder.setRecipientName("Toni Montana");
        mOrder.setRecipientPhone("1112223333");
        mOrder.setDiningName("Marketplace");

    }

    public Order getIncompleteOrder(){
        mOrder = new Order();
        mOrder.setId("1oiwh1092he");
        Timestamp today = new Timestamp(System.currentTimeMillis());
        mOrder.setDatePosted(today);
        Timestamp tomorrow = new Timestamp(System.currentTimeMillis());
        tomorrow.setDate(tomorrow.getDay()+1);
        mOrder.setDateDelivered(tomorrow);
        mOrder.setStudentId("rqXb2oZizsQLy6mXM2lkCuHU7UH3");
        mOrder.setDiningId("lasndSPROUT12312");
        mOrder.setStatus(1);
        mOrder.setPickupLocation("1324+Campus+Dr,+Durham+NC+27705");
        mOrder.setDropoffLocation("300+Swift+Ave,+Durham+NC+27705");
        mOrder.setAllergens("Peanuts");
        return mOrder;
    }


    // use this method to return student user object
    public Order getSampleOrder() {
        return mOrder;
    }
}
