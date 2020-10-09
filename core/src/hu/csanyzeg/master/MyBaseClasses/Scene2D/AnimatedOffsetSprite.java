package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

public class AnimatedOffsetSprite extends OffsetSprite {

    protected float elapsedTime = 0;
    protected final TextureAtlas textureAtlas;
    protected float fps = 30;
    protected boolean running = true;
    protected boolean looping = true;
    protected float animationTime = 0;

    private int actualFrame = 0;
    private int prevFrame = 0;

    @Deprecated
    public AnimatedOffsetSprite(TextureAtlas textureAtlas, float xOffset, float yOffset) {
        super(textureAtlas.getRegions().get(0).getTexture(), xOffset, yOffset);
        this.textureAtlas = textureAtlas;
    }

    @Deprecated
    public AnimatedOffsetSprite(TextureAtlas textureAtlas, float xOffset, float yOffset, float width, float height) {
        super(textureAtlas.getRegions().get(0).getTexture(), xOffset, yOffset, width, height);
        this.textureAtlas = textureAtlas;
    }


    public AnimatedOffsetSprite(MyGame game, String hash, float xOffset, float yOffset) {
        super(game, hash, xOffset, yOffset);
        textureAtlas = game.getMyAssetManager().getTextureAtlas(hash);
    }

    public AnimatedOffsetSprite(MyGame game, String hash, float xOffset, float yOffset, float width, float height) {
        super(game, hash, xOffset, yOffset, width, height);
        textureAtlas = game.getMyAssetManager().getTextureAtlas(hash);
    }

    public void init() {
        setSize(textureAtlas.getRegions().get(0).getRegionWidth(), textureAtlas.getRegions().get(0).getRegionHeight());
    }

    public float getFps() {
        return fps;
    }

    public void setFps(float fps) {
        this.fps = fps;
    }



    public void act(float delta) {
        elapsedTime += delta;
        if (running) {
            animationTime+=delta;
            int actualFrame=((int) (animationTime * fps)) % textureAtlas.getRegions().size;
            if (actualFrame<prevFrame){
                repeated();
                if (!looping) {
                    stop();
                    return;
                }
            }
            setFrame(actualFrame);
            prevFrame = actualFrame;
        }
    }

    protected void repeated(){
    }

    public void setFrame(int frame)
    {
        if(textureAtlas == null) return;
        this.setRegion(textureAtlas.getRegions().get(frame % textureAtlas.getRegions().size));
    }

    public void setFramePercent(float percent) {
        setFrame((int)(getFrameCount()*percent));
    }

    public int getFrameCount()
    {
        return textureAtlas.getRegions().size;
    }

    public void start()
    {
        running = true;
    }

    public void stop()
    {
        running = false;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }


}
