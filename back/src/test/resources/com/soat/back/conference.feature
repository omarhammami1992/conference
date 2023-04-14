Feature: Gestion des conférences

  Scenario: Création d'une conférence avec un système de tarification early bird
    Given une conférence ayant le nom "Devoxx", le lien "https://www.devoxx" et qui dure entre le "01-12-2022" et le "03-12-2022" et qui aura lieu à "Paris" en "France"
    And qu'elle a un système de tarification early bird à 150 € avant le "31-08-2022"
    And qu'elle a un système de tarification early bird à 200 € entre le "01-09-2022" et le "30-09-2022"
    And qu'elle a un système de tarification early bird à 250 € entre le "01-10-2022" et le "31-10-2022"
    And qu'elle a une tarification pleine à 300 €
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée avec le prix 300 € et les intervalles de réduction early bird à "Paris" en "France"
      | price | startDate  | endDate    |
      | 150   | null       | 31-08-2022 |
      | 200   | 01-09-2022 | 30-09-2022 |
      | 250   | 01-10-2022 | 31-10-2022 |

  Scenario: Création d'une conférence avec un système tarification par groupe
    Given une conférence ayant le nom "Devoxx", le lien "https://www.devoxx" et qui dure entre le "01-12-2022" et le "03-12-2022" et qui aura lieu à "Paris" en "France"
    And qu'elle a un système de tarification de groupe à 150 € par personne lorsqu on réserve à partir de 10 billets
    And qu'elle a une tarification pleine à 300 €
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée avec le prix 300 € et un prix réduit de 150 € à partir de 10 participants à "Paris" en "France"

  Scenario: Création d'une conférence avec un système de tarification par journée de présence
    Given une conférence ayant le nom "Devoxx", le lien "https://www.devoxx" et qui dure entre le "01-12-2022" et le "07-12-2022" et qui aura lieu à "Paris" en "France"
    And qu'elle a un système de tarification par journée de présence à 400 € les 2 jours
    And qu'elle a un système de tarification par journée de présence à 600 € les 2.5 jours
    And qu'elle a une tarification pleine à 1000 €
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée avec le prix 1000 € et les prix réduits par jour de présence à "Paris" en "France"
      | price | attendingDays |
      | 400   | 2             |
      | 600   | 2.5           |

  Scenario: Récupérer la liste de toutes les conférences
    Given une liste de conférences enregistrées
      | id | name          | link                               | price | startDate  | endDate    | city  | country |
      | 1  | Devoxx        | https://www.devoxx                 | 300   | 01-09-2022 | 30-09-2022 | Paris | France  |
      | 2  | DataOps Rocks | https://summit-2022.dataops.rocks/ | 0     | 01-12-2022 | 30-12-2022 | Paris | France  |
    When l'utilisateur consulte la liste de conferences
    Then les conférences à venir s'affichent
      | id | name          | link                               | fullPrice | startDate  | endDate    | isOnline | city  | country |
      | 1  | Devoxx        | https://www.devoxx                 | 300       | 01-09-2022 | 30-09-2022 | False    | Paris | France  |
      | 2  | DataOps Rocks | https://summit-2022.dataops.rocks/ | 0         | 01-12-2022 | 30-12-2022 | True     | Paris | France  |

  Scenario: Récupérer une conférence existante
    Given une liste de conférences enregistrées
      | id | name          | link                               | price | startDate  | endDate    | city  | country |
      | 1  | Devoxx        | https://www.devoxx                 | 300   | 01-09-2022 | 30-09-2022 | Paris | France  |
      | 2  | DataOps Rocks | https://summit-2022.dataops.rocks/ | 0     | 01-12-2022 | 30-12-2022 | Paris | France  |
    When l'utilisateur consulte le détail de la conférence 1
    Then la conférence récupérée devrait contenir l id 1, le nom "Devoxx", le lien "https://www.devoxx", ayant le prix 300 et qui dure entre le "01-09-2022" et le "30-09-2022" et qui aura lieu à "Paris" ("France") "en présentielle"

  Scenario: Récupérer une conférence inexistante
    Given une liste de conférences enregistrées
      | id | name          | link                               | price | startDate  | endDate    |
      | 1  | Devoxx        | https://www.devoxx                 | 300   | 01-09-2022 | 30-09-2022 |
      | 2  | DataOps Rocks | https://summit-2022.dataops.rocks/ | 0     | 01-12-2022 | 30-12-2022 |
    When l'utilisateur consulte le détail de la conférence 3
    Then la conférence n'existe pas

  Scenario: Création d'une conférence
    Given une conférence ayant le nom "Devoxx", le lien "https://www.devoxx" et qui dure entre le "01-12-2022" et le "03-12-2022" et qui aura lieu à "Paris" en "France"
    And qu'elle a une tarification pleine à 300 €
    When l utilisateur tente de l enregistrer
    Then la conférence est enregistée avec le prix 300 €
    And la conférence n'a aucun système de tarification particulier
