Feature: Validating Place API's
  @Create
  Scenario Outline: Verify if place is being Successfully added using AddPlaceAPI
    Given Add Place Payload <accuracy> "<name>" "<phone_number>" "<address>" "<website>" "<language>" "<types>" "<lat>" "<lng>"
    When User calls "AddPlaceAPI" with "Post" http request
    Then The API call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    Then Store all placeIds based on "<name>"
    Then Get Place Payload "<name>"
    And User calls "GetPlaceAPI" with "Get" http request
    Then The API call is success with status code 200
    And Verify all field values <accuracy> "<name>" "<phone_number>" "<address>" "<website>" "<language>" "<types>" "<lat>" "<lng>"

    Examples:
      | accuracy | name  | phone_number     | address   | website       | language | types           | lat        | lng       |
      | 50       | Bata  | (+91) 9538913778 | Bengaluru | www.test.com  | English  | shoe park,shop  | -38.383494 | 33.427362 |
      | 60       | Siraz | (+91) 9538913777 | Kolkata   | www.test1.com | Bengali  | Food,Restaurant | -23.383494 | 28.427362 |
      | 100      | Baga  | (+91) 9538913776 | Goa       |               | Goanis   | Beach           | -13.383494 | 18.427362 |


  Scenario Outline: Verify if place is being Successfully updated using UpdatePlaceAPI
    Given Update Place Payload "<address>" "<name>"
    When User calls "UpdatePlaceAPI" with "Put" http request
    Then The API call is success with status code 200
    And Verify "msg" as "Address successfully updated"
    Then Get Place Payload "<name>"
    And User calls "GetPlaceAPI" with "Get" http request
    Then The API call is success with status code 200
    And Verify all field values <accuracy> "<name>" "<phone_number>" "<address>" "<website>" "<language>" "<types>" "<lat>" "<lng>"

    Examples:
      | accuracy | name  | phone_number     | address | website       | language | types           | lat        | lng       |
      | 50       | Bata  | (+91) 9538913778 | Mumbai  | www.test.com  | English  | shoe park,shop  | -38.383494 | 33.427362 |
      | 60       | Siraz | (+91) 9538913777 | Pune    | www.test1.com | Bengali  | Food,Restaurant | -23.383494 | 28.427362 |
      | 100      | Baga  | (+91) 9538913776 | Chennai |               | Goanis   | Beach           | -13.383494 | 18.427362 |


  Scenario Outline: Verify if place is being Successfully deleted using deletePlaceAPI
    Given Delete Place Payload "<name>"
    When User calls "DeletePlaceAPI" with "Post" http request
    Then The API call is success with status code 200
    And "status" in response body is "OK"
    Then Get Place Payload "<name>"
    And User calls "GetPlaceAPI" with "Get" http request
    Then The API call is success with status code 404
    And Verify "msg" as "Get operation failed, looks like place_id  doesn't exists"

    Examples:
      | name  |
      | Bata  |
      | Siraz |
      | Baga  |