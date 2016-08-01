Feature: Blues Scales

  Scenario Outline: Retrieve Notes in Specific Blues Scale
    Given I am on the transcript pane
    When I type the command 'play scale <startingNote> blues'
    Then The following is printed to the transcript pane - <scale>

    Examples:
      | startingNote | scale             |
      | C            | C Eb F Gb G Bb C  |
      | D            | D F G Ab A C D    |
      | E            | E G A Bb B D E    |
      | F            | F Ab Bb B C Eb F  |
      | G            | G Bb C Db D F G   |
      | A            | A C D Eb E G A    |
      | B            | B D E F F# A B    |

      | c            | C Eb F Gb G Bb C  |
      | d            | D F G Ab A C D    |
      | e            | E G A Bb B D E    |
      | f            | F Ab Bb B C Eb F  |
      | g            | G Bb C Db D F G   |
      | a            | A C D Eb E G A    |
      | b            | B D E F F# A B    |

      | C2           | C2 Eb2 F2 Gb2 G2 Bb2 C3  |
      | D2           | D2 F2 G2 Ab2 A2 C3 D3    |
      | E2           | E2 G2 A2 Bb2 B2 D3 E3    |
      | F2           | F2 Ab2 Bb2 B2 C3 Eb3 F3  |
      | G2           | G2 Bb2 C3 Db3 D3 F3 G3   |
      | A2           | A2 C3 D3 Eb3 E3 G3 A3    |
      | B2           | B2 D3 E3 F3 F#3 A3 B3    |

      | C5           | C5 Eb5 F5 Gb5 G5 Bb5 C6  |
      | D5           | D5 F5 G5 Ab5 A5 C6 D6    |
      | E5           | E5 G5 A5 Bb5 B5 D6 E6    |
      | F5           | F5 Ab5 Bb5 B5 C6 Eb6 F6  |
      | G5           | G5 Bb5 C6 Db6 D6 F6 G6   |
      | A5           | A5 C6 D6 Eb6 E6 G6 A6    |
      | B5           | B5 D6 E6 F6 F#6 A6 B6    |