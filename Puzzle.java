import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Stack;

class Puzzle {
	//array to hold puzzle pieces pieces, initilized to starting point
	//[0]=x [1]=y [2]=x [3]=y for the coordinates of the shape
	public static final byte[][] pieces = new byte[][]{
		{3,1,3,2,4,1,4,2},//0
		{5,1,6,1,6,2},
		{5,2,5,3,6,3},
		{7,3,8,3,8,4},
		{7,4,7,5,8,5},
		{7,6,7,7,8,6},//5
		{4,5,5,4,5,5,6,5},
		{4,6,5,6,5,7,6,6},
		{6,7,6,8,5,8},
		{3,5,3,6,2,6},
		{1,5,2,5,1,6}//10
	};

	//for simple debugging
	public static void printBoardtoTerminal(boolean[][] board){
		for(int i=0; i < 10; i++){
			for(int j=0; j < 10; j++) {
				if(board[i][j] ==true){
					System.out.print(1);
				}
				else{
					System.out.print(0);
				}
			}
			System.out.println();
		}
	}

	//creates a boolean board that represents the empty game board
	public static boolean[][] initBoard(){
		boolean[][] board = new boolean[10][10];

		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){

				//paint row 0 and 9
				if(i==0 || i==9){
					board[i][j] = true;
				}
				//paint row 1 and 8
				else if(i==1 || i==8){
					if(j<3 || j>6)
						board[i][j] = true;
					else
						board[i][j] = false;
				}
				//paint rows 2 and 7
				else if(i==2 || i==7){
					if(j<2 || j>7)
						board[i][j] = true;
					else
						board[i][j] = false;
				}
				//paint row 3 and 4 and the object
				else if(i==3 || i==4){
					if(i==3){
						if(j<1 || j>8){
							board[i][j] = true;
						}
						else if(j==4){
							board[i][j] = true;
						}

					}
					if(i==4){
						if(j<1 || j>8){
							board[i][j] = true;
						}
						else if(j==3 || j==4){
							board[i][j] = true;
						}
					}
				}
				//paint row 5 and 6
				else{
					if(j<1 || j>8){
						board[i][j] = true;
					}
					else{
						board[i][j] = false;
					}
				}
			}
		}
		return board;
	}

	//applies the state to the pieces and see if the move is valid
	public static boolean validBoard(byte[] state){
		//make a clean gameboard
		boolean[][] tempboard = initBoard();

		//loop through the pieces and write them to the board
		for(int i=0; i < 11; i++){
			int temp = pieces[i].length;
			for(int j=0; j<temp-1; j+=2) {
					//add the state to the original shape position
					int x = pieces[i][j] + state[2*i];
					int y = pieces[i][j+1] + state[2*i+1];
					//if the spot is already marked then boad is invalid
					if(tempboard[x][y] == true){
						//System.out.println();
						//printBoardtoTerminal(tempboard);
						return false;
					}
					else{	//else write the shape to there
						tempboard[x][y] = true;
					}
				}
			}
		return true;
	}

	//checks if Board is valid and if board is valid checks for winning condition
	public static boolean gameOver(byte[] state){
		//check to see if the square is in the correct placement
		//if the squares placement is [1][5], [1][6], [2][5], [2][6]
		if(pieces[0][0]+state[0]==1 && pieces[0][1]+state[1]==5 && pieces[0][2]+state[0]==1 && pieces[0][3]+state[1]==6 && pieces[0][4]+state[0]==2 && pieces[0][5]+state[1]==5 && pieces[0][6]+state[0]==2 && pieces[0][7]+state[1]==6){
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception{
		GameState game = null;
		try{
			game = GameState.BreadthFirst();
		} catch(Exception e){
			System.out.println(e);
		}


		Stack<GameState> stack = new Stack<GameState>();
		while(game != null){
			stack.push(game);
			game = game.previous;
		}

		while(!stack.empty()){
			GameState space = new GameState(stack.pop());
			for(int i=0; i<22; i+=2){
				System.out.print("(" + space.state[i+1] + "," + space.state[i] + ") ");
			}
			System.out.println();
		}
	}
}
