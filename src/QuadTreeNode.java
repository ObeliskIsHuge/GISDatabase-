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
     * @param x_low lowest x-value
     * @param x_high highest x-value
     * @param y_low lowest y-value
     * @param y_high highest y-value
     * @return QuadTreeNode that contains the inserted record
     */
    public abstract QuadTreeNode<T> insert(QuadTreeNode<T> node, T record, long x_low,
                                           long x_high, long y_low, long y_high);

}
