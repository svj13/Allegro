#Manual Testing for Interval Tutor

Listed are all of the manual tests that were carried out on the application, flagged with either
pass or fail

##Test 1: Tutor correctly creates the specified number of questions
PASS
How the test passed:
1) The "Number of questions" indicator follows the slider when the number of questions changes.
2) Quantities 1, 5, 10 and 25 were tested and manually counted, each time the tutor generates the
 correct number of question panes
3) The results pane also displays that the right quantity of questions were generated.

##Test 2: Tutor will only display questions that were within the specified conditions
PASS
How the test passed:
1) Questions within note ranges of G0 - G#1, G3 - G#4 and G7 - G#8 were generated and tested to
 ensure that only notes within the correct ranges were generated.
2) A guitar tuner was used to identify the notes and ensure they were the right notes.

##Test 3: Tutor plays two notes correctly
PASS
How the test passed:
1) 10 questions were generated and their note pairs played to a guitar tuner, all notes were played
 and were identified.
2) These note pairs' intervals were calculated (after they had been identified) and checked against
 the tutors' answers, all interval answers were correct.

##Test 4: Tutor allows the user to select an answer and marks accordingly
PASS
How the test passed:
1) Drop down menus are displayed for the user to select their answers and allow selection.
2) Upon answer selection the tutor prevents answers from being changed, changes the tutor border
 color and no longer allows the question to be skipped.
3) 10 questions were generated and four were answered correctly, three incorrectly and three were
 skipped, each time the tutor changed color accordingly and dusplayed the correct answer for
 incorrect answers.

##Test 5: Tutor provides accurate feedback for the user when all questions are answered/skipped
PASS
How the test passed:
1) 8 questions were generated, three answered correctly, three incorrectly and two skipped. The
 results pane showed the expected response with the number of questions answered, and the result
 percentage.
2) This method was repeated with 8 questions twice more, each time the tutor giving the expected
 feedback.

##Test 6: Tutor retests the user on incorrectly answered questions
PASS
How the test passed:
1) After mixed answer questions on question sets of 8, the retest button was pressed on the results
 pane resulting in the correct number of questions remaining. This subset was answered, again with
 mixed correct and incorrect answers and the retest button pressed once more. The tutor generated
 the correct number of questions again.

##Test 7: Tutor clears properly after a question set is finished with
PASS
How the test passed:
1) After 8 question sets were generated and completed, the clear button was pressed resulting in a
 a newly generated empty tutor.
2) A second test was completed and the results pane checked for any incorrect quantities