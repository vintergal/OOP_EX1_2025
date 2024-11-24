import java.util.ArrayList;
import java.util.List;

public class GreedyAI extends AIPlayer{
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> moves = this.ValidMoves();
        List<Position> greedyMoves=new ArrayList<Position>(); //a list of all the greedy moves
        int mostFlips = 0;
        for (int i=0; i<this.ValidMoves().size();i++) {
            Position currentPos = new Position(ValidMoves().get(i).row,ValidMoves().get(i).col());
            int numOfMoves = greedyMoves.get(i).countFlips(currentPos); //GameLogic methode
            if(numOfMoves>mostFlips) {
                greedyMoves.clear(); //reset the greedy moves
                greedyMoves.add(currentPos);
                mostFlips = numOfMoves;
            }
            if(numOfMoves==mostFlips) {
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



        return null;
    }
}
