public class Position {
    private final int _row; //the row of this position
    private final int _col; // the col of this position

    public Position(int row, int col){
        this._row=row;
        this._col=col;
    }

    /**
     * get the row of the position
     *
     * @return the row of the position
     */
    public int row() {
        return this._row;
    }

    /**
     * get the col of the position
     *
     * @return the col of the position
     */
    public int col() {return this._col;}

    /**
     * indicate if this position equal to another position
     *
     * @param obj the othere position
     * @return true if the two position equal and false if they are not equal or if the other object is not of Position type
     */
    @Override
    public boolean equals(Object obj) {
        if (this.getClass()!=obj.getClass()){
            return false;
        }
        Position other_position=(Position) obj;
        return (this._row==other_position.row() && this._col==other_position.col());
    }

    /**
     * get a string that describe the Position
     *
     * @return string that describe the Position
     */
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

