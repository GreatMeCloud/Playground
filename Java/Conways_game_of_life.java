import java.util.*;
public class ConwaysGameOfLife {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//Introduction
		System.out.println("Welcome to Conway's Game of Life\n");
		//Enter the length and width of the array
		System.out.print("Please enter the length of the map:");
		int l = sc.nextInt(); 
		System.out.print("Please enter the width of the map:");
		int w = sc.nextInt();
		
		//Initialize a 2d array store the position of the cells
		char[][] map = new char[l][w];
		
		//Input the positions of cells
		System.out.println("Please fill the array with 0 (blank) and # (cell) without space:");
		for(int i = 0; i < l; i++) {
			String line = sc.next();
            for (int j = 0; j < w; j++) {
                map[i][j] = line.charAt(j);
            }
		}
		
		for(int i = 0; i < l; i++) {
            for (int j = 0; j < w; j++) {
                if(map[i][j] != '#' && map[i][j] != '0') {
                	System.out.println("Map input invalid\\nProgram ended");
                	return;
                }
            }
		}
		
		sc.nextLine();
		//Main program
		String answer;
		int counter = 1;
		while(true) {
			System.out.printf("\n---------------\nIteration %d\n\n",counter);
			counter++;
			
			map = Iteration(map);
			
			//Display the new map
			for(int i = 0; i < l; i++) {
				for(int j = 0; j < w; j++) {
					System.out.print(map[i][j]);
				}
				System.out.println();
			}
			
			//Continue or end the program
			while(true) {
				System.out.print("Press \'Enter\' to continue, input \'N\' to exit the program:");
				answer = sc.nextLine().toUpperCase();
				if(answer.equals("") || answer.equals("N")) break;
				else System.out.println("Wrong input please enter \'Enter\' or \'N\'\n");
			}
			
			//If user entered 'n', exit the program
			if(answer.equals("N")) {
				System.out.println("\nProgram ended");
				break;
			}
		}
	}
	
	public static char[][] Iteration(char[][] map){
		int l = map.length, w = map[0].length;
		char[][] tempMap = new char[l][w];
		for(int i = 0; i < l; i++) {
			for(int j = 0; j < w; j++) {
				//Count the number of neighbors
				int neighbor = 0;
				
				//These arrays used to access the neighbor coordinate 
				int[] dx = {-1,-1,-1,0,0,1,1,1};
				int[] dy = {-1,0,1,-1,1,-1,0,1};

				for(int d = 0; d < 8; d++){
				    int nx = i + dx[d];
				    int ny = j + dy[d];

				    if(nx >= 0 && nx < l && ny >= 0 && ny < w && map[nx][ny] == '#') {
				        neighbor++;
				    }
				}
				
				//The cell is alive on the previous day
				if(map[i][j] == '#') {
					if(neighbor == 2 || neighbor == 3) {
						tempMap[i][j] = '#';
					}
					else {
						tempMap[i][j] = '0';
					}
				}
				//The cell is not alive on the previous day
				else if(map[i][j] == '0'){
					if(neighbor == 3) {
						tempMap[i][j] = '#';
					}
					else {
						tempMap[i][j] = '0';
					}
				}
			}
		}
		
		return tempMap;
	}
}
