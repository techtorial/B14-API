Feature: Pet store

  Scenario: Create a pet
    Given user has valid URL
    When user sends POST request to create a pet
    Then status code is 200
    And pet name, pet id, pet status are correct