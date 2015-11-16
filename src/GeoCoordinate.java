/**
 *
 * Class Represents a Geographic Coordinate
 *
 * @author Brandon Potts
 * @version November 14, 2015
 */
public class GeoCoordinate {

    // Holds the Primary Latitude
    private String pLatitude;
    // Holds the Primary Longitude
    private String pLongitude;
    // Holds the converted latitude to seconds
    private long latitudeInSec;
    // Holds the converted longitude to seconds
    private long longitudeInSec;


    /***
     * Class constructor
     * @param latitude given Latitude
     * @param longitude given Longitude
     */
    public GeoCoordinate(String latitude, String longitude){
        this.pLatitude = latitude;
        this.pLongitude = longitude;
        convertToSeconds();
    }

    /***
     * Returns the primary latitude
     * @return primary latitude
     */
    public String getpLatitude() {
        return pLatitude;
    }

    public String getpLongitude() {
        return pLongitude;
    }

    /***
     * Returns the Longitude in seconds
     * @return longitude in seconds
     */
    public long getLongitudeInSec() {
        return longitudeInSec;
    }

    /***
     * Returns the latitude in seconds
     * @return latitude in seconds
     */
    public long getLatitudeInSec() {
        return latitudeInSec;
    }


    /***
     * Converts the latitude and longitude to seconds
     */
    private void convertToSeconds(){

        StringBuilder latStringBuilder = new StringBuilder(this.pLatitude);
        String latSeconds;
        String latMinutes;
        String latDays;
        int lineLength = latStringBuilder.length();
        // holds the symbol for direction
        char directionChar = latStringBuilder.charAt(lineLength - 1);


        /*---------------------- Handles the conversion of latitude first ------------------------*/
        latSeconds = latStringBuilder.substring(lineLength - 3 , lineLength - 1);
        // gets rid of the unnecessary '0' if it exists
        if(latSeconds.charAt(0) == '0' && latSeconds.length() > 1){
            latSeconds = "" + latSeconds.charAt(1);
        }
        latMinutes = latStringBuilder.substring(lineLength - 5 , lineLength - 3);
        // gets rid of the unnecessary '0' if it exists
        if(latMinutes.charAt(0) == '0'&& latMinutes.length() > 1){
            latMinutes = "" + latMinutes.charAt(1);
        }

        latDays = latStringBuilder.substring(lineLength - 7 , lineLength - 5);
        if (latDays.charAt(0) == '0' && latDays.length() > 1){
            latDays = "" + latDays.charAt(1);
        }

        long iSeconds = Integer.parseInt(latSeconds);
        long iMinutes = Integer.parseInt(latMinutes);
        long iDays = Integer.parseInt(latDays);
        // Determines if the values should be negative or not
        switch (directionChar){
            case 'N':
                this.latitudeInSec = (iDays * 3600) + (iMinutes * 60) + iSeconds;
                break;
            case 'S':
                this.latitudeInSec = 0 - ((iDays * 3600) + (iMinutes * 60) + iSeconds);
                break;
            default:
                // Do nothing
                break;
        }


        /*---------------------  Handles the longitude ---------------------------*/
        StringBuilder longStringBuilder = new StringBuilder(this.pLongitude);
        String longSeconds;
        String longMinutes;
        String longDays;
        int longLineLength = longStringBuilder.length();

        /* Handles the conversion of latitude first */
        longSeconds = longStringBuilder.substring(longLineLength - 3 , longLineLength - 1);
        // gets rid of the unnecessary '0' if it exists
        if(longSeconds.charAt(0) == '0' && longSeconds.length() > 1){
            longSeconds = "" + longSeconds.charAt(1);
        }

        longMinutes = longStringBuilder.substring(longLineLength - 5 , longLineLength - 3);
        // gets rid of the unnecessary '0' if it exists
        if(longMinutes.charAt(0) == '0'&& longMinutes.length() > 1){
            longMinutes = "" + longMinutes.charAt(1);
        }

        longDays = longStringBuilder.substring(longLineLength - 8 , longLineLength - 5);
        if (longDays.charAt(0) == '0' && longDays.length() > 1){
            longDays = "" + longDays.charAt(1) + longDays.charAt(2);
        }

        iSeconds = Integer.parseInt(longSeconds);
        iMinutes = Integer.parseInt(longMinutes);
        iDays = Integer.parseInt(longDays);

        directionChar = longStringBuilder.charAt(longLineLength - 1);
        // Determines if the values should be negative or not
        switch (directionChar){
            case 'E':
                this.longitudeInSec = (iDays * 3600) + (iMinutes * 60) + iSeconds;
                break;
            case 'W':
                this.longitudeInSec = 0 - ((iDays * 3600) + (iMinutes * 60) + iSeconds);
                break;
            default:
                // Do nothing
                break;
        }
    }


}
