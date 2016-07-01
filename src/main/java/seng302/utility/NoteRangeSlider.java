package seng302.utility;

import org.controlsfx.control.RangeSlider;
import org.controlsfx.control.spreadsheet.StringConverterWithFormat;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import seng302.data.Note;

/**
 * Created by isabelle on 20/05/16.
 */
public class NoteRangeSlider extends RangeSlider {

    RangeSlider slider;
    Label notes;
    Integer minNotes;

    public NoteRangeSlider(Label noteLabel, Integer minNotesInterval) {
        super(0, 127, 60, 72);
        slider = this;
        notes = noteLabel;
        minNotes = minNotesInterval;

        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(12);
        slider.setPrefWidth(340);
        slider.setShowTickLabels(true);

        slider.setLabelFormatter(new StringConverterWithFormat<Number>() {
            @Override
            public String toString(Number object) {
                Integer num = object.intValue();
                return Note.lookup(String.valueOf(num)).getNote();
            }

            @Override
            public Number fromString(String string) {
                return Note.lookup(string).getMidi();
            }
        });

        ChangeListener<Number> updateLabelLower = (observable, oldValue, newValue) -> {
            if ((Double) newValue > slider.getHighValue() - minNotes) {
                slider.setLowValue(slider.getHighValue() - minNotes);
            }
            updateText();
        };
        ChangeListener<Number> updateLabelHigher = (observable, oldValue, newValue) -> {
            if ((Double) newValue < slider.getLowValue() + minNotes) {
                slider.setHighValue(slider.getLowValue() + minNotes);
            }
            updateText();
        };

        slider.lowValueProperty().addListener(updateLabelLower);
        slider.highValueProperty().addListener(updateLabelHigher);
        updateText();

    }

    private void updateText() {
        notes.setText(slider.getLabelFormatter().toString(slider.getLowValue()) + " - "
                + slider.getLabelFormatter().toString(slider.getHighValue()));
    }
}
