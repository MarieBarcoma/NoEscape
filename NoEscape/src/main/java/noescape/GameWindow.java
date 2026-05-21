package noescape;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame {

    public static final Color BG_DARK = new Color(13,  13,  23);
    public static final Color BG_CARD = new Color(22,  22,  40);
    public static final Color BG_INPUT = new Color(18,  18,  32);
    public static final Color COL_PURPLE = new Color(160,  80, 220);
    public static final Color COL_CYAN = new Color( 60, 200, 220);
    public static final Color COL_GREEN = new Color( 60, 200, 100);
    public static final Color COL_YELLOW = new Color(240, 200,  60);
    public static final Color COL_RED = new Color(220,  70,  70);
    public static final Color COL_ORANGE = new Color(230, 140,  50);
    public static final Color COL_TEXT = new Color(210, 210, 230);
    public static final Color COL_DIM = new Color(130, 120, 155);
    public static final Color COL_BORDER = new Color( 55,  45,  85);

    private JLabel timerLabel;
    private JLabel roomLabel;
    private JPanel cardArea;      // center — swapped per screen
    private JTextField inputField;
    private JButton submitButton;
    private JButton clueButton;
    private JButton hintButton;
    private ActionListener onCourseCS;
    private ActionListener onCourseNursing;

    public GameWindow() {
        buildWindow();
    }

    private void buildWindow() {
        setSize(860, 640);
        setMinimumSize(new Dimension(720, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_DARK);
        setLocationRelativeTo(null);

        add(buildHeaderPanel(), BorderLayout.NORTH);
        add(buildFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel buildHeaderPanel() {
        JPanel h = new JPanel(new BorderLayout());
        h.setBackground(new Color(8, 8, 18));
        h.setBorder(new MatteBorder(0, 0, 2, 0, COL_PURPLE));
        h.setPreferredSize(new Dimension(0, 54));

        roomLabel = new JLabel("", SwingConstants.CENTER);
        roomLabel.setFont(new Font("Consolas", Font.PLAIN, 13));
        roomLabel.setForeground(COL_DIM);

        timerLabel = new JLabel("", SwingConstants.RIGHT);
        timerLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        timerLabel.setForeground(COL_YELLOW);
        timerLabel.setBorder(new EmptyBorder(0, 0, 0, 18));

        h.add(roomLabel, BorderLayout.CENTER);
        h.add(timerLabel, BorderLayout.EAST);
        return h;
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout(8, 0));
        footer.setBackground(BG_DARK);
        footer.setBorder(new CompoundBorder(
            new MatteBorder(2, 0, 0, 0, COL_BORDER),
            new EmptyBorder(10, 28, 14, 28)
        ));

        inputField = new JTextField();
        inputField.setFont(new Font("Consolas", Font.PLAIN, 15));
        inputField.setBackground(BG_INPUT);
        inputField.setForeground(COL_TEXT);
        inputField.setCaretColor(COL_PURPLE);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COL_PURPLE, 1, true),
            new EmptyBorder(7, 14, 7, 14)
        ));

        submitButton = makeBtn("Submit", COL_GREEN);
        clueButton = makeBtn("Clue", COL_CYAN);
        hintButton = makeBtn("Hint", COL_YELLOW);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setBackground(BG_DARK);
        btns.add(clueButton);
        btns.add(hintButton);
        btns.add(submitButton);

        footer.add(inputField, BorderLayout.CENTER);
        footer.add(btns, BorderLayout.EAST);
        return footer;
    }

    public void showEnterNameScreen() {
        roomLabel.setText("");
        JPanel card = makeCard();
        card.setLayout(new GridBagLayout());

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        inner.add(makeTitleLabel("NO ESCAPE", COL_PURPLE, 36));
        inner.add(Box.createVerticalStrut(8));
        inner.add(makeCenteredLabel("An endless campus time-loop", COL_DIM, 14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(28));
        inner.add(makeDivider());
        inner.add(Box.createVerticalStrut(28));
        inner.add(makeCenteredLabel("You are a student trapped in a time loop.", COL_TEXT, 14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(6));
        inner.add(makeCenteredLabel("Solve puzzles in each room to break free!", COL_TEXT, 14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(32));
        inner.add(makeCenteredLabel("What is your name?", COL_YELLOW, 16, Font.BOLD));
        inner.add(Box.createVerticalStrut(10));
        inner.add(makeCenteredLabel("Type your name below and press  Submit.", COL_DIM, 13, Font.PLAIN));
    }

    public void showChooseCourseScreen(String playerName, ActionListener csAction, ActionListener nursingAction) {
        roomLabel.setText("Choose Your Course");
        JPanel card = makeCard();
        card.setLayout(new GridBagLayout());

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        inner.add(makeCenteredLabel("Welcome,  " + playerName + "!", COL_YELLOW, 20, Font.BOLD));
        inner.add(Box.createVerticalStrut(24));
        inner.add(makeCenteredLabel("Choose your course:", COL_TEXT, 14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(20));

        // CS button
        JButton csBtn = makeBigCourseBtn(
            "💻  Computer Science",
            "+20 bonus seconds  ·  3 attempts per room",
            COL_CYAN, csAction
        );
        inner.add(csBtn);
        inner.add(Box.createVerticalStrut(14));

        // Nursing button
        JButton nurseBtn = makeBigCourseBtn(
            "🏥  Nursing",
            "No time bonus ·  5 attempts per room",
            new Color(255, 130, 180), nursingAction
        );
        inner.add(nurseBtn);
        inner.add(Box.createVerticalStrut(20));
        inner.add(makeCenteredLabel("Or type  1  or  2  and press Submit.", COL_DIM, 12, Font.PLAIN));
    }

    public void showSplashScreen(Player player) {
        roomLabel.setText("Ready to Play");
        JPanel card = makeCard();
        card.setLayout(new GridBagLayout());

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        inner.add(makeTitleLabel("NO ESCAPE", COL_PURPLE, 34));
        inner.add(Box.createVerticalStrut(24));
        inner.add(makeDivider());
        inner.add(Box.createVerticalStrut(18));
        inner.add(makeCenteredLabel("Player :  " + player.getName(), COL_TEXT, 15, Font.PLAIN));
        inner.add(Box.createVerticalStrut(6));
        inner.add(makeCenteredLabel("Course :  " + player.getCourse(), COL_CYAN, 15, Font.PLAIN));
        inner.add(Box.createVerticalStrut(6));
        inner.add(makeCenteredLabel("Attempts:  " + player.getMaxAttempts() + " per room", COL_TEXT, 14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(6));
        inner.add(makeCenteredLabel("Bonus :  +" + player.getBonusSeconds() + " seconds", COL_GREEN, 14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(26));
        inner.add(makeDivider());
        inner.add(Box.createVerticalStrut(22));

        JButton startBtn = makeBigCourseBtn(
            "▶   START GAME",
            "Begin the time loop",
            COL_GREEN,
            e -> {
                inputField.setText("start");
                submitButton.doClick();
            }
        );
        startBtn.setMaximumSize(new Dimension(320, 70));
        inner.add(startBtn);
    }

    public void showRoomScreen(Room room, int index, int total, Player player, String adminMsg, Room[] rooms, int currentIndex) {
        roomLabel.setText("Room " + (index + 1) + " of " + total + "  —  " + room.getName());

        JPanel card = makeCard();
        card.setLayout(new BorderLayout(0, 14));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        top.add(makeTitleLabel(room.getName(), COL_PURPLE, 20));
        top.add(Box.createVerticalStrut(16));

        if (room.isLocked()) {
            top.add(makeCenteredLabel("🔒  This room is locked.", COL_RED, 15, Font.BOLD));
            top.add(Box.createVerticalStrut(8));
            top.add(makeCenteredLabel("Solve the previous room first.", COL_DIM, 13, Font.PLAIN));
        } else {
            JPanel puzzleBox = new JPanel();
            puzzleBox.setOpaque(true);
            puzzleBox.setBackground(new Color(30, 20, 55));
            puzzleBox.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COL_PURPLE, 1, true),
                new EmptyBorder(14, 24, 14, 24)
            ));
            puzzleBox.setLayout(new BoxLayout(puzzleBox, BoxLayout.Y_AXIS));

            room.showPuzzle();
            String puzzle = room.getLastMessage().replace("PUZZLE: ", "");

            JLabel puzzleIcon = makeCenteredLabel("📝  PUZZLE", COL_YELLOW, 13, Font.BOLD);
            puzzleBox.add(puzzleIcon);
            puzzleBox.add(Box.createVerticalStrut(10));

            for (String part : wrapText(puzzle, 55)) {
                puzzleBox.add(makeCenteredLabel(part, COL_TEXT, 14, Font.PLAIN));
            }

            JPanel puzzleWrap = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            puzzleWrap.setOpaque(false);
            puzzleBox.setMaximumSize(new Dimension(560, 120));
            puzzleWrap.add(puzzleBox);
            top.add(puzzleWrap);
            top.add(Box.createVerticalStrut(14));
            top.add(makeCenteredLabel("Type your answer below and press  Submit.", COL_DIM, 13, Font.PLAIN));
            top.add(Box.createVerticalStrut(4));
            top.add(makeCenteredLabel("Use the  Clue  or  Hint  buttons if stuck.", COL_DIM, 12, Font.PLAIN));
        }

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.add(makeDivider());
        bottom.add(Box.createVerticalStrut(8));
        bottom.add(makeCenteredLabel("[ " + adminMsg + " ]", COL_DIM, 12, Font.ITALIC));
        bottom.add(Box.createVerticalStrut(10));
        bottom.add(buildRoomMap(rooms, currentIndex));

        card.add(top, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
    }

    public void showWinScreen(Player player, int secondsLeft, String adminMsg) {
        roomLabel.setText("YOU ESCAPED!");
        JPanel card = makeCard();
        card.setLayout(new GridBagLayout());

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        inner.add(makeTitleLabel("🎓  YOU ESCAPED!", COL_GREEN, 28));
        inner.add(Box.createVerticalStrut(18));
        inner.add(makeCenteredLabel("Congratulations,  " + player.getName() + "!", COL_YELLOW, 16, Font.BOLD));
        inner.add(Box.createVerticalStrut(6));
        inner.add(makeCenteredLabel("You solved all rooms and broke free!", COL_TEXT, 14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(24));
        inner.add(makeDivider());
        inner.add(Box.createVerticalStrut(14));
        inner.add(makeCenteredLabel("Course: " + player.getCourse(), COL_CYAN,   14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(6));
        inner.add(makeCenteredLabel("Time left :  " + secondsLeft + " seconds", COL_GREEN, 14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(14));
        inner.add(makeDivider());
        inner.add(Box.createVerticalStrut(20));
        inner.add(makeCenteredLabel("[ " + adminMsg + " ]", COL_DIM, 12, Font.ITALIC));
        inner.add(Box.createVerticalStrut(20));
        inner.add(Box.createVerticalStrut(20));

        JButton playAgainBtn = makeBigCourseBtn(
            "🔄   PLAY AGAIN",
            "Start a new game",
            COL_GREEN,
            e -> { inputField.setText("restart"); submitButton.doClick(); }
        );
        playAgainBtn.setMaximumSize(new Dimension(320, 70));
        inner.add(playAgainBtn);
        inner.add(Box.createVerticalStrut(12));

        JButton exitWinBtn = makeBigCourseBtn(
            "✖   EXIT GAME",
            "Close the application",
            COL_RED,
            e -> System.exit(0)
        );
        exitWinBtn.setMaximumSize(new Dimension(320, 70));
        inner.add(exitWinBtn);
    }

    public void showLoopScreen(Player player, String adminMsg) {
        roomLabel.setText("You Failed!");
        JPanel card = makeCard();
        card.setLayout(new GridBagLayout());

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        inner.add(makeTitleLabel("💀  YOU FAILED!", COL_RED, 28));
        inner.add(Box.createVerticalStrut(20));
        inner.add(makeCenteredLabel("Time ran out,  " + player.getName() + ".", COL_TEXT, 15, Font.PLAIN));
        inner.add(Box.createVerticalStrut(6));
        inner.add(makeCenteredLabel("Better luck next time.", COL_TEXT, 14, Font.PLAIN));
        inner.add(Box.createVerticalStrut(24));
        inner.add(makeDivider());
        inner.add(Box.createVerticalStrut(14));
        inner.add(makeCenteredLabel("[ " + adminMsg + " ]", COL_DIM, 12, Font.ITALIC));
        inner.add(Box.createVerticalStrut(14));
        inner.add(makeDivider());
        inner.add(Box.createVerticalStrut(20));

        JButton tryAgainBtn = makeBigCourseBtn(
            "🔄   TRY AGAIN",
            "Restart the loop",
            COL_YELLOW,
            e -> { inputField.setText("restart"); submitButton.doClick(); }
        );
        tryAgainBtn.setMaximumSize(new Dimension(320, 70));
        inner.add(tryAgainBtn);
        inner.add(Box.createVerticalStrut(12));

        JButton exitBtn = makeBigCourseBtn(
            "✖   EXIT GAME",
            "Close the application",
            COL_RED,
            e -> System.exit(0)
        );
        exitBtn.setMaximumSize(new Dimension(320, 70));
        inner.add(exitBtn);
    }

    private JPanel buildRoomMap(Room[] rooms, int currentIndex) {
        JPanel map = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        map.setOpaque(false);
        for (int i = 0; i < rooms.length; i++) {
            Color c;
            String icon;
            if (rooms[i].isSolved()) { c = COL_GREEN; icon = "✓"; }
            else if (i == currentIndex) { c = COL_PURPLE; icon = "►"; }
            else if (rooms[i].isLocked()) { c = COL_DIM; icon = "🔒"; }
            else { c = COL_YELLOW; icon = " "; }

            JPanel chip = new JPanel();
            chip.setOpaque(true);
            chip.setBackground(new Color(c.getRed(), c.getGreen(), c.getBlue(), 30));
            chip.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(c, 1, true),
                new EmptyBorder(5, 10, 5, 10)
            ));
            chip.setLayout(new BoxLayout(chip, BoxLayout.Y_AXIS));

            JLabel nameL = new JLabel(icon + "  " + rooms[i].getName(), SwingConstants.CENTER);
            nameL.setFont(new Font("Consolas", Font.BOLD, 11));
            nameL.setForeground(c);
            nameL.setAlignmentX(Component.CENTER_ALIGNMENT);
            chip.add(nameL);
            map.add(chip);
        }
        return map;
    }

    private JPanel makeCard() {
        JPanel card = new JPanel();
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COL_BORDER, 1, true),
            new EmptyBorder(28, 32, 28, 32)
        ));
        return card;
    }

    private JLabel makeCenteredLabel(String text, Color color, int size, int style) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Consolas", style, size));
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private JLabel makeTitleLabel(String text, Color color, int size) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Consolas", Font.BOLD, size));
        lbl.setForeground(color);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        return lbl;
    }

    private JPanel makeDivider() {
        JPanel d = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(COL_BORDER);
                g.drawLine(20, getHeight() / 2, getWidth() - 20, getHeight() / 2);
            }
        };
        d.setOpaque(false);
        d.setPreferredSize(new Dimension(400, 10));
        d.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
        return d;
    }

    private JButton makeBigCourseBtn(String title, String subtitle, Color accent, ActionListener action) {
        JButton btn = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover()
                    ? new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 55)
                    : new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 22);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setLayout(new BoxLayout(btn, BoxLayout.Y_AXIS));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(accent, 1, true),
            new EmptyBorder(14, 28, 14, 28)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(460, 80));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setFont(new Font("Consolas", Font.BOLD, 16));
        t.setForeground(accent);
        t.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel s = new JLabel(subtitle, SwingConstants.CENTER);
        s.setFont(new Font("Consolas", Font.PLAIN, 12));
        s.setForeground(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 180));
        s.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.add(t);
        btn.add(Box.createVerticalStrut(4));
        btn.add(s);
        btn.addActionListener(action);
        return btn;
    }

    private JButton makeBtn(String text, Color accent) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover()
                    ? new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 60)
                    : new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 25);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Consolas", Font.BOLD, 13));
        btn.setForeground(accent);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(accent, 1, true),
            new EmptyBorder(7, 18, 7, 18)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private String[] wrapText(String text, int maxChars) {
        if (text.length() <= maxChars) return new String[]{text};
        java.util.List<String> lines = new java.util.ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String w : words) {
            if(line.length() + w.length() + 1 > maxChars && line.length() > 0) {
                lines.add(line.toString().trim());
                line = new StringBuilder();
            }
            line.append(w).append(" ");
        }
        if(line.length() > 0) lines.add(line.toString().trim());
        return lines.toArray(new String[0]);
    }

    public void attachListeners(ActionListener onSubmit, ActionListener onClue, ActionListener onHint) {
        submitButton.addActionListener(onSubmit);
        inputField.addActionListener(onSubmit);
        clueButton.addActionListener(onClue);
        hintButton.addActionListener(onHint);
    }

    public void setInputEnabled(boolean enabled) {
        submitButton.setEnabled(enabled);
        clueButton.setEnabled(enabled);
        hintButton.setEnabled(enabled);
        inputField.setEnabled(enabled);
    }

    public void setRoomLabel(String text) { roomLabel.setText(text); }

    public void showFeedback(String msg, Color color) {
        
        Component[] comps = cardArea.getComponents();
        if (comps.length > 0 && comps[0] instanceof JPanel) {
            JPanel card = (JPanel) comps[0];
            
            Component last = card.getComponent(card.getComponentCount() - 1);
            if (last instanceof JLabel && ((JLabel) last).getName() != null && ((JLabel) last).getName().equals("feedback")) {
                card.remove(last);
            }
            JLabel fb = new JLabel(msg, SwingConstants.CENTER);
            fb.setName("feedback");
            fb.setFont(new Font("Consolas", Font.BOLD, 13));
            fb.setForeground(color);
            fb.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(fb);
            card.revalidate();
            card.repaint();
        }
    }
    // Getters
    public JTextField getInputField() { 
        return inputField;   
    }
    public JLabel getTimerLabel() { 
        return timerLabel;   
    }
    public JButton getSubmitButton() { 
        return submitButton; 
    }
    public JButton getClueButton() {
        return clueButton; 
    }
    public JButton getHintButton() {
        return hintButton;   
    }

    // Kept for compatibility — not used for display anymore
    public JTextPane getDisplayArea() { return null; }
}