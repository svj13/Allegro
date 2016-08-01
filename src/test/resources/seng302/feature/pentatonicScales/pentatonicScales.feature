Feature: Pentatonic Scales

  Scenario Outline: Pentatonic Scales
    Given I am on the transcript pane
    When I type the command 'scale <scale name> <scale type> pentatonic'
    Then The following is printed to the transcript pane - <returnedNotes>
    Examples:
      | scale name | scale type | returnedNotes   |
      | C          | major      | C D E G A C     |
      | D          | major      | D E F# A B D    |
      | E          | major      | E F# G# B C# E  |
      | F          | major      | F G A C D F     |
      | G          | major      | G A B D E G     |
      | A          | major      | A B C# E F# A   |
      | B          | major      | B C# D# F# G# B |
      | C          | minor      | C Eb F G Bb C   |
      | D          | minor      | D F G A C D     |
      | E          | minor      | E G A B D E     |
      | F          | minor      | F Ab Bb C Eb F  |
      | G          | minor      | G Bb C D F G    |
      | A          | minor      | A C D E G A     |
      | B          | minor      | B D E F# A B    |
