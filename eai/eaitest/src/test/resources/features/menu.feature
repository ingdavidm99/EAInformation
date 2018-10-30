# Menu: 1 Configuration
#	subMenu:
#		- 1 systemparameter
#		- 2 logerror
#		- 3 user
#		- 4 profile
#	Menu: 2 Eainformation
#	subMenu:
#		- 5 eainformation
@tag
Feature: I want to enter the menu

  Background: 
    Given Open browser
    And Open main web page
    And Enter correct credentials dmarsigl 1234

  @tag1
  Scenario: Enter the menu
    When Enter the following menu menu-1 subMenu-1
