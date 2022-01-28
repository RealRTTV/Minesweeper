package ca.rttv.minesweeper;

import static ca.rttv.minesweeper.Main.flagsLeft;
import static ca.rttv.minesweeper.Main.updateFlagDisplay;
import static ca.rttv.minesweeper.Main.updateTile;

public class Tile {
   public final int     x;
   public final int     y;
   public       boolean covered;
   public       boolean flagged;
   public       boolean selected;
   
   public Tile(int x, int y) {
      this.flagged = false;
      this.covered = true;
      this.selected = false;
      this.x = x;
      this.y = y;
   }
   
   public void click() {
      if (covered) {
         if (this.flagged) {
            this.flag();
         }
         this.covered = false;
      }
   }
   
   public void flag() {
      if (this.covered) {
         updateTile(this);
         flagsLeft = this.flagged ? flagsLeft + 1 : flagsLeft - 1;
         this.flagged = !this.flagged;
         updateFlagDisplay();
      }
   }
   
   public void init() {
   
   }
}