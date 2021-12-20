package io.swagger.api;

import java.sql.*;

public class DataBase {
    public static Connection connection;
    public static Statement statement;

    public DataBase() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:UsersDB");
        statement = connection.createStatement();
        System.out.println("DataBase is find!");
        statement.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "    id      INTEGER PRIMARY KEY AUTOINCREMENT\n" +
                "                    NOT NULL,\n" +
                "    email   STRING  UNIQUE ON CONFLICT FAIL\n" +
                "                    NOT NULL,\n" +
                "    name    STRING  NOT NULL,\n" +
                "    surname STRING  NOT NULL,\n" +
                "    phone   STRING,\n" +
                "    city    STRING  NOT NULL,\n" +
                "    country STRING  NOT NULL,\n" +
                "    street  STRING  NOT NULL,\n" +
                "    house   STRING  NOT NULL,\n" +
                "    flat    STRING  NOT NULL\n" +
                ");\n");
    }
}
