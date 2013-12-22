package jp.crudefox.server.ricoh;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Const {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/ricoh0";
    public static final String DB_USER = "ricoh0";
    public static final String DB_PASSWORD = "Krx3wwpeZuaYJtW5";



    public static final Connection getDefaultDBConnection() throws SQLException{
    	return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

}
