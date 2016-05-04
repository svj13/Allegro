package seng302;

import seng302.managers.ProjectHandler;
import seng302.gui.RootController;
import seng302.utility.EditHistory;
import seng302.utility.MusicalTermsTutorBackEnd;
import seng302.managers.TranscriptManager;

public class Environment {

    private DslExecutor executor;
    private TranscriptManager transcriptManager;
    private MusicalTermsTutorBackEnd mttDataManager;
    private MusicPlayer player;
    private String recordLocation;
    private EditHistory em = new EditHistory(this);

    public RootController getRootController() {
        return rootController;
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    // Root Controller
    private RootController rootController;

    private ProjectHandler json;


    private ProjectHandler projectHandler;

    public Environment() {
        executor = new DslExecutor(this);
        player = new MusicPlayer();
        transcriptManager = new TranscriptManager();
        mttDataManager = new MusicalTermsTutorBackEnd();
        projectHandler = new ProjectHandler(this);

    }

    /**
     * Resets the environment so it clears the existing saved information.
     */
    public void resetEnvironment() {
        executor = new DslExecutor(this);
        player = new MusicPlayer();
        transcriptManager = new TranscriptManager();
        mttDataManager = new MusicalTermsTutorBackEnd();
        projectHandler = new ProjectHandler(this);
        recordLocation = null;
        em = new EditHistory(this);
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

    public MusicalTermsTutorBackEnd getMttDataManager(){ return mttDataManager;}

    public void setTranscriptManager(TranscriptManager t) {
        this.transcriptManager = t;
    }

    public void setMttDataManager(MusicalTermsTutorBackEnd t){this.mttDataManager = t;}

    public String getRecordLocation() {
        return recordLocation;
    }

    public void setRecordLocation(String recordLocation) {
        this.recordLocation = recordLocation;
    }

    public MusicPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MusicPlayer m) {
        this.player = m;
    }

    public ProjectHandler getProjectHandler() {
        return projectHandler;
    }

    public void setProjectHandler(ProjectHandler p) {
        this.projectHandler = p;
    }

    public EditHistory getEditManager() {
        return this.em;
    }




}