package noescape;

public class Administrator {

    private String message;
    private boolean isOverridden;

    public Administrator() {
        this.message = " ";
        this.isOverridden = false;
    }

    public void sendMessage(String msg) {
        this.message = msg;
    }

    public void resetGame() {
        this.isOverridden = false;
    }

    public String  getMessage() {
        return message;      
    }
    
    public boolean isOverridden() {
        return isOverridden; 
    }

    public void overrideLoop() {
        this.isOverridden = true;
        this.message = "LOOP OVERRIDE: You are free.";
    }
}