package com.breckinloggins;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Main {
    /** screen dimensions */
    int screenWidth = 800, screenHeight = 600;

    /** position of quad */
    float x = 400, y = 300;

    /** angle of quad rotation */
    float rotation = 0;

    /** time at last frame */
    long lastFrame;

    /** frames per second */
    int fps;

    /** last fps time */
    long lastFPS;

    public Main(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        try {
            Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
            Display.setVSyncEnabled(true);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        initGL();
        getDelta(); // called once before loop to initialize lastFrame
        lastFPS = getTime();
        while (!Display.isCloseRequested()) {
            int delta = getDelta();

            update(delta);
            renderGL();

            Display.update();
            Display.sync(60);
        }

        Display.destroy();
    }

    private void update(int delta) {
        // rotate the quad
        rotation += 0.15f * delta;

        updateFPS();
    }

    /**
     * Calculate how many milliseconds have passed since the last frame
     *
     * @return milliseconds passed since last frame
     */
    private int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return the system time in milliseconds
     */
    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    private void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("SenseGraph (FPS: " + fps + ")");
            fps = 0;
            lastFPS += 1000;
        }

        fps++;
    }

    private void initGL() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, screenWidth, 0, screenHeight, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    private void renderGL() {
        // Clear the screen and the depth buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // R,G,B,A set the color to blue one time only
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        // Draw the quad
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(x, y, 0);
            GL11.glRotatef(rotation, 0f, 0f, 1f);
            GL11.glTranslatef(-x, -y, 0);

            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glVertex2f(x - 50, y - 50);
                GL11.glVertex2f(x + 50, y - 50);
                GL11.glVertex2f(x + 50, y + 50);
                GL11.glVertex2f(x - 50, y + 50);
            }
            GL11.glEnd();
        }
        GL11.glPopMatrix();

        drawCircle(50, 60, 30);
    }

    private void drawCircle(int x, int y, int radius) {
        GL11.glBegin(GL11.GL_LINE_LOOP);

        for (int angle = 0; angle <= 360; angle += 5) {
            double rad = Math.toRadians(angle);
            GL11.glVertex2d(x + Math.sin(rad) * radius, y + Math.cos(rad) * radius);
        }

        GL11.glEnd();
    }

    public static void main(String[] args) {
        new Main(1280, 1024);
    }
}
