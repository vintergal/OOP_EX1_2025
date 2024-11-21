public class SimpleDisc implements Disc{
    private Player owner;
    public static final String Symbol="⬤";
    public SimpleDisc(Player player){
        this.owner=player;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(Player player) {
        this.owner=player;
    }

    @Override
    public String getType() {
        return SimpleDisc.Symbol;
    }
}
