package hu.csanyzeg.master.MyBaseClasses.Scene2D;

public interface IPrettyStage {

    /**
     * @author hdani1337
     *
     * Ez az interfész egy kicsit szebbé és átláthatóbbá varázsolja a kódunkat
     * {@link PrettyStage}-nél kerül implementálásra, aminek a konstruktorában minden metódus automatikusan meghívódik, de természetesen külön is implementálható
     * Különböző típusú értékek beállítása külön-külön voidokban történik meg
     * **/

    /**
     * Értékek hozzárendelése a változókhoz és objektumokhoz
     * **/
    void assignment();


    /**
     * Actorok, Labelek méreteinek beállítása
     * **/
    void setSizes();


    /**
     * Actorok, Labelek pozícióinak beállítása
     * **/
    void setPositions();


    /**
     * Listenerek hozzáadása az Actorokhoz és Labelekhez
     * **/
    void addListeners();


    /**
     * Actorok, Labelek Z indexeinek beállítása
     * **/
    void setZIndexes();


    /**
     * Actorok, Labelek hozzáadása a Stagehez
     * **/
    void addActors();
}
