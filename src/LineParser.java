/**
 *
 *
 * @author Brandon Potts
 * @version November 10, 2015
 */
public class LineParser {

    // line that will be parsed
    private String line;

    /***
     * LineParser constructor
     * @param line line that will be parsed
     */
    public LineParser(String line){

        this.line = line;
    }

    /***
     * Builds a GISRecord Object from a line
     * @return GISRecord containing the data from the line
     */
    public GISRecord buildGISRecord(){

        StringBuilder currentLine = new StringBuilder(this.line);
        GISRecord currentRecord = new GISRecord();
        String currentField;
        int startIndex = 0;
        int endIndex = 0;
        int fieldCount = 0;

        // Keeps running until the end of the line is found
        while(endIndex < currentLine.length()){

            // Keeps running until the first '|' is found
            while(currentLine.charAt(endIndex) != '|'){
                if (endIndex < currentLine.length() - 1){
                    endIndex++;
                }
                else {
                    break;
                }

            }

            // Will be true when we have a blank record
            if(startIndex == endIndex){
                startIndex++;
                endIndex++;
                fieldCount++;
                continue;
            }

            currentField = currentLine.substring(startIndex, endIndex);

            // Determines which field needs to be set
            switch(fieldCount){

                // sets the Feature ID
                case 0: currentRecord.setfId(currentField);
                    break;
                // sets the Feature Name
                case 1: currentRecord.setfName(currentField);
                    break;
                // sets the Feature Class
                case 2: currentRecord.setfClass(currentField);
                    break;
                // sets the State Alphabetic Code
                case 3: currentRecord.setsAC(currentField);
                    break;
                // sets the State Numeric Code
                case 4: currentRecord.setsNC(currentField);
                    break;
                // sets the Country Name
                case 5: currentRecord.setcName(currentField);
                    break;
                // sets the Country Numeric Code
                case 6: currentRecord.setcNC(currentField);
                    break;
                // sets the Primary Latitude(DMS)
                case 7: currentRecord.setpLatitudeDMS(currentField);
                    break;
                // sets the Primary Longitude(DMS)
                case 8: currentRecord.setpLongitudeDMS(currentField);
                    break;
                // sets the Primary Latitude(dec deg)
                case 9: currentRecord.setpLatitudeDD(currentField);
                    break;
                // sets the Primary Longitude(dec deg)
                case 10: currentRecord.setpLongitudeDD(currentField);
                    break;
                // sets the Source Latitude (DMS)
                case 11: currentRecord.setsLatitude(currentField);
                    break;
                // sets the Source Longitude (DMS)
                case 12: currentRecord.setsLongitude(currentField);
                    break;
                // sets the Source Latitude (dec deg)
                case 13: currentRecord.setSLatitudeDD(currentField);
                    break;
                // sets the Source Longitude (dec deg)
                case 14: currentRecord.setSLongitudeDD(currentField);
                    break;
                // sets the Feature Elevation in Meters
                case 15: currentRecord.setElevationInMeters(currentField);
                    break;
                // sets the Feature Elevation in Feet
                case 16: currentRecord.setElevationInFeet(currentField);
                    break;
                // sets the Map Name
                case 17: currentRecord.setMapName(currentField);
                    break;
                // sets the Date Created
                case 18: currentRecord.setDateCreated(currentField);
                    break;
                // sets the Date Edited
                case 19: currentRecord.setDateEdited(currentField);
                    break;
                default:
                    //Do Nothing
                    break;
            }

            fieldCount++;
            endIndex++;
            startIndex = endIndex;
        }

        return currentRecord;
    }
}
