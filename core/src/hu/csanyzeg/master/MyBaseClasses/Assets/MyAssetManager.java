package hu.csanyzeg.master.MyBaseClasses.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.awt.Font;
import java.util.Map;

import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;

public class MyAssetManager implements Disposable {

    public interface DebugChangeListener{
        public void change(String info);
    }

    public interface ProgressChangeListener {
        public void change(int percent);
    }


    public Texture.TextureFilter textureMinFilter = Texture.TextureFilter.Linear;
    public Texture.TextureFilter textureMagFilter = Texture.TextureFilter.Linear;


    protected DebugChangeListener DebugChangeListener = null;
    protected ProgressChangeListener progressChangeListener = null;

    private AssetManager assetManager = new AssetManager();


    private String Debug = "null";
    private int progress = 0;

    AssetList assetList = new AssetList();

    public MyAssetManager() {
        Texture.setAssetManager(assetManager);
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".otf", new FreetypeFontLoader(resolver));
        DebugChangeListener = new DebugChangeListener() {
            @Override
            public void change(String info) {
                Gdx.app.log("Asset", "MyAssetManager: " + info);
            }
        };
    }

    public int getProgress() {
        return progress;
    }

    private void setProgress(int progress) {
        this.progress = progress;
        if (progressChangeListener != null) {
            progressChangeListener.change(progress);
        }
    }

    public boolean isLoadingComplete(){
        return assetManager.isFinished();
    }

    public void changeAssets(AssetList to){
        setDebug("Loading...");
        setProgress(0);

        Array<String> remove = new Array<>();

        if (to!=null) {
            for (Map.Entry<String, MyAssetDescriptor> e : assetList.getMap().entrySet()) {
                if (!to.getMap().containsKey(e.getKey())) {
                    if (e.getValue().protect) {
                        setDebug("Protect: " + e.getKey());
                    } else {
                        remove.add(e.getKey());
                        setDebug("Unused: " + e.getKey());
                    }
                }
            }
        }
/*
        if (protect != null) {
            for (Map.Entry<String, MyAssetDescriptor> e : protect.getMap().entrySet()) {
                setDebug("Protect: " + e.getValue().fileName);
                remove.remove(e.getKey());
            }
        }
*/
        for(String s : remove){
            MyAssetDescriptor MyAssetDescriptor = assetList.getMap().remove(s);
            setDebug("Remove: " + MyAssetDescriptor.fileName);
            assetManager.unload(MyAssetDescriptor.fileName);
        }


        if (to != null) {
            for (Map.Entry<String, MyAssetDescriptor> e : to.getMap().entrySet()) {
                if (!assetList.getMap().containsKey(e.getKey())) {
                    assetManager.load(e.getValue());
                    assetList.getMap().put(e.getKey(), e.getValue());
                    setDebug("Queue: " + e.getKey());
                }
            }
        }

        setDebug("!!!! CALL updateManager FROM LOADINGSCREEN WHILE DONE. !!!!");
    }

    public void updateManager(){
        assetManager.update();
        setProgress((int)(assetManager.getProgress()*100));
        setDebug(getProgress() + " % ("+ getActualLoadingName() +")");
    }

    private Array<String> lastList = new Array<>();
    private String lastLoadedName = "";
    public String getActualLoadingName(){
        Array<String> a = assetManager.getAssetNames();
        a.removeAll(lastList, true);
        if (a.size>0){
            lastLoadedName = a.get(0);
        }
        lastList = assetManager.getAssetNames();
        return lastLoadedName;
    }


    public void loadAssetAsync(MyAssetDescriptor MyAssetDescriptor, String hash){
        if (!assetList.getMap().containsKey(hash)) {
            assetManager.load(MyAssetDescriptor);
            assetList.add(MyAssetDescriptor, hash);
            setDebug("Queue: " + hash + " !!!! CALL updateManager FROM LOADINGSCREEN WHILE DONE. !!!!");
        }else{
            setDebug("Queue: " + hash + " already loaded.");
        }
    }

    public void loadAsset(MyAssetDescriptor MyAssetDescriptor, String hash){
        if (!assetList.getMap().containsKey(hash)) {
            setDebug("Loading: " + hash);
            assetManager.load(MyAssetDescriptor);
            assetList.add(MyAssetDescriptor, hash);
            while (!assetManager.isFinished()) {
                assetManager.update();
            }
            setDebug("Loading: " + hash + " done.");
        }else{
            setDebug("Loading: " + hash + " already loaded.");
        }
    }

    public void loadAsset(MyAssetDescriptor MyAssetDescriptor){
        loadAsset(MyAssetDescriptor, MyAssetDescriptor.fileName);
    }

    public void loadAsset(AssetList assetList) {
        for (Map.Entry<String, MyAssetDescriptor> e : assetList.getMap().entrySet()) {
            loadAsset(e.getValue(), e.getKey());
        }
    }

    public void loadAssetAsync(AssetList assetList) {
        for (Map.Entry<String, MyAssetDescriptor> e : assetList.getMap().entrySet()) {
            loadAssetAsync(e.getValue(), e.getKey());
        }
    }

    public Texture getTexture(String hash){
        try {
            Texture t = assetManager.get((MyAssetDescriptor<Texture>) (assetList.getAssetDescriptor(hash)));
            t.setFilter(textureMinFilter, textureMagFilter);
            return t;
        }catch (NullPointerException e){
            setDebug("!!!! WARNING !!!! AZ ASSET NINCS ELORE BETOLTVE, A BETOLTESE KESLELTETI A JATEKMENETET: " + hash);
            loadAsset(new MyAssetDescriptor(hash, Texture.class));
            return getTexture(hash);
        }
    }

    public TextureAtlas getTextureAtlas(String hash){
        try {
            TextureAtlas t = assetManager.get((MyAssetDescriptor<TextureAtlas>) (assetList.getAssetDescriptor(hash)));
            for (Texture texture : t.getTextures()) {
                texture.setFilter(textureMinFilter, textureMagFilter);
            }
            return t;
        }catch (NullPointerException e){
            setDebug("!!!! WARNING !!!! AZ ASSET NINCS ELORE BETOLTVE, A BETOLTESE KESLELTETI A JATEKMENETET: " + hash);
            loadAsset(new MyAssetDescriptor(hash, TextureAtlas.class));
            return getTextureAtlas(hash);
        }
    }

    public Sound getSound(String hash){
        try {
            return assetManager.get((MyAssetDescriptor<Sound>)(assetList.getAssetDescriptor(hash)));
        }catch (Exception e){
            setDebug("!!!! WARNING !!!! AZ ASSET NINCS ELORE BETOLTVE, A BETOLTESE KESLELTETI A JATEKMENETET: " + hash);
            loadAsset(new MyAssetDescriptor(hash, Sound.class));
            return assetManager.get((MyAssetDescriptor<Sound>)(assetList.getAssetDescriptor(hash)));
        }
    }

    public Music getMusic(String hash){
        try {
            return assetManager.get((MyAssetDescriptor<Music>)(assetList.getAssetDescriptor(hash)));
        }catch (Exception e){
            setDebug("!!!! WARNING !!!! AZ ASSET NINCS ELORE BETOLTVE, A BETOLTESE KESLELTETI A JATEKMENETET: " + hash);
            loadAsset(new MyAssetDescriptor(hash, Music.class));
            return assetManager.get((MyAssetDescriptor<Music>)(assetList.getAssetDescriptor(hash)));
        }
    }

    public BitmapFont getFont(String hash){
        try {
            return assetManager.get((MyAssetDescriptor<BitmapFont>) (assetList.getAssetDescriptor(hash)));
        }catch (Exception e){
            setDebug("!!!! WARNING !!!! AZ ASSET NINCS ELORE BETOLTVE, A BETOLTESE KESLELTETI A JATEKMENETET: " + hash);
            loadAsset((new AssetList()).addFont(hash));
            return assetManager.get((MyAssetDescriptor<BitmapFont>) (assetList.getAssetDescriptor(hash)));
        }
    }

    public String getDebug() {
        return Debug;
    }

    public DebugChangeListener getDebugChangeListener() {
        return DebugChangeListener;
    }

    public void setDebugChangeListener(DebugChangeListener DebugChangeListener) {
        this.DebugChangeListener = DebugChangeListener;
    }

    public Texture.TextureFilter getTextureMinFilter() {
        return textureMinFilter;
    }

    public void setTextureMinFilter(Texture.TextureFilter textureMinFilter) {
        this.textureMinFilter = textureMinFilter;
    }

    public Texture.TextureFilter getTextureMagFilter() {
        return textureMagFilter;
    }

    public void setTextureMagFilter(Texture.TextureFilter textureMagFilter) {
        this.textureMagFilter = textureMagFilter;
    }

    public void setDebug(String Debug) {
        if (Debug.compareTo(this.Debug) != 0){
            this.Debug = Debug;
            if (DebugChangeListener!=null){
                DebugChangeListener.change(Debug);
            }
        }
    }

    public void dispose(){
        assetManager.dispose();
        assetList.getMap().clear();
        setDebug("Dispose");
    }

}
