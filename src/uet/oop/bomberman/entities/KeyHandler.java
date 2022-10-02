package uet.oop.bomberman.entities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed,downPressed,rightPressed,leftPressed;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void getKeyEvent(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            System.out.println("W is pressed") ;
        }
        else if(code == KeyEvent.VK_S) {
            System.out.println("S is pressed") ;
        }
        else if(code == KeyEvent.VK_D) {
            System.out.println("D is pressed");
        }
        else if(code == KeyEvent.VK_A) {
            System.out.println("A is pressed");
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
}
