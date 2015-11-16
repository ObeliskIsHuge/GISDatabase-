import java.util.ArrayList;
/**
 *
 * QuadTree implementation
 *
 * @author Brandon Potts
 * @version November 14, 2015
 */
public class QuadTree<T> {

    // holds the root
    private QuadTreeNode<T> root;
    // holds the minimum x-value
    private long xMin;
    // holds the maximum x-value
    private long xMax;
    // holds the minimum y-value
    private long yMin;
    // holds the maximum y-value
    private long yMax;

    private enum Direction {NW , NE, SE, SW , NODIRECTION}


    /***
     * Instantiates the class
     * @param xMin minimum x-value
     * @param xMax maximum x-value
     * @param yMin minimum y-value
     * @param yMax maximum y-value
     */
    public QuadTree(long xMin, long xMax, long yMin, long yMax){
        root = new QuadTreeLeaf();
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    /***
     * Sets the y-Max value
     * @param yMax new y-Max
     */
    public void setyMax(long yMax) {
        this.yMax = yMax;
    }

    /***
     * Sets the minimum y value
     * @param yMin new minimum y value
     */
    public void setyMin(long yMin) {
        this.yMin = yMin;
    }

    /***
     * Sets the x-max value
     * @param xMax maximum x value
     */
    public void setxMax(long xMax) {
        this.xMax = xMax;
    }

    /***
     * Sets the minimum x-value
     * @param xMin minimum x-value
     */
    public void setxMin(long xMin) {
        this.xMin = xMin;
    }


    /***
     * Determines if a coordinate is in bounds or not
     * @param coordinate that will be checked
     * @return true if the coordinate is in bounds
     *         false otherwise
     */
    public boolean inBounds(GeoCoordinate coordinate){


        // Will be true when the value is inside the rectangle
        if(coordinate.getLatitudeInSec() <= yMax && coordinate.getLatitudeInSec() >= yMin &&
                coordinate.getLongitudeInSec() <= xMax && coordinate.getLongitudeInSec() >= xMin){
            return true;
        }
        return false;
    }

    /***
     * Inserts a tuple into the tree
     * @param tuple tuple that will be inserted
     */
    public void insert(T tuple){
        this.root = root.insert(this.root, tuple, xMin, xMax, yMin, yMax);
    }

    /***
     * Implements a Quad Tree Leaf
     */
    private class QuadTreeLeaf extends QuadTreeNode<T>{

        // maximum bucket size
        private final int MAX_BUCKET_SIZE = 4;
        // amount of objects in the bucket
        private int leafSize;
        // list holds the records
        private ArrayList<T> buckets;

        /***
         * Instantiates the class
         */
        public QuadTreeLeaf(){
            this.leafSize = 0;
            this.buckets = new ArrayList<>();
        }

        /***
         * Returns the size of the leaf
         * @return size of the leaf
         */
        public int getLeafSize(){
            return leafSize;
        }

        /***
         * Returns the buckets
         * @return returned bucket
         */
        public ArrayList<T> getBuckets(){
            return buckets;
        }


        /***
         * Adds values to the bucket
         * @param tuple value that will be added
         */
        @SuppressWarnings("unchecked")
        private void addToBucket(HashTuple tuple){
            this.buckets.add((T)tuple);
            this.leafSize++;
        }

        /****
         * Inserts a record into the QuadLeaf
         *
         * @param node node that is being processsed
         * @param record record that will be inserted
         * @param x_low lowest x-value
         * @param x_high highest x-value
         * @param y_low lowest y-value
         * @param y_high highest y-value
         * @return QuadTreeNode that holds the record
         */
        @SuppressWarnings("unchecked")
        public QuadTreeNode<T> insert(QuadTreeNode<T> node, T record, long x_low,
                                      long x_high, long y_low, long y_high){

            // Will be true when we're working with a Hash tuple
            if(record instanceof HashTuple && node.getClass().equals(QuadTreeInternal.class)){

                HashTuple tuple = (HashTuple) record;
                // Will be true when no values exist
                if(leafSize == 0){
                    this.buckets.add((T)tuple);
                    leafSize++;
                    // Will be true when we have less than 4 records
                } else if (leafSize <= MAX_BUCKET_SIZE){

                    // Will be true if the current tuple exists in the leaf
                    if(this.buckets.contains((T)tuple)){
                        int index = this.buckets.indexOf(tuple);
                        T duplicateRecord = this.buckets.get(index);

                        // Will be true when the record is a HashTuple
                        if(duplicateRecord instanceof HashTuple){

                            HashTuple duplicateTuple = (HashTuple) duplicateRecord;
                            duplicateTuple.addToOffset(tuple.getSigleOffset());
                            this.buckets.remove(index);
                            this.buckets.add((T)duplicateTuple);
                        }
                        // Adds another record to the bucket list
                    } else {
                        this.buckets.add((T)tuple);
                        leafSize++;
                    }
                    // Will be true when we've reached the max amount of records
                    // and need to change to an internal node
                } else {

                    QuadTreeInternal internal = new QuadTreeInternal();
                    // Iterate through the buckets and reinserting the values
                    for (T inputTuple : this.buckets){
                        internal.insert(node , inputTuple, x_low, x_high, y_low, y_high);
                    }
                    return internal;
                }
            }

            return this;
        }

        /***
         * Returns the Node that contains the
         * @param coordinate coordinate that will be printed
         * @return node that contains the coordinates
         */
        public QuadTreeNode<T> find(T coordinate,  long x_low, long x_high, long y_low, long y_high){

            // Will be true only when coordinate is an instance of GeoCoordinate
            if(coordinate instanceof GeoCoordinate){

                QuadTreeLeaf returnLeaf = new QuadTreeLeaf();
                GeoCoordinate geoCoordinate = (GeoCoordinate) coordinate;

                // iterates over the entire bucket array
                for(T inputTuple: this.buckets){
                    HashTuple tuple = (HashTuple) inputTuple;

                    if(tuple.compareCoordinate(geoCoordinate)){
                        returnLeaf.addToBucket(tuple);
                    }
                }

                // makes sure the leaf has values
                if(returnLeaf.getLeafSize() != 0){
                    return returnLeaf;
                }
            }
            return null;
        }
    }


    /***
     * Internal node of the QuadTree
     */
    private class QuadTreeInternal extends QuadTreeNode<T>{

        // QuadTree node that represents the North West
        private QuadTreeNode<T> northWest;
        // QuadTree node that represents the North East
        private QuadTreeNode<T> northEast;
        // QuadTree node that represents the South East
        private QuadTreeNode<T> southEast;
        // QuadTree node that represents the South West
        private QuadTreeNode<T> southWest;




        /****
         * Instantiates a new QuadTreeInternal
         */
        public QuadTreeInternal(){
            this.northWest = new QuadTreeLeaf();
            this.northEast = new QuadTreeLeaf();
            this.southEast = new QuadTreeLeaf();
            this.southWest = new QuadTreeLeaf();
        }

        /***
         * Sets the North West Node
         * @param northWest new NorthWest Node
         */
        public void setNorthWest(QuadTreeNode<T> northWest) {
            this.northWest = northWest;
        }

        /***
         * Sets the North East Node
         * @param northEast new NorthEast Node
         */
        public void setNorthEast(QuadTreeNode<T> northEast) {
            this.northEast = northEast;
        }

        /***
         * Sets the South East Node
         * @param southEast new SouthEast Node
         */
        public void setSouthEast(QuadTreeNode<T> southEast) {
            this.southEast = southEast;
        }

        /***
         * Sets the South West Node
         * @param southWest new SouthWest Node
         */
        public void setSouthWest(QuadTreeNode<T> southWest) {
            this.southWest = southWest;
        }


        public QuadTreeNode<T> getNorthWest() {
            return northWest;
        }

        public QuadTreeNode<T> getNorthEast() {
            return northEast;
        }

        public QuadTreeNode<T> getSouthEast() {
            return southEast;
        }

        public QuadTreeNode<T> getSouthWest() {
            return southWest;
        }

        /****
         * Determines which direction the record resides
         *
         * @param record record that is being fit in
         * @param xLow lowest x-value
         * @param xHigh highest x-value
         * @param yLow lowest y-value
         * @param yHigh highest y-value
         * @return Direction that the record should be inserted
         */
        private Direction whichDirection(GISRecord record, GeoCoordinate coordinate,  long xLow, long xHigh,
                                         long yLow, long yHigh){

            long longitude;
            long latitude;

            // Will be true when record wasn't passed
            if(record != null) {
                longitude = record.buildCoordinates().getLongitudeInSec();
                latitude = record.buildCoordinates().getLatitudeInSec();
            } else {
                longitude = coordinate.getLongitudeInSec();
                latitude = coordinate.getLatitudeInSec();
            }

            long xCenter = (xLow + xHigh) / 2;
            long yCenter = (yLow + yHigh) / 2;

            // Checks to see if the point lies on the origin
            if(longitude == xCenter && latitude == yCenter){
                return Direction.NE;
                // North East
            } else if (longitude >= xCenter && latitude >= yCenter){
                return Direction.NE;
                //North West
            } else if(longitude <= xCenter && latitude >= yCenter){
                return Direction.NW;
                // South West
            } else if(longitude <= xCenter && latitude <= yCenter){
                return Direction.SW;
                // South East
            } else if(longitude >= xCenter && latitude <= yCenter){
                return Direction.SE;
            }
            return Direction.NODIRECTION;
        }


        /****
         * Inserts a record into the internal Node
         *
         * @param node node that is being processed
         * @param record record that will be inserted
         * @param xLow lowest x-value
         * @param xHigh highest x-value
         * @param yLow lowest y-value
         * @param yHigh highest y-value
         * @return QuadTreeNode that contains the inserted data
         */
        public QuadTreeNode<T> insert(QuadTreeNode<T> node, T record, long xLow, long xHigh, long yLow, long yHigh){

            // Will be true only when the record is a HashTuple
            if(record instanceof HashTuple){

                HashTuple tuple = (HashTuple) record;
                long xCenter = (xLow + xHigh) / 2;
                long yCenter = (yLow + yHigh) / 2;
                Direction recordDirection = whichDirection(tuple.getRecord(), null, xLow, xHigh, yLow, yHigh);

                // Determines which action to take TODO THESE MIGHT NEED TO BE CHANGED
                switch (recordDirection){
                    case NE:
                        setNorthEast(insert(this.northEast, record, xCenter, xHigh, yCenter, yHigh));
                        break;
                    case NW:
                        setNorthWest(insert(this.northWest, record, xLow, xCenter, yCenter, yHigh));
                        break;
                    case SW:
                        setSouthWest(insert(this.southWest, record, xLow, xCenter, yLow, yCenter));
                        break;
                    case SE:
                        setSouthEast(insert(this.southEast, record, xCenter, xHigh, yLow, yCenter));
                        break;
                    default:
                        // Do Nothing
                        break;
                }

            }

            return this;
        }

        /****
         * Searches the Node for values that match the GeoCoordinate
         * @param coordinate coordinate that will be printed
         * @return Node that contains the records at the given coordinate
         */
        public QuadTreeNode<T> find(T coordinate,  long xLow, long xHigh, long yLow, long yHigh){

            QuadTreeNode<T> returnNode = null;
            // Will only be true if the coordinate is a GeoCoordinate
            if(coordinate instanceof GeoCoordinate){

                GeoCoordinate geoCoordinate = (GeoCoordinate) coordinate;
                long xCenter = (xLow + xHigh) / 2;
                long yCenter = (yLow + yHigh) / 2;

                Direction recordDirection = whichDirection(null, geoCoordinate, xLow, xHigh, yLow, yHigh);
                switch (recordDirection){
                    case NE:
                        returnNode = find(coordinate, xCenter, xHigh, yCenter, yHigh);
                        break;
                    case NW:
                        returnNode = find(coordinate, xLow, xCenter, yCenter, yHigh);
                        break;
                    case SW:
                        returnNode = find(coordinate, xLow, xCenter, yLow, yCenter);
                        break;
                    case SE:
                        returnNode = find(coordinate, xCenter, xHigh, yLow, yCenter);
                        break;
                    default:
                        // Do Nothing
                        break;
                }
            }
            return returnNode;
        }



    }
}
