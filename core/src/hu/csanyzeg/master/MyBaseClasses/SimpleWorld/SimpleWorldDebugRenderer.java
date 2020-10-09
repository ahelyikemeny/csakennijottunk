package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import static hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleBody.debugPointSize;
import static hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleBody.drawDebugLines;

public class SimpleWorldDebugRenderer {
    protected float elapsedTime = 0;
    protected ShapeRenderer shapes = new ShapeRenderer();

    public void render(SimpleWorld world, float deltaTime, Matrix4 projection){
        elapsedTime += deltaTime;
        shapes.setProjectionMatrix(projection);
        shapes.setAutoShapeType(true);
        shapes.begin();
        for(SimpleBody body : world.bodies){
            switch ((((int) ((elapsedTime) * 4)) % 4)) {
                case 0:
                    shapes.setColor(Color.WHITE);
                    break;
                case 1:
                    shapes.setColor(Color.BLACK);
                    break;
            }
            if (((((int) ((elapsedTime) * 4)) % 4)) < 2)
            {
                for (MyShape shape : body.shapeMap.values()) {
                    if (shape.active) {
                        drawDebugLines(shape.getCorners(), shapes);
                        shapes.circle(shape.getOriginX() + shape.getCenterX() + shape.getOffsetX(), shape.getOriginY() + shape.getCenterY() + shape.getOffsetY(), body.getWidth() / debugPointSize, 7);
                        shapes.circle(shape.getRealCenterX(), shape.getRealCenterY(), body.getWidth() / debugPointSize, 3);
                    }
                }
            }
            if (((((int) ((elapsedTime) * 4)) % 4)) == 2)
            {
                switch (body.bodyType){
                    case Sensor:
                        shapes.setColor(Color.YELLOW);
                        break;
                    case Dinamic:
                        shapes.setColor(Color.RED);
                        break;
                    default:
                        shapes.setColor(Color.GOLD);
                }
                drawDebugLines(body.getCorners(), shapes);
                shapes.circle(body.getLeftBottomOriginX() + body.getLeftBottomX(), body.getLeftBottomOriginY() + body.getLeftBottomY(), body.getWidth() / debugPointSize*3, 3);
            }
        }
        shapes.end();
    }
}
