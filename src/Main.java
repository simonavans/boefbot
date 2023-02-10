import java.util.Scanner;

public class Main {
    private static boolean gameIsRunning = true;
    private static Player player = new Player(1, 1);

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        Scanner consoleInput = new Scanner(System.in);
        Screen.renderMap();

//        while (gameIsRunning) {
//            System.out.print("w: forward | s: backward | a: left | d: right\nInput: ");
//            String input = consoleInput.nextLine();
//
//            if (input.isEmpty()) continue;
//
//            switch (input.charAt(0)) {
//                case 'w':
//                    player.tryMovingTo(MovementType.UP);
//                case 's':
//                    player.tryMovingTo(MovementType.DOWN);
//                case 'a':
//                    player.tryMovingTo(MovementType.LEFT);
//                case 'd':
//                    player.tryMovingTo(MovementType.RIGHT);
//            }
//        }
    }
}

class Screen {
    private static String[][] map = {
            { "##", "##", "##", "##", "##", "##", "##", "##", "##", "##" },
            { "##", "  ", "  ", "  ", "  ", "##", "  ", "##", "  ", "##" },
            { "##", "  ", "##", "  ", "  ", "##", "  ", "##", "  ", "##" },
            { "##", "  ", "##", "##", "  ", "##", "  ", "  ", "  ", "##" },
            { "##", "  ", "  ", "##", "  ", "##", "  ", "##", "  ", "##" },
            { "##", "##", "  ", "##", "  ", "##", "  ", "##", "  ", "##" },
            { "##", "  ", "  ", "  ", "  ", "!!", "  ", "##", "  ", "##" },
            { "##", "##", "  ", "##", "##", "##", "##", "##", "  ", "##" },
            { " +", "  ", "  ", "##", " *", "  ", "  ", "  ", "  ", "##" },
            { "##", "##", "##", "##", "##", "##", "##", "##", "##", "##" },
    };
    private static boolean isEmeraldStolen = false;

    public static void renderMap() {
        for (String[] row : map) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    public static int[] playerTriedMove(int x, int y) {
        return new int[1];
    }
}

class Player {
    private int x;
    private int y;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void tryMovingTo(MovementType movementType) {
        int requestedX = 0;
        int requestedY = 0;

        switch (movementType) {
            case UP:
                requestedY = this.y + 1;
                break;
            case DOWN:
                requestedY = this.y - 1;
                break;
            case RIGHT:
                requestedX = this.x + 1;
                break;
            case LEFT:
                requestedX = this.x - 1;
        }

        int[] newLocation = Screen.playerTriedMove(requestedX, requestedY);
        this.x = newLocation[0];
        this.y = newLocation[1];
    }
}

class Guard {

}

enum MovementType { UP, DOWN, RIGHT, LEFT }
