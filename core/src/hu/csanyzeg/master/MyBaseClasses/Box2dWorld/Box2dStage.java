package hu.csanyzeg.master.MyBaseClasses.Box2dWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.csanyzeg.master.MyBaseClasses.WorldHelper.HelperStage;

public abstract class Box2dStage extends HelperStage<World, Box2DWorldHelper> {

    protected Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    protected WorldBodyEditorLoader loader;

    public Box2dStage(Viewport viewport, MyGame game, Vector2 gravity) {
        super(viewport, game);
        world  = new World(gravity, false);
    }

    public Box2dStage(Viewport viewport, MyGame game) {
        super(viewport, game);
        world  = new World(new Vector2(0,-9.81f), false);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getUserData() instanceof Box2DWorldHelper && contact.getFixtureB().getUserData() instanceof Box2DWorldHelper){
                    Box2DWorldHelper helperA = (Box2DWorldHelper)contact.getFixtureA().getUserData();
                    Box2DWorldHelper helperB = (Box2DWorldHelper)contact.getFixtureB().getUserData();
                    for(MyContactListener myContactListener : helperA.contactListeners){
                        myContactListener.beginContact(contact, helperA, helperB);
                    }
                    for(MyContactListener myContactListener : helperB.contactListeners){
                        myContactListener.beginContact(contact, helperB, helperA);
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
                if (contact.getFixtureA().getUserData() instanceof Box2DWorldHelper && contact.getFixtureB().getUserData() instanceof Box2DWorldHelper){
                    Box2DWorldHelper helperA = (Box2DWorldHelper)contact.getFixtureA().getUserData();
                    Box2DWorldHelper helperB = (Box2DWorldHelper)contact.getFixtureB().getUserData();
                    for(MyContactListener myContactListener : helperA.contactListeners){
                        myContactListener.endContact(contact, helperA, helperB);
                    }
                    for(MyContactListener myContactListener : helperB.contactListeners){
                        myContactListener.endContact(contact, helperB, helperA);
                    }
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                if (contact.getFixtureA().getUserData() instanceof Box2DWorldHelper && contact.getFixtureB().getUserData() instanceof Box2DWorldHelper){
                    Box2DWorldHelper helperA = (Box2DWorldHelper)contact.getFixtureA().getUserData();
                    Box2DWorldHelper helperB = (Box2DWorldHelper)contact.getFixtureB().getUserData();
                    for(MyContactListener myContactListener : helperA.contactListeners){
                        myContactListener.preSolve(contact, helperA, helperB);
                    }
                    for(MyContactListener myContactListener : helperB.contactListeners){
                        myContactListener.preSolve(contact, helperB, helperA);
                    }
                }
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                if (contact.getFixtureA().getUserData() instanceof Box2DWorldHelper && contact.getFixtureB().getUserData() instanceof Box2DWorldHelper){
                    Box2DWorldHelper helperA = (Box2DWorldHelper)contact.getFixtureA().getUserData();
                    Box2DWorldHelper helperB = (Box2DWorldHelper)contact.getFixtureB().getUserData();
                    for(MyContactListener myContactListener : helperA.contactListeners){
                        myContactListener.postSolve(contact, helperA, helperB);
                    }
                    for(MyContactListener myContactListener : helperB.contactListeners){
                        myContactListener.postSolve(contact, helperB, helperA);
                    }
                }
            }
        });
    }

    @Override
    public void init() {
        super.init();
        if (game.debug){
            addTimer(new TickTimer(1.017f, true, new TickTimerListener() {
                @Override
                public void onRepeat(TickTimer sender) {
                    Gdx.app.log("world", "DT stage: " + (lastDelta * 1000f) +" ms; \tDT world: " + (worldDelta*1000f) + " ms. \tET real: " + realElapsedTime + " \tET world & B2Dstage: " + elapsedTime + " \tWorld iterations per delta: " + iterations + ".\tWorldT-stageT diff: " + (elapsedTime - realElapsedTime)+ " s");
                }
            }));
        };
    }

    public WorldBodyEditorLoader getLoader() {
        return loader;
    }

    public void setLoader(String filename) {
        this.loader = new WorldBodyEditorLoader(filename);
    }

    private long lastWorldMs = 0;

    protected float worldDelta = 0.015f;
    protected float realElapsedTime = 0;
    protected float lastDelta;
    protected int iterations = 1;
    protected float minFps = 10f;
    protected float criticalFps = 2f;
    protected float iterationPerSec = 666f;

    public float getMinFps() {
        return minFps;
    }

    public void setMinFps(float minFps) {
        this.minFps = minFps;
    }

    public float getIterationPerSec() {
        return iterationPerSec;
    }

    public void setIterationPerSec(float iterationPerSec) {
        this.iterationPerSec = iterationPerSec;
    }

    @Override
    public void act(float delta) {
        float fps = 1f / delta;
        float delta2;
        long m = TimeUtils.millis();
        if (fps < minFps || fps < criticalFps){
            if (worldDelta > 0.050 && fps > criticalFps || worldDelta > 0.007 && fps <=criticalFps) {
                worldDelta -= 0.001f;
                if (fps<=criticalFps){
                    worldDelta -= 0.001f;
                }
            }
            delta2 = worldDelta;
        }else{
            if (worldDelta > delta){
                delta2 = delta;
            }else{
                worldDelta += 0.001f;
                delta2 = worldDelta;
            }
        }
        iterations = 1 + (int)(delta2*iterationPerSec);
        world.step(delta2, iterations, iterations);
        realElapsedTime += delta;
        lastDelta = delta;
        super.act(delta2);
    }

    @Override
    public void draw() {
        super.draw();
        if(game.debug) box2DDebugRenderer.render(world, getCamera().combined);
    }
}
