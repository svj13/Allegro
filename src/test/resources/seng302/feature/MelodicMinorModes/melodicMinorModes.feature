Feature: Melodic Minor Modes

  Scenario Outline: Retrieve the major mode of a given tonic and degree
    Given I am on the transcript pane
    When I type the command 'melodic minor mode of  <tonic> <degree>'
    Then The following is printed to the transcript pane - <mode>

    Examples:
      | tonic | degree | mode               |
      | C     | 2      | D dorian b2        |
      | C     | 4      | F lydian dominant  |
      | G     | 3      | Bb lydian #5       |
      | G     | 7      | F# altered         |
      | A     | 1      | A majorminor       |
      | A     | 6      | F# locrian #2      |
      | Bb    | 4      | Eb lydian dominant |
      | Eb    | 5      | Bb mixolydian b6   |
      | Gb    | 3      | Bbb lydian #5      |


  Scenario Outline: Retrieve the parent scale when given a tonic and major mode
    Given I am on the transcript pane
    When I type the command 'melodic minor parent of  <scale>'
    Then The following is printed to the transcript pane - <parentScale>

    Examples:
      | scale              | parentScale      |
      | D dorian b2        | C melodic minor  |
      | F lydian dominant  | C melodic minor  |
      | Bb lydian #5       | G melodic minor  |
      | F# altered         | G melodic minor  |
      | A majorminor       | A melodic minor  |
      | F# locrian #2      | A melodic minor  |
      | Eb lydian dominant | Bb melodic minor |
      | Bb mixolydian b6   | Eb melodic minor |
      | Bbb lydian #5      | Gb melodic minor |


  Scenario Outline: Retrieve the scale when given a tonic and major mode
    Given I am on the transcript pane
    When I type the command 'melodic minor scale of  <tonic> <majorMode>'
    Then The following is printed to the transcript pane - <outputScale>

    Examples:
      | tonic | majorMode       | outputScale             |
      | D     | dorian b2       | D Eb F G A B C D        |
      | A     | lydian #5       | A B C# D# E# F# G# A    |
      | C     | lydian dominant | C D E F# G A Bb C       |
      | Bb    | mixolydian b6   | Bb C D Eb F Gb Ab Bb    |
      | D     | locrian #2      | D E F G Ab Bb C D       |
      | F     | altered         | F Gb Ab Bbb Cb Db Eb F  |
      | F#    | locrian #2      | F# G# A B C D E F#      |
      | C#    | mixolydian b6   | C# D# E# F# G# A B C#   |
      | Cb    | lydian dominant | Cb Db Eb F Gb Ab Bbb Cb |
