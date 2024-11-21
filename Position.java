public class Position {
    private final int _row;
    private final int _col;

    public Position(int row, int col){
        this._row=row;
        this._col=col;
    }

    public int row() {
        return this._row;
    }
    public int col() {return this._col;}

    @Override
    public boolean equals(Object obj) {
        Position other_position=(Position) obj;
        if (this.getClass()!=obj.getClass()){
            return false;
        }
        return (this._row==other_position.row() && this._col==other_position.col());
    }

    @Override
    public String toString(){
        return String.format("(%d, %d)",this._row,this._col);
    }
}
