package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import hu.csanyzeg.master.MyBaseClasses.WorldHelper.WorldHelper;

public class SimpleWorldHelper extends WorldHelper<SimpleBody, Actor> {

    protected final SimpleWorld world;

    protected final Array<SimpleBodyContactListener> contactListeners = new Array<>();

    public void addContactListener(SimpleBodyContactListener contactListener){
        contactListeners.add(contactListener);
    }

    public void removeContactListener(SimpleBodyContactListener contactListener){
        contactListeners.removeValue(contactListener, true);
    }

    public void clearContactListener(){
        contactListeners.clear();
    }



    public SimpleWorldHelper(SimpleWorld world, Actor actor, ShapeType shapeType, SimpleBodyType bodyType) {
        super(actor, new SimpleBody(actor.getWidth(), actor.getHeight(), actor.getOriginX(), actor.getOriginY(), actor.getRotation(), actor.getX(), actor.getY(), bodyType, actor.getColor()));
        switch (shapeType){
            case Null:
                break;
            case Circle:
                body.addBaseCollisionCircleShape();
                break;
            case Rectangle:
                body.addBaseCollisionRectangleShape();
                break;
        }
        body.setUserData(this);
        this.world = world;
        resetChangeFlags();
    }

    @Override
    public float getBodyX() {
        return body.getLeftBottomX();
    }

    @Override
    public float getBodyY() {
        return body.getLeftBottomY();
    }

    @Override
    public float getBodyRotation() {
        return body.getRotation();
    }

    @Override
    public float getBodyOriginX() {

        return body.getLeftBottomOriginX();
    }

    @Override
    public float getBodyOriginY() {
        return body.getLeftBottomOriginY();
    }

    @Override
    public float getBodyWidth() {
        return body.getWidth();
    }

    @Override
    public float getBodyHeight() {
        return body.getHeight();
    }

    @Override
    public float getActorX() {
        return actor.getX();
    }

    @Override
    public float getActorY() {
        return actor.getY();
    }

    @Override
    public float getActorWidth() {
        return actor.getWidth();
    }

    @Override
    public float getActorHeight() {
        return actor.getHeight();
    }

    @Override
    public float getActorRotation() {
        return actor.getRotation();
    }

    @Override
    public float getActorOriginX() {
        return actor.getOriginX();
    }

    @Override
    public float getActorOriginY() {
        return actor.getOriginY();
    }

    @Override
    public WorldHelper setBodyRotation(float rotation) {
        body.setRotation(rotation);
        return this;
    }

    @Override
    public WorldHelper setBodyOrigin(float x, float y) {
        body.setOrigin(x,y);
        return this;
    }

    @Override
    public WorldHelper setBodySize(float w, float h) {
        body.setSizeByOrigin(w,h);
        return this;
    }

    @Override
    public WorldHelper setBodyPosition(float x, float y) {
        body.setPosition(x,y);
        return this;
    }

    @Override
    protected void beforeAddToWorld() {

    }

    @Override
    protected void afterAddToWorld() {

    }

    @Override
    protected void beforeRemoveFromWorld() {

    }

    @Override
    protected void afterRemoveFromWorld() {

    }

    @Override
    public void addToWorld() {
        invoke(new Runnable() {
            @Override
            public void run() {
                beforeAddToWorld();
                world.addBody(body);
                afterAddToWorld();
            }
        });
    }

    @Override
    public void removeFromWorld() {
        invoke(new Runnable() {
            @Override
            public void run() {
                beforeRemoveFromWorld();
                world.removeBody(body);
                afterRemoveFromWorld();
            }
        });
    }

    @Override
    public void refreshBodyOnWorld() {
        removeFromWorld();
        addToWorld();
    }


    @Override
    public void remove(){
        invoke(new Runnable() {
            @Override
            public void run() {
                removeFromWorld();
                if (actor.getStage()!= null) {
                    actor.getStage().getActors().removeValue(actor, true);
                }
                if (actor.getParent()!=null) {
                    actor.getParent().removeActor(actor);
                }
            }
        });
    }

    @Override
    public void invoke(Runnable runnable){
        if (!world.isLocked()){
            runnable.run();
        }else{
            runnables.add(runnable);
        }
    }

    @Override
    public Color getBodyColor() {
        return body.getColor();
    }

    @Override
    public Color getActorColor() {
        return actor.getColor();
    }

    public void resetChangeFlags(){
        rotationChanged = true;
        sizeChanged = true;
        positionChanged = true;
        colorChanged = true;
        originChanged = true;
    }

    @Override
    public WorldHelper setBodyColor(Color color) {
        body.setColor(color);
        return this;
    }

    public SimpleWorldHelper setBodyType(final SimpleBodyType bodyType){
        invoke(new Runnable() {
            @Override
            public void run() {
                world.setBodyType(body, bodyType);
            }
        });
        return this;
    }
}
