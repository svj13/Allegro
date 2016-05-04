#Manual Testing for Pitch Comparison Tutor

Listed are all of the manual tests that were carried out on the application, flagged with either
pass or fail

##Test 1: Tutor will only display questions that were in the selected range
PASS
The slider restricts the user to select between octaves C-1 and G9
How the test passed:
1) I selected the range to be in between C5 and C8, chose 5 questions to be generated and clicked
"Go"
2) I pressed the play button, and had an online tuner nearby to indicate to me the note played
and the octave
3) All played notes were within the octave range
4) Repeated test for C-1 to C3, and C-6 to G-9
5) All played notes were within the octave range


##Test 2: Tutor will generate in between 1-50 questions as selected on the provided slider
PASS
How the test passed:
1) The slider restricts the user to the select between 1-50 questions. User cannot go above or
beyond
2) The following numbers 1, 10, 25 and 50 were selected as the "number of questions" on the slider
3) The number of questions generated, and were manually counted
4) The "number of questions" matched the manual count


##Test 3: Tutor will mark questions right or wrong correspondingly
PASS
How the test passed:
1) For each question, I had an online tuner open so I could determine which note was actually
higher/lower/same
2) I selected 5 as the number of questions for the range C-1 to G9 and pressed "Go"
3) I chose the right answer according to the tuner, and it would mark it as correct and the
border would light up green
4) I deliberately chose the wrong answer according to the tuner, and it would mark it as incorrect
and the border would light up red and display correct answer next to corresponding question
5) I repeated this test 5x and all questions I answered (whether right or wrong) responded as
expected

##Test 4: When all questions are answered, Pitch Comparison Results are displayed to user
PASS

Instant feedback once all questions have been answered. It will return the number of questions
that were answered/skipped, how many were answered correctly and incorrectly.
I manually calculated the statistics of 5 generated questions, and the feedback page matched my
calculations. I repeated this test 5x

##Test 5: The test should reset whenever the go button is pushed, or when retest is selected
PASS
How the test failed:
1) I selected 50 questions and clicked "Go" and answered only half of them
2) I then selected 3 questions and clicked "Go" and it generated 3 questions
3) In the Pitch Comparison Results, it disregarded the test that had 50 questions, and only
generated the results for the 3 questions (re clicking go reset the test)

##Test 6: All of the buttons should generate a response:
- "Go" should initialized the test with the given octave and the number of questions
- The play button should play the 2 notes that are to be compared
- Higher/Lower should respond by turning the question border either red/green (corresponding to
the answer
- Skip should skip the question
- When "Clear" is selected in the feedback window, it should prompt the user to save changes
PASS
All of the buttons behaved as expected
