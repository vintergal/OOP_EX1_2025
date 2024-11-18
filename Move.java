import java.util.List;
import java.util.Stack;

public class Move {
    private Disc _disc;
    private Position _position;
    private List<Position> flippedDisksPositions;
    private Disc[][] ptr_board;
    private GameLogic game_logic;
    public Move(GameLogic game_logic,Disc _disc, Position _position){
        this.game_logic=game_logic;
        this.ptr_board=game_logic.getBoardPtr();
        this._disc=_disc;
        this._position=_position;
        this.flippedDisksPositions=game_logic.getDisksToFlipPositions(_position,false);





//        int num_of_rows=game_logic.getBoardSize();
//        int num_of_cols=game_logic.getBoardSize();
//
//        this.game_logic=game_logic;
//
//        this.previous_state=new Disc[num_of_rows][num_of_cols];
//        for (int row=0;row<num_of_rows;row++){
//            for (int col=0;col<num_of_cols;col++){
//                Disc disc_to_copy=game_logic.getDiscAtPosition(new Position(row,col));
//                if (disc_to_copy!=null){
//                    switch (disc_to_copy.getType()){
//                        case "⬤":
//                            this.previous_state[row][col]=new SimpleDisc(disc_to_copy.getOwner());
//                            break;
//                        case "⭕":
//                            this.previous_state[row][col]=new UnflippableDisc(disc_to_copy.getOwner());
//                            break;
//                        case "💣":
//                            this.previous_state[row][col]=new BombDisc(disc_to_copy.getOwner());
//                            break;
//                    }
//                }
//            }
//        }


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
        //TODO
        this.ptr_board[_position.row()][_position.col()]=null;
        for (Position pos : this.flippedDisksPositions){
            this.game_logic.flipDisc(pos);
        }
    }


}
