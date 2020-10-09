package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

import java.util.ArrayList;
import java.util.Map;

public class SimpleContact {
    public SimpleBody bodyA;
    public SimpleBody bodyB;

    public SimpleContact(SimpleBody bodyA, SimpleBody bodyB) {
        this.bodyA = bodyA;
        this.bodyB = bodyB;
    }

    public ArrayList<Map.Entry<String, MyShape>> getOverlappedShapeEntriesA(){
        return SimpleBody.getBodyAOverlappedShapeEntries(bodyA, bodyB);
    }

    public ArrayList<Map.Entry<String, MyShape>> getOverlappedShapeEntriesB(){
        return SimpleBody.getBodyAOverlappedShapeEntries(bodyB, bodyA);
    }
}
