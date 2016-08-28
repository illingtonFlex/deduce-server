package jcn.deduce.server.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomWordTest {

    @Test
    public void testGetRandomWord() throws Exception
    {
        String word = RandomWord.getRandomWord();

        assertNotNull(word);
        assertTrue(word.length() == 5);
    }
}