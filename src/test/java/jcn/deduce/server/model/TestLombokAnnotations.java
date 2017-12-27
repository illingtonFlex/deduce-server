package jcn.deduce.server.model;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class TestLombokAnnotations
{
    private Object obj0 = new Object();
    private Object obj1 = new Object();

    private DeduceResponseEntity dre0 = new DeduceResponseEntity(Response.Status.CREATED, obj0, "String value");
    private DeduceResponseEntity dre1 = new DeduceResponseEntity();
    private DeduceResponseEntity dre2 = new DeduceResponseEntity(Response.Status.CREATED, obj0, "String value");
    private DeduceResponseEntity dre3 = new DeduceResponseEntity(Response.Status.CREATED, obj1, "String value");
    private DeduceResponseEntity dre4 = new DeduceResponseEntity(Response.Status.ACCEPTED, obj0, "String value");
    private DeduceResponseEntity dre5 = new DeduceResponseEntity(Response.Status.CREATED, obj0, "Different string value");

    @Test(expected = NullPointerException.class)
    public void allNullParametersThrowsNullPointerException()
    {
        DeduceResponseEntity dre = new DeduceResponseEntity(null, null, null);
    }

    @Test(expected = NullPointerException.class)
    public void nullStatausOnlyThrowsNullPointerException()
    {
        DeduceResponseEntity dre = new DeduceResponseEntity(null, new Object(), "String value");
    }

    @Test
    public void nullEntityOnlyDoesNotThrowNullPointerException()
    {
        DeduceResponseEntity dre = new DeduceResponseEntity(Response.Status.CREATED, null, "String value");
    }

    @Test(expected = NullPointerException.class)
    public void nullMessageOnlythrowssNullPointerException()
    {
        DeduceResponseEntity dre = new DeduceResponseEntity(Response.Status.CREATED, new Object(), null);
    }

    @Test
    public void thereShouldBeANoArgsConstructor()
    {
        DeduceResponseEntity dre = new DeduceResponseEntity();
    }

    @Test
    public void testEqualsMethod()
    {
        //compare empty object with populated fields
        assertFalse(dre0.equals(dre1));
        assertFalse(dre1.equals(dre0));

        assertTrue(dre0.equals(dre0)); // test pointer equality

        assertTrue(dre0.equals(dre2)); // test object equality for all params
        assertTrue(dre2.equals(dre0));

        assertFalse(dre2.equals(dre3)); // test object inequality for entity param
        assertFalse(dre3.equals(dre2));

        assertFalse(dre0.equals(dre4)); // test object inequality for status param
        assertFalse(dre4.equals(dre0));

        assertFalse(dre0.equals(dre5)); // test object inequality for message param
        assertFalse(dre5.equals(dre0));
    }

    @Test
    public void testHashCodeMethod()
    {
        assertNotEquals(dre0.hashCode(), dre1.hashCode());
        assertEquals(dre0.hashCode(), dre2.hashCode());
        assertNotEquals(dre0.hashCode(), dre3.hashCode());
    }

    @Test
    public void testToStringMethod()
    {
        String dre0ExpectedToStringFormatted =
                String.format("DeduceResponseEntity(status=Created, entity=%s, message=String value)",
                        obj0.toString());

        assertEquals(dre0.toString(), dre0ExpectedToStringFormatted);
    }
}
