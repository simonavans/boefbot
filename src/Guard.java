public class Guard {
    private Location location;
    private boolean isFacingRight;

    public Guard(int x, int y, boolean isFacingRight) {
        this.location = new Location(x, y);
        this.isFacingRight = isFacingRight;
    }

    public void turnAround() {
        isFacingRight = !isFacingRight;
    }

    public Location tryPatrolling() {
        if (isFacingRight) {
            return new Location(location.getX() + 1, location.getY());
        } else {
            return new Location(location.getX() - 1, location.getY());
        }
    }

    public void patrol() {
        if (isFacingRight) {
            location = new Location(location.getX() + 1, location.getY());
        } else {
            location = new Location(location.getX() - 1, location.getY());
        }
    }

    public Location getLocation() {
        return location;
    }
}
