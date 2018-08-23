package brucetoo.videomanage.playermessages;

import android.media.MediaPlayer;

import brucetoo.videomanage.PlayerMessageState;
import brucetoo.videomanage.manager.VideoPlayerManagerCallback;
import brucetoo.videomanage.ui.VideoPlayerView;

/**
 * This PlayerMessage calls {@link MediaPlayer#release()} on the instance that is used inside {@link VideoPlayerView}
 */
public class Release extends PlayerMessage {

    public Release(VideoPlayerView videoPlayerView, VideoPlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected void performAction(VideoPlayerView currentPlayer) {
        currentPlayer.release();
    }

    @Override
    protected PlayerMessageState stateBefore() {
        return PlayerMessageState.RELEASING;
    }

    @Override
    protected PlayerMessageState stateAfter() {
        return PlayerMessageState.RELEASED;
    }
}
