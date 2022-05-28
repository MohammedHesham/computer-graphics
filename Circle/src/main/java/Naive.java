import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Naive implements GLEventListener {

    private static List<Point> points;

    public static void main(String[] args) {

        //getting the capabilities object of GL2 profile
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        points = generatePoints();

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        Naive l = new Naive();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(800, 800);

        //creating frame
        final JFrame frame = new JFrame("Naive");

        //adding canvas to frame
        frame.getContentPane().add(glcanvas);

        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
    }


    private static List<Point> generatePoints() {
        List<Point> points = new ArrayList<Point>();
        int r = 20;
        int xc = 2, yc = 3;
        for (int i = 0; i <= r; i++) {
            double y = Math.sqrt(Math.pow(r, 2) - Math.pow(i, 2));
            Point point = new Point(i + xc, Math.round(y + yc));
            points.add(point);

        }


        for (Point point : points) {
            System.out.println(point);
        }

        return points;
    }

    public void init(GLAutoDrawable drawable) {

    }

    public void dispose(GLAutoDrawable drawable) {

    }

    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        // clear the window
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        float scale = 30;
        // approximate  a circle with a polygon
        gl.glBegin(GL2.GL_LINES);
        {
            for (Point point : points) {
                gl.glVertex2d(point.getX() / scale, point.getY() / scale);
            }
            for (int i = points.size()-1; i >= 0; i--) {
                Point point = points.get(i);
                gl.glVertex2d(point.getX()/scale,-point.getY()/scale);
            }

            for (Point point : points) {
                gl.glVertex2d(-point.getX() / scale, -point.getY() / scale);
            }

            for (int i = points.size()-1; i >= 0; i--) {
                Point point = points.get(i);
                gl.glVertex2d(-point.getX()/scale,point.getY()/scale);
            }
        }
        gl.glEnd();
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }
}
