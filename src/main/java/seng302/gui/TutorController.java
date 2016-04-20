package seng302.gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import seng302.Environment;
import seng302.data.Interval;
import seng302.data.Note;
import seng302.data.Term;
import seng302.utility.TutorManager;
import seng302.utility.TutorRecord;

public class TutorController {
    VBox questionRows;

    public Environment env;

    public TutorManager manager;

    public TutorRecord record;

    public TutorController() {

    }

    public void create(Environment env){
        this.env = env;
        manager = env.getIrtManager();
    }

    /**
     * If the user chooses to re-test their self on their failed questions, this function
     * sets up the tutoring environment for that.
     */
    public void retest() {
        ArrayList<Pair> tempIncorrectResponses = new ArrayList<Pair>(manager.getTempIncorrectResponses());
        manager.clearTempIncorrect();
        manager.questions = tempIncorrectResponses.size();
        for(Pair pair : tempIncorrectResponses){
            HBox questionRow = generateQuestionRow(pair);
            questionRows.getChildren().add(questionRow);
            questionRows.setMargin(questionRow, new Insets(10, 10, 10, 10));
        }
    }

    public HBox generateQuestionRow(Pair intervalAndNote) {
        return new HBox();
    }

}