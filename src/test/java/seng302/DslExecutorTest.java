package seng302;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.command.Command;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Created by isabelle on 7/03/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DslExecutorTest {
    @Mock
    private Environment env;
    private DslExecutor executor;

    @Before
    public void initializeApp() {
        executor = new DslExecutor(env);
    }

    @After
    public void clearApp() {
        executor = null;
    }

    @Test
    public void executesCommandsWithCorrectEnvironment() {
        Command mockCommand = mock(Command.class);

        executor.executeCommand(mockCommand);

        verify(mockCommand).execute(env);
    }

    @Test
    public void canParseValidCommandStrings() {
        Command command = executor.parseCommandString("midi C#1");

        assertThat(command, instanceOf(seng302.command.Midi.class));
    }

    @Test
    public void logsErrorOnInvalidNoteMidi() {
        Command command = executor.parseCommandString("midi cake");
        verify(env).error("'cake' is not a valid note.");
    }

    @Test
    public void returnsANullCommandOnParseFailure() {
        Command command = executor.parseCommandString("cake");

        assertThat(command, instanceOf(seng302.command.NullCommand.class));
    }

    @Test
    public void logsErrorOnParseFailure() {
        Command command = executor.parseCommandString("cake");

        verify(env).error("'cake' is not a valid command.");
    }

    @Test
    public void parsesNoteCommand() {
        Command command = executor.parseCommandString("note 100");
        assertThat(command, instanceOf(seng302.command.NoteCommand.class));
    }

    public void parseNegativeMidiCommand() {
        Command command = executor.parseCommandString("midi d-1");
        assertThat(command, instanceOf(seng302.command.Midi.class));
    }

    @Test
    public void logsErrorOnInvalidMidi() {
        Command command = executor.parseCommandString("note cake");
        verify(env).error("'cake' is not a valid MIDI value.");
    }

    @Test
    public void parsesEnharmonicHigherCommand() {
        Command command = executor.parseCommandString("enharmonic higher C4");
        assertThat(command, instanceOf(seng302.command.Enharmonic.class));
    }

    @Test
    public void logsErrorOnInvalidNote() {
        Command command = executor.parseCommandString("enharmonic higher cake");
        verify(env).error("'cake' is not a valid note.");
    }

    @Test
    public void parsesEnharmonicLowerCommand() {
        Command command = executor.parseCommandString("enharmonic lower C4");
        assertThat(command, instanceOf(seng302.command.Enharmonic.class));
    }

    @Test
    public void parsesEnharmonicLowerCommandWithMidi() {
        Command command = executor.parseCommandString("enharmonic lower 100");
        assertThat(command, instanceOf(seng302.command.Enharmonic.class));
    }

    @Test
    public void parsesSimpleEnharmonicCommand() {
        Command command = executor.parseCommandString("simple enharmonic C4");
        assertThat(command, instanceOf(seng302.command.Enharmonic.class));
    }

    @Test
    public void parsesHelpCommand() {
        Command command = executor.parseCommandString("help");
        assertThat(command, instanceOf(seng302.command.Help.class));
    }

    @Test
    public void parsesIntervalSemitoneCommand() {
        Command command = executor.parseCommandString("interval major second");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void logsErrorOnInvalidIntervalSemitoneCommand() {
        Command command = executor.parseCommandString("interval major fifth");
        verify(env).error("'major' is not a valid interval.");
    }

    @Test
    public void parsesIntervalCommand() {
        Command command = executor.parseCommandString("interval major second C");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void logsErrorOnInvalidNoteIntervalCommand() {
        Command command = executor.parseCommandString("interval major second H");
        verify(env).error("'H' is not a valid note.");
    }

    @Test
    public void parsesPlayIntervalCommand() {
        Command command = executor.parseCommandString("play interval major second C");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void logsErrorOnInvalidPlayIntervalCommand() {
        Command command = executor.parseCommandString("play interval cake");
        verify(env).error("'cake' is not a valid interval.");
    }

    @Test
    public void logsErrorOnInvalidPlayIntervalNoteCommand() {
        Command command = executor.parseCommandString("play interval perfect fifth H");
        verify(env).error("'H' is not a valid note.");
    }

    @Test
    public void parsesCrotchetDurationCommand() {
        Command command = executor.parseCommandString("crotchet duration");
        assertThat(command, instanceOf(seng302.command.CrotchetDuration.class));
    }

    @Test
    public void parsesAddMusicalTermCommand() {
        Command command = executor.parseCommandString("add musical term lento; speed; slow;french");
        assertThat(command, instanceOf(seng302.command.MusicalTerm.class));
    }

    @Test
    public void logsErrorOnAddMusicalTermCommand() {
        Command command = executor.parseCommandString("add musical term");
        verify(env).error("Please provide the name, origin, category and definition of the musical term.");
    }

    @Test
    public void logsErrorOnAddMusicalTermCommandTooFewArgs() {
        Command command = executor.parseCommandString("add musical term tempo");
        verify(env).error("Please provide the name, origin, category and definition of the musical term, separated by semicolons.");
    }

    @Test
    public void parsesMusicalTermCommand() {
        Command command = executor.parseCommandString("meaning of lento");
        assertThat(command, instanceOf(seng302.command.MusicalTerm.class));
    }

    @Test
    public void parsesMusicalTermOriginCommand() {
        Command command = executor.parseCommandString("origin of lento");
        assertThat(command, instanceOf(seng302.command.MusicalTerm.class));
    }

    @Test
    public void parsesMusicalTermCategoryCommand() {
        Command command = executor.parseCommandString("category of lento");
        assertThat(command, instanceOf(seng302.command.MusicalTerm.class));
    }

    @Test
    public void parsesPlayNoteCommand() {
        Command command = executor.parseCommandString("play G4");
        assertThat(command, instanceOf(seng302.command.PlayNote.class));
    }

    public void parsesPlayNegativeNoteCommand() {
        Command command = executor.parseCommandString("play D-1");
        assertThat(command, instanceOf(seng302.command.PlayNote.class));
    }

    @Test
    public void parsesPlayNoteMidiCommand() {
        Command command = executor.parseCommandString("play 100");
        assertThat(command, instanceOf(seng302.command.PlayNote.class));
    }

    @Test
    public void logsErrorOnInvalidPlayNoteCommand() {
        Command command = executor.parseCommandString("play cake");
        verify(env).error("'cake' is not a valid note.");
    }

    @Test
    public void parsesPlayScaleCommand() {
        Command command = executor.parseCommandString("play scale c major");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void parsesPlayScaleOctavesCommand() {
        Command command = executor.parseCommandString("play scale c major 3");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void parsesPlayScaleOctavesDirectionCommand() {
        Command command = executor.parseCommandString("play scale c major 3 updown");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void parsesPlayScaleOctavesDirectionOppositeCommand() {
        Command command = executor.parseCommandString("play scale c major updown 3");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void parsesPlayMelodicScale() {
        Command command = executor.parseCommandString("play scale c melodic minor updown 3");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void logsErrorOnPlayBadScale() {
        Command command = executor.parseCommandString("play scale c major cake");
        verify(env).error("Invalid input for this command. Please type 'help' and the command you want to use for more information.");
    }

    @Test
    public void parsesScaleCommand() {
        Command command = executor.parseCommandString("scale c major");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void parsesMinorScaleCommand() {
        Command command = executor.parseCommandString("scale c minor");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void parsesMelodicMinorScaleCommand() {
        Command command = executor.parseCommandString("scale c melodic minor");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void parsesMelodicMinorShortcut() {
        Command command = executor.parseCommandString("scale c mel minor");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void logsErrorOnScaleOctaveCommand() {
        Command command = executor.parseCommandString("scale c major 3");
        verify(env).error("Invalid input for this command. Please type 'help' and the command you want to use for more information.");
    }

    @Test
    public void logsErrorScaleOctaveDirectionCommand() {
        Command command = executor.parseCommandString("scale c major updown");
        verify(env).error("Invalid input for this command. Please type 'help' and the command you want to use for more information.");
    }

    @Test
    public void parsesMidiScaleCommand() {
        Command command = executor.parseCommandString("midi scale c major");
        assertThat(command, instanceOf(seng302.command.Scale.class));
    }

    @Test
    public void logsErrorOnInvalidScaleCommand() {
        Command command = executor.parseCommandString("scale H major");
        verify(env).error("'H' is not a valid note.");
    }

    @Test
    public void logsErrorOnInvalidScaleTypeCommand() {
        Command command = executor.parseCommandString("scale C blah");
        verify(env).error("'blah' is not a valid scale type.");
    }


    @Test
    public void logsErrorOnInvalidScaleExtraCommand() {
        Command command = executor.parseCommandString("scale C major cake");
        verify(env).error("Invalid input for this command. Please type 'help' and the command you want to use for more information.");
    }

    @Test
    public void parsesSemitoneUpCommand() {
        Command command = executor.parseCommandString("semitone up C");
        assertThat(command, instanceOf(seng302.command.Semitone.class));
    }

    @Test
    public void parsesSemitoneDownCommand() {
        Command command = executor.parseCommandString("semitone down B");
        assertThat(command, instanceOf(seng302.command.Semitone.class));
    }

    @Test
    public void parsesSemitoneDownMidiCommand() {
        Command command = executor.parseCommandString("semitone down 100");
        assertThat(command, instanceOf(seng302.command.Semitone.class));
    }

    @Test
    public void logsErrorOnInvalidNoteSemitone() {
        Command command = executor.parseCommandString("semitone up cake");
        verify(env).error("'cake' is not a valid note.");
    }

    @Test
    public void parsesTempoCommand() {
        Command command = executor.parseCommandString("tempo");
        assertThat(command, instanceOf(seng302.command.Tempo.class));
    }

    @Test
    public void parsesSetTempoCommand() {
        Command command = executor.parseCommandString("set tempo 100");
        assertThat(command, instanceOf(seng302.command.Tempo.class));
    }

    @Test
    public void parsesForceTempoCommand() {
        Command command = executor.parseCommandString("force set tempo 100");
        assertThat(command, instanceOf(seng302.command.Tempo.class));
    }

    @Test
    public void logsErrorOnInvalidSetTempoCommand() {
        Command command = executor.parseCommandString("set tempo cake");
        verify(env).error("'cake' is not a valid tempo.");
    }

    @Test
    public void logsErrorOnInvalidTempoCommand() {
        Command command = executor.parseCommandString("tempo cake");
        verify(env).error("Invalid input for this command. Please type 'help' and the command you want to use for more information.");
    }

    @Test
    public void parsesVersionCommand() {
        Command command = executor.parseCommandString("version");
        assertThat(command, instanceOf(seng302.command.Version.class));
    }

    /**
     * Chord related tests
     *
     */

    @Test
    public void basicChords() {
        Command command = executor.parseCommandString("chord f major");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord f4 major");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord C minor");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord f4 major inversion 1");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord C5 major inversion 3");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord f6 major 7th inversion 1");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord f6 major 7th inv 1"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));


    }

    @Test
    public void playBasicChords() {
        Command command = executor.parseCommandString("play chord f major");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord f4 major");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord C minor");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord f4 major inversion 1");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord C5 major inversion 3");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord f6 major 7th inversion 1");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord f6 major 7th inv 1"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));


    }

    @Test
    public void fourNoteChords(){
        //4 Note chords

        Command command = executor.parseCommandString("chord G1 seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord G1 half dim");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord G1 major seventh");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord G1 seventh");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord G1 minor 7th"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord G1 minor seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord G1 half dim seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord G1 half dim seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("chord G1 seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));


        //Play chord tests
    }

    @Test
    public void playFourNoteChords(){
        //4 Note chords

        Command command = executor.parseCommandString("play chord G1 seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord G1 half dim");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord G1 major seventh");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord G1 seventh");
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord G1 minor 7th"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord G1 minor seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord G1 half dim seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord G1 half dim seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));

        command = executor.parseCommandString("play chord G1 seventh"); //shortkey
        assertThat(command, instanceOf(seng302.command.Chord.class));


        //Play chord tests
    }


    @Test
    public void testIntervalUnison() {
        Command command = executor.parseCommandString("interval unison");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalMinorSecond() {
        Command command = executor.parseCommandString("interval minor second");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalMajorSecond() {
        Command command = executor.parseCommandString("interval major second");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalMinorThird() {
        Command command = executor.parseCommandString("interval minor third");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalMajorThird() {
        Command command = executor.parseCommandString("interval major third");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalPerfectFourth() {
        Command command = executor.parseCommandString("interval perfect fourth");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalAugmentedFourth() {
        Command command = executor.parseCommandString("interval augmented fourth");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalDiminishedFifth() {
        Command command = executor.parseCommandString("interval diminished fifth");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalMinorSixth() {
        Command command = executor.parseCommandString("interval minor sixth");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalMajorSixth() {
        Command command = executor.parseCommandString("interval major sixth");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalDiminishedSeventh() {
        Command command = executor.parseCommandString("interval diminished seventh");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalMinorSeventh() {
        Command command = executor.parseCommandString("interval minor seventh");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalMajorSeventh() {
        Command command = executor.parseCommandString("interval major seventh");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    @Test
    public void testIntervalPerfectOctave() {
        Command command = executor.parseCommandString("interval perfect octave");
        assertThat(command, instanceOf(seng302.command.IntervalCommand.class));
    }

    // DIATONIC TESTS

    @Test
    public void testQualityOfCommand() {
        Command command = executor.parseCommandString("quality of II");
        assertThat(command, instanceOf(seng302.command.Diatonic.class));
    }

    @Test
    public void testChordFunctionCommand() {
        Command command = executor.parseCommandString("chord function D major IV");
        assertThat(command, instanceOf(seng302.command.Diatonic.class));
    }

    @Test
    public void testFunctionOfCommand() {
        Command command = executor.parseCommandString("function of c major d minor 7th");
        assertThat(command, instanceOf(seng302.command.Diatonic.class));
    }


}