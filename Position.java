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
    // Comparators
    /**
     * Compares two positions' columns
     *
     * @param other the position to compare to
     * @return true if the position is in a column to the right of the parameter position
     */
    public boolean isRightTo(Position other){
        return this.col() > other.col();
    }
    /**
     * Compares two positions' row
     *
     * @param other the position to compare to
     * @return true if the position is in a row under of the parameter position
     */
    public boolean isUnder(Position other){
        return this.row() > other.row();
    }
}

