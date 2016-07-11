Feature: Transcript Pane

  Scenario Outline: Play Notes
    Given I am on the transcript pane
    When I type the command 'play "<inputNote>"'
    Then The note '"<resultNote>"' should be played
    Examples:
      | inputNote | resultNote |
      | C4        | C4         |
      | C5        | C5         |
      | F7        | F7         |