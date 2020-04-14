
package ratkaisija;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tino
 */
public class RatkaisijaTest {
    
    Algorithm a;
    WordScanner wc;
    DatabaseHandler dbh;
    File testfile;

    public RatkaisijaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        a = new Algorithm();
        wc = new WordScanner();
        dbh = new DatabaseHandler();
        
        testfile = new File("testfile.txt");
        try {
            testfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        testfile.delete();
    }


    //Write tests here:
    
    @Test
    public void algorithmRecognizesIncorrectInputs() {
        assertEquals(false, a.checkInputLenght("this should be false"));
    }
    
    @Test
    public void algorithmRecognizesCorrectInputs() {
        assertEquals(true, a.checkInputLenght("aaaaaaaaaaaaaaaa")); //16 a's -> true
    }
    
    @Test
    public void algorithmSetsInputCorrectly() {
        String testString = "kuustoistacharia";
        if (a.checkInputLenght(testString)) { //check if test input is of correct lenght (it is), pretty redundant given
            a.setInput(testString);           //test no. 2 but eh
        }
        assertEquals(testString, a.getInput());
    }
    
    @Test
    public void testFileIsCreated() {
        assertTrue(testfile.exists());
    }
    
    @Test
    public void wordsCanBeAddedAndChecked() {
        wc.addWord("aaa", testfile);
        assertTrue(wc.checkWord("aaa",testfile));
    }
    
    @Test
    public void nonexistentWordsArentFound() {
        assertFalse(wc.checkWord("bbb",testfile));
    }
    
    @Test
    public void databaseHandlerDoesntAddExistingWords() {
        wc.addWord("aaa",testfile);
        assertFalse(dbh.addWord("aaa",testfile));
    }
    
    @Test
    public void databaseHandlerCanAddWords() {
        assertTrue(dbh.addWord("aaa",testfile));
        assertFalse(dbh.addWord("aaa",testfile));
    }
    
    @Test
    public void wordsCanBeDeleted() {
        wc.addWord("aaa", testfile);
        wc.delWord("aaa", testfile);
        assertFalse(wc.checkWord("aaa", testfile));
    }
    
    @Test
    public void databaseHandlerDeletesWord() {
        assertTrue(dbh.addWord("aaa", testfile));
        assertTrue(dbh.delWord("aaa", testfile));
    }
    
    @Test
    public void databaseHandlerReturnsFalseWhenDeletingNonexistentWord() {
        assertFalse(dbh.delWord("asdasdasdasd", testfile));
    }
}