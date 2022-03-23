package chess.domain.position;

public class Position {
    private Rank rank;
    private File file;

    public Position(String text){
        this.rank = Rank.of(String.valueOf(text.charAt(0)));
        this.file = File.of(String.valueOf(text.charAt(1)));
    }
}
