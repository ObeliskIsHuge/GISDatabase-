import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

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
    // Database that contains all the data that was imported
    private RandomAccessFile importedDatabase;
    // File that contains all the output
    private FileOutput logFile;
    // Creates the buffer pool
    private BufferPool bufferPool;
    // Creates the Quad Tree
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

        importedDatabase = new RandomAccessFile(databaseFileName, "rw");
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
        int commandCount = 1;
        String header = "-------------------------------------";


        // Keeps running until there aren't any more commands
        while(commandLine != null){

            String[] pieces = commandLine.split("\\t");
            // Skip all the comments
            if(commandLine.charAt(0) != ';'){

                // Echos the commands
                if(pieces[0].equals("world")){
                    logFile.printLine(commandLine + "\n");
                } else {
                    logFile.printLine(header);
                    logFile.printLine("Command " + commandCount + ": "  +commandLine + "\n");
                    commandCount++;
                }
                // Decides which action to take
                switch(pieces[0]){
                    case "world":
                        processWorld(pieces[1], pieces[2], pieces[3], pieces[4]);
                        break;
                    case "import":
                        processImport(pieces[1]);
                        break;
                    case "what_is_at":
                        processWhatIsAt(pieces[1], pieces[2]);
                        break;
                    case "what_is":
                        processWhatIs(pieces[1], pieces[2]);
                        break;
                    case "what_is_in":
                        processWhatIsIn();
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
//            logFile.printLine(header);
        }

        databaseFile.close();
        commandFile.close();
        logFile.closeFile();
    }

    /***
     * Processes the "What_Is_At" command
     * @param latitude latitude
     * @param longitude longitude
     */
    private void processWhatIsAt(String latitude, String longitude) throws IOException {

        GISRecord gisRecord = new GISRecord();
        gisRecord.setpLatitudeDMS(latitude);
        gisRecord.setpLongitudeDMS(longitude);
        HashTuple insertTuple = new HashTuple(gisRecord, 0);
        QuadTreeNode node = quadTree.find(insertTuple);

        if(node != null){
            String data = node.toString();
//            logFile.printLine(data);
        }

        HashTuple poolTuple = bufferPool.find(gisRecord);
        // Will be true when the pool couldn't find the record
        if(poolTuple == null){
            logFile.printLine("Record not found");
        } else {
            GISRecord foundRecord = poolTuple.getRecord();
            logFile.printLine(poolTuple.getSigleOffset() + ": " + foundRecord.getfName() + " " +
                    foundRecord.getcName() + " " + foundRecord.getsAC());
        }


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
    private void processImport(String fileName) throws IOException {

        databaseFile = new RandomAccessFile(fileName, "r");
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
                importedDatabase.writeChars(line + "\n");
                quadTree.insert(tuple);
                numberOfRecords++;
            }

            line = databaseFile.readLine();
        }

        logFile.printLine("Number of entries added: " + numberOfRecords);
        logFile.printLine("Largest Probe sequence: " + largestProbSequence + "\n");

        // Reset the file's position
        databaseFile.seek(0);
        databaseFile.close();
        bufferPool.openDataBaseFile(fileName);
        importedDatabase.seek(0);
    }

    /***
     * Prints the contents of a data structure
     * @param structure data structure that will be printed
     */
    private void processDebug(String structure){

        String printString;
        // prints the quad
        if(structure.equals("quad")){
//            System.out.println("Need to figure out debug for Quad");
            // Prints the contents of the hash
        } else if(structure.equals("hash")){

            // Build header content
            printString = "Format of display is\n" +
                    "Slot number: data record\n";
            printString += "Current table size is " + hashTable.getTableSize() + "\n";
            printString += "Number of elements in table is " + hashTable.getFillCount() + "\n\n";

            printString += hashTable.buildArrayContents();
            logFile.printLine(printString);
            // Prints the contents of the buffer pool
        } else if(structure.equals("pool")){
            logFile.printLine(bufferPool.toString());
        }
    }

    /***
     * Processes the "What_Is" instruction
     * @param name is the name that will be processed
     * @param state is the state
     */
    private void processWhatIs(String name, String state){

        GISRecord gisRecord = new GISRecord();
        gisRecord.setfName(name);
        gisRecord.setsAC(state);
        HashTuple insertTuple = new HashTuple(gisRecord, 1);
        Stack<HashTuple> stack =  hashTable.find(insertTuple);

        // Will be true when no record was found
        if(stack == null || stack.size() == 0){
            logFile.printLine("Record not found");
        } else {

            for(HashTuple foundTuple : stack){
                GISRecord foundRecord = foundTuple.getRecord();
                String printString = foundTuple.getSigleOffset() + ":  " +
                        foundRecord.getcName() + "  " + foundRecord.getpLongitudeDMS() + "  " + foundRecord.getpLatitudeDMS();
                logFile.printLine(printString);
                bufferPool.insert(foundRecord);
            }

        }

    }

    /***
     * Processes the "WhatIsIn" command
     */
    private void processWhatIsIn(){
        logFile.printLine("Record not found");
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
