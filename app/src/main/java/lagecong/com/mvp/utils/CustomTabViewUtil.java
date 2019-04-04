package lagecong.com.mvp.utils;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import lagecong.com.mvp.R;

public class CustomTabViewUtil {
    public static void setView(Context context, String url){
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(context.getResources()
                        .getColor(R.color.colorPrimaryDark))
                .setShowTitle(true)
                .setStartAnimations(context,R.anim.enter,R.anim.exit)
                .setExitAnimations(context,R.anim.left_to_right, R.anim.right_to_left)
                .build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }
}
