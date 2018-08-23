package brucetoo.videomanage.manager;


//import com.brucetoo.listvideoplay.videomanage.meta.MetaData;


import brucetoo.videomanage.meta.MetaData;

public interface PlayerItemChangeListener {
    void onPlayerItemChanged(MetaData currentItemMetaData);
}
