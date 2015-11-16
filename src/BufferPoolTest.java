import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Brandon Potts
 * @version November 15, 2015
 */
public class BufferPoolTest {

//    @org.junit.Before
//    public void setUp() throws Exception {
//
//    }


    @Test
    public void testInsert(){

        BufferPool bufferPool = new BufferPool();
        GISRecord recordOne = new GISRecord();

        // Sets the Data for the record One
        recordOne.setfName("Virginia Polytechnic Institute and State University Horticultural Research Area");
        recordOne.setsAC("VA");
        recordOne.setpLatitudeDMS("370958N");
        recordOne.setpLongitudeDMS("0802505W");

        GISRecord recordTwo = new GISRecord();
        recordTwo.setfName("Lane Stadium");
        recordTwo.setsAC("VA");
        recordTwo.setpLatitudeDMS("371312N");
        recordTwo.setpLatitudeDMS("0802505W");


        GISRecord recordThree = new GISRecord();
        recordThree.setfName("Shanks Hall");
        recordThree.setsAC("VA");
        recordThree.setpLatitudeDMS("371354N");
        recordThree.setpLongitudeDMS("0802512W");

        bufferPool.insert(recordOne);

        // tests insert
        assertEquals(1 , bufferPool.getCurrentSize());

        // tests exists
        assertTrue(bufferPool.exists(recordOne));
        assertFalse(bufferPool.exists(recordTwo));

        // add more nodes
        bufferPool.insert(recordTwo);
        bufferPool.insert(recordThree);
        assertTrue(bufferPool.exists(recordTwo));
        assertTrue(bufferPool.exists(recordThree));
        assertEquals(3, bufferPool.getCurrentSize());

        bufferPool.remove(recordOne);
        bufferPool.remove(recordTwo);
        bufferPool.remove(recordThree);
        assertEquals(0, bufferPool.getCurrentSize());
        assertFalse(bufferPool.exists(recordOne));
        assertFalse(bufferPool.exists(recordTwo));
        assertFalse(bufferPool.exists(recordThree));

    }
}