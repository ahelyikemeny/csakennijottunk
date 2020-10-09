package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

/**
 * Created by M on 12/14/2017.
 */

public class OffsetSprite extends Sprite {
    protected Vector2 offsetVector;
    MyGame game;

    public boolean visible = true;

    static protected final float PI = (float) Math.PI;

    @Deprecated
    public OffsetSprite(Texture texture, float xOffset, float yOffset) {
        super(texture);
        this.game = game;
        offsetVector = new Vector2(xOffset, yOffset);
    }

    @Deprecated
    public OffsetSprite(TextureRegion texture, float xOffset, float yOffset) {
        super(texture);
        this.game = game;
        offsetVector = new Vector2(xOffset, yOffset);
    }

    @Deprecated
    public OffsetSprite(Texture texture, float xOffset, float yOffset, float width, float height) {
        super(texture);
        offsetVector = new Vector2(xOffset, yOffset);
        this.game = game;
        setSize(width, height);
    }

    @Deprecated
    public OffsetSprite(TextureRegion texture, float xOffset, float yOffset, float width, float height) {
        super(texture);
        offsetVector = new Vector2(xOffset, yOffset);
        this.game = game;
        setSize(width, height);
    }



    public OffsetSprite(MyGame game, String hash, float xOffset, float yOffset) {
        super(game.getMyAssetManager().getTexture(hash));
        this.game = game;
        offsetVector = new Vector2(xOffset, yOffset);
    }

    public OffsetSprite(MyGame game, String hash, float xOffset, float yOffset, float width, float height) {
        super(game.getMyAssetManager().getTexture(hash));
        offsetVector = new Vector2(xOffset, yOffset);
        this.game = game;
        setSize(width, height);
    }



    public Vector2 getOffsetVector() {
        return offsetVector;
    }

    public Vector2[] getCorners() {

        Vector2 center = new Vector2(getX() + getWidth()/2, getY() + getHeight()/2); //A sprite középpontja
        Vector2 origin = new Vector2(getOriginX() + getX(), getOriginY() + getY()); //Amelyik pont körül forog
        Vector2 rotOrigin = center.sub(origin); // Origóra helyezve
        Vector2 rot = rotOrigin.rotate(getRotation()); //Elforgatva
        Vector2 rotCenter = rot.add(origin); // Visszatolva

        //System.out.println("center: " + center + " origin: " + origin + " rotOrigin: " + rotOrigin + " rot: " + rot);

        Vector2[] vector2 = new Vector2[4];
        float w2 = getWidth()/2;
        float h2 = getHeight()/2;
        float radius = (float) Math.sqrt(h2*h2 + w2*w2);
        float radrot = (float) Math.toRadians(getRotation());
        float angle = (float) Math.asin(h2 / radius);
        vector2[0] = new Vector2( rotCenter.x + radius * (float) Math.cos(radrot - angle), rotCenter.y + radius * (float) Math.sin(radrot - angle));
        vector2[1] = new Vector2( rotCenter.x + radius * (float) Math.cos(radrot + angle),  rotCenter.y + radius * (float) Math.sin(radrot + angle));
        vector2[2] = new Vector2( rotCenter.x + radius * (float) Math.cos(radrot + PI - angle),  rotCenter.y + radius * (float) Math.sin(radrot + PI - angle));
        vector2[3] = new Vector2( rotCenter.x + radius * (float) Math.cos(radrot + PI + angle),  rotCenter.y + radius * (float) Math.sin(radrot + PI + angle));
        return vector2;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


}
