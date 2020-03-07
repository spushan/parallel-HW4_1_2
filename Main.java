
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    public static void main( String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of players :");
         int playerNum = scanner.nextInt();
        scanner.close();
        // creates the player object and push it in the array list
        ArrayList<Player> playerList = new ArrayList<Player>();
        for (int i = 0; i < playerNum; i++) {
            Player temp = new Player(String.valueOf(i), playerList);
            playerList.add(temp);
        }
        // end
        
        Player currentHighPlayer = null;
        while (playerList.size() > 1) {
            ForkJoinPool pool = ForkJoinPool.commonPool();
            for (Player temp : playerList) {
                temp.setGesture(); // picks from rock paper scissors
                System.out.println("player picks gesture");
            }

            for (Player temp : playerList) {
                System.out.println("players executing");
                pool.execute(temp);
            }
            pool.shutdown();
            System.out.println("executor shutdown");

            currentHighPlayer = playerList.get(0);
            for (Player temp : playerList) {
                if (temp.getScore() >= currentHighPlayer.getScore()) {
                    System.out.println("new high player");
                    currentHighPlayer = temp;
                }
            }
            currentHighPlayer.setWinner(true);
            currentHighPlayer.run();
        }
        System.out.println("Winner "+ currentHighPlayer.getName());
    }

    public static class Player implements Runnable {
        private String name;
        private int gesture;
        private int score;
        private ArrayList<Player> playerList;
        private boolean winner = false;

        public Player(String name, ArrayList<Player> playerList) {
            this.name = "Player " + name;
            this.playerList = playerList;
        }

        public String getName() {
            return name;
        }

        public int getGesture() {
            return gesture;
        }

        public void setGesture() {
            Random random = new Random();
            this.gesture = random.nextInt(3);
        }

        public int getScore() {
            return score;
        }

        public void check(int opponent) {
            switch (opponent) {
                case 1:
                    if (gesture == 2) {
                        score++;
                        System.out.println("score increase");
                    } else if (gesture == 3) {
                        score--;
                        System.out.println("score decrease");
                    }
                    break;
                case 2:
                    if (gesture == 3) {
                        score++;
                        System.out.println("score increase");
                    } else if (gesture == 1) {
                        score--;
                        System.out.println("score decrease");
                    }
                    break;
                case 3:
                    if (gesture == 1) {
                        score++;
                        System.out.println("score increase");
                    } else if (gesture == 2) {
                        score--;
                        System.out.println("score decrease");
                    }
                    break;
                default:
                    break;
            }
        }

        public void setWinner(boolean result) {
            this.winner = result;
        }

        public void run() {
            if (winner) {
                Player currentLowPlayer = playerList.get(0);
                for (Player temp : playerList) {
                    if (temp.getScore() < currentLowPlayer.getScore()) {
                        currentLowPlayer = temp;
                        System.out.println("new low player" + currentLowPlayer.getName());
                    }
                }
                System.out.println("player remove");
                System.out.print("player List size: ");
                System.out.println(playerList.size());
                playerList.remove(currentLowPlayer);
            } else {
                for (Player temp : playerList) {
                    System.out.println(this.getName() + " checking gesture of " + temp.getName());
                    check(temp.getGesture());
                }
            }
        }

    }


}
































