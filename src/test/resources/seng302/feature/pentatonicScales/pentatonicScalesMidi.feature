@pentatonicMidi
Feature: Pentatonic Scales Midi Representation

  Scenario Outline: Pentatonic Scales
    Given I am on the transcript pane
    When I type the command 'pentatonic midi scale <scale name> <scale type>'
    Then The following is printed to the transcript pane - <returnedNotes>
    Examples:
      | scale name | scale type | returnedNotes     |
      | C          | major      | 60 62 64 67 69 72 |
      | D          | major      | 62 64 66 69 71 74 |
      | E          | major      | 64 66 68 71 73 76 |
      | F          | major      | 65 67 69 72 74 77 |
      | G          | major      | 67 69 71 74 76 79 |
      | A          | major      | 69 71 73 76 78 81 |
      | B          | major      | 71 73 75 78 80 83 |
      | C          | minor      | 60 63 65 67 70 72 |
      | D          | minor      | 62 65 67 69 72 74 |
      | E          | minor      | 64 67 69 71 74 76 |
      | F          | minor      | 65 68 70 72 75 77 |
      | G          | minor      | 67 70 72 74 77 79 |
      | A          | minor      | 69 72 74 76 79 81 |
      | B          | minor      | 71 74 76 78 81 83 |
