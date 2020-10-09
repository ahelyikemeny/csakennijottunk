package hu.csanyzeg.master.MyBaseClasses.WorldHelper;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.WorldActor;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleBody;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleWorld;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleWorldHelper;

public abstract class HelperStage<TWorld, THelper> extends MyStage {

    protected TWorld world;


    public HelperStage(Viewport viewport, MyGame game) {
        super(viewport, game);
    }


    public THelper getHelper(Actor actor){
        return (THelper) (((WorldActor)actor).getActorWorldHelper());
    }

    public THelper getHelper(SimpleBody body){
        return (THelper) (body.getUserData());
    }

    public Array<THelper> getHelpers(){
        Array<THelper> helpers = new Array<>();
        for(Actor actor : getActors()){
            if (actor instanceof WorldActor){
                if (((WorldActor)actor).getActorWorldHelper()!= null){
                    try {
                        THelper th = (THelper) ((WorldActor)actor).getActorWorldHelper();
                        helpers.add(th);
                    }
                    catch (Exception e){

                    }
                }
            }
        }
        return helpers;
    }

    public TWorld getWorld() {
        return world;
    }

}
