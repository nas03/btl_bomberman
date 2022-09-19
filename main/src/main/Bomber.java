package main;

import main.Bomb;

public class Bomber {
  int x;
  int y;
  //Motion
  void moveUp() {
    y -= 1;
  }
  void moveDown() {
    y += 1;
  }
  void moveRight() {
    x += 1;
  }
  void moveLeft() {
    x -= 1;
  }
  void setBomb() {
    Bomb bomb = new Bomb (x, y);
  }
}
