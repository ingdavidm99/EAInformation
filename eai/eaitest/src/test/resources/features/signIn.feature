@tag
Feature: Title of your test

  Background: 
    Given Abrir pagina

  @tag1
  Scenario Outline: SignIn failed
    When Ingresar credenciales failed <userName> <password>
    Then failed

    Examples: 
      | userName | password |
      | lol      |      123 |

  @tag2
  Scenario Outline: SignIn successful
    When Ingresar credenciales successful <userName> <password>
    Then successful

    Examples: 
      | userName | password |
      | dmarsigl |     1234 |
