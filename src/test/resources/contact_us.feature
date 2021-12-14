Feature: Formulario de contacto.
  Yo como PO
  quiero un formulario de contacto
  Para que mis clientes y proveedrores tengan un canal de comunicacion con la empresa.

  Background: el usuario se encuentra en el formulario contacto
    Given que el usuario esta en el formulario de contacto

  Scenario Outline: Deberia enviar el mensaje de contacto cuando enviamos todos los campos requeridos

    When el usuario completa el formulario con los campos requeridos
      |<cabecera>        |<email>           |<orderReferencia>|<message>       |
      |customer service  |pepito@gmail.com  |045456664334455  |mensaje random  |
    Then el usario deberia poder ver mensaje de confirmacion que se envio el mensaje
    Examples:
      | cabecera         | email            | orderReferencia | message        |
      |customer service  |pepito@gmail.com  |045456664334455  |mensaje random  |
      |webmaster         |manubalgmail.com  |454545466666444  |otro mensaje    |
  Scenario Outline: Scenario: El campo orden referencia solo debe admitir valores numericos
    When El usuario ingresa el valor <"23242424"> en el campo orden referencia
    Then El usuario deberia poder ver que el color del campo es <"verde">
    Examples:
      | "23242424" | "verde" |
      | "@$%&!"    | "rojo"  |
      | "prueba"   | "rojo"  |


