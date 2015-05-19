import java.io.IOException;

public class SudokuSolver {

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.out.println("Usage : SudokuSolver <filepath>");
			return;
		}
		
		try {
			PuzzleReader reader = new PuzzleReader();
			Puzzle puzzle = reader.getPuzzleFromFile(args[0]);
			System.out.println("---- Game Board ----");
			System.out.println(puzzle.toString());

			if (puzzle.solve()) {			
				System.out.println("---- Solution ----");
				System.out.println(puzzle.toString());
			}
			else {			
				System.out.println("---- No Solution ----");
			}
		} catch (IOException e) {
			// Something went terribly wrong. 
			throw new Exception(e.getMessage());
		}
	}
}
