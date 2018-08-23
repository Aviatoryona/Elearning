package brucetoo.videomanage.playermessages;

import android.media.MediaPlayer;

import brucetoo.videomanage.PlayerMessageState;
import brucetoo.videomanage.manager.VideoPlayerManagerCallback;
import brucetoo.videomanage.ui.VideoPlayerView;


/**
 * This PlayerMessage calls {@link MediaPlayer#reset()} on the instance that is used inside {@link VideoPlayerView}
 */
public class Reset extends PlayerMessage {
    public Reset(VideoPlayerView videoPlayerView, VideoPlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected void performAction(VideoPlayerView currentPlayer) {
        currentPlayer.reset();
    }

    @Override
    protected PlayerMessageState stateBefore() {
        return PlayerMessageState.RESETTING;
    }

    @Override
    protected PlayerMessageState stateAfter() {
        return PlayerMessageState.RESET;
    }
}
