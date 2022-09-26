Feature: Gestion des conférences

   Scenario: Création d'une conférence
      Given une conférence ayant le nom "Devoxx", le lien "https://www.devoxx" et qui dure entre le "01-12-2022" et le "03-12-2022"
      And qu'elle a un système de tarification early bird à 150 € avant le "31-08-2022"
      And qu'elle a un système de tarification early bird à 200 € entre le "01-09-2022" et le "30-09-2022"
      And qu'elle a un système de tarification early bird à 250 € entre le "01-10-2022" et le "31-10-2022"
      And qu'elle a une tarification pleine à 300 € à partir du "01-11-2022"
      When l utilisateur tente de l enregistrer
      Then la conférence est enregistée
         | price | startDate  | endDate    |
         | 150   | null       | 31-08-2022 |
         | 200   | 01-09-2022 | 30-09-2022 |
         | 250   | 01-10-2022 | 31-10-2022 |
         | 300   | 01-11-2022 | null       |