# Advanced Android Hidden Backdoor

This kind of backdoor differs from any other seen in this context because it goes beyond their typical limitations. It can be hidden in any other application and it is resistant to rebooting or connectivity drop (thanks to Giovanni Colonna code that modified the msfvenom generated code, link [here](https://github.com/giovannicolonna/msfvenom-backdoor-android)).
The peculiarity of this implementation that allows this behaviour is that the activity of the backdoor is completly unrelated with the host app startup.

**The backdoor is invoked by two events:**

* The phone has been switched on or rebooted.
* There has been a connectivity change

In this two cases, the connectivity is checked:
* if the phone is connected, the backdoor tries to instantiate the connection with the attacker and launches a background service that retry every X minutes. (Preconfigured at 30 minutes)
* if it is disconnected and the service has been launched, the backdoor's background service is killed.

**The service will show in the list of all processes with the name of the host app**

# HOW TO
This procedure **allows you to insert the backdoor code inside the host application without modifying its original code** (unlike any other backdoor seen before).
```
Decompile the generated backdoor apk and, if not already done before the compilation, modify attacker's host and port in the /app/src/main/java/livingbox/Connect.java(String URL line). Then decompile the app we want to insert the code in, copy all the files in /app/src/main/java/livingbox/ to a new subfolder in the original decompiled app, in /smali/livingbox (you have to create it).
After all, modify the original AndroidManifest adding the missing permission and the rest of the lines about service and receiver wrote in this project's AndroidManifest.
Rebuild the original app and sign it!
```
**In order to make the backdoor working, you need to launch the host app at least one time.**

**Permission needs to be granted to the host app in order to access the particular resources you are asking for.**

## Created for research purposes. Use only on devices you are authorized to do so. Use it at your own risk.
Tested on Nexus 5, Android 7.1.1
