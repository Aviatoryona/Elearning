package brucetoo.videomanage.playermessages;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.FileDescriptor;

import brucetoo.videomanage.manager.VideoPlayerManagerCallback;
import brucetoo.videomanage.ui.VideoPlayerView;

/**
 * This PlayerMessage calls {@link MediaPlayer#setDataSource(FileDescriptor)} on the instance that is used inside {@link VideoPlayerView}
 */
public class SetAssetsDataSourceMessage extends SetDataSourceMessage{

    private final AssetFileDescriptor mAssetFileDescriptor;

    public SetAssetsDataSourceMessage(VideoPlayerView videoPlayerView, AssetFileDescriptor assetFileDescriptor, VideoPlayerManagerCallback callback) {
        super(videoPlayerView, callback);
        mAssetFileDescriptor = assetFileDescriptor;
    }

    @Override
    protected void performAction(VideoPlayerView currentPlayer) {
        currentPlayer.setDataSource(mAssetFileDescriptor);
    }
}
