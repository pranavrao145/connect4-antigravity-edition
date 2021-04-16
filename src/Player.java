public class Player {
    private int highestLevel;
    private boolean removedAntiGravityPiece;
    private final char colorName;
    private final int colorNum;

    public Player(char colorName, int colorNum) {
        this.highestLevel = 7;
        this.removedAntiGravityPiece = false;
        this.colorName = colorName;
        this.colorNum = colorNum;
    }

    public int getHighestLevel() {
        return highestLevel;
    }

    public void setHighestLevel(int highestLevel) {
        this.highestLevel = highestLevel;
    }

    public boolean isRemovedAntiGravityPiece() {
        return removedAntiGravityPiece;
    }

    public void setRemovedAntiGravityPiece(boolean removedAntiGravityPiece) {
        this.removedAntiGravityPiece = removedAntiGravityPiece;
    }

    public char getColorName() {
        return colorName;
    }

    public int getColorNum() {
        return colorNum;
    }
}
