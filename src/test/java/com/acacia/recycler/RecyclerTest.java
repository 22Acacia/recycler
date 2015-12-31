package com.acacia;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.acacia.recycler.Recycler;
import org.json.JSONObject;

/**
 * Unit test for simple App.
 */
public class RecyclerTest 
    extends TestCase
{
    protected Recycler testRecycler;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RecyclerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( RecyclerTest.class );
    }

    protected void setUp() 
    {
        testRecycler = new Recycler();
    }
    
    /**
     * Rigourous Test :-)
     */
    public void testRecycler()
    {
        String output = testRecycler.transform("{\"forty\":\"hives\"}");
        System.out.println(output);
        JSONObject oJson = new JSONObject(output);
        assertEquals("length is off", oJson.length(), 3);
        assertEquals("error_count is off", oJson.getInt("error_count"), 1);
        assertEquals("forty value is off", oJson.getString("forty"), "hives");
    }
}
