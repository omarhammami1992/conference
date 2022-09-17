Feature: Gestion des conférences
  Scenario: Vréation d'une condérence
    Given une conférence ayant le nom "Devoxx", le lien "https:www.devoxx" et qui dure entre "01-01-2022" et "03-01-2022"
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée