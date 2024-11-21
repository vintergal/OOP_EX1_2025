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
    private Player getCurrentPlayer(){
        if (this.is_player1_turn){
            return player1;
        }
        else {
            return player2;
        }
    }
    private void addDisksToFlipByBombToList(Position bomb_placed_at,List<Position> listToAddTo){
        List<Position> otherPlayerDiscs=new ArrayList<Position>();
        for (int vec_y = -1; vec_y <=1 ; vec_y++) {
            for (int vec_x = -1; vec_x <=1 ; vec_x++) {
                if (vec_y==0 && vec_x==0){
                    continue; // the bomb itself position
                }
                Position pos_to_check=new Position(bomb_placed_at.row()+vec_y,bomb_placed_at.col()+vec_x);
                Disc disc_to_check=this.getDiscAtPosition(pos_to_check);
                if (disc_to_check==null){
                    //went out of bound or to empty
                    continue;
                }

                if (disc_to_check.getOwner().isPlayerOne()!=this.getCurrentPlayer().isPlayerOne()
                        && !listToAddTo.contains(pos_to_check)
                         && (!disc_to_check.getType().equals(UnflippableDisc.Symbol))){
                    listToAddTo.add(pos_to_check);
                    if (disc_to_check.getType().equals(BombDisc.Symbol)){
                        this.addDisksToFlipByBombToList(pos_to_check,listToAddTo);
                    }
                }

            }
        }
    }
    public List<Position> getDisksToFlipPositions(Position disc_placed_at){
        List<Position> otherPlayerDiscs=new ArrayList<Position>();
        Player thisPlayer,otherPlayer;
        if (this.is_player1_turn){
            thisPlayer=player1;
        }
        else {
            thisPlayer=player2;
        }

        for (int vec_y = -1; vec_y <=1 ; vec_y++) {
            for (int vec_x = -1; vec_x <=1 ; vec_x++) {
                boolean is_other_player_discs_between=false;
                if (vec_y==0 && vec_x==0){
                    continue; // because in this case it will not move in any direction
                }
                List<Position> otherPlayerInThisDirection=new ArrayList<Position>();
                for (int row_to_check=disc_placed_at.row()+vec_y, col_to_check=disc_placed_at.col()+vec_x;;
                     row_to_check+=vec_y,col_to_check+=vec_x){

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
                        if (disc_to_check.getOwner().isPlayerOne()!=this.getCurrentPlayer().isPlayerOne()
                                && (!disc_to_check.getType().equals(UnflippableDisc.Symbol))){
                            otherPlayerInThisDirection.add(pos_to_check);
                        }

                    }
                }
            }
        }
        for (int i=0; i<otherPlayerDiscs.size();i++){
            Position pos= otherPlayerDiscs.get(i);
            if (this.getDiscAtPosition(pos).getType().equals(BombDisc.Symbol)){
                addDisksToFlipByBombToList(pos,otherPlayerDiscs);
            }
        }
        return otherPlayerDiscs;
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (this.isValidMove(a)) {
            Player disc_owner=disc.getOwner();
            String disc_type=disc.getType();
            if (disc_type.equals(BombDisc.Symbol)){
                if (disc_owner.getNumber_of_bombs()>0) {
                    disc_owner.reduce_bomb();
                }else {
                    return false;
                }

            } else if (disc_type.equals(UnflippableDisc.Symbol)){
                if (disc_owner.getNumber_of_unflippedable()>0) {
                    disc_owner.reduce_unflippedable();
                }else {
                    return false;
                }
            }
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
                diskToFlip.setOwner(this.player2);
            }else{
                diskToFlip.setOwner(this.player1);
            }
        }
    }

    public Disc[][] getBoardPtr(){
        return this.board;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
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
        // TODO check what should it be
        return 8;
    }

    private boolean isValidMove(Position position){
        if (this.getDiscAtPosition(position)!=null){ // if the squere already has disc, new disc cant be placed there
            return false;
        }
        return countFlips(position)>0;
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
        return this.getDisksToFlipPositions(a).size();
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
        for (Player player : new Player[]{player1, player2}) {
            player.reset_bombs_and_unflippedable();
        }
    }
    private void increaseSpecialDisc(Disc disc,Player player){
        if (!disc.getType().equals(SimpleDisc.Symbol)){
            int special_before_undo;
            if (disc.getType().equals(BombDisc.Symbol)){
                int todo=0;
            }
        }
    }
    private void changeTurn(){
        this.is_player1_turn=!this.is_player1_turn;
    }

    @Override
    public void undoLastMove() {
        if (!this.moves.isEmpty()) {
            Move lastMove = this.moves.pop();
            lastMove.undo();
            if (lastMove.disc().getType().equals(BombDisc.Symbol)){
                lastMove.disc().getOwner().increase_bomb();
            } else if (lastMove.disc().getType().equals(UnflippableDisc.Symbol)){
                lastMove.disc().getOwner().increase_unflippedable();
            }
            this.changeTurn();
        }else {
           System.out.println("\tNo previous move available to undo.");
        }
        System.out.println();
    }
}
