package com.mosi;

import com.mosi.server.SocketServer;

public class Application {
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer(8808);
        try {
            socketServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
