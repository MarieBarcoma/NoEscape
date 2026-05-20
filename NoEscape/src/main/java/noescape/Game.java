package noescape;
 
public class Game {

    private GameWindow window;
    private GameDisplay display;
    private Player player;
    private Room[] rooms;
    private int currentRoomIndex;
    private Administrator admin;
    private TimerSystem timer;
    private GameState state;
    private EnvLoader env;

    public Game() {
        env = new EnvLoader(".env");
        admin = new Administrator();
        currentRoomIndex = 0;
        state = GameState.ENTER_NAME;

        window = new GameWindow();
        display = new GameDisplay(window.getDisplayArea(), window.getTimerLabel(), window);

        window.attachListeners(e->processPlayerInput(), e->onCluePressed(),e->onHintPressed());

        showEnterName();
        startGameLoop();
    }

    private void showEnterName() {
        state = GameState.ENTER_NAME;
        display.showEnterName();
        display.setTimerText("", GameWindow.COL_DIM);
        window.setInputEnabled(false);
        window.getSubmitButton().setEnabled(true);
        window.getInputField().setEnabled(true);
        window.getInputField().setText("");
        window.getInputField().requestFocus();
    }

    private void showChooseCourse() {
        state = GameState.CHOOSE_COURSE;
        display.showChooseCourse(player.getName(),e->selectCourse("Computer Science"),e->selectCourse("Nursing"));
        window.setInputEnabled(false);
        window.getSubmitButton().setEnabled(true);
        window.getInputField().setEnabled(true);
        window.getInputField().setText("");
        window.getInputField().requestFocus();
    }

    private void selectCourse(String course) {
        player = new Player(player.getName(), course);
        showSplash();
    }

    private void showSplash() {
        state = GameState.SPLASH;
        SplashPanel.showSplashDialog(player.getName(), player.getCourse());
        display.showSplash(player);
        display.setTimerText("", GameWindow.COL_DIM);
        window.setInputEnabled(false);
        window.getSubmitButton().setEnabled(true);
        window.getInputField().setEnabled(true);
        window.getInputField().setText("");
        window.getInputField().requestFocus();
    }

    private void startGameLoop() {
        javax.swing.Timer gameLoop = new javax.swing.Timer(1000, e -> onTick());
        gameLoop.start();
    }

    private void onTick() {
        if(state != GameState.PLAYING) return;
        display.updateTimer(timer.getSecondsRemaining());
        if(timer.hasTimeExpired()) triggerLoop();
    }

    private void startGame() {
        rooms = RoomFactory.createRooms(env, player.getCourse());
        int total = env.getInt("TIMER_SECONDS", 120) + player.getBonusSeconds();
        timer = new TimerSystem(total);
        state = GameState.PLAYING;
        timer.start();
        admin.sendMessage("The loop has begun. Find a way out, " + player.getName() + ".");
        loadRoom(0);
    }

    private void loadRoom(int index) {
        currentRoomIndex = index;
        Room room = rooms[index];
        display.showRoom(room, index, rooms.length, player, admin.getMessage(), rooms, index);
        window.setInputEnabled(true);
    }

    private void processPlayerInput() {
        String input = window.getInputField().getText().trim();
        window.getInputField().setText("");
        if(input.isEmpty()) return;

        switch (state) {
            case ENTER_NAME->handleEnterName(input);
            case CHOOSE_COURSE->handleChooseCourse(input);
            case SPLASH->handleSplashInput(input);
            case WIN, LOOP->handleEndInput(input);
            case PLAYING->handlePlayingInput(input);
        }
    }

    private void handleEnterName(String input) {
        player = new Player(input, "Computer Science");
        showChooseCourse();
    }

    private void handleChooseCourse(String input) {
        if(input.equals("1")) selectCourse("Computer Science");
        else if(input.equals("2")) selectCourse("Nursing");
        else display.showFeedback("Type  1  or  2  to choose your course.", GameWindow.COL_YELLOW);
    }

    private void handleSplashInput(String input) {
        if(input.equalsIgnoreCase("start")) startGame();
        else display.showFeedback("Type  START  to begin.", GameWindow.COL_YELLOW);
    }

    private void handleEndInput(String input) {
        if (input.equalsIgnoreCase("restart")) restartGame();
        else display.showFeedback("Type  RESTART  to play again.", GameWindow.COL_YELLOW);
    }

    private void handlePlayingInput(String input) {
        // Navigate rooms by number
        if (input.matches("[1-4]")) {
            int idx = Integer.parseInt(input) - 1;
            if (!rooms[idx].isLocked() || rooms[idx].isSolved()) loadRoom(idx);
            else display.showFeedback("Room " + input + " is still locked.", GameWindow.COL_RED);
            return;
        }

        Room room = rooms[currentRoomIndex];

        if(room.isSolved()) {
            display.showFeedback("Already solved! Move to the next room.", GameWindow.COL_GREEN);
            return;
        }
        if(room.isLocked()) {
            display.showFeedback("This room is locked.", GameWindow.COL_RED);
            return;
        }
        if(room.getAttempts() >= player.getMaxAttempts()) {
            display.showFeedback("No more attempts. Press Hint for help.", GameWindow.COL_RED);
            return;
        }
        room.checkAnswer(input);

        if(room.isSolved()) {
            display.showFeedback("✓  Correct!  " + room.getLastMessage(), GameWindow.COL_GREEN);
            onRoomSolved();
        }else{
            display.showFeedback("✗  " + room.getLastMessage() + "  (" + room.getAttempts() + "/" + player.getMaxAttempts() + ")",GameWindow.COL_RED);
        }
    }

    private void onRoomSolved() {
        if (currentRoomIndex + 1 < rooms.length) {
            rooms[currentRoomIndex + 1].unlock();
            admin.sendMessage("Unlocked: " + rooms[currentRoomIndex + 1].getName());
        }
        player.setProgress(player.getProgress() + 1);
        if(allRoomsSolved()) {
            triggerWin();
        }else{
            // Brief pause then load next room
            javax.swing.Timer t = new javax.swing.Timer(900, e -> loadRoom(currentRoomIndex + 1));
            t.setRepeats(false);
            t.start();
        }
    }

    private void onCluePressed() {
        if(state != GameState.PLAYING) return;
        Room room = rooms[currentRoomIndex];
        room.showClue();
        display.showFeedback("🔍  " + room.getLastMessage(), GameWindow.COL_CYAN);
    }

    private void onHintPressed() {
        if(state != GameState.PLAYING) return;
        Room room = rooms[currentRoomIndex];
        room.showHint();
        display.showFeedback("💡  " + room.getLastMessage(), GameWindow.COL_YELLOW);
    }

    private void triggerWin() {
        timer.stop();
        admin.overrideLoop();
        state = GameState.WIN;
        display.showWin(player, timer.getSecondsRemaining(), admin.getMessage());
        display.setTimerText("ESCAPED!", GameWindow.COL_GREEN);
        window.setInputEnabled(false);
        window.getSubmitButton().setEnabled(true);
        window.getInputField().setEnabled(true);
    }

    private void triggerLoop() {
        timer.stop();
        admin.sendMessage("The loop resets. Try again.");
        state = GameState.LOOP;
        display.showLoop(player, admin.getMessage());
        display.setTimerText("FAILED!", GameWindow.COL_RED);
        window.setInputEnabled(false);
        window.getSubmitButton().setEnabled(true);
        window.getInputField().setEnabled(true);
    }

    private void restartGame() {
        admin.resetGame();
        currentRoomIndex = 0;
        player.reset();
        showEnterName();
    }

    private boolean allRoomsSolved() {
        for (Room r : rooms) if (!r.isSolved()) return false;
        return true;
    }
}