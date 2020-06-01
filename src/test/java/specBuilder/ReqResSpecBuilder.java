package specBuilder;

import base.BaseClass;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.*;

public class ReqResSpecBuilder {

    static RequestSpecification request;
    static ResponseSpecification response;

    public static RequestSpecification getGoogleMapRequestSpecification() throws IOException {

        if(request==null)
        {
            PrintStream log = new PrintStream(new FileOutputStream("src/test/resources/logs/GoogleMapLogs.txt"));
            request = new RequestSpecBuilder()
                    .setBaseUri(BaseClass.getGlobalValues("baseURL"))
                    .addQueryParam("key", "qaclick123")
                    .setContentType(ContentType.JSON)
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .build();
            return request;
        }
        return request;
    }

    public static ResponseSpecification getGoogleMapSuccessResponse() {

        response = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
        return response;
    }

    public static ResponseSpecification getGoogleMapNoDataFoundResponse() {

        response = new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType(ContentType.JSON)
                .build();
        return response;
    }
}
