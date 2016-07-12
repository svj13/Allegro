#Manual Testing for DSL Reference Card
Listed are all of the manual tests that were carried out on the application, flagged with either
pass or fail

##Test 1: The example DSL commands given yield valid results.
FAIL
How the test failed:
1) For each command example given I typed it into the input field and checked that the result was
what would be expected. In the case of the command "play interval unison D", the result was an error.
This revealed a bug in the play interval section of the app.

Furthermore, the command "set rhythm 1/3,1/3,1/3" failed. I discovered the fractions should be
separated by space, not by comma.


##Test 2: The previous DSL command examples that failed yield valid results.
PASS
How the test passed:
1) Typed in "play interval unison D" and the result was as expected
2) Typed in "set rhythm 1/3 1/3 1/3" and the result was as expected