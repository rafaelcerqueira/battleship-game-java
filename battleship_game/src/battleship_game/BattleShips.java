package battleship_game;

import java.util.Scanner;

public class BattleShips {
    public static int numRows = 10;
    public static int numCols = 10;
    public static int playerShips;
    public static int cpuShips;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];

    public static void main(String[] args){
        System.out.println("Bem-vindo ao jogo de Batalha Naval!");

        createOceanMap();

        deployPlayerShips();

        deployCpuShips();

        do {
            Battle();
        } while (BattleShips.playerShips != 0 && BattleShips.cpuShips != 0);

        gameOver();
    }

    public static void createOceanMap() {
        System.out.print(" ");

        for (int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = " ";
                if (j == 0)
                    System.out.print(i + "|" + grid[i][j]);
                else if (j == grid[i].length - 1)
                    System.out.print(grid[i][j] + "|" + i);
                else
                    System.out.println(grid[i][j]);
            }
            System.out.println();
        }

        System.out.print(" ");
        for (int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

    }

    public static void deployPlayerShips() {
        Scanner input = new Scanner(System.in);

        System.out.println("\nPosicione seus navios:");
        BattleShips.playerShips = 5;
        for (int i = 1; i <= BattleShips.playerShips; ) {
            System.out.print("Digite a coordenada X para o seu navio " + i + " : ");
            int x = input.nextInt();
            System.out.print("Digite a coordenada Y para o seu navio " + i + " : ");
            int y = input.nextInt();

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " ")) {
                grid[x][y] = "@";
                i++;
            } else if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[x][y] == "@")
                System.out.println("Você não pode posicionar dois ou mais navios no mesmo lugar");
            else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                System.out.println("Você não pode posicionar o navio fora da matriz " + numRows + " por " + numCols);
        }
        printOceanMap();
    }

    public static void deployCpuShips() {
        System.out.println("\nCPU está posicionando os navios...");

        BattleShips.cpuShips = 5;
        for (int i = 1; i <= BattleShips.cpuShips; ) {
            int x = (int)(Math.random() * 10);
            int y = (int)(Math.random() * 10);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[x][y] == " ")) {
                grid[x][y] = "x";
                System.out.println(i + ". navio posicionado");
                i++;
            }
        }
        printOceanMap();
    }

    public static void Battle() {
        playerTurn();
        cpuTurn();

        printOceanMap();

        System.out.println();
        System.out.println("Seus navios: " + BattleShips.playerShips + " | CPU ships: " + BattleShips.cpuShips);
        System.out.println();
    }

    public static void playerTurn() {
        System.out.println("\nSua vez!");
        int x = -1, y = -1;

        do {
            Scanner input = new Scanner(System.in);
            System.out.print("Digite a coordenada X: ");
            x = input.nextInt();
            System.out.print("Digite a coordenada Y: ");
            y = input.nextInt();

            //tentativa válida
            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) {
                //quando o player acerta o navio!
                if (grid[x][y] == "x") {
                    System.out.println("Acertou!");
                    grid[x][y] = "!";
                    --BattleShips.cpuShips;
                } else if(grid[x][y] == "@") {
                    System.out.println("Ah, não! Você afundou seu próprio navio.");
                    grid[x][y] = "x";
                    --BattleShips.playerShips;
                    ++BattleShips.cpuShips;
                }else if (grid[x][y] == " ") {
                    System.out.println("Desculpa, errou");
                    grid[x][y] = "-";
                }
            } else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //invalid guess
                System.out.println("Você não pode posicionar o navio fora da matriz " + numRows + " by " + numCols);
            }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
        }

    public static void cpuTurn(){
        System.out.println("\nÉ a vez do CPU");
        //Guess co-ordinates
        int x = -1, y = -1;
        do {
            x = (int)(Math.random() * 10);
            y = (int)(Math.random() * 10);

            if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
            {
                if (grid[x][y] == "@") //if player ship is already there; player loses ship
                {
                    System.out.println("O CPU afundou seu navio");
                    grid[x][y] = "x";
                    --BattleShips.playerShips;
                    ++BattleShips.cpuShips;
                }
                else if (grid[x][y] == "x") {
                    System.out.println("O CPU afundou o próprio navio");
                    grid[x][y] = "!";
                }
                else if (grid[x][y] == " ") {
                    System.out.println("O CPU errou");
                    //Saving missed guesses for computer
                    if(missedGuesses[x][y] != 1)
                        missedGuesses[x][y] = 1;
                }
            }
        }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
    }

    public static void gameOver(){
        System.out.println("Seus navios: " + BattleShips.playerShips + " | Navios do CPU: " + BattleShips.cpuShips);
        if(BattleShips.playerShips > 0 && BattleShips.cpuShips <= 0)
            System.out.println("Você venceu a batalha! :)");
        else
            System.out.println("Tente outra vez! Você perdeu a batalha. ");
        System.out.println();
    }

    public static void printOceanMap(){
        System.out.println();
        //First section of Ocean Map
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

        //Middle section of Ocean Map
        for(int x = 0; x < grid.length; x++) {
            System.out.print(x + "|");

            for (int y = 0; y < grid[x].length; y++){
                System.out.print(grid[x][y]);
            }

            System.out.println("|" + x);
        }

        //Last section of Ocean Map
        System.out.print("  ");
        for(int i = 0; i < numCols; i++)
            System.out.print(i);
        System.out.println();

    }

}
