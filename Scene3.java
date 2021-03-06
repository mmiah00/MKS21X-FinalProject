//API : http://mabe02.github.io/lanterna/apidocs/2.1/
import com.googlecode.lanterna.terminal.Terminal.SGR;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.Key.Kind;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.LanternaException;
import com.googlecode.lanterna.input.CharacterPattern;
import com.googlecode.lanterna.input.InputDecoder;
import com.googlecode.lanterna.input.InputProvider;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.input.KeyMappingProfile;

public class Scene3 extends Scene{

  public Scene3(Terminal t) {
    putString(0, 1, t, "  ,                 _,-,   "); //hammer and ax
    putString(0, 2, t, " /(  ___________   T_  |   ");
    putString(0, 3, t, "|  >:===========`  ||`-'   ");
    putString(0, 4, t, " )(                ||      ");
    putString(0, 5, t, " \"\"                ||      ");
    putString(0, 6, t, "                   --      ");

    putString(0, 8, t,  "| You opened the box! You can |");
    putString(0, 9, t,  "| use the materials to build  |");
    putString(0, 10, t, "| a bridge. Put the planks in |");
    putString(0, 11, t, "| the correct numerical order |");
    putString(0, 12, t, "|    to build your bridge.    |");
  }

  public static int[] playScene3(Terminal terminal, int beginMin, int beginSec) {
    Scene3 A = new Scene3(terminal);
    int [] returns = new int [3];

    long lastTime =  System.currentTimeMillis();
    long currentTime = lastTime;
    long timer = 0;
    boolean firstPass = true;

    int counter = 0;
    while (counter != 100000) { //display picture and prompt for a while
      counter++;
      Key key = terminal.readInput();
      if (key != null){
        if (key.getKind() == Key.Kind.Escape) {
          terminal.exitPrivateMode();
          counter = 100000;
          returns[0] = -1; //exits game
          return returns;
        }
      }

      lastTime = currentTime;
      currentTime = System.currentTimeMillis();
      timer += (currentTime -lastTime);  //changes timer
      A.setMinLeft(beginMin - (int)(timer/60000)); //changes min left
      String minPassed = String.format("%02d", A.getMinLeft());
      if ((int)(timer%60000/1000) > beginSec) {
        firstPass = false;
      }
      if (firstPass) { //changes sec left
        A.setSecLeft(beginSec -(int)(timer%60000/1000));
      }
      else {
        A.setSecLeft(60 - (int)(timer%60000/1000));
      }
      String secPassed = String.format("%02d", A.getSecLeft());
      if (A.getSecLeft() == 60) { //special case
        A.setMinLeft(beginMin - (int)(timer/60000));
        minPassed = String.format("%02d", A.getMinLeft());
        secPassed = "00";
      }
      putString(0,0,terminal, "Time Left: "+ minPassed + ":" + secPassed);
      returns[1] = A.getMinLeft(); //passes on min left at end
      returns[2] = A.getSecLeft(); //passes on sec left at end

      if (A.getMinLeft() == 0 && A.getSecLeft() == 1) { //run out of time
        counter = 100000;
        returns [0] = -2; //fail message
        return returns;
      }
    }
    terminal.clearScreen();
    returns [0] = 4; //next mode
    return returns;
  }
}
