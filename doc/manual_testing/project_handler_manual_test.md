#Project Handler Manual Testing
Listed are all of the manual tests that were carried out on the application, flagged with either
pass or fail

##Test 1: Am able to correctly create a new project
PASS
How the test passed:
1) Create a new project without having a project open, should clear the environment
2) Quantities 1, 5, 10 and 25 were tested and manually counted, each time the tutor generates the
 correct number of question panes
3) The results pane also displays that the right quantity of questions were generated.
2) A second test was completed and the results pane checked for any incorrect quantities

##Test 2: Are able to correctly load a project
PASS
1) If project is in the correct directory
2) If project is renamed/deleted/moved/corrupt it should show appropriate message

##Test 3: Are able to save project
PASS
1) Saving existing project saves correctly
2) Save to new project saves correctly

##Test 4: Are able to import projects correctly.
PASS
1) Should not be able to import if project already exists in projects directory
2) Should copy all project files over when imported

##Test 4: All exported/imported documents default to project directory
PASS
1) tutor records
2) Transcript/Commands import
3) Transcript/Commands export

##Test 5: Projects quick launch menu is updated correctly.
PASS
1) When creating new project, name should be added to quick launch
2) When a project is imported, name should be added to quick launch.
3) If project is saved, name should be added to quick launch if not included.
4) If a project is imported but not saved, the quick launch name should still persist.
