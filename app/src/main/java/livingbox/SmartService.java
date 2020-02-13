package livingbox;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SmartService extends Service {
    ScheduledFuture<?> beeperHandle=null;
    private ScheduledExecutorService scheduler;
    private boolean active=false;

    @Override
    public int onStartCommand(Intent intent, final int flags, final int startId) {
        boolean check= checkConnection();
        if (check) {
            Connect.start(this);
            if (!active){
                periodicallyAttempt();
            }
            return START_STICKY;
        }
        else {
            if (active){
                beeperHandle.cancel(true);
                scheduler.shutdown();
                active=false;
            }
            return START_NOT_STICKY;
        }
    }


    public boolean checkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else return false;
    }

    public void periodicallyAttempt() {
        long half_an_hour = (3600)/(2);
        final Runnable beeper = new Runnable() {
            public void run() {
                Connect.start(getApplicationContext());
            }
        };
        scheduler= Executors.newScheduledThreadPool(1);
        beeperHandle = scheduler.scheduleAtFixedRate(beeper, half_an_hour, half_an_hour, TimeUnit.SECONDS);
        active=true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
