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

public class Scene2 {

  String[] path = new String[20];
  int index = 0;

  public Scene2 (Terminal t) {
    putString(0, 0, t, "--------------------");
    putString(0, 1, t, "      /   .\\        ");
    putString(0, 2, t, "     / ` .  \\       ");
    putString(0, 3, t, " [] / .   `  \\      ");
    putString(0, 4, t, "   /.   `  `  \\     ");
    putString(0, 5, t, "  /  ` . `  . .\\    ");
    putString(0, 6, t, " / `  .   .  `  \\   ");
    putString(0, 7, t, "/ .  `   ` .  .  \\  ");

    path[0] = "o";
    for (int i = 1; i < 20; i++) {
      path[i] = " ";
    }
    putString(0, 8, t, toString());

    putString(0, 10, t, "|  Oh No! There's a river! |");
    putString(0, 11, t, "|   Wait, there's a box!   |");
    putString(0, 12, t, "| Solve the puzzle to open |");
    putString(0, 13, t, "| the box and get materials|");
    putString(0, 14, t, "|    to build a bridge.    |");
  }

  public String toString() {
    String s = " ";
    for (int i = 0; i < 20; i++) {
      s = s + path[i];
    }
    return s;
  }

  public void moveLeft() {
    if (index != 0) {
      path[index-1] = "o";
      path[index] = " ";
      index--;
    }
  }

  public void moveRight() {
    if (index != 19) {
      path[index+1] = "o";
      path[index] = " ";
      index++;
    }
  }

  public boolean isLastSpot() {
    return index == 19;
  }

  public static void putString(int r, int c,Terminal t, String s){
    t.moveCursor(r,c);
    for(int i = 0; i < s.length();i++){
      t.putCharacter(s.charAt(i));
    }
  }

  public static int playScene2(Terminal terminal) {
    Scene2 A = new Scene2(terminal);

    boolean pathNotDone = true;
    while (pathNotDone) {
      pathNotDone = !(A.isLastSpot());
      putString(0, 10, terminal, A.toString());
      Key key = terminal.readInput();
      if (key != null){
        if (key.getKind() == Key.Kind.Escape) {
          terminal.exitPrivateMode();
          pathNotDone = false;
        }
        if (key.getKind() == Key.Kind.ArrowLeft) {
          A.moveLeft();
        }
        if (key.getKind() == Key.Kind.ArrowRight) {
          A.moveRight();
        }
      }
    }
    terminal.clearScreen();
    return 2;
  }
}
