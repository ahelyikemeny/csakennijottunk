package hu.csanyzeg.master.MyBaseClasses.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class AssetList {
    public static final String CHARS = "0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_*<>#&@{}[],-.";
    public static final String NUMBERS = "0123456789";
    public static final String SIGNS = "'+!%/=()?:_*<>#&@{}[],-.";

    private HashMap<String, MyAssetDescriptor> map = new HashMap<String, MyAssetDescriptor>();

    public static void collectAssetDescriptor(Class aClass, AssetList targetList){
        for(Field f : aClass.getFields()){
            if (f.getType().isInstance(targetList)){
                Gdx.app.log("Asset", "Class scanning found: " + f.getName() + " in " + aClass.getName() + " class.");
                try {
                    AssetList a = (AssetList)f.get(f);
                    targetList.add(a);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MyAssetDescriptor<Texture> addTexture(String fileName){
        return addTexture(fileName, fileName);
    }
    public MyAssetDescriptor<Texture> addTexture(String fileName, String hash){
        MyAssetDescriptor<Texture> descriptor;
        if (!map.containsKey(hash)){
            map.put(hash != null ? hash : fileName, descriptor = new MyAssetDescriptor<Texture>(fileName, Texture.class));
        }else{
            descriptor = map.get(hash);
        }
        return descriptor;
    }



    public MyAssetDescriptor<TextureAtlas> addTextureAtlas(String fileName){
        return addTextureAtlas(fileName,fileName);
    }

    public MyAssetDescriptor<TextureAtlas> addTextureAtlas(String fileName, String hash){
        MyAssetDescriptor<TextureAtlas> descriptor;
        if (!map.containsKey(hash)){
            map.put(hash != null ? hash : fileName, descriptor = new MyAssetDescriptor<TextureAtlas>(fileName, TextureAtlas.class));
        }else{
            descriptor = map.get(hash);
        }
        return descriptor;
    }



    public MyAssetDescriptor<Sound> addSound(String fileName) {
        return addSound(fileName, fileName);
    }

    public MyAssetDescriptor<Sound> addSound(String fileName, String hash){
        MyAssetDescriptor<Sound> descriptor;
        if (!map.containsKey(hash)){
            map.put(hash != null ? hash : fileName, descriptor = new  MyAssetDescriptor<Sound>(fileName, Sound.class));
        }else{
            descriptor = map.get(hash);
        }
        return descriptor;
    }


    public MyAssetDescriptor<Music> addMusic(String fileName) {
        return addMusic(fileName, fileName);
    }

    public MyAssetDescriptor<Music> addMusic(String fileName, String hash){
        MyAssetDescriptor<Music> descriptor;
        if (!map.containsKey(hash)){
            map.put(hash != null ? hash : fileName, descriptor = new  MyAssetDescriptor<Music>(fileName, Music.class));
        }else{
            descriptor = map.get(hash);
        }
        return descriptor;
    }


    public MyAssetDescriptor<BitmapFont> addFont(String fileName) {
        return addFont(fileName, fileName, 32, Color.WHITE);
    }

    public MyAssetDescriptor<BitmapFont> addFont(String fileName, int size, Color color) {
        return addFont(fileName, fileName, size, color);
    }

    public MyAssetDescriptor<BitmapFont> addFont(String fileName, int size) {
        return addFont(fileName, fileName, size, Color.WHITE);
    }

    public MyAssetDescriptor<BitmapFont> addFont(String fileName, String hash, int size, Color color) {
        return addFont(fileName, hash, size, color, CHARS);
    }


    public MyAssetDescriptor<BitmapFont> addFont(String fileName, String hash, int size, Color color, String chars) {
        MyAssetDescriptor<BitmapFont> descriptor;
        FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        fontParameter.fontFileName = fileName;
        fontParameter.fontParameters.size = size;
        fontParameter.fontParameters.characters = chars;
        fontParameter.fontParameters.color = color;
        fontParameter.fontParameters.magFilter = Texture.TextureFilter.Linear;
        fontParameter.fontParameters.minFilter = Texture.TextureFilter.Linear;
        if (!map.containsKey(hash)) {
            map.put(hash != null ? hash : fileName, descriptor = new MyAssetDescriptor<BitmapFont>(fontParameter.fontFileName, BitmapFont.class, fontParameter));
        }else{
            descriptor = map.get(hash);
        }
        return descriptor;
    }


    public MyAssetDescriptor<BitmapFont> addFont(String fileName, String hash, FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter) {
        MyAssetDescriptor<BitmapFont> descriptor;
        if (!map.containsKey(hash)) {
            map.put(hash != null ? hash : fileName, descriptor = new MyAssetDescriptor<BitmapFont>(fontParameter.fontFileName, BitmapFont.class, fontParameter));
        }else{
            descriptor = map.get(hash);
        }
        return descriptor;
    }



    public void add(MyAssetDescriptor MyAssetDescriptor, String hash) {
        map.put(hash, MyAssetDescriptor);
    }

    public void add(MyAssetDescriptor MyAssetDescriptor) {
        map.put(MyAssetDescriptor.fileName, MyAssetDescriptor);
    }


    public void add(AssetList source) {
        for(Map.Entry<String, MyAssetDescriptor> a : source.getMap().entrySet()){
            map.put(a.getKey(), a.getValue());
        }
    }


    public MyAssetDescriptor getAssetDescriptor(String hash){
        return map.get(hash);
    }

    public HashMap<String, MyAssetDescriptor> getMap() {
        return map;
    }

}
