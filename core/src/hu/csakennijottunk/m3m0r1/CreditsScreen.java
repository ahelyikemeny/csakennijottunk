package hu.csakennijottunk.m3m0r1;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class CreditsScreen extends MyScreen {
    public CreditsScreen(MyGame game) {
        super(game);
    }

    @Override
    protected void afterAssetsLoaded() {
        addStage(new CreditsStage(game),0,true);
    }

    @Override
    public AssetList getAssetList() {
        return null;
    }
}
