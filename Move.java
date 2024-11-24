import java.util.List;


public class Move {
    private final Disc _disc;
    private final Position _position;
    private final List<Position> flippedDisksPositions;
    private final Disc[][] ptr_board;
    private final GameLogic game_logic;
    public Move(GameLogic game_logic,Disc _disc, Position _position){
        this.game_logic=game_logic;
        this.ptr_board=game_logic.getBoardPtr();
        this._disc=_disc;
        this._position=_position;
        this.flippedDisksPositions=game_logic.getDisksToFlipPositions(_position);
    }
    public void executeMove(){
        int player_num=(this._disc.getOwner().isPlayerOne?1:2);
        this.ptr_board[_position.row()][_position.col()]=_disc;
        System.out.printf("Player %d placed a %s in %s\n",player_num,this._disc.getType(),this._position.toString());
        for (Position pos : this.flippedDisksPositions){
            this.game_logic.flipDisc(pos);
            System.out.printf("Player %d flipped the %s in %s\n",player_num,game_logic.getDiscAtPosition(pos).getType(),pos.toString());
        }
        System.out.println();
    }

    public Disc disc(){
        return this._disc;
    }
    public Position position(){
        return this._position;
    }
    public void undo(){
        System.out.print("Undoing last move :\n");
        this.ptr_board[_position.row()][_position.col()]=null;
        System.out.printf("\tUndo: removing %s from %s\n",this._disc.getType(),this._position.toString());
        for (Position pos : this.flippedDisksPositions){
            this.game_logic.flipDisc(pos);
            System.out.printf("\tUndo: flipping back %s in %s\n",game_logic.getDiscAtPosition(pos).getType(),pos.toString());
        }
    }
}
