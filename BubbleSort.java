import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Random;

public class BubbleSort {
    public static void main(String[] args) {
	String filename = "example.txt";
	String outFile = "example.txt.sorted";
	if(args.length>0) {
	    filename = args[0];
	    outFile = filename + "sorted";
	}

	if (args.length==2) {
	    outFile = args[1];
	}

	// int[] input = readList(filename);
	int[] input = getRandomInts(10000);
	int[] output = bubbleSort(input);

	writeList(output, outFile);
    }

    public static int[] bubbleSort(int[] input) {
	long startTime=System.currentTimeMillis();
	int swap=0;

	for (int i=input.length-1; i>0; i--) {
	    for (int j=0; j<i; j++) {
		if (input[j] > input[j+1]) {
			swap = input[j];
			input[j] = input[j+1];
			input[j+1] = swap;
		    }
	    }
	}
	long duration = System.currentTimeMillis() - startTime;
	System.out.printf("Sorted in %d !\n", duration);
	return input;
    }

    public static int[] readList(String filename) {
	int i=0;
	int n=0;

	try {
	    n=countLines(filename);
	} catch(IOException e) {
	    System.out.println("Cannot open" + filename + "!");
	    return new int[] {-1,-1,-1};
	}
	
	// We have to declare the array to have the same length as the file upfront
	int[] output = new int[n];
	
	try {
	    Scanner sc = new Scanner(new File(filename));
	    while (sc.hasNextInt()) {
		output[i++] = sc.nextInt(); // this is a little evil, because postincrement
	    }
	} catch (FileNotFoundException e) {
	    System.out.println("Cannot open" + filename + "!");
	    return new int[] {-1,-1,-1};
	}
	
	return output;
    }
    
    public static void writeList(int[] data, String filename) {

	PrintWriter pw = null;
	
	try {
	    pw = new PrintWriter(filename);
	} catch(FileNotFoundException e) {
	    System.out.println("Can't open a file for writing");
	}
	
	// Write out the list of frequencies
	System.out.printf("Writing to %s ... ",filename);
	for(int number : data) {
	    pw.println(number);	   
	}

	pw.flush();
	pw.close();
	System.out.println("done!");
    
    }

    public static int[] getRandomInts(int quantity) {
	Random r = new Random();
	int[] output = new int[quantity];
	
	for(int i = 0; i < quantity; i++) {
	    output[i]=r.nextInt(Integer.MAX_VALUE);
	}

	return output;
    }
    
    public static int countLines(String filename) throws IOException {
	InputStream is = new BufferedInputStream(new FileInputStream(filename));
	try {
	    byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        boolean endsWithoutNewLine = false;
        while ((readChars = is.read(c)) != -1) {
            for (int i = 0; i < readChars; ++i) {
                if (c[i] == '\n')
                    ++count;
            }
            endsWithoutNewLine = (c[readChars - 1] != '\n');
        }
        if(endsWithoutNewLine) {
            ++count;
        } 
        return count;
	} finally {
	    is.close();
	}
    }
}
