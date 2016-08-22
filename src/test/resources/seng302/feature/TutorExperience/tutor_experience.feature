Feature: Tutor Experience

  Scenario Outline: Receive a given amount of XP for completing a tutor
    Given I am in the pitch recognition tutor
    When I answer <correct> questions correctly out of <total> questions
    Then I am given the experience <score>

    Examples:
      | correct | total | score |
      | 10      | 10    | 1100  |

