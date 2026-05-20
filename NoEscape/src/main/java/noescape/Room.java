package noescape;

public abstract class Room implements RoomBehavior {

    private String name;
    private boolean locked;
    private String puzzle;
    private String answer;
    private String clue;
    private String hint;

    protected String lastMessage = "";
    protected boolean solved = false;
    protected int attempts = 0;

    public Room(String name, boolean locked, String puzzle, String answer, String clue, String hint) {
        this.name = name;
        this.locked = locked;
        this.puzzle = puzzle;
        this.answer = answer;
        this.clue = clue;
        this.hint = hint;
    }

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

    public abstract String getRoomType();

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