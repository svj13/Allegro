Feature: major modes

  Scenario Outline: Retrieve the major mode of a given tonic and degree
    Given I am on the transcript pane
    When I type the command 'mode of  <tonic> <degree>'
    Then The following is printed to the transcript pane - <majorMode>

    Examples:
      | tonic | degree  | majorMode     |
      | C     | 2       | D dorian      |
      | C     | 4       | F lydian      |
      | G     | 3       | B phrygian    |
      | G     | 7       | F# locrian    |
      | A     | 1       | A ionian      |
      | A     | 6       | F# aeolian    |
      | Bb    | 4       | Eb lydian     |
      | Eb    | 5       | Bb mixolydian |
      | Gb    | 3       | Bb phrygian   |



  Scenario Outline: Retrieve the parent scale when given a tonic and major mode
    Given I am on the transcript pane
    When I type the command 'parent of  <scale>'
    Then The following is printed to the transcript pane - <parentScale>

    Examples:
      | scale        | parentScale  |
      | D dorian     | C major      |
      | A phrygian   | F major      |
      | C mixolydian | F major      |
      | Bb ionian    | Bb major     |
      | D locrian    | Eb major     |
      | F locrian    | Gb major     |
      | F# mixolydian| B major      |
      | C# aeolian   | E major      |
      | Cb lydian    | Gb major     |



  Scenario Outline: Retrieve the scale when given a tonic and major mode
    Given I am on the transcript pane
    When I type the command 'scale of  <tonic> <majorMode>'
    Then The following is printed to the transcript pane - <outputScale>

    Examples:
      | tonic | majorMode  | outputScale            |
      | D     | dorian     | D E F G A B C D        |
      | A     | phrygian   | A Bb C D E F G A       |
      | C     | mixolydian | C D E F G A Bb C       |
      | Bb    | ionian     | Bb C D Eb F G A Bb     |
      | D     | locrian    | D Eb F G Ab Bb C D     |
      | F     | locrian    | F Gb Ab Bb Cb Db Eb F  |
      | F#    | mixolydian | F# G# A# B C# D# E F#  |
      | C#    | aeolian    | C# D# E F# G# A B C#   |
      | Cb    | lydian     | Cb Db Eb F Gb Ab Bb Cb |
