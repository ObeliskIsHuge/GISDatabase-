import org.junit.Test;

import static org.junit.Assert.*;
/**
 * @author Brandon Potts
 * @version November 15, 2015
 */
public class HashTableTest {

    @Test
    public void testHashTable(){
        HashTable<HashTuple> hashTable = new HashTable<>(HashTuple.class , 1019);

        GISRecord recordOne = new GISRecord();
        // Sets the Data for the record One
        recordOne.setfName("Virginia Polytechnic Institute and State University Horticultural Research Area");
        recordOne.setsAC("VA");
        recordOne.setpLatitudeDMS("370958N");
        recordOne.setpLongitudeDMS("0802505W");

        GISRecord recordTwo = new GISRecord();
        recordTwo.setfName("Lane Stadium");
        recordTwo.setsAC("VA");
        recordTwo.setpLatitudeDMS("371312S");
        recordTwo.setpLongitudeDMS("0802505E");


        GISRecord recordThree = new GISRecord();
        recordThree.setfName("Shanks Hall");
        recordThree.setsAC("VA");
        recordThree.setpLatitudeDMS("371354N");
        recordThree.setpLongitudeDMS("0802512W");

        HashTuple tupleOne = new HashTuple(recordOne, 1);
        HashTuple tupleTwo = new HashTuple(recordTwo, 2);
        HashTuple tupleThree = new HashTuple(recordThree, 3);

        // Tests inserts and find
        hashTable.insert(tupleOne);
        assertEquals(tupleOne, hashTable.find(tupleOne));
        assertNull(hashTable.find(tupleTwo));
        assertNull(hashTable.find(tupleThree));

        hashTable.insert(tupleTwo);
        hashTable.insert(tupleThree);
        assertEquals(tupleTwo, hashTable.find(tupleTwo));
        assertEquals(tupleThree, hashTable.find(tupleThree));
        assertEquals(3, hashTable.getFillCount());

    }
}
