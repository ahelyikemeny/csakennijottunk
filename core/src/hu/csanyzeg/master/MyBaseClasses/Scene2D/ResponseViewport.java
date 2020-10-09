package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ResponseViewport extends Viewport {
    /**
     * Ez a Viewport a ScalingViewport alapján jött létre
     * A ResponseViewport alkalmazkodik a telefon kijelzőjéhez
     *
     * @author hdani1337
     * */

    /**
     * @param missingValue
     *      Fekvő módban ez az érték a stage magassága lesz, így a szélesség kerül majd kiszámításra
     *      Álló módban ez az érték a stage szélessége lesz, így a magasság kerül majd kiszámításra
     *
     * Új viewportot csinál egy új {@link OrthographicCamera} típusú kamera létrehozásával.
     * */
    public ResponseViewport(float missingValue) {
        this(missingValue, new OrthographicCamera());
    }

    /**
     * @param missingValue A stage magassága vagy szélessége
     * @param camera A használandó kamera a stagehez
     * */
    public ResponseViewport(float missingValue, Camera camera) {
        if(Gdx.graphics.getWidth() > Gdx.graphics.getHeight())
            setWorldSize(widthLandscape(missingValue), missingValue);
            /**Ha fekvő módban van, akkor a stage szélessége a kiszámított szélesség lesz, a magassága pedig a megadott érték**/
        else
            setWorldSize(missingValue, widthPortrait(missingValue));
            /**Különben portré módban van, akkor a stage szélessége a megadott érték lesz, a magassága pedig a kiszámított magasság**/

        setCamera(camera);/**A Kamera beállítása**/
    }

    /**
     * Visszaadja a stage szélességét !FEKVŐ! módban
     * @param worldHeight A szélességet ez alapján számolja ki
     * */
    private float widthLandscape(float worldHeight)
    {
        float keparany = Gdx.graphics.getWidth() / (Gdx.graphics.getHeight()/1.0f); //A képarány floatban
        float egyArany = worldHeight/9;//Egy arányra eső szélesség (worldHeight) magasságnál ((worldHeight/9)*x)
        int x = 1;//Alap szélességi arány
        while (keparany > (x/9.0f)) x++;//Kiszámolja a telefon szélességi arányát (pl. 16)

        if((int)keparany*(x*egyArany) != Gdx.graphics.getWidth()) return (int)(worldHeight/Gdx.graphics.getHeight() * Gdx.graphics.getWidth());
        //Ha nem pontos a képarány számítása, akkor a stage szélessége legyen a telefon kijelzőjének szélessége (worldHeight) pixelhez viszonyítva

        return x * egyArany;
    }

    /**
     * Visszaadja a stage magasságát !ÁLLÓ! módban
     * @param worldWidth A magasságot ez alapján számolja ki
     * */
    private float widthPortrait(float worldWidth)
    {
        float keparany = (Gdx.graphics.getHeight()/1.0f) / Gdx.graphics.getWidth();//A portré mód miatt csak megkellett cserélnem a kettőt
        float egyArany = worldWidth/9;//Egy arányra eső szélesség (worldWidth) magasságnál ((worldWidth/9)*x)
        int x = 1;//Szélességi arány
        while (keparany > (x/9.0f)) x++;

        if((int)keparany*(x*egyArany) != Gdx.graphics.getWidth()) return (worldWidth/Gdx.graphics.getWidth() * Gdx.graphics.getHeight());
        //Ha nem pontos a képarány számítása, akkor a stage magassága legyen a telefon kijelzőjének magassága (worldWidth) pixelhez viszonyítva

        return x * egyArany;
    }

    /**A Viewport frissítése**/
    @Override
    public void update (int screenWidth, int screenHeight, boolean centerCamera) {
        Vector2 scaled = Scaling.fit.apply(getWorldWidth(), getWorldHeight(), screenWidth, screenHeight);
        int viewportWidth = Math.round(scaled.x);
        int viewportHeight = Math.round(scaled.y);

        // Center.
        setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);

        apply(centerCamera);
    }
}
