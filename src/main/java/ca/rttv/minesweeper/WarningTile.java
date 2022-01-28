package ca.rttv.minesweeper;

import static ca.rttv.minesweeper.Main.mineField;

public class WarningTile extends Tile {
   
   public final int value;
   
   public WarningTile(int x, int y) {
      super(x, y);
      this.value = getValue();
   }
   
   public int getValue() {
      int value = 0;
      
      // right
      if (mineField.length - 1 >= x + 1 && mineField[x + 1][y] instanceof MineTile) {
         value++;
      }
      
      // left
      if (x - 1 >= 0 && mineField[x - 1][y] instanceof MineTile) {
         value++;
      }
      
      // up
      if (mineField[x].length - 1 >= y + 1 && mineField[x][y + 1] instanceof MineTile) {
         value++;
      }
      
      // down
      if (y - 1 >= 0 && mineField[x][y - 1] instanceof MineTile) {
         value++;
      }
      
      // northeast
      if (mineField.length - 1 >= x + 1 && mineField[x].length - 1 >= y + 1 && mineField[x + 1][y + 1] instanceof MineTile) {
         value++;
      }
      
      // southwest
      if (x - 1 >= 0 && y - 1 >= 0 && mineField[x - 1][y - 1] instanceof MineTile) {
         value++;
      }
      
      // northwest
      if (x - 1 >= 0 && mineField[x].length - 1 >= y + 1 && mineField[x - 1][y + 1] instanceof MineTile) {
         value++;
      }
      
      // southeast
      if (mineField.length - 1 >= x + 1 && y - 1 >= 0 && mineField[x + 1][y - 1] instanceof MineTile) {
         value++;
      }
      return value;
   }
   
   @Override
   public void click() {
      if (covered && !flagged) {
         covered = false;
         Main.tilesLeft--;
         Main.updateTile(this);
      }
   }
}