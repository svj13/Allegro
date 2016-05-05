#Manual Testing for Enharmonics
Listed are all of the manual tests that were carried out on the application, flagged with either
pass or fail

##Test 1: Notes correctly return higher and lower enharmonics.
PASS
1) Call 'enharmonic higher' for every note, not using octave specifiers.
2) Affirm that the correct value was returned, or an error message was returned if there
 was not a higher enharmonic.
3) Call 'enharmonic lower' for every note, not using octave specifiers.
4) Affirm that the correct value was returned, or an error message was returned if there
 was not a lower enharmonic.
5) Repeat steps 1-4 for notes that have a negative octave specifier - as we had been having troubles
 with these.

##Test 2: Notes correctly return all other enharmonics.

PASS
1) Call 'all enharmonics' for every note, not using octave specifiers.
2) Affirm that the correct enharmonics were returned for all notes.