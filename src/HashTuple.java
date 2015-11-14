/**
 *
 * Class is used to hold data that will be used to
 * store data that will be saved in the HashTable
 *
 * @author Brandon Potts
 * @version November 13, 2015
 */
public class HashTuple {

    // GISRecord
    private GISRecord record;
    // Offset that the GISRecord resides in the database file
    private int offset;
    // Determines if the value is a tombstone or not
    private boolean tombStone;


    /****
     * Instantiates the class
     * @param pRecord GISRecord that is being stored
     * @param pOffset location that the record resides in the database
     */
    public HashTuple(GISRecord pRecord , int pOffset){
        this.record = pRecord;
        this.offset = pOffset;
        this.tombStone = false;
    }

    /***
     * Returns the record for the Tuple
     * @return GISRecord
     */
    public GISRecord getRecord() {
        return record;
    }

    /***
     * Returns the file offset of the GISRecord
     * @return file offset
     */
    public int getOffset() {
        return offset;
    }

    public void changeToTombStone(){
        this.record = null;
        this.offset = -1;
        this.tombStone = true;
    }

    /***
     * Determines if two HashTuples are considered equals
     * @param obj HashTuple that will be compared to
     * @return returns true if the they tuples are equal
     *         false if they aren't equal
     */
    @Override
    public boolean equals(Object obj){

        // Will be true when Object is a HashTuple
        if(obj instanceof HashTuple){
            HashTuple tuple = (HashTuple)obj;
            GISRecord pRecord = tuple.getRecord();

            return this.record.equals(pRecord);
        }

        return false;
    }
}
