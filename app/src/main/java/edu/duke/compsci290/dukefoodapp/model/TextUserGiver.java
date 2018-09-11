package edu.duke.compsci290.dukefoodapp.model;

import java.util.List;

/**
 * Created by tannerjohnson on 4/5/18.
 *
 * Test class to view user and settings fields in console
 */

// FOR TESTING ONLY
public class TextUserGiver {

    public static void main(String[] args) throws UserMalformedException {
    	System.out.println("testing...");
        SampleUserFactory factory = SampleUserFactory.getInstance();
        StudentUser studentUser = factory.getSampleStudentUser();

        int i=0;
        List<String> allStats = studentUser.getStatistics();
        for(String s : allStats) {
            i++;
            System.out.println("stat #" +Integer.toString(i) + ": " + s);
        }

        System.out.println("student user name: " + studentUser.getName());

        List<String> studentSettings = studentUser.getSettings();
        for(String s : studentSettings) {
            i++;
            System.out.println(s);
        }

        RecipientUser recipientUser = factory.getSampleRecipientUser();
        System.out.println("recipient user name: " + recipientUser.getName());
    }

}
