package com.example.apoorvaagupta.livewhiteboard;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by bhavyaaggarwal on 13/01/18.
 */

public class SocketHandler extends Application {
    private Socket socket;

    {
        try {
            socket = IO.socket("http://192.168.1.7:3000/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
