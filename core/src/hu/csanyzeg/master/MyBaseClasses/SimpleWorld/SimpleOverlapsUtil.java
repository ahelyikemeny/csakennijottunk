package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.Rectangle;

import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyActor;

public class SimpleOverlapsUtil {

    /*Ha létre lett hozva neki SimpleBody, akkor azon keresztül nézi az átfedést, ha nem, akkor pedig egyszerű téglalapként kezeli.*/
    public static boolean overlaps(Actor A, Actor B){
        if (A instanceof MyActor && B instanceof MyActor && ((MyActor)A).getActorWorldHelper() != null && ((MyActor)B).getActorWorldHelper() != null && ((MyActor)A).getActorWorldHelper().getBody() instanceof SimpleBody && ((MyActor)B).getActorWorldHelper().getBody() instanceof SimpleBody){
            return ((SimpleBody) ((MyActor)A).getActorWorldHelper().getBody()).overlaps((SimpleBody) ((MyActor)B).getActorWorldHelper().getBody());
        }else{
            MyRectangle ar = new MyRectangle(A.getWidth(), A.getHeight(), 0,0, A.getOriginX(), A.getOriginY(), A.getRotation(), 0, A.getX(), A.getY(), true);
            MyRectangle br = new MyRectangle(B.getWidth(), B.getHeight(), 0,0, B.getOriginX(), B.getOriginY(), B.getRotation(), 0, B.getX(), B.getY(), true);
            return ar.overlaps(br);
        }
    }

    public static boolean overlaps(Actor A, ShapeType AShape, Actor B, ShapeType BShape){
        if (AShape == ShapeType.Rectangle && BShape == ShapeType.Rectangle){
            MyRectangle ar = new MyRectangle(A.getWidth(), A.getHeight(), 0,0, A.getOriginX(), A.getOriginY(), A.getRotation(), 0, A.getX(), A.getY(), true);
            MyRectangle br = new MyRectangle(B.getWidth(), B.getHeight(), 0,0, B.getOriginX(), B.getOriginY(), B.getRotation(), 0, B.getX(), B.getY(), true);
            return ar.overlaps(br);
        }

        if (AShape == ShapeType.Rectangle && BShape == ShapeType.Circle){
            MyRectangle ar = new MyRectangle(A.getWidth(), A.getHeight(), 0,0, A.getOriginX(), A.getOriginY(), A.getRotation(), 0, A.getX(), A.getY(), true);
            MyCircle br = new MyCircle(B.getWidth(), B.getHeight(), 0,0, B.getOriginX(), B.getOriginY(), B.getRotation(), 0, B.getX(), B.getY(), true);
            return ar.overlaps(br);
        }

        if (AShape == ShapeType.Circle && BShape == ShapeType.Rectangle){
            MyCircle ar = new MyCircle(A.getWidth(), A.getHeight(), 0,0, A.getOriginX(), A.getOriginY(), A.getRotation(), 0, A.getX(), A.getY(), true);
            MyRectangle br = new MyRectangle(B.getWidth(), B.getHeight(), 0,0, B.getOriginX(), B.getOriginY(), B.getRotation(), 0, B.getX(), B.getY(), true);
            return ar.overlaps(br);
        }

        if (AShape == ShapeType.Circle && BShape == ShapeType.Circle){
            MyCircle ar = new MyCircle(A.getWidth(), A.getHeight(), 0,0, A.getOriginX(), A.getOriginY(), A.getRotation(), 0, A.getX(), A.getY(), true);
            MyCircle br = new MyCircle(B.getWidth(), B.getHeight(), 0,0, B.getOriginX(), B.getOriginY(), B.getRotation(), 0, B.getX(), B.getY(), true);
            return ar.overlaps(br);
        }
        return false;
    }
}
