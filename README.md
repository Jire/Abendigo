# Abendigo
A free as in both freedom and free beer game modding/hacking platform using Kotlin on the JVM.

[![Build Status](https://travis-ci.org/Jire/Abendigo.svg?branch=master)](https://travis-ci.org/Jire/Abendigo)
[![Dependency Status](https://www.versioneye.com/user/projects/5784c44276ef40003fba815c/badge.svg?style=flat)](https://www.versioneye.com/user/projects/5784c44276ef40003fba815c)
[![license](https://img.shields.io/github/license/Jire/Abendigo.svg)](https://github.com/Jire/Abendigo/blob/master/LICENSE.txt)

---

The project was originally started as a closed-source cheat and everything was done in-house. We since decided to move the project on to a new vision: a free and open-source modding platform.

Initially there will be a focus on CS:GO because it was our original game target. In the future other games may be supported.

## Dependencies

We use some really cool technology to drive Abendigo. Here's a list:

- [Arrowhead](https://github.com/Jire/Arrowhead) for our high-level interfacing with processes
- [Java Native Access (JNA)](https://github.com/java-native-access/jna) as the backbone for interfacing with native libraries
- [Netty](http://netty.io) for our controller server's networking

## Building from source

You can watch this video guide to show you how to build from source.

[![View on YouTube](http://i.imgur.com/fzn2l86.png)](https://www.youtube.com/watch?v=0jd0fDMDjxw)

## Official controller client

You can find our official controller client here: https://github.com/Jire/Abendigo-Controller

Our controller protocol is very simple, you can also easily make your own controller.