import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic{

    private Disc[][] board;
    private Player player1;
    private Player player2;
    private boolean is_player1_turn;
    Stack<Move> moves=new Stack<Move>();
    public GameLogic(){
        this.is_player1_turn=true;
        int size=this.getBoardSize();
        this.board=new Disc[size][size];


    }
    @Override
    public boolean locate_disc(Position a, Disc disc) {

        //TODO
        // now only places without checking if valid and without flips
        //consider what to do with that it isnt clear that you need to create the moves before the disc placing
        moves.add(new Move(this));
        this.board[a.row()][a.col()]=disc;

        this.is_player1_turn=!this.is_player1_turn;
        return true;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {

        //TODO
        int size=this.getBoardSize();
        int col=position.col();
        int row=position.row();
        if (col<0 || row<0 || col>=size || row >=size){
            return null;
        }else{
            return this.board[row][col];
        }
    }

    @Override
    public int getBoardSize() {

        //TODO
        return 8;
    }

    @Override
    public List<Position> ValidMoves() {
        //TODO
        return new ArrayList<Position>();
    }

    @Override
    public int countFlips(Position a) {

        //TODO
        return 0;
    }

    @Override
    public Player getFirstPlayer() {

        return this.player1;
    }


    @Override
    public Player getSecondPlayer() {
        return this.player2;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        this.player1=player1;
        this.player2=player2;
    }

    @Override
    public boolean isFirstPlayerTurn() {
        return this.is_player1_turn;
    }

    @Override
    public boolean isGameFinished() {
        //TODO
        return false;
    }

    @Override
    public void reset() {
        //TODO

        int size=this.getBoardSize();
        this.board=new Disc[size][size];
        this.board[size/2][size/2]=new SimpleDisc(player1);
        this.board[size/2 -1][size/2]=new SimpleDisc(player2);
        this.board[size/2][size/2-1]=new SimpleDisc(player2);
        this.board[size/2-1][size/2-1]=new SimpleDisc(player1);
    }

    @Override
    public void undoLastMove() {
        //TODO
        if (!this.moves.isEmpty()) {
            this.moves.pop().undo();
            this.is_player1_turn=!this.is_player1_turn;
        }


    }

    public void setBoard(Disc [][]boardStateToSet){
        this.board=boardStateToSet;
    }
}
