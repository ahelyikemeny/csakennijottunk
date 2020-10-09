package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class SimpleBody extends MyRectangle {
    protected final HashMap<String, MyShape> shapeMap = new HashMap<>();



    public SimpleBody(float width, float height, float originX, float originY, float rotation, float x, float y, SimpleBodyType bodyType, Color color) {
        super(width, height, 0,0, originX,originY, rotation, 0, x, y, true);
        this.bodyType = bodyType;
        this.color = color;
    }

    /**
    * A világba rakás után csak a világon keresztül lehet megváltoztatni
     */
    protected SimpleBodyType bodyType;
    protected SimpleBodyBehaviorListener simpleBodyBehaviorListener = new SimpleBodyBehaviorListener();

    public SimpleBodyBehaviorListener getSimpleBodyBehaviorListener() {
        return simpleBodyBehaviorListener;
    }

    public void setSimpleBodyBehaviorListener(SimpleBodyBehaviorListener simpleBodyBehaviorListener) {
        this.simpleBodyBehaviorListener = simpleBodyBehaviorListener;
    }

    public static final String BASERECTANGLE = "BaseRectangle";
    public static final String BASECIRCLE = "BaseCircle";

    protected final Array<SimpleBody> connectedBodies = new Array<SimpleBody>();

    protected float elapsedTime = 0;

    protected PositionRule sizingPositionRule = PositionRule.Origin;
    protected PositionRule movingPositionRule = PositionRule.LeftBottom;
    protected OriginRule originRule = OriginRule.Normal;
    protected boolean positionCorrection = true;

    protected Color color = Color.WHITE;

    protected boolean needToCalculateOverlaps = true;


    /** világegység / mp **/
    protected Vector2 originVelocity = new Vector2(0,0);
    /** világegység / mp **/
    protected Vector2 linearVelocity = new Vector2(0,0);
    /** Fok / mp **/
    protected float angularVelocity = 0f;
    /** világegység / mp **/
    protected Vector2 sizeVelocity = new Vector2(0,0);
    /** színválzotás / mp **/
    protected float colorVelocityA = 0f;
    protected float colorVelocityR = 0f;
    protected float colorVelocityG = 0f;
    protected float colorVelocityB = 0f;


    protected Vector2 targetOrigin = new Vector2();
    protected Vector2 targetPosition = new Vector2();
    protected float targetRotation = 0;
    protected Vector2 targetSize = new Vector2();
    protected Color targetColor = Color.WHITE;


    protected float originTimer = INVALIDTIMER;
    protected float linearTimer = INVALIDTIMER;
    protected float angularTimer = INVALIDTIMER;
    protected float sizeTimer = INVALIDTIMER;
    protected float colorTimer = INVALIDTIMER;
    protected static final float INVALIDTIMER = Float.NEGATIVE_INFINITY;


    protected static float debugPointSize = 30f;
    protected boolean changedByWorld = false;


    public HashMap<String, MyShape> getCollisionShapeMap(){
        return shapeMap;
    }

    public SimpleBodyType getBodyType() {
        return bodyType;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        linearTimer = INVALIDTIMER;
        this.linearVelocity.set(linearVelocity);
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
    }

    public void setLinearVelocity(float x, float y) {
        linearTimer = INVALIDTIMER;
        this.linearVelocity.set(x,y);
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(float angularVelocity) {
        angularTimer = INVALIDTIMER;
        this.angularVelocity = angularVelocity;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
    }

    public Vector2 getSizeVelocity() {
        return sizeVelocity;
    }

    public void setSizeVelocity(Vector2 sizeVelocity, PositionRule sizingPositionRule) {
        this.sizingPositionRule = sizingPositionRule;
        sizeTimer = INVALIDTIMER;
        this.sizeVelocity.set(sizeVelocity);
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onSizeVelocityChanged(this);
    }

    public void setSizeVelocity(float w, float h, PositionRule sizingPositionRule) {
        sizeTimer = INVALIDTIMER;
        this.sizingPositionRule = sizingPositionRule;
        this.sizeVelocity.set(w,h);
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onSizeVelocityChanged(this);
    }


    public Vector2 getOriginVelocity() {
        return originVelocity;
    }

    public void setOriginVelocity(Vector2 originVelocity, OriginRule originRule) {
        this.originVelocity.set(originVelocity);
        this.originRule = originRule;
        originTimer = INVALIDTIMER;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onOriginVelocityChanged(this);
    }

    public PositionRule getSizingPositionRule() {
        return sizingPositionRule;
    }

    public OriginRule getOriginRule() {
        return originRule;
    }

    /**
     * Az origint mozgatja, ami mindig a bal alsó (forgatás nélküli) saroktól relatív.
     * @param x Relatív a bal alsó saroktól
     * @param y Relatív a bal alsó saroktól
     * @param sec
     * @param originRule
     */
    public void originToFixTime(float x, float y, float sec, OriginRule originRule){
        targetOrigin.set(x,y);
        originVelocity.set((x - getLeftBottomOriginX()) / sec, (y - getLeftBottomOriginY()) / sec);
        this.originRule = originRule;
        originTimer = sec;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onOriginVelocityChanged(this);
    }

    /**
     * Az origint mozgatja, ami mindig a bal alsó (forgatás nélküli) saroktól relatív.
     * @param x Relatív a bal alsó saroktól
     * @param y Relatív a bal alsó saroktól
     * @param speed egység / sec
     * @param originRule
     */
    public void originToFixSpeed(float x, float y, float speed, OriginRule originRule){
        targetOrigin.set(x,y);
        this.originRule = originRule;
        originVelocity.set(speed, 0);
        Vector2 trip = new Vector2(targetOrigin);
        trip.sub(getLeftBottomOriginX(),getLeftBottomOriginY());
        originVelocity.rotate(trip.angle());
        originTimer = trip.len() / speed;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onOriginVelocityChanged(this);
    }

    public void originTo(float sec, float velocityX, float velocityY, OriginRule originRule){
        this.originRule = originRule;
        originTimer = sec;
        originVelocity.set(velocityX, velocityY);
        targetOrigin.set(velocityX * sec + getLeftBottomOriginX(), velocityY * sec + getLeftBottomOriginY());
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onOriginVelocityChanged(this);
    }

    /**
     *
     * @param x
     * @param y
     * @param speed egység / sec
     * @param positionRule
     */
    public void moveToFixSpeed(float x, float y, float speed, PositionRule positionRule) {
        movingPositionRule = positionRule;
        switch (positionRule) {
            case Center:
                targetPosition.set(x - getRealCenterX() + getLeftBottomX(), y - getRealCenterY() + getLeftBottomY());
                break;
            case LeftBottom:
                targetPosition.set(x, y);
                break;
            case Origin:
                targetPosition.set(x - getLeftBottomOriginX(), y - getLeftBottomOriginY());
                break;
        }
        linearVelocity.set(speed, 0);
        Vector2 trip = new Vector2(targetPosition);
        trip.sub(getLeftBottomX(),getLeftBottomY());
        linearVelocity.rotate(trip.angle());
        linearTimer = trip.len() / speed;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onLinearVelocityChanged(this);
    }

    public void moveTo(float sec, float velocityX, float velocityY) {
        linearTimer = sec;
        linearVelocity.set(velocityX, velocityY);
        targetPosition.set(velocityX * sec + getLeftBottomX(), velocityY * sec + getLeftBottomY());
        if (stopped && !isStopped()) {
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onLinearVelocityChanged(this);
    }

    public void moveToFixTime(float x, float y, float sec, PositionRule positionRule) {
        movingPositionRule = positionRule;
        switch (positionRule) {
            case Center:
                targetPosition.set(x - getRealCenterX() + getLeftBottomX(), y - getRealCenterY() + getLeftBottomY());
                linearVelocity.set((x - getRealCenterX()) / sec, (y - getRealCenterY()) / sec);
                break;
            case LeftBottom:
                targetPosition.set(x, y);
                linearVelocity.set((x - getLeftBottomX()) / sec, (y - getLeftBottomY()) / sec);
                break;
            case Origin:
                targetPosition.set(x - getLeftBottomOriginX(), y - getLeftBottomOriginY());
                linearVelocity.set((x - getLeftBottomOriginX() - getLeftBottomX()) / sec, (y - getLeftBottomOriginY() - getLeftBottomY()) / sec);
                break;
        }
        linearTimer = sec;
        if (stopped && !isStopped()) {
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onLinearVelocityChanged(this);
    }

    /**
     *
     * @param rot
     * @param speed deg / sec
     * @param direction
     */
    public void rotateToFixSpeed(float rot, float speed, Direction direction) {
        rot = rot % 360f;
        float cw = ( rot < getRotation() ?  rot  - getRotation() : - 360 + rot - getRotation()) / speed;
        float ccw = (rot < getRotation() ? rot + 360 - getRotation() : rot - getRotation()) / speed;

        switch (direction){
            case ClockWise:
                angularVelocity = -speed;
                angularTimer = Math.abs(cw);
                break;
            case CounterClockWise:
                angularVelocity = speed;
                angularTimer = Math.abs(ccw);
                break;
            case Shorter:
                angularVelocity = Math.abs(cw) < Math.abs(ccw) ? -speed : speed;
                angularTimer = Math.abs( Math.abs(cw) < Math.abs(ccw) ? cw : ccw);
                if (rot == getRotation()){
                    angularVelocity = 0;
                    angularTimer = INVALIDTIMER;
                }
                break;
            case Longer:
                angularVelocity = Math.abs(cw) >= Math.abs(ccw) ? -speed : speed;
                angularTimer = Math.abs( Math.abs(cw) >= Math.abs(ccw) ? cw : ccw);
                if (rot == getRotation()){
                    angularVelocity = speed;
                    angularTimer = 360f / speed;
                }
                break;
        }
        targetRotation = rot;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onAngularVelocityChanged(this);
    }

    public void rotateToFixTime(float rot, float sec, Direction direction){
        rot = rot % 360f;
        float cw = ( rot < getRotation() ?  rot  - getRotation() : - 360 + rot - getRotation()) / sec;
        float ccw = (rot < getRotation() ? rot + 360 - getRotation() : rot - getRotation()) / sec;

        switch (direction){
            case ClockWise:
                angularVelocity = cw;
                break;
            case CounterClockWise:
                angularVelocity = ccw;
                break;
            case Shorter:
                angularVelocity = Math.abs(cw) < Math.abs(ccw) ? cw : ccw;
                if (rot == getRotation()){
                    angularVelocity = 0;
                }
                break;
            case Longer:
                angularVelocity = Math.abs(cw) >= Math.abs(ccw) ? cw : ccw;
                if (rot == getRotation()){
                    angularVelocity = 360 / sec;
                }
                break;
        }
        angularTimer = sec;
        targetRotation = rot;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onAngularVelocityChanged(this);
    }



    public void rotateTo(float sec, float velocity) {
        angularTimer = sec;
        angularVelocity = velocity;
        targetRotation = velocity * sec + realRotation;
        if (stopped && !isStopped()) {
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onAngularVelocityChanged(this);
    }


    /**
     *
     * @param width
     * @param height
     * @param speed egység / sec
     * @param sizingPositionRule
     */
    public void sizeToFixSpeed(float width, float height, float speed, PositionRule sizingPositionRule) {
        this.sizingPositionRule = sizingPositionRule;
        targetSize.set(width, height);
        if ((width - this.width) > (height - this.height)) {
            sizeTimer = (width - this.width) / speed / 2f;
        }else{
            sizeTimer = (height - this.height) / speed / 2f;
        }
        sizeVelocity.set((width - this.width) / sizeTimer, (height - this.height) / sizeTimer);
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onSizeVelocityChanged(this);
        /*if (linearVelocity.len() > 0 && linearTimer > 0){
            targetPosition.sub((width - this.width) / 2f, (height - this.height) / 2f);
        }*/
    }

    public void sizeToFixTime(float width, float height, float sec, PositionRule sizingPositionRule) {
        this.sizingPositionRule = sizingPositionRule;
        targetSize.set(width, height);
        sizeVelocity.set((width - this.width) / sec, (height - this.height) / sec);
        sizeTimer = sec;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onSizeVelocityChanged(this);
    }


    public void sizeTo(float sec, float velocityX, float velocityY, PositionRule sizingPositionRule) {
        this.sizingPositionRule = sizingPositionRule;
        targetSize.set(width + velocityX *sec, height + velocityY * sec);
        sizeVelocity.set(velocityX, velocityY);
        sizeTimer = sec;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
        simpleBodyBehaviorListener.onSizeVelocityChanged(this);
    }

    /**
     *
     * @param scale
     * @param speed egység / sec
     * @param sizingPositionRule
     */
    public void scaleToFixSpeed(float scale, float speed, PositionRule sizingPositionRule) {
        sizeToFixTime(width * scale, height * scale, speed, sizingPositionRule);
    }

    public void scaleToFixTime(float scale, float sec, PositionRule sizingPositionRule){
        sizeToFixTime(width * scale, height * scale, sec, sizingPositionRule);
    }

    public void scaleTo(float sec, float widthVelocity, PositionRule sizingPositionRule){
        sizeTo(sec, widthVelocity, height / width * widthVelocity, sizingPositionRule);
    }

    public void colorToFixTime(Color color, float sec){
        targetColor = color;
        setColorVelocity(-(this.color.r-color.r)/sec, -(this.color.g-color.g)/sec, -(this.color.b-color.b)/sec, -(this.color.a-color.a)/sec);
        colorTimer = sec;
        if (stopped && !isStopped()){
            simpleBodyBehaviorListener.onStart(this);
        }
    }

    public void colorToFixTime(float sec, float r, float g, float b, float a){
        Color color = new Color();
        color.a= a;
        color.b = b;
        color.g = g;
        color.r = r;
        targetColor = color;
        colorToFixTime(color, sec);
    }



    public void colorTo(float sec, float r, float g, float b, float a) {
        targetColor = color;
        setColorVelocity(r, g, b, a);
        colorTimer = sec;
        if (stopped && !isStopped()) {
            simpleBodyBehaviorListener.onStart(this);
        }
    }



    protected boolean stopped = true;
    public boolean isStopped(){
        stopped =  originVelocity.len() == 0f && linearVelocity.len() == 0f && angularVelocity == 0f && sizeVelocity.len() == 0f && colorVelocityA == 0f && colorVelocityR == 0f && colorVelocityG == 0f && colorVelocityB == 0f;
        return stopped;
    }



    public void step(float deltaTime){
        changedByWorld = true;
        elapsedTime += deltaTime;




        if (originTimer >= 0f){
            originTimer -= deltaTime;
            if (originTimer <= 0f){
                originTimer = INVALIDTIMER;
                originVelocity.set(0f,0f);
                if (positionCorrection) setOriginFixedOrigin(targetOrigin.x, targetOrigin.y);
                simpleBodyBehaviorListener.onOriginVelocityChanged(this);
                if (!stopped && isStopped()) {
                    simpleBodyBehaviorListener.onStop(this);
                }
            }
        }

        if (originVelocity.len() != 0f) {
            float x1 = getLeftBottomX();
            float y1 = getLeftBottomY();
            switch (originRule) {
                case Normal:
                    setOrigin(getLeftBottomOriginX() + originVelocity.x * deltaTime, getLeftBottomOriginY() + originVelocity.y * deltaTime);
                    break;
                case FixOrigin:
                    setOriginFixedOrigin(getLeftBottomOriginX() + originVelocity.x * deltaTime, getLeftBottomOriginY() + originVelocity.y * deltaTime);
                    break;
                case FixPosition:
                    setOriginFixedPosition(getLeftBottomOriginX() + originVelocity.x * deltaTime, getLeftBottomOriginY() + originVelocity.y * deltaTime);
                    break;
            }
            float x2 = getLeftBottomX();
            float y2 = getLeftBottomY();
            targetPosition.add(x2-x1, y2-y1);
        }







        if (angularTimer >= 0f){
            angularTimer -= deltaTime;
            if (angularTimer <= 0f){
                angularTimer = INVALIDTIMER;
                angularVelocity = 0f;
                if (positionCorrection) setRotation(targetRotation);
                simpleBodyBehaviorListener.onAngularVelocityChanged(this);
                if (!stopped && isStopped()){
                    simpleBodyBehaviorListener.onStop(this);
                }
            }
        }
        if (angularVelocity != 0) {
            setRotation(getRotation() + deltaTime * angularVelocity);
        }



        if (linearTimer >= 0f){
            linearTimer -= deltaTime;

            if (linearTimer <= 0f){
                linearTimer = INVALIDTIMER;
                linearVelocity.set(0f,0f);
                if (positionCorrection) setPosition(targetPosition.x, targetPosition.y);
                simpleBodyBehaviorListener.onLinearVelocityChanged(this);
                if (!stopped && isStopped()) {
                    simpleBodyBehaviorListener.onStop(this);
                }
            }
        }
        if (linearVelocity.len() != 0f) {
            /*
            Vector2 correction = new Vector2(sizeVelocity);
            correction.scl(deltaTime);
            if (movingPositionRule == PositionRule.Origin) {
                correction.scl(getLeftBottomOriginX() / getWidth(), getLeftBottomOriginY() / getHeight());
            }
            if (movingPositionRule == PositionRule.Center) {
                correction.scl(0.5f);
            }
            if (movingPositionRule == PositionRule.LeftBottom){
                correction.scl(0f);
            }*/
            setPosition(getLeftBottomX() + linearVelocity.x * deltaTime, getLeftBottomY() + linearVelocity.y * deltaTime);
            //targetPosition.sub(correction);
            //System.out.println(this);
        }


        if (sizeTimer >= 0f){
            sizeTimer -= deltaTime;
            if (sizeTimer <= 0f){
                sizeTimer = INVALIDTIMER;
                sizeVelocity.set(0f,0f);
                if (positionCorrection){
                    switch (sizingPositionRule) {
                        case Center:
                            setSizeByCenter(targetSize.x, targetSize.y);
                            break;
                        case LeftBottom:
                            setSize(targetSize.x, targetSize.y);
                            break;
                        case Origin:
                            setSizeByOrigin(targetSize.x, targetSize.y);
                            break;
                    }
                }
                simpleBodyBehaviorListener.onSizeVelocityChanged(this);
                if (!stopped && isStopped()) {
                    simpleBodyBehaviorListener.onStop(this);
                }
            }
        }

        if (sizeVelocity.len() != 0f) {
            float x1 = getLeftBottomX();
            float y1 = getLeftBottomY();
            switch (sizingPositionRule) {
                case Center:
                    setSizeByCenter(getWidth() + sizeVelocity.x * deltaTime, getHeight() + sizeVelocity.y * deltaTime);
                break;
                case LeftBottom:
                    setSize(getWidth() + sizeVelocity.x * deltaTime, getHeight() + sizeVelocity.y * deltaTime);
                    break;
                case Origin:
                    setSizeByOrigin(getWidth() + sizeVelocity.x * deltaTime, getHeight() + sizeVelocity.y * deltaTime);
                    break;
            }
            float x2 = getLeftBottomX();
            float y2 = getLeftBottomY();
            targetPosition.add(x2-x1, y2-y1);
        }



        if (colorTimer >= 0f){
            colorTimer -= deltaTime;
            if (colorTimer <= 0f){
                colorTimer = INVALIDTIMER;
                color = targetColor;
                setColorVelocity(0f,0f,0f,0f);
                if (!stopped && isStopped()) {
                    simpleBodyBehaviorListener.onStop(this);
                }
            }
        }
        if (colorVelocityA != 0f || colorVelocityB != 0f || colorVelocityG != 0f || colorVelocityR != 0f){
            float v;
            if (color.r + colorVelocityR * deltaTime > 1f) v=1f; else if ((v = color.r + colorVelocityR * deltaTime) < 0f) v = 0f;
            color.r = v;
            if (color.g + colorVelocityG * deltaTime > 1f) v=1f; else if ((v = color.g + colorVelocityG * deltaTime) < 0f) v = 0f;
            color.g = v;
            if (color.b + colorVelocityB * deltaTime > 1f) v=1f; else if ((v = color.b + colorVelocityB * deltaTime) < 0f) v = 0f;
            color.b = v;
            if (color.a + colorVelocityA * deltaTime > 1f) v=1f; else if ((v = color.a + colorVelocityA * deltaTime) < 0f) v = 0f;
            color.a = v;
            //color.add(colorVelocityR * deltaTime, colorVelocityG * deltaTime, colorVelocityB* deltaTime, colorVelocityA* deltaTime);
        }

        changedByWorld = false;
    }

    public void setColorVelocity(float r, float g, float b, float a) {
        this.colorVelocityA = a;
        this.colorVelocityR = r;
        this.colorVelocityG = g;
        this.colorVelocityB = b;
        simpleBodyBehaviorListener.onColorVelocityChanged(this);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.a = a;
        this.color.r = r;
        this.color.g = g;
        this.color.b = b;
    }


    public void addBaseCollisionRectangleShape(){
        addCollisionShape(BASERECTANGLE,new MyRectangle(width, height,offsetX,offsetY, originX, originY, rotation, offsetRotation, centerX, centerY,false));
    }

    public void addBaseCollisionCircleShape() {
        addCollisionShape(BASECIRCLE, new MyCircle(width, height,offsetX,offsetY, originX, originY, rotation, offsetRotation, centerX, centerY,false));
    }

    public void removeBaseCollisionRectangleShape(){
        removeCollisionShape(BASERECTANGLE);
    }

    public void removeBaseCollisionCircleShape(){
        removeCollisionShape(BASECIRCLE);
    }

    /**
     *
     * @param name
     * @param shape A pozíciója és a forgatása relatív az bodytól
     */
    public void addCollisionShape(String name, MyShape shape){
        shape.setUserData(this);
        shapeMap.put(name, shape);
    }

    public void addCollisionRectangleShape(String name, float offsetX, float offsetY, float w, float h, float offsetR){
        MyRectangle shape = new MyRectangle(w,h,offsetX,offsetY, getLeftBottomOriginX(), getLeftBottomOriginY(), getRotation(), offsetR, getLeftBottomX(), getLeftBottomY(),true);
        shape.setUserData(this);
        shapeMap.put(name, shape);
    }


    public void addCollisionCircleShape(String name, float offsetX, float offsetY, float radius, float offsetR){
        MyCircle shape = new MyCircle(radius,offsetX,offsetY, getLeftBottomOriginX(), getLeftBottomOriginY(), getRotation(), offsetR, getLeftBottomX(), getLeftBottomY(),true);
        shape.setUserData(this);
        shapeMap.put(name, shape);
    }

    public void removeCollisionShape(String name){
            shapeMap.remove(name);
    }

    public MyShape getCollisionShape(String name){
            return shapeMap.get(name);
    }


    public static float getDebugPointSize() {
        return debugPointSize;
    }

    public static void setDebugPointSize(float debugPointSize) {
        SimpleBody.debugPointSize = debugPointSize;
    }

    public static void drawDebugLines(Vector2[] v, ShapeRenderer shapes){
        for (int i = 0; i < v.length - 1; i++) {
            shapes.line(v[i].x, v[i].y, v[i + 1].x, v[i + 1].y);
        }
        shapes.line(v[v.length - 1].x, v[v.length - 1].y, v[0].x, v[0].y);
    }


    public static boolean overlaps(SimpleBody bodyA, SimpleBody bodyB){
        for(MyShape shapeA : bodyA.getCollisionShapeMap().values()){
            for(MyShape shapeB : bodyB.getCollisionShapeMap().values()){
                if (shapeA.active && shapeB.active) {
                    if (shapeA.overlaps(shapeB)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ArrayList<String> getbodyAOverlappedShapeKeys(SimpleBody bodyA, SimpleBody bodyB){
        ArrayList<String> strings = new ArrayList<String>();
        for(Map.Entry shapeA : bodyA.getCollisionShapeMap().entrySet()){
            for(Map.Entry shapeB : bodyB.getCollisionShapeMap().entrySet()){
                if (((MyShape) shapeA.getValue()).active && ((MyShape) shapeB.getValue()).active) {
                    if (((MyShape) shapeA.getValue()).overlaps((MyShape) shapeB.getValue())) {
                        strings.add((String) (shapeA.getKey()));
                    }
                }
            }
        }
        return strings;
    }

    public static ArrayList<String> getbodyBOverlappedShapeKeys(SimpleBody bodyA, SimpleBody bodyB){
        return getbodyAOverlappedShapeKeys(bodyB,bodyA);
    }

    public ArrayList<String> getMyOverlappedShapeKeys(SimpleBody anotherbody){
        return getbodyAOverlappedShapeKeys(this, anotherbody);
    }

    public ArrayList<String> getOtherOverlappedShapeKeys(SimpleBody anotherbody){
        return getbodyAOverlappedShapeKeys(anotherbody, this);
    }

    public static ArrayList<MyShape> getbodyAOverlappedShapeValues(SimpleBody bodyA, SimpleBody bodyB){
        ArrayList<MyShape> strings = new ArrayList<MyShape>();
        for(Map.Entry shapeA : bodyA.getCollisionShapeMap().entrySet()){
            for(Map.Entry shapeB : bodyB.getCollisionShapeMap().entrySet()){
                if (((MyShape) shapeA.getValue()).active && ((MyShape) shapeB.getValue()).active) {
                    if (((MyShape) shapeA.getValue()).overlaps((MyShape) shapeB.getValue())) {
                        strings.add((MyShape) (shapeA.getValue()));
                    }
                }
            }
        }
        return strings;
    }

    public static ArrayList<MyShape> getbodyBOverlappedShapeValues(SimpleBody bodyA, SimpleBody bodyB){
        return getbodyAOverlappedShapeValues(bodyB,bodyA);
    }

    public ArrayList<MyShape> getMyOverlappedShapeValues(SimpleBody anotherbody){
        return getbodyAOverlappedShapeValues(this, anotherbody);
    }

    public ArrayList<MyShape> getOtherOverlappedShapeValues(SimpleBody anotherbody){
        return getbodyAOverlappedShapeValues(anotherbody, this);
    }

    public static ArrayList<Map.Entry<String, MyShape>> getBodyAOverlappedShapeEntries(SimpleBody bodyA, SimpleBody bodyB){
        ArrayList<Map.Entry<String, MyShape>> strings = new ArrayList<Map.Entry<String, MyShape>>();
        for(Map.Entry shapeA : bodyA.getCollisionShapeMap().entrySet()){
            for(Map.Entry shapeB : bodyB.getCollisionShapeMap().entrySet()){
                if (((MyShape) shapeA.getValue()).active && ((MyShape) shapeB.getValue()).active) {
                    if (((MyShape) shapeA.getValue()).overlaps((MyShape) shapeB.getValue())) {
                        strings.add((Map.Entry<String, MyShape>) (shapeA));
                    }
                }
            }
        }
        return strings;
    }

    public static ArrayList<Map.Entry<String, MyShape>> getbodyBOverlappedShapeEntries(SimpleBody bodyA, SimpleBody bodyB){
        return getBodyAOverlappedShapeEntries(bodyB,bodyA);
    }

    public ArrayList<Map.Entry<String, MyShape>> getMyOverlappedShapeEntries(SimpleBody anotherbody){
        return getBodyAOverlappedShapeEntries(this, anotherbody);
    }

    public ArrayList<Map.Entry<String, MyShape>> getOtherOverlappedShapeEntries(SimpleBody anotherbody){
        return getBodyAOverlappedShapeEntries(anotherbody, this);
    }

    public boolean overlaps(SimpleBody anotherbody){
        return overlaps(this, anotherbody);
    }


    @Override
    protected void rotationChanged(float newR, float oldR) {
        if (shapeMap == null) return;
        if (shapeMap!=null) {
            for (MyShape shape:shapeMap.values()) {
                shape.setRotation(newR);
            }
        }
        if (!changedByWorld){
            angularTimer = INVALIDTIMER;
        }
        needToCalculateOverlaps = true;
    }

    @Override
    protected void sizeChanged(float newW, float newH, float oldW, float oldH) {
        if (shapeMap == null) return;
        float w = newW / oldW;
        float h = newH / oldH;
        for (MyShape shape : shapeMap.values()) {
            shape.setSize(shape.getWidth() * w, shape.getHeight() * h);
            shape.setOffset(shape.getOffsetX()*w, shape.getOffsetY()*h);
            shape.setOrigin(getLeftBottomOriginX(), getLeftBottomOriginY());
        }
        if (!changedByWorld){
            sizeTimer = INVALIDTIMER;
        }
        needToCalculateOverlaps = true;
    }

    @Override
    protected void originChanged(float newX, float newY, float oldX, float oldY){
        if (shapeMap == null) return;
        if (shapeMap!=null) {
            for (MyShape shape:shapeMap.values()) {
                shape.setOrigin (getLeftBottomOriginX(), getLeftBottomOriginY());
            }
        }
        needToCalculateOverlaps = true;
    }

    @Override
    protected void positionChanged(float newX, float newY, float oldX, float oldY) {
        if (shapeMap == null) return;
        for (MyShape shape : shapeMap.values()) {
            shape.setPosition(getLeftBottomX(), getLeftBottomY());
        }
        if (!changedByWorld){
            linearTimer = INVALIDTIMER;
        }
        needToCalculateOverlaps = true;
    }

    protected void colorChanged(Color newC, Color oldC) {
        if (!changedByWorld){
            colorTimer = INVALIDTIMER;
        }
    }
}
