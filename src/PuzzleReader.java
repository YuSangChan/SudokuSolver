import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;  
import java.io.InputStreamReader;

public class PuzzleReader {
	int rowLength = Config.rowLength;
	int colHeight = Config.colHeight;
	

	/*
		Build a new instance of a puzzle from a file.
		The file must contain rows of integers with no other characters, 
		with values on the same line delimited by spaces.
		This doesn't elegantly handle files that look different than 
		that, so if someone decides this is code worth re-using, 
		beware - It isn't.
	*/
	public Puzzle getPuzzleFromFile(String filePath) throws Exception {
		Puzzle puzzle = new Puzzle();
		
		try {
			FileInputStream fstream = new FileInputStream(filePath);
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    
		    int yCoord = 0;
		    
		    while (((strLine = br.readLine()) != null) && (yCoord < colHeight)) {
		    	String[] tokens = strLine.split(" ");
		    	
		    	for (int xCoord = 0; xCoord < rowLength; xCoord++) {
		    		puzzle.put(xCoord, yCoord, Integer.valueOf(tokens[xCoord]));
		    	}		    	
		    	yCoord++;
		    }
		    
		    in.close();
		}
		catch (IOException ignore) {
			throw new IOException("IOException in PuzzleReader.read()");
		}
		
		return puzzle;
	}
}
