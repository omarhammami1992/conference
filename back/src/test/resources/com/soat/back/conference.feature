Feature: Gestion des conférence
  Scenario: création d'une condérence
    Given une conférence avant le nom "Devoxx", le lien "https:www.devoxx" et qui dure entre "01-01-2022" et "03-01-2022"
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée avec le nom "Devoxx", le lien "https:www.devoxx" et qui dure entre "01-01-2022" et "03-01-2022"