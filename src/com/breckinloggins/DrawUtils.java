package com.breckinloggins;

import org.lwjgl.opengl.GL11;

/**
 * Created by bloggins on 10/3/14.
 *
 * See LICENSE. If not found, assume All Rights Reserved.
 */
public class DrawUtils {
    public static void drawQuad(float x, float y, int size, float rotation) {
        int halfSize = size / 2;

        GL11.glPushMatrix();
        {
            GL11.glTranslatef(x, y, 0);
            GL11.glRotatef(rotation, 0f, 0f, 1f);
            GL11.glTranslatef(-x, -y, 0);

            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glVertex2f(x - halfSize, y - halfSize);
                GL11.glVertex2f(x + halfSize, y - halfSize);
                GL11.glVertex2f(x + halfSize, y + halfSize);
                GL11.glVertex2f(x - halfSize, y + halfSize);
            }
            GL11.glEnd();
        }
        GL11.glPopMatrix();
    }

    public static void drawCircle(int x, int y, int radius) {
        GL11.glBegin(GL11.GL_LINE_LOOP);

        for (int angle = 0; angle <= 360; angle += 5) {
            double rad = Math.toRadians(angle);
            GL11.glVertex2d(x + Math.sin(rad) * radius, y + Math.cos(rad) * radius);
        }

        GL11.glEnd();

    }
}
