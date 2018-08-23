package brucetoo.videomanage;


//import com.brucetoo.listvideoplay.videomanage.manager.VideoPlayerManagerCallback;
//import com.aviator.buynsell.brucetoo.videomanage.meta.MetaData;
//import com.brucetoo.listvideoplay.videomanage.playermessages.PlayerMessage;
//import com.brucetoo.listvideoplay.videomanage.ui.VideoPlayerView;

import brucetoo.videomanage.manager.VideoPlayerManagerCallback;
import brucetoo.videomanage.meta.MetaData;
import brucetoo.videomanage.playermessages.PlayerMessage;
import brucetoo.videomanage.ui.VideoPlayerView;

public class SetNewViewForPlayback extends PlayerMessage {

    private final MetaData mCurrentItemMetaData;
    private final VideoPlayerView mCurrentPlayer;
    private final VideoPlayerManagerCallback mCallback;

    public SetNewViewForPlayback(MetaData currentItemMetaData, VideoPlayerView videoPlayerView, VideoPlayerManagerCallback callback) {
        super(videoPlayerView, callback);
        mCurrentItemMetaData = currentItemMetaData;
        mCurrentPlayer = videoPlayerView;
        mCallback = callback;
    }

    @Override
    public String toString() {
        return SetNewViewForPlayback.class.getSimpleName() + ", mCurrentPlayer " + mCurrentPlayer;
    }

    @Override
    protected void performAction(VideoPlayerView currentPlayer) {
        mCallback.setCurrentItem(mCurrentItemMetaData, mCurrentPlayer);
    }

    @Override
    protected PlayerMessageState stateBefore() {
        return PlayerMessageState.SETTING_NEW_PLAYER;
    }

    @Override
    protected PlayerMessageState stateAfter() {
        return PlayerMessageState.IDLE;
    }
}