package uet.oop.bomberman.entities;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {
    private static BufferedReader in;
    private static PrintWriter out;
    private static Socket socket;

    public Client(Socket s){
        socket = s;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToServer(String mess){
        out.println(mess);
    }

    public String giveFromServer() {
        String mess = null;
        try{
            mess = in.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return mess;
    }
}