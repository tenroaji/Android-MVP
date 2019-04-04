package lagecong.com.mvp.slider;


import android.app.Activity;
import android.support.annotation.NonNull;
import lagecong.com.mvp.slider.model.SliderConfig;


class ConfigPanelSlideListener extends ColorPanelSlideListener {

    private final SliderConfig config;


    ConfigPanelSlideListener(@NonNull Activity activity, @NonNull SliderConfig config) {
        super(activity, -1, -1);
        this.config = config;
    }


    @Override
    public void onStateChanged(int state) {
        if(config.getListener() != null){
            config.getListener().onSlideStateChanged(state);
        }
    }


    @Override
    public void onClosed() {
        if(config.getListener() != null){
            if(config.getListener().onSlideClosed()) {
                return;
            }
        }
        super.onClosed();
    }


    @Override
    public void onOpened() {
        if(config.getListener() != null){
            config.getListener().onSlideOpened();
        }
    }


    @Override
    public void onSlideChange(float percent) {
        super.onSlideChange(percent);
        if(config.getListener() != null){
            config.getListener().onSlideChange(percent);
        }
    }


    @Override
    protected int getPrimaryColor() {
        return config.getPrimaryColor();
    }


    @Override
    protected int getSecondaryColor() {
        return config.getSecondaryColor();
    }
}
