import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

/**
 *  Class implements the Buffer Pool
 *
 * @author Brandon Potts
 * @version November 10, 2015
 */
public class BufferPool {

    // Linked list that holds the records
    private LinkedList<GISRecord> pool;
    // holds the current size of the buffer pool
    private int currentSize;
    // holds the maximum size of the buffer pool
    private int maxSize;
    // Holds the database file
    private RandomAccessFile databaseFile;


    /***
     * Class Constructor
     */
    public BufferPool(){
        pool = new LinkedList<>();
        currentSize = 0;
        maxSize = 10;
        this.databaseFile = null;
    }


    /***
     * Opens the database file
     * @param fileName name of the databasefile
     */
    public void openDataBaseFile(String fileName) throws FileNotFoundException {
        this.databaseFile = new RandomAccessFile(fileName, "r");
    }

    /***
     * Returns the maxSize of the BufferPool
     * @return maxSize of the
     */
    public int getMaxSize(){
        return maxSize;
    }

    /***
     * Returns the current size of the BufferPool
     * @return current size of the BufferPool
     */
    public int getCurrentSize(){
        return currentSize;
    }

    /***
     * Determines if a record exists in the pool or not
     * @param record that will be checked to see if it exists
     * @return true when the record exists
     *         false otherwise
     */
    public boolean exists(GISRecord record){

        // Gets the index of the first occurrence of the record
        if(pool.indexOf(record) != -1){
            return true;
        }
        return false;
    }

    /***
     * Inserts a record into the buffer pool
     * @param record is the record that will be inserted
     */
    public void insert(GISRecord record){

        // Checks to see if the list is full
        if(currentSize >= 10){

            int index = pool.indexOf(record);
            // Will be true when the record exists in the pool
            if(index != -1){
                pool.remove(index);
                pool.add(record);
                // Will be true when the record doesn't exist in the pool
            } else {
                // removes the LRU node
                pool.removeFirst();
                pool.add(record);
            }

        } else {
            pool.add(record);
            currentSize++;
        }
    }

    /***
     * Removes a record from the linked list
     * @param record that will be removed
     * @return true if the record was deleted
     *         false otherwise
     */
    public boolean remove(GISRecord record){

        int index = pool.indexOf(record);
        // Will be true when the record exists in the pool
        if(index != -1){
            currentSize--;
            pool.remove(index);
            return true;
        }

        return false;
    }

    /***
     * Searches the database file for the record at the given coordinates
     * @param soughtRecord record that contains the coordinates we're searching
     *                     for
     * @return record if found
     *          null otherwise
     */
    public HashTuple find(GISRecord soughtRecord) throws IOException {

        // Get rid of first line
        databaseFile.seek(0);
        databaseFile.readLine();
        long offset = databaseFile.getFilePointer();
        String currentLine = databaseFile.readLine();
        boolean found = false;
        LineParser lineParser;
        GISRecord currentRecord = null;

        // Keeps running until the record is found or the file ends
        while(!found){

            lineParser = new LineParser(currentLine);
            currentRecord = lineParser.buildGISRecord();

            // Will be true when the records locations are equal
            if(soughtRecord.getpLatitudeDMS().equals(currentRecord.getpLatitudeDMS()) &&
                    soughtRecord.getpLongitudeDMS().equals(currentRecord.getpLongitudeDMS())){

                found = true;
                break;
            }

            insert(currentRecord);
            offset = databaseFile.getFilePointer();
            currentLine = databaseFile.readLine();
        }

        databaseFile.seek(0);
        return new HashTuple(currentRecord , offset);
    }


}
