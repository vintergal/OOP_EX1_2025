import java.util.ArrayList;
import java.util.List;

public class GreedyAI extends AIPlayer{
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) { //TODO ask what does normal distribution means
        List<Position> moves = ValidMoves();
        List<Position> greedyMoves=new ArrayList<Position>(); //a list of all the greedy moves
        int mostFlips = 0;
        for (int i=0; i=ValidMoves().size();i++) {
            Position currentPos = ValidMoves().get(i);
            int numOfMoves = greedyMoves.get(i).countFlips(currentPos); //GameLogic methode
            if(numOfMoves>mostFlips) {
                List<Position> greedyMoves=new ArrayList<Position>(); //reset the greedy moves
                greedyMoves.add(currentPos);
                mostFlips = numOfMoves;
            }
            if(numOfMoves=mostFlips) {
                greedyMoves.add(currentPos);
            }



        }
        }
        return null;
    }
}
