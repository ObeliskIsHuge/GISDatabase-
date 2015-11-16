import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * File handles the outfile printing
 *
 * @author Brandon Potts
 * @version September 04, 2015
 */
public class FileOutput {

    // Object that handles the output writing of the file
    private PrintWriter writer;
    // Keeps track of how many commands have been printed
    private int commandCount;

    /***
     * Class constructor
     * @param fileName name of the file
     */
    public FileOutput(String fileName){
        // Creates new file
        File file = new File(fileName);
        try {
            this.writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        commandCount = 1;
    }


    /**
     * Prints the header for the results file
     */
    public void printHeader(){

        String header = "GIS data file contains the following records:";
        printLine(header);
        printNewLine();
    }


    /**
     * Prints a line to the output file
     *
     * @param line String that will be printed
     */
    public void printLine(String line){
        writer.println(line);
    }

    /***
     * Prints a new line
     */
    public void printNewLine(){
        writer.print("\n");
    }

    /***
     * Formats and prints the command output
     * @param commandLine command from the command file
     * @param response result from the execution of that command
     */
    public void printCommandResponse(String commandLine , String response){

        writer.println(commandCount + ": " + commandLine + "\n\t" + response);
        commandCount++;
    }

//    /***
//     * Formats and prints a RecordReporter object
//     * @param recordReporter RecordReporter that will be printed
//     */
//    public void printRecordReporter(RecordReporter recordReporter){
//        writer.println("    " + recordReporter.getOffset() + "   " + recordReporter.getfID());
//    }

    /***
     * Closes the writer file
     */
    public void closeFile(){
        writer.close();
    }
}
