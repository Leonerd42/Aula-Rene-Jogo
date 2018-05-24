import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

class JogoBase extends JFrame {
  Image img[] = new Image[8];
  Desenho des = new Desenho();
  final int FUNDO = 0; 
  final int PARADO = 1; 
  final int ANDA0 = 2; 
  final int ANDA1 = 3; 
  final int SOCO0 = 4; 
  final int SOCO1 = 5; 
  final int SOCO2 = 6; 
  final int ARBUSTO = 7; 
  int posX = 0; 
  boolean emExecucao = true; 
  int estado = PARADO; 

  class Desenho extends JPanel {

    Desenho() {
      try {
        img[FUNDO] = ImageIO.read(new File("fundo.jpeg"));
        img[PARADO] = ImageIO.read(new File("parado.gif"));
        img[ANDA0] = ImageIO.read(new File("anda0.gif"));
        img[ANDA1] = ImageIO.read(new File("anda1.gif"));
        img[SOCO0] = ImageIO.read(new File("soco0.gif"));
        img[SOCO1] = ImageIO.read(new File("soco1.gif"));
        img[SOCO2] = ImageIO.read(new File("soco2.gif"));
        img[ARBUSTO] = ImageIO.read(new File("arbusto.png"));
      } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "A imagem não pode ser carregada!\n" + e, "Erro", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(img[FUNDO], 0, 0, getSize().width, getSize().height, this);
      g.drawImage(img[estado], posX , getSize().height - img[estado].getHeight(this) - 20, this);
      g.drawImage(img[PARADO], 800 , getSize().height - img[PARADO].getHeight(this) - 20, this);
      g.drawImage(img[ARBUSTO], 400 , getSize().height - img[ARBUSTO].getHeight(this) - 20, this);
    }

    public Dimension getPreferredSize() {
      return new Dimension(1000, 600);
    }
  }

  JogoBase() {
    super("Trabalho");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(des);
    Thread t = new Thread(){
      public void run(){
        while(emExecucao){
          try{
            sleep(67); 
          } catch (InterruptedException e) {}
          switch(estado){
            case ANDA0:
              posX += 10; 
              estado = ANDA1; 
              break; 
            case ANDA1: 
              posX += 10; 
              estado = ANDA0; 
              break; 
            case SOCO0:
              estado = SOCO1; 
              break; 
            case SOCO1: 
              estado = SOCO2;
              break; 
            case SOCO2: 
              estado = PARADO; 
          }
          repaint();  
        }
      }
    };
    t.start(); 
    addKeyListener(new KeyAdapter (){
     public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
          case KeyEvent.VK_RIGHT: estado = ANDA0; 
                                  break; 
          case KeyEvent.VK_P:  estado = PARADO;  
                                  break; 
          case KeyEvent.VK_S:  estado = SOCO0; 
                                break; 
        }        
      }
    });
    pack();
    setVisible(true);
  }

  static public void main(String[] args) {
    JogoBase f = new JogoBase();
  }
}
