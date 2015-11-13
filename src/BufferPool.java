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


    /***
     * Class Constructor
     */
    public BufferPool(){
        pool = null;
        currentSize = 0;
        maxSize = 10;
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


}
