package com.example.apoorvaagupta.livewhiteboard;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by bhavyaaggarwal on 13/01/18.
 */

public class SocketHandler {
    private Socket socket;

    {
        try {
            socket = IO.socket("http://192.168.0.181:3000/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
