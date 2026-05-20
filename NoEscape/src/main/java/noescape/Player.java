package noescape;

public class Player {
    // Private fields
    private String name;
    private String course;
    private int progress; 
    private int bonusSeconds;
    private int maxAttempts;
    
    public Player(String name, String course) {
        this.name = name;
        this.course = course;
        this.progress = 0;
        
        if(course.contains("Computer Science")) {
            this.bonusSeconds = 20;
            this.maxAttempts = 3;
        }else if(course.contains("Nursing")) {
            this.bonusSeconds = 0;
            this.maxAttempts = 5;
        }else{
            this.bonusSeconds = 0;
            this.maxAttempts = 3;
        }
    }

    public void chooseCharacter(String course) {
        this.course = course;
    }

    public String getName(){
        return name;
    }
    public String getCourse() { 
        return course;
    }
    public int getProgress() {
        return progress;     
    }
    public int getBonusSeconds() {
        return bonusSeconds; 
    }
    public int getMaxAttempts() {
        return maxAttempts;
    }

    public void setProgress(int progress) {
        this.progress = progress; 
    }

    public void reset() {
        this.progress = 0;
    }
}