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
    public List<Position> getDisksToFlipPositions(Position disc_placed_at, boolean include_unflippable){
        List<Position> otherPlayerDiscs=new ArrayList<Position>();
        Player thisPlayer,otherPlayer;
        if (this.is_player1_turn){
            thisPlayer=player1;
            otherPlayer=player2;
        }
        else {
            thisPlayer=player2;
            otherPlayer=player1;
        }

        for (int i = -1; i <=1 ; i++) {
            for (int j = -1; j <=1 ; j++) {
                boolean is_other_player_discs_between=false;
                int row_to_check=disc_placed_at.row()+i;
                int col_to_check=disc_placed_at.col()+j;
                if (i==0 && j==0){
                    continue; // because in this case it will not move in any direction
                }
                List<Position> otherPlayerInThisDirection=new ArrayList<Position>();
                while (true){

                    Position pos_to_check=new Position(row_to_check,col_to_check);
                    Disc disc_to_check=this.getDiscAtPosition(pos_to_check);
                    if (disc_to_check==null){
                        //went out of bound or to empty before reaching current player disk-search in other directions
                        break;
                    }
                    if (disc_to_check.getOwner().isPlayerOne()==thisPlayer.isPlayerOne()) {
                        if (is_other_player_discs_between) {
                            //there is another disk of the current player in the other side and
                            //other player disks between the two disks - add disks to primary lists
                            // and check other directions
                            otherPlayerDiscs.addAll(otherPlayerInThisDirection);
                            break;
                        } else {
                            // another current player disk and no other player disks between-search in other directions
                            break;
                        }
                    }else{
                        //in this location there is other player disk
                        is_other_player_discs_between=true;
                        if (include_unflippable || !disc_to_check.getType().equals("⭕")) {
                            otherPlayerInThisDirection.add(pos_to_check);
                        }
                    }
                    row_to_check+=i;
                    col_to_check+=j;
                }
            }
        }
        return otherPlayerDiscs;
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {

        //TODO
        // now only places without checking if valid and without flips
        //consider what to do with that it isnt clear that you need to create the moves before the disc placing
        if (this.isValidMove(a)) {
            Move move = new Move(this, disc, a);
            moves.add(move);
            move.executeMove();
            this.is_player1_turn = !this.is_player1_turn;
            return true;
        } else {
            return  false;
        }
    }

    public void flipDisc(Position pos_of_disc){
        Disc diskToFlip=this.getDiscAtPosition(pos_of_disc);
        if (diskToFlip!=null) //second test for bug prevention
        {
            if (diskToFlip.getOwner().isPlayerOne){
                diskToFlip.setOwner(player2);
            }else{
                diskToFlip.setOwner(player1);
            }
        }
    }

    public Disc[][] getBoardPtr(){
        return this.board;
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

    private boolean isValidMove(Position position){
        if (this.getDiscAtPosition(position)!=null){
            return false;
        }
        return !this.getDisksToFlipPositions(position,true).isEmpty();
    }

    @Override
    public List<Position> ValidMoves() {
        //TODO
        List<Position> validMoves=new ArrayList<Position>();
        for (int row = 0; row <this.getBoardSize() ; row++) {
            for (int col = 0; col <this.getBoardSize() ; col++) {
                Position pos =new Position(row,col);
                if (this.isValidMove(pos)){
                    validMoves.add(pos);
                }
            }
        }
        return validMoves;
    }

    @Override
    public int countFlips(Position a) {

        //TODO
        //for now without bombs
        return this.getDisksToFlipPositions(a,false).size();
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
        return this.ValidMoves().isEmpty();
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
        this.moves= new Stack<Move>();
    }

    @Override
    public void undoLastMove() {
        //TODO
        if (!this.moves.isEmpty()) {
            this.moves.pop().undo();
            this.is_player1_turn=!this.is_player1_turn;
        }
    }
}
