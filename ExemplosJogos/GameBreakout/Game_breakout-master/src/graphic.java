

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import static java.awt.Font.BOLD;

public class graphic extends JPanel implements KeyListener, ActionListener {


    private int score = 0;
    private Timer timer;
    boolean play = false;
    boolean game_over = false;
    
    // dimensions of frame
    
    private int height = 640;
    private int width = 800;

    //position of slide
    
    private int pos_slid_X = 400;
    final private int pos_slid_Y = 590;


    //position of ball
    
    private int pos_ball_X = 100;
    private int pos_ball_Y = 250;

    //direction of ball
    
    private int ball_dirX = -2;
    private int ball_dirY = -3;

    //brick dimensions and stuff
    
    private int brick_width = 25;
    private int brick_height= 15;
    private int bricks [][];
    private int columns = 20;
    private int rows = 8;
    private int nofbricks = columns *rows;

    public graphic(){                                                        // construtor
        bricks = new int[columns][rows];
        for (int i =0;i<columns;i++){
            Arrays.fill(bricks[i],1);
        }
        addKeyListener(this);                                               
        setFocusable(true);                                               
        timer = new Timer(15, this);
    }

    public void paint(Graphics g){
        moveball();                                                         // metodo responsavel por movimentar a bola

        g.setColor(new Color(200, 102, 96));
        g.fillRect(5,5, width-10,height-10);                  

        drawbricks(g);                                                      // função que cria os tijolos

        g.setColor(Color.yellow);
        g.fillOval(pos_ball_X,pos_ball_Y,20,20);                    // bola

        g.setColor(new Color(93, 57, 200));
        g.fillRect(pos_slid_X,pos_slid_Y,50,10);                    // deslizador
        score(g);                                                          // metodo que mostra os pontos
        lost(game_over,g);                                                 // verifica se a bola está fora do retangulo(deslizador)
        if (nofbricks ==0) {                                               // Se não houver tijolos voce ganha
            win(g);
        }

        Toolkit.getDefaultToolkit().sync();                                // sem este método haverá atrasos no jogo
        g.dispose();
    }

    private void score(Graphics g) {
        /*Atualizações de pontos*/
        g.setColor(Color.black);
        g.setFont(new Font(Font.SERIF, BOLD,20));
        g.drawString("Pontos: " + score, width-200,height-20);
    }

    private void win(Graphics g) {
        /*Se não houver tijolos, este método é chamado e você pode recomeçar*/
        timer.stop();
        play = false;
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.MONOSPACED,Font.BOLD,18));
        g.drawString("Voce Ganhou", width/2,height/2);
        for (int i =0;i<columns;i++){
            Arrays.fill(bricks[i],1);
        }
        nofbricks = rows * columns;
        pos_ball_X = 100;
        pos_ball_Y = 200;
        ball_dirX = -3;
        ball_dirY = -3;
    }

    private void drawbricks(Graphics g) {
        /*Fará os tijolos*/
        for (int i =0; i< columns;i++){
            for (int j =0; j< rows;j++){
                if (bricks[i][j]>0) {
                    g.setColor(Color.black);
                    g.fillRect(100 + i * brick_width + i * 5, 40 + j * brick_height + j * 2, brick_width, brick_height);
                    g.setColor(Color.white);
                    g.drawRect(100 + i * brick_width + i * 5, 40 + j * brick_height + j * 2, brick_width, brick_height);
                }
            }
        }
    }

    private void lost(boolean game_over, Graphics g) {
        /*Metodo para o game over*/
        if (game_over)
        {
            g.setColor(Color.BLACK);
            g.setFont(new Font(Font.MONOSPACED,BOLD,18));
            g.drawString("GAME OVER", width/2,height/2);
            score = 0;
        }
        else this.game_over = false;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        /*Depois que der play, pressione qualquer botão para começar*/

        if (!play) timer.start();

        game_over = false;

        /*Aqui começa o código responsável pelo movimento do "deslizador" */

        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if (pos_slid_X>=this.width-50) {
                pos_slid_X = this.width-50;
            }
            else {
            pos_slid_X += 20;
            }
        }

        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if (pos_slid_X < 5) {
                pos_slid_X = 5;
            }
            else {
                pos_slid_X -= 20;
            }
        }

        /*Se voce precionar  P o jogo pausa*/

        if (keyEvent.getKeyCode() == KeyEvent.VK_P){
            if (play) {
                play = false;
                timer.stop();
            }
            else if (!play){
                play=true;
                timer.start();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
    /*Se voce clicar em qualquer botão, o jogo retoma*/
        play = true;
        if (play) {
            repaint();
        }
    }

    private void moveball(){
    /* Este método é responsável pela mudança da direcção da bola.Mudar a direção*/

        Rectangle ball_rect = new Rectangle(pos_ball_X,pos_ball_Y,20,20);
        Rectangle slider = new Rectangle(pos_slid_X,pos_slid_Y,50,10);

        if (pos_ball_X <6) ball_dirX = -ball_dirX;


        if (pos_ball_X > width-22) ball_dirX = -ball_dirX;

        if (pos_ball_Y > height-22) {
            play = false;
            game_over = true;
            pos_ball_X = 100;
            pos_ball_Y = 250;
            for (int i =0;i<columns;i++){
                Arrays.fill(bricks[i],1);
            }
            timer.stop();
        }


        if (pos_ball_Y < 6) ball_dirY = -ball_dirY;

        if  (ball_rect.intersects(slider)) ball_dirY = -ball_dirY;


        A:for (int i =0; i< columns;i++){
            for (int j =0; j< rows;j++){
                if (bricks[i][j]>0) {
                    int brick_X_pos = 100 + i * brick_width + i * 5;
                    int brick_Y_pos = 40 + j * brick_height + j * 2;
                    Rectangle brick_rect = new Rectangle(brick_X_pos, brick_Y_pos, brick_width, brick_height);
                    if (ball_rect.intersects(brick_rect)) {
                        bricks[i][j] = 0;
                        if (pos_ball_X+19 <= brick_X_pos || pos_ball_X+1>=brick_X_pos + brick_width ){

                            ball_dirX = -ball_dirX;
                        }
                        else {
                            ball_dirY = -ball_dirY;
                        }
                        score +=2;
                        nofbricks--;
                        break A;
                    }

                }
            }
        }


        this.pos_ball_X += ball_dirX;
        this.pos_ball_Y += ball_dirY;
    }
}
