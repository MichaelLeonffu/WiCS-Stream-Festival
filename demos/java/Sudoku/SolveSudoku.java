//finds a solution to sudoku


import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import java.awt.GridBagConstraints;

import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.util.Arrays;

//strategy

//insert sudoku

//generate a probability map

//check if solved or issues (0 1 or 1+)

//loop though elimination
	//elimination possibilities

//if no elimination
//recursively try a low possibility branch

public class SolveSudoku{

	public static int worldClock = 0;

	public static void main(String[] args){

		int[][] matrix = new int[][]{
			{0,0,8, 2,0,0, 9,0,3},
			{3,4,2, 0,9,5, 0,0,7},
			{1,9,7, 0,0,0, 0,0,4},

			{0,0,5, 3,1,2, 4,7,9},
			{0,0,0, 0,0,0, 0,0,0},
			{2,0,0, 0,7,4, 5,0,0},

			{0,2,0, 0,0,1, 0,0,5},
			{0,7,0, 0,0,6, 8,9,1},
			{8,0,0, 4,3,0, 7,0,6}
		}; //assumes matrix is always square and 9x9; number range of 1-9; where 0 represents no number

		//none
		if(args.length > 0 && args[0].equals("n"))
			matrix = new int[][]{
				{0,0,0, 0,0,0, 0,0,0},
				{0,0,0, 0,0,0, 0,0,0},
				{0,0,0, 0,0,0, 0,0,0},

				{0,0,0, 0,0,0, 0,0,0},
				{0,0,0, 0,0,0, 0,0,0},
				{0,0,0, 0,0,0, 0,0,0},

				{0,0,0, 0,0,0, 0,0,0},
				{0,0,0, 0,0,0, 0,0,0},
				{0,0,0, 0,0,0, 0,0,0}
			};

		//none 14 center not solvable
		if(args.length > 0 && args[0].equals("no14"))
			matrix = new int[][]{
				{0,0,0, 0,0,0, 0,1,4},
				{4,0,0, 0,0,1, 0,0,0},
				{0,0,1, 4,0,0, 0,0,0},

				{0,0,0, 0,0,0, 1,4,0},
				{0,0,0, 0,0,0, 0,0,0},
				{1,0,4, 0,0,0, 0,0,0},

				{0,1,0, 0,0,4, 0,0,0},
				{0,4,0, 1,0,0, 0,0,0},
				{0,0,0, 0,0,0, 4,0,1}
			};

		//none 19 top left not solvable
		if(args.length > 0 && args[0].equals("no19"))
			matrix = new int[][]{
				{0,0,0, 0,0,0, 0,0,0},
				{0,0,0, 1,9,0, 0,0,0},
				{0,0,0, 0,0,0, 1,9,0},

				{0,1,0, 0,0,0, 0,0,0},
				{0,9,0, 0,0,0, 0,0,0},
				{0,0,0, 0,0,0, 0,0,0},

				{0,0,1, 0,0,0, 0,0,0},
				{0,0,9, 0,0,0, 0,0,0},
				{0,0,0, 0,0,0, 0,0,0}
			};

		//expert
		if(args.length > 0 && args[0].equals("e"))
			matrix = new int[][]{
				{0,2,0, 5,0,0, 0,0,0},
				{0,3,0, 0,0,7, 0,0,0},
				{0,8,9, 0,2,0, 0,0,4},

				{0,0,7, 0,3,0, 0,0,0},
				{9,0,1, 0,0,0, 6,0,2},
				{0,0,0, 0,5,0, 8,0,0},

				{2,0,0, 0,1,0, 9,8,0},
				{0,0,0, 9,0,0, 0,5,0},
				{0,0,0, 0,0,6, 0,7,0}
			};

		//hardest
		if(args.length > 0 && args[0].equals("h"))
			matrix = new int[][]{
				{1,0,0, 0,0,7, 0,9,0},
				{0,3,0, 0,2,0, 0,0,8},
				{0,0,9, 6,0,0, 5,0,0},

				{0,0,5, 3,0,0, 9,0,0},
				{0,1,0, 0,8,0, 0,0,2},
				{6,0,0, 0,0,4, 0,0,0},

				{3,0,0, 0,0,0, 0,1,0},
				{0,4,0, 0,0,0, 0,0,7},
				{0,0,7, 0,0,0, 3,0,0}
			};

		// System.out.println(SudokuNotes.viewSudokuMap(SudokuNotes.generateProbabilityMapFromInitalSudoku(matrix)));
		// System.out.println("Ready?");

		// new Scanner(System.in).nextLine();

		// SudokuNotes.solveAndPrint(matrix);

		new SudokuInterface();

	}

	public static class SudokuNotes{

		public boolean[][][] sudokuMap;

		//not really useful....
		public SudokuNotes(int[][] initalSudoku){
			this.sudokuMap = SudokuNotes.generateProbabilityMapFromInitalSudoku(initalSudoku);
		}

		//prints the given puzzle then solves it using solve recursion then prints the solution
		public static boolean[][][] solveAndPrint(int[][] initalSudoku){
			SudokuNotes mySudokuPuzzle = new SudokuNotes(initalSudoku);

			//print init
			System.out.println(SudokuNotes.viewSudokuMap(mySudokuPuzzle.sudokuMap));

			if(!SudokuNotes.solve(mySudokuPuzzle.sudokuMap))
				System.out.println("Cannot solve!");

			//print final
			System.out.println(SudokuNotes.viewSudokuMap(mySudokuPuzzle.sudokuMap));

			return mySudokuPuzzle.sudokuMap;
		}

		//solves the puzzle using recursion; returns true if it worked and false otherwise
		public static boolean solve(boolean[][][] sudokuMap){

			worldClock++;
			System.out.println(viewSudokuMap(sudokuMap));
			System.out.println("Attempt: " + worldClock);

			//check if the given is wrong to begin with

			//reduce possibility map as much as possible before having to guess or checking for correctness
			while(SudokuNotes.updateProbabilityMap(sudokuMap) != 0);

			//check which situation we are in; whether to guess or not
			switch(determineSolutionSituation(sudokuMap)){
				case -1:			//incorrect
					//recursive	//this means either there was a mistake or an unfilled cell

					//perform guesswork recursively by attempting every attempt in order of least attempts
					int[] coordinateOfLeastPossibility = SudokuNotes.findLowestProbabilityCoordinate(sudokuMap);

					//check if coordinate exists; meaning its not -1 -1, if it is then false
					if(coordinateOfLeastPossibility[0] == -1) //if one is -1 it implies the other is also -1
						return false;

					//generate guess plan using possibilities remaining
					boolean[] guessPlan = Arrays.copyOf(sudokuMap[coordinateOfLeastPossibility[0]][coordinateOfLeastPossibility[1]], sudokuMap[coordinateOfLeastPossibility[0]][coordinateOfLeastPossibility[1]].length);

					//arithmetically attempt each one recursively
					for(int i = 0; i < guessPlan.length; i++){				//for the guess plan
						if(!guessPlan[i])
							continue;			//only guess values possible skip otherwise

						//make a copy of the sudokuMap
						boolean[][][] sudokuMapCopy = SudokuNotes.copySudokuMap(sudokuMap);

						//make change to the copy of the map at the location of coordinate
						//first clear the current values there by setting to false
						Arrays.fill(sudokuMapCopy[coordinateOfLeastPossibility[0]][coordinateOfLeastPossibility[1]], false);
						//then set one value decided by guess plan to true
						sudokuMapCopy[coordinateOfLeastPossibility[0]][coordinateOfLeastPossibility[1]][i] = true;

						// if(determineSolutionSituation(sudokuMapCopy) == -1)
						// 	continue;

						if(SudokuNotes.solve(sudokuMapCopy)){ //attempt to solve; if it worked then update actual map then return true
							SudokuNotes.overrideMapData(sudokuMapCopy, sudokuMap);
							return true;
						}

						//if it failed then just try again with next guess
					}
					// System.out.println("Failed to solve using solution situation -1: incomplete!"); //not actually failed
					return false;	//failed somehow?!
				case 0:				//incomplete
					return false;	//wrong to have no possible option for a location; this means that its not finished yet.
				case 1:				//solved
					return true;
				default:
					System.out.println("failed to determine Solution Situation correctly");
					return false;
			}
		}

		//fills in the blank spaces (denoted by 0) with an array from 1-9 inclusive
		public static boolean[][][] generateProbabilityMapFromInitalSudoku(int[][] initalSudoku){
			//must be a 9x9; all values must be 0-9 inclusive!
			assert initalSudoku.length != 9;
			assert initalSudoku[0].length != 9;

			//transform into Probability map
			boolean[][][] probabilityMap = new boolean[9][9][9];

			//where true means the value is there and false means the value cannot be there

			for(int i = 0; i < 9; i++)				//rows
				for(int j = 0; j < 9; j++)			//columns
					if(initalSudoku[i][j] == 0) 	//meaning if the space is empty
						Arrays.fill(probabilityMap[i][j], true); //if empty then make them all possible
					else //will fail if not 1-9 inclusive
						probabilityMap[i][j][initalSudoku[i][j]-1] = true; //rest remain falsE
			return probabilityMap;
		}

		//updates possibility by using defined rules given; returns # of changes
		public static int updateProbabilityMap(boolean[][][] probabilityMap){ 		//for now no rules args
			int updateCounter = 0;

			//updates SELECT cell and iterates though TARGET cells;

			for(int i = 0; i < 9; i++)					//rows		SELECT
				for(int j = 0; j < 9; j++)				//columns	SELECT
					for(int k = 0; k < 9; k++)			//rows 		TARGET
						for(int l = 0; l < 9; l++) 		//columns 	TARGET
							if(i == k || j == l || (i/3 == k/3 && j/3 == l/3))	//MUST be on same ROW or COLUMN: SELECT and TARGET OR on same group
								if(!(i == k && j == l))	//SELECT cannot be == TARGET
									if(SudokuNotes.findProbabilityValue(probabilityMap[i][j]) == 0)		//is SELECT a probability
										if(SudokuNotes.findProbabilityValue(probabilityMap[k][l]) != 0)	//is TARGET a solid number
											if(probabilityMap[i][j][SudokuNotes.findProbabilityValue(probabilityMap[k][l])-1]){
												updateCounter++; 										//update counter that an update occurred
												probabilityMap[i][j][SudokuNotes.findProbabilityValue(probabilityMap[k][l])-1] = false; //set TARGET solid number to be false in SELECT probability
											}

			// System.out.println(SudokuNotes.viewSudokuMap(probabilityMap));
			// System.out.println();
			// try{
			// 	TimeUnit.MILLISECONDS.sleep(16);
			// }catch(Exception e){}

			return updateCounter;
		}

		//generates a grid of values where more than one possible is rep as ' ' and 1 possible is rep as the number value
		public static String viewSudokuMap(boolean[][][] sudokuMap){
			String field = "";
			for(int i = 0; i < 9; i++){
				for(int j = 0; j < 9; j++){

					//find probabiltyiesValue
					int value = SudokuNotes.findProbabilityValue(sudokuMap[i][j]);

					//paste the value into the string where every 3 is added with a space
					if(value == 0)
						field += " ";
					else
						field += value;

					if((j+1)%3 == 0 && j != 8)
						field += "|";

				}

				//every 3 is added with an extra newline
				field += "\n";
				if((i+1)%3 == 0 && i != 8)
					field += "---+---+---\n";
			}
			return field;
		}

		//returns 0 if more than one possible value; and returns the value 1-9 inclusive if there is only one possible
		public static int findProbabilityValue(boolean[] probabilties){
			int possibilityCount = 0;
			int value = 0;
			for(int i = 0; i < probabilties.length; i++)
				if(probabilties[i]){
					possibilityCount++;
					value = i+1; //since index values are 0 based and values are 1-9 inclusive
				}

			//if there is at 0 then something is wrong in the logic!
			assert possibilityCount != 0;

			//if there is more than one possible value then default value to 0
			if(possibilityCount == 1)
				return value;
			else
				return 0;
		}

		//returns an int[] where [0] = i coordinate and [1] = j coordinate of the location with the min Probability values; -1 -1 if not found
		public static int[] findLowestProbabilityCoordinate(boolean[][][] sudokuMap){

			//find min
			int min = 9+1; //max value + 1
			int[] cord = {-1, -1};
			
			for(int i = 0; i < 9; i++)											//row
				for(int j = 0; j < 9; j++)										//column
					if(SudokuNotes.findProbabilityValue(sudokuMap[i][j]) == 0){ //there are Probability values here
						int probabilityCount = 0;
						for(boolean probabilityValue: sudokuMap[i][j]){
							if(probabilityValue)
								probabilityCount++;
							if(probabilityCount > min)							//skip if the value is too high anyways
								break;
						}
						if(min > probabilityCount){
							min = probabilityCount; //update min
							cord = new int[]{i, j}; //save cord
						}
					}

			if(min == 0)
				cord = new int[]{-1, -1}; //bad cord

			return cord;
		}

		//returns -1 for incorrect, 0 for incomplete, and 1 for complete and correct
		public static int determineSolutionSituation(boolean[][][] sudokuMap){
			//assumes input is 9x9

			//first check for incomplete
			for(boolean[][] row: sudokuMap)
				for(boolean[] cell: row){
					boolean containsValue = false;
					for(boolean probabilityValue: cell)
						if(probabilityValue)				//if a value of the cell contains a true
							if(!containsValue)				//if the containsValue in this cell is still false
								containsValue = true;		//set to true
							else
								return -1;					//set to incorrect since there cannot be two probabiltyValues
					
					if(!containsValue) //somehow still false means no possibly and wrong
						return 0;
				}

			//next check for incorrect or correct
			//SELECTS a value then compares it to TARGET

			for(int i = 0; i < 9; i++)					//rows		SELECT
				for(int j = 0; j < 9; j++)				//columns	SELECT
					for(int k = 0; k < 9; k++)			//rows 		TARGET
						for(int l = 0; l < 9; l++) 		//columns 	TARGET
							if(i == k || j == l || (i/3 == k/3 && j/3 == l/3))	//MUST be on same ROW or COLUMN: SELECT and TARGET OR on same group
								if(!(i == k && j == l))	//SELECT cannot be == TARGET
									if(SudokuNotes.findProbabilityValue(sudokuMap[i][j]) == SudokuNotes.findProbabilityValue(sudokuMap[k][l]) ||
										SudokuNotes.findProbabilityValue(sudokuMap[i][j]) == 0 ||
										SudokuNotes.findProbabilityValue(sudokuMap[k][l]) == 0
									) //if SELECT and TARGET are same number or if either of them are 0
										return -1; //incorrect

			return 1; //if there is no incorrect and it is complete then there must only be correct
		}

		//make a copy of the sudokuMap deep copy.
		public static boolean[][][] copySudokuMap(boolean[][][] sudokuMap){
			boolean[][][] newSudokuMap = new boolean[9][9][9]; //must be 9x9 with 1-9 inclusive
			for(int i = 0; i < 9; i++)
				for(int j = 0; j < 9; j++)
					for(int k = 0; k < 9; k++)
						newSudokuMap[i][j][k] = sudokuMap[i][j][k];

			return newSudokuMap;
		}

		//overrides the data in TO from FROM
		public static void overrideMapData(boolean[][][] sudokuMapFrom, boolean[][][] sudokuMapTo){
			//assume a 9x9 for both.
			for(int i = 0; i < 9; i++)
				for(int j = 0; j < 9; j++)
					for(int k = 0; k < 9; k++)
						sudokuMapTo[i][j][k] = sudokuMapFrom[i][j][k];
		}
	}

	public static class SudokuInterface extends JFrame{

		public static final int WINDOW_HEIGHT = 600;
		public static final int WINDOW_WIDTH = 600;

		public JTextField[][] sudokuFieldArray;

		//placing all items in here since it's a JFrame
		public SudokuInterface(){
			//setting the title
			super("Sudoku");

			//misc
			setSize(SudokuInterface.WINDOW_WIDTH, SudokuInterface.WINDOW_HEIGHT);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			//where sudoku takes place
			JPanel sudokuField = new JPanel(new GridLayout(9,9));

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;

			sudokuFieldArray = new JTextField[9][9];

			for(int i = 0; i < 9; i++)
				for(int j = 0; j < 9; j++){
					JTextField aTextField = new JTextField(1);
					sudokuField.add(aTextField, gbc);
					sudokuFieldArray[i][j] = aTextField;
				}

			JPanel controlPannel = new JPanel();

			JButton solveButton = new JButton("Solve");
			JButton clearButton = new JButton("Clear");

			solveButton.addActionListener(e -> {
				int[][] matrix = new int[9][9];

				for(int i = 0; i < 9; i++)
					for(int j = 0; j < 9; j++)
						try{
							matrix[i][j] = Integer.parseInt(sudokuFieldArray[i][j].getText());
						}catch(Exception error){
							System.out.println("error");
							matrix[i][j] = 0;
						}

				boolean[][][] sudokuSolved = SudokuNotes.solveAndPrint(matrix);

				for(int i = 0; i < 9; i++)
					for(int j = 0; j < 9; j++)
						for(int k = 0; k < 9; k++)
							if(sudokuSolved[i][j][k])
								sudokuFieldArray[i][j].setText(Integer.toString(k+1));
			});

			clearButton.addActionListener(e -> {
				for(int i = 0; i < 9; i++)
					for(int j = 0; j < 9; j++)
						sudokuFieldArray[i][j].setText("");
			});

			controlPannel.add(solveButton);
			controlPannel.add(clearButton);

			add(sudokuField, BorderLayout.CENTER);
			add(controlPannel, BorderLayout.SOUTH);

			pack();
            // setLocationRelativeTo(null);

			//allows user to see the GUI
			setVisible(true);

		}
	}
}