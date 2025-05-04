Feature: Everything About Pets

  Scenario: Successfully Upload Image Pet
    Given I have a pet with ID '86002031'
    When I request to upload pet image
    Then The client should receive HTTP 200 response status
    And The response body should contain 'message' with value 'File uploaded to ./cat-image.jpeg'

  Scenario: Successfully Add New Pet To Store
    Given I have pet data to add
    When I request to add a new pet
    Then The client should receive HTTP 200 response status
    And I verify add new pet response already correct

  Scenario: Successfully Update Existing Pet Data
    Given I have an existing pet
    When I request to update the pet data
    Then The client should receive HTTP 200 response status
    And I verify pet data already updated

  Scenario Outline: Successfully Find Pet By Status
    Given There are pets with status '<Status>'
    When I request to find pets by status
    Then The client should receive HTTP 200 response status
    And The response body should contain 'status[0]' with value '<Status>'

    Examples:
      | Status    |
      | available |
      | pending   |
      | sold      |

  Scenario: Successfully Find Pet By ID
    Given I have a pet with ID '86002031'
    When I request to find the pet by ID
    Then The client should receive HTTP 200 response status
    And The response body should contain 'id' with value '86002031'

  Scenario: Successfully Delete Existing Pet Data
    Given I have a pet with ID '86002031'
    When I request to delete the pet by ID
    Then The client should receive HTTP 200 response status
    And The response body should contain 'message' with value '86002031'
    And I verify pet data already deleted
    And I revert deleted data

  Scenario: Find Pet With Unregistered ID
    Given I have a pet with ID '1314151617198235321'
    When I request to find the pet by ID
    Then The client should receive HTTP 404 response status
    And The response body should contain 'code' with value '1'
    And The response body should contain 'type' with value 'error'
    And The response body should contain 'message' with value 'Pet not found'

  Scenario Outline: Find Pet With Invalid ID Format
    Given I have a pet with ID '<ID>'
    When I request to find the pet by ID
    Then The client should receive HTTP 404 response status
    And The response body should contain 'code' with value '404'
    And The response body should contain 'message' with value 'java.lang.NumberFormatException: For input string: "<ID>"'

    Examples:
      | ID                      |
      | abc                     |
      | 12345678901234567890123 |

  Scenario: Find Pet With Empty Parameter
    Given I have a pet with ID 'NULL'
    When I request to find the pet by ID
    Then The client should receive HTTP 405 response status

  Scenario Outline: Find Pet Status With Not Allowed Method
    Given There are pets with status 'available'
    When I request to find pets status with method '<Method>'
    Then The client should receive HTTP 405 response status

    Examples:
      | Method |
      | POST   |
      | PUT    |
      | PATCH  |
      | DELETE |

  Scenario Outline: Add New Pet To Store With Invalid ID Format
    Given I have a pet with ID '<ID>'
    When I request to add a new pet by ID
    Then The client should receive HTTP 500 response status
    And The response body should contain 'code' with value '500'
    And The response body should contain 'type' with value 'unknown'
    And The response body should contain 'message' with value 'something bad happened'

    Examples:
      | ID                   |
      | Abc                  |
      | !@#                  |
      | 12345123451234512345 |

  Scenario: Delete Pet Data With Unregistered ID
    Given I have a pet with ID '1314151617198235321'
    When I request to delete the pet by ID
    Then The client should receive HTTP 404 response status

