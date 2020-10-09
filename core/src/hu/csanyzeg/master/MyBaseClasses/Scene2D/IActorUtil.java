package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public interface IActorUtil {

    public default void fitToViewportRealWorldSizeWithBlackBars() {
        Actor actor = (Actor)this;
        Stage s;
        Viewport ev;
        if ((s = actor.getStage()) != null) {
            ev = s.getViewport();
            float mulw = ev.getWorldWidth() / actor.getWidth();
            float mulh = ev.getWorldHeight() / actor.getHeight();
            if (mulw < mulh) {
                actor.setSize(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                actor.setSize(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }

    public default void fitToViewportMinWorldSizeWithBlackBars(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            float mulw = ev.getMinWorldWidth() / actor.getWidth();
            float mulh = ev.getMinWorldHeight() / actor.getHeight();
            if (mulw < mulh) {
                actor.setSize(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                actor.setSize(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }


    public default void fitToViewportMaxWorldSizeWithBlackBars(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            float mulw = ev.getMaxWorldWidth() / actor.getWidth();
            float mulh = ev.getMaxWorldHeight() / actor.getHeight();
            if (mulw < mulh) {
                actor.setSize(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                actor.setSize(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }

    public default void stretchToViewportRealWorldSizeWithoutBlackBars(){
        Actor actor = (Actor)this;
        Stage s;
        Viewport ev;
        if ((s = actor.getStage()) != null) {
            ev = s.getViewport();
            actor.setSize(ev.getWorldWidth(), ev.getWorldHeight());
        }
    }

    public default void stretchToViewportMinWorldSizeWithoutBlackBars(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            actor.setSize(ev.getMinWorldWidth(), ev.getMinWorldHeight());
        }
    }


    public default void stretchToViewportMaxWorldSizeWithoutBlackBars(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            actor.setSize(ev.getMaxWorldWidth(), ev.getMaxWorldHeight());
        }
    }

    public default void fitToViewportRealWorldSizeWithoutBlackBars(){
        Actor actor = (Actor)this;
        Stage s;
        Viewport ev;
        if ((s = actor.getStage()) != null) {
            ev = s.getViewport();
            float mulw = ev.getWorldWidth() / actor.getWidth();
            float mulh = ev.getWorldHeight() / actor.getHeight();
            if (mulw > mulh) {
                actor.setSize(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                actor.setSize(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }

    public default void fitToViewportMinWorldSizeWithoutBlackBars(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            float mulw = ev.getMinWorldWidth() / actor.getWidth();
            float mulh = ev.getMinWorldHeight() / actor.getHeight();
            if (mulw > mulh) {
                actor.setSize(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                actor.setSize(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }


    public default void fitToViewportMaxWorldSizeWithoutBlackBars(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            float mulw = ev.getMaxWorldWidth() / actor.getWidth();
            float mulh = ev.getMaxWorldHeight() / actor.getHeight();
            if (mulw > mulh) {
                actor.setSize(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                actor.setSize(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }




    public default void fitToViewportRealWorldSizeWithBlackBarsByOrigin() {
        Actor actor = (Actor)this;
        Stage s;
        Viewport ev;
        if ((s = actor.getStage()) != null) {
            ev = s.getViewport();
            float mulw = ev.getWorldWidth() / actor.getWidth();
            float mulh = ev.getWorldHeight() / actor.getHeight();
            if (mulw < mulh) {
                setSizeByOrigin(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                setSizeByOrigin(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }

    public default void fitToViewportMinWorldSizeWithBlackBarsByOrigin(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            float mulw = ev.getMinWorldWidth() / actor.getWidth();
            float mulh = ev.getMinWorldHeight() / actor.getHeight();
            if (mulw < mulh) {
                setSizeByOrigin(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                setSizeByOrigin(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }


    public default void fitToViewportMaxWorldSizeWithBlackBarsByOrigin(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            float mulw = ev.getMaxWorldWidth() / actor.getWidth();
            float mulh = ev.getMaxWorldHeight() / actor.getHeight();
            if (mulw < mulh) {
                setSizeByOrigin(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                setSizeByOrigin(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }

    public default void stretchToViewportRealWorldSizeWithoutBlackBarsByOrigin(){
        Actor actor = (Actor)this;
        Stage s;
        Viewport ev;
        if ((s = actor.getStage()) != null) {
            ev = s.getViewport();
            setSizeByOrigin(ev.getWorldWidth(), ev.getWorldHeight());
        }
    }

    public default void stretchToViewportMinWorldSizeWithoutBlackBarsByOrigin(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            setSizeByOrigin(ev.getMinWorldWidth(), ev.getMinWorldHeight());
        }
    }


    public default void stretchToViewportMaxWorldSizeWithoutBlackBarsByOrigin(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            setSizeByOrigin(ev.getMaxWorldWidth(), ev.getMaxWorldHeight());
        }
    }

    public default void fitToViewportRealWorldSizeWithoutBlackBarsByOrigin(){
        Actor actor = (Actor)this;
        Stage s;
        Viewport ev;
        if ((s = actor.getStage()) != null) {
            ev = s.getViewport();
            float mulw = ev.getWorldWidth() / actor.getWidth();
            float mulh = ev.getWorldHeight() / actor.getHeight();
            if (mulw > mulh) {
                setSizeByOrigin(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                setSizeByOrigin(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }

    public default void fitToViewportMinWorldSizeWithoutBlackBarsByOrigin(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            float mulw = ev.getMinWorldWidth() / actor.getWidth();
            float mulh = ev.getMinWorldHeight() / actor.getHeight();
            if (mulw > mulh) {
                setSizeByOrigin(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                setSizeByOrigin(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }


    public default void fitToViewportMaxWorldSizeWithoutBlackBarsByOrigin(){
        Actor actor = (Actor)this;
        Stage s;
        ExtendViewport ev;
        if ((s = actor.getStage()) != null) {
            ev = (ExtendViewport)s.getViewport();
            float mulw = ev.getMaxWorldWidth() / actor.getWidth();
            float mulh = ev.getMaxWorldHeight() / actor.getHeight();
            if (mulw > mulh) {
                setSizeByOrigin(actor.getWidth() * mulw, actor.getHeight() * mulw);
            } else {
                setSizeByOrigin(actor.getWidth() * mulh, actor.getHeight() * mulh);
            }
        }
    }

    public default void setPositionCenterOfActorToCenterOfViewport(){
        Actor actor = (Actor)this;
        Stage s;
        Viewport ev;
        if ((s = actor.getStage()) != null) {
            ev = s.getViewport();
            actor.setPosition(ev.getWorldWidth()/2-actor.getWidth()/2, ev.getWorldHeight()/2-actor.getHeight()/2);
        }
    }
    public default void setPositionCenter(float y){
        Actor actor = (Actor)this;
        Stage s;
        Viewport ev;
        if ((s = actor.getStage()) != null) {
            ev = s.getViewport();
            actor.setPosition(ev.getWorldWidth()/2-actor.getWidth()/2, y);
        }
    }

    public default void setPositionMiddle(float x){
        Actor actor = (Actor)this;
        Stage s;
        Viewport ev;
        if ((s = actor.getStage()) != null) {
            ev = s.getViewport();
            actor.setPosition(x, ev.getWorldHeight()/2-actor.getHeight()/2);
        }
    }

    public default void setPositionCenter(){
        Actor actor = (Actor)this;
        setPositionCenter(actor.getY());
    }

    public default void setPositionMiddle() {
        Actor actor = (Actor)this;
        setPositionMiddle(actor.getX());
    }


    public default boolean isInFrustum(){
        Actor actor = (Actor)this;
        MyStage m = (MyStage)(actor.getStage());
        return m.isActorShowing(actor);
    }
    public default boolean isInFrustum(float zoom){
        Actor actor = (Actor)this;
        MyStage m = (MyStage)(actor.getStage());
        return m.isActorShowing(actor, zoom);
    }

    public default void setWidthWhithAspectRatio(float width){
        Actor actor = (Actor)this;
        actor.setSize(width, actor.getHeight()*(width/actor.getWidth()));
    }

    public default void setHeightWhithAspectRatio(float height){
        Actor actor = (Actor)this;
        actor.setSize(actor.getWidth()*(height/actor.getHeight()), height);
    }

    public default void magnify(float mul){
        Actor actor = (Actor)this;
        actor.setSize(actor.getWidth()*mul, actor.getHeight()*mul);
    }

    public default void setSizeByOrigin(float width, float height) {
        Actor actor = (Actor)this;
        actor.setPosition(actor.getX() + (actor.getWidth() - width) / (actor.getWidth() / actor.getOriginX()), actor.getY() + (actor.getHeight() - height) / (actor.getHeight() / actor.getOriginY()));
        actor.setSize(width, height);
    }

    public default void magnifyByOrigin(float mul){
        Actor actor = (Actor)this;
        setSizeByOrigin(actor.getWidth()*mul, actor.getHeight()*mul);
    }

    public default void setWidthWhithAspectRatioByOrigin(float width){
        Actor actor = (Actor)this;
        setSizeByOrigin(width, actor.getHeight()*(width/actor.getWidth()));
    }

    public default void setHeightWhithAspectRatioByOrigin(float height){
        Actor actor = (Actor)this;
        setSizeByOrigin(actor.getWidth()*(height/actor.getHeight()), height);
    }

    public default void setWidthByOrigin(float width) {
        Actor actor = (Actor)this;
        setSizeByOrigin(width, actor.getHeight());
    }

    public default void setHeightByOrigin(float height) {
        Actor actor = (Actor)this;
        setSizeByOrigin(actor.getWidth(), actor.getHeight());
    }


    public default void setOrigintoCenter(){
        Actor actor = (Actor)this;
        actor.setOrigin(actor.getWidth()/2, actor.getHeight()/2);
    }

    public default String toStr() {
        Actor actor = (Actor)this;
        String name = actor.getName();
        if (name == null) {
            name = getClass().getName();
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex != -1) name = name.substring(dotIndex + 1);
        }

        return name +  " {" +
                " X = " + actor.getX() +
                " Y = " + actor.getX() +
                ", width = " + actor.getWidth() +
                ", height = " + actor.getHeight() +
                ", rotation = " + actor.getRotation() +
                ", originX = " + actor.getOriginX() +
                ", originY = " + actor.getOriginY();
    }


    public default void copyFrom(Actor other){
        Actor actor = (Actor)this;
        actor.setColor(other.getColor());
        actor.setOrigin(other.getOriginX(), other.getOriginY());
        actor.setPosition(other.getX(), other.getY());
        actor.setSize(other.getWidth(),other.getHeight());
        actor.setRotation(other.getRotation());
        actor.setVisible(other.isVisible());
        actor.setTouchable(other.getTouchable());
        actor.setName(other.getName());
        actor.setZIndex(other.getZIndex());
        if (this instanceof IZindex && other instanceof IZindex){
            IZindex zindex = (IZindex)this;
            IZindex ozindex = (IZindex)other;
            zindex.setZIndex(ozindex.getZIndex());
        }
    }

}
