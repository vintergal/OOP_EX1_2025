public class Position {
    private int _row;
    private int _col;

    public Position(int row, int col){
        this._row=row;
        this._col=col;
    }

    public int row() {
        return this._row;
    }
    public int col() {
        return this._col;
    }
}
