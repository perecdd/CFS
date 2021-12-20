package io.swagger.api;

import io.swagger.model.Product;
import io.swagger.model.UserBody;
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
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-20T15:07:17.781Z[GMT]")
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

    public ResponseEntity<List<Product>> getUser(@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email,@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<Product>>(objectMapper.readValue("[ {\n  \"companyid\" : 0,\n  \"productid\" : 6,\n  \"price\" : 1,\n  \"name\" : \"name\",\n  \"count\" : 5,\n  \"description\" : \"description\",\n  \"Photo\" : \"Photo\"\n}, {\n  \"companyid\" : 0,\n  \"productid\" : 6,\n  \"price\" : 1,\n  \"name\" : \"name\",\n  \"count\" : 5,\n  \"description\" : \"description\",\n  \"Photo\" : \"Photo\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<Product>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<Product>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> patchUser(@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email,@Parameter(in = ParameterIn.HEADER, description = "" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password,@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody List<Product> body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> postUser(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody UserBody body) {
        String accept = request.getHeader("Accept");
        try {
            DataBase.statement.execute("SELECT id,\n" +
                    "       email,\n" +
                    "       name,\n" +
                    "       surname,\n" +
                    "       phone,\n" +
                    "       city,\n" +
                    "       country,\n" +
                    "       street,\n" +
                    "       house,\n" +
                    "       flat\n" +
                    "  FROM users WHERE email == '" + body.getEmail() + "';");
            ResultSet rs = DataBase.statement.getResultSet();

            if(!rs.next()) {
                System.out.println("Add User to DB");
                DataBase.statement.execute("INSERT INTO users (\n" +
                        "                      email,\n" +
                        "                      name,\n" +
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

    public ResponseEntity<Void> putUser(@Parameter(in = ParameterIn.HEADER, description = "Like nickname" ,required=true,schema=@Schema()) @RequestHeader(value="email", required=true) String email,@Parameter(in = ParameterIn.HEADER, description = "To authentificate user" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="phone", required=false) String phone,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="city", required=false) String city,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="country", required=false) String country,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="house", required=false) String house,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="flat", required=false) String flat,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="new email", required=false) String newEmail,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="new password", required=false) String newPassword) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
