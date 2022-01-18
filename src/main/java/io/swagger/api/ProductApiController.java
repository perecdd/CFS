package io.swagger.api;

import io.swagger.model.Address;
import io.swagger.model.Product;
import io.swagger.model.User;
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
import org.postgresql.jdbc.PgArray;
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
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-20T16:27:40.021Z[GMT]")
@RestController
public class ProductApiController implements ProductApi {

    private static final Logger log = LoggerFactory.getLogger(ProductApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ProductApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<Product>> getProduct(@Parameter(in = ParameterIn.HEADER, description = "Product name." ,schema=@Schema()) @RequestHeader(value="name", required=false) String name, @Parameter(in = ParameterIn.HEADER, description = "The minimum price of the product." ,schema=@Schema()) @RequestHeader(value="minPrice", required=false) Integer minPrice, @Parameter(in = ParameterIn.HEADER, description = "The maximum price of the product." ,schema=@Schema()) @RequestHeader(value="maxPrice", required=false) Integer maxPrice, @Parameter(in = ParameterIn.HEADER, description = "Company ID." ,schema=@Schema()) @RequestHeader(value="companyID", required=false) Integer companyID, @Parameter(in = ParameterIn.HEADER, description = "Minimum number of products." ,schema=@Schema()) @RequestHeader(value="count", required=false) Integer count, @Parameter(in = ParameterIn.HEADER, description = "Product ID." ,schema=@Schema()) @RequestHeader(value="productID", required=false) Integer productID) {
        String accept = request.getHeader("Accept");

        JSONObject jsonObject = new JSONObject();
        if(name != null) jsonObject.put("name", name);
        if(minPrice != null) jsonObject.put("minPrice", minPrice);
        if(maxPrice != null) jsonObject.put("maxPrice", maxPrice);
        if(companyID != null) jsonObject.put("companyID", companyID);
        if(count != null) {
            jsonObject.put("count", count);
        }
        else if(count <= 0){
            jsonObject.put("count", 1);
        }
        else{
            jsonObject.put("count", count);
        }
        if(productID != null) jsonObject.put("productID", productID);

        JSONObject info = ShopOwnerSide.GET(jsonObject, "/storage");

        try {
            return new ResponseEntity<List<Product>>(objectMapper.readValue(info.get("products").toString(), List.class), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<Product>>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> postProduct(@Parameter(in = ParameterIn.HEADER, description = "User's email" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email, @Parameter(in = ParameterIn.HEADER, description = "User's password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.execute("SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "';");
            ResultSet resultSet = DataBase.statement.getResultSet();

            if(resultSet.next()){
                JSONObject user = new JSONObject();
                user.put("name", resultSet.getString("name"));
                user.put("surname", resultSet.getString("surname"));
                user.put("password", resultSet.getString("password"));
                user.put("phone", resultSet.getString("phone"));
                user.put("id", resultSet.getInt("id"));
                user.put("email", resultSet.getString("email"));

                JSONObject address = new JSONObject();
                address.put("city", resultSet.getString("city"));
                address.put("country", resultSet.getString("country"));
                address.put("flat", resultSet.getString("flat"));
                address.put("house", resultSet.getString("house"));
                address.put("street", resultSet.getString("street"));

                user.put("address", address);

                JSONArray basket = new JSONArray();

                PgArray sqlProducts = (PgArray) resultSet.getArray("products");
                ResultSet rs = sqlProducts.getResultSet();
                for(int i = 0; rs.next(); i++){
                    StringBuilder sb = new StringBuilder(rs.getString(2));
                    sb.delete(0, 1);
                    sb.delete(sb.length() - 1, sb.length());

                    String[] objs = sb.toString().split(",");

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", objs[0].replaceAll("\"", ""));
                    jsonObject.put("Photo", objs[1].replaceAll("\"", ""));
                    jsonObject.put("companyid", Integer.parseInt(objs[2]));
                    jsonObject.put("productid", Integer.parseInt(objs[3]));
                    jsonObject.put("price", Integer.parseInt(objs[4]));
                    jsonObject.put("count", Integer.parseInt(objs[5]));
                    jsonObject.put("description", objs[6].replaceAll("\"", ""));
                    basket.add(jsonObject);
                }

                user.put("basket", basket);

                if(ShopOwnerSide.POST(user.toString(), "/order")) {
                    DataBase.statement.execute("UPDATE users\n" +
                            "   SET products = ARRAY[]::PRODUCT[] " +
                            " WHERE email = '" + email + "' AND \n" +
                            "       password = '" + password + "';");
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
                }
            }
            else{
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
