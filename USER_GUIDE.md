## Preparation
* Install the latest JDK from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* Ensure neither Steam nor CS:GO are running as admin (their executable properties should have [this option](http://i.imgur.com/qZG6Qrs.png) unchecked)

Using IntelliJ IDEA will help you tremendously when programming.

### If you want to use IntelliJ IDEA, you need to
* Install Git from https://git-scm.com
* Set your **JAVA_HOME** environmental variable to the JDK install directory (The easiest way to do this is with the `setx` command, like `setx JAVA_HOME "C:\Program Files\Java\jdk1.8.0_102"`)
* Finally, install [IntelliJ IDEA](https://www.jetbrains.com/idea/download)

## Building (compiling)

After you make any changes to the source code, you need to run _build.bat_ to compile your changes.

The first time you build, the Gradle wrapper will download and install Gradle to your system, and then use it to build the project.
This is a process that can take up to 30 minutes on slow connections, but usually takes under 5 minutes.
Once the first build has been completed, future builds should take 30 seconds or less time, and usually around 5 seconds.

If errors are reported, you should go back and look for mistakes you might have made in the source code.

### For those using IntelliJ IDEA

Your changes will be automatically compiled when running from IntelliJ.

You can also manually compile by clicking [_Build_ -> _Rebuild project_](http://i.imgur.com/XdXfa0Q.png).

## Running

Before you start Abendigo, you should be in a game, completely spawned in, with a weapon out.

You can then use _run.bat_ to start the cheat.

### For those using IntelliJ IDEA

You can navigate to the main file (_Abendigo.kt_) and right click, then press _Run_.
