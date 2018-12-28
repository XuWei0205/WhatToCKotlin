package hanyu.com.whattockotlin.commons;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by HanYu on 2018/12/28.
 */
public class TestUtil {
    public   int getStatusBarHeight(Context context) {
        int result = 0;
        try {
            int resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                int sizeOne = context.getResources().getDimensionPixelSize(resourceId);
                Log.e(getClass().getSimpleName() + "sizeOne ====== ", String.valueOf(sizeOne));
                int sizeTwo = Resources.getSystem().getDimensionPixelSize(resourceId);
                Log.e(getClass().getSimpleName() + "sizeTwo ====== ", String.valueOf(sizeTwo));

                float densityOne = context.getResources().getDisplayMetrics().density;
                Log.e(getClass().getSimpleName() + "densityOne ====== ", String.valueOf(densityOne));
                float densityTwo = Resources.getSystem().getDisplayMetrics().density;
                Log.e(getClass().getSimpleName() + "densityTwo ====== ", String.valueOf(densityTwo));

                if (sizeTwo >= sizeOne) {
                    return sizeTwo;
                } else {
                    return Math.round(sizeOne * densityTwo / densityOne);
                }
            }
        } catch (Resources.NotFoundException ignored) {
            return 0;
        }
        return result;
    }
}
