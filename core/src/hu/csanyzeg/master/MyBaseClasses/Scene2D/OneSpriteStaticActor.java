package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import hu.csanyzeg.master.MyBaseClasses.Assets.MyAssetManager;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

/**
 * Created by tuskeb on 2016. 09. 30..
 */
public class OneSpriteStaticActor extends OneSpriteActor {

    public OneSpriteStaticActor(MyGame game, String hash) {
        super(game, new Sprite(game.getMyAssetManager().getTexture(hash)));
    }

    public MyGame getGame() {
        return game;
    }

    public Texture getTexture()
    {
        return sprite.getTexture();
    }

}
