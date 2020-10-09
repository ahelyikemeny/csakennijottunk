package hu.csanyzeg.master.MyBaseClasses.Scene2D;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetCollector;
import hu.csanyzeg.master.MyBaseClasses.Game.InitableInterface;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;

/**
 * Created by tuskeb on 2016. 09. 30..
 */
abstract public class MyScreen implements Screen, InitableInterface, AssetCollector, ITimer, IGame {



    public float r=0,g=0,b=0;

    private boolean assetsLoaded = false;

    public MyGame game;

    protected Array<MyStage> stages = new Array<MyStage>();
    public final Array<Timer> timers = new Array<>();
    public Array<Timer> getTimers() {
        return timers;
    }

    protected InputMultiplexer inputMultiplexer = new InputMultiplexer();


    public MyScreen(MyGame game) {
        this.game = game;
        game.getMyAssetManager().changeAssets(this.getAssetList());
        if (game.getLoadingStage()!= null) {
            game.getLoadingStage().show();
        }
        Gdx.input.setInputProcessor(inputMultiplexer);
        init();
    }

    protected void updateInputMultiplexer() {
        inputMultiplexer.clear();
        for (int i = stages.size - 1; i >= 0; i--) {
            if (stages.get(i).visible && !stages.get(i).pause && stages.get(i).processInput){
                inputMultiplexer.addProcessor(stages.get(i));
            }
        }
    }

    public void addStage(final MyStage stage, int zIndex, boolean processInput){
        stages.add(stage);
        stage.setScreen(this);
        stage.processInput = processInput;
        stage.setZIndex(zIndex);
        if (processInput) {
            if (stage.visible && !stage.pause) {
                inputMultiplexer.addProcessor(stage);
            }
            stage.addVisibleChangeListener(new MyStage.VisibleChangeListener() {
                @Override
                public void change(boolean visible, MyStage sender) {
                    updateInputMultiplexer();
                }
            });

            stage.addPauseChangeListener(new MyStage.PauseChangeListener() {
                @Override
                public void change(boolean pause, MyStage sender) {
                    updateInputMultiplexer();
                }
            });


            stage.addProcessInputChangeListener(new MyStage.ProcessInputChangeListener() {
                @Override
                public void change(boolean pause, MyStage sender) {
                    updateInputMultiplexer();
                }
            });

        }
    }

    public void removeStage(MyStage stage){
        stages.removeValue(stage, true);
        inputMultiplexer.removeProcessor(stage);
    }


    public void sortStagesByZindex(){

        stages.sort(new Comparator<MyStage>() {
            @Override
            public int compare(MyStage actor, MyStage t1) {
                    return actor.zIndex - t1.zIndex;
            }
        });
        updateInputMultiplexer();
    }


    @Override
    public void dispose() {
        for (int i = 0; i< stages.size; i++){
            stages.get(i).dispose();
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ITimer.super.act(delta);
        if (!assetsLoaded) {
            if (!game.getMyAssetManager().isLoadingComplete()) {
                if (game.getLoadingStage() != null) {
                    game.getLoadingStage().act(delta);
                    game.getLoadingStage().draw();
                } else {
                    game.getMyAssetManager().updateManager();
                }
                return;
            }

            assetsLoaded = true;
            if (game.getLoadingStage() != null) {
                game.getLoadingStage().hide();
            }
            afterAssetsLoaded();
        }
        for(MyStage s : stages){
            if (s.visible && !s.pause) {
                s.act(delta);
            }
            if (s.visible){
                s.getViewport().apply(false);
                s.draw();
            }
        }
        for(MyStage s : stages){

        }
    }

    @Override
    public void resize(int width, int height) {
        if (game.getLoadingStage()!=null){
            game.getLoadingStage().getViewport().update(width,height);
        }
        for(MyStage s : stages){
            s.resize(width, height);
            s.getViewport().update(width, height);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void show() {

    }

    @Override
    public MyGame getGame() {
        return game;
    }

    @Override
    public void setGame(MyGame game) {
        this.game = game;
    }

    public void setBackGroundColor(float r, float g, float b)
    {
        this.r=r;
        this.g = g;
        this.b = b;
    }

    @Deprecated
    public InputMultiplexer getInputMultiplexer() {
        return inputMultiplexer;
    }


    protected abstract void afterAssetsLoaded();


}
