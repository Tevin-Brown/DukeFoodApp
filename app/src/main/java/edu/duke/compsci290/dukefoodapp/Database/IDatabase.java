package edu.duke.compsci290.dukefoodapp.Database;

/**
 * Created by tevin on 4/22/2018.
 *
 * Interface implemented by OrderDB and UserDB
 */

public interface IDatabase {
    void writeToDatabase();
    void readFromDatabase(String id);
    void setObject(Object object);
    Object getObject();
}
