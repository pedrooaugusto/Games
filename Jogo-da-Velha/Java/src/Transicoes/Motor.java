package Transicoes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Motor {
    
    public static int RUN_TIME;
    private Timer timer;
    private Component panel;
    private ColorRectangle from;
    private ColorRectangle to;

    private long startTime;

    public Motor(Component panel, ColorRectangle from, ColorRectangle to, int runTime) {
        this.panel = panel;
        this.from = from;
        this.to = to;
        this.RUN_TIME = runTime;
    }

    public void start() {
            timer = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    long duration = System.currentTimeMillis() - startTime;
                    double progress = (double) duration / (double) RUN_TIME;
                    if (progress > 1f) {
                        progress = 1f;
                        ((Timer) e.getSource()).stop();
                    }
                    Rectangle targetRectangle = calculateProgress(from, to, progress);
                    Color targetColor = calculateProgress(from.getCor(), to.getCor(), progress);
                    panel.setBounds(targetRectangle);
                    panel.setBackground(targetColor);
                }
            });
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.setInitialDelay(0);
            startTime = System.currentTimeMillis();
            timer.start();
    }
    public void stop()
    {
        timer.stop();
    }
    private Color calculateProgress(Color startColor, Color endColor, double progress)
    {
        int red = (int)(progress * endColor.getRed() + (1 - progress) * startColor.getRed());
        int green = (int)(progress * endColor.getGreen() + (1 - progress) * startColor.getGreen());
        int blue = (int)(progress * endColor.getBlue() + (1 - progress) * startColor.getBlue());
        // set our new color appropriately
        return new Color(red, green, blue);
    }
    private Rectangle calculateProgress(Rectangle startBounds, Rectangle targetBounds, double progress) 
    {
        Rectangle bounds = new Rectangle();
        if (startBounds != null && targetBounds != null)
        {
            bounds.setLocation(calculateProgress(startBounds.getLocation(), targetBounds.getLocation(), progress));
            bounds.setSize(calculateProgress(startBounds.getSize(), targetBounds.getSize(), progress));
        }
        return bounds;
    }

    private Point calculateProgress(Point startPoint, Point targetPoint, double progress) 
    {
        Point point = new Point();
        if(startPoint != null && targetPoint != null) 
        {
            point.x = calculateProgress(startPoint.x, targetPoint.x, progress);
            point.y = calculateProgress(startPoint.y, targetPoint.y, progress);
        }
        return point;
    }
    private int calculateProgress(int startValue, int endValue, double fraction) 
    {
        int value = 0;
        int distance = endValue - startValue;
        value = (int) Math.round((double) distance * fraction);
        value += startValue;
        return value;
    }
    private Dimension calculateProgress(Dimension startSize, Dimension targetSize, double progress) 
    {
        Dimension size = new Dimension();

        if (startSize != null && targetSize != null) {

            size.width = calculateProgress(startSize.width, targetSize.width, progress);
            size.height = calculateProgress(startSize.height, targetSize.height, progress);

        }
        return size;
    }
}
