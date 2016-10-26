## Preparation
* Install the latest JDK from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* Ensure neither Steam nor CS:GO are running as admin (their executable properties should have [this option](http://i.imgur.com/qZG6Qrs.png) unchecked)

Using IntelliJ IDEA will help you tremendously when programming.

#### If you want to use IntelliJ IDEA, you need to
* Install Git from https://git-scm.com
* Set your **JAVA_HOME** environmental variable to the JDK install directory (The easiest way to do this is with the `setx` command, like `setx JAVA_HOME "C:\Program Files\Java\jdk1.8.0_102"`)
* Finally, install [IntelliJ IDEA](https://www.jetbrains.com/idea/download) (the Community Edition is free and does everything you need)

## Cloning (downloading)

You can use [this link](https://github.com/Jire/Abendigo/archive/master.zip) to download a ZIP archive of the latest update. You can use any archive manager such as 7-Zip, WinRAR, and even the archive manager included with Windows to extract the contents.

#### For those using IntelliJ, you will be forking and pulling from GitHub

* [Sign up for GitHub](https://github.com/join) and come to the [Abendigo repository](https://github.com/Jire/Abendigo)
* [At the top right](http://i.imgur.com/EAJrlFl.png) click on _Watch_ to get update notifications, _Star_ to show some love, and finally _Fork_ to create your own clone repository of Abendigo
* After forking, which may take a minute or so, you should see your own repository which looks identical to Abendigo's, except instead of _Jire/Abendigo_ it will be listed as _Your username/Abendigo_
* In the ["Welcome to IntelliJ IDEA" window](http://i.imgur.com/xV9psyX.png), click on "Check out from Version Control" on the bottom right
* Click on GitHub
* In the "Git Repository URL" box, you can click on the [green Clone or download button](http://i.imgur.com/eBccYex.png) at the top right of your repository page, and copy the URL which should look like `https://github.com/Your name/Abendigo.git`
* Click on _Clone_, which will download the repository from your fork and open it up in IntelliJ IDEA

## Building (compiling)

After you make any changes to the source code, you need to run _build.bat_ to compile your changes.

The first time you build, the Gradle wrapper will download and install Gradle to your system, and then use it to build the project.
This is a process that can take up to 30 minutes on slow connections, but usually takes under 5 minutes.
Once the first build has been completed, future builds should take 30 seconds or less time, and usually around 5 seconds.

If errors are reported, you should go back and look for mistakes you might have made in the source code.

#### For those using IntelliJ IDEA

Your changes will be automatically compiled when running from IntelliJ.

You can also manually compile by clicking [_Build_ -> _Rebuild project_](http://i.imgur.com/XdXfa0Q.png).

## Running

Before you start Abendigo, you should be in a game, completely spawned in, with a weapon out.

You can then use _run.bat_ to start the cheat.

#### For those using IntelliJ IDEA

You can navigate to the main file (_Abendigo.kt_) and right click, then press _Run_.

## Updates

Abendigo uses pattern and netvar scanning to work across many CS:GO updates, which means you usually won't need an update.
Sometimes however, a large CS:GO update can break the pattern scanning and you will need an update.

If you're not using IntelliJ IDEA, you need to redownload (reclone) and start over from the [cloning step](#Cloning).

### For those using IntelliJ IDEA

You can click [_VCS_ on the top menu, then hover over _Git_, then click _Pull_](http://i.imgur.com/FMvnyUe.png).
