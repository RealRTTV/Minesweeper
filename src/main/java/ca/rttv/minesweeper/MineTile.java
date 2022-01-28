package ca.rttv.minesweeper;

import static ca.rttv.minesweeper.Main.mineField;

public class MineTile extends Tile {
   public MineTile(int x, int y) {
      super(x, y);
   }
   
   @Override
   public void click() {
      if (!flagged) {
         Main.endGame(this.x, this.y);
      }
   }
   
   @Override
   public void init() {
      if (mineField.length - 1 >= x + 1 && mineField[x + 1][y] instanceof EmptyTile) {
         mineField[x + 1][y] = new WarningTile(x + 1, y);
      }
      
      if (x - 1 >= 0 && mineField[x - 1][y] instanceof EmptyTile) {
         mineField[x - 1][y] = new WarningTile(x - 1, y);
      }
      
      if (mineField[x].length - 1 >= y + 1 && mineField[x][y + 1] instanceof EmptyTile) {
         mineField[x][y + 1] = new WarningTile(x, y + 1);
      }
      
      if (y - 1 >= 0 && mineField[x][y - 1] instanceof EmptyTile) {
         mineField[x][y - 1] = new WarningTile(x, y - 1);
      }
      
      if (mineField.length - 1 >= x + 1 && mineField[x].length - 1 >= y + 1 && mineField[x + 1][y + 1] instanceof EmptyTile) {
         mineField[x + 1][y + 1] = new WarningTile(x + 1, y + 1);
      }
      
      if (x - 1 >= 0 && y - 1 >= 0 && mineField[x - 1][y - 1] instanceof EmptyTile) {
         mineField[x - 1][y - 1] = new WarningTile(x - 1, y - 1);
      }
      
      if (x - 1 >= 0 && mineField[x].length - 1 >= y + 1 && mineField[x - 1][y + 1] instanceof EmptyTile) {
         mineField[x - 1][y + 1] = new WarningTile(x - 1, y + 1);
      }
      
      if (mineField.length - 1 >= x + 1 && y - 1 >= 0 && mineField[x + 1][y - 1] instanceof EmptyTile) {
         mineField[x + 1][y - 1] = new WarningTile(x + 1, y - 1);
      }
   }
}