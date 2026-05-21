package noescape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * GAME DISPLAY: Delegates all screen rendering to GameWindow's panel methods.
 * Keeps Game.java clean — it calls display.showX(), this calls window.showXScreen().
 *
 * OOP: Encapsulation - Game never talks to GameWindow directly for screens.
 */
public class GameDisplay {

    private JLabel timerLabel;
    private GameWindow window;

    public GameDisplay(Object ignored, JLabel timerLabel, GameWindow window) {
        this.timerLabel = timerLabel;
        this.window = window;
    }

    public void showEnterName() {
        window.showEnterNameScreen();
    }

    public void showChooseCourse(String playerName, ActionListener csAction, ActionListener nursingAction) {
        window.showChooseCourseScreen(playerName, csAction, nursingAction);
    }

    public void showSplash(Player player) {
        window.showSplashScreen(player);
    }

    public void showRoom(RoomBehavior room, int index, int total,
                         Player player, String adminMessage) {
        // Room map rendered inside showRoomScreen — pass placeholder rooms array
        window.showRoomScreen(room, index, total, player, adminMessage,
                              new Room[]{room}, 0);
    }

    // Full version used by Game (with rooms array for the map)
    public void showRoom(RoomBehavior room, int index, int total,
                         Player player, String adminMessage,
                         Room[] rooms, int currentIndex) {
        window.showRoomScreen(room, index, total, player, adminMessage, rooms, currentIndex);
    }

    public void showWin(Player player, int secondsLeft, String adminMessage) {
        window.showWinScreen(player, secondsLeft, adminMessage);
    }

    public void showLoop(Player player, String adminMessage) {
        window.showLoopScreen(player, adminMessage);
    }

    // ── Feedback (answer result, hints, clues) ────────────────────────────────

    public void showFeedback(String msg, Color color) {
        window.showFeedback(msg, color);
    }

    // ── Timer ─────────────────────────────────────────────────────────────────

    public void updateTimer(int secondsLeft) {
        Color color;
        if      (secondsLeft <= 10) color = GameWindow.COL_RED;
        else if (secondsLeft <= 30) color = GameWindow.COL_ORANGE;
        else                        color = GameWindow.COL_YELLOW;
        timerLabel.setForeground(color);
        timerLabel.setText("⏱  " + secondsLeft + "s  ");
    }

    public void setTimerText(String text, Color color) {
        timerLabel.setText(text);
        timerLabel.setForeground(color);
    }

    // ── No-op stubs kept so Game.java compiles ────────────────────────────────
    public void print(String t)  {}
    public void clear()          {}
    public void blank()          {}
    public void showRoomMap(RoomBehavior[] rooms, int i) {}
}