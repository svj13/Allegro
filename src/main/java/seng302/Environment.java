package seng302;

import seng302.utility.TranscriptManager;

public class Environment {

    private DslExecutor executor;
    private TranscriptManager transcriptManager;

    public Environment() {
        executor = new DslExecutor(this);
        transcriptManager = new TranscriptManager();
    }

    public void error(String error_message) {
        transcriptManager.setResult(String.format("[ERROR] %s", error_message));
    }

    public DslExecutor getExecutor() {
        return executor;
    }

    public TranscriptManager getTranscriptManager() {
        return transcriptManager;
    }

    public void setTranscriptManager(TranscriptManager t) {
        this.transcriptManager = t;
    }
}