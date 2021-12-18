package io.swagger.api;

import io.swagger.model.Product;
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
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-18T16:54:35.582Z[GMT]")
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

    public ResponseEntity<List<Product>> getProduct(@Parameter(in = ParameterIn.HEADER, description = "Name" ,schema=@Schema()) @RequestHeader(value="name", required=false) String name,@Parameter(in = ParameterIn.HEADER, description = "Min Price" ,schema=@Schema()) @RequestHeader(value="minPrice", required=false) Integer minPrice,@Parameter(in = ParameterIn.HEADER, description = "Max Price" ,schema=@Schema()) @RequestHeader(value="maxPrice", required=false) Integer maxPrice,@Parameter(in = ParameterIn.HEADER, description = "ID of company" ,schema=@Schema()) @RequestHeader(value="companyID", required=false) Integer companyID,@Parameter(in = ParameterIn.HEADER, description = "Count of products" ,schema=@Schema()) @RequestHeader(value="count", required=false) Integer count,@Parameter(in = ParameterIn.HEADER, description = "Product ID" ,schema=@Schema()) @RequestHeader(value="productID", required=false) Integer productID) {
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

    public ResponseEntity<Void> postProduct(@Parameter(in = ParameterIn.HEADER, description = "User ID" ,schema=@Schema()) @RequestHeader(value="UserID", required=false) Integer userID,@Parameter(in = ParameterIn.HEADER, description = "user password" ,schema=@Schema()) @RequestHeader(value="password", required=false) String password) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
