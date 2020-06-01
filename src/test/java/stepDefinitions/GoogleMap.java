package stepDefinitions;

import apiResources.APIResources;
import base.BaseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import payLoadGeneration.PayLoadSetup;
import pojo.googleMap.*;
import specBuilder.ReqResSpecBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GoogleMap {

    RequestSpecification request;
    Response response;
    public static HashMap<String, String> placeIds = new HashMap<>();
    String responseValue;

    @Given("Add Place Payload {int} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void addPlacePayload(int accuracy, String name, String phone_number, String address, String website, String language, String types, String lat, String lng) throws IOException {
        AddPlaceRequest addPlaceRequest = PayLoadSetup.addGoogleMapPayloadSetup(accuracy, name, phone_number, address, website, language, types, lat, lng);
        RestAssured.useRelaxedHTTPSValidation();
        request = given()
                .spec(ReqResSpecBuilder.getGoogleMapRequestSpecification())
                .body(addPlaceRequest);
    }

    @Given("Update Place Payload {string} {string}")
    public void updatePlacePayload(String address, String name) throws IOException {

        UpdatePlaceRequest updatePlaceRequest = PayLoadSetup.updateGoogleMapPayloadSetup(address, placeIds.get(name));
        request = given()
                .spec(ReqResSpecBuilder.getGoogleMapRequestSpecification())
                .body(updatePlaceRequest);
    }

    @Given("Delete Place Payload {string}")
    public void deletePlacePayload(String name) throws IOException {
        DeletePlaceRequest deletePlaceRequest = PayLoadSetup.deleteGoogleMapPayloadSetup(placeIds.get(name));
        request = given()
                .spec(ReqResSpecBuilder.getGoogleMapRequestSpecification())
                .body(deletePlaceRequest);
    }

    @Then("Get Place Payload {string}")
    public void getPlacePayload(String name) throws IOException {
        request = given()
                .spec(ReqResSpecBuilder.getGoogleMapRequestSpecification())
                .queryParam("place_id", placeIds.get(name));
    }

    @When("User calls {string} with {string} http request")
    public void user_calls_with_http_request(String resourceName, String actionType) {
        APIResources apiResources = APIResources.valueOf(resourceName);
        String resource = apiResources.getResource();

        if (actionType.equalsIgnoreCase("GET")) {
            response = request.when().get(resource);
        } else if (actionType.equalsIgnoreCase("POST")) {
            response = request.when().post(resource);
        } else if (actionType.equalsIgnoreCase("PUT")) {
            response = request.when().put(resource);
        }
    }

    @Then("The API call is success with status code {int}")
    public void the_API_call_is_success_with_status_code(int expectedStatusCode) {
        if (expectedStatusCode == 200) {
            response.then().spec(ReqResSpecBuilder.getGoogleMapSuccessResponse());
        } else if (expectedStatusCode == 404) {
            response.then().spec(ReqResSpecBuilder.getGoogleMapNoDataFoundResponse());
        }

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String expectedValue) {
        responseValue = this.response.asString();
        String actualValue = BaseClass.convertStringToJSONAndGetStringValue(responseValue, key);
        Assert.assertEquals(expectedValue, actualValue);
    }

    @And("Verify all field values {int} {string} {string} {string} {string} {string} {string} {string} {string}")
    public void verifyAllFieldValues(int accuracy, String name, String phone_number, String address, String website, String language, String types, String lat, String lng) {
        GetPlaceResponse getPlaceResponse = response.as(GetPlaceResponse.class);

        LocationResponse location = getPlaceResponse.getLocation();

        Assert.assertEquals(getPlaceResponse.getAccuracy(), accuracy);
        Assert.assertEquals(getPlaceResponse.getName(), name);
        Assert.assertEquals(getPlaceResponse.getPhone_number(), phone_number);
        Assert.assertEquals(getPlaceResponse.getAddress(), address);
        Assert.assertEquals(getPlaceResponse.getWebsite(), website);
        Assert.assertEquals(getPlaceResponse.getLanguage(), language);
        Assert.assertEquals(getPlaceResponse.getTypes(), types);
        Assert.assertEquals(location.getLatitude(), lat);
        Assert.assertEquals(location.getLongitude(), lng);
    }


    @And("Verify {string} as {string}")
    public void verifyAs(String key, String expectedMessage) {
        responseValue = this.response.asString();
        String actualMessage = BaseClass.convertStringToJSONAndGetStringValue(responseValue, key);
        Assert.assertEquals(expectedMessage, actualMessage);
    }


    @Then("Store all placeIds based on {string}")
    public void storeAllPlaceIdsBasedOn(String name) {
        String placeID = BaseClass.convertStringToJSONAndGetStringValue(responseValue, "place_id");
        placeIds.put(name, placeID);
    }
}
