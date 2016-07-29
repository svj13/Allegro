Feature: Pentatonic Scales

  Scenario: Major Pentatonic Scales
    Given I am on the transcript pane
    When I type the command 'scale "<scale name>" major pentatonic'
    Then The following is printed to the transcript pane - "<returnedNotes>"

  Examples:
  | scale name | returnedNotes   |
  | C          | C D E G A C     |
  | D          | D E F# A B D    |
  | E          | E F# G# B C# E  |
  | F          | F G A C D F     |
  | G          | G A B D E G     |
  | A          | A B C# E F# A   |
  | B          | B C# D# F# G# B |

  Scenario: Minor Pentatonic Scales
    Given I am on the transcript pane
    When I type the command 'scale "<scale>" minor pentatonic'
    Then The following is printed to the transcript pane - "<returnedNotes>"

  Examples:
  | scale | returnedNotes |
  | C     | C Eb F G Bb C |