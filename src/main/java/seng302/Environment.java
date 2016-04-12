package seng302;

import seng302.utility.PitchComparisonTutorManager;
import seng302.utility.TranscriptManager;
import seng302.JSON.jsonHandler;

public class Environment {

    private DslExecutor executor;
    private TranscriptManager transcriptManager;
    private PitchComparisonTutorManager pctManager;
    private PitchComparisonTutorManager irtManager;
    private MusicPlayer player;



    private seng302.JSON.jsonHandler json;

    public Environment() {
        executor = new DslExecutor(this);
        player = new MusicPlayer();
        transcriptManager = new TranscriptManager();
        pctManager = new PitchComparisonTutorManager();
        irtManager = new PitchComparisonTutorManager();
        json = new seng302.JSON.jsonHandler(this);

    }

    /**
     * All errors are handled through here. They are then passed to the transcriptmanager to be
     * displayed.
     *
     * @param error_message The error message to be display to the user.
     */
    public void error(String error_message) {
        transcriptManager.setResult(String.format("[ERROR] %s", error_message));
    }

    public DslExecutor getExecutor() {
        return executor;
    }

    public TranscriptManager getTranscriptManager() {
        return transcriptManager;
    }

    public PitchComparisonTutorManager getPctManager() {
        return pctManager;
    }

    public PitchComparisonTutorManager getIrtManager() {
        return irtManager;
    }

    public void setTranscriptManager(TranscriptManager t) {
        this.transcriptManager = t;
    }

    public void setPctManager(PitchComparisonTutorManager p) {
        this.pctManager = p;
    }

    public MusicPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MusicPlayer player) {
        this.player = player;
    }

    public seng302.JSON.jsonHandler getJson() {
        return json;
    }

    public void setJson(seng302.JSON.jsonHandler json) {
        this.json = json;
    }

}