package noescape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

    public void showRoom(Room room, int index, int total, Player player, String adminMessage) {
        window.showRoomScreen(room, index, total, player, adminMessage, new Room[]{room}, 0);
    }

    public void showRoom(Room room, int index, int total, Player player, String adminMessage, Room[] rooms, int currentIndex) {
        window.showRoomScreen(room, index, total, player, adminMessage, rooms, currentIndex);
    }

    public void showWin(Player player, int secondsLeft, String adminMessage) {
        window.showWinScreen(player, secondsLeft, adminMessage);
    }

    public void showLoop(Player player, String adminMessage) {
        window.showLoopScreen(player, adminMessage);
    }

    public void showFeedback(String msg, Color color) {
        window.showFeedback(msg, color);
    }

    public void updateTimer(int secondsLeft) {
        Color color;
        if (secondsLeft <= 10) color = GameWindow.COL_RED;
        else if(secondsLeft <= 30) color = GameWindow.COL_ORANGE;
        else color = GameWindow.COL_YELLOW;
        timerLabel.setForeground(color);
        timerLabel.setText("⏱  " + secondsLeft + "s  ");
    }

    public void setTimerText(String text, Color color) {
        timerLabel.setText(text);
        timerLabel.setForeground(color);
    }

    public void print(String t) {}
    public void clear() {}
    public void blank() {}
    public void showRoomMap(Room[] rooms, int i) {}
}