package brucetoo.videomanage.manager;

import brucetoo.videomanage.PlayerMessageState;
import brucetoo.videomanage.meta.MetaData;
import brucetoo.videomanage.ui.VideoPlayerView;

///**
// * This callback is used by {@link com.brucetoo.listvideoplay.videomanage.playermessages.PlayerMessage}
// * to get and set data it needs
// */
public interface VideoPlayerManagerCallback {

    void setCurrentItem(MetaData currentItemMetaData, VideoPlayerView newPlayerView);

    void setVideoPlayerState(VideoPlayerView videoPlayerView, PlayerMessageState playerMessageState);

    PlayerMessageState getCurrentPlayerState();
}
