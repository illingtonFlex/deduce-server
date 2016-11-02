package jcn.deduce.server;

public class RandomWordNotFoundException extends Exception
{
    //Parameterless Constructor
    public RandomWordNotFoundException() {}

    //Constructor that accepts a message
    public RandomWordNotFoundException(String message)
    {
        super(message);
    }
}
