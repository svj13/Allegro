package seng302.command;


import java.util.HashMap;
import java.util.Map;

import seng302.Environment;

public class Help implements Command {

    String keyword;

    private static Map<String, String> keywordToCommand = new HashMap<String, String>();

    static {
        keywordToCommand.put("chord", Chord.getHelp("display"));
        keywordToCommand.put("play chord", Chord.getHelp("play"));


        keywordToCommand.put("find chord", ChordFinder.getHelp());

        keywordToCommand.put("crotchet duration", CrotchetDuration.getHelp());

        keywordToCommand.put("simple enharmonic", Enharmonic.getHelp("simple"));
        keywordToCommand.put("enharmonic higher", Enharmonic.getHelp("higher"));
        keywordToCommand.put("enharmonic lower", Enharmonic.getHelp("lower"));

        keywordToCommand.put("help", Help.getHelp());

        keywordToCommand.put("interval", IntervalCommand.getHelp("display"));
        keywordToCommand.put("play interval", IntervalCommand.getHelp("play"));
        keywordToCommand.put("interval enharmonic", IntervalCommand.getHelp("enharmonic"));

        keywordToCommand.put("scale signature with", KeySignatureCommand.getHelp("with"));
        keywordToCommand.put("scale signature num", KeySignatureCommand.getHelp("num"));
        keywordToCommand.put("scale signature", KeySignatureCommand.getHelp(""));

        keywordToCommand.put("midi", Midi.getHelp());

        keywordToCommand.put("category of", MusicalTerm.getHelp("category"));
        keywordToCommand.put("origin of", MusicalTerm.getHelp("origin"));
        keywordToCommand.put("meaning of", MusicalTerm.getHelp("meaning"));
        keywordToCommand.put("add musical term", MusicalTerm.getHelp("add"));

        keywordToCommand.put("note", NoteCommand.getHelp());

        keywordToCommand.put("play", PlayNote.getHelp());

        keywordToCommand.put("set rhythm", Rhythm.getHelp("set"));
        keywordToCommand.put("rhythm", Rhythm.getHelp("get"));

        keywordToCommand.put("scale", Scale.getHelp("display"));
        keywordToCommand.put("midi scale", Scale.getHelp("midi"));
        keywordToCommand.put("play scale", Scale.getHelp("play"));


        keywordToCommand.put("semitone up", Semitone.getHelp("up"));
        keywordToCommand.put("semitone down", Semitone.getHelp("down"));

        keywordToCommand.put("force set tempo", Tempo.getHelp("force"));
        keywordToCommand.put("set tempo", Tempo.getHelp("set"));
        keywordToCommand.put("tempo", Tempo.getHelp("get"));

        keywordToCommand.put("version", Version.getHelp());

        keywordToCommand.put("", null);

    }


    public Help() {
        keyword = "";
    }

    public Help(String keyword) {
        this.keyword = keyword;
    }

    public void execute(Environment env) {
        String result = keywordToCommand.get(keyword);
        env.getTranscriptManager().setResult(result);

    }

    public static String getHelp() {
        return "Type help followed by any command to get more information on that command.";
    }


}