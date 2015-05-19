public class Puzzle {
	int rowLength = Config.rowLength;
	int colHeight = Config.colHeight;
	int[][] tiles;

	/* 
		Inner class.
		It's a pair of ints representing a position x,y in the puzzle grid.
	*/
	class Coordinate {
		int xCoord;
		int yCoord;

		public Coordinate(int xCoord, int yCoord) {
			this.xCoord = xCoord;
			this.yCoord = yCoord;
		}
	}
	
	/* 
		Constructor.
		Makes a new 2D array of ints representing the puzzle grid.
		Use the grid size specified in Config.java.	
	*/
	public Puzzle() {
		tiles = new int[rowLength][colHeight];
	}

	/* 
		Brute force algorithm with backtracking.
		http://en.wikipedia.org/wiki/Backtracking 
		Recurses until FindUnassignedPosition() returns null, indicating that 
		the puzzle has been solved (or at least all the spaces have been filled!).
	*/
	public boolean solve() {
		Coordinate curr;
	   	if ((curr = FindUnassignedPosition()) != null) {
	   		int row = curr.xCoord;
	   		int col = curr.yCoord;

	   		// Not all positions have been assigned
	    	for (int num = 1; num <= 9; num++) {
	        	if (safeAssignment(row, col, num)) {
	            	// Try the assignment
	            	tiles[row][col] = num;

	            	if (solve()) {
	            		// Lucky us, the assignment worked
	                	return true;
	            	}
	           		else {	 
	            		// The assignment was bad, undo it
	            		tiles[row][col] = 0;
	        		}
	        	}
	    	}
	    	// Backtrack
	    	return false;
	   	}
	   	else {
	   		// We're done!
	   		return true;
	    }
	}

	/* 
		Put val into position x,y in tiles
  	*/
	public void put(int x, int y, int val) {
		if (x >= rowLength || y >= colHeight) {
			throw new IllegalArgumentException("Put failed : invalid tile");
		}
		
		tiles[x][y] = val;
	}
	

	/* 
		Return current value at the x,y position
  	*/
	public int get(int x, int y){
		if (x >= rowLength || y >= colHeight) {
			throw new IllegalArgumentException("Get failed : invalid tile");
		}
		
		return tiles[x][y];
	}

	/* 
		Searches the board for an entry that is still unassigned, returning a 
		Coordinate representing the first such entry that is found. If no unassigned 
		positions are found, returns null.
  	*/
	private Coordinate FindUnassignedPosition() {
	    for (int row = 0; row < rowLength; row++) {
	        for (int col = 0; col < colHeight; col++) {
	            if (tiles[row][col] == 0)
	                return new Coordinate(row, col);
	        }
	    }
	    return null;
	}

	/* 
	  	Returns a boolean which indicates whether any assigned position in row is num
	*/
	private boolean UsedInRow(int row, int num) {
	    for (int col = 0; col < colHeight; col++) {
	        if (tiles[row][col] == num)
	            return true;
	    }
	    return false;
	}

	/* 
	  	Returns a boolean which indicates whether any assigned position in col is num
  	*/
	private boolean UsedInColumn(int col, int num) {
	    for (int row = 0; row < rowLength; row++) {
	        if (tiles[row][col] == num)
	            return true;
	    }
	    return false;
	}

	/* 
		Returns a boolean which indicates whether any assigned position in the box 
		whose upper left corner is boxStartRow,boxStartCol is num
	*/
	private boolean UsedInBox(int row, int col, int num) {
		int boxStartRow = upperLeftValue(row);
		int boxStartCol = upperLeftValue(col);

		// dividing by 3 is the magic number that makes this work for traditional 9x9 games
	    for (int _row = 0; _row < (rowLength / 3); _row++) {
	        for (int _col = 0; _col < (colHeight / 3); _col++) {
	            if (tiles[_row + boxStartRow][_col + boxStartCol] == num)
	                return true;
	        }
	    }
	    return false;
	}
 
 	/* 
 		Some magic to make UsedInBox easier. Given an integer representing
 		a row or column position, returns the value corresponding to the 
 		upper left hand corner of the box that position is in.
 	*/
	private static int upperLeftValue(int num) {
		switch(num) {
			case 0:
			case 1:
			case 2:
				return 0;
			case 3:
			case 4:
			case 5:
				return 3;
			default:
				return 6;
		}
	}

	/* 
		Returns a boolean which indicates whether it will be legal to assign
   		num to the given row,col location. 
  	*/
	private boolean safeAssignment(int row, int col, int num) {
	    return (!UsedInRow(row, num) && 
	    		!UsedInColumn(col, num) && 
	    		!UsedInBox(row, col, num));
	}

	/* 
		Pretty-prints the puzzle as a grid with single space between 
		each position.
  	*/
	@Override
	public String toString() {
		String board = "";
		
		for (int y = 0; y < colHeight; y++) {
			for (int x = 0; x < rowLength; x++) {
				board += Integer.toString(tiles[x][y]);
				board += " ";			
			}	
			board += "\n";
		}
		return board;
	}
}
