@tag
Feature: Title of your test

  @tag1
  Scenario Outline: SignIn fail
    Given Ingresar a la pagina
    When Ingresar credenciales <userName> <password>
    Then Errror

    Examples: 
      | userName | password |
      | dmarsigl |      123 |
