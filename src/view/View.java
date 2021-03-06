package view;

import javafx.util.Pair;
import model.FieldTile;
import model.Fighter;
import model.Game;
import model.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.LinkedList;

public class View {

    private JFrame frame;
    private JPanel gamePanel;
    private JButton undo;
    private boolean buttonsDisabled = false;

    private static Game model;
    private static Controller con;
    private final int FIELDSIZE = 7;

    private HashMap<String, AnimatedFighter> animatedFighters = new HashMap<>();
    private Animation playerIdle;
    private Animation enemyIdle;


    @SuppressWarnings("unused")
    public static void main(String[] args) {
        View newView = new View();
    }

    /**
     * Calls the Constructor of the Controller class and builds a new Frame.
     */
    public View() {
        Assets.init();

        View.con = new Controller(this);
        model = con.getModel();

        frame = new JFrame("rtsg");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1280, 720));

        gamePanel = getFieldView(model);
        gamePanel.setPreferredSize(new Dimension(400, 400));
        frame.add(gamePanel);
        generateControlBar();
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); //Fullscreen
        //frame.setUndecorated(true); // Menu bar ausblenden
        frame.pack();
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
        for (int i = 0; i < FIELDSIZE; ++i) {
            for (int j = 0; j < FIELDSIZE; ++j) {
                int row = i;
                int col = j;
                PlayerType playerType = model.getPlayerFromPosition(i, j);
                //TODO fighter durch AnimatedFighter ersetzen
                Fighter fighter = model.getFighterFromPosition(i, j);
                con.determineSprite(fighter);
                FieldPartView fpv = new FieldPartView(fighter);
                JButton button = new JButton();
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                if (buttonsDisabled) {
                    button.setEnabled(false);
                }
                if (playerType == PlayerType.Human || playerType == PlayerType.Bot) {
                    button.addActionListener(e -> con.clickOnFighter(row, col));
                    /*fpv.addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            con.clickOnFighter(row, col);
                        }
                    });*/ // Alternative zu buttons
                } else {
                    button.addActionListener(e -> con.clickOnEmptyTile(row, col));
                    fpv.setForeground(Color.LIGHT_GRAY);
                }
                fpv.add(button);
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
        //BufferStrategy bs = this.get;
        View.model = model;
        JPanel newGamePanel = getFieldView(model);
        frame.add(newGamePanel);
        frame.remove(gamePanel);
        gamePanel = newGamePanel;
        frame.revalidate();
    }

    private synchronized void updateAnimations() {
        getFieldView(model);
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

        private Pair<Integer, Integer> selectedFighterPos;

        /**
         * The default skill level of the machine.
         */
        private static final int DEFAULT_LEVEL = 3;

        private int level;
        private MachineThread machine;
        private AnimationThread animationThread;

        private Controller(View view) {
            this.view = view;
            model = new Game();
            level = DEFAULT_LEVEL;
            selectedFighterPos = null;
            animationThread = new AnimationThread(view);
            animationThread.start();
        }

        private void cmdQuit() {
            view.frame.setVisible(false);
            animationThread.running = false;
            System.exit(0);
        }

        private void cmdNew() {
            if (machine != null && machine.isAlive()) {
                machine.interrupt();
            }
            model = new Game();
            view.update(model);
        }

        public void clickOnEmptyTile(int row, int col) {
                if (selectedFighterPos != null){
                    cmdMove(row, col);
                } else {
                    //evtl info über leere tile?
                }
                selectedFighterPos = null;
        }

        private void clickOnFighter(int row, int col) {
            if (model.getFighterFromPosition(row, col).getOwner() == PlayerType.Human){
                clickOnHumanFighter(row, col);
            } else if ((model.getFighterFromPosition(row, col).getOwner() == PlayerType.Bot)){
                clickOnBotFighter(row, col);
            }
        }

        private void clickOnHumanFighter(int row, int col) {
            //TODO stats anzeigen und range
            selectedFighterPos = new Pair<>(row, col);
            System.out.println("Fighter on position (" + row + ", " + col + ") has been selected.");
        }

        private void clickOnBotFighter(int row, int col) {
            if (selectedFighterPos != null){
                Fighter attacker = model.getFighterFromPosition(selectedFighterPos.getKey(), selectedFighterPos.getValue());
                Fighter defender = model.getFighterFromPosition(row, col);
                if (attacker == null || defender == null) {
                    throw new IllegalStateException("Oops this should not have happened");
                }
                cmdAttack(attacker, defender);
            } else {
                //TODO stats anzeigen
            }
            selectedFighterPos = null;
        }


        private void cmdAttack(Fighter attacker, Fighter defender) {
            if (model.getCurrent() == PlayerType.Human) {
                if (model.isValidAttack(attacker, defender)){
                    //Angriffs animation hier

                    model.humanAttack(attacker, defender);
                }

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

        private void cmdMove(int x, int y) {
            if (selectedFighterPos != null){
                Integer xPos = selectedFighterPos.getKey();
                Integer yPos = selectedFighterPos.getValue();
                Fighter fighter = model.getFighterFromPosition(xPos, yPos);
                Fighter destination = model.getFighterFromPosition(x, y);
                if (fighter == null) {
                    throw new IllegalStateException("Oops, this shouldn't have happened");
                }
                if (destination == null){
                    model.move(fighter, x, y);
                    System.out.println("Fighter on position (" + xPos + ", " + yPos + ") moved to (" + x + ", " + y + ")");
                    view.update(model);
                } else {
                    printErrorBox("Tile already occupied");
                }
            }
            selectedFighterPos = null;
        }

        private void determineSprite(Fighter fighter) {
            if (fighter != null) {
                AnimatedFighter animatedFighter;
                if (animatedFighters.containsKey(fighter.getUniqueID())){
                    animatedFighter = animatedFighters.get(fighter.getUniqueID());
                } else {
                    animatedFighter = new AnimatedFighter(fighter, playerIdle);
                    fighter.setCurrentSprite(animatedFighter.getCurrentSprite());
                    animatedFighters.put(fighter.getUniqueID(), animatedFighter);
                }

                if (fighter.getOwner() == PlayerType.Human) {
                    fighter.setCurrentSprite(playerIdle.getCurrentFrame());
                }
                else {
                    fighter.setCurrentSprite(enemyIdle.getCurrentFrame());
                }
            }
        }

        private void machineMove() {
            machine = new MachineThread(model);
            machine.start();
        }


        private Game getModel() {
            return model;
        }

        private final class AnimationThread extends Thread {
            private View view;
            final int timeTillNextFrame = 400;

            boolean running;

            private AnimationThread(View view) {
                super();
                this.view = view;
                view.playerIdle = new Animation(timeTillNextFrame, Assets.playerIdle);
                view.enemyIdle = new Animation(timeTillNextFrame, Assets.enemyIdle);
                running = true;
            }

            @Override
            public void run() {
                int fps = 60;
                double timePerTick = 1000000000 / fps;
                double delta = 0;
                long now;
                long lastTime = System.nanoTime();
                int ticks = 0;
                long timer = 0;

                while (running){
                    now = System.nanoTime();
                    delta += (now - lastTime) / timePerTick;
                    timer += now - lastTime;
                    lastTime = now;
                    if (delta >= 1){
                        //Animations-Updates hier
                        view.playerIdle.tick();
                        view.enemyIdle.tick();
                        view.updateAnimations();
                        ticks++;
                        delta--;
                    }
                    if (timer >= 1000000000){
                        System.out.println("Ticks and Frames: " + ticks);
                        ticks = 0;
                        timer = 0;
                    }
                }
            }
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
