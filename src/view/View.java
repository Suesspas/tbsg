package view;

import javafx.util.Pair;
import model.Game;
import model.Player;
import model.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class View {

    private JFrame frame;
    private JPanel gamePanel;
    private JButton undo;
    private boolean buttonsDisabled = false;

    private static Game model;
    private static Controller con;
    private final int FIELDSIZE = 7;


    @SuppressWarnings("unused")
    public static void main(String[] args) {
        View newView = new View();
    }

    /**
     * Calls the Constructor of the Controller class and builds a new Frame.
     */
    public View() {
        View.con = new Controller(this);
        model = con.getModel();

        frame = new JFrame("rtsg");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(640, 480)); //TODO view ist in breite gezogen

        gamePanel = getFieldView(model);
        gamePanel.setPreferredSize(new Dimension(200, 200));
        frame.add(gamePanel);
        generateControlBar();
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //Fullscreen
        //frame.setUndecorated(true); // Menu bar ausblenden
        frame.setVisible(true);
    }

    /*private JPanel getGameView(Game model) {
        FieldPartView panel = new FieldPartView();
        //TODO paint
        return panel;
    }*/

    private JPanel getFieldView(Game model) {
        JPanel field = new JPanel();
        final JPanel container = new JPanel(new FlowLayout());
        container.add(field);
        container.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizePreview(field, container);
            }
        });
        LayoutManager grid = new GridLayout(FIELDSIZE, FIELDSIZE);
        field.setLayout(grid);
        int humanFighters = model.getP1().getCurrentGroupSize();
        int botFighters = model.getP2().getCurrentGroupSize();
        LinkedList<Pair<Integer, Integer>> hPos = con.tileDistribution(humanFighters, PlayerType.Human);
        LinkedList<Pair<Integer, Integer>> bPos = con.tileDistribution(botFighters, PlayerType.Bot);
        for (int i = 0; i < FIELDSIZE; ++i) {
            for (int j = 0; j < FIELDSIZE; ++j) {
                int row = i;
                int col = j;
                PlayerType playerType = PlayerType.Nobody;
                Pair<Integer, Integer> coords = new Pair<>(row, col);
                if (hPos.contains(coords)) {
                    playerType = PlayerType.Human;
                } else if (bPos.contains(coords)) {
                    playerType = PlayerType.Bot;
                }
                FieldPartView fpv = new FieldPartView(playerType);
                if (playerType == PlayerType.Human || playerType == PlayerType.Bot) {
                    JButton button = new JButton();
                    button.addActionListener(e -> con.cmdMove(row, col));
                    button.setOpaque(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(false);
                    if (buttonsDisabled) {
                        button.setEnabled(false);
                    }
                    fpv.add(button);
                } else {
                    fpv.setForeground(Color.LIGHT_GRAY);
                }
                field.add(fpv);
            }
        }
        return container;
    }

    private static void resizePreview(JPanel innerPanel, JPanel container) {
        int w = container.getWidth();
        int h = container.getHeight();
        int size =  Math.min(w, h);
        innerPanel.setPreferredSize(new Dimension(size, size));
        container.revalidate();
    }


    private void generateControlBar() {
        JPanel controlBar = new JPanel();
        controlBar.add(getNewGameButton());
        controlBar.add(getQuitButton());
        frame.add("South", controlBar);
    }


    private JButton getQuitButton() {
        JButton quit = new JButton();
        quit.addActionListener(e -> con.cmdQuit());
        quit.setText("Quit");
        quit.setMnemonic(KeyEvent.VK_Q);
        quit.setToolTipText("Quits the Game");
        return quit;
    }


    private JButton getNewGameButton() {
        JButton newGame = new JButton();
        newGame.addActionListener(e -> con.cmdNew());
        newGame.setText("New");
        newGame.setMnemonic(KeyEvent.VK_N);
        newGame.setToolTipText("Starts a new Game");
        return newGame;
    }

    private synchronized void update(Game model) {
        View.model = model;
        JPanel newGamePanel = getFieldView(model);
        frame.add(newGamePanel);
        frame.remove(gamePanel);
        gamePanel = newGamePanel;

        frame.revalidate();
    }

    private void printErrorBox(String message) {
        JOptionPane.showMessageDialog(
                frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void printTextBox(String title, String message) {
        JOptionPane.showMessageDialog(
                frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void disableButtons() {
        buttonsDisabled = true;
        update(model);
    }

    private void enableButtons() {
        buttonsDisabled = false;
        update(model);
    }

    private final class Controller {
        private View view;
        private Game model;

        /**
         * The maximum skill level of the machine.
         */
        private static final int MAX_LEVEL = 5;

        /**
         * The default skill level of the machine.
         */
        private static final int DEFAULT_LEVEL = 3;

        private int level;
        private MachineThread machine;

        private Controller(View view) {
            this.view = view;
            model = new Game();
            level = DEFAULT_LEVEL;
        }

        private void cmdQuit() {
            view.frame.setVisible(false);
            System.exit(0);
        }

        private void cmdNew() {
            if (machine != null && machine.isAlive()) {
                machine.interrupt();
            }
            model = new Game();
            view.update(model);
        }

        private void cmdMove(int atk, int def) {
            if (model.getCurrent() == PlayerType.Human) {
                model.humanAttack(atk, def);
                /*if (modelBuffer == null) {
                    printErrorBox("Invalid move!");
                } else {*/
                    view.update(model);
                    //nextTurn(Player.HUMAN);
                //}
            } else {
               view.printErrorBox("It´s not your turn!");
            }
        }

        /*private void nextTurn(Player p) {
            if (!model.gameOver()) {
                if (p == model.next()) {
                    if (p == Player.HUMAN) {
                        boardView.enableButtons();
                        boardView.update(model);
                        boardView.printTextBox("Miss a turn", "The bot has to "
                                + "miss a turn");
                    } else {
                        boardView.printTextBox("Miss a turn", "Human has to "
                                + "miss a turn");
                        boardView.disableButtons();
                        machineMove();
                    }
                } else {
                    if (p == Player.HUMAN) {
                        boardView.disableButtons();
                        machineMove();
                    }
                }
            } else {
                won();
            }
        }*/

        private void machineMove() {
            machine = new MachineThread(model);
            machine.start();
        }


        private Game getModel() {
            return model;
        }

        //TODO switch case kann ned bleim, for anstatt
        private LinkedList<Pair<Integer, Integer>> tileDistribution(int fighters, PlayerType playerType) {
            int i = playerType == PlayerType.Human ? 1 : 5;
            LinkedList<Pair<Integer, Integer>> positions = new LinkedList<>();
            switch (fighters) {
                case 1:
                    positions.add(new Pair<>(3, i));
                    break;
                case 2:
                    positions.add(new Pair<>(2, i));
                    positions.add(new Pair<>(4, i));
                    break;
                case 3:
                    positions.add(new Pair<>(1, i));
                    positions.add(new Pair<>(3, i));
                    positions.add(new Pair<>(5, i));
                    break;

            }
            return positions;
        }

        private final class MachineThread extends Thread {
            private Game threadModel;

            private MachineThread(Game model) {
                super();
                this.threadModel = model;
            }

            @Override
            public void run() {
                Game newBoard = threadModel.machineMove();
                if (isInterrupted()) {
                    return;
                }
                model = newBoard;
                view.update(model);
                view.enableButtons();
            }
        }
    }
}
