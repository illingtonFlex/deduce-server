package jcn.deduce.server.util;

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
    private static final String DEFAULT_OUTFILE = "./deduceWordlist.txt";

    private static Predicate<String> hasLengthOfFive = s -> s.length() == 5;
    private static Predicate<String> hasNoDuplicateCharacters = CreateWordsFileFromSource::hasNoDuplicateCharacters;

    public static void main(String[] args)
    {
        String filePath = parseArgs(args);

        try(Stream<String> wordStream = Files.lines(new File(filePath).toPath(), Charset.defaultCharset()))
        {
            Files.write(Paths.get(DEFAULT_OUTFILE),
                    wordStream
                        .filter(hasLengthOfFive)
                        .filter(hasNoDuplicateCharacters)
                        .map(String::toUpperCase)
                        .sorted(new ShuffledComparator<>())
                        .collect(Collectors.toList())
            );
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    private static String parseArgs(String[] args)
    {
        if(args.length < 1)
        {
            System.err.println("ERROR! Please provide path to words file as command line argument.");
            System.exit(-1);
        }

        return args[0];
    }

    private static boolean hasNoDuplicateCharacters(String s)
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
}