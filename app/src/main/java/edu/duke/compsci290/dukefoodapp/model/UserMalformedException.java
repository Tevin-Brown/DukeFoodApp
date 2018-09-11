package edu.duke.compsci290.dukefoodapp.model;

/**
 * Created by tannerjohnson on 4/11/18.
 *
 * Thrown if view tries to access user fields with null user
 */

public class UserMalformedException extends Throwable {

    private final String mMessage;


    public UserMalformedException(String message) {
        mMessage = message;
    }

    @Override
    public String toString(){
        return mMessage;
    }

}
