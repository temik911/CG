package rangetreevisual;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Vector;

public class RangeTreeVisual extends Canvas {
    static final int HEIGHT = 800;
    static final int WIDHT = 600;
    
    private boolean isQuery = false;
    private boolean isFirst = true;
    private boolean isEnd = false;
    
    private int x, y;
    private Point left;
    private Point right;
    private Interval2D query;
    static public Vector<Point> vector = new Vector<Point>();
    static public RangeTree rt = new RangeTree();
    
    public RangeTreeVisual() {
        super();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!isEnd) {
                    if (!isQuery) {
                        x = e.getX();
                        y = e.getY();
                        rt.insert(new Point(x,y));
                        repaint();
                    } else {
                        if (isFirst) {
                            left = new Point(e.getX(), e.getY());
                            isFirst = false;
                        } else {
                            right = new Point(e.getX(), e.getY());
                            query = new Interval2D(left, right);
                            repaint();
                        }
                    }
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    isQuery = true;
                }
                if (e.getKeyChar() == 'c') {
                    isQuery = false;
                    isFirst = true;
                    rt.clear();
                    repaint();
                    rt.clear();
                }
            }
        });
    }
    
    public void update(Graphics g) {
        if (!isEnd) {
            if (!isQuery) {
                g.setColor(Color.BLACK);
                g.fillOval(x, y, 4, 4);
            } else {
                if (!isFirst) {
                    g.drawLine(query.getIntervalX().getLow(), query.getIntervalY().getLow(), 
                               query.getIntervalX().getHigh(), query.getIntervalY().getLow());
                    g.drawLine(query.getIntervalX().getLow(), query.getIntervalY().getLow(), 
                               query.getIntervalX().getLow(), query.getIntervalY().getHigh());
                    g.drawLine(query.getIntervalX().getHigh(), query.getIntervalY().getHigh(), 
                               query.getIntervalX().getLow(), query.getIntervalY().getHigh());
                    g.drawLine(query.getIntervalX().getHigh(), query.getIntervalY().getHigh(), 
                               query.getIntervalX().getHigh(), query.getIntervalY().getLow());
                    rt.query2D(query);
                    print(g);
                    isEnd = true;
                }
            }
        } else {
            g.clearRect(0, 0, getWidth(), getHeight());
            isEnd = false;
        }
    }
    public void print(Graphics g) {
        Vector<Point> answer = rt.getAnswer();
        g.setColor(Color.GREEN);
        Iterator<Point> i = answer.listIterator();
        Point point;
        while (i.hasNext()) {
            point = i.next();
            g.fillOval(point.getX(), point.getY(), 4, 4);
        }
        
    }
    
    public static void main(String[] args) {
        final Frame f = new Frame();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                f.dispose();
            }
        });
        f.setSize(HEIGHT, WIDHT);
        final Canvas c = new RangeTreeVisual();
        f.add(c);
        f.setVisible(true);
        
    }
}
