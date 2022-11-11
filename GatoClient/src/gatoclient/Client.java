/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gatoclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ulacit
 */
public class Client extends Thread{

    private DatagramSocket socket;
    private DatagramPacket out, in;
    private String flag;
    
    
    public Client() {
        try {
            socket = new DatagramSocket();
            write("start");
        } catch (SocketException ex) {

        }
    }

    public void read() {
        try {
            var buffer = new byte[200];
            out = new DatagramPacket(buffer, 0, buffer.length);
            socket.receive(out);
            readString(new String(out.getData()));
            try {
                socket.receive(out);
            } catch (IOException ex) {
                
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    private void readString(String value)
    {
        
        value = value.trim();
        System.out.println(value);
        var splitString = value.split(",");
        
        switch (splitString[0]) {
            case "flag":
                flag = splitString[1];
                System.out.println(flag);
                send();
                break;
            case "play":
                send();
            case "wind":
                System.out.println("win");
            case "end":
                System.out.println("End");
                System.exit(0);
            default:
                throw new AssertionError();
        }
    }

    public void send() {
        var text = JOptionPane.showInputDialog("x,y",flag);
        var data = "play," + flag + "," + text ;
        write(data);
    }

    private void write(String text) {

        try {
            in = new DatagramPacket(text.getBytes(), 0, text.getBytes().length, InetAddress.getLocalHost(), 7410);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.send(in);
        } catch (IOException ex) {

        }
    }
    
    @Override
    public void run()
    {
        while(true)
           read();
    }
}
