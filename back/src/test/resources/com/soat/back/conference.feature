Feature: Gestion des conférences

  Scenario: Création d'une conférence avec un système de tarification early bird
    Given une conférence ayant le nom "Devoxx", le lien "https://www.devoxx" et qui dure entre le "01-12-2022" et le "03-12-2022"
    And qu'elle a un système de tarification early bird à 150 € avant le "31-08-2022"
    And qu'elle a un système de tarification early bird à 200 € entre le "01-09-2022" et le "30-09-2022"
    And qu'elle a un système de tarification early bird à 250 € entre le "01-10-2022" et le "31-10-2022"
    And qu'elle a une tarification pleine à 300 €
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée avec le prix 300 € et  les intervalles de réduction early bird
      | price | startDate  | endDate    |
      | 150   | null       | 31-08-2022 |
      | 200   | 01-09-2022 | 30-09-2022 |
      | 250   | 01-10-2022 | 31-10-2022 |

  Scenario: Création d'une conférence avec un système de groupe
    Given une conférence ayant le nom "Devoxx", le lien "https://www.devoxx" et qui dure entre le "01-12-2022" et le "03-12-2022"
    And qu'elle a un système de tarification de groupe à 150 € par personne lorsqu on réserve à partir de 10 billets
    And qu'elle a une tarification pleine à 300 €
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée avec le prix 300 € et un prix réduit de 150 € à partir de 10 participants

  Scenario: Création d'une conférence avec un système de par journée de présence
    Given une conférence ayant le nom "Devoxx", le lien "https://www.devoxx" et qui dure entre le "01-12-2022" et le "07-12-2022"
    And qu'elle a un système de tarification par journée de présence à 400 € les 2 jours
    And qu'elle a un système de tarification par journée de présence à 600 € les 2.5 jours
    And qu'elle a un système de tarification par journée de présence à 950 € les 4.5 jours
    And qu'elle a une tarification pleine à 1000 €
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée avec le prix 1000 € et les prix réduits par jour de présence
      | price | attendingDays |
      | 400   | 2             |
      | 600   | 2.5           |
      | 950   | 4.5           |