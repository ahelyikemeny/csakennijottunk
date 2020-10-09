package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Assets.LoadingStage;
import hu.csanyzeg.master.MyBaseClasses.Game.InitableInterface;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

import java.util.ArrayList;


/**
 * Created by tuskeb on 2016. 09. 30..
 */
abstract public class MyStage extends Stage implements InitableInterface, IZindex, ITimer, IGame, IElapsedTime {
    public MyGame game;

    private MyScreen screen = null;

    protected float elapsedTime = 0;

    protected int ZIndexAutoInc = 1;

    protected boolean visible = true;
    protected boolean processInput = true;
    protected boolean pause = false;

    public final Array<Timer> timers = new Array<>();
    public Array<Timer> getTimers() {
        return timers;
    }

    protected ArrayList<VisibleChangeListener> visibleChangeListeners = new ArrayList<>();

    public interface VisibleChangeListener{
        public void change(boolean visible, MyStage sender);
    }

    public void addVisibleChangeListener(VisibleChangeListener listener){
        visibleChangeListeners.add(listener);
    }

    public void removeVisibleChangeListener(VisibleChangeListener listener){
        visibleChangeListeners.remove(listener);
    }


    protected ArrayList<PauseChangeListener> pauseChangeListeners = new ArrayList<PauseChangeListener>();

    public interface PauseChangeListener{
        public void change(boolean pause, MyStage sender);
    }

    public void addPauseChangeListener(PauseChangeListener listener){
        pauseChangeListeners.add(listener);
    }

    public void removePauseChangeListener(PauseChangeListener listener){
        pauseChangeListeners.remove(listener);
    }



    protected ArrayList<ProcessInputChangeListener> processInputChangeListeners = new ArrayList<>();

    public interface ProcessInputChangeListener{
        public void change(boolean visible, MyStage sender);
    }

    public void addProcessInputChangeListener(ProcessInputChangeListener listener){
        processInputChangeListeners.add(listener);
    }

    public void removeProcessInputChangeListener(ProcessInputChangeListener listener){
        processInputChangeListeners.remove(listener);
    }


    public MyStage(Viewport viewport, MyGame game) {
        super(viewport);
        this.game = game;
        setCameraResetToCenterOfScreen();
        init();
    }

    @Override
    public void init() {
        if(game.debug){
            addTimer(new TickTimer(2f, true, new TickTimerListener() {
                @Override
                public void onTick(Timer sender, float correction) {
                    Gdx.app.log("stage","Stage (" + this.hashCode() +") \tfps: " + 1f / Gdx.graphics.getDeltaTime() + ". \tActor count: " + getActors().size + ". \tDraw DT: " + drawTime + " ms. Act DT: " + actTime + " ms");
                }
            }));
        }
    }

    public interface BackButtonListener {
        public void backKeyDown();
    }

    public void addBackButtonListener(final BackButtonListener backButton)    {
        addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode== Input.Keys.ESCAPE) {
                    backButton.backKeyDown();
                }
                if(keycode== Input.Keys.BACK) {
                    backButton.backKeyDown();
                }
                return true;
            }
        });
    }


    public void addBackButtonScreenBackByStackPopListener()    {
        addBackButtonListener(new BackButtonListener() {
            @Override
            public void backKeyDown() {
                game.setScreenBackByStackPop();
            }
        });
    }



    public void addBackButtonScreenBackByStackPopListenerWithPreloadedAssets(final LoadingStage loadingStage) {
        addBackButtonListener(new BackButtonListener() {
            @Override
            public void backKeyDown() {
                game.setScreenBackByStackPopWithPreloadAssets(loadingStage);
            }
        });
    }



    public Actor getLastAdded() {
        return getActors().get(getActors().size-1);
    }

    public void setCameraZoomXY(float x, float y, float zoom)
    {
        OrthographicCamera c = (OrthographicCamera)getCamera();
        c.zoom=zoom;
        c.position.set(x,y,0);
        cameraTargetX = x;
        cameraTargetY = y;
        cameraTargetZoom = zoom;
        c.update();
    }

    public void fitWorldToWidth(){
        ExtendViewport v = (ExtendViewport)getViewport();
        float f = (v.getWorldWidth() / v.getMinWorldWidth());
        setCameraZoomXY(v.getWorldWidth()/2/f,v.getWorldHeight()/2,1/f);
    }


    public void fitWorldToHeight(){
        ExtendViewport v = (ExtendViewport)getViewport();
        float f = (v.getWorldHeight() / v.getMinWorldHeight());
        setCameraZoomXY(v.getWorldWidth()/2/f,v.getWorldHeight()/2,1/f);
    }



    private float cameraTargetX = 0;
    private float cameraTargetY = 0;
    private float cameraTargetZoom = 0;
    private float cameraMoveSpeed = 0;
    private float cameraZoomSpeed = 0;

    public float getCameraMoveToX() {
        return cameraTargetX;
    }

    public void setCameraMoveToX(float cameraTargetX) {
        this.cameraTargetX = cameraTargetX;
    }

    public float getCameraMoveToY() {
        return cameraTargetY;
    }

    public void setCameraMoveToY(float cameraTargetY) {
        this.cameraTargetY = cameraTargetY;
    }

    public float getCameraMoveToZoom() {
        return cameraTargetZoom;
    }

    public void setCameraMoveToZoom(float cameraTargetZoom) {
        this.cameraTargetZoom = cameraTargetZoom;
    }

    public float getCameraMoveSpeed() {
        return cameraMoveSpeed;
    }

    public void setCameraMoveSpeed(float cameraMoveSpeed) {
        this.cameraMoveSpeed = cameraMoveSpeed;
    }

    public float getCameraZoomSpeed() {
        return cameraZoomSpeed;
    }

    public void setCameraZoomSpeed(float cameraZoomSpeed) {
        this.cameraZoomSpeed = cameraZoomSpeed;
    }

    public void setCameraMoveToXY(float x, float y)
    {
        cameraTargetX = x;
        cameraTargetY = y;
    }


    public void setCameraMoveToXY(float x, float y, float zoom)
    {
        cameraTargetX = x;
        cameraTargetY = y;
        cameraTargetZoom = zoom;
    }

    @Deprecated
    public void setCameraMoveToXY(float x, float y, float zoom, float speed)
    {
        cameraTargetX = x;
        cameraTargetY = y;
        cameraTargetZoom = zoom;
        cameraMoveSpeed = speed;
        cameraZoomSpeed = speed;
    }

    @Deprecated
    public void setCameraMoveToXY(float x, float y, float zoom, float zoomSpeed, float moveSpeed)
    {
        cameraTargetX = x;
        cameraTargetY = y;
        cameraTargetZoom = zoom;
        cameraMoveSpeed = moveSpeed;
        cameraZoomSpeed = zoomSpeed;
    }


    public void setCameraResetToCenterOfScreen()
    {
        if (getViewport() instanceof ExtendViewport) {
            OrthographicCamera c = (OrthographicCamera) getCamera();
            ExtendViewport v = (ExtendViewport) getViewport();
            c.setToOrtho(false, getViewport().getWorldWidth(), getViewport().getWorldHeight());
            c.translate((v.getWorldWidth() - v.getMinWorldWidth() / 2) < 0 ? 0 : -((v.getWorldWidth() - v.getMinWorldWidth()) / 2),
                    ((v.getWorldHeight() - v.getMinWorldHeight()) / 2) < 0 ? 0 : -((v.getWorldHeight() - v.getMinWorldHeight()) / 2));
            c.update();
        }
    }
    public void setCameraResetToLeftBottomOfScreen(){
        OrthographicCamera c = (OrthographicCamera)getCamera();
        Viewport v = getViewport();
        setCameraZoomXY(v.getWorldWidth()/2, v.getWorldHeight()/2,1);
        c.update();

    }

    public void resize(int screenWidth, int screenHeight){
        getViewport().update(screenWidth, screenHeight, true);
        resized();
    }

    protected void resized(){
        setCameraResetToCenterOfScreen();
    };


    protected float actTime = 0;
    @Override
    public void act(float delta) {
        long nanoTimet = TimeUtils.nanoTime();
        super.act(delta);

        IElapsedTime.super.act(delta);
        ITimer.super.act(delta);

        OrthographicCamera c = (OrthographicCamera)getCamera();
        if (cameraTargetX!=c.position.x || cameraTargetY!=c.position.y || cameraTargetZoom!=c.zoom){
            if (Math.abs(c.position.x-cameraTargetX)<cameraMoveSpeed*delta) {
                c.position.x = (c.position.x + cameraTargetX) / 2;
            } else {
                if (c.position.x<cameraTargetX){
                    c.position.x += cameraMoveSpeed*delta;
                }else{
                    c.position.x -= cameraMoveSpeed*delta;
                }
            }
            if (Math.abs(c.position.y-cameraTargetY)<cameraMoveSpeed*delta) {
                c.position.y = (c.position.y + cameraTargetY) / 2;
            } else {
                if (c.position.y<cameraTargetY){
                    c.position.y += cameraMoveSpeed*delta;
                }else{
                    c.position.y -= cameraMoveSpeed*delta;
                }
            }
            if (Math.abs(c.zoom-cameraTargetZoom)<cameraZoomSpeed*delta) {
                c.zoom = (c.zoom + cameraTargetZoom) / 2;
            } else {
                if (c.zoom<cameraTargetZoom){
                    c.zoom += cameraZoomSpeed*delta;
                }else{
                    c.zoom -= cameraZoomSpeed*delta;
                }
            }
            c.update();

        }
        actTime = ((float)(TimeUtils.nanoTime() - nanoTimet)) / 100000;
    }


    public void resetElapsedTime()
    {
        elapsedTime = 0;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void updateFrustumActorVisible(){
        Camera c = getCamera();
        for (Actor a: getActors()) {
            a.setVisible(isActorShowing(c,a));
        }
    }

    public void updateFrustumActorVisible(float zoom){
        OrthographicCamera c = (OrthographicCamera)getCamera();
        for (Actor a: getActors()) {
            a.setVisible(isActorShowing(c,a, zoom));
        }
    }


    public void updateFrustumActorRemove(){
        Camera c = getCamera();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        for (Actor a: getActors()) {
            if(isActorShowing(c,a)) {
                actors.add(a);
            }
        }
        for (Actor a: getActors()){
            getActors().removeValue(a,true);
        }
    }

    public void updateFrustumActorRemove(float zoom){
        OrthographicCamera c = (OrthographicCamera)getCamera();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        for (Actor a: getActors()) {
            if(isActorShowing(c,a,zoom)) {
                actors.add(a);
            }
        }
        for (Actor a: getActors()){
            getActors().removeValue(a,true);
        }
    }
    public boolean isActorShowing(Actor a, float zoom) {
        return isActorShowing((OrthographicCamera)getCamera(),a,zoom);
    }

    public boolean isActorShowing(Actor a){
        Camera c = getCamera();
        return c.frustum.pointInFrustum(a.getX(), a.getY(), 0) || c.frustum.pointInFrustum(a.getX() + a.getWidth(), a.getY() + a.getHeight(), 0) ||
                c.frustum.pointInFrustum(a.getX() + a.getWidth(), a.getY(), 0) || c.frustum.pointInFrustum(a.getX(), a.getY() + a.getHeight(), 0);
    }

    public static boolean isActorShowing(Camera c, Actor a){
        return c.frustum.pointInFrustum(a.getX(), a.getY(), 0) || c.frustum.pointInFrustum(a.getX() + a.getWidth(), a.getY() + a.getHeight(), 0) ||
                c.frustum.pointInFrustum(a.getX() + a.getWidth(), a.getY(), 0) || c.frustum.pointInFrustum(a.getX(), a.getY() + a.getHeight(), 0);
    }

    public static boolean isActorShowing(OrthographicCamera c, Actor a, float zoom){
        float z = c.zoom;
        c.zoom *= zoom;
        c.update();
        boolean b = isActorShowing(c,a);
        c.zoom = z;
        c.update();
        return b;
    }

    @Override
    /**
     * Automatikusan növekvő Z index 1-től.
     * @param bActor
     */
    public void addActor(Actor actor) {
        addActor(actor, ZIndexAutoInc);
        ZIndexAutoInc++;
    }


    /**
     * A nagy érték van elől!
     * @param actor
     * @param ZIndex A nagy érték van elől!
     */
    public void addActor(Actor actor, int ZIndex) {
        super.addActor(actor);
        if (actor instanceof IZindex){
            actor.setZIndex(ZIndex);
        }else{
            sortActorsByZindex();
        }
       // System.out.println(ZIndex);
    }


    public void sortActorsByZindex(){
/*
        for (Actor a : getActors()) {
            if (a instanceof IZindex){
                System.out.println("U Z: " + ((IZindex) a).getZIndex());
            }else{
                System.out.println("E not instance");
            }
        }
*/

        boolean change = true;

        while (change) {
            change = false;
            int z = Integer.MAX_VALUE;
            for (int i = getActors().items.length - 1; i > 0; i--) {
                if (getActors().items[i] instanceof IZindex) {
                    z = getActors().items[i].getZIndex();
                }
                if (getActors().items[i - 1] instanceof IZindex) {
                    if (getActors().items[i - 1].getZIndex() > z) {
                        Actor a = getActors().items[i - 1];
                        getActors().items[i - 1] = getActors().items[i];
                        getActors().items[i] = a;
                        change = true;
                    }
                }
            }
        }

        /*
        getActors().sort(new Comparator<Actor>() {
            @Override
            public int compare(Actor bActor, Actor t1) {
                if (bActor instanceof MyActor && t1 instanceof MyActor){
                    return ((MyActor) bActor).zIndex - ((MyActor) t1).zIndex;
                }else {
                    return 0;
                }
            }
        });

         */

/*
        for (Actor a : getActors()) {
            if (a instanceof IZindex){
                System.out.println("U Z: " + ((IZindex) a).getZIndex());
            }else{
                System.out.println("U not instance");
            }
        }


        System.out.println("--------------");
*/
    }





    protected int zIndex = 0;

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public boolean setZIndex(int index) {
        this.zIndex = index;
        if (getScreen() != null){
            getScreen().sortStagesByZindex();
            return true;
        }
        return false;
    }

    public MyScreen getScreen() {
        return screen;
    }

    public void setScreen(MyScreen screen) {
        this.screen = screen;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        for(VisibleChangeListener v : visibleChangeListeners){
            v.change(visible, MyStage.this);
        }
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
        for(PauseChangeListener v : pauseChangeListeners){
            v.change(pause, MyStage.this);
        }
    }


    public boolean isProcessInput() {
        return processInput;
    }

    public void setProcessInput(boolean processInput) {
        this.processInput = processInput;
        for(ProcessInputChangeListener v : processInputChangeListeners){
            v.change(processInput, MyStage.this);
        }
    }

    protected float drawTime = 0;
    @Override
    public void draw() {
        long t = TimeUtils.nanoTime();
        super.draw();
        drawTime = ((float)(TimeUtils.nanoTime() - t)) / 100000;
    }


    @Override
    public MyGame getGame() {
        return game;
    }

    @Override
    public void setGame(MyGame game) {
        this.game = game;
    }
}
