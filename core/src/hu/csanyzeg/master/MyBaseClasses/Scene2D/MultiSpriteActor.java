package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.ShapeType;
import hu.csanyzeg.master.MyBaseClasses.Game.InitableInterface;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyCircle;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.MyRectangle;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by M on 12/14/2017.
 */

public abstract class MultiSpriteActor extends MyActor implements InitableInterface {
    protected HashMap<String, OffsetSprite> spriteMap = new HashMap<String, OffsetSprite>();
    public static int debugLineNumbers = 16;


    public MultiSpriteActor(MyGame game, float width, float height) {
        super(game);
        super.setWidth(width);
        super.setHeight(height);
        init();
    }

    /*  OffsetSprite... olyam mint egy tömb de simán fel lehet sorolni a paramétereket. Nincs fix hossza.
            https://docs.oracle.com/javase/tutorial/java/javaOO/arguments.html#varargs */
    public MultiSpriteActor(MyGame game, float width, float height, OffsetSprite... offsetSprites) {
        super(game);
        super.setWidth(width);
        super.setHeight(height);
        for (OffsetSprite spite : offsetSprites) {
            addSprite(spite);
        }
        init();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        for (OffsetSprite os : spriteMap.values()) {
            if (os instanceof AnimatedOffsetSprite) {
                ((AnimatedOffsetSprite) os).act(delta);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (OffsetSprite sprite : spriteMap.values()) {
            if (sprite.visible) {
                sprite.draw(batch);
            }
        }
    }


    @Override
    public void setSize(float width, float height) {
        float w = width / getWidth();
        float h = height / getHeight();
        for (OffsetSprite sprite : spriteMap.values()) {
            sprite.setSize(sprite.getWidth() * w, sprite.getHeight() * h);
            sprite.getOffsetVector().x *= w;
            sprite.getOffsetVector().y *= h;
        }
        positionChanged();
        super.setSize(width, height);
    }

    @Override
    public void setHeight(float height) {
        setSize(getWidth(), height);
    }

    @Override
    public void setWidth(float width) {
        setSize(width, getHeight());
    }


    @Override
    protected void positionChanged() {
        super.positionChanged();
        for (OffsetSprite sprite : spriteMap.values()) {
            sprite.setPosition(getX() + sprite.getOffsetVector().x, getY() + sprite.getOffsetVector().y);
        }
    }


    @Override
    public void originChanged() {
        super.originChanged();
        for (OffsetSprite sprite : spriteMap.values()) {
            sprite.setOrigin(getOriginX() - sprite.getOffsetVector().x, getOriginY() - sprite.getOffsetVector().y);
        }
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        for (OffsetSprite sprite : spriteMap.values()) {
            sprite.setRotation(getRotation());
        }
    }


    public void addSprite(OffsetSprite sprite) {
        spriteMap.put("Sprite" + spriteMap.size(), sprite);
        sprite.setPosition(getX() + sprite.getOffsetVector().x, getY() + sprite.getOffsetVector().y);
        sprite.setOrigin(getOriginX() - sprite.getOffsetVector().x, getOriginY() - sprite.getOffsetVector().y);
    }


    public void addSprite(OffsetSprite sprite, String key) {
        spriteMap.put(key, sprite);
        sprite.setPosition(getX() + sprite.getOffsetVector().x, getY() + sprite.getOffsetVector().y);
        sprite.setOrigin(getOriginX() - sprite.getOffsetVector().x, getOriginY() - sprite.getOffsetVector().y);
    }


    public void changeSprite(String key, OffsetSprite sprite) {
        for (OffsetSprite offsetSprite : spriteMap.values()) {
            if (getSprite(key) == offsetSprite) {
                float y = offsetSprite.getY();
                float x = offsetSprite.getX();
                float height = offsetSprite.getHeight();
                float width = offsetSprite.getWidth();
                offsetSprite.set(sprite);
                offsetSprite.setSize(width, height);
                offsetSprite.setPosition(x, y);
            }
        }
    }


    public boolean removeSprite(String key) {
        if (spriteMap.containsKey(key)) {
            spriteMap.remove(key);
            return true;
        }
        return false;
    }

    public OffsetSprite getSprite(String key) {
        if (spriteMap.containsKey(key)) return spriteMap.get(key);
        else return null;
    }

    public HashMap<String, OffsetSprite> getSpritesMap() {
        return spriteMap;
    }

    public Collection<OffsetSprite> getSprites() {
        return spriteMap.values();
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {
        super.drawDebugBounds(shapes);
        if (spriteMap != null) {
            for (OffsetSprite sprite : spriteMap.values()) {
                if (((int) ((elapsedTime) * 5)) % 2 == 0 && sprite.visible) {
                    Color c = new Color(Color.MAGENTA);
                    shapes.setColor(c);
                    shapes.circle(sprite.getOriginX() + sprite.getX(), sprite.getOriginY() + sprite.getY(), getWidth() , 5);
                }
            }
        }
    }


    public void setAlpha(float a) {
        if (spriteMap != null) {
            for (OffsetSprite sprite : spriteMap.values()) {
                sprite.setAlpha(a);
            }
        }
    }


    public void setColor(Color c) {
        if (spriteMap != null) {
            for (OffsetSprite sprite : spriteMap.values()) {
                sprite.setColor(c);
            }
        }
    }

    public void setColor(float r, float g, float b, float a) {
        if (spriteMap != null) {
            for (OffsetSprite sprite : spriteMap.values()) {
                sprite.setColor(r, g, b, a);
            }
        }
    }
}
