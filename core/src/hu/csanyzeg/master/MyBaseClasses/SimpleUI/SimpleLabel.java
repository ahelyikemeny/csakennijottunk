package hu.csanyzeg.master.MyBaseClasses.SimpleUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.ShapeType;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleBody;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleBodyType;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleWorld;
import hu.csanyzeg.master.MyBaseClasses.SimpleWorld.SimpleWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;

public class SimpleLabel extends MyGroup {
    public enum ColorMode{
        byGroup, byChar
    }

    private Array<SimpleWorldHelper> simpleWorldHelpers = new Array<SimpleWorldHelper>();
    private SimpleLabelListener simpleUIListener = null;
    private ColorMode colorMode = ColorMode.byGroup;

    public SimpleLabel(MyGame game, SimpleWorld world, CharSequence text, String fontHash, float r, float g, float b, float a, float fontSize, SimpleLabelListener simpleUIListener) {
        super(game);
        Label.LabelStyle style;
        style = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle();
        style.font = game.getMyAssetManager().getFont(fontHash);
        Color color = new Color();

        color.a = a;
        color.r = r;
        color.g = g;
        color.b = b;

        style.fontColor = color;

        create(world, text, style, fontSize, simpleUIListener);
    }

    public SimpleLabel(MyGame game, SimpleWorld world, CharSequence text, Label.LabelStyle style, float fontSize) {
        super(game);
        create(world, text, style, fontSize, null);
    }

    public SimpleLabel(MyGame game, SimpleWorld world, CharSequence text, Label.LabelStyle style, float fontSize, SimpleLabelListener simpleUIListener) {
        super(game);
        create(world, text, style, fontSize, simpleUIListener);
    }

    private void create(SimpleWorld world, CharSequence text, Label.LabelStyle style, float fontSize, SimpleLabelListener simpleUIListener){
        SimpleWorldHelper mainHelper = null;
        if (world != null){
            setActorWorldHelper(mainHelper = new SimpleWorldHelper(world, this, ShapeType.Rectangle, SimpleBodyType.Ghost));
        }


        float position = 0f;

        this.simpleUIListener = simpleUIListener;
        int i = 0;

        for (char c : text.toString().toCharArray()) {
            MyLabel myLabel = new MyLabel(game, c + "", style);
            float scale = fontSize / myLabel.getPrefHeight();
            myLabel.setFontScale(scale);
            myLabel.setWidthWhithAspectRatio(myLabel.getWidth() * scale);


            MyGroup myGroup = new MyGroup(game){
                private MyLabel label;

                @Override
                public void setColor(Color color) {
                    super.setColor(color);
                    label.setColor(color);
                }

                @Override
                public void setColor(float r, float g, float b, float a) {
                    super.setColor(r, g, b, a);
                    label.setColor(r,g,b,a);
                }

                @Override
                public void addActor(Actor actor) {
                    super.addActor(actor);
                    label = (MyLabel)actor;
                }
            };
            myGroup.addActor(myLabel);
            myGroup.setWidth(myLabel.getWidth());
            myGroup.setHeight(myLabel.getHeight());
            myGroup.setX(position);
            SimpleBody simpleBody = null;
            if (world != null) {
                SimpleWorldHelper simpleWorldHelper = new SimpleWorldHelper(world, myGroup, ShapeType.Null, SimpleBodyType.Ghost);
                myGroup.setActorWorldHelper(simpleWorldHelper);
                simpleBody = ((SimpleWorldHelper) myGroup.getActorWorldHelper()).getBody();
                simpleBody.setUserData(myLabel);
                simpleWorldHelpers.add(simpleWorldHelper);
            }

            addActor(myGroup);

            position += myLabel.getPrefWidth() * 1.2f;

            if (world != null && simpleUIListener != null) {
                simpleUIListener.onCharAdd(this, simpleBody, myGroup, myLabel, i);
            }
            i++;
        }
        setWidth(position);
        setHeight(fontSize);
        if (mainHelper != null) {
            mainHelper.getBody().setPosition(getX(), getY());
            mainHelper.getBody().setSize(getWidth(), getHeight());
            mainHelper.getBody().setOriginToCenter();
        }
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        if (getActorWorldHelper() != null && simpleUIListener != null && isVisible()){
            simpleUIListener.onShow(this, simpleWorldHelpers);
        }

    }

    public SimpleLabelListener getSimpleUIListener() {
        return simpleUIListener;
    }

    public void setSimpleUIListener(SimpleLabelListener simpleUIListener) {
        this.simpleUIListener = simpleUIListener;
    }

    public void removeSimpleUIListener() {
        this.simpleUIListener = null;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        if (colorMode == ColorMode.byGroup) {
            for (SimpleWorldHelper helper : simpleWorldHelpers) {
                ((MyLabel) helper.getBody().getUserData()).setColor(color);
            }
        }
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        super.setColor(r, g, b, a);
        for(SimpleWorldHelper helper : simpleWorldHelpers){
            ((MyLabel)helper.getBody().getUserData()).setColor(r, g, b, a);
        }
    }

    public ColorMode getColorMode() {
        return colorMode;
    }

    public void setColorMode(ColorMode colorMode) {
        this.colorMode = colorMode;
    }

    @Override
    public boolean remove() {
        for(SimpleWorldHelper helper : simpleWorldHelpers){
            helper.remove();
        }
        return super.remove();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (simpleUIListener!=null) {
            if (visible) {
                simpleUIListener.onShow(this, simpleWorldHelpers);
            } else {
                simpleUIListener.onHide(this, simpleWorldHelpers);
            }
        }
    }

    public void hide(){
        if (simpleUIListener!=null) {
            if (!isVisible()) {
                simpleUIListener.onHide(this, simpleWorldHelpers);
            }
        }
    }
}
