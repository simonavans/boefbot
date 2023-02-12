public class MapManager {
    private static final String[][] templateMap = {
            { "#", "#", "#", "#", "#", "#", "#", "#", "#", "#" },
            { "#", " ", " ", " ", " ", " ", " ", "#", " ", "#" },
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
            { "#", " ", " ", " ", "G", " ", " ", "#", " ", "#" },
            { "#", " ", "#", " ", " ", "#", " ", "#", " ", "#" },
            { "#", " ", "#", "#", " ", "#", " ", " ", " ", "#" },
            { "#", " ", " ", "#", " ", "#", " ", "#", " ", "#" },
            { "#", "#", " ", "#", " ", "#", " ", "#", " ", "#" },
            { "#", " ", "G", " ", " ", " ", " ", "#", " ", "#" },
            { "#", "#", " ", "#", "#", "#", "#", "#", " ", "#" },
            { "P", " ", " ", "#", "E", " ", " ", " ", " ", "#" },
            { "#", "#", "#", "#", "#", "#", "#", "#", "#", "#" }
    };
    private Location playerLocation = new Location(0, 1);
    private final Location emeraldLocation = new Location(4, 1);
    private final Location finishLocation = new Location(-1, 1);
    private boolean playerHasEmerald = false;
    private final Guard guard1 = new Guard(2, 3, false);
    private final Guard guard2 = new Guard(4, 8, true);

    public void renderMap() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                updatableMap[i][j] = templateMap[i][j];
            }
        }

        setFieldAt(playerLocation, "P");
        if (!playerHasEmerald) {
            setFieldAt(emeraldLocation, "E");
        }

        updateGuard(guard1);
        updateGuard(guard2);
    }
    
    public String getFieldAt(int x, int y) {
        return updatableMap[updatableMap.length - 1 - y][x];
    }

    private void setFieldAt(Location location, String fieldType) {
        updatableMap[updatableMap.length - 1 - location.getY()][location.getX()] = fieldType;
    }

    private String getFieldAt(Location location) {
        if (location.getX() == finishLocation.getX() && location.getY() == finishLocation.getY()) {
            if (playerHasEmerald) {
                triggerGameOver();
            }
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

    private void updateGuard(Guard guard) {
        Location attemptedLocation = guard.tryPatrolling();
        String field = getFieldAt(attemptedLocation);
        if (field.equals(" ")) {
            Main.updateGuard(guard, attemptedLocation);
            setFieldAt(guard.getLocation(), " ");
            guard.patrol();
            setFieldAt(guard.getLocation(), "G");
        } else if (field.equals("P")) {
            Main.updateGuard(guard, attemptedLocation);
            setFieldAt(guard.getLocation(), " ");
            guard.patrol();
            setFieldAt(guard.getLocation(), "G");
            triggerGameOver();
        } else if (field.equals("#")) {
            guard.turnAround();
            attemptedLocation = guard.tryPatrolling();
            field = getFieldAt(attemptedLocation);
            if (field.equals(" ")) {
                Main.updateGuard(guard, attemptedLocation);
                setFieldAt(guard.getLocation(), " ");
                guard.patrol();
                setFieldAt(guard.getLocation(), "G");
            } else if (field.equals("P")) {
                Main.updateGuard(guard, attemptedLocation);
                setFieldAt(guard.getLocation(), " ");
                guard.patrol();
                setFieldAt(guard.getLocation(), "G");
                triggerGameOver();
            }
        }
    }

    private void triggerGameOver() {
        playerLocation = new Location(0, 1);
        playerHasEmerald = false;
        renderMap();
        Main.triggerGameOver();
    }
}
