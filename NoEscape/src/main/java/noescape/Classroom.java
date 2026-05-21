package noescape;

/**
 * CLASSROOM ROOM: Room 1 - the starting room, always unlocked.
 *
 * OOP:
 *   Encapsulation - private fields, public methods
 *   Abstraction   - implements RoomBehavior interface
 */
public class Classroom implements RoomBehavior {

    // Private fields (Encapsulation)
    private String name;
    private boolean locked;
    private String puzzle;
    private String answer;
    private String clue;
    private String hint;
    private String lastMessage = "";
    private boolean solved = false;
    private int attempts = 0;

    // Constructor
    public Classroom(String name, boolean locked, String puzzle, String answer, String clue, String hint) {
        this.name = name;
        this.locked = locked;
        this.puzzle = puzzle;
        this.answer = answer;
        this.clue = clue;
        this.hint = hint;
    }

    // RoomBehavior implementations
    @Override
    public String getName() { 
        return name; 
    }

    @Override
    public boolean isLocked() { 
        return locked; 
    }

    public void unlock() { 
        this.locked = false; 
    }

    @Override
    public boolean enter(Player player) {
        if (locked) {
            lastMessage = "Room is locked! Solve the previous room first.";
            return false;
        }
        lastMessage = "You entered: " + name;
        return true;
    }

    @Override
    public void showPuzzle() {
        lastMessage = "PUZZLE: " + puzzle;
    }

    @Override
    public void showClue() {
        lastMessage = "CLUE: " + clue;
    }

    @Override
    public void showHint() {
        lastMessage = "HINT: " + hint;
    }

    @Override
    public void checkAnswer(String userAnswer) {
        if (userAnswer.trim().equalsIgnoreCase(answer)) {
            solved = true;
            lastMessage = "Correct! You cleared: " + name;
        } else {
            attempts++;
            lastMessage = "Wrong answer. Attempt " + attempts + " used.";
        }
    }

    public String  getRoomType() { 
        return "Classroom"; 
    }
    public boolean isSolved() {
        return solved;      
    }
    public int getAttempts() {
        return attempts;
    }
    public String getLastMessage() {
        return lastMessage;
    }
    public String getPuzzle() {
        return puzzle;
    }
}