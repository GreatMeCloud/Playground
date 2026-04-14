//Yahtzee - Dungeon Dice Adventure - Final Project

/* Precaution
*
* The program used special emoji that can not be displayed on legacy computers.
* Please change to a newer computer system.
*/

/* Description
 * 
 * The game is based on Yahtzee game with Dungeon elements
 * Each player can play a maximum of three rounds per turn.
 * During gameplay, players can interrupt the game and score points immediately.
 * Players can also choose which dice to roll and which to keep.
 * The person with the most points wins at the end of the game.
 */
import java.util.*;
public class Yahtzee {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		//Introduction
		System.out.println("Welcome to 𝕯𝖚𝖓𝖌𝖊𝖔𝖓 𝕯𝖎𝖈𝖊 𝕬𝖉𝖛𝖊𝖓𝖙𝖚𝖗𝖊!");
		System.out.println("\n------------------\n");
		System.out.println("Gamemodes include:\n◆ Two-player Duel\n◆ Overlord Trial\n");
		
		//Select Mode
		System.out.print("(Enter 1 for Two-player Duel, enter 2 for Overlord Trial)\nSelect a mode:");
		int gamemode;
		while(true) {
			gamemode = sc.nextInt();
			if(gamemode != 1 && gamemode != 2) {
				System.out.print("\nWrong input\nSelect a mode carefully:");
			}
			else break;
		}
		if(gamemode == 1) {
			System.out.println("\nTwo-player Duel was selected");
		}
		else {
			System.out.println("Overlord Trial was seleced");
		}
		System.out.println("\n------------------\n");
		
		//Rules display
		sc.nextLine(); 
		System.out.print("Press R to read the rule, or enter any other key to skip:");
		String rule = sc.nextLine().trim().toUpperCase();
		if(rule.equals("R")) {
			RuleDisplay();
		}
		
		//Declarations
		Random rand = new Random();
		
		String[] items = {
				"① One-Sword Style\t", 
				"② Two-Sword Style\t", 
				"③ Trident Strike\t", 
				"④ Sharpshooter\t\t", 
				"⑤ Sword Souls\t\t", 
				"⑥ Shuriken Splash\t", 
				"⑦ Speed Up\t\t", 
				"⑧ Shield Up\t\t", 
				"⑨ Full house\t\t",  
				"⑩ Accessories\t\t", 
				"⑪ Charge Up\t\t", 
				"⑫ Chance\t\t", 
				"⑬ YAHTZEE\t\t"};
		   
		
		int[][] score = new int[2][items.length];
		//initial score elements to -1
		for(int j = 0; j < score.length; j++) {
		 for(int i = 0; i < score[0].length; i++) score[j][i] = -1;
		}
		
		int who = 0;//Whose turn
		
		//How many rounds
		sc.nextLine();
		int rounds;
		while(true){
			System.out.print("\nHow many rounds you want to play:");
			rounds = sc.nextInt();
			if(rounds > 0 && rounds < items.length+1) break;
			System.out.println("Wrong input, please enter a number that >0 and ≦" + items.length);
		}
		
		wait(300);
		System.out.println("\nAdventure begins!\n");
		wait(2000);
		
		//Total number of rounds is 2*(items.length - 1)
		for(int l = 0; l < 2*rounds; l++) {
			int[] AvalibleDice = {-1, -1, -1, -1, -1};
			int[] FixedDice = new int[5];
			int nextplayer = 0;
			//A round begins
			System.out.println("\n------------------\n");
			for(int i = 0; i < 3; i++) {
				//Initialization
				for(int j = 0; j < 5; j++) {
					if(AvalibleDice[j] != 0) AvalibleDice[j] = rand.nextInt(6) + 1;
				}
				//Display turn and rounds
				System.out.println("Turn " + (l/2+1));
				if(gamemode == 1) {
					System.out.println("Player " + (who+1) + ": Round " + (i+1) + "/3");
				}
				else {
					if(who == 0)System.out.println("Player round " + (i+1) + "/3");
					else System.out.println("Overlord round " + (i+1) + "/3");
				}
				//Display dices
				DicesDisplay(AvalibleDice, FixedDice, who);
				
				//three rounds per turn, and only first two rounds can make free moves
				if(i == 0 || i == 1) {
					
					int nextStep;
					//Decide next step
					while(true) {
						//Human
						if(gamemode != 2 || who != 1) {
							System.out.println("Choose the following operations\n");
							System.out.println("◆ ① Continue rolling the dice");
							System.out.println("◆ ② Pick up the dice not going to roll");
							System.out.println("◆ ③ Move some dice back to the table");
							System.out.println("◆ ④ Check the score chart\n");
							System.out.print("Enter your next step:");
							nextStep = sc.nextInt();
							sc.nextLine();//Prevent leakage
						}
						//Bot
						else {
							nextStep = botStep(AvalibleDice, FixedDice, who, score);
							if(nextStep == 0) {
								System.out.println("The Overlord choose to finish this round");
								nextStep = 4;
							}
							else if(nextStep == 1) {
								System.out.println("The Overlord choose to continue rolling the dice");
							}
							else if(nextStep == 2) {
								System.out.println("The Overlord choose to move dice");
							}
						}
						
						//Procession
						if(nextStep == 1) {
							break;
						}
						else if(nextStep == 2) {
							moveDice(AvalibleDice, FixedDice, who, gamemode, 1, score);
						}
						else if(nextStep == 3) {
							moveDice(AvalibleDice, FixedDice, who, gamemode, 2, score);
						}
						else if(nextStep == 4) {
							nextplayer = chartDisplay(gamemode, who, items, score, AvalibleDice, FixedDice, i);
							if(nextplayer == 1) break;
						}
						else {
							System.out.println("Please enter numbers from 1 to 4");
						}
						System.out.println();
						//bot only move once
						if(gamemode == 2 && who == 1) break;
					}
					
					System.out.println("\n------------------\n");
				}
				if(nextplayer == 1) break;
				if(gamemode == 2 && who == 1) wait(2000);
			}
			
			if(!(gamemode == 2 && who == 1) && nextplayer != 1) {
				System.out.println("Three rounds end. Start settlement for this round");
				wait(1000);
				System.out.println("Press Enter to continue");
				sc.nextLine();
			}
			//Three rounds ended
			if(nextplayer != 1) chartDisplay(gamemode, who, items, score, AvalibleDice, FixedDice, 2);
			
			if(gamemode == 2 && who == 1) wait(1000);
			//change player
			if(who == 0) who++;
			else who = 0;
		}
		
		//unused symbols
		String[] symbols = {"🔪", "⚔️", "🔱", "🏹", "🗡", "💥", "🚕", "🛡️", "🏠", "👑", "⚡️", "🎰", "🔥"};
		
		int[] sum = new int[2];
		//Game settlement
		System.out.println("\n------------------\n");
		for(int i = 0; i < 3; i++) {
			System.out.println("Final Battle");
			wait(500);
		}
		wait(500);
		
		for(int i = 0; i < score.length; i++) {
			for(int j = 0; j < score[0].length; j++) {
				if(score[i][j] != -1) sum[i] += score[i][j];
			}
		}
		
		System.out.println("┌───────────────────────┬───────────────┬───────────────┐");
		if(gamemode == 1) System.out.println("|                       |   Player 1    |    Player 2   |");
		else System.out.println("|                       |    Player     |   Overlord    |");
		for(int i = 0; i < items.length; i++) {  
			wait(700);
			System.out.printf("|%s|", items[i]);
			for(int j = 0; j < score.length; j++) {
				if(score[j][i] != -1) System.out.printf("\t%d\t|", score[j][i]);
				else System.out.printf("\t0\t|");
			}
			System.out.println();
		}
		wait(700);
		System.out.printf("|Total Attack Power\t|");
		wait(1500);
		System.out.printf("\t%d\t|\t%d\t|\n", sum[0], sum[1]);
		System.out.println("└───────────────────────┴───────────────┴───────────────┘");
		
		wait(1000);
		System.out.println("\n------------------\n");
		
		if(gamemode == 1) {
			if(sum[0] > sum[1]) System.out.println("🎉Player 1 won. GG🎉");
			else if(sum[0] < sum[1])System.out.println("🎉Player 2 won. GG🎉");
			else System.out.println("😲The game ended in a draw. Well played👍");
		}
		else {
			if(sum[0] > sum[1]) System.out.println("🎉Player won. GG🎉");
			else if(sum[0] < sum[1])System.out.println("👹👺👿The Overlord won.");
			else System.out.println("😲The game ended in a draw. Well played👍");
		}
	}
	
	public static void wait(int t) {
		try {
            Thread.sleep(t);
        } 
		catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
	
	public static int[] combination(int[] AvalibleDice, int[]FixedDice, int who) {
		int[] AllDice = new int[5];
		int counter = 0;
		//Store all dice in an array
		for (int i = 0; i < 5; i++) {
		    if (AvalibleDice[i] != 0) {
		        AllDice[counter++] = AvalibleDice[i];
		    }
		}
		for (int i = 0; i < 5; i++) {
		    if (FixedDice[i] != 0) {
		        AllDice[counter++] = FixedDice[i];
		    }
		}
		//Sort the array
		Arrays.sort(AllDice);
		
		int[] PossibleChoice = new int[13];
		
		//① One-Sword Style
		//② Two-Sword Style 
		//③ Trident Strike
		//④ Sharpshooter
		//⑤ Sword Souls 
		//⑥ Shuriken Splash
		//Continuous Combination
		for(int i = 0; i < 5; i++) {
			if(AllDice[i] == 1) PossibleChoice[0]++;
			else if(AllDice[i] == 2) PossibleChoice[1] += 2;
			else if(AllDice[i] == 3) PossibleChoice[2] += 3;
			else if(AllDice[i] == 4) PossibleChoice[3] += 4;
			else if(AllDice[i] == 5) PossibleChoice[4] += 5;
			else PossibleChoice[5] += 6;
		}
		
		//⑦ Speed Up
		for(int i = 0; i < 3; i++) {
			if(AllDice[i] == AllDice[i+1] && AllDice[i+1] == AllDice[i+2]) {
				for(int j = 0; j < 3; j++) PossibleChoice[6] += AllDice[i+j];
			}
		}
		//⑧ Shield Up 
		for(int i = 0; i < 2; i++) {
			if(AllDice[i] == AllDice[i+1] && AllDice[i+1] == AllDice[i+2] && AllDice[i+2] == AllDice[i+3]) {
				for(int j = 0; j < 4; j++) PossibleChoice[7] += AllDice[i+j];
			}
		}
		//⑨ Full house  
		if((AllDice[0] == AllDice[1] && AllDice[2] == AllDice[3] && AllDice[3] == AllDice[4] && AllDice[1] != AllDice[2]) ||
				(AllDice[3] == AllDice[4] && AllDice[0] == AllDice[1] && AllDice[1] == AllDice[2] && AllDice[2] != AllDice[3])) {
			PossibleChoice[8] = 25;
		}
		//⑩ Accessories
		boolean[] exist = new boolean[7]; 

		// Mark the dice that have appeared
		for (int i = 0; i < AllDice.length; i++) {
		    exist[AllDice[i]] = true;
		}

		// Check if 4 dice are consecutive
		for (int i = 1; i <= 3; i++) { // 1-4, 2-5, 3-6
		    if (exist[i] && exist[i+1] && exist[i+2] && exist[i+3]) {
		        PossibleChoice[9] = 30;
		        break;
		    }
		}

		
		//⑪ Charge Up
		if(AllDice[0] == AllDice[1]-1 && AllDice[1] == AllDice[2]-1 && AllDice[2] == AllDice[3]-1 && AllDice[3] == AllDice[4]-1) {
			PossibleChoice[10] = 40;
		}
		//⑫ Chance 
		for(int i = 0; i < 5; i++) PossibleChoice[11] += AllDice[i];
		//⑬ YAHTZEE
		if(AllDice[0] == AllDice[1] && AllDice[1] == AllDice[2] && AllDice[2] == AllDice[3] && AllDice[3] == AllDice[4]) PossibleChoice[12] = 50;
		
		
		return PossibleChoice; 
	}
	
	public static int botStep(int[] AvalibleDice, int[]FixedDice, int who, int[][]score) {
		//current dice score
		int[] PossibleChoice = combination(AvalibleDice, FixedDice, who);
		
		//expectation
		
		//check lower section
		//⑬ YAHTZEE, ⑪ Charge Up, ⑩ Accessories, ⑨ Full house 
		if(PossibleChoice[12] != 0 && score[1][12] == -1) return 4;
		if(PossibleChoice[10] != 0 && score[1][10] == -1) return 4;
		if(PossibleChoice[9] != 0 && score[1][9] == -1) return 4;
		if(PossibleChoice[8] != 0 && score[1][8] == -1) return 4;
		//⑧ Shield Up
		//try to get ⑬ YAHTZEE
		if(PossibleChoice[7] != 0) {
			if(score[1][12] == -1) return 2; //move dice. not necessarily mean to pick up but also could be move back
			return 4; //end the round
		}
		//⑦ Speed Up
		//try to get ⑧ Shield Up or ⑨ Full house
		if(PossibleChoice[6] != 0) {
			if(score[1][7] == -1 || score[1][8] == -1) return 2; //explanation as above
			return 4; //end the round
		}
		
		//check upper section (① to ⑥) when lower section are all occupied.
		boolean occupied = true;
		for(int i = score.length - 1; i > 6; i--) {
			if(score[1][i] == -1) {
				occupied = false;
				break;
			}
		}
		if(occupied) {
			return 2;
		}
		//⑫ Chance
		//Guaranteed option
		
		//still some lower sections are avalible
		//Straight 
		
		//Kind
		
		//No suitable choice
	    return 1;//keep rolling
	}
	
	public static void moveDice(int[] AvalibleDice, int[]FixedDice, int who, int gamemode, int operation, int[][] score) {
		Scanner sc = new Scanner(System.in); 
		//human
		if(gamemode != 2 || who != 1) {
			System.out.print("Enter the corresponding dice numbers (split with spaces):");
			String numbers = sc.nextLine().trim();
			if(numbers.length() == 0) {
				System.out.println("No number was entered");
				return;
			}
			numbers += " ";
			//Find numbers by traversing a string
			String temp = "";
			for(int i = 0; i < numbers.length(); i++) {
				//wrong input
				if((numbers.charAt(i) < '1' || numbers.charAt(i) > '6') && numbers.charAt(i) != ' ') {
					System.out.println("Number out of range");
					return;
				}
				
				if(numbers.charAt(i) == ' ') {
					if(operation == 1) {
						//does the number exist in the array
						boolean exist = false;
						for(int j = 0; j < 5; j++) {
							if(AvalibleDice[j] == Integer.parseInt(temp)) {
								exist = true;
								break;
							}
						}
						if(!exist) {
							System.out.println("Dice " + temp + " is not on the table");
							return;
						}
						//Add to fixed
						for(int j = 0; j < 5; j++) {
							if(FixedDice[j] == 0) {
								FixedDice[j] = Integer.parseInt(temp);
								break;
							}
						}
						//Delete from table
						for(int j = 0; j < 5; j++) {
							if(AvalibleDice[j] == Integer.parseInt(temp)) {
								AvalibleDice[j] = 0;
								break;
							}
						}
						
						System.out.println("Dice " + temp + " was held aside");
						 
						 temp = "";
					}
					else {
						//does the number exist in the array
						boolean exist = false;
						for(int j = 0; j < 5; j++) {
							if(FixedDice[j] == Integer.parseInt(temp)) {
								exist = true;
								break;
							}
						}
						if(!exist) {
							System.out.println("Dice " + temp + " is not held");
							return;
						}
						//Move back to table
						 for(int j = 0; j < 5; j++) {
							 if(AvalibleDice[j] == 0) {
								 AvalibleDice[j] = Integer.parseInt(temp);
								 break;
							 }
						 }
						//Delete from fixed
						 for(int j = 0; j < 5; j++) {
							 if(FixedDice[j] == Integer.parseInt(temp)) {
								 FixedDice[j] = 0;
								 break;
							 }
						 }
						 
						 System.out.println("Dice " + temp + " was moved back to table");
						 
						 temp = "";
					}
				}
				else {
					temp += numbers.charAt(i);
				}
			}
			wait(1000);
		}
		//bot
		else {
			int[] PossibleChoice = combination(AvalibleDice, FixedDice, who);
			int[] AllDice = new int[5];
			int counter = 0;
			//Store all dice in an array
			for (int i = 0; i < 5; i++) {
			    if (AvalibleDice[i] != 0) {
			        AllDice[counter++] = AvalibleDice[i];
			    }
			}
			for (int i = 0; i < 5; i++) {
			    if (FixedDice[i] != 0) {
			        AllDice[counter++] = FixedDice[i];
			    }
			}
			//Sort the array
			Arrays.sort(AllDice);
			
			//⑧ Shield Up
			//try to get ⑬ YAHTZEE
			if(PossibleChoice[7] != 0) {
				if(score[1][12] == -1) {
					for(int i = 0; i < 2; i++) {
						if(AllDice[i] == AllDice[i+1] && AllDice[i+1] == AllDice[i+2] && AllDice[i+2] == AllDice[i+3]) {
							for(int j = 0; j < 5; j++) {
								if(AllDice[j] != AllDice[i]) {
									AvalibleDice[j] = AllDice[j];
									FixedDice[j] = 0;
								}
								else {
									FixedDice[j] = AllDice[j];
									AvalibleDice[j] = 0;
								}
							}
							return;
						}
					}
				}
			}
			//⑦ Speed Up
			//try to get ⑧ Shield Up or ⑨ Full house
			if(PossibleChoice[6] != 0) {
				if(score[1][7] == -1 || score[1][8] == -1) {
					for(int i = 0; i < 3; i++) {
						if(AllDice[i] == AllDice[i+1] && AllDice[i+1] == AllDice[i+2]) {
							for(int j = 0; j < 5; j++) {
								if(AllDice[j] != AllDice[i]) {
									AvalibleDice[j] = AllDice[j];
									FixedDice[j] = 0;
								}
								else {
									FixedDice[j] = AllDice[j];
									AvalibleDice[j] = 0;
								}
							}
							return;
						}
					}
				}
			}
			//check upper section (① to ⑥) when lower section are all occupied.
			boolean occupied = true;
			for(int i = score.length - 1; i > 6; i--) {
				if(score[1][i] == -1) {
					occupied = false;
					break;
				}
			}
			if(occupied) {
				//From large to small
				for(int i = 4; i > 0; i--) {
					if(AllDice[i] > 3) {
						AvalibleDice[i] = 0;
						FixedDice[i] = AllDice[i];
					}
				}
			}
			return;
		}
	}
	
	public static int chartDisplay(int gamemode, int who, String[] items, int[][]score, int[] AvalibleDice, int[]FixedDice, int round) {
		Scanner sc = new Scanner(System.in);
		
		//human
		if(gamemode != 2 || who != 1) {
			//Display the chart
			System.out.println("┌───────────────────────┬───────────────┬───────────────┐");
			if(gamemode == 1) System.out.println("|                       |   Player 1    |    Player 2   |");
			else System.out.println("|                       |    Player     |   Overlord    |");
			for(int i = 0; i < items.length; i++) {   
				System.out.printf("|%s|", items[i]);
				for(int j = 0; j < score.length; j++) {
					if(score[j][i] != -1) System.out.printf("\t%d\t|", score[j][i]);
					else System.out.printf("\t \t|");
				}
				System.out.println();
			}
			System.out.println("└───────────────────────┴───────────────┴───────────────┘");
			//Display choice
			int[] combo = combination(AvalibleDice, FixedDice, who); 
			boolean exist = false;
			System.out.println("\nYou can choose following combination:");
			for(int i = 0; i < combo.length; i++) {
				if(combo[i] != 0 && score[who][i] == -1) {
					System.out.println(items[i] + ": Get " + combo[i] + " points");
					exist = true;
				}
			}
			if(!exist) System.out.println("None of these combination can score\n");
			else System.out.println("Or you can choose any other spot but no score for this round\n");
			
			//take spot
			if(round != 2) {
				while(true) {
					System.out.print("Are you going to take one spot (Y/N):");
					String answer = sc.nextLine().toUpperCase();
					if(answer.equals("Y")){
						break;
					}
					if(answer.equals("N")) {
						return 0;
					}
					else {
						System.out.println("Please enter \'Y\' or \'N\'");
					}
				}
			}
			
			int comboNum;
			do {
				System.out.print("Enter the corresponding combination number:");
				comboNum = sc.nextInt();
				if(comboNum < 1|| comboNum > items.length) {
					System.out.println("Combination number not exist");
					continue;
				}
				if(score[who][comboNum - 1] != -1) {
					System.out.println("The combination has been taken");
					continue;
				}
				break;
			} while(true);
			System.out.println("You get " + combo[comboNum - 1] + " scores from " + comboNum);
			score[who][comboNum - 1] = combo[comboNum - 1];
			wait(1500);
			return 1;
		}
		//bot
		else {
			int[] combo = combination(AvalibleDice, FixedDice, who);
			//Declare an array to store the index of combo elements from large to small
			int n = combo.length;
			int[] indices = new int[n];
			for(int i = 0; i < n; i++) indices[i] = i;//1, 2, 3, 4....
			//Sort indices by using tempC
			for(int i = 0; i < combo.length - 1; i++) {
				int max = i;
				for(int j = i + 1; j < combo.length; j++) {
					if(combo[indices[max]] < combo[indices[j]]) max = j;
				}
				int temp = indices[i];
				indices[i] = indices[max];
				indices[max] = temp;
			}
			//try to put into score
			for(int i = 0; i < combo.length; i++) {
				if(score[who][indices[i]] == -1) {
					score[who][indices[i]] = combo[indices[i]];
					System.out.println("The Overlord got " + combo[indices[i]] + " from " + items[indices[i]]);
					break;
				}
			}
			return 1;
		}
	}
	
	//Display dice on the table and simulate a random roll place.
	public static void DicesDisplay(int[] Dice, int[] fixed, int who) {
		Random rand = new Random();
		
		int[] oneDarray = new int[48];
		
		int counter = 0, placed = 0;
		//Place dices on an 1D array randomly
		while(placed != 5) {
			if(rand.nextInt(48) == 0 && oneDarray[counter] == 0) {
				oneDarray[counter] = Dice[placed];
				placed++;
			}
			counter++;
			if(counter >= 48) counter = 0;
		}
		
		//Display the array on the table
		System.out.println("┌────────────┐");
		for(int i = 0; i < 4; i++) {
			System.out.print("|");
			for(int j = 0; j < 12; j++) {
				if(oneDarray[i*12+j] == 1) System.out.print("⚀");
				else if(oneDarray[i*12+j] == 2) System.out.print("⚁");
				else if(oneDarray[i*12+j] == 3) System.out.print("⚂");
				else if(oneDarray[i*12+j] == 4) System.out.print("⚃");
				else if(oneDarray[i*12+j] == 5) System.out.print("⚄");
				else if(oneDarray[i*12+j] == 6) System.out.print("⚅");
				else System.out.print(" ");
			}
			System.out.println("|");
		}
		System.out.println("└────────────┘");
		System.out.print("Hold Area:");
		for(int i = 0; i < 5; i++) {
			if(fixed[i] != 0) {
				if(fixed[i] == 1) System.out.print("⚀");
				else if(fixed[i] == 2) System.out.print("⚁");
				else if(fixed[i] == 3) System.out.print("⚂");
				else if(fixed[i] == 4) System.out.print("⚃");
				else if(fixed[i] == 5) System.out.print("⚄");
				else if(fixed[i] == 6) System.out.print("⚅");
			}
		}
		System.out.println();
	}
	
	public static void RuleDisplay() {
		System.out.println("𓆝 𓆟 𓆞 𓆝 𓆟 Game Rules 𓆝 𓆟 𓆞 𓆝 𓆟\n");
		
		System.out.println("Upper Section Combinations\n");
		System.out.println("🔪️ ① One-Sword Style: \tGet as many ones as possible");
		System.out.println("⚔️ ② Two-Sword Style: \tGet as many twos as possible");
		System.out.println("🔱 ③ Trident Strike: \tGet as many threes as possible");
		System.out.println("🏹 ④ Sharpshooter: \tGet as many fours as possible");
		System.out.println("🗡️ ⑤ Sword Souls: \tGet as many fives as possible");
		System.out.println("💥 ⑥ Shuriken Splash: \tGet as many sixes as possible");
		
		System.out.println("\nLower Section Combinations\n");
		System.out.println("🚕 ⑦ Speed Up: \t\tGet three dice with the same number. Points are the sum all dice (not just the three of a kind).");
		System.out.println("🛡️ ⑧ Shield Up: \tGet four dice with the same number. Points are the sum all dice (not just the four of a kind).");
		System.out.println("🏠 ⑨ Full house: \tGet three of a kind and a pair, e.g. 1,1,3,3,3 or 3,3,3,6,6. Scores 25 points.");
		System.out.println("👑 ⑩ Accessories: \tGet four sequential dice, 1,2,3,4 or 2,3,4,5 or 3,4,5,6. Scores 30 points.");
		System.out.println("⚡️ ⑪ Charge Up: \tGet five sequential dice, 1,2,3,4,5 or 2,3,4,5,6. Scores 40 points.");
		System.out.println("🎰 ⑫ Chance: \t\tYou can put anything into chance. The score is simply the sum of the dice.");
		System.out.println("🔥 ⑬ YAHTZEE: \t\tFive of a kind. Scores 50 points.");
		
		System.out.println("\nPress Enter to continue");
	}
}
