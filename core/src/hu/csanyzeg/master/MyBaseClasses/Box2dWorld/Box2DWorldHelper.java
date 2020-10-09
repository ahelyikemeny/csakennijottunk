package hu.csanyzeg.master.MyBaseClasses.Box2dWorld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import hu.csanyzeg.master.MyBaseClasses.WorldHelper.WorldHelper;

public class Box2DWorldHelper extends WorldHelper<Body, Actor> {

    protected final Array<MyContactListener> contactListeners = new Array<>();

    protected ShapeType shapeType;
    protected MyFixtureDef fixtureDef;
    protected BodyDef.BodyType bodyType;
    protected World world;
    protected WorldBodyEditorLoader loader;
    protected String bodyID;
    protected  float originX;
    protected  float originY;


    protected Array<MyJoint> joints = new Array<MyJoint>();

    public void addContactListener(MyContactListener contactListener){
        contactListeners.add(contactListener);
    }

    public void removeContactListener(MyContactListener contactListener){
        contactListeners.removeValue(contactListener, true);
    }

    public void clearContactListener(){
        contactListeners.clear();
    }

    public Array<MyContactListener> getContactListeners() {
        return contactListeners;
    }

    public void addJoint(MyJoint joint){
        joints.add(joint);
    }

    public void removeJoint(MyJoint joint){
        MyJoint.removeJoint(joint);
    }

    public void clearJoint(){
        while (joints.size>0){
            removeJoint(joints.get(0));
        }
    }

    public Array<MyJoint> getJoints() {
        return joints;
    }

    public Box2DWorldHelper(World world, Actor actor, WorldBodyEditorLoader loader, String bodyID, MyFixtureDef fixtureDef, BodyDef.BodyType bodyType) {
        super(null, null);
        shapeType = ShapeType.Polygon;
        this.loader = loader;
        this.bodyID = bodyID;
        this.bodyType = bodyType;
        this.fixtureDef = fixtureDef;
        this.world = world;
        this.actor = actor;
        resetChangeFlags();
    }

    public Box2DWorldHelper(World world, Actor actor, ShapeType shapeType, MyFixtureDef fixtureDef, BodyDef.BodyType bodyType) {
        super(null, null);
        this.bodyType = bodyType;
        this.fixtureDef = fixtureDef;
        this.shapeType = shapeType;
        this.world = world;
        this.actor = actor;
        resetChangeFlags();
    }


    //------------------  BODY SETTERS -----------------------------------------
    //------------------  BODY SETTERS -----------------------------------------
    //------------------  BODY SETTERS -----------------------------------------

    @Override
    public WorldHelper setBodyRotation(float rotation) {
        invoke(new Runnable() {
            @Override
            public void run() {
                if (body == null){
                    return;
                }
                if (!modifyedByWorld) {
                    body.setTransform(getActorX() + getActorOriginX(), getActorY() + getActorOriginY(), getActorRotation() * MathUtils.degreesToRadians);
                }
            }
        });
        return this;
    }

    @Override
    public void refreshBodyOnWorld(){
        float av = body.getAngularVelocity();
        Vector2 lv = body.getLinearVelocity();
        removeFromWorld();
        addToWorld();
        body.setLinearVelocity(lv);
        body.setAngularVelocity(av);
    }

    @Override
    public WorldHelper setBodySize(float w, float h) {
        invoke(new Runnable() {
            @Override
            public void run() {
                if (body == null) {
                    return;
                }
                if (!modifyedByWorld) {
                    refreshBodyOnWorld();
                }

            }
        });
        return this;
    }

    @Override
    public WorldHelper setBodyPosition(float x, float y) {
        invoke(new Runnable() {
            @Override
            public void run() {
                if (body == null) {
                    return;
                }
                if (!modifyedByWorld) {
                    body.setTransform(getActorX(), getActorY(), getActorRotation() * MathUtils.degreesToRadians);
                }
            }
        });
        return this;
    }


    //------------------  BODY GETTERS -----------------------------------------
    //------------------  BODY GETTERS -----------------------------------------
    //------------------  BODY GETTERS -----------------------------------------


    @Override
    public float getBodyX() {
        if (body != null) {
            return body.getPosition().x - actor.getOriginX();
        }else
            return 0;

    }

    @Override
    public float getBodyY() {
        if (body != null) {
            return body.getPosition().y - actor.getOriginY();
        }else
            return 0;
    }

    @Override
    public float getBodyRotation() {
        return MathUtils.radDeg * body.getAngle();
    }

    @Override
    public float getBodyOriginX() {
        return originX;
    }

    @Override
    public float getBodyOriginY() {
        return originY;
    }

    @Override
    public WorldHelper setBodyOrigin(float x, float y) {
        originX = x;
        originY = y;
        return this;
    }

    @Override
    public float getBodyWidth() {
        return actor.getWidth();
    }

    @Override
    public float getBodyHeight() {
        return actor.getHeight();
    }

    //------------------  ACTOR GETTERS -----------------------------------------
    //------------------  ACTOR GETTERS -----------------------------------------
    //------------------  ACTOR GETTERS -----------------------------------------
    //------------------  ACTOR GETTERS -----------------------------------------


    @Override
    public float getActorX() {
        return actor.getX();
    }

    @Override
    public float getActorY() {
        return actor.getY();
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
        if (body!=null){
            return;
        }
        beforeAddToWorld();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(getActorX() + getActorOriginX(),getActorY() + getActorOriginY());
        bodyDef.angle = getActorRotation() * MathUtils.degRad;

        body = world.createBody(bodyDef);

        body.setUserData(this.actor);

        Shape shape;
        FixtureDef fixtureDef = new FixtureDef();
        this.fixtureDef.setFixtureValues(fixtureDef);

        switch (shapeType)
        {
            case Circle:
                shape = new CircleShape();
                ((CircleShape)shape).setRadius((getActorWidth()+getActorHeight())/4);
                //((CircleShape)shape).setBodyPosition(new Vector2((getActorWidth()+getActorHeight())/4, (getActorWidth()+getActorHeight())/4));
                fixtureDef.shape = shape;
                body.createFixture(fixtureDef);
                originX =(getActorWidth()+getActorHeight())/4;
                originY = (getActorWidth()+getActorHeight())/4;
                shape.dispose();
                break;
            case Rectangle:
                shape = new PolygonShape();
                //((PolygonShape)shape).setAsBox(getActorWidth()/2,getActorHeight()/2,new Vector2(getActorWidth()/2, getActorHeight()/2),0);
                ((PolygonShape)shape).setAsBox(getActorWidth()/2,getActorHeight()/2,new Vector2(0, 0),0);
                fixtureDef.shape = shape;
                body.createFixture(fixtureDef);
                originX = getActorWidth()/2;
                originY = getActorHeight()/2;
                //body.setTransform(originX, originY, getActorRotation()*MathUtils.degRad);
                shape.dispose();
                break;

            case Polygon:
                loader.attachFixture(body, bodyID, fixtureDef, getActorWidth()>getActorHeight()?getActorWidth():getActorHeight());
                Vector2 vector2 = loader.getOrigin(bodyID,getActorWidth()>getActorHeight()?getActorWidth():getActorHeight());
                actor.setOrigin(vector2.x, vector2.y);
                break;


        }
        body.getMassData().center.set(getActorOriginX(),getActorOriginY());
        for(Fixture f : body.getFixtureList()) {
            //f.setUserData(this.actor);
            f.setUserData(this);
        }
        //body.setFixedRotation(false);
        //System.out.println(body.isFixedRotation());
        afterAddToWorld();
    }



    @Override
    public void removeFromWorld() {
        if (body == null) {
            return;
        }
        beforeRemoveFromWorld();
        world.destroyBody(this.body);
        this.body = null;
        afterRemoveFromWorld();
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
    public void remove(){
        invoke(new Runnable() {
            @Override
            public void run() {
                clearJoint();
                removeFromWorld();
                actor.getStage().getActors().removeValue(actor, true);
                actor.getParent().removeActor(actor);
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

    public void setSensor(boolean sensor){
        for(Fixture f : body.getFixtureList()){
            f.setSensor(sensor);
        }
    }

    @Override
    public void resetChangeFlags() {
        rotationChanged = true;
        sizeChanged = false;
        positionChanged = true;
        colorChanged = false;
        originChanged = false;
    }

    @Override
    public Color getBodyColor() {
        return null;
    }

    @Override
    public Color getActorColor() {
        return null;
    }

    @Override
    public WorldHelper setBodyColor(Color color) {
        return null;
    }
}
