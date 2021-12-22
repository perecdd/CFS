package io.swagger.api;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class SQLProduct implements SQLData {
    private String sql_type;
    public String name;
    public String photo;
    public Integer companyid;
    public Integer productid;
    public Integer price;
    public Integer count;
    public String description;

    @Override
    public String getSQLTypeName() throws SQLException {
        return sql_type;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        sql_type = typeName;
        name = stream.readString();
        photo = stream.readString();
        companyid = stream.readInt();
        productid = stream.readInt();
        price = stream.readInt();
        count = stream.readInt();
        description = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(name);
        stream.writeString(photo);
        stream.writeInt(companyid);
        stream.writeInt(productid);
        stream.writeInt(price);
        stream.writeInt(count);
        stream.writeString(description);
    }
}
