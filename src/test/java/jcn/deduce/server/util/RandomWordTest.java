package jcn.deduce.server.util;

import jcn.deduce.server.model.DeduceWords;
import org.junit.Test;

import static org.junit.Assert.*;

public class RandomWordTest {

    @Test
    public void testGetRandomWord() throws Exception
    {
        String word = DeduceWords.getRandomWord();

        assertNotNull(word);
        assertTrue(word.length() == 5);
    }
}