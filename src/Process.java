import java.io.*;

/**
 *
 * Class acts as the controller between the I/O
 * @author Brandon Potts
 * @version November 10, 2015
 */
public class Process {


    // File that contains all the records
    private RandomAccessFile databaseFile;
    // File that contains all the commands
    private RandomAccessFile commandFile;
    // File that contains all the output
    private FileOutput logFile;
    // Creates the buffer pool
    private BufferPool bufferPool;
    // Creates the Quad Treee
    private QuadTree<HashTuple> quadTree;
    // Creates the HashTable
    private HashTable<HashTuple> hashTable;



    /****
     * Instantiates the class
     * @param databaseFileName name of the database file
     * @param commandFileName name of the command file
     * @param logFileName name of the log file
     */
    public Process(String databaseFileName, String commandFileName, String logFileName)
            throws FileNotFoundException {

        databaseFile = new RandomAccessFile(databaseFileName, "r");
        commandFile = new RandomAccessFile(commandFileName, "r");
        logFile = new FileOutput(logFileName);
        bufferPool = new BufferPool();
        quadTree = null;
        hashTable = new HashTable<>(HashTuple.class, 1019);
    }


    /***
     * Drives much of the program
     */
    public void processFiles() throws IOException {

        String commandLine = commandFile.readLine();

        // Keeps running until there aren't any more commands
        while(commandLine != null){

            String[] pieces = commandLine.split("\\t");
            // Skip all the comments
            if(commandLine.charAt(0) != ';'){

                logFile.printLine(commandLine);
                // Decides which action to take
                switch(pieces[0]){
                    case "world":
                        processWorld(pieces[1], pieces[2], pieces[3], pieces[4]);
                        break;
                    case "import":
                        processImport();
                        break;
                    case "what_is_at":
                        break;
                    case "what_is":
                        break;
                    case "what_is_in":
                        break;
                    case "debug":
                        processDebug(pieces[1]);
                        break;
                    case "quit":
                        break;
                    default:
                        System.out.println("Something didn't work");
                        break;
                }
            }
            commandLine = commandFile.readLine();
        }

        databaseFile.close();
        commandFile.close();
        logFile.closeFile();
    }


    /****
     * Processes the World command
     * @param westLong west longitude
     * @param eastLong east longitude
     * @param southLat south latitude
     * @param northLat north latitude
     */
    private void processWorld(String westLong , String eastLong, String southLat, String northLat){

        int xMin = latAndLongFormatConvert(westLong);
        int xMax = latAndLongFormatConvert(eastLong);
        int yMin = latAndLongFormatConvert(southLat);
        int yMax = latAndLongFormatConvert(northLat);
        quadTree = new QuadTree<>(xMin, xMax, yMin, yMax);
    }

    /****
     * Imports all the records into the database
     * @throws IOException
     */
    private void processImport() throws IOException {
        // Skip the first line
        databaseFile.readLine();
        String line = databaseFile.readLine();

        long offset;
        GISRecord record;
        LineParser lineParser;
        HashTuple tuple;
        int largestProbSequence = 0;
        int currentSequence;
        int numberOfRecords = 0;
        // Will run until the file has been fully processed
        while (line != null){

            lineParser = new LineParser((line));
            offset = databaseFile.getFilePointer();
            record = lineParser.buildGISRecord();

            // Will be true when the record is in bounds
            if(quadTree.inBounds(record.buildCoordinates())){
                tuple = new HashTuple(record, offset);
                currentSequence = hashTable.insert(tuple);

                // Updates the sequence count if the currentSequence is larger
                if(currentSequence > largestProbSequence){
                    largestProbSequence = currentSequence;
                }

                quadTree.insert(tuple);
                numberOfRecords++;
            }

            line = databaseFile.readLine();
        }

        logFile.printLine("Number of entries added: " + numberOfRecords);
        logFile.printLine("Largest Probe sequence: " + largestProbSequence + "\n");

        // Reset the file's position
        databaseFile.seek(0);
    }

    /***
     * Prints the contents of a data structure
     * @param structure data structure that will be printed
     */
    private void processDebug(String structure){
        System.out.println("Need to figure out Debug");
    }


    /****
     * Converts a DMS format location to seconds
     * @param line that contains the location that will be converted
     * @return int
     */
    public static int latAndLongFormatConvert(String line){

        String seconds;
        String minutes;
        String days;
        StringBuilder stringBuilder = new StringBuilder(line);
        int lineLength = stringBuilder.length();

        seconds = stringBuilder.substring(lineLength - 3 , lineLength - 1);
        // gets rid of the unnecessary '0' if it exists
        if(seconds.charAt(0) == '0' && seconds.length() > 1){
            seconds = "" + seconds.charAt(1);
        }

        minutes = stringBuilder.substring(lineLength - 5 , lineLength - 3);
        // gets rid of the unnecessary '0' if it exists
        if(minutes.charAt(0) == '0'&& minutes.length() > 1){
            minutes = "" + minutes.charAt(1);
        }

        // checks to see if the we're converting for a latitude
        if(stringBuilder.charAt(lineLength - 1) == 'N' || stringBuilder.charAt(lineLength - 1) == 'S'){
            days = stringBuilder.substring(lineLength - 7 , lineLength - 5);
            if (days.charAt(0) == '0' && days.length() > 1){
                days = "" + days.charAt(1);
            }
            // will be true when we're converting for a longitude
        } else {
            days = stringBuilder.substring(lineLength - 8 , lineLength - 5);
            if (days.charAt(0) == '0' && days.length() > 1){
                days = "" + days.charAt(1) + days.charAt(2);
            }
        }

        int iSeconds = Integer.parseInt(seconds);
        int iMinutes = Integer.parseInt(minutes);
        int iDays = Integer.parseInt(days);
        int returnInt = -1;

        // holds the symbol for direction
        char directionChar = stringBuilder.charAt(lineLength - 1);
        switch (directionChar){
            case 'N':
                returnInt = (iDays * 3600) + (iMinutes * 60) + iSeconds;
                break;
            case 'E':
                returnInt = (iDays * 3600) + (iMinutes * 60) + iSeconds;
                break;
            case 'S':
                returnInt =  0 - ((iDays * 3600) + (iMinutes * 60) + iSeconds);
                break;
            case 'W':
                returnInt =  0 - ((iDays * 3600) + (iMinutes * 60) + iSeconds);
                break;
            default:
                // Do nothing
                break;
        }

        return returnInt;
    }
}
