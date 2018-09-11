package edu.duke.compsci290.dukefoodapp.model;

import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 *
 * Test class to view day fields in console
 */

// FOR TESTING ONLY
public class TextDayGiver {

    public static void main(String[] args) {
        System.out.println("testing day factory...");
        SampleDayFactory factory = SampleDayFactory.getInstance();
        IDay day = factory.getDay();


    }

}