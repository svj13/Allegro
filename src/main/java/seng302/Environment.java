package seng302;

import seng302.gui.RootController;
import seng302.utility.MusicalTermsTutorBackEnd;
import seng302.utility.TutorManager;
import seng302.utility.TranscriptManager;

public class Environment {

    private DslExecutor executor;
    private TranscriptManager transcriptManager;
    private TutorManager pctManager;
    private TutorManager irtManager;
    private TutorManager mttManager;
    private MusicalTermsTutorBackEnd mttDataManager; ///////////////////////////////////////
    private MusicPlayer player;

    public RootController getRootController() {
        return rootController;
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    // Root Controller
    private RootController rootController;




    private seng302.JSON.jsonHandler json;

    public Environment() {
        executor = new DslExecutor(this);
        player = new MusicPlayer();
        transcriptManager = new TranscriptManager();
        pctManager = new TutorManager();
        irtManager = new TutorManager();
        mttManager = new TutorManager();
        mttDataManager = new MusicalTermsTutorBackEnd(); ///////////////////////////////
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

    public TutorManager getPctManager() {
        return pctManager;
    }

    public TutorManager getIrtManager() {
        return irtManager;
    }

    public TutorManager getMttManager() {
        return mttManager;
    }

    public MusicalTermsTutorBackEnd getMttDataManager(){ return mttDataManager;} //////////////////////////////

    public void setTranscriptManager(TranscriptManager t) {
        this.transcriptManager = t;
    }

    public void setPctManager(TutorManager p) {
        this.pctManager = p;
    }
    public void setMttManager(TutorManager p) {
        this.mttManager = p;
    }

    public MusicPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MusicPlayer m) {
        this.player = m;
    }

    public seng302.JSON.jsonHandler getJson() {
        return json;
    }

    public void setJson(seng302.JSON.jsonHandler json) {
        this.json = json;
    }




}