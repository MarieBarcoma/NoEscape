package noescape;

/**
 * ROOM BEHAVIOR: Interface that every room must implement.
 * Replaces the old IRoom interface and abstract Room class.
 *
 * OOP: Abstraction - defines WHAT a room does, not HOW.
 */
public interface RoomBehavior {
    String getName();
    boolean isLocked();
    void unlock();

    // Actions
    boolean enter(Player player);
    void showPuzzle();
    void showClue();
    void showHint();
    void checkAnswer(String answer);

    // State getters
    boolean isSolved();
    int getAttempts();
    String getLastMessage();
    String getPuzzle();
    String getRoomType();
}