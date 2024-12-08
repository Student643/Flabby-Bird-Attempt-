import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.*;



//FB is inheriting all the functionalities of JPanel
public class FB extends JPanel implements ActionListener,KeyListener{
    
    //array for choices later
    String[] choices = {"yes", "no"};
    String user_game_choice = " ";
    
    
    int boardWidth = 400;
    int boardHeight = 628;
    int topOfBoard = 0;
    double score;

    //Images
    Image background;
    Image topPipe;
    Image bottomPipe;
    Image bird;

    //icons
    ImageIcon flappy = new ImageIcon("flappySized.png");

    //Bird
    int birdX = boardWidth/8;
    int birdY = boardHeight/2;
    int birdWidth = 34;
    int birdHeight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image birdi;

        Bird(Image birdi){
            this.birdi = birdi;
        }

    }
     //Pipes
     int pipeX = boardWidth;
     int pipeY = 0;
     int pipeWidth = 88; //scaled by 1/6
     int pipeHeight = 540;

     class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;
        double score = 0;

        Pipe(Image img){
            this.img = img;
        }
        class gameTimer{
            int x = 0;
            int y = 0;
            int timerWidth = 100;
            int timerHeight = 100;
            Image timer ;

        }
    }
    //game logic
    Bird birdi;
    //moves pipes to the left
    int velocityX = -4;
    //so the bird moves upward
    int velocityY = 0;
    //now introducing a factor for gravity
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    //game loop timer
    Timer gameLoop;
    Timer pipeTimer;
    Timer gameStart;
    Timer music;


    boolean gameOver = false ;

    FB() {
        
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        //load images
        background = new ImageIcon(getClass().getResource("background1.png")).getImage();
        topPipe = new ImageIcon(getClass().getResource("topPipe4.png")).getImage();
        bottomPipe = new ImageIcon(getClass().getResource("bottomPipe4.png")).getImage();
        bird = new ImageIcon(getClass().getResource("flappy.png")).getImage();

        //bird
        birdi = new Bird(bird); 
        pipes = new ArrayList<Pipe>();

        
        
        //game and pipe timer
        pipeTimer = new Timer(1500,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){

                placePipes();
            }
        });
        gameStart = new Timer(5000,new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){

                placePipes();
            }
        });
        
        //game and pipes timer
       // pipeTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this);
        
        //scoreboard
        
        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setText("Score: " + score);
        scoreLabel.setBounds(10, 10, 100, 50);
    }
    

    public void placePipes(){

    int randomPipeY = (int)(pipeY - pipeHeight/4- Math.random()*pipeHeight/2);
    int openingSpace = boardHeight/4;

    Pipe topPipe1= new Pipe(topPipe);
    topPipe1.y = randomPipeY;
    pipes.add(topPipe1);

    Pipe bottomPipe1 = new Pipe(bottomPipe);
    bottomPipe1.y = topPipe1.y + pipeHeight + openingSpace;
    pipes.add(bottomPipe1);

    }
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        draw(g);
    }
    public void draw(Graphics g) {
        //background
        g.drawImage(background, 0, 0, boardWidth, boardHeight, null);

        //bird
        g.drawImage(birdi.birdi, birdi.x,birdi.y,birdi.width,birdi.height,null );
        g.setColor(Color.white);
        g.setFont(new Font ("Ariel", Font.PLAIN,35));
        if (gameOver){
            g.drawString("GameOver : "+String.valueOf((int)score), 10, 35);

        }
        else{
            g.drawString(String.valueOf((int)score),10,35);

        }

        //pipes
        for(int i =0; i < pipes.size();i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x,pipe.y,pipe.width,pipe.height, null);
        }
    }

    public void move(){
        
        velocityY += gravity;
        birdi.y += velocityY;
        birdi.y = Math.max(birdi.y,0);
        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x +=velocityX;

            if (!pipe.passed && birdi.x > pipe.x + pipe.width){
                pipe.passed = true;
                score +=0.5;
            }

            if (collision(birdi,pipe)){
                gameOver = true;
                
            }
        }

        if (birdi.y > boardHeight){

            gameOver = true;
        }
        if (birdi.y == topOfBoard ){

            gameOver = true ;
        }      
    }
    public boolean collision (Bird a, Pipe b){
        //these next few lines of code is checking for a coordinate overlap of the
        // bird and pipes. 
         return a.x<b.x + b.width && 
            a.x + a.width > b.x &&
            a.y < b.y + b.height &&
            a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
     
            pipeTimer.start();
            move();
            repaint();
            if (gameOver){

                pipeTimer.stop();
                gameLoop.stop();
                continueGame();
            
            }
     
    }
    public void continueGame(){

        int choice = JOptionPane.showOptionDialog(null,"Would you like to continue playing?", "Continue?            A.Freeman and C.Chuka",
        0,0,flappy,choices,choices[0]);
        switch(choice)
        {
            case 1 -> user_game_choice = "\"no\"";
            case 0 -> user_game_choice = "\"yes\"";
        }
        if (user_game_choice.equals("\"no\"")){

            gameEnding();
        }
        
    }
    public void gameEnding (){
        JOptionPane.showMessageDialog(null,"GAME OVER");
        String prompt5 = "Thanks for Playing! This program is now ending!";
        JOptionPane.showMessageDialog(null,prompt5,"Termination Screen     "          
                + "         A.Freeman and C.Chuka",0,flappy);
      
        System.exit(0);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        
        gameLoop.start();
        pipeTimer.start();

        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            
            velocityY = -8;
        }
    
        
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

}