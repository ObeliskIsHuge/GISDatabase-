/**
 *
 * Abstract class of the QuadTreeNode
 *
 * @author Brandon Potts
 * @version November 14, 2015
 */
public abstract class QuadTreeNode<T> {

    /***
     * Inserts a GISRecord into the node
     *
     * @param node QuadTree node that is being processed
     * @param record record that will be inserted
     * @param xLow lowest x-value
     * @param xHigh highest x-value
     * @param yLow lowest y-value
     * @param yHigh highest y-value
     * @return QuadTreeNode that contains the inserted record
     */
    public abstract QuadTreeNode<T> insert(QuadTreeNode<T> node, T record, long xLow,
                                           long xHigh, long yLow, long yHigh);


    /****
     * Prints the values that exist at the given Coordinate
     * @param coordinate coordinate that will be printed
     * @return Node that contains the records at the coordinate
     */
    public abstract QuadTreeNode<T> find(T coordinate,  long xLow, long xHigh, long yLow, long yHigh);

}
