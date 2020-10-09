package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

public enum OriginRule {
    /**
     * A test bal alsó forgatás nélküli sarka fix, azaz a forgatott test elfordul.
     */
    Normal,


    /**
     * A test pozíciója fix marad, csak az origin mozdul el. Figyelni kell az elforgatsra, mert x,y a test forgatásával együtt forog.
     */
    FixPosition,


    /**
     * A test originja fix marad, a pozíciója változik meg. Figyelni kell az elforgatsra, mert x,y a test forgatásával együtt forog.
     */
    FixOrigin
}
