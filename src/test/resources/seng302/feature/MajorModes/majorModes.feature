Feature: major modes

  Scenario Outline: Retrieve the major mode of a given tonic and degree
    Given I am on the transcript pane
    When I type the command 'mode of  <tonic> <degree>'
    Then The following is printed to the transcript pane - <majorMode>

    Examples:
      | tonic | degree  | majorMode     |
      | C     | 2       | D dorian      |
      | C     | 4      | F lydian      |
      | G     | 3       | B phrygian    |
      | G     | 7      | F# locrian    |
      | A     | 1       | A ionian      |
      | A     | 6       | F# aeolian    |
      | Bb    | 4       | Eb lydian     |
      | Eb    | 5       | Bb mixolydian |
      | Gb    | 3      | Bb phrygian   |



  Scenario Outline: Retrieve the parent scale when given a tonic and major mode
    Given I am on the transcript pane
    When I type the command 'parent of  <tonic> <majorMode>'
    Then The following is printed to the transcript pane - <parentScale>

    Examples:
      | tonic | majorMode  | parentScale  |
      | D     | dorian     | C major      |
      | A     | phrygian   | F major      |
      | C     | mixolydian | F major      |
      | Bb    | ionian     | Bb major     |
      | D     | locrian    | Eb major     |
      | F     | locrain    | Gb major     |
      | F#    | mixolydian | B major      |
      | C#    | aeolian    | E major      |
      | Cb    | lydian     | Gb major     |
