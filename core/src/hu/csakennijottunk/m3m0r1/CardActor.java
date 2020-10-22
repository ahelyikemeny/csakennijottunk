package hu.csakennijottunk.m3m0r1;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class CardActor extends OneSpriteStaticActor {
    private static final String[] images = {"lap1.png", "lap2.png", "lap3.png", "lap4.png", "lap5.png", "lap6.png", "lap7.png", "lap8.png"};

    protected int imageID;

    public CardActor(MyGame game, int imageID, float x, float y) {
        super(game, images[imageID]);
        setSize(70,100);
        setPosition(x, y);
        this.imageID = imageID;

        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //System.out.println(imageID);
                //System.out.println(getStage());
                ((FirstStage)getStage()).clickCard(CardActor.this);
            }
        });

        hideCard();
    }

    public int getImageID() {
        return imageID;
    }

    public void hideCard(){
        setTexture("hatulja.png");
    }

    public void showCard(){
        setTexture(images[imageID]);
    }

}
