package com.breckinloggins;

/**
 * Created by bloggins on 10/3/14.
 *
 * See LICENSE. If not found, assume All Rights Reserved.
 */
public class GraphNode implements IDrawable {

    /** Coordinates in OpenGL space */
    int x, y;

    public GraphNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        DrawUtils.drawCircle(x, y, 30);
    }
}
