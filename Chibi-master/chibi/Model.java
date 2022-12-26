package chibi;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Model extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Arial", Font.BOLD, 14);
    private boolean inGame = false;
    private boolean dying = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int MAX_GHOSTS = 12;
    private final int PACMAN_SPEED = 6;

    private int N_GHOSTS = 6;
    private int lives, score;
    private int[] dx, dy;
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    private Image heart, ghost_red;
    private Image up, down, left, right, menu, decor;

    private int chibi_x, chibi_y, chibid_x, chibid_y;
    private int req_dx, req_dy;

    //map
    private final short levelData[] = {
    	19, 26, 26, 18, 26, 26, 22, 15, 19, 26, 26, 18, 26, 26, 22,
        21,  3,  6, 21,  3,  6, 17, 18, 20,  3,  6, 21,  3,  6, 21,
        21,  9, 12, 21,  9, 12, 17, 16, 20,  9, 12, 21,  9, 12, 21,
        25, 18, 18, 16, 26, 18, 16, 24, 16, 18, 26, 16, 18, 26, 20,
         7, 17, 16, 20,  2, 17, 28,  7, 25, 20,  7, 17, 20,  7, 21,
         5, 17, 16, 28,  5, 21, 11,  0,  14, 21,  5, 25, 20,  5, 21,
        13, 17, 20, 11, 12, 17, 22, 13, 19, 20,  9, 14, 21, 13, 21,
        19, 16, 16, 26, 26, 16, 24, 26, 24, 16, 26, 26, 16, 18, 20,
        25, 16, 20, 11,  6, 21, 19, 18, 22, 21,  3, 14, 17, 24, 28,
        14, 17, 16, 22,  4, 21, 17,  0, 20, 21,  5, 19, 20, 11, 14,
        19, 16, 16, 20, 13, 21, 17, 24, 20, 21, 13, 17, 16, 18, 22,
        17, 16, 16, 24, 26, 16, 16, 24, 16, 16, 26, 24, 16, 16, 20,
        17, 16, 20, 11, 14, 17, 20, 15, 17, 20, 11, 14, 17, 16, 20,
        17, 16, 16, 18, 18, 16, 16, 18, 16, 16, 18, 18, 16, 16, 20,
        25, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };

    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 8;

    private int currentSpeed = 2;
    private short[] screenData;
    private Timer timer;

    public Model() {

        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }
    
    private void loadImages() {
    	down = new ImageIcon(".Chibi-master/images/down.png").getImage();
    	up = new ImageIcon(".Chibi-master/images/up.png").getImage();
    	left = new ImageIcon(".Chibi-master/images/left.png").getImage();
    	right = new ImageIcon(".Chibi-master/images/right.png").getImage();
        menu = new ImageIcon(".Chibi-master/images/menu.gif").getImage();
        ghost_red = new ImageIcon(".Chibi-master/images/ghost_red.gif").getImage();
        heart = new ImageIcon(".Chibi-master/images/heart.png").getImage();
        decor = new ImageIcon(".Chibi-master/images/decor.gif").getImage();
    }
    private void initVariables() {

        screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
        
        timer = new Timer(20, this);
        timer.start();
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {

            moveChibi();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }
    
    
    private void showIntroScreen(Graphics2D g2d) {
        Font fnt1 = new Font("arial",Font.BOLD, 15);
        Font fnt0 = new Font("GARAMOND",Font.BOLD, 40);
        g2d.setFont(fnt0);
        g2d.setColor(Color.WHITE);
        g2d.drawString("PACMAN GAME", (SCREEN_SIZE)/9, 100);
        g2d.drawImage(menu,(SCREEN_SIZE)/6, 200, this);
    	String start = "Press SPACE to start";
        g2d.setFont(fnt1);
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (SCREEN_SIZE)/4, 150);
        g2d.drawImage(decor,8, (SCREEN_SIZE)/3, this);
    }

    private void drawScore(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(5, 181, 79));
        String s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (int i = 0; i < lives; i++) {
            g.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void checkMaze() {

        int i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i]) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    private void death() {

    	lives--;

        if (lives == 0) {
            inGame = false;
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d) {

        int pos;
        int count;

        for (int i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            if (chibi_x > (ghost_x[i] - 12) && chibi_x < (ghost_x[i] + 12)
                    && chibi_y > (ghost_y[i] - 12) && chibi_y < (ghost_y[i] + 12)
                    && inGame) {

                dying = true;
                 score = score -10; //if pacman dies, the score will decrecres 10 points 
            }
        }
    }

    private void drawGhost(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(ghost_red, x, y, this);
        }

    private void moveChibi() {

        int pos;
        short ch;

        if (chibi_x % BLOCK_SIZE == 0 && chibi_y % BLOCK_SIZE == 0) {
            pos = chibi_x / BLOCK_SIZE + N_BLOCKS * (int) (chibi_y / BLOCK_SIZE);
            ch = screenData[pos];

            //increase score
            if ((ch & 16) != 0) { //score
                screenData[pos] = (short) (ch & 15);
                score++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    chibid_x = req_dx;
                    chibid_y = req_dy;
                }
            }

            // Check for standstill
            if ((chibid_x == -1 && chibid_y == 0 && (ch & 1) != 0)
                    || (chibid_x == 1 && chibid_y == 0 && (ch & 4) != 0)
                    || (chibid_x == 0 && chibid_y == -1 && (ch & 2) != 0)
                    || (chibid_x == 0 && chibid_y == 1 && (ch & 8) != 0)) {
                chibid_x = 0;
                chibid_y = 0;
            }
        } 
        chibi_x = chibi_x + PACMAN_SPEED * chibid_x;
        chibi_y = chibi_y + PACMAN_SPEED * chibid_y;
    }

    private void drawPacman(Graphics2D g2d) {

        if (req_dx == -1) {
        	g2d.drawImage(left, chibi_x + 1, chibi_y + 1, this);
        } else if (req_dx == 1) {
        	g2d.drawImage(right, chibi_x + 1, chibi_y + 1, this);
        } else if (req_dy == -1) {
        	g2d.drawImage(up, chibi_x + 1, chibi_y + 1, this);
        } else {
        	g2d.drawImage(down, chibi_x + 1, chibi_y + 1, this);
        }
    }

    private void drawMaze(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(new Color(19,53,191));
                g2d.setStroke(new BasicStroke(5));
                
                if ((levelData[i] == 0)) { 
                	g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                 }

                if ((screenData[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) { 
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) { 
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) { 
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x + 10, y + 10, 6, 6);
               }
                i++;
            }
        }
    }

    private void initGame() {

    	lives = 3;
        score = 0;
        initLevel();
        N_GHOSTS = 6;
        currentSpeed = 1;
    }

    private void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }

    private void continueLevel() {

    	int dx = 1;
        int random;

        for (int i = 0; i < N_GHOSTS; i++) {

            ghost_y[i] = 10 * BLOCK_SIZE; //start position
            ghost_x[i] = 6 * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }

        chibi_x = 7 * BLOCK_SIZE;  //start position
        chibi_y = 7 * BLOCK_SIZE;
        chibi_x = 0;	//reset direction move
        chibi_y = 0;
        req_dx = 0;		// reset direction controls
        req_dy = 0;
        dying = false;
    }

 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //create screen
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame) {
            playGame(g2d);
        }else {
            g2d.setColor(Color.black);
            g2d.fillRect(0, 0, d.width, d.height);
            drawMaze(g2d);
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    //controls
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } 
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    initGame();
                }
            }
        }
}

	
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
		
	}
