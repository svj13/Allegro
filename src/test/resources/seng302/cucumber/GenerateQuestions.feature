Feature: Tutor Pane

  Scenario Outline: Generate Questions
    Given I am on the tutor pane
    When I press the Go button
    Then The questions should be generated
    Examples:
#        Press the button ?