import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

public class GameState{

  GameState previous;
  byte[] state = new byte[22];

  GameState(GameState prev){
    previous = prev;
    if(prev !=null){
      for(int i = 0; i<22; i++){
        this.state[i] = prev.state[i];
      }
    }
  }

  public static GameState BreadthFirst() throws Exception{
    GameState root = new GameState(null);
    GameState current = new GameState(null);
    LinkedList<GameState> q = new LinkedList<GameState>();
    TreeSet<GameState> set = new TreeSet<GameState>(new StateComparator());
    int iterate = 0;

    q.add(root);
    set.add(root);

    while(!q.isEmpty()){
      current = q.poll();

      if(Puzzle.gameOver(current.state)){
        System.out.println("Solution found");
        return current;
      }

      //iterate through state array
      for(int i=0; i<22; i++){
        //add the value of j to ith spoth in state array
        //when i = 0 we do up and down, when i = 1 we move left and right
        for(int j=-1; j<2; j+=2){
          GameState node = new GameState(current);
          node.state[i] += j;

          if(!set.contains(node)){
            //System.out.println("not in set");
            if(Puzzle.validBoard(node.state)){
              //System.out.println("added");
              q.add(node);
              set.add(node);
            }
          }
        }
      }
    }

    throw new Exception("No Path Possible");
  }
}
