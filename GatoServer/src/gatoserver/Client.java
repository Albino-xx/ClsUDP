/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gatoserver;

import java.net.InetAddress;

/**
 *
 * @author ulacit
 */
public class Client {
    private int port;
    private InetAddress ip;
    private String flag;

    public Client(int port, InetAddress ip, String flag) {
        this.port = port;
        this.ip = ip;
        this.flag = flag;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    
}
