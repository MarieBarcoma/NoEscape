package noescape;

public interface RoomBehavior {
    String getName();
    boolean isLocked();
    boolean enter(Player player);
    void showPuzzle();
    void showClue();
    void showHint();
    void checkAnswer(String answer);
}