Feature: Transcript Pane

  Scenario: Play Notes
    Given I am on the transcript pane
    When I type the command "Play C"
    Then The note C4 should be played