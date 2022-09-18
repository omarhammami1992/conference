Feature: Gestion des conférences
  Scenario: Vréation d'une condérence
    Given une conférence ayant le nom "Devoxx", le lien "https:www.devoxx" et qui dure entre "01-12-2022" et "03-12-2022"
    And qu'elle a un système de tarifiaction early bird à 150 € avant "31-08-2022"
    And qu'elle a un système de tarifiaction early bird à 200 € entre "01-09-2022" et "30-09-2022"
    And qu'elle a un système de tarifiaction early bird à 250 € entre "01-10-2022" et "31-10-2022"
    And qu'elle a un système de tarifiaction early bird à 300 € à partir du "01-11-2022"
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée