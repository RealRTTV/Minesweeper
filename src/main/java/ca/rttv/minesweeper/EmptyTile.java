package ca.rttv.minesweeper;

import static ca.rttv.minesweeper.Main.mineField;

public class EmptyTile extends Tile {
   public EmptyTile(int x, int y) {
      super(x, y);
   }
   
   @Override
   public void click() { // we don't have to check if something is an instance of a MineTile because there will be warning tiles inbetween
      if (covered) {
         if (this.flagged) {
            this.flag();
         }
         Main.tilesLeft--;
         covered = false;
         Main.updateTile(this);
         if (mineField.length - 1 >= x + 1) {
            mineField[x + 1][y].click();
         }
         
         if (x - 1 >= 0) {
            mineField[x - 1][y].click();
         }
         
         if (mineField[x].length - 1 >= y + 1) {
            mineField[x][y + 1].click();
         }
         
         if (y - 1 >= 0) {
            mineField[x][y - 1].click();
         }
         
         if (mineField.length - 1 >= x + 1 && mineField[x].length - 1 >= y + 1) {
            mineField[x + 1][y + 1].click();
         }
         
         if (x - 1 >= 0 && y - 1 >= 0) {
            mineField[x - 1][y - 1].click();
         }
         
         if (x - 1 >= 0 && mineField[x].length - 1 >= y + 1) {
            mineField[x - 1][y + 1].click();
         }
         
         if (mineField.length - 1 >= x + 1 && y - 1 >= 0) {
            mineField[x + 1][y - 1].click();
         }
      }
   }
}