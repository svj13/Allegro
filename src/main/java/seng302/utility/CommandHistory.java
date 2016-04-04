package seng302.utility;

/**
 * Created by Jonty on 28-Mar-16.
 */
public class CommandHistory {
    TranscriptManager tm;
    private int historyLevel;
    private String storedInput;

    public CommandHistory(TranscriptManager manager) {
        tm = manager;

        historyLevel = -1;




    }

    public void resetLevel() {
        historyLevel = -1;
    }
    /**
     * Called when the 'Up' arrow is pressed when the command prompt is active. Cycles through
     * command history.
     *
     * @return new command text.
     */
    public String handleScrollUp(String currentText) {
        int size = tm.getTranscriptTuples().size();
        System.out.println(historyLevel);

        if (historyLevel == -1 && size > 0) {
            historyLevel = 1;

            storedInput = currentText;
            //return currentText;

        } else {
            if (historyLevel < size) {
                historyLevel++;
            } else {
                //historyLevel = 0;
                //return storedInput;



            }


        }
        if (tm.getTranscriptTuples().size() > 0) {
            return tm.getTranscriptTuples().get(size - historyLevel).getCommand();
        } else return currentText;
        //txtCommand.setText(env.getTranscriptManager().getTranscriptTuples().get(size-historyLevel).getCommand());
    }

    /**
     * Called when the 'Down' key is pressed when the command prompt is active. If the command
     * prompt is showing a previously used command from the history, then a newer command will be
     * shown.
     */
    //s trsdtrt
    public String handleScrollDown(String currentInput) {
        int size = tm.getTranscriptTuples().size();
        System.out.println(historyLevel);
        if (historyLevel > 1) {

            historyLevel--;

            return tm.getTranscriptTuples().get(size - historyLevel).getCommand();
        } else if (historyLevel == 1) {
            historyLevel--;

            if (storedInput == null || storedInput.equals("")) {
                System.out.println("empty!");
                historyLevel = -1;
                return "";

            } else {

                return storedInput;
            }


        } else if (historyLevel == 0) {
            historyLevel = -1;
            return "";

        }
        else if(historyLevel == 0){
            //historyLevel = -1;
           //return "";
            return storedInput;

        }
        return currentInput;
    }



}
