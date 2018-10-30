@tag
Feature: I want to make login in the application web

  Background: 
    Given Open browser
    And Open main web page

  @tag1
  Scenario Outline: Sign in failed
    When Enter wrong credentials <userName> <password>
    Then Error message

    Examples: 
      | userName | password |
      | lol      |      123 |

  @tag2
  Scenario Outline: Sign in successful
    When Enter correct credentials <userName> <password>
    Then Successful message

    Examples: 
      | userName | password |
      | dmarsigl |     1234 |
