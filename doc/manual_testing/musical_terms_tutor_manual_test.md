#Manual Testing for Musical Terms Tutor

Listed are all of the manual tests that were carried out on the application, flagged with either
pass or fail

##Test 1: Tutor will generate questions in between 1-50. It will not generate any questions if there
are no terms in the dictionary
PASS
How the test passed:
1) I tried to generate questions when there were no terms in the dictionary. It popped up with a
message "No terms added", and prompted the user to add the musical terms with the musical
terms command
2) When one or more terms are added, it will generate the number of questions of the chosen
range

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

##Test 4: When all questions are answered, Musical Term Results are displayed to user
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
3) After I had selected all of the 3 questions, the feedback page did showed up with the
relevant 3 questions

##Test 6: Tutor will mark questions right or wrong correspondingly
PASS
How the test passed:
1) I wrote out the list of musical terms I had added: a;b;c;d and w;x;y;z
2) I selected 5 as the number of questions for the range and selected "Go"
3) I chose the right answer according to my answers, and it would mark it as correct and the
border would light up green
4) I deliberately chose the wrong answer according to the tuner, and it would mark it as incorrect
and the border would light up red and display correct answer next to corresponding question
5) If I got some answers right, and some wrong, the border would light up as yellow
5) I repeated this test 5x and all questions I answered (whether right or wrong or partial)
responded as expected

##Test 7: In the drop down boxes, it should only contain options that belong to the corresponding
category (category, origin or definition)
PASS
1) referring to my dictionary, and adding more dictionary options, the drop down boxes had the
expected options

##Test 8: In the drop down boxes, even if there are more than 5 musical terms in the dictionary,
the drop down will only show 5 options
PASS
1) when there were less than 5 terms, it would only show the number of options that corresponded
to the number of items in the dictionary (e.g. if there were only 3 items, only 3 options
would be in the drop down box)
2) When there were more than 5 terms, the drop down box would show the option and 4 other randomly
selected options from the dictionary

##Test 9: The correct answer will always be an option in the drop down box
PASS
1) I tested this by having the musical terms added written out, and cross checking with the
questions to ensure the correct answer was an option




