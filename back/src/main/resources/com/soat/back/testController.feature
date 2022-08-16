Feature: test TestController
  Scenario: client makes call to GET /api/test
    When the client calls ""
    Then the client receives as status code 200
    And the client receives as body Hello world