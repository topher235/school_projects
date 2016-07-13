
public class GameOfLife {
	private boolean[][] grid; //rows, columns
	private int time = 0; //number of rounds played
	
	//This is the default constructor
	GameOfLife() {
		grid = new boolean[10][10];
		setGridFalse(grid);
	} //end constructor
	
	//This is a constructor that take parameters of rows and columns
	GameOfLife(int rows, int columns) {
		if (rows > 0) {
			if(columns > 0) {
				grid = new boolean[rows][columns];
			} else {
				grid = new boolean[rows][10];
			}
		} else if(columns > 0) {
			grid = new boolean[10][columns];
		} else {
			grid = new boolean[10][10];
		}
		setGridFalse(grid);
	}
	
	//This method returns a copy of the grid
	boolean[][] getGrid() {
		int rows = getRows();
		int columns = getColumns();
		boolean[][] copy = new boolean[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				copy[i][j] = grid[i][j];
			}
		} //end for loops
		return copy;
	}
	
	//Returns the time
	int getTime() {
		return time;
	}
	
	
	//Sets the grid based on an array of initial coordinates
	void simpleSetUp(int[][] arr) {
		if(time == 0) {
			for(int i = 0; i < arr.length; i++) {
				if(arr[i].length >= 2) {
					int row = arr[i][0];
					int column = arr[i][1];
					if(hasRow(row) && hasColumn(column)) {
						grid[row][column] = true;
					}
				}
			} //end for loop
		} //end if statement
	}
	
	//This method restarts the game
	//It sets the grid to false and sets the time to zero
	void clearGrid() {
		time = 0;
		setGridFalse(grid);
	}
	
	//Based on the rules of the Game of Life:
	//   If a cell has 1 neighbors, it becomes dead (false)
	//   If a cell has 4 or more neighbors, it becomes dead (false)
	//   If a cell has 2 neighbors, it survives to the next generation (true)
	//   If a cell is dead and it has 3 neighbors, it becomes populated (true)
	//Finally, the time is updated by one because one round was played
	//
	//To not interfere with the actual grid, the grid needed to be copied
	//   the contents of the grid copy are changed, and the actual grid is checked for neighbors
	void runRound() {
		boolean[][] gridCopy = new boolean[getRows()][getColumns()];
		for(int i = 0; i < getRows(); i++) {
			for(int j = 0; j < getColumns(); j++) {
				int neighbors = getNeighborCount(i,j);
				
				if (neighbors > 3 || neighbors < 2) {
					gridCopy[i][j] = false;
				} else if (neighbors == 3) {
					gridCopy[i][j] = true;
				} else {
					gridCopy[i][j] = grid[i][j];
				}
			}
		}
		grid = gridCopy;
		time++;
	}
	
	//This method runs the game for x amount of turns
	void runGame(int numTurns) {
		for(int i = 0; i < numTurns; i++) {
			runRound();
		}
	}
	
	//***Helper Methods***
	
	
	//This method sets the entire grid to false
	void setGridFalse(boolean[][] grid) {
		for(int i = 0; i < getRows(); i++) {
		for(int j = 0; j < getColumns(); j++) {
				grid[i][j] = false;
			} //end inner loop
		}
	}
		
	//Returns the number of rows on the grid
	int getRows() {
		return grid.length;
	}
	
	//Returns the number of columns on the grid
	int getColumns() {
		return grid[0].length;
	}
	
	//Used to check if the grid has the row for set up
	boolean hasRow(int row) {
		if(row >= 0 && row <= getRows()) {
			return true;
		} else {
			return false;
		}
	}
	
	//Used to check if the grid has the column for set up
	boolean hasColumn(int col) {
		if(col >= 0 && col <= getColumns()) {
			return true;
		} else {
			return false;
		}
	}
	
	//This method checks the number of neighbors a cell has
	//If a cell is on the edge of the grid, it would throw an out of bounds error
	//   wrapping this in a try/catch block allows for the code to continue when the error is found
	int getNeighborCount(int row, int column) {
		int neighbors = 0;
		for(int i = row-1; i <= row+1; i++) {
			for(int j = column-1; j <= column + 1; j++) {
				try {
					if(grid[i][j] == true && (i != row || j != column)) {
						neighbors++;
					}
				} catch(ArrayIndexOutOfBoundsException err) {
					continue; //Doesn't matter if the block doesn't exist, just skip it
				}
			} //end inner for loop
		}
		return neighbors;
	}		
}
