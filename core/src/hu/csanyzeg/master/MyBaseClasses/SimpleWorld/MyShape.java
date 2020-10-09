package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by tanulo on 2017. 12. 13..
 */

public abstract class MyShape {

    /**
     * Tényleges középpont. Ez alapján számolja a pozícióját. center=cx+offsetx forgatva origin körül
     */
    protected float realCenterX = 0;

    /**
     * Tényleges középpont. Ez alapján számolja a pozícióját. center=cx+offsetx forgatva origin körül
     */
    protected float realCenterY = 0;

    /**
     * A szélességből és magasságból számított, körülvevő kür sugara
     */
    protected float realRadius = 0f;


    /**
     * Tényleges forgatás. A offsetRotation forgatás és az elforgatás összege.
     */
    protected float realRotation = 0;


    /**
     * Szélesség. Forgatásnál nem változik.
     */
    protected float width = 0;

    /**
     * Magasság. Forgatásnál nem változik.
     */
    protected float height = 0;

    /**
     * Forgatás fokban megadva.
     */
    protected float rotation = 0;

    /**
     * Relatív elforgatás. realRotation=offsetRotation+rotation
     */
    protected float offsetRotation = 0;

    /**
     * Relatív eltolás cx-től számítva. center=cx+offsetx
     */
    protected float offsetX = 0;

    /**
     * Relatív eltolás cy-tól számítva. center=cy+offsety
     */
    protected float offsetY=0;

    /**
     * A középpont abszolút pozíciója a játéktérben.
     */
    protected float centerX =0;

    /**
     * A középpont abszolút pozíciója a játéktérben.
     */
    protected float centerY =0;

    /**
     * A forgatás középpontja. Relatív a valódi helyétől (offsetxy+cxy) az alakzatnak.
     */
    protected float originX = 0;

    /**
     * A forgatás középpontja. Relatív a valódi helyétől (offsetxy+cxy) az alakzatnak.
     */
    protected float originY = 0;

    static protected float PI = (float) Math.PI;

    public boolean active = true;




    /**
     * Az alakzathoz hozzácsatolható objektum, például egy Actor
     */
    public Object userData = null;

    abstract public Vector2[] getCorners();
    abstract public boolean overlaps(MyShape other);

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
    public MyShape(float x, float y, float width, float height, float rotation,  float offsetRotation, float originX, float originY, float offsetX, float offsetY, boolean alignToLeftBottom) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.offsetRotation = offsetRotation;
        this.rotation = rotation;
        if (alignToLeftBottom){
            setPosition(x,y);
            setOrigin(originX,originY);
        }else {
            setPositionFromCenter(x,y);
            setOriginFromCenter(originX,originY);
        }
    }


    public void setSizeByCenter(float width, float height) {
        float olx = getLeftBottomX();
        float oly = getLeftBottomY();
        float oldW = this.width;
        float oldH = this.height;
        float oldOriginX = originX;
        float oldOriginY = originY;
        float orcx = realCenterX;
        float orcy = realCenterY;

        originX = originX / this.width * width;
        originY = originY / this.height * height;
        this.width = width;
        this.height = height;
        calculateCenterXY();
        this.centerX += orcx - realCenterX;
        this.centerY += orcy - realCenterY;
        calculateCenterXY();

        sizeChanged(width, height, oldW, oldH);
        positionChanged(getLeftBottomX(), getLeftBottomY(), olx, oly);
        //originChanged(originX, originY, oldOriginX, oldOriginY);
    }

    public void setSize(float width, float height) {
        float olx = getLeftBottomX();
        float oly = getLeftBottomY();
        float oldOriginX = originX;
        float oldOriginY = originY;
        float oldW = this.width;
        float oldH = this.height;

        originX = originX / this.width * width;
        originY = originY / this.height * height;
        this.centerX -= (this.width - width) / 2f;
        this.centerY -= (this.height - height) / 2f;
        this.width = width;
        this.height = height;
        calculateCenterXY();

        sizeChanged(width, height, oldW, oldH);
        positionChanged(getLeftBottomX(), getLeftBottomY(), olx, oly);
        //originChanged(originX, originY, oldOriginX, oldOriginY);
    }


    public void setSizeByOrigin(float width, float height) {
        float olx = getLeftBottomX();
        float oly = getLeftBottomY();
        float oldOriginX = originX;
        float oldOriginY = originY;
        float oldW = this.width;
        float oldH = this.height;

        originX = originX / this.width * width;
        originY = originY / this.height * height;
        this.centerX -= originX - oldOriginX;
        this.centerY -= originY - oldOriginY;
        this.width = width;
        this.height = height;
        calculateCenterXY();

        sizeChanged(width, height, oldW, oldH);
        positionChanged(getLeftBottomX(), getLeftBottomY(), olx, oly);
        //originChanged(originX, originY, oldOriginX, oldOriginY);

    }


    protected void calculateCenterXY(){
        realRotation = rotation + offsetRotation;
        Vector2 origCenter = new Vector2(centerX + offsetX, centerY + offsetY);
        Vector2 origin =  new Vector2(originX + centerX + offsetX,originY + centerY + offsetY);
        Vector2 v = origCenter.sub(origin);
        v.rotate(rotation);
        Vector2 s = v.add(origin);
        this.realCenterX = s.x;
        this.realCenterY = s.y;
        this.realRadius = (float)Math.sqrt(width * width + height * height) / 2f;
    }

    public void setPosition(float X, float Y) {
        float olx = getLeftBottomX();
        float oly = getLeftBottomY();
        this.centerX = X + width/2;
        this.centerY = Y + height/2;
        calculateCenterXY();
        positionChanged(getLeftBottomX(), getLeftBottomY(), olx, oly);
    }

    public void setX(float x) {
        setPosition(x,getLeftBottomY());
    }

    public void setY(float y) {
        setPosition(getLeftBottomX(),y);
    }


    public void setPositionFromCenter(float centerX, float centerY) {
        float olx = getLeftBottomX();
        float oly = getLeftBottomY();
        this.centerX = centerX;
        this.centerY = centerY;
        calculateCenterXY();
        positionChanged(getLeftBottomX(), getLeftBottomY(), olx, oly);
    }

    public void setOffset(float offsetX, float offsetY){
        float olx = getLeftBottomX();
        float oly = getLeftBottomY();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        calculateCenterXY();
        positionChanged(getLeftBottomX(), getLeftBottomY(), olx, oly);
    }

    public void rotateBy(float degree) {
        setRotation(rotation + degree);
    }

    public void setRotation(float degree) {
        float or = rotation;
        rotation = degree;
        calculateCenterXY();
        rotationChanged(rotation, or);
    }

    public float getRealCenterX() {
        return realCenterX;
    }

    public float getRealCenterY() {
        return realCenterY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    /**
     * A bal alsó sarok abszolút pozíciója a játéktérben, eltolással (offsetXY), forgatással együtt
     * @return
     */
    public float getX() {
        return realCenterX - width/2;
    }

    /**
     * A bal alsó sarok abszolút pozíciója a játéktérben, eltolással (offsetXY), forgatással együtt
     * @return
     */
    public float getY() {
        return realCenterY - height/2;
    }


    public void setOriginToCenter(){
        float ox = originX;
        float oy = originY;
        originX = 0;
        originY = 0;
        calculateCenterXY();
        originChanged(originX, originY, ox, oy);
    }

    /**
     * Forgatási középpont beállítása a középponttól számítva
     * @param x
     * @param y
     */
    public void setOriginFromCenter(float x, float y){
        float ox = originX;
        float oy = originY;
        originX = x - offsetX;
        originY = y - offsetY;
        calculateCenterXY();
        originChanged(originX, originY, ox, oy);
    }

    /**
     * Forgatási középpont beállítása a bal alsó saroktól mérve.
     * @param x
     * @param y
     */
    public void setOrigin(float x, float y){
        float ox = originX;
        float oy = originY;
        originX = x - width / 2 - offsetX;
        originY = y - height / 2 - offsetY;
        calculateCenterXY();
        originChanged(originX, originY, ox, oy);
    }

    public void setOriginFixedPositionAbsolute(float x, float y){
        setOriginFixedPosition(x - getLeftBottomX(),y - getLeftBottomY());
    }

    public void setOriginFixedPosition(float x, float y){
        float ox = originX;
        float oy = originY;
        float olx = getLeftBottomX();
        float oly = getLeftBottomY();
        Vector2 v0 = new Vector2(realCenterX,realCenterY);
        setOrigin(x,y);
        calculateCenterXY();
        v0.sub(realCenterX, realCenterY);
        centerX += v0.x;
        centerY += v0.y;
        calculateCenterXY();
        originChanged(originX, originY, ox, oy);
        positionChanged(getLeftBottomX(), getLeftBottomY(), olx, oly);
    }


    public void setOriginFixedOrigin(float x, float y){
        float ox = originX;
        float oy = originY;
        float olx = getLeftBottomX();
        float oly = getLeftBottomY();
        Vector2 v0 = new Vector2(getLeftBottomOriginX() + getLeftBottomX(),getLeftBottomOriginY() + getLeftBottomY());
        setOrigin(x,y);
        calculateCenterXY();
        v0.sub(getLeftBottomOriginX() + getLeftBottomX(),getLeftBottomOriginY() + getLeftBottomY());
        centerX += v0.x;
        centerY += v0.y;
        calculateCenterXY();
        originChanged(originX, originY, ox, oy);
        positionChanged(getLeftBottomX(), getLeftBottomY(), olx, oly);
    }


    public float getOffsetRotation() {
        return offsetRotation;
    }

    public void setOffsetRotation(float offsetRotation) {
        this.offsetRotation = offsetRotation;
        calculateCenterXY();
    }

    public void offsetRotateBy(float degree) {
        this.offsetRotation += degree;
        calculateCenterXY();
    }

    public float getRealRotation() {
        return realRotation;
    }

    public float getOriginX() {
        return originX;
    }

    public void setOriginX(float originX) {
        setOrigin(originX, originY);
    }

    public float getOriginY() {
        return originY;
    }

    public void setOriginY(float originY) {
        setOrigin(originX, originY);
    }


    public float getLeftBottomOriginY() {
        return originY + offsetY  + height/2;
    }


    public float getLeftBottomOriginX() {
        return originX + offsetX + width / 2;
    }


    public float getLeftBottomY() {
        return originY + offsetY + centerY - (height + originY*2) / 2f;
    }


    public float getLeftBottomX() {
        return originX + offsetX + centerX - (width + originX*2) / 2f;
    }


    public void setWidth(float width) {
        setSize(width, height);
    }

    public void setHeight(float height) {
        setSize(width, height);
    }


    public void setOffsetX(float offsetX) {
        setOffset(offsetX, offsetY);
    }

    public void setOffsetY(float offsetY) {
        setOffset(offsetX, offsetY);
    }

    public void setCenter(float cx, float cy){
        float x = getLeftBottomX();
        float y = getLeftBottomY();
        this.centerX = cx;
        this.centerY = cy;
        calculateCenterXY();
        positionChanged( getLeftBottomX(), getLeftBottomY(), x,y);
    }

    public void setCenterX(float centerX) {
        setCenter(centerX, centerY);
    }

    public void setCenterY(float centerY) {
        setCenter(centerX, centerY);
    }

    @Override
    public String toString() {
        Vector2[] vector2s = getCorners();
        String corners = "";
        if (vector2s != null) {
            int x = 1;
            for (Vector2 v : vector2s) {
                corners += "\n(X" + x + "=" + Math.round(v.x*100.0f)/100.0f + " Y" + x + "=" + Math.round(v.y*100.0f)/100.0f + ")";
            }
        }
        return "MyShape{" +
                "realCenterX=" + realCenterX +
                ", realCenterY=" + realCenterY +
                ", realRotation=" + realRotation +
                ", width=" + width +
                ", height=" + height +
                ", rotation=" + rotation +
                ", offsetRotation=" + offsetRotation +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", centerX=" + centerX +
                ", centerY=" + centerY +
                ", originX=" + originX +
                ", originY=" + originY;
    }

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object extraData) {
        this.userData = extraData;
    }

    @Deprecated
    public void setExtraData(Object userData) {
        this.userData = userData;
    }

    @Deprecated
    public Object getExtraData() {
        return userData;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    protected void originChanged(float newX, float newY, float oldX, float oldY){}
    protected void positionChanged(float newX, float newY, float oldX, float oldY) {}
    protected void sizeChanged(float newW, float newH, float oldW, float oldH) {}
    protected void rotationChanged(float newR, float oldR) {}



}
