import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class App {
   
    public static void main(String[] args) throws Exception {
        
        int boardWidth = 440;
        int boardHeight = 628;

        JFrame fframe = new JFrame("Flappy Bird");
        fframe.setSize(boardWidth, boardHeight);
       
        fframe.setLocationRelativeTo(null);
        fframe.setResizable(false);
        fframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon1 = new ImageIcon("flappySized.png");

        String prompt1 = "Welcome to Flappy Bird!\nClick 'ok ' to continue into the game!\nOnce the game opens, click the spacebar to start.\nGood luck and have fun!";
        JOptionPane.showMessageDialog(null,prompt1,
        "Introduction  A.Freeman and C.Chuka",0,icon1);
        
        final FB flappy = new FB();
        fframe.add(flappy);
        //pack is used for the title bar isnt included in our set dimensions
        fframe.pack();
        flappy.requestFocus();
        fframe.setVisible(true);   
    }
}
