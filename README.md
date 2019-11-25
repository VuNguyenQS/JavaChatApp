# JavaChatApp
This is a simple version of chat app for practicing multithreaded and socket programming in Java.

## Introduction
This app is a console application,the server and client need to run on the same network. Some features i have built for it is:
* Both client and server can detect loosing the connection from the other side.
* It only supports chats for all online users that means when you send a message, the rest of online users all see that messagge.

### Prerequisites
The code has been run and tested on Java 11, but i think this one would be compiled and run properly if you use java 8 (or later).

### Compile and run this app from the source code.


* Server side:
To compile server code you follow the below statement:
```
javac server/ChatSercer.java
```
You need to give the port number for running server by command line:
```
java server.ChatServer 8000
```
* Client side:
Compiling step is finished by this command:
```
javac client/ChatClient.java
```
On this side, you must specify the name of the server or Ip address and the port number that server has run.
```
java client.ChatClient localhost 8000
```
