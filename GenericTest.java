

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class GenericTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GenericTest
{
    /**
     * Default constructor for test class GenericTest
     */
    public GenericTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testRandomness()
    {
        Generic g = new Generic();
        int minRandom = 1;
        int maxRandom = 100;
        int[] results = new int[maxRandom + 1];
        for(int i = 0; i < maxRandom * 1000; i++)
        results[g.randomNumber(minRandom, maxRandom)]++;
        
        for(int i = minRandom; i < results.length; i++)
        System.out.println(i + ":\t"+results[i]);
        
        if(1000/maxRandom - maxRandom > results[0] && results[0] > 1000/maxRandom + maxRandom )
        System.out.print(true);
        else
        System.err.print("Not in range");
        
    }
}

