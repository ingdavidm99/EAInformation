@tag
Feature: Title of your test

  @tag2
  Scenario Outline: SignIn fail
    Given Ingresar a la pagina
    When ingresar credenciales <userName> y <password>
    Then errror

    Examples: 
      | userName  | password |
      | dmarsigl  |   123    |
