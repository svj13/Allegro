#Manual Testing for undo/redo functionality of setting instruments.
Listed are all of the manual tests that were carried out on the application, flagged with either
pass or fail

##Test 1: Undoing a single instrument selection reverts to the default instrument
PASS
How the test passed:
1) I used the "instrument" command to check that the default instrument (Bright Acoustic Piano)
 was set. Next, I used the "set instrument" command to change the instrument to Flute.
 Next, I used "play scale C major" and listened to the scale. It sounded like a wind instrument.
 I then used the "undo" command, and used "play scale C major" again. It sounded like a piano.
 As a secondary check, I used the "instrument" command, and it displayed the default instrument.

 ##Test 2: Redoing a single instrument selection
 PASS
 How the test passed:
 1) Continuing with the environment from the previous test, I used the "redo" command.
 I then listened to the output of "play scale C major", which again sounded like a wind instrument.
 I then used the "instrument" command, and it displayed "Flute".

 ##Test 3: Undoing several instrument selections reverts to the correct instrument
 PASS
 How the test passed:
 1) I began with the default instrument. I then entered the following commands:
    "set instrument 10" (Music Box)
    "set instrument 20" (Reed Organ)
    "set instrument 30" (Distortion Guitar)
 This changed the instrument three times.
 2)I typed "undo" once, then used "instrument". This returned "Reed Organ", as expected.
 I repeated the above step a second time, which returned "Music Box", as expected.
 Repeating the step a third time returned the instrument to the default.

 ##Test 4: Redoing several instrument selections
 PASS
 How the test passed:
 1) I began with the environment resulting from the previous test.
 2) I typed "redo", followed by "instrument". This returned "Music Box", as expected.
 I then repeated the above twice more, and each time it returned the expected instrument.