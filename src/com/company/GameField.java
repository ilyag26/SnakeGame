package com.company;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.KeyAdapter;
        import java.awt.event.KeyEvent;
        import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE=320;
    private final int DOT_SIZE=16;
    private final int ALL_DOTS=400;
    private Image dot;
    private Image dotxx;
    private Image pineapple;
    private int pineappleX;
    private int pineappleY;
    private int xxxX;
    private int xxxY;
    private int xxxX2;
    private int xxxY2;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean inGame=true;

    public GameField(){
        setBackground(Color.black);
        loadImages();
        inGame();
        addKeyListener(new FieldKey());
        setFocusable(true);
        createDotxx();
        createDotxx2();
    }
    public void loadImages(){
      ImageIcon iipa = new ImageIcon("pineapple.png");
      pineapple = iipa.getImage();
      ImageIcon iid = new ImageIcon("dot.jpg");
      dot = iid.getImage();
        ImageIcon iix = new ImageIcon("dotxxx.gif");
        dotxx = iix.getImage();
    }
    public void inGame(){
        dots = 2;
        for (int i = 0; i < dots; i++) {
           x[i] = 48 - i*DOT_SIZE;
           y[i] = 48;
        }
        timer = new Timer(100,this);
        timer.start();
        createPineapple();
    }

    private void createPineapple() {
        pineappleX = new Random().nextInt(20)*DOT_SIZE;
        pineappleY = new Random().nextInt(20)*DOT_SIZE;
    }
    private void createDotxx() {
        xxxX = new Random().nextInt(20)*DOT_SIZE;
        xxxY = new Random().nextInt(20)*DOT_SIZE;
    }
    private void createDotxx2() {
        xxxX2 = new Random().nextInt(20)*DOT_SIZE;
        xxxY2 = new Random().nextInt(20)*DOT_SIZE;
    }
    private void checkPineapple() {
        if(x[0] == pineappleX && y[0] == pineappleY){
            dots++;
            createPineapple();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(pineapple,pineappleX,pineappleY,this);
            g.drawImage(dotxx,xxxX,xxxY,this);
            g.drawImage(dotxx,xxxX2,xxxY2,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        }else{
            String str = "Игра окончена, лох!";
            g.setColor(Color.RED);
            g.drawString(str,120,130);

        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0]-=DOT_SIZE;

        }
        if(right){
            x[0]+=DOT_SIZE;

        }
        if(up){
            y[0]-=DOT_SIZE;

        }
        if(down){
            y[0]+=DOT_SIZE;

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            move();
            checkPineapple();
            checkCollisions();
        }
        repaint();
    }

    private void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if(x[0] == xxxX && y[0] == xxxY){
            inGame = false;
        }
        if(x[0] == xxxX2 && y[0] == xxxY2){
            inGame = false;
        }
        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }
class FieldKey extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_LEFT && !right){
        left = true;
        up = false;
        down = false;
        }
        if(key == KeyEvent.VK_RIGHT && !left){
            right = true;
            up = false;
            down = false;
        }
        if(key == KeyEvent.VK_UP && !down){
            up = true;
            right = false;
            left = false;
        }
        if(key == KeyEvent.VK_DOWN && !up){
            down = true;
            right = false;
            left = false;
        }
    }
}
}
