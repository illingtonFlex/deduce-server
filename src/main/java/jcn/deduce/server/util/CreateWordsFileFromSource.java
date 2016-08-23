package jcn.deduce.server.util;

import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateWordsFileFromSource
{
    private static final String DEFAULT_OUTFILE = "deduceWordlist.txt";

    private StopWatch stopWatch;
    private Predicate<String> hasLengthOfFive = s -> s.length() == 5;

    public CreateWordsFileFromSource()
    {
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    public static void main(String[] args)
    {
        CreateWordsFileFromSource cwffs = new CreateWordsFileFromSource();
        cwffs.run(parseArgs(args));
    }

    private void run(String filePath)
    {
        try(Stream<String> wordStream = Files.lines(new File(filePath).toPath(), Charset.defaultCharset()))
        {
            Files.write(Paths.get(DEFAULT_OUTFILE),
                    wordStream
                            .filter(hasLengthOfFive)
                            .filter(this::hasNoDuplicateCharacters)
                            .map(String::toUpperCase)
                            .sorted(new ShuffledComparator<>())
                            .collect(Collectors.toList())
            );
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
            quit();
        }

        System.out.println(
                "Completed in "
                        + stopWatch.getTime()
                        + " milliseconds.\nGenerated data file: "
                        + Paths.get(DEFAULT_OUTFILE).toAbsolutePath());
    }

    private boolean hasNoDuplicateCharacters(String s)
    {
        Map<Character, Character> map = new HashMap<>();
        char[] chars = s.toCharArray();

        for(Character c : chars)
        {
            if(map.containsKey(c))
            {
                return false;
            }
            else
            {
                map.put(c, c);
            }
        }

        return true;
    }

    private static String parseArgs(String[] args)
    {
        if(args.length < 1)
        {
            System.err.println("ERROR! Please provide path to words file as command line argument.");
            quit();
        }

        return args[0];
    }

    private static void quit()
    {
        System.exit(-1);
    }
}