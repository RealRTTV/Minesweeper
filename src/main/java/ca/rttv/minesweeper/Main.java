package ca.rttv.minesweeper;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.jetbrains.annotations.NotNull;

import static java.awt.event.KeyEvent.VK_BACK_QUOTE;
import static java.awt.event.KeyEvent.VK_F2;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.imageio.ImageIO.read;

public class Main {
   public static final ImageIcon   clickedMineTileTexture       = new ImageIcon(smartRead("/assets/tiles/clickmine.png"));
   public static final ImageIcon   deadResetButtonTexture       = new ImageIcon(smartRead("/assets/frame/resetdead.png"));
   public static final ImageIcon   eastBorder                   = new ImageIcon(smartRead("/assets/frame/east.png"));
   public static final ImageIcon   eight                        = new ImageIcon(smartRead("/assets/values/eight.png"));
   public static final ImageIcon   emptyTileTexture             = new ImageIcon(smartRead("/assets/tiles/empty.png"));
   public static final ImageIcon   five                         = new ImageIcon(smartRead("/assets/values/five.png"));
   public static final ImageIcon   flaggedTileTexture           = new ImageIcon(smartRead("/assets/tiles/flagged.png"));
   public static final ImageIcon   four                         = new ImageIcon(smartRead("/assets/values/four.png"));
   public static final JFrame      frame                        = new JFrame();
   public static final ImageIcon   hoveredFlagTileTexture       = new ImageIcon(smartRead("/assets/tiles/hoverflag.png"));
   public static final ImageIcon   hoveredTile                  = new ImageIcon(smartRead("/assets/tiles/hover.png"));
   public static final ImageIcon   imageDisplayBorder           = new ImageIcon(smartRead("/assets/values/frame.png"));
   public static final ImageIcon   incorrectFlaggedTileTexture  = new ImageIcon(smartRead("/assets/tiles/wrongmine.png"));
   public static final int[]       index                        = { 0 };
   public static final int         leftXBorder                  = 12;
   public static final int         lowerYBorder                 = 8;
   public static final ImageIcon   mineTileTexture              = new ImageIcon(smartRead("/assets/tiles/mine.png"));
   public static final ImageIcon   minus                        = new ImageIcon(smartRead("/assets/values/minus.png"));
   public static final ImageIcon   nine                         = new ImageIcon(smartRead("/assets/values/nine.png"));
   public static final ImageIcon   northBorder                  = new ImageIcon(smartRead("/assets/frame/north.png"));
   public static final ImageIcon   northEastBorder              = new ImageIcon(smartRead("/assets/frame/northeast.png"));
   public static final ImageIcon   northWestBorder              = new ImageIcon(smartRead("/assets/frame/northwest.png"));
   public static final ImageIcon   one                          = new ImageIcon(smartRead("/assets/values/one.png"));
   public static final ImageIcon   pressedResetButtonTexture    = new ImageIcon(smartRead("/assets/frame/resetpressed.png"));
   public static final Random      random                       = new Random();
   public static final ImageIcon   resetButtonTexture           = new ImageIcon(smartRead("/assets/frame/reset.png"));
   public static final int         rightXBorder                 = 8;
   public static final ImageIcon   seven                        = new ImageIcon(smartRead("/assets/values/seven.png"));
   public static final ImageIcon   six                          = new ImageIcon(smartRead("/assets/values/six.png"));
   public static final ImageIcon   southBorder                  = new ImageIcon(smartRead("/assets/frame/south.png"));
   public static final ImageIcon   southEastBorder              = new ImageIcon(smartRead("/assets/frame/southeast.png"));
   public static final ImageIcon   southWestBorder              = new ImageIcon(smartRead("/assets/frame/southwest.png"));
   public static final ImageIcon   sunglassesResetButtonTexture = new ImageIcon(smartRead("/assets/frame/resetsunglasses.png"));
   public static final ImageIcon   three                        = new ImageIcon(smartRead("/assets/values/three.png"));
   public static final ImageIcon   tileTexture                  = new ImageIcon(smartRead("/assets/tiles/covered.png"));
   public static final ImageIcon   two                          = new ImageIcon(smartRead("/assets/values/two.png"));
   public static final int         upperYBorder                 = 55;
   public static final ImageIcon[] warningTileTextures          = new ImageIcon[] { new ImageIcon(smartRead("/assets/tiles/warning1.png")),
                                                                                    new ImageIcon(smartRead("/assets/tiles/warning2.png")),
                                                                                    new ImageIcon(smartRead("/assets/tiles/warning3.png")),
                                                                                    new ImageIcon(smartRead("/assets/tiles/warning4.png")),
                                                                                    new ImageIcon(smartRead("/assets/tiles/warning5.png")),
                                                                                    new ImageIcon(smartRead("/assets/tiles/warning6.png")),
                                                                                    new ImageIcon(smartRead("/assets/tiles/warning7.png")),
                                                                                    new ImageIcon(smartRead("/assets/tiles/warning8.png")) };
   public static final ImageIcon   westBorder                   = new ImageIcon(smartRead("/assets/frame/west.png"));
   public static final ImageIcon   zero                         = new ImageIcon(smartRead("/assets/values/zero.png"));
   public static       boolean     covered                      = true;
   public static       boolean     firstClick                   = true;
   public static       JLabel[]    flagDisplay                  = new JLabel[3];
   public static       int         flagsLeft;
   public static       boolean     gameActive                   = false;
   public static       boolean     gameEnded                    = false;
   public static       int         h                            = 9;
   public static       JLabel[]    east                         = new JLabel[h];
   public static       int         mineCount;
   public static       int         mineX                        = -1;
   public static       int         mineY                        = -1;
   public static       JLabel      minesDisplayBorder;
   public static       int         mouseX;
   public static       int         mouseY;
   public static       String[]    newValues                    = new String[3];
   public static       JLabel      northeast;
   public static       JLabel      northwest;
   public static       int         previousX                    = -1;
   public static       int         previousY                    = -1;
   public static       JLabel      resetButton;
   public static       int         seconds                      = 0;
   public static       JLabel[]    secondsDisplay               = new JLabel[3];
   public static       JLabel      secondsDisplayBorder;
   public static       JLabel      southeast;
   public static       JLabel      southwest;
   public static       int         tilesLeft;
   public static       int         w                            = 9;
   public static       Tile[][]    mineField                    = new Tile[w][h];
   public static       JLabel[][]  labelField                   = new JLabel[w][h];
   public static       JLabel[]    north                        = new JLabel[w];
   public static       JLabel[]    south                        = new JLabel[w];
   public static       JLabel[]    west                         = new JLabel[h];
   public static       int         xInset;
   public static       int         yInset;
   
   public static void buildListeners() {
      frame.addMouseListener(new MouseListener() {
         @Override
         public void mouseClicked(MouseEvent e) { }
         
         @Override
         public void mouseEntered(MouseEvent e) { }
         
         @Override
         public void mouseExited(MouseEvent e) { }
         
         @Override
         public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
               mouseX = e.getX() - leftXBorder - xInset;
               mouseY = e.getY() - upperYBorder - yInset - 1;
               mouseX = mouseX >= 0 ? mouseX / 16 : (mouseX / 16) - 1;
               mouseY = mouseY >= 0 ? mouseY / 16 : (mouseY / 16) - 1;
               
               if (mouseY < w && mouseX < h && mouseY >= 0 && mouseX >= 0) {
                  if (!mineField[mouseX][mouseY].flagged) {
                     if (mineField[mouseX][mouseY].covered && !gameEnded) {
                        labelField[mouseX][mouseY].setIcon(emptyTileTexture);
                     }
                     if (previousY != mouseY || previousX != mouseX) {
                        if (previousY != -1 && previousX != -1) {
                           updateTile(mineField[previousX][previousY]);
                        }
                     }
                     previousX = mouseX;
                     previousY = mouseY;
                  } else if (previousX != -1 && previousY != -1) {
                     updateTile(mineField[previousX][previousY]);
                  }
               } else if (e.getX() - xInset > ((frame.getWidth() - 12) / 2 - 13) && e.getX() - xInset < (frame.getWidth() - 12) / 2 + 13 && e.getY() - yInset - 1 > 15 && e.getY() - yInset - 1 < 42) {
                  resetButton.setIcon(pressedResetButtonTexture);
                  frame.repaint();
               }
            } else if (!gameEnded && e.getButton() == MouseEvent.BUTTON2 || e.getButton() == /*MouseEvent.BUTTON*/4 || e.getButton() == /*MouseEvent.BUTTON*/5) {
               int x = (e.getX() - leftXBorder - xInset) / 16;
               int y = (e.getY() - upperYBorder - yInset - 1) / 16;
               
               if (firstClick) {
                  placeMines(x, y);
                  firstClick = false;
                  seconds++;
                  updateSecondsDisplay();
               }
               
               if (x < w && y < h && x >= 0 && y >= 0) {
                  
                  if (!mineField[x][y].flagged) {
                     mineField[x][y].click();
                  }
                  
                  if (mineField.length - 1 >= x + 1 && !mineField[x + 1][y].flagged) {
                     mineField[x + 1][y].click();
                  }
                  
                  if (x - 1 >= 0 && !mineField[x - 1][y].flagged) {
                     mineField[x - 1][y].click();
                  }
                  
                  if (mineField[x].length - 1 >= y + 1 && !mineField[x][y + 1].flagged) {
                     mineField[x][y + 1].click();
                  }
                  
                  if (y - 1 >= 0 && !mineField[x][y - 1].flagged) {
                     mineField[x][y - 1].click();
                  }
                  
                  if (mineField.length - 1 >= x + 1 && mineField[x].length - 1 >= y + 1 && !mineField[x + 1][y + 1].flagged) {
                     mineField[x + 1][y + 1].click();
                  }
                  
                  if (x - 1 >= 0 && y - 1 >= 0 && !mineField[x - 1][y - 1].flagged) {
                     mineField[x - 1][y - 1].click();
                  }
                  
                  if (x - 1 >= 0 && mineField[x].length - 1 >= y + 1 && !mineField[x - 1][y + 1].flagged) {
                     mineField[x - 1][y + 1].click();
                  }
                  
                  if (mineField.length - 1 >= x + 1 && y - 1 >= 0 && !mineField[x + 1][y - 1].flagged) {
                     mineField[x + 1][y - 1].click();
                  }
               }
               
               if (tilesLeft == 0) {
                  gameActive = false;
                  gameEnded = true;
                  for (Tile[] tiles : mineField) {
                     for (Tile tile : tiles) {
                        if (tile instanceof MineTile && !tile.flagged) {
                           tile.flag();
                           updateTile(tile);
                        }
                     }
                  }
               }
            } else if (e.getButton() == MouseEvent.BUTTON3 && !gameEnded) {
               int x = (e.getX() - leftXBorder - xInset) / 16;
               int y = (e.getY() - upperYBorder - yInset - 1) / 16;
               
               if (x < w && y < h && x >= 0 && y >= 0) {
                  mineField[x][y].flag();
                  updateTile(mineField[x][y]);
                  gameActive = true;
               }
            }
         }
         
         @Override
         public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getX() - xInset > ((frame.getWidth() - 12) / 2 - 13) && e.getX() - xInset < (frame.getWidth() - 12) / 2 + 13 && e.getY() - yInset - 1 > 15 && e.getY() - yInset - 1 < 42) {
               remakeGame(w, h, mineCount);
               return;
            }
            
            if (!gameEnded && e.getButton() == MouseEvent.BUTTON1) {
               mouseX = e.getX() - leftXBorder - xInset;
               mouseY = e.getY() - upperYBorder - yInset - 1;
               mouseX = mouseX >= 0 ? mouseX / 16 : (mouseX / 16) - 1;
               mouseY = mouseY >= 0 ? mouseY / 16 : (mouseY / 16) - 1;
               
               if (firstClick) {
                  placeMines(mouseX, mouseY);
                  firstClick = false;
                  seconds++;
                  updateSecondsDisplay();
               }
               
               if (mouseX < w && mouseY < h && mouseX >= 0 && mouseY >= 0) {
                  mineField[mouseX][mouseY].click();
                  updateTile(mineField[mouseX][mouseY]);
                  gameActive = true;
               }
               
               if (tilesLeft == 0) {
                  gameActive = false;
                  gameEnded = true;
                  for (Tile[] tiles : mineField) {
                     for (Tile tile : tiles) {
                        if (tile instanceof MineTile && !tile.flagged) {
                           tile.flag();
                           updateTile(tile);
                        }
                     }
                  }
                  resetButton.setIcon(sunglassesResetButtonTexture);
                  frame.repaint();
               }
            }
         }
      });
      
      frame.addKeyListener(new KeyListener() {
         @Override
         public void keyPressed(KeyEvent e) {
            char key = e.getKeyChar();
            if (e.getKeyCode() == VK_F2) {
               remakeGame(w, h, mineCount);
               return;
            }
            if (e.getKeyCode() == VK_BACK_QUOTE) {
               newValues[0] = "";
               newValues[1] = "";
               newValues[2] = "";
               index[0] = 0;
            }
            if (key <= '9' && key >= '0') { // numbers
               newValues[index[0]] = newValues[index[0]].concat(Character.toString(key));
            }
            if (key == (char) (byte) 0x20) { // space
               index[0] = (index[0] + 1) % 3;
            }
            if (key == (char) (byte) 0x0A && index[0] == 2) { // enter
               int height = Integer.parseInt(newValues[0]);
               int width = Integer.parseInt(newValues[1]);
               int mines = Integer.parseInt(newValues[2]);
               
               if (height < 8) {
                  height = 8;
               }
               
               if (mines > height * width - 1) {
                  mines = height * width - 1;
               }
               
               remakeGame(height, width, mines);
            }
         }
         
         @Override
         public void keyReleased(KeyEvent e) { }
         
         @Override
         public void keyTyped(KeyEvent e) { }
      });
      
      frame.addMouseMotionListener(new MouseMotionListener() {
         @Override
         public void mouseDragged(MouseEvent e) {
            mouseX = e.getX() - leftXBorder - xInset;
            mouseY = e.getY() - upperYBorder - yInset - 1;
            mouseX = mouseX >= 0 ? mouseX / 16 : (mouseX / 16) - 1;
            mouseY = mouseY >= 0 ? mouseY / 16 : (mouseY / 16) - 1;
            
            if (mouseY < h && mouseX < w && mouseY >= 0 && mouseX >= 0) {
               if (!mineField[mouseX][mouseY].flagged) {
                  if (mineField[mouseX][mouseY].covered && !gameEnded) {
                     labelField[mouseX][mouseY].setIcon(emptyTileTexture);
                  }
                  if (previousY != mouseY || previousX != mouseX) {
                     if (previousY != -1 && previousX != -1) {
                        updateTile(mineField[previousX][previousY]);
                     }
                  }
                  previousX = mouseX;
                  previousY = mouseY;
               } else if (previousX != -1 && previousY != -1) {
                  updateTile(mineField[previousX][previousY]);
               }
            }
            if (e.getX() - xInset > ((frame.getWidth() - 12) / 2 - 13) && e.getX() - xInset < (frame.getWidth() - 12) / 2 + 13 && e.getY() - yInset - 1 > 15 && e.getY() - yInset - 1 < 42) {
               resetButton.setIcon(resetButtonTexture);
               frame.repaint();
            }
         }
         
         @Override
         public void mouseMoved(MouseEvent e) {
            mouseX = e.getX() - leftXBorder - xInset;
            mouseY = e.getY() - upperYBorder - yInset - 1;
            mouseX = mouseX >= 0 ? mouseX / 16 : (mouseX / 16) - 1;
            mouseY = mouseY >= 0 ? mouseY / 16 : (mouseY / 16) - 1;
            
            if (mouseY < h && mouseX < w && mouseY >= 0 && mouseX >= 0) {
               if (mineField[mouseX][mouseY].covered && !gameEnded) {
                  if (!mineField[mouseX][mouseY].flagged) {
                     labelField[mouseX][mouseY].setIcon(hoveredTile);
                  } else {
                     labelField[mouseX][mouseY].setIcon(hoveredFlagTileTexture);
                  }
               }
               if (previousY != mouseY || previousX != mouseX) {
                  if (previousY != -1 && previousX != -1) {
                     updateTile(mineField[previousX][previousY]);
                  }
               }
               previousX = mouseX;
               previousY = mouseY;
            } else if (previousX != -1 && previousY != -1) {
               updateTile(mineField[previousX][previousY]);
            }
         }
      });
   }
   
   public static void endGame(int x, int y) {
      Main.covered = false;
      gameEnded = true;
      gameActive = false;
      mineX = x;
      mineY = y;
      for (Tile[] tiles : mineField) {
         for (Tile tile : tiles) {
            updateTile(tile);
         }
      }
      resetButton.setIcon(deadResetButtonTexture);
      frame.repaint();
   }
   
   public static void updateTile(@NotNull Tile tile) {
      ImageIcon tileTexture = Main.tileTexture;
      
      // I apologize that this logic sucks.
      if (tile.flagged && tile.x == mouseX && tile.y == mouseY && !gameEnded && gameActive) {
         tileTexture = hoveredFlagTileTexture;
      } else if (tile.flagged && !gameEnded && gameActive) {
         tileTexture = flaggedTileTexture;
      } else if (tile.flagged && tile instanceof MineTile) {
         tileTexture = flaggedTileTexture;
      } else if (tile.flagged) {
         tileTexture = incorrectFlaggedTileTexture;
      } else if (!tile.covered && !(tile instanceof MineTile)) {
         if (tile instanceof EmptyTile) {
            tileTexture = emptyTileTexture;
         } else if (tile instanceof WarningTile) {
            tileTexture = warningTileTextures[((WarningTile) tile).value - 1];
         }
      } else if (tile instanceof MineTile && !Main.covered && (mineX != tile.x || mineY != tile.y)) {
         tileTexture = mineTileTexture;
      } else if (tile instanceof MineTile && !Main.covered) {
         tileTexture = clickedMineTileTexture;
      }
      
      JLabel label = labelField[tile.x][tile.y];
      
      label.setIcon(tileTexture);
      label.setBounds(tile.x * 16 + leftXBorder, tile.y * 16 + upperYBorder, tileTexture.getIconWidth(), tileTexture.getIconHeight());
      frame.remove(label);
      frame.add(label);
      frame.repaint();
   }
   
   public static void main(String[] args) {
      
      newValues[0] = "";
      newValues[1] = "";
      newValues[2] = "";
      
      buildListeners();
      
      setupBoard(h, w, 10, true);
      
      renderField();
      
      new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> {
         
         if (gameActive && !gameEnded) {
            seconds++;
            updateSecondsDisplay();
         }
      }, 0, 1, SECONDS);
   }
   
   public static void placeMines(int x, int y) {
      for (int i = 0; i < flagsLeft; ) {
         int mineX = random.nextInt(w);
         int mineY = random.nextInt(h);
         if (!(mineX == x && mineY == y) && !(mineField[mineX][mineY] instanceof MineTile)) {
            mineField[mineX][mineY] = new MineTile(mineX, mineY);
            i++;
         }
      }
      
      for (Tile[] tiles : mineField) {
         for (Tile tile : tiles) {
            tile.init();
         }
      }
   }
   
   public static void remakeGame(int height, int width, int mines) {
      for (JLabel[] jLabels : labelField) {
         for (JLabel jLabel : jLabels) {
            frame.remove(jLabel);
         }
      }
      
      for (int i = 0; i < w; i++) {
         frame.remove(north[i]);
         frame.remove(south[i]);
      }
      
      for (int i = 0; i < h; i++) {
         frame.remove(east[i]);
         frame.remove(west[i]);
      }
      
      frame.remove(northeast);
      frame.remove(southeast);
      frame.remove(southwest);
      frame.remove(northwest);
      
      frame.remove(resetButton);
      
      frame.remove(minesDisplayBorder);
      for (JLabel jLabel : flagDisplay) {
         frame.remove(jLabel);
      }
      
      frame.remove(secondsDisplayBorder);
      for (JLabel jLabel : secondsDisplay) {
         frame.remove(jLabel);
      }
      
      newValues[0] = "";
      newValues[1] = "";
      newValues[2] = "";
      north = new JLabel[height];
      east = new JLabel[width];
      south = new JLabel[height];
      west = new JLabel[width];
      previousY = -1;
      previousX = -1;
      seconds = 0;
      index[0] = 0;
      gameActive = false;
      gameEnded = false;
      firstClick = true;
      mineX = -1;
      mineY = -1;
      frame.repaint();
      setupBoard(height, width, mines, false);
      renderField();
   }
   
   public static void renderField() {
      for (int i = 0; i < w; i++) {
         for (int j = 0; j < h; j++) {
            labelField[i][j] = new JLabel();
            updateTile(mineField[i][j]);
         }
      }
      
      for (int i = 0; i < h; i++) {
         west[i] = new JLabel(westBorder);
         west[i].setBounds(0, upperYBorder + i * 16, 12, 16);
         frame.add(west[i]);
         
         east[i] = new JLabel(eastBorder);
         east[i].setBounds(frame.getWidth() - 24, upperYBorder + i * 16, 8, 16);
         frame.add(east[i]);
      }
      
      for (int i = 0; i < w; i++) {
         north[i] = new JLabel(northBorder);
         north[i].setBounds(12 + i * 16, 0, 16, 55);
         frame.add(north[i]);
         
         south[i] = new JLabel(southBorder);
         south[i].setBounds(12 + i * 16, frame.getHeight() - 47, 16, 8);
         frame.add(south[i]);
      }
      
      northeast = new JLabel(northEastBorder);
      northeast.setBounds(frame.getWidth() - 24, 0, 8, 55);
      frame.add(northeast);
      
      southeast = new JLabel(southEastBorder);
      southeast.setBounds(frame.getWidth() - 24, frame.getHeight() - 47, 8, 8);
      frame.add(southeast);
      
      southwest = new JLabel(southWestBorder);
      southwest.setBounds(0, frame.getHeight() - 47, 12, 8);
      frame.add(southwest);
      
      northwest = new JLabel(northWestBorder);
      northwest.setBounds(0, 0, 12, 55);
      frame.add(northwest);
      
      minesDisplayBorder = new JLabel(imageDisplayBorder);
      minesDisplayBorder.setBounds(16, 15, 41, 25);
      frame.add(minesDisplayBorder, 1);
      
      secondsDisplayBorder = new JLabel(imageDisplayBorder);
      secondsDisplayBorder.setBounds(frame.getWidth() - 71, 15, 41, 25);
      frame.add(secondsDisplayBorder, 1);
      
      resetButton = new JLabel(resetButtonTexture);
      resetButton.setBounds((frame.getWidth() - 12) / 2 - 13, 15, 26, 26);
      frame.add(resetButton, 2);
      
      updateFlagDisplay();
      updateSecondsDisplay();
      
      frame.repaint();
      frame.setVisible(true);
   }
   
   public static void setupBoard(int height, int width, int mines, boolean moveFrame) {
      w = height;
      h = width;
      mineCount = mines;
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(null);
      frame.setResizable(false);
      if (moveFrame) {
         frame.setLocationRelativeTo(null);
      }
      frame.setIconImage(flaggedTileTexture.getImage());
      frame.setPreferredSize(new Dimension(w * 16 + frame.getInsets().left + frame.getInsets().right + leftXBorder + rightXBorder, h * 16 + frame.getInsets().top + frame.getInsets().bottom + upperYBorder + lowerYBorder));
      frame.pack();
      yInset = frame.getInsets().top;
      xInset = frame.getInsets().left;
      frame.setSize(w * 16 + frame.getInsets().left + frame.getInsets().right + leftXBorder + rightXBorder, h * 16 + frame.getInsets().top + frame.getInsets().bottom + upperYBorder + lowerYBorder);
      frame.setTitle("Minesweeper (" + w + "x" + h + ")");
      
      flagsLeft = mineCount;
      tilesLeft = h * w - flagsLeft;
      covered = true;
      
      mineField = new Tile[height][width];
      labelField = new JLabel[height][width];
      
      for (int i = 0; i < flagDisplay.length; i++) {
         flagDisplay[i] = new JLabel();
      }
      
      for (int i = 0; i < secondsDisplay.length; i++) {
         secondsDisplay[i] = new JLabel();
      }
      
      for (int x = 0; x < mineField.length; x++) {
         for (int y = 0; y < mineField[x].length; y++) {
            mineField[x][y] = new EmptyTile(x, y);
            labelField[x][y] = new JLabel();
         }
      }
   }
   
   public static Image smartRead(String name) {
      try {
         return read(requireNonNull(Main.class.getResourceAsStream(name)));
      } catch (IOException ignored) {
      }
      return new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
   }
   
   public static void updateFlagDisplay() {
      String displayedFlags = "000".concat(Integer.toString(flagsLeft));
      displayedFlags = displayedFlags.substring(displayedFlags.length() - 3);
      char[] chars = { displayedFlags.charAt(0),
                       displayedFlags.charAt(1),
                       displayedFlags.charAt(2) };
      for (int i = 0; i < chars.length; i++) {
         flagDisplay[i].setIcon(switch (chars[i]) {
            case '-' -> minus;
            case '0' -> zero;
            case '1' -> one;
            case '2' -> two;
            case '3' -> three;
            case '4' -> four;
            case '5' -> five;
            case '6' -> six;
            case '7' -> seven;
            case '8' -> eight;
            case '9' -> nine;
            default -> throw new IllegalStateException("Unexpected value: " + chars[i]);
         });
         flagDisplay[i].setBounds(17 + i * 13, 16, 13, 23);
         frame.add(flagDisplay[i], 2);
      }
   }
   
   public static void updateSecondsDisplay() {
      String displayedSeconds = "000".concat(Integer.toString(seconds));
      displayedSeconds = displayedSeconds.substring(displayedSeconds.length() - 3);
      if (seconds >= 999) displayedSeconds = "999";
      char[] chars = { displayedSeconds.charAt(0),
                       displayedSeconds.charAt(1),
                       displayedSeconds.charAt(2) };
      for (int i = 0; i < chars.length; i++) {
         frame.remove(secondsDisplay[i]);
         secondsDisplay[i].setIcon(switch (chars[i]) {
            case '-' -> minus;
            case '0' -> zero;
            case '1' -> one;
            case '2' -> two;
            case '3' -> three;
            case '4' -> four;
            case '5' -> five;
            case '6' -> six;
            case '7' -> seven;
            case '8' -> eight;
            case '9' -> nine;
            default -> throw new IllegalStateException("Unexpected value: " + chars[i]);
         });
         secondsDisplay[i].setBounds(frame.getWidth() - 70 + i * 13, 16, 13, 23);
         frame.add(secondsDisplay[i], 2);
      }
      frame.repaint();
   }
}