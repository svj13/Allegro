# User Manual for Allegro

## Commands


**add musical term**: When followed by a musical term in the format of 
'name; origin; category; definition', will add the musical term to the Musical Term dictionary.

**all enharmonics**: Returns all of the enharmonics of a given note.

**category of**: When followed by a musical term, it returns the category of that term.

**crotchet duration**: Returns the duration of a crotchet in milliseconds at the current tempo.

**enharmonic above**: Returns the enharmonic that corresponds to the same note, a 'letter' above the current note.

**enharmonic below**: Returns the enharmonic that corresponds to the same note, a 'letter' below the current note.

**force set tempo**: When followed by a tempo, it will set the given tempo, even if it is outside of the recommended range of 20-300BPM.

**interval**: When followed by an interval name, it returns the number of semitones in that interval.
              When followed by an interval name and a note, it returns the note that is the specified interval above the given note. 
              e.g interval perfect fifth C4 will return G4.

**meaning of**: When followed by a musical term, it returns the definition of that term. 

**midi**: Converts the given note into a MIDI value. e.g. midi C4 will result in 60.

**midi scale**: When followed by a valid scale (made up of a note and a scale type) the corresponding scale midi notes will be returned.

**note**: Converts the given MIDI value into a note. e.g. midi 60 will result in C4.

**origin of**: When followed by a musical term, it returns the origin of that term.

**play interval**: When followed by an interval name and a note, it will play the given note 
                   and then the note that is the specified interval above the given note.

**play note**: When followed by a valid midi number or valid note, the corresponding note will be played.

**play scale**: When followed by a valid scale (made up of a note and a 
                scale type) the corresponding scale will be played. Options 
                for the number of octaves and direction can be given. 
                E.g play scale c major \[number of octaves\] \[up|updown|down\]

**scale**: When followed by a valid scale (made up of a note and a scale type) the corresponding scale notes will be returned.

**semitone up** : Displays the note 1 semitone higher than the given note.
e.g. semitone up C4 will result in C#4.

**semitone down** : Displays the note 1 semitone lower than the given note.
e.g. semitone down B4 will result in Bb4.

**set tempo**: When followed by a valid tempo (20-300BPM) will change the tempo to that value. 

**tempo**: Returns the current tempo. When the program is launched, it will have a default value of 120BPM.

**version**: Displays the current version of the software.

## Keyboard Shortcuts

(Replace Ctrl with Cmd if using a Mac computer)

**Ctrl-O** Open project

**Ctrl-N** New project

**Ctrl-S** Save project

**Ctrl-Q** Quit Program

**Ctrl-Z** Undo

**Ctrl-Y** Redo
