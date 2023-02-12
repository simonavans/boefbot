import javax.swing.*;
import java.awt.*;

public class Main {
    private static MapManager mapManager = new MapManager();
    private static JPanel[][] mapTiles = new JPanel[10][10];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel mapPanel = createMap();
            JPanel controlsPanel = createControls();
            JPanel panel = createPanel(mapPanel, controlsPanel);
            JFrame window = createWindow(panel);
            window.setVisible(true);
        });
    }

    private static JPanel getCurrentPlayerTile() {
        // if !isPlayerTile, then will look for emerald tile
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel tile = mapTiles[9 - j][i];
                if (tile.getBackground().equals(Color.GREEN)) {
                    return tile;
                }
            }
        }
        return mapTiles[0][0];
    }

    public static void resetGame() {
        mapTiles[9 - 1][4].setBackground(Color.RED);
    }

    private static JFrame createWindow(JPanel panel) {
        JFrame window = new JFrame("BoefBot");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800, 1000);
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
                        tile.setBackground(Color.GREEN);
                        break;
                    case "E":
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
//        controlsPanel.setLayout(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
        controlsPanel.setLayout(new GridLayout(3, 3));
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));

        Button upButton = new Button("Up");
        Button downButton = new Button("Down");
        Button leftButton = new Button("Left");
        Button rightButton = new Button("Right");

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

        upButton.addActionListener(e -> onClickControl("up"));
        downButton.addActionListener(e -> onClickControl("down"));
        leftButton.addActionListener(e -> onClickControl("left"));
        rightButton.addActionListener(e -> onClickControl("right"));

        controlsPanel.add(new JPanel());
        controlsPanel.add(upButton);
        controlsPanel.add(new JPanel());
        controlsPanel.add(leftButton);
        controlsPanel.add(new JPanel());
        controlsPanel.add(rightButton);
        controlsPanel.add(new JPanel());
        controlsPanel.add(downButton);
        controlsPanel.add(new JPanel());

//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 2;
//        controlsPanel.add(upButton, gbc);
//
//        gbc.gridwidth = 1;
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        controlsPanel.add(leftButton, gbc);
//
//        gbc.gridx = 1;
//        gbc.gridy = 1;
//        controlsPanel.add(rightButton, gbc);
//
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.gridwidth = 2;
//        controlsPanel.add(downButton, gbc);

        return controlsPanel;
    }

    private static void onClickControl(String controlDirection) {
        getCurrentPlayerTile().setBackground(Color.WHITE);
        Location newPlayerLocation = mapManager.tryMoveTo(controlDirection);
        JPanel playerTile = mapTiles[9 - newPlayerLocation.getY()][newPlayerLocation.getX()];
        playerTile.setBackground(Color.GREEN);
        mapManager.renderMap();
    }
}
