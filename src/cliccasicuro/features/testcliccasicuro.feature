Feature: Testing Cliccasicuro
  
  Scenario: iPhone purchase process in Cliccasicuro
  
    Given open "https://www-staging.cliccasicuro.it"
    When set the model to "iPhone"
    Then click on button "Protezione"
    Then fill the product details
    Then click on button "Ordina"
    Then fill the order with credit card "master"
    Then click on button "Al pagamento"
    Then fill credit card form with credit card "9451123100000111"
    Then click on button "Ordinare a pagamento"
    Then check if it was successful
 