package io.swagger.api;

import io.swagger.model.Product;
import io.swagger.model.User;
import io.swagger.model.UserBody;
import io.swagger.model.UserBody1;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.postgresql.core.Query;
import org.postgresql.jdbc.PgArray;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-20T16:27:40.021Z[GMT]")
@RestController
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<User> getUserProfileInfo(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password) {
        String accept = request.getHeader("Accept");
        if(email != null && password != null){
            try {
                DataBase.statement.execute("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "';");
                ResultSet rs = DataBase.statement.getResultSet();

                if(rs.next()){
                    JSONObject userObject = new JSONObject();
                    JSONObject address = new JSONObject();
                    address.put("city", rs.getString("city"));
                    address.put("country", rs.getString("country"));
                    address.put("flat", rs.getString("flat"));
                    address.put("house", rs.getString("house"));
                    address.put("street", rs.getString("street"));
                    userObject.put("address", address);
                    userObject.put("email", rs.getString("email"));
                    userObject.put("id", rs.getInt("id"));
                    userObject.put("name", rs.getString("name"));
                    userObject.put("password", rs.getString("password"));
                    userObject.put("phone", rs.getString("phone"));
                    userObject.put("surname", rs.getString("surname"));
                    userObject.put("basket", new JSONArray());

                    return new ResponseEntity<User>(objectMapper.readValue(userObject.toString(), User.class), HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<List<Product>> getUser(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                try {
                    DataBase.statement.execute("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "';");
                    ResultSet rs = DataBase.statement.getResultSet();

                    if(rs.next()){
                        JSONArray jsonArray = new JSONArray();

                        PgArray sqlProducts = (PgArray) rs.getArray("products");
                        ResultSet resultSet = null;
                        if(sqlProducts != null) resultSet = sqlProducts.getResultSet();
                        for(int i = 0; sqlProducts != null && resultSet.next(); i++){
                            StringBuilder sb = new StringBuilder(resultSet.getString(2));
                            sb.delete(0, 1);
                            sb.delete(sb.length() - 1, sb.length());

                            String[] objs = sb.toString().split(",");

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("name", objs[0].replace("\"", ""));
                            jsonObject.put("Photo", objs[1].replace("\"", ""));
                            jsonObject.put("companyid", Integer.parseInt(objs[2]));
                            jsonObject.put("productid", Integer.parseInt(objs[3]));
                            jsonObject.put("price", Integer.parseInt(objs[4]));
                            jsonObject.put("count", Integer.parseInt(objs[5]));
                            jsonObject.put("description", objs[6].replace("\"", ""));
                            jsonArray.add(jsonObject);
                        }
                        return new ResponseEntity<List<Product>>(objectMapper.readValue(jsonArray.toString(), List.class), HttpStatus.OK);
                    }
                    else{
                        return new ResponseEntity<List<Product>>(HttpStatus.UNAUTHORIZED);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return new ResponseEntity<List<Product>>(HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Product>>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<List<Product>>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> patchUser(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password, @Parameter(in = ParameterIn.DEFAULT, description = "New user basket", schema=@Schema()) @Valid @RequestBody List<Product> body) {
        String accept = request.getHeader("Accept");

        try {
            DataBase.statement.execute("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "';");
            ResultSet rs = DataBase.statement.getResultSet();

            if(rs.next()){
                if(body.isEmpty()) {
                    DataBase.statement.execute("UPDATE users\n" +
                            "   SET products = ARRAY[]::PRODUCT[] " +
                            " WHERE email = '" + email + "' AND \n" +
                            "       password = '" + password + "';");
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }

                String queue = new String();
                queue += "ARRAY[";
                var it = body.listIterator();
                while(it.hasNext()){
                    Product product = it.next();
                    queue += "('" + product.getName() + "', '" + product.getPhoto() + "', " + product.getCompanyid() + ", " + product.getProductid() + ", " + product.getPrice() + ", " + product.getCount() + ", '" + product.getDescription() + "')::PRODUCT,";
                }
                queue = queue.substring(0, queue.length() - 1);
                queue += ']';

                System.out.println(queue);

                DataBase.statement.execute("UPDATE users\n" +
                        "   SET products = " + queue + "\n" +
                        " WHERE email = '" + email + "' AND \n" +
                        "       password = '" + password + "';");
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> postUser(@Parameter(in = ParameterIn.DEFAULT, description = "Basic user information.", schema=@Schema()) @Valid @RequestBody UserBody1 body) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.execute("SELECT * FROM users WHERE email = '" + body.getEmail() + "';");
            ResultSet rs = DataBase.statement.getResultSet();

            if(!rs.next()) {
                DataBase.statement.execute("INSERT INTO users (\n" +
                        "                      email,\n" +
                        "                      name,\n" +
                        "                      password,\n" +
                        "                      surname,\n" +
                        "                      phone,\n" +
                        "                      city,\n" +
                        "                      country,\n" +
                        "                      street,\n" +
                        "                      house,\n" +
                        "                      flat\n" +
                        "                  )\n" +
                        "                  VALUES (\n" +
                        "                      '" + body.getEmail() + "',\n" +
                        "                      '" + body.getName() + "',\n" +
                        "                      '" + body.getPassword() + "',\n" +
                        "                      '" + body.getSurname() + "',\n" +
                        "                      '" + body.getPhone() + "',\n" +
                        "                      '" + body.getAddress().getCity() + "',\n" +
                        "                      '" + body.getAddress().getCountry() + "',\n" +
                        "                      '" + body.getAddress().getStreet() + "',\n" +
                        "                      '" + body.getAddress().getHouse() + "',\n" +
                        "                      '" + body.getAddress().getFlat() + "'\n" +
                        "                  );\n");
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> putUser(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password, @Parameter(in = ParameterIn.DEFAULT, description = "New user information.", schema=@Schema()) @Valid @RequestBody UserBody body) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.execute("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "';");
            ResultSet rs = DataBase.statement.getResultSet();

            if(rs.next()) {
                String queue = new String();
                if(body.getEmail() != null){
                    queue += "email = '" + body.getEmail() + "'\n,";
                }
                else{
                    queue += "email = '" + email + "'\n,";
                }
                if(body.getName() != null){
                    queue += "name = '" + body.getName() + "'\n,";
                }
                if(body.getPassword() != null){
                    queue += "password = '" + body.getPassword() + "'\n,";
                }
                else{
                    queue += "password = '" + password + "'\n,";
                }
                if(body.getSurname() != null){
                    queue += "surname = '" + body.getSurname() + "'\n,";
                }
                if(body.getPhone() != null){
                    queue += "phone = '" + body.getPhone() + "'\n,";
                }
                if(body.getAddress() != null){
                    queue +="city = '" + body.getAddress().getCity() + "'\n," +
                            "country = '" + body.getAddress().getCountry() + "'\n," +
                            "street = '" + body.getAddress().getStreet() + "'\n," +
                            "house = '" + body.getAddress().getHouse() + "'\n," +
                            "flat = '" + body.getAddress().getFlat() + "'\n";
                }

                queue = queue.substring(0, queue.length() - 1);

                DataBase.statement.execute("UPDATE users\n" +
                        "   SET id = " + rs.getString("id") + ",\n" + queue +
                        " WHERE email = '" + email + "' AND \n" +
                        "       password = '" + password + "';");
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
