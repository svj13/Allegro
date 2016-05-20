package seng302.command;


import seng302.Environment;

public class Help implements Command {

    String keyword;

    public Help() {
        keyword = "";
    }

    public Help(String keyword) {
        this.keyword = keyword;
    }

    public float getLength(Environment env) {
        return 0;
    };

    public void execute(Environment env) {
        String result = "";

        if (keyword.equals("note")) {
            result = "When followed by a valid midi number (within the range of 0-127), it will" +
                    " return the corresponding note name and its octave e.g. 'note 60' will return C4.";
        } else if (keyword.equals("add musical term")) {
            result = "When followed by a musical term in the format of 'name; origin; " +
                    "category; definition', it will add the musical term to the Musical Term " +
                    "dictionary.";
        } else if (keyword.equals("all enharmonics")) {
            result = "Return all the enharmonics of a given note.";
        } else if (keyword.equals("crotchet duration")) {
            result = "Returns the duration of a crotchet in milliseconds at the current tempo.";
        } else if (keyword.equals("enharmonic higher")) {
            result = "Returns the enharmonic that corresponds to the same note, a " +
                    "'letter' above the current note.";
        } else if (keyword.equals("enharmonic lower")) {
            result = "Returns the enharmonic that corresponds to the same note, a " +
                    "'letter' below the current note.";
        } else if (keyword.equals("force set tempo")) {
            result = "When followed by a tempo, it will set the given tempo, even if it" +
                    " is outside of the recommended range of 20-300BPM.";
        } else if (keyword.equals("meaning of")) {
            result = "When followed by a musical term, will display the definition of that" +
                    " term.";
        } else if (keyword.equals("midi")) {
            result = "When followed by a valid note, it will return its corresponding midi number" +
                    " within the range of 0-127. If an octave not specified to the note, it will default " +
                    " to octave 4. e.g. 'midi' C4 will return 60. It is not case sensitive. ";
        } else if (keyword.equals("play note")) {
            result = "When followed by a valid midi number or valid note, the corresponding" +
                    " note will be played.";
        } else if (keyword.equals("semitone up")) {
            result = "semitone up : When followed by a valid note or midi number, it will return" +
                    " the note that is a semitone higher.";
        } else if (keyword.equals("semitone down")) {
            result = "semitone down : When followed by a valid note or midi number, it will return" +
                    " the note that is a semitone lower.";
        } else if (keyword.equals("set tempo")) {
            result = "When followed by a valid tempo (20-300BPM) will change the tempo to that value.";
        } else if (keyword.equals("tempo")) {
            result = "Returns the current tempo. When the program is launched, it will have" +
                    " a default value of 120BPM.";
        } else if (keyword.equals("version")) {
            result = "Returns the current version number of the application.";
        } else if (keyword.equals("origin of")) {
            result = "When followed by a musical term, will display the origin of that" +
                    " term.";
        } else if (keyword.equals("category of")) {
            result = "When followed by a musical term, will display the category of that" +
                    " term.";
        } else if (keyword.equals("scale")) {
            result = "When followed by a valid scale (made up of a note and a scale type) " +
                    "the corresponding scale notes will be returned. e.g scale c major. ";
        } else if (keyword.equals("midi scale")) {
            result = "When followed by a valid scale (made up of a note and a scale type) " +
                    " the corresponding scale midi notes will be returned. ";
        } else if (keyword.equals("play scale")) {
            result = "When followed by a valid scale (made up of a note and a scale type)" +
                    " the corresponding scale will be played. Options for the number of octaves and direction" +
                    " can be given. E.g play scale c major [number of octaves] [up|updown|down]";
        } else if (keyword.equals("interval")) {
            result = "When followed by an interval name, it returns the number of semitones in that interval." +
                    " When followed by an interval name and a note, it returns the note that is the specified interval above the given note." +
                    " e.g interval perfect fifth C4 will return G4.";
        } else if (keyword.equals("play interval")) {
            result = "When followed by an interval name and a note, it will play the given note and then the note that is the specified interval above the given note.";
        } else if (keyword.equals("play chord")) {
            result = "When followed by a valid chord and a valid chord type (i.e. major, minor), will" +
                    " play the given chord. Type 'help chord types' for more information on" +
                    "valid chord types";
        } else if (keyword.equals("chord")) {
            result = "When followed by a valid chord and a valid chord type (i.e. major, minor), will" +
                    " return the corresponding notes that make up the given chord. Type 'help " +
                    "chord types' for more information on valid chord types\n\n" + "Valid chords:\n" +
                    "major: when put after a valid chord, will show/play the chord in major. " +
                    "A major chord that has a root note, a major third above this root, and a perfect " +
                    "fifth above this root note\n\n" +
                    "minor: when put after a valid chord, will show/play the chord in. A minor" +
                    " chord is a chord having a root, a minor third, and a perfect fifth. \n\n" +
                    "minor 7th/minor seventh: when put after a valid chord, will show/play the chord " +
                    "in minor seventh. A minor seventh chord is any nondominant seventh chord where " +
                    "the \"third\" note is a minor third above the root. \n\n" +
                    "major 7th/major seventh: when put after a valid chord, will show/play the " +
                    "chord in major 7th. A major seventh chord is a seventh chord where the" +
                    " \"third\" note is a major third above the root, and the \"seventh\" note " +
                    "is a major seventh above the root \n\n" +
                    "7th/seventh: when put after a valid chord, will show/play the chord in " +
                    "seventh. A seventh chord is a chord consisting of a triad plus a note " +
                    "forming an interval of a seventh above the chord's root.\n\n" +
                    "diminished: when put after a valid chord, will show/play the chord in " +
                    "diminished. It is a triad chord " +
                    "consisting of two minor thirds above the root\n\n" +
                    "diminished seventh/diminished 7th: when put after a valid chord, will show/" +
                    "play the chord in diminished 7th. A diminished seventh chord is a four note " +
                    "chord that comprises a diminished triad plus the interval of a diminished " +
                    "seventh above the root\n\n" +
                    "half diminished: when put after a valid chord, will show/play the chord in " +
                    "half diminished. the half-diminished seventh chord—also known as a " +
                    "half-diminished chord or a minor seventh flat five (m7?5)—is formed " +
                    "by a root note, a minor third, a diminished fifth, and a minor " +
                    "seventh \n";

        } else if (keyword.equals("")) {
            result = "" +
                    "\nadd musical term:\nWhen followed by a musical term in the format of 'name; origin; " +
                    "category; definition', will add the musical term to the Musical Term " +
                    "dictionary. \n\n" +
                    "all enharmonics:\nReturns all of the enharmonics of a given note. \n\n" +
                    "category of:\nWhen followed by a musical term, it returns the category of that term. \n\n" +
                    "chord:\nWhen followed by a valid chord and a valid chord type (i.e. major, minor), will" +
                    " return the corresponding notes that make up the given chord\n\n" +
                    "crotchet duration:\nReturns the duration of a crotchet in milliseconds at the current tempo.\n\n" +
                    "enharmonic higher:\nReturns the enharmonic that corresponds to the same note, a " +
                    "'letter' above the current note.\n\n" +
                    "enharmonic lower:\nReturns the enharmonic that corresponds to the same note, a " +
                    "'letter' below the current note. \n\n" +
                    "force set tempo:\nWhen followed by a tempo, it will set the given tempo, even if it" +
                    " is outside of the recommended range of 20-300BPM. \n\n" +
                    "interval:\nWhen followed by an interval name, it returns the number of semitones in that interval." +
                    " When followed by an interval name and a note, it returns the note that is the specified interval above the given note." +
                    " e.g interval perfect fifth C4 will return G4. \n\n" +
                    "meaning of:\nWhen followed by a musical term, it returns the definition of that" +
                    " term. \n\n" +
                    "midi:\nWhen followed by a valid note, it will return its corresponding midi number" +
                    " within the range of 0-127. If an octave not specified to the note, it will default " +
                    "to octave 4. e.g. 'midi' C4 will return 60. \n\n" +
                    "midi scale:\nWhen followed by a valid scale (made up of a note and a scale type) " +
                    " the corresponding scale midi notes will be returned. \n\n" +
                    "note:\nWhen followed by a valid midi number (within the range of 0-127), it will" +
                    " return the corresponding note name and its octave e.g. 'note 60' will return C4. \n\n" +
                    "origin of:\nWhen followed by a musical term, it returns the origin of that term. \n\n" +
                    "play chord:\n When followed by a valid chord and a valid chord type (i.e. major, minor), will" +
                    " play the given chord\n\n" +
                    "play interval:\nWhen followed by an interval name and a note, it will play the given " +
                    "note and then the note that is the specified interval above the given note.\n\n" +
                    "play note:\nWhen followed by a valid midi number or valid note, the corresponding" +
                    " note will be played. \n\n" +
                    "play scale:\nWhen followed by a valid scale (made up of a note and a scale type) " +
                    "the corresponding scale will be played. Options for the number of octaves and direction " +
                    "can be given. E.g play scale c major [number of octaves] [up|updown|down] \n\n" +
                    "scale:\nWhen followed by a valid scale (made up of a note and a scale type) " +
                    " the corresponding scale notes will be returned. \n\n" +
                    "semitone up:\nWhen followed by a valid note or midi number, it will return" +
                    " the note that is a semitone higher.  \n\n" +
                    "semitone down:\nWhen followed by a valid note or midi number, it will return" +
                    " the note that is a semitone lower. \n\n" +
                    "set tempo:\nWhen followed by a valid tempo (20-300BPM) will change the tempo to that value. \n\n" +
                    "tempo:\nReturns the current tempo. When the program is launched, it will have" +
                    " a default value of 120BPM. \n\n" +
                    "version:\nReturns the current version number of the application. \n\n";

        }

        env.getTranscriptManager().setResult(result);

    }


}