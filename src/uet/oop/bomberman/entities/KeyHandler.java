package uet.oop.bomberman.entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed,downPressed,rightPressed,leftPressed;
    KeyEvent e;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public char getKeyEvent() {

        if(upPressed) {
            return 'W';
        }
        else if(downPressed) {
            System.out.println("S is pressed") ;
            return 'S';
        }
        else if(rightPressed) {
            System.out.println("D is pressed");
            return 'D';
        }
        else  {
            System.out.println("A is pressed");
            return 'A';
        }


    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
    }
    public static void main(String[] args) {
        /*KeyEvent e = new KeyEvent();*/

    }
}
