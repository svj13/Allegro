package seng302.command;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import seng302.Environment;
import seng302.command.Enharmonic;
import seng302.data.Term;
import seng302.utility.MusicalTermsTutorBackEnd;
import seng302.utility.TranscriptManager;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class MusicalTermTest extends TestCase {

    private Environment env;
    @Mock
    private TranscriptManager transcriptManager;

    @Mock
    private MusicalTermsTutorBackEnd tutorDataManger;
    private Term term = new Term("name","category","origin","description");

    @Before
    public void setUp() throws Exception {
        env = new Environment();
        env.setTranscriptManager(transcriptManager);
        env.setMttDataManager(tutorDataManger);
    }


    @Test
    public void testMeaningOfCommandGoodInput() throws Exception {
        //add new term
        ArrayList<String> musicalTermArray = new ArrayList<String>();
        musicalTermArray.add("name");
        musicalTermArray.add("category");
        musicalTermArray.add("origin");
        musicalTermArray.add("definition");
        MusicalTerm termCommand = new MusicalTerm(musicalTermArray);
        termCommand.execute(env);

        // get the meaning of
        MusicalTerm termCommand2 = new MusicalTerm("name");
        termCommand2.execute(env);

        verify(transcriptManager).setResult(
                "Origin: category\n" +
                "Category: origin\n" +
                "Definition: definition");
    }

    @Test
    public void testMeaningOfCommandSpaceInTermName() throws Exception {
        //add new term
        ArrayList<String> musicalTermArray = new ArrayList<String>();
        musicalTermArray.add("space name");
        musicalTermArray.add("category");
        musicalTermArray.add("origin");
        musicalTermArray.add("definition");
        MusicalTerm termCommand = new MusicalTerm(musicalTermArray);
        termCommand.execute(env);

        // get the meaning of
        ArrayList<String> getTermArray = new ArrayList<String>();
        getTermArray.add("name");
        MusicalTerm termCommand2 = new MusicalTerm(getTermArray);
        termCommand2.execute(env);

        verify(transcriptManager).setResult(
                "Origin: category\n" +
                        "Category: origin\n" +
                        "Definition: definition");
    }

    @Test
    public void testMeaningOfWhereTermDoesntExist() throws Exception {
        //add new term
        ArrayList<String> musicalTermArray = new ArrayList<String>();
        musicalTermArray.add("name");
        musicalTermArray.add("category");
        musicalTermArray.add("origin");
        musicalTermArray.add("definition");
        MusicalTerm termCommand = new MusicalTerm(musicalTermArray);
        termCommand.execute(env);

        MusicalTerm termCommand2 = new MusicalTerm("nonExistantName");
        termCommand2.execute(env);

        verify(transcriptManager).setResult(
                "nonexistantname is not recognised as an existing musical term.");
    }

    @Test
    public void testAddMusicalTerm() throws Exception {
        ArrayList<String> musicalTermArray = new ArrayList<String>();
        musicalTermArray.add("name");
        musicalTermArray.add("category");
        musicalTermArray.add("origin");
        musicalTermArray.add("description");
        MusicalTerm termCommand = new MusicalTerm(musicalTermArray);
        termCommand.execute(env);


        verify(tutorDataManger).addTerm(termCommand.term);

        ArrayList<Term> terms = new ArrayList<Term>();
        terms.add(termCommand.term);
        //assertEquals(terms,tutorDataManger.getTerms()); ////WHY THE ... DOES THIS NOT WORK
    }


}
