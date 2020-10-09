package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

public class SimpleBodyBehaviorListener {
    /**
     * Akkor fut le, ha minden mozgás megszűnt, és előtte mozgásban volt.
     * @param sender
     */
    public void onStop(SimpleBody sender){}

    /**
     * Akkor fut le, ha bármilyen mozgás elindult, de előtte nem volt mozgásban.
     * @param sender
     */
    public void onStart(SimpleBody sender){}


    public void onLinearVelocityChanged(SimpleBody sender){}
    public void onAngularVelocityChanged(SimpleBody sender){}
    public void onSizeVelocityChanged(SimpleBody sender){}
    public void onColorVelocityChanged(SimpleBody sender){}
    public void onOriginVelocityChanged(SimpleBody sender){}

    /**
     * Akkor fut le, ha egy másik test átfedésbe került vele. Csak a belépésjor fut le egyszer.
     * @param sender
     * @param other
     */
    public void onContactAdded(SimpleBody sender, SimpleBody other){}

    /**
     * Akkor fut le, ha egy másik test kilp az átfedésből. Csak egyszer fut le.
     * @param sender
     * @param other
     */
    public void onContactRemoved(SimpleBody sender, SimpleBody other){}
}
