package hu.csanyzeg.master.MyBaseClasses.SimpleWorld;

public enum Direction {
    ClockWise,
    CounterClockWise,
    /**
     * A rövidebb űton fordul, ha jó helyen van, nem mozdul.
     */
    Shorter,
    /**
     * A hosszabb űton fordul, ha jó helyen van, akkor is megy egx kört CCW
     */
    Longer
}
