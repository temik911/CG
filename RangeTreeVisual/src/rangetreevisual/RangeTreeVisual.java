package rangetreevisual;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class RangeTreeVisual extends Canvas {
    static final int HEIGHT = 800;
    static final int WIDHT = 600;
    
    private boolean isQuery = false;
    private boolean isFirst = true;
    private boolean isEnd = false;
    private boolean isRepaint = false;
    
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
                    isEnd = true;
                    repaint();
                }
                if (e.getKeyChar() == 'r') {
                    isRepaint = true;
                    repaint();
                }
            }
        });
    }
    
    public void update(Graphics g) {
        if (!isRepaint) {
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
        } else {
            g.clearRect(0, 0, getWidth(), getHeight());
            Vector<Point> points = new Vector<Point>();
            points = rt.getPoints(rt.getRoot(), points);
            Iterator<Point> i = points.listIterator();
            Point point;
            while (i.hasNext()) {
                point = i.next();
                g.fillOval(point.getX(), point.getY(), 4, 4);
            }
            rt.clearAnswer();
            isRepaint = false;
            isQuery = false;
            isEnd = false;
            isFirst = true;
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
    
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0) {
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
        } else {
            if (args.length == 1) {
                File file = new File(args[0]);
                String s;
                Scanner scannerLine = new Scanner(file);
                Scanner scanner = new Scanner(scannerLine.nextLine());
                int n = scanner.nextInt();
                for (int i = 0; i < n; i++) {
                    s = scannerLine.nextLine();
                    s = s.replace('(', ' ').replace(',', ' ').replace(')', ' ');
                    scanner = new Scanner(s);
                    rt.insert(new Point(scanner.nextInt(),scanner.nextInt()));
                }
                scanner = new Scanner(scannerLine.nextLine());
                int m = scanner.nextInt();
                for (int i = 0; i < m; i++) {
                    s = scannerLine.nextLine();
                    s = s.replace('(', ' ').replace(',', ' ').replace(')', ' ');
                    scanner = new Scanner(s);
                    rt.query2D(new Interval2D(new Point(scanner.nextInt(),scanner.nextInt()),
                                              new Point(scanner.nextInt(),scanner.nextInt())));
                    System.out.println(rt.getAnswer().size());
                    rt.clearAnswer();
                }
            }
        }
    }
}
