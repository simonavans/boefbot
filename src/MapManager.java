import java.util.Arrays;

public class MapManager {
    private static final String[][] templateMap = {
            { "#", "#", "#", "#", "#", "#", "#", "#", "#", "#" },
            { "#", " ", " ", " ", " ", "#", " ", "#", " ", "#" },
            { "#", " ", "#", " ", " ", "#", " ", "#", " ", "#" },
            { "#", " ", "#", "#", " ", "#", " ", " ", " ", "#" },
            { "#", " ", " ", "#", " ", "#", " ", "#", " ", "#" },
            { "#", "#", " ", "#", " ", "#", " ", "#", " ", "#" },
            { "#", " ", " ", " ", " ", " ", " ", "#", " ", "#" },
            { "#", "#", " ", "#", "#", "#", "#", "#", " ", "#" },
            { " ", " ", " ", "#", " ", " ", " ", " ", " ", "#" },
            { "#", "#", "#", "#", "#", "#", "#", "#", "#", "#" }
    };
    private static String[][] updatableMap = {
            { "#", "#", "#", "#", "#", "#", "#", "#", "#", "#" },
            { "#", " ", " ", " ", " ", "#", " ", "#", " ", "#" },
            { "#", " ", "#", " ", " ", "#", " ", "#", " ", "#" },
            { "#", " ", "#", "#", " ", "#", " ", " ", " ", "#" },
            { "#", " ", " ", "#", " ", "#", " ", "#", " ", "#" },
            { "#", "#", " ", "#", " ", "#", " ", "#", " ", "#" },
            { "#", " ", " ", " ", " ", " ", " ", "#", " ", "#" },
            { "#", "#", " ", "#", "#", "#", "#", "#", " ", "#" },
            { "P", " ", " ", "#", "E", " ", " ", " ", " ", "#" },
            { "#", "#", "#", "#", "#", "#", "#", "#", "#", "#" }
    };
    private Location playerLocation = new Location(0, 1);
    private final Location emeraldLocation = new Location(4, 1);
    private final Location finishLocation = new Location(-1, 1);
    private boolean playerHasEmerald = false;

    public void renderMap() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                updatableMap[i][j] = templateMap[i][j];
            }
        }

        updatableMap[updatableMap.length - 1 - playerLocation.getY()][playerLocation.getX()] = "P";

        if (!playerHasEmerald) {
            updatableMap[updatableMap.length - 1 - emeraldLocation.getY()][emeraldLocation.getX()] = "E";
        }
    }
    
    public String getFieldAt(int x, int y) {
        return updatableMap[updatableMap.length - 1 - y][x];
    }

    public String getFieldAt(Location location) {
        if (location.getX() == finishLocation.getX() && location.getY() == finishLocation.getY()) {
            if (playerHasEmerald) Main.resetGame();
            return "#";
        }
        return updatableMap[updatableMap.length - 1 - location.getY()][location.getX()];
    }

    public Location tryMoveTo(String direction) {
        Location tileLocation = new Location(playerLocation);

        switch (direction) {
            case "up":
                tileLocation.setY(playerLocation.getY() + 1);
                break;
            case "down":
                tileLocation.setY(playerLocation.getY() - 1);
                break;
            case "left":
                tileLocation.setX(playerLocation.getX() - 1);
                break;
            default:
                // case right
                tileLocation.setX(playerLocation.getX() + 1);
        }

        if (getFieldAt(tileLocation).equals(" ")) {
            playerLocation = tileLocation;
        } else if (getFieldAt(tileLocation).equals("E")) {
            playerHasEmerald = true;
            playerLocation = tileLocation;
        }
        return playerLocation;
    }
}
