package brucetoo.videomanage.playermessages;

import android.media.MediaPlayer;

import brucetoo.videomanage.PlayerMessageState;
import brucetoo.videomanage.manager.VideoPlayerManagerCallback;
import brucetoo.videomanage.ui.VideoPlayerView;


/**
 * This PlayerMessage calls {@link MediaPlayer#pause()} ()} on the instance that is used inside {@link VideoPlayerView}
 */
public class Pause extends PlayerMessage {
    public Pause(VideoPlayerView videoView, VideoPlayerManagerCallback callback) {
        super(videoView, callback);
    }

    @Override
    protected void performAction(VideoPlayerView currentPlayer) {
        currentPlayer.pause();
    }

    @Override
    protected PlayerMessageState stateBefore() {
        return PlayerMessageState.PAUSING;
    }

    @Override
    protected PlayerMessageState stateAfter() {
        return PlayerMessageState.PAUSED;
    }
}
