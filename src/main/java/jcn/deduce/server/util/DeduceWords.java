package jcn.deduce.server.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DeduceWords
{
    private static String WORDS_FILE = "deduceWordlist.txt";
    private static List<String> words = null;

    public static String getRandomWord()
    {
        init();

        return words.get(new Random().nextInt(words.size()));
    }

    public static List<String> getAllWords()
    {
        init();

        return words;
    }

    private static void init()
    {
        if (words == null)
        {
            loadWords();
        }
    }

    private static void loadWords()
    {
        Optional<URL> optFileUrl = Optional.ofNullable(DeduceWords.class.getClassLoader().getResource(WORDS_FILE));

        if(optFileUrl.isPresent())
        {
            File file = new File(optFileUrl.get().getFile());

            try
            {
                words = new ArrayList<>(Files.readAllLines(file.toPath()));
            }
            catch (IOException e)
            {
                System.err.println("ERROR READING WORDS FILE: " + e.getMessage());
            }
        }
        else
        {
            System.err.print("ERROR READING WORDS FILE: file path is null");
        }
    }
}
