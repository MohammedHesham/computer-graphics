import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MidPoint implements GLEventListener {
    private static final List<Point> points = new ArrayList<Point>();
    private int r, xc, yc;

    private void midPointAlgorithm() {
        //P0 = 5/4 - r
        float p = (float) (5.0 / 4 - r);
        //Starting point is (x,y)=(0,r)
        int x = 0, y = r;

        List<Point> points1 = new ArrayList<Point>();

        Point point = new Point(x, y);
        points.add(point);
        while (x < y) {
            x += 1;
            if (p < 0) {
                //Pk+1 = Pk + 2*Xk+1 + 1
                p = p + 2 * x + 1;
            } else {
                //Yk+1 = Yk - 1
                y -= 1;
                //Pk+1 = Pk - 2*Yk+1 + 2*Xk+1 +1
                p = p - 2 * y + 2 * x + 1;
            }
            point = new Point(x, y);
            points.add(point);
        }

        //The rest of the first quadrant(octant 2)
        for (int i = points.size() - 1; i >= 0; i--) {

            Point point2 = points.get(i);
            if (point2.getY() == point2.getX()) continue;

            Point point1 = new Point(point2.getY(), point2.getX());
            points1.add(point1);
        }

        points.addAll(points1);

        for (Point point1 : points) {
            System.out.println("(x,y)=(" + point1.getX() + "," + point1.getY() + ")");
        }

    }


    public static void main(String[] args) {
        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Xc:");
        int xCenter = scanner.nextInt();

        System.out.println("Enter Yc:");
        int yCenter = scanner.nextInt();

        System.out.println("Enter Radius:");
        int radius = scanner.nextInt();


        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        MidPoint midPoint = new MidPoint();
        midPoint.xc = xCenter;
        midPoint.yc = yCenter;
        midPoint.r = radius;
        midPoint.midPointAlgorithm();
        glcanvas.addGLEventListener(midPoint);
        glcanvas.setSize(800, 800);

        //creating frame
        final JFrame frame = new JFrame("MidPoint");

        //adding canvas to frame
        frame.getContentPane().add(glcanvas);

        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }

    public void init(GLAutoDrawable drawable) {

    }

    public void dispose(GLAutoDrawable drawable) {

    }

    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        // clear the window
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        //we need to scale output points because range of jogl is from -1 to 1
        int maxXScale = this.xc + r + 1;
        int maxYScale = this.yc + r + 1;
        float scale = 30;
        gl.glBegin(GL2.GL_POINTS);
        {
            for (Point point : points) {
                //First Quadrant
                gl.glVertex2d((point.getX() + xc) / maxXScale, (point.getY() + yc) / maxYScale);
                //Second Quadrant
                gl.glVertex2d((point.getX() + xc) / maxXScale, (-point.getY() + yc) / maxYScale);
                //Third Quadrant
                gl.glVertex2d((-point.getX() + xc) / maxXScale, (-point.getY() + yc) / maxYScale);
                //Fourth Quadrant
                gl.glVertex2d((-point.getX() + xc) / maxXScale, (point.getY() + yc) / maxYScale);
            }
        }
        gl.glEnd();
    }


    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }


}
