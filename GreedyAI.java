import java.util.*;

public class GreedyAI extends AIPlayer{
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> moves = gameStatus.ValidMoves();
        List<Position> greedyMoves=new ArrayList<Position>(); //a list of all the greedy moves
        int mostFlips = 0;

        /* for (int i=0; i<moves.size();i++) {
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
        return new Move((GameLogic) gameStatus,disc,greedyPos); */

        Comparator<Position> comparatorByPosition= new Comparator<Position>() {
            @Override
            public int compare(Position o1, Position o2) {
                if (o1.col()>o2.col()){ // o1 is righter
                    return 1; // for o1
                } else if(o1.col()<o2.col()) { // o2 is righter
                    return -1; // for o2
                }else{ //same col
                    return Integer.compare(o1.row(), o2.row()); // return 1 if o1 below, -1 if o2 below and 0 if the same
                }
            }
        };
        for (Position currentPos : moves) {
            int numOfFlips = gameStatus.countFlips(currentPos);
            if (numOfFlips > mostFlips) {
                greedyMoves.clear(); //reset the greedy moves
                greedyMoves.add(currentPos);
                mostFlips = numOfFlips;
            }
            if (numOfFlips == mostFlips) {
                greedyMoves.add(currentPos);
            }
        }
        SimpleDisc disc = new SimpleDisc(this);
        Position greedyPos = Collections.max(greedyMoves,comparatorByPosition);
        return new Move((GameLogic) gameStatus,disc,greedyPos);
    }
}
