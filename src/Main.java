import javax.swing.*;
import java.awt.*;

public class Main {
    private static MapManager mapManager = new MapManager();
    private static JPanel[][] mapTiles = new JPanel[10][10];
    private static JLabel statusLabel = new JLabel("", SwingConstants.CENTER);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel mapPanel = createMap();
            JPanel controlsPanel = createControls();
            JPanel panel = createPanel(mapPanel, controlsPanel);
            JFrame window = createWindow(panel);
            window.setVisible(true);
        });
    }

    private static JPanel getCurrentEntityTile() {
        // if !isPlayerTile, then will look for guard tile
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel tile = mapTiles[9 - j][i];
                if (tile.getBackground().equals(Color.BLUE)) {
                    return tile;
                }
            }
        }
        return mapTiles[0][0];
    }

    public static void triggerGameOver() {
        mapTiles[8][4].setBackground(Color.CYAN);
        mapTiles[8][0].setBackground(Color.BLUE);
        //todo fix
        statusLabel.setText("Game over!");
    }

    public static void updateGuard(Guard guard, Location newLocation) {
        JPanel oldGuardTile = mapTiles[9 - guard.getLocation().getY()][guard.getLocation().getX()];
        oldGuardTile.setBackground(Color.WHITE);
        JPanel newGuardTile = mapTiles[9 - newLocation.getY()][newLocation.getX()];
        newGuardTile.setBackground(Color.RED);
    }

    private static JFrame createWindow(JPanel panel) {
        JFrame window = new JFrame("BoefBot");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(700, 900);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.add(panel, BorderLayout.CENTER);
        return window;
    }

    private static JPanel createPanel(JPanel mapPanel, JPanel controlsPanel) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(mapPanel);
        mainPanel.add(controlsPanel, BorderLayout.SOUTH);
        return mainPanel;
    }

    private static JPanel createMap() {
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new GridLayout(10, 10));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String field = mapManager.getFieldAt(j, 9 - i);
                JPanel tile = new JPanel();

                switch(field) {
                    case "#":
                        tile.setBackground(Color.DARK_GRAY);
                        break;
                    case "P":
                        tile.setBackground(Color.BLUE);
                        break;
                    case "E":
                        tile.setBackground(Color.CYAN);
                        break;
                    case "G":
                        tile.setBackground(Color.RED);
                        break;
                    default:
                        tile.setBackground(Color.WHITE);
                }

                tile.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                mapPanel.add(tile);
                mapTiles[i][j] = tile;
            }
        }
        return mapPanel;
    }

    private static JPanel createControls() {
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(3, 3));
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));

        Button upButton = new Button("Up");
        Button downButton = new Button("Down");
        Button leftButton = new Button("Left");
        Button rightButton = new Button("Right");
        Button waitButton = new Button("Wait");

        upButton.setPreferredSize(new Dimension(75, 75));
        upButton.setBackground(Color.BLUE);
        upButton.setForeground(Color.WHITE);
        downButton.setPreferredSize(new Dimension(75, 75));
        downButton.setBackground(Color.BLUE);
        downButton.setForeground(Color.WHITE);
        leftButton.setPreferredSize(new Dimension(75, 75));
        leftButton.setBackground(Color.BLUE);
        leftButton.setForeground(Color.WHITE);
        rightButton.setPreferredSize(new Dimension(75, 75));
        rightButton.setBackground(Color.BLUE);
        rightButton.setForeground(Color.WHITE);
        waitButton.setPreferredSize(new Dimension(75, 75));
        waitButton.setBackground(Color.BLUE);
        waitButton.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Sans-serif", Font.PLAIN, 18));

        upButton.addActionListener(e -> onClickControl("up"));
        downButton.addActionListener(e -> onClickControl("down"));
        leftButton.addActionListener(e -> onClickControl("left"));
        rightButton.addActionListener(e -> onClickControl("right"));
        waitButton.addActionListener(e -> mapManager.renderMap());

        controlsPanel.add(new JPanel());
        controlsPanel.add(upButton);
        controlsPanel.add(statusLabel);
        controlsPanel.add(leftButton);
        controlsPanel.add(waitButton);
        controlsPanel.add(rightButton);
        controlsPanel.add(new JPanel());
        controlsPanel.add(downButton);
        controlsPanel.add(new JPanel());

        return controlsPanel;
    }

    private static void onClickControl(String controlDirection) {
        getCurrentEntityTile().setBackground(Color.WHITE);
        Location newPlayerLocation = mapManager.tryMoveTo(controlDirection);
        JPanel tileToMoveTo = mapTiles[9 - newPlayerLocation.getY()][newPlayerLocation.getX()];
        tileToMoveTo.setBackground(Color.BLUE);
        mapManager.renderMap();
        statusLabel.setText("");
    }
}
