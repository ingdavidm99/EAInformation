@tag
Feature: Log Error

  Background: 
    Given Open browser
    And Open main web page
    And Enter correct credentials dmarsigl 1234
    And Enter the following menu menu-1 subMenu-2

  @tag1
  Scenario: Enter the menu
    When Enter the following menu menu-1 subMenu-1
