Feature: Creating a New User

  Scenario Outline: A new user has the default level of 1
    Given I am on the sign in page
    When I register a new user with the name <username> and the password <password>
    Then The new user has the level 1
    Examples:
      | username | password |
      | username | password |
      | test     | test     |
      | newuser  | newpass  |

