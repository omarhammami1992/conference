Feature: Gestion des conférence
  Scenario: création d'une condérence
    Given une conférence avant le nom "Devoxx", le lien "https:www.devoxx" et qui dure entre "01-01-2022" et "03-01-2022"
    And qu'il y un système de tarifaction early bird between date 1 et date 2 à 100 euros
    And qu'il y un système de tarifaction early bird between date 3 et date 4 à 150 euros
    And qu'il y un système de tarifaction early bird between date 5 et date 6 à 200 euros
    And qu'il y une pleine de tarifaction à 300 euros à partir de la date 6
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée avec le nom "Devoxx", le lien "https:www.devoxx" et qui dure entre "01-01-2022" et "03-01-2022"