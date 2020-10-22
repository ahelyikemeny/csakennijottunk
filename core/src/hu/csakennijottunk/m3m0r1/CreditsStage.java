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
        labelStyle.fontColor = Color.BLUE;

addActor(new Creditbackground(game));

        MyLabel label = new MyLabel(game, "Creators:", labelStyle);
        label.setFontScale(0.5f);
        label.setPosition(20,350);
        addActor(label);

        label = new MyLabel(game, "Kele Lorand", labelStyle);
        label.setFontScale(0.5f);
        label.setPosition(20,320);
        addActor(label);

        label = new MyLabel(game, "Fellner Milan", labelStyle);
        label.setFontScale(0.5f);
        label.setPosition(20,290);
        addActor(label);

        label = new MyLabel(game, "Zsebok David Ferenc", labelStyle);
        label.setFontScale(0.5f);
        label.setPosition(20,260);
        addActor(label);

        label = new MyLabel(game, "Kancsal Mate", labelStyle);
        label.setFontScale(0.5f);
        label.setPosition(20,230);
        addActor(label);

        label = new MyLabel(game, "Teacher:", labelStyle);
        label.setFontScale(0.5f);
        label.setPosition(20,200);
        addActor(label);

        label = new MyLabel(game, "Tuske Balazs", labelStyle);
        label.setFontScale(0.5f);
        label.setPosition(20,170);
        label.setColor(0,50,100,100);
        addActor(label);



        addActor(new ExitButton(game));

    }
}
