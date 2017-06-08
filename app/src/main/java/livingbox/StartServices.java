package livingbox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartServices extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context, SmartService.class);
        context.startService(intent);
    }
}

