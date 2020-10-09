package hu.csanyzeg.master.MyBaseClasses.Assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.files.FileHandle;

public class MyAssetDescriptor<T> extends AssetDescriptor<T> {
    public boolean protect = false;
    public MyAssetDescriptor(String fileName, Class assetType) {
        super(fileName, assetType);
    }

    public MyAssetDescriptor(FileHandle file, Class assetType) {
        super(file, assetType);
    }

    public MyAssetDescriptor(String fileName, Class assetType, AssetLoaderParameters params) {
        super(fileName, assetType, params);
    }

    public MyAssetDescriptor(FileHandle file, Class assetType, AssetLoaderParameters params) {
        super(file, assetType, params);
    }
}
