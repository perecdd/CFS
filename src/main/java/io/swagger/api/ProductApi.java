/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.29).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.Product;
import io.swagger.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-20T15:07:17.781Z[GMT]")
@Validated
public interface ProductApi {

    @Operation(summary = "Your GET endpoint", description = "Get Products by parametrs", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
        
        @ApiResponse(responseCode = "400", description = "Bad Request") })
    @RequestMapping(value = "/product",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Product>> getProduct(@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="name", required=false) String name, @Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="minPrice", required=false) Integer minPrice, @Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="maxPrice", required=false) Integer maxPrice, @Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="companyID", required=false) Integer companyID, @Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="count", required=false) Integer count, @Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="productID", required=false) Integer productID);


    @Operation(summary = "", description = "Order products by user", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "OK"),
        
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized") })
    @RequestMapping(value = "/product",
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<Void> postProduct(@Parameter(in = ParameterIn.HEADER, description = "User ID" ,required=true,schema=@Schema()) @RequestHeader(value="UserID", required=true) Integer userID, @Parameter(in = ParameterIn.HEADER, description = "user password" ,required=true,schema=@Schema()) @RequestHeader(value="password", required=true) String password, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody User body);

}

