package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by tanulo on 2017. 12. 13..
 */

public class MyCircle extends MyShape {
    protected float radius = 0;
    public static int debugLineNumbers = 16;





    /**
     * @param radius A kör sugara
     */
    public MyCircle(float radius) {
        super(0, 0, radius, radius, 0, 0, 0, 0, 0, 0, true);
        this.radius = radius;
    }


    /**
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param radius A kör sugara
     */
    public MyCircle(float radius, float offsetX, float offsetY) {
        super(0, 0, radius*2, radius*2, 0, 0,0, 0, offsetX, offsetY, true);
        this.radius = radius;
    }


    /**
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     */
    public MyCircle(float radius, float offsetX, float offsetY, float originX, float originY) {
        super(0, 0, radius*2, radius*2, 0, 0, originX, originY, offsetX, offsetY, true);
        this.radius = radius;
    }

    /**
     * @param x Az alakzat helye
     * @param y Az alakzat helye
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param radius A kör sugara
     */
    public MyCircle(float radius, float offsetX, float offsetY, float originX, float originY, float x, float y) {
        super(x, y, radius*2, radius*2, 0, 0, originX, originY, offsetX, offsetY, true);
        this.radius = radius;
    }


    /**
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     * @param radius A kör sugara
     */
    public MyCircle(float radius,  boolean alignToLeftBottom) {
        super(0, 0, radius*2, radius*2, 0, 0, 0, 0, 0, 0, alignToLeftBottom);
        this.radius = radius;
    }
    /**
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     * @param radius A kör sugara
     */
    public MyCircle( float radius, float offsetX, float offsetY, boolean alignToLeftBottom) {
        super(0, 0, radius*2, radius*2, 0, 0,0, 0, offsetX, offsetY, alignToLeftBottom);
        this.radius = radius;
    }

    /**
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     * @param radius A kör sugara
     */
    public MyCircle( float radius, float offsetX, float offsetY, float originX, float originY,  boolean alignToLeftBottom) {
        super(0, 0, radius*2, radius*2, 0, 0, originX, originY, offsetX, offsetY, alignToLeftBottom);
        this.radius = radius;
    }

    /**
     * @param x Az alakzat helye
     * @param y Az alakzat helye
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     * @param radius A kör sugara
     */
    public MyCircle(float radius, float offsetX, float offsetY, float originX, float originY, float x, float y,  boolean alignToLeftBottom) {
        super(x, y, radius*2, radius*2, 0, 0, originX, originY, offsetX, offsetY, alignToLeftBottom);
        this.radius = radius;
    }

    /**
     * @param y Az alakzat helye
     * @param rotation Az alakzat forgatása az origin körül
     * @param offsetRotation Az alakzat forgatása saját maga körül
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     */
    public MyCircle(float radius, float offsetX, float offsetY, float originX, float originY,  float rotation,  float offsetRotation, float x, float y, boolean alignToLeftBottom) {
        super(x, y, radius*2, radius*2, rotation, offsetRotation, originX, originY, offsetX, offsetY, alignToLeftBottom);
        this.radius = radius;
    }

    /**
     * @param x Az alakzat helye
     * @param y Az alakzat helye
     * @param width  Az alakzat szélessége
     * @param height Az alakzat magassága
     * @param rotation Az alakzat forgatása az origin körül
     * @param offsetRotation Az alakzat forgatása saját maga körül
     * @param originX A forgatás középpontja
     * @param originY A forgatás középpontja
     * @param offsetX Eltolás az X koordinátától
     * @param offsetY Eltolás az Y koordinátától
     * @param alignToLeftBottom Igaz esetén az alakzatot a bal alsó sarkától számított X és Y koordinátákkal hozza létre, ellenkező esetben a küzepétől.
     */
    public MyCircle(float width, float height, float offsetX, float offsetY, float originX, float originY,  float rotation,  float offsetRotation, float x, float y, boolean alignToLeftBottom) {
        super(x, y, width, height, rotation, offsetRotation, originX, originY, offsetX, offsetY, alignToLeftBottom);
        radius = width < height ? width / 2f  : height / 2f;
    }


    @Override
    public Vector2[] getCorners() {
        Vector2[] vector2 = new Vector2[debugLineNumbers];
        for(int i=0; i<debugLineNumbers;i++){
            vector2[i] = new Vector2(radius, 0);
            vector2[i].rotate(360.0f/debugLineNumbers*i+rotation);
            vector2[i].add(realCenterX, realCenterY);
        }
        return vector2;
    }


    public static boolean overlaps(MyCircle objA, MyCircle objB) {
        return (objA.realCenterX - objB.realCenterX) * (objA.realCenterX - objB.realCenterX) +
                (objA.realCenterY - objB.realCenterY) * (objA.realCenterY - objB.realCenterY) <=
                (objA.radius + objB.radius) * (objA.radius + objB.radius);
    }

    public static boolean overlaps(MyCircle objA, MyRectangle objB){
        return MyRectangle.overlaps(objB, objA);
    }

    @Override
    public boolean overlaps(MyShape other) {
        if (other instanceof MyCircle) {
            return overlaps(this, (MyCircle)other);
        }
        if (other instanceof MyRectangle){
            return MyRectangle.overlaps((MyRectangle)other, this);
        }
        return false;
    }

    @Override
    public void setSize(float width, float height) {
        radius = width < height ? width / 2f  : height / 2f;
        super.setSize(width, height);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        super.setSize(radius * 2f, radius * 2f);
    }

    @Deprecated
    public void setRadiusByCenter(float radius) {
        this.radius = radius;
        this.width = radius*2;
        this.height = radius*2;
        calculateCenterXY();
    }
}
