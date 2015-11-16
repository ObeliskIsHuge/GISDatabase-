import java.util.ArrayList;

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
    private ArrayList<Long> offsets;
    // Determines if the value is a tombstone or not
    private boolean tombStone;

    /****
     * Instantiates the class
     * @param pRecord GISRecord that is being stored
     * @param pOffset location that the record resides in the database
     */
    public HashTuple(GISRecord pRecord , long pOffset){
        this.record = pRecord;
        this.offsets = new ArrayList<>();
        this.offsets.add(pOffset);
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
     * Returns the file offsets of the GISRecord
     * @return file offsets
     */
    public ArrayList<Long> getOffsets() {
        return offsets;
    }

    /***
     * Returns the single offset in the array
     * @return single offset
     */
    public long getSigleOffset(){
        return offsets.get(0);
    }


    /***
     * Adds a file offset to the offsets
     * @param offset that will be added to the collection
     */
    public void addToOffset(long offset){
        this.offsets.add(offset);
    }

    /***
     * Changes the HashTuple to a tombstone
     */
    public void changeToTombStone(){
        this.record = null;
        this.offsets = null;
        this.tombStone = true;
    }

    /***
     * Will be true when the coordinate equals the coordinate of the tuple
     * @param comparedGeo coordinate that will be compared
     * @return true if the coordinates are equal
     *          false otherwise
     */
    public boolean compareCoordinate(GeoCoordinate comparedGeo){

        GeoCoordinate thisCoordinate = this.getRecord().buildCoordinates();
        // Will only be true when
        if(thisCoordinate.getLatitudeInSec() == comparedGeo.getLatitudeInSec() &&
                thisCoordinate.getLongitudeInSec() == comparedGeo.getLongitudeInSec()){
            return true;
        }
        return false;
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
