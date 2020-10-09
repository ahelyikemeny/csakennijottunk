package hu.csanyzeg.master.MyBaseClasses.Game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Assets.LoadingListener;
import hu.csanyzeg.master.MyBaseClasses.Assets.LoadingStage;
import hu.csanyzeg.master.MyBaseClasses.Assets.MyAssetManager;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;

import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

/**
 * Created by tanulo on 2017. 10. 06..
 */

abstract public class MyGame extends Game {

    private LoadingStage loadingStage = null;
    private MyAssetManager myAssetManager;
    public boolean debug;

    public MyGame(boolean debug) {
        this.debug = debug;
    }

    public MyGame() {
        debug = false;
    }

    @Override
    public void create() {
        myAssetManager = new MyAssetManager();
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }


    public final Stack<Class> backButtonStack = new Stack();

    @Override
    public void setScreen(Screen screen) {
        setScreen(screen,true);
    }

    public void setScreenBackByStackPop(){
        setScreenBackByStackPop(null);
    }

    public interface ScreenInit{
        public void init(MyScreen scr);
    }

    public void setScreenBackByStackPop(ScreenInit init){
        if (backButtonStack.size()>0){
            try {
                MyScreen scr = (MyScreen) backButtonStack.pop().getConstructor(MyGame.class).newInstance(this);
                if (init != null) {
                    init.init(scr);
                }
                setScreen(scr,false);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Gdx.app.exit();
        }
    }


    public void setScreen(Screen screen, boolean pushToStack) {
        Screen prevScreen = getScreen();
        if (prevScreen!=null) {
            if (pushToStack) {backButtonStack.push(prevScreen.getClass());}
            prevScreen.dispose();
        }
        super.setScreen(screen);
    }

    public MyAssetManager getMyAssetManager() {
        return myAssetManager;
    }
    public LoadingStage getLoadingStage() {
        return loadingStage;
    }

    public void setLoadingStage(LoadingStage loadingStage) {
        this.loadingStage = loadingStage;
    }


    @Override
    public void dispose() {
        myAssetManager.dispose();
    }

    public static void printStackTrace(){
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            Gdx.app.log("Stack", ste.toString());
        }
    }

    public void preloadAssets(Class assetSource, LoadingStage preloadAssetStage) {
        AssetList assetList = new AssetList();
        AssetList.collectAssetDescriptor(assetSource, assetList);
        preloadAssets(assetList, preloadAssetStage);
    }

    public void preloadAssets(AssetList assetSource, LoadingStage preloadAssetStage){
        myAssetManager.loadAssetAsync(assetSource);

        preloadAssetStage.addLoadingListener(new LoadingListener(){
            @Override
            public void complete(LoadingStage sender) {
                super.complete(sender);
                ((MyScreen)getScreen()).removeStage(sender);
            }
        });

        ((MyScreen)getScreen()).addStage(preloadAssetStage, Integer.MAX_VALUE, true);
    }

    public void setScreenWithPreloadAssets(final Class myScreenClass, final boolean pushToStack, LoadingStage preloadAssetStage){
        if (!myAssetManager.isLoadingComplete()){
            return;
        }
        preloadAssets(myScreenClass, preloadAssetStage);
        preloadAssetStage.addLoadingListener(new LoadingListener(){
            @Override
            public void complete(LoadingStage sender) {
                super.complete(sender);
                MyScreen screen = null;
                try {
                    screen = (MyScreen) myScreenClass.getConstructor(MyGame.class).newInstance(MyGame.this);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                setScreen(screen, pushToStack);
            }
        });
    }

    public void setScreenWithPreloadAssets(Class myScreenClass, LoadingStage preloadAssetStage){
        setScreenWithPreloadAssets(myScreenClass, true, preloadAssetStage);
    }



    public void setScreenBackByStackPopWithPreloadAssets( LoadingStage preloadAssetStage){
        setScreenBackByStackPopWithPreloadAssets(null, preloadAssetStage);
    }


    public void setScreenBackByStackPopWithPreloadAssets(final ScreenInit init, LoadingStage preloadAssetStage) {
        if (!myAssetManager.isLoadingComplete()){
            return;
        }
        if (backButtonStack.size() > 0) {
            final Class c = backButtonStack.pop();
            preloadAssets(c, preloadAssetStage);
            preloadAssetStage.addLoadingListener(new LoadingListener() {
                @Override
                public void complete(LoadingStage sender) {
                    super.complete(sender);
                    try {
                        MyScreen scr = (MyScreen) c.getConstructor(MyGame.class).newInstance(MyGame.this);
                        if (init != null) {
                            init.init(scr);
                        }
                        setScreen(scr, false);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            });


        } else {
            Gdx.app.exit();
        }
    }

}
