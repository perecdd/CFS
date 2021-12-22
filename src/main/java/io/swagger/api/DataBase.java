package io.swagger.api;

import java.sql.*;

public class DataBase {
    public static Connection connection;
    public static Statement statement;

    public DataBase() throws Exception {
        Class.forName("org.postgresql.Driver");
        //connection = DriverManager.getConnection("jdbc:sqlite:UsersDB");
        StringBuilder url = new StringBuilder();
        url.
                append("jdbc:postgresql://").  //db type
                append("localhost:").          //host name
                append("5432/").               //port
                append("postgres?").             //db name
                append("user=postgres&").      //login
                append("password=postgres");     //password

        connection = DriverManager.getConnection(url.toString());
        statement = connection.createStatement();
        statement.execute("DO\n" +
                "$$\n" +
                "BEGIN\n" +
                "  IF NOT EXISTS (SELECT *\n" +
                "                        FROM pg_type typ\n" +
                "                             INNER JOIN pg_namespace nsp\n" +
                "                                        ON nsp.oid = typ.typnamespace\n" +
                "                        WHERE nsp.nspname = current_schema()\n" +
                "                              AND typ.typname = 'product') THEN\n" +
                "    CREATE TYPE PRODUCT AS (name TEXT, photo TEXT, companyid integer, productid integer, price integer, count integer, description TEXT);" +
                "  END IF;\n" +
                "END;\n" +
                "$$\n" +
                "LANGUAGE plpgsql;");
        statement.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                "    id      SERIAL PRIMARY KEY\n" +
                "                    NOT NULL,\n" +
                "    email   TEXT  UNIQUE\n" +
                "                    NOT NULL,\n" +
                "    name    TEXT  NOT NULL,\n" +
                "    password    TEXT  NOT NULL,\n" +
                "    surname TEXT  NOT NULL,\n" +
                "    phone   TEXT,\n" +
                "    city    TEXT  NOT NULL,\n" +
                "    country TEXT  NOT NULL,\n" +
                "    street  TEXT  NOT NULL,\n" +
                "    house   TEXT  NOT NULL,\n" +
                "    flat    TEXT  NOT NULL,\n" +
                "    products    PRODUCT[]  \n" +
                ");\n");
    }
}
