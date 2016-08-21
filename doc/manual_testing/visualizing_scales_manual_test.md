#Manual Testing for visualizing scales
Listed are all of the manual tests that were carried out on the application, flagged with either
pass or fail


FUNCTIONALITY:
##Test 1:
When you click the "Display Scales" button, it launches the pop over. The "Display Scales" button changes text to
"Hide Display Scales"
PASS

##Test 2:
When you click "Hide Display Scales", the popover should close and change the text back to "Display Scales",
 but not clear input data. Input data should be maintained if "Display Scales" is clicked again. The exit button 'x'
 in the top right corner of the popover should behave the same as "Hide Display Scales"
 PASS

##Test 3:
"Select scale:" and "Select 2nd Scale (Optional):" should enable the user to input a note and a scale type from the drop
down
PASS

##Test 4:
When the OK button for "Select scale:" and "Select 2nd Scale (Optional):" is pressed, it should change the text of
the button to "Hide". It will also display the corresponding images on the keyboard. The start note should be
represented by a square, and the other notes of the scale displayed by a circle
PASS

##Test 5:
When the "Hide" button for "Select scale:" and "Select 2nd Scale (Optional):" is pressed, it should change the text of
the button back to "OK". It will also display hide the display images of the corresponding scale. It should not
affect the user input and the user input should be maintained
PASS

##Test 6:
Scale 1 should be represented by blue images consistently. Scale 2 should be represented by green images consistently
PASS

##Test 7:
When "Reset Scales" is selected, the text fields should clear of any text input, the buttons change to default "OK"
state, and the scale types should defaul to "Major"
PASS

##Test 8:
Each scale section of the pop over should be represented by a symbol that represents that scales colour. i.e
scale 1 text field should have a blue symbol next to it to indicate to the user what colour it will become on the
keyboard
PASS

##Test 9:
In the popover, there should be key symbols indicating to the user the difference between a square image and a circle
image in terms of the displayed images representing a scale on the keyboard
PASS



ERROR HANDLING:

##Test 10:
The text input fields for scale 1 and scale 2 should highlight with a red border upon pressing "OK" if the text input
and the scale type are identical. This includes if both fields are NULL or ""
PASS

##Test 11:
If a text input of a scale is empty, but the other scale isn't, and "OK" is selected for the scale that does not
have a text input, the text input border will highlight red for that scale only
PASS

##Test 12:
If an invalid text input is executed (i.e. not a note) the text input border for that scale will highlight red
PASS

##Test 13:
If valid input is executed in a text field with a red border, the border will revert back to the defaul grey colour
PASS

##Test 14:
If both text input fields are highlighted red from a user executing with two null text fields (or from both fields
having the same input), upon putting in valid input for one of these scales, executing will remove the red border
from both input fields
PASS



