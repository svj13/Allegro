Feature: Pentatonic Scales

  Scenario Outline: I can get the note representation of a pentatonic scale.
    Given I am on the transcript pane
    When I type the command '<return type> scale <scale name> pentatonic <scale type>'
    Then The following is printed to the transcript pane - <returnedNotes>
    Examples:
      | scale name | scale type | returnedNotes   | return type |
      | C          | major      | C D E G A C     | note        |
      | D          | major      | D E F# A B D    | note        |
      | E          | major      | E F# G# B C# E  | note        |
      | F          | major      | F G A C D F     | note        |
      | G          | major      | G A B D E G     | note        |
      | A          | major      | A B C# E F# A   | note        |
      | B          | major      | B C# D# F# G# B | note        |
      | C          | minor      | C Eb F G Bb C   | note        |
      | D          | minor      | D F G A C D     | note        |
      | E          | minor      | E G A B D E     | note        |
      | F          | minor      | F Ab Bb C Eb F  | note        |
      | G          | minor      | G Bb C D F G    | note        |
      | G          | minor      | G Bb C D F G    | note        |
      | A          | minor      | A C D E G A     | note        |
      | B          | minor      | B D E F# A B    | note        |


  Scenario Outline: I can get the midi representation of a pentatonic scale.
    Given I am on the transcript pane
    When I type the command '<return type> scale <scale name> pentatonic <scale type>'
    Then The following is printed to the transcript pane - <returnedNotes>
    Examples:
      | scale name | scale type | returnedNotes     | return type |
      | C          | major      | 60 62 64 67 69 72 | midi        |
      | D          | major      | 62 64 66 69 71 74 | midi        |
      | E          | major      | 64 66 68 71 73 76 | midi        |
      | F          | major      | 65 67 69 72 74 77 | midi        |
      | G          | major      | 67 69 71 74 76 79 | midi        |
      | A          | major      | 69 71 73 76 78 81 | midi        |
      | B          | major      | 71 73 75 78 80 83 | midi        |
      | C          | minor      | 60 63 65 67 70 72 | midi        |
      | D          | minor      | 62 65 67 69 72 74 | midi        |
      | E          | minor      | 64 67 69 71 74 76 | midi        |
      | F          | minor      | 65 68 70 72 75 77 | midi        |
      | G          | minor      | 67 70 72 74 77 79 | midi        |
      | A          | minor      | 69 72 74 76 79 81 | midi        |
      | B          | minor      | 71 74 76 78 81 83 | midi        |
