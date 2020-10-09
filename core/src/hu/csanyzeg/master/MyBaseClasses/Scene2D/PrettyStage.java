package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

/**
 * @author hdani1337
 * @date 2020.02.29.
 *
 * Ez az absztrakt osztály tulajdonképpen csak a StageInterfacet implementálja,
 * de itt van egy alap konstruktorunk, szóval nem kell minden stagenél egyesével végighívogatni az összes voidot
 * **/
public abstract class PrettyStage extends MyStage implements IPrettyStage {

    public PrettyStage(Viewport viewport, MyGame game) {
        super(viewport, game);
        beforeInit();
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
        afterInit();
    }

    public PrettyStage(MyGame game) {
        super(new ResponseViewport(900), game);
        beforeInit();
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
        afterInit();
    }

    /**
     * Az értékek beállítása előtt végrehajtandó utasítások
     * Pl. debuggoláshoz, vagy írhatsz egy programot ide ami minden sikeres lefutásnál lefőz egy kávét
     * **/
    public void beforeInit(){
    }

    /**
     * Az értékek beállítása után végrehajtandó utasítások
     * Pl. itt is főzhetsz le kávét
     * **/
    public void afterInit(){
    }
}
