import java.io.IOException;
import java.util.Arrays;

/*****
 * Class that runs the
 */
public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here

        // Checks to see if the correct amount of arguments is given
        if(args.length != 3){
            System.out.println("Incorrect amount of arguments given.");
            System.out.println("Given: " + args.length);
            System.out.println(Arrays.toString(args));
        } else {
            // Gets the gis Record and command file paths
            String gisRecordPath = args[0];
            String commandFilePath = args[1];
            String logFilePath = args[2];
            Process process = new Process(gisRecordPath, commandFilePath, logFilePath);
            process.processFiles();
        }
    }
}


// On my honor:
//
// - I have not discussed the Java language code in my program with
// anyone other than my instructor or the teaching assistants
// assigned to this course.
//
// - I have not used Java language code obtained from another student,
// or any other unauthorized source, either modified or unmodified.
//
// - If any Java language code or documentation used in my program
// was obtained from another source, such as a text book or course
// notes, that has been clearly noted with a proper citation in
// the comments of my program.
//
// - I have not designed this program in such a way as to defeat or
// interfere with the normal operation of the Automated Grader.
//
// Pledge: On my honor, I have neither given nor received unauthorized
// aid on this assignment.
//
// Brandon Emerson Potts