import java.util.ArrayList;
import java.util.List;

public class GreedyAI extends AIPlayer{
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> moves = gameStatus.ValidMoves();
        List<Position> greedyMoves=new ArrayList<Position>(); //a list of all the greedy moves
        int mostFlips = 0;
        for (int i=0; i<moves.size();i++) {
            Position currentPos = new Position(moves.get(i).row(),moves.get(i).col());
            int numOfFlips = gameStatus.countFlips(currentPos);
            if(numOfFlips>mostFlips) {
                greedyMoves.clear(); //reset the greedy moves
                greedyMoves.add(currentPos);
                mostFlips = numOfFlips;
            }
            if(numOfFlips==mostFlips) {
                greedyMoves.add(currentPos);
            }
        }
        Position greedyPos = new Position (greedyMoves.get(0).row(),greedyMoves.get(0).col());
        for(int i=1;i<greedyMoves.size();i++) {
            if(greedyMoves.get(i).isRightTo(greedyPos)) {
                greedyPos = greedyMoves.get(i);
            }
            if(greedyMoves.get(i).col()==greedyPos.col() && greedyMoves.get(i).isUnder(greedyPos)){
                greedyPos = greedyMoves.get(i);
            }
        }
        SimpleDisc disc = new SimpleDisc(this);
        return new Move((GameLogic) gameStatus,disc,greedyPos);
    }
}
