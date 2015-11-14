/**
 *
 * Object represents a single GIS Record
 *
 * @author Brandon Potts
 * @version August 29, 2015
 */
public class GISRecord {


    // Feature ID
    private String fId;
    // Feature Name
    private String fName;
    // Feature Class
    private String fClass;
    // State Alphabetic Code
    private String sAC;
    // State Numeric Code
    private String sNC;
    // Country Name
    private String cName;
    // County Numeric Code
    private String cNC;
    // Primary latitude (DMS)
    private String pLatitudeDMS;
    // Primary longitude (DMS)
    private String pLongitudeDMS;
    // Primary latitude (dec deg)
    private String pLatitudeDD;
    // Primary longitude (dec deg)
    private String pLongitudeDD;
    // Source latitude (DMS)
    private String sLatitude;
    // Source longitude (DMS)
    private String sLongitude;
    // Source Latitude (dec deg)
    private String sLatitudeDD;
    // Source Longitude (dec deg)
    private String sLongitudeDD;
    // Elevation in Meters
    private String elevationInMeters;
    // Elevation in Feet
    private String elevationInFeet;
    // Map Name
    private String mapName;
    // Date Created
    private String dateCreated;
    // Date Edited
    private String dateEdited;

    /***
     * Class constructor
     */
    public GISRecord(){

        this.fId = "";
        this.fClass = "";
        this.fName = "";
        this.sAC = "";
        this.sNC = "";
        this.cName = "";
        this.cNC = "";
        this.pLatitudeDMS = "";
        this.pLongitudeDMS = "";
        this.pLatitudeDD = "";
        this.pLongitudeDD = "";
        this.sLatitude = "";
        this.sLongitude = "";
        this.elevationInMeters = "";
        this.elevationInFeet = "";
        this.mapName = "";
        this.sLatitudeDD = "";
        this.sLongitudeDD = "";
        this.dateCreated = "";
        this.dateEdited = "";

    }

    /***
     * Returns the Feature Id for the record
     * @return FeatureId
     */
    public String getfId() {
        return fId;
    }

    /***
     * Sets the Feature Id of a record
     * @param fId new FeatureId
     */
    public void setfId(String fId) {
        this.fId = fId;
    }

    /***
     * Returns the Feature Name for the record
     * @return Feature Name
     */
    public String getfName() {
        return fName;
    }

    /***
     * Sets the Feature Name for a record
     * @param fName new Feature Name
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /***
     * Returns the Feature Class
     * @return Feature Class
     */
    public String getfClass() {
        return fClass;
    }

    /***
     * Sets the Feature Class
     * @param fClass new Feature Class
     */
    public void setfClass(String fClass) {
        this.fClass = fClass;
    }

    /***
     * Returns the state alphabetic code
     * @return State Alphabetic Code
     */
    public String getsAC() {
        return sAC;
    }

    /***
     * Sets a new State Alphabetic Code
     * @param sAC new State Alphabetic Code
     */
    public void setsAC(String sAC) {
        this.sAC = sAC;
    }

    /***
     * Returns State Numeric Code
     * @return State Numeric Code
     */
    public String getsNC() {
        return sNC;
    }

    /***
     * Sets a new State Numeric Code
     * @param sNC new State Numeric Code
     */
    public void setsNC(String sNC) {
        this.sNC = sNC;
    }

    /***
     * Returns Country Name
     * @return Country Name
     */
    public String getcName() {
        return cName;
    }

    /***
     * Sets a new Country Name
     * @param cName new Country Name
     */
    public void setcName(String cName) {
        this.cName = cName;
    }

    /***
     * Returns Country Numeric Code
     * @return Country Numeric Code
     */
    public String getcNC() {
        return cNC;
    }

    /***
     * Sets a new Country Numeric Code
     * @param cNC new Country Numeric Code
     */
    public void setcNC(String cNC) {
        this.cNC = cNC;
    }

    /***
     * Returns Primary Latitude (DMS)
     * @return Primary Latitude (DMS)
     */
    public String getpLatitudeDMS() {
        return pLatitudeDMS;
    }

    /***
     * Sets a new Primary Latitude (DMS)
     * @param pLatitudeDMS new Primary Latitude (DMS)
     */
    public void setpLatitudeDMS(String pLatitudeDMS) {
        this.pLatitudeDMS = pLatitudeDMS;
    }

    /***
     * Returns Primary Longitude (DMS)
     * @return Primary Longitude (DMS)
     */
    public String getpLongitudeDMS() {
        return pLongitudeDMS;
    }

    /***
     * Sets a new Primary Longitude (DMS)
     * @param pLongitudeDMS new Primary Longitude (DMS)
     */
    public void setpLongitudeDMS(String pLongitudeDMS) {
        this.pLongitudeDMS = pLongitudeDMS;
    }

    /***
     * Returns Primary Latitude (dec deg)
     * @return Primary Latitude (dec deg)
     */
    public String getpLatitudeDD() {
        return pLatitudeDD;
    }

    /***
     * Sets Primary Latitude (dec deg)
     * @param pLatitudeDD new Primary Latitude (dec deg)
     */
    public void setpLatitudeDD(String pLatitudeDD) {
        this.pLatitudeDD = pLatitudeDD;
    }

    /***
     * Return Primary Longitude (dec deg)
     * @return Primary Longitude (dec deg)
     */
    public String getpLongitudeDD() {
        return pLongitudeDD;
    }

    /***
     * Sets new Primary Longitude (dec deg)
     * @param pLongitudeDD new Primary Longitude (dec deg)
     */
    public void setpLongitudeDD(String pLongitudeDD) {
        this.pLongitudeDD = pLongitudeDD;
    }

    /****
     * Return Source Latitude (DMS)
     * @return Source Latitude (DMS)
     */
    public String getsLatitude() {
        return sLatitude;
    }

    /***
     * Sets new Source Latitude (DMS)
     * @param sLatitude new Source Latitude (DMS)
     */
    public void setsLatitude(String sLatitude) {
        this.sLatitude = sLatitude;
    }

    /***
     * Returns Source Longitude (DMS)
     * @return Source Longitude (DMS)
     */
    public String getsLongitude() {
        return sLongitude;
    }

    /***
     * Sets new Source Longitude (DMS)
     * @param sLongitude new Source Longitude (DMS)
     */
    public void setsLongitude(String sLongitude) {
        this.sLongitude = sLongitude;
    }

    /***
     * Returns Elevation in Meters
     * @return Elevation in Meters
     */
    public String getElevationInMeters() {
        return elevationInMeters;
    }

    /***
     * Sets Elevation in Meters
     * @param elevationInMeters new Elevation in Meters
     */
    public void setElevationInMeters(String elevationInMeters) {
        this.elevationInMeters = elevationInMeters;
    }

    /***
     * Returns Elevation in Feet
     * @return Elevation in Feet
     */
    public String getElevationInFeet() {
        return elevationInFeet;
    }

    /***
     * Sets new Elevation in Feet
     * @param elevationInFeet new Elevation in Feet
     */
    public void setElevationInFeet(String elevationInFeet) {
        this.elevationInFeet = elevationInFeet;
    }

    /***
     * Returns Map Name
     * @return Map Name
     */
    public String getMapName() {
        return mapName;
    }

    /***
     * Sets Map Name
     * @param mapName new Map Name
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /***
     * Returns Date Created
     * @return Date Created
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /***
     * Sets new Date Created
     * @param dateCreated newe Date Created
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /***
     * Returns Date Edited
     * @return Date Edited
     */
    public String getDateEdited() {
        return dateEdited;
    }

    /***
     * sets Date Edited
     * @param dateEdited new Date Edited
     */
    public void setDateEdited(String dateEdited) {
        this.dateEdited = dateEdited;
    }

    /***
     * Returns Source Latitude (dec deg)
     * @return Source Latitude (dec deg)
     */
    public String getSLatitudeDD() {
        return sLatitudeDD;
    }

    /***
     * Sets new Source Latitude (dec deg)
     * @param sLatitudeDD new Source Latitude (dec deg)
     */
    public void setSLatitudeDD(String sLatitudeDD) {
        this.sLatitudeDD = sLatitudeDD;
    }

    /***
     * Returns Source Longitude (dec deg)
     * @return Source Longitude (dec deg)
     */
    public String getSLongitudeDD() {
        return sLongitudeDD;
    }

    /***
     * Sets new Source Longitude (dec deg)
     * @param sLongitudeDD new Source Longitude (dec deg)
     */
    public void setSLongitudeDD(String sLongitudeDD) {
        this.sLongitudeDD = sLongitudeDD;
    }

    /***
     * Compares the GIS records to see if they're equal
     * @param obj that will be compared
     * @return true if the records are equal
     *         false if the records aren't equal
     */
    @Override
    public boolean equals(Object obj){

        // Handle any null pointers
        if(obj == null){
            return false;
        }

        // Checks to make sure that the parameter is a GISRecord
        if(!(obj instanceof GISRecord)){
          return false;
        }

        GISRecord record = (GISRecord)obj;
        // Will be true when the two records are equal
        if(this.fName.equals(record.getfName()) && this.sAC.equals(record.getsAC())){
            return true;
        }
        return false;
    }


    /****
     * Creates a hash code of the current GISRecord
     * @return hashcode of the GIS record
     */
    @Override
    public int hashCode(){

        String concateString = this.fName + this.sAC;
        int h = 0;
        int g;
        for( int i = 0; i < concateString.length( ); i++ ) {
            h = (h<<4) + concateString.charAt( i );
            g = h & 0xf0000000;
            if( g != 0 )
                h ^= g >> 24;
            h &= ~g;
        }
        return h & 0x7fffffff;
    }

}
