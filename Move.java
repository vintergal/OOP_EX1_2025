public class Move {
    private Disc _disc;
    private Position _position;
    private Disc[][] previous_state;
    private GameLogic game_logic;
    public Move(GameLogic game_logic){
        int num_of_rows=game_logic.getBoardSize();
        int num_of_cols=game_logic.getBoardSize();

        this.game_logic=game_logic;

        this.previous_state=new Disc[num_of_rows][num_of_cols];
        for (int row=0;row<num_of_rows;row++){
            for (int col=0;col<num_of_cols;col++){
                Disc disc_to_copy=game_logic.getDiscAtPosition(new Position(row,col));
                if (disc_to_copy!=null){
                    switch (disc_to_copy.getType()){
                        case "⬤":
                            this.previous_state[row][col]=new SimpleDisc(disc_to_copy.getOwner());
                            break;
                        case "⭕":
                            this.previous_state[row][col]=new UnflippableDisc(disc_to_copy.getOwner());
                            break;
                        case "💣":
                            this.previous_state[row][col]=new BombDisc(disc_to_copy.getOwner());
                            break;
                    }
                }
            }
        }

    }
    public Disc disc(){
        return this._disc;
    }
    public Position position(){
        return this._position;
    }
    public void undo(){
        //TODO
        this.game_logic.setBoard(this.previous_state);
    }
}
