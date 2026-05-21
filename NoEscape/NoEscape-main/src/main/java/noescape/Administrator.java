package noescape;
public class Administrator {

    private String  message;
    private boolean isOverridden;

    public Administrator() {
        this.message = " ";
        this.isOverridden = false;
    }
    public void sendMessage(String message) {
        this.message = message;
    }
    public void resetGame() {
        this.isOverridden = false;
    }

    public String  getMessage(){ 
        return message; 
    }
    public boolean isOverridden(){ 
        return isOverridden; 
    }
}
