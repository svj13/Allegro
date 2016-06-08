Feature: Transcript Pane

  Scenario: Play Notes
    Given I am on the transcript pane
    When I type the command 'play C'
    Then The note 'C' should be played