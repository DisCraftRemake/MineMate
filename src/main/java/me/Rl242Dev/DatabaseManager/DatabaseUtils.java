package me.Rl242Dev.DatabaseManager;

import me.Rl242Dev.classes.Items.Item;

import java.sql.*;

public class DatabaseUtils {

    private final String url = "jdbc://mysql://localhost:3306/";
    private final String password = "";
    private final String user = "";

    public DatabaseUtils() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    /*
    public boolean UUIDExist(int uuid){

    }

     */
}
