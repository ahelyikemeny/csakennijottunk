package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import hu.csanyzeg.master.MyBaseClasses.Game.InitableInterface;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

/**
 * Created by tuskeb on 2016. 09. 30..
 */
abstract public class OneSpriteActor extends MyActor implements InitableInterface {
    public Sprite sprite;

    public OneSpriteActor(MyGame game, Sprite sprite) {
        super(game);
        if (sprite!=null) {
            this.sprite = sprite;
            init();
        }
    }

    @Override
    public void init()
    {
        setSize(sprite.getWidth(), sprite.getHeight());
        sprite.setOrigin(getOriginX(), getOriginY());
        setOrigintoCenter();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.draw(batch);
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }


    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(), getY());
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        sprite.setRotation(getRotation());

    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        sprite.setSize(getWidth(), getHeight());
        sprite.setOrigin(getOriginX(), getOriginY());
    }

    @Override
    public void originChanged() {
        super.originChanged();
        sprite.setOrigin(getOriginX(), getOriginY());
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        super.setColor(r, g, b, a);
        sprite.setColor(r,g,b,a);
    }

    public void setAlpha(float a){
        sprite.setAlpha(a);
    }

    @Override
    public void setColor(Color c) {
        super.setColor(c);
        sprite.setColor(c);
    }
}
