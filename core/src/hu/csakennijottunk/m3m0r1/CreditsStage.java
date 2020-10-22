package hu.csakennijottunk.m3m0r1;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class CreditsStage extends MyStage {
    public CreditsStage(MyGame game) {
        super(new ExtendViewport(640, 480), game);
        addBackButtonScreenBackByStackPopListener();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.getMyAssetManager().getFont("latinwd.ttf");
        labelStyle.fontColor = Color.BROWN;

        MyLabel label = new MyLabel(game, "Készítok", labelStyle);
        label.setFontScale(0.5f);
        label.setPosition(20,100);
        addActor(label);

        addActor(new ExitButton(game));



    }
}
