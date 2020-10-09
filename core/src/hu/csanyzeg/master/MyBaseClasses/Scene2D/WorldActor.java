package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.scenes.scene2d.Actor;

import hu.csanyzeg.master.MyBaseClasses.WorldHelper.WorldHelper;

public interface WorldActor {
    public void setActorWorldHelper(WorldHelper<?, Actor> worldHelper);
    public WorldHelper<?, Actor> getActorWorldHelper();

    public default void act(float delta){
        WorldHelper actorWorldHelper = getActorWorldHelper();
        Actor actor = (Actor)this;
        if (actorWorldHelper != null){
            actorWorldHelper.act(delta);
            if(actorWorldHelper.getBody()!= null) {
                actorWorldHelper.beginUpdate();
                if (actorWorldHelper.positionChanged) {
                    actor.setPosition(actorWorldHelper.getBodyX(), actorWorldHelper.getBodyY());
                }
                if (actorWorldHelper.originChanged) {
                    actor.setOrigin(actorWorldHelper.getBodyOriginX(), actorWorldHelper.getBodyOriginY());
                }
                if (actorWorldHelper.sizeChanged) {
                    actor.setSize(actorWorldHelper.getBodyWidth(), actorWorldHelper.getBodyHeight());
                }
                if (actorWorldHelper.rotationChanged) {
                    actor.setRotation(actorWorldHelper.getBodyRotation());
                }
                if (actorWorldHelper.colorChanged){
                    actor.setColor(actorWorldHelper.getBodyColor());
                }
                actorWorldHelper.endUpdate();
            }
        }
    }

    public default void sizechangedWorldActor(){
        WorldHelper actorWorldHelper = getActorWorldHelper();
        Actor actor = (Actor)this;
        if (actorWorldHelper != null && !actorWorldHelper.isModifyedByWorld()){
            actorWorldHelper.setBodySize(actor.getWidth(), actor.getHeight());
        }
    }


    public default void positionchangedWorldActor(){
        WorldHelper actorWorldHelper = getActorWorldHelper();
        Actor actor = (Actor)this;
        if (actorWorldHelper != null && !actorWorldHelper.isModifyedByWorld()){
            actorWorldHelper.setBodyPosition(actor.getX(), actor.getY());
        }
    }

    public default void rotationchangedWorldActor(){
        WorldHelper actorWorldHelper = getActorWorldHelper();
        Actor actor = (Actor)this;
        if (actorWorldHelper != null && !actorWorldHelper.isModifyedByWorld()){
            actorWorldHelper.setBodyRotation(actor.getRotation());
        }
    }


    public default void originchangedWorldActor(){
        WorldHelper actorWorldHelper = getActorWorldHelper();
        Actor actor = (Actor)this;
        if (actorWorldHelper != null && !actorWorldHelper.isModifyedByWorld()){
            actorWorldHelper.setBodyOrigin(actor.getOriginX(), actor.getOriginY());
        }
    }

    public default void colorchangedWorldActor(){
        WorldHelper actorWorldHelper = getActorWorldHelper();
        Actor actor = (Actor)this;
        if (actorWorldHelper != null && !actorWorldHelper.isModifyedByWorld()){
            actorWorldHelper.setBodyColor(actor.getColor());
        }
    }

    public default void setstageWorldActor(){
        Actor actor = (Actor)this;
        if (getActorWorldHelper() != null){
            if (actor.getStage() == null){
                getActorWorldHelper().removeFromWorld();
            }else{
                getActorWorldHelper().addToWorld();
            }
        }
    }


    public default boolean remove(){
        if(getActorWorldHelper()!=null){
            getActorWorldHelper().remove();
            return true;
        }
        return false;
    }

}
