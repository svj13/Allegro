package seng302.command;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import seng302.Environment;
import seng302.data.Note;

public class Help implements Command {

    String keyword;

    private static Map<String, Command> keywordToCommand = new HashMap<String, Command>();

    static {
        HashMap chordInfo = new HashMap<>();
        chordInfo.put("scale_type", "major");
        chordInfo.put("note", "C4");

        keywordToCommand.put("chord", new Chord(chordInfo, "chord"));
        keywordToCommand.put("play chord", new Chord(chordInfo, "play"));


        ArrayList chordNotes = new ArrayList();
        chordNotes.add(Note.lookup("A4"));
        chordNotes.add(Note.lookup("C4"));
        chordNotes.add(Note.lookup("F4"));
        keywordToCommand.put("find chord", new ChordFinder(chordNotes, false));
        //add this to keywords in the parser
        keywordToCommand.put("find chord all", new ChordFinder(chordNotes, true));

        keywordToCommand.put("crotchet duration", new CrotchetDuration());

        HashMap diatonicInfo = new HashMap<>();
        diatonicInfo.put("function", "");
        diatonicInfo.put("note", "C4");
        diatonicInfo.put("scale_type", "major");

        keywordToCommand.put("chord function", new Diatonic(diatonicInfo));

        HashMap diatonicInfo2 = new HashMap<>();
        diatonicInfo2.put("chord_type", "");
        diatonicInfo2.put("scaleNote", "C4");
        diatonicInfo2.put("chordNote", "C4");

        keywordToCommand.put("function of", new Diatonic(diatonicInfo2));
        keywordToCommand.put("quality of", new Diatonic("II"));

        keywordToCommand.put("simple enharmonic", new Enharmonic("C", 2));
        keywordToCommand.put("enharmonic higher", new Enharmonic("C", 0));
        keywordToCommand.put("enharmonic lower", new Enharmonic("C", 1));
        keywordToCommand.put("all enharmonics", new Enharmonic("C", 3));

        HashMap intervalInfo = new HashMap();

        keywordToCommand.put("interval", new IntervalCommand(intervalInfo, "note"));
        keywordToCommand.put("play interval", new IntervalCommand(intervalInfo, "play"));
        keywordToCommand.put("interval enharmonic", new IntervalCommand(intervalInfo, "equivalent"));

        keywordToCommand.put("scale signature with", new KeySignatureCommand(new ArrayList<String>()));

        HashMap keySigInfo = new HashMap();
        keySigInfo.put("note", "C4");

        keywordToCommand.put("scale signature num", new KeySignatureCommand(keySigInfo, "number"));
        keywordToCommand.put("scale signature", new KeySignatureCommand(keySigInfo, "notes"));

        keywordToCommand.put("midi", new Midi("C"));

        keywordToCommand.put("category of", new MusicalTerm("term", "category"));
        keywordToCommand.put("origin of", new MusicalTerm("term", "origin"));
        keywordToCommand.put("meaning of", new MusicalTerm("term", "meaning"));

        ArrayList musicalTermArray = new ArrayList();
        musicalTermArray.add("a");
        musicalTermArray.add("b");
        musicalTermArray.add("c");
        musicalTermArray.add("d");
        keywordToCommand.put("add musical term", new MusicalTerm(musicalTermArray));

        keywordToCommand.put("note", new NoteCommand("1"));

        keywordToCommand.put("play", new PlayNote("C"));

        keywordToCommand.put("set rhythm", new Rhythm("heavy", false));
        keywordToCommand.put("rhythm", new Rhythm());

        HashMap scaleInfo = new HashMap<>();
        scaleInfo.put("scale_type", "major");
        scaleInfo.put("note", "C4");

        keywordToCommand.put("scale", new Scale(scaleInfo, "note"));
        keywordToCommand.put("midi scale", new Scale(scaleInfo, "midi"));
        keywordToCommand.put("play scale", new Scale(scaleInfo, "play"));


        keywordToCommand.put("semitone up", new Semitone("C", true));
        keywordToCommand.put("semitone down", new Semitone("C", false));

        keywordToCommand.put("force set tempo", new Tempo("120", true));
        keywordToCommand.put("set tempo", new Tempo("120", false));
        keywordToCommand.put("tempo", new Tempo());

        keywordToCommand.put("version", new Version());

    }


    public Help() {
        keyword = "";
    }

    public Help(String keyword) {
        this.keyword = keyword;
    }

    public void execute(Environment env) {
        System.out.println(keyword);
        try {
            Command result = keywordToCommand.get(keyword);
            if (result != null) {
                env.getTranscriptManager().setResult(result.getHelp());
                System.out.println(result.getParams());
            } else {
                env.getTranscriptManager().setResult("Showing DSL Reference");
                env.getRootController().getTranscriptController().showDslRef();
            }
        } catch (Exception e) {
            e.printStackTrace();
            env.getTranscriptManager().setResult("Showing DSL Reference");
            env.getRootController().getTranscriptController().showDslRef();
        }


    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getCommandText() {
        return "help";
    }

}