/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gatoserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author ulacit
 */
public class Server {

    private DatagramSocket socket;
    private DatagramPacket out, in;
    private Client client1;
    private Client client2;
    private static String[][] game = new String[4][4];

    public Server() {
        try {
            socket = new DatagramSocket(7410);
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void read() {
        var buffer = new byte[200];
        out = new DatagramPacket(buffer, 0, buffer.length);
        try {
            socket.receive(out);
            ReadString(new String(out.getData()), out);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ReadString(String value, DatagramPacket client) {
        value = value.trim();
        var splitData = value.split(",");
        System.out.println(value);
        switch (splitData[0]) {
            case "start":
                if (client1 == null) {
                    client1 = new Client(client.getPort(), client.getAddress(), "x");
                    write("flag,x", client.getPort(), client.getAddress());
                } else {
                    client2 = new Client(client.getPort(), client.getAddress(), "o");
                    write("flag,o", client.getPort(), client.getAddress());
                    //  write("play", client1.getPort(), client1.getIp());
                }
                break;
            case "play":
                var flag = splitData[1];
                var x1 = Integer.parseInt(splitData[2]);
                var y1 = Integer.parseInt(splitData[3]);
                if (game[x1][y1].isEmpty()) {
                    game[x1][y1] = flag;
                }
                System.out.println(flag + " " + x1);

                if (win(flag)) {
                    
                } else {
                    if (flag.equals("x")) {
                        write("play", client2.getPort(), client2.getIp());
                    } else {
                        write("play", client1.getPort(), client1.getIp());
                    }
                }
                break;

            default:
                throw new AssertionError();
        }

        print();
    }

    private boolean win(String flag) {
        
        if (game[0][0].equals(game[0][3])) {
            game[0][0]=null;
            game[0][3]=null;
             write("Pareja encontrada", client1.getPort(), client1.getIp());
             write("Pareja encontrada", client2.getPort(), client2.getIp());
        } else if(game[0][1].equals(game[1][1])) {
            game[0][1]=null;
            game[1][1]=null;
            return true;
            }  else if(game[0][2].equals(game[3][0])) {
                game[0][2]=null;
                game[3][0]=null;
                 return true;
             }  else if(game[1][2].equals(game[2][2])) {
                 game[1][2]=null;
                  game[2][2]=null;
                 return true;
                 }  else if(game[2][0].equals(game[2][1])) {
                     game[2][0]=null;
                     game[2][1]=null;
                 return true;
                    }else if(game[2][3].equals(game[3][3])) {
                        game[2][3]=null;
                        game[3][3]=null;
                        return true;
                    }   else if(game[3][1].equals(game[1][0])) {
                        game[3][1]=null;
                        game[1][0]=null;
                        return true;
                     }      else if(game[1][3].equals(game[3][2])) {
                         game[1][3]=null;
                         game[3][2]=null;
                             return true;
                                  }

        return false;
    }

    public void write(final String text, final int port, final InetAddress IP) {
        in = new DatagramPacket(text.getBytes(), 0, text.getBytes().length, IP, port);
        try {
            socket.send(in);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print() {
     
        for (var i : game) { 
                game[0][0]="1";
                game[0][1]="3";
                game[0][2]="2";
                game[0][3]="1";
                game[1][0]="7";
                game[1][1]="3";
                game[1][2]="4";
                game[1][3]="8";
                game[2][0]="5";
                game[2][1]="5";
                game[2][2]="4";
                game[2][3]="6";
                game[3][0]="2";
                game[3][1]="7";
                game[3][2]="8";
                game[3][3]="6";
            
            for (var j : i) {
                
                System.out.print("|"+j+"|");
            }
            System.out.println("");
        }
    }
    
    

    static {
        for (var i = 0; i < 4; i++) {
            for (var j = 0; j < 4; j++) {
               
                game[i][j] = new String();
            }
        }
    }
}
