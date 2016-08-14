Feature: Diatonic Chords

  Scenario Outline: Retrieve Quality of Diatonic Roman Numeral
    Given I am on the transcript pane
    When I type the command 'quality of <function>'
    Then The following is printed to the transcript pane - <quality>

    Examples:
      | function | quality             |
      | I        | major 7th           |
      | II       | minor 7th           |
      | III      | minor 7th           |
      | IV       | major 7th           |
      | V        | 7th                 |
      | VI       | minor 7th           |
      | VII      | half-diminished 7th |


  Scenario Outline: Retrieve Chord Function of a function and key
    Given I am on the transcript pane
    When I type the command 'chord function <function> <key>'
    Then The following is printed to the transcript pane - <chord function>

    Examples:
      | function | key      | chord function         |
      | I        | D major  | D major 7th            |
      | II       | D major  | E minor 7th            |
      | III      | D major  | F# minor 7th           |
      | IV       | D major  | G major 7th            |
      | V        | D major  | A 7th                  |
      | VI       | D major  | B minor 7th            |
      | VII      | D major  | C# half-diminished 7th |
      | I        | C# major | C# major 7th           |
      | II       | C# major | D# minor 7th           |
      | III      | C# major | E# minor 7th           |
      | IV       | C# major | F# major 7th           |
      | V        | C# major | G# 7th                 |
      | VI       | C# major | A# minor 7th           |
      | VII      | C# major | B# half-diminished 7th |

  Scenario Outline: Retrieve function of a chord and a key
    Given I am on the transcript pane
    When I type the command 'function of <chord> <key>'
    Then The following is printed to the transcript pane - <function>

    Examples:
      | chord         | key      | function       |
      | D major 7th   | D major  | I              |
      | D minor 7th   | D major  | Non Functional |
      | G minor 7th   | F major  | II             |
      | F# 7th        | B major  | V              |
      | F# 7th        | B# major | Non Functional |
      | Bbb major 7th | Fb major | IV             |