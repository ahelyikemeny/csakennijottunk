package hu.csanyzeg.master.MyBaseClasses.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleWorldStage;

public abstract class LoadingStage extends SimpleWorldStage implements AssetCollector {

    protected final Array<LoadingListener> loadingListeners = new Array<>();

    public LoadingStage(Viewport viewport, MyGame game) {
        super(viewport, game);
        game.getMyAssetManager().loadAsset(getAssetList());
    }

    protected boolean loadedOnce = false;

    @Override
    public void act(float delta) {
        super.act(delta);
        boolean b = game.getMyAssetManager().isLoadingComplete();
        if (!b){
            loadedOnce = false;
        }
        game.getMyAssetManager().updateManager();
        if ((!b || !loadedOnce) && game.getMyAssetManager().isLoadingComplete()){
            for(LoadingListener l : loadingListeners) {
                System.out.println(l);
                l.complete(this);
                loadedOnce = true;
            }
        }
    }

    public int getPercent(){
        return game.getMyAssetManager().getProgress();
    }

    public String getActualLoadingName(){
        return game.getMyAssetManager().getActualLoadingName();
    }

    public void show(){
        getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void hide() {
    }

    public void removeLoadingListener(LoadingListener listener) {
        loadingListeners.removeValue(listener, true);
    }

    public void addLoadingListener(LoadingListener loadingListener) {
        this.loadingListeners.add(loadingListener);
    }

}
