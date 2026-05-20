package noescape;

import javax.swing.*;
import java.awt.*;

public class SplashPanel extends JPanel {

    private final String playerName;
    private final String course;
    private float glowAlpha = 0f;   // for animation
    private Timer animTimer;
    
    public SplashPanel(String playerName, String course) {
        this.playerName = playerName;
        this.course = course;
        setPreferredSize(new Dimension(560, 340));
        setBackground(new Color(10, 10, 20));
        // Animate the glow alpha from 0 → 1
        animTimer = new Timer(30, e -> {
            glowAlpha = Math.min(1f, glowAlpha + 0.04f);
            repaint();
        });
        animTimer.start();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        drawBackground(g2, w, h);
        drawGlowCircle(g2, w, h);
        drawTitle(g2, w);
        drawSubtitle(g2, w);
        drawDivider(g2, w);
        drawPlayerInfo(g2, w);
        drawInstruction(g2, w, h);
        drawBorder(g2, w, h);
    }

    private void drawBackground(Graphics2D g2, int w, int h) {
        g2.setColor(new Color(10, 10, 20));
        g2.fillRect(0, 0, w, h);
    }

    private void drawGlowCircle(Graphics2D g2, int w, int h) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, glowAlpha * 0.25f));
        g2.setColor(new Color(140, 60, 220));
        g2.fillOval(w / 2 - 120, 10, 240, 140);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawTitle(Graphics2D g2, int w) {
        g2.setFont(new Font("Consolas", Font.BOLD, 44));
        g2.setColor(new Color(80, 0, 140, 120));
        drawCentered(g2, "NO ESCAPE", w, 82);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, glowAlpha));
        g2.setColor(new Color(180, 90, 240));
        drawCentered(g2, "NO ESCAPE", w, 80);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private void drawSubtitle(Graphics2D g2, int w) {
        g2.setFont(new Font("Consolas", Font.PLAIN, 13));
        g2.setColor(new Color(120, 110, 150));
        drawCentered(g2, "An endless campus time-loop adventure", w, 108);
    }

    private void drawDivider(Graphics2D g2, int w) {
        g2.setColor(new Color(70, 30, 110));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawLine(60, 124, w - 60, 124);
    }

    private void drawPlayerInfo(Graphics2D g2, int w) {
        g2.setColor(new Color(20, 16, 36));
        g2.fillRoundRect(60, 138, w - 120, 90, 12, 12);
        g2.setColor(new Color(70, 40, 100));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(60, 138, w - 120, 90, 12, 12);

        g2.setFont(new Font("Consolas", Font.BOLD, 14));
        g2.setColor(new Color(200, 200, 60));
        drawCentered(g2, "Player :  " + playerName, w, 168);

        g2.setFont(new Font("Consolas", Font.PLAIN, 13));
        g2.setColor(new Color(160, 180, 220));
        drawCentered(g2, "Course :  " + course, w, 192);
        drawCentered(g2, "Get ready to escape the loop!", w, 214);
    }

    private void drawInstruction(Graphics2D g2, int w, int h) {
        g2.setFont(new Font("Consolas", Font.BOLD, 13));
        g2.setColor(new Color(60, 200, 100));
        drawCentered(g2, "Loading your campus...", w, h - 24);
    }

    private void drawBorder(Graphics2D g2, int w, int h) {
        g2.setColor(new Color(100, 40, 160));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(8, 8, w - 16, h - 16, 18, 18);
    }

    private void drawCentered(Graphics2D g2, String text, int width, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        g2.drawString(text, x, y);
    }

    public static void showSplashDialog(String playerName, String course) {
        JDialog dialog = new JDialog();
        dialog.setTitle("NO ESCAPE");
        dialog.setModal(true);
        dialog.setUndecorated(true);   // no title bar — cleaner look
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setBackground(new Color(10, 10, 20));

        SplashPanel panel = new SplashPanel(playerName, course);
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(null); // center on screen

        Timer closeTimer = new Timer(2800, e -> {
            panel.animTimer.stop();
            dialog.dispose();
        });
        closeTimer.setRepeats(false);
        closeTimer.start();

        dialog.setVisible(true);
    }
}