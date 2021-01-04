package reversemulti;

import static util.NetworkUtilities.getLocalHost;


import java.net.*;

import time.TimeServer;

import java.io.*;
 
/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 *
 * @author www.codejava.net
 */
public class ReverseClient {
 
    public static void main(final String[] args) {
        /*
         * OK to use try with resources here. Its ok to close all open streams when the user
         * exits the loop. 
         */
        try (Socket socket = new Socket(getLocalHost(), TimeServer.DEFAULT_PORT)) {
            try (InputStream input = socket.getInputStream()) {
               try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                   try (OutputStream output = socket.getOutputStream()) {
                       try (PrintWriter writer = new PrintWriter(output, true)) {

                           final Console console = System.console();
                           String text;
                           do {
                               text = console.readLine("Enter text: ");
                               writer.println(text);
                               System.out.println(reader.readLine());
                           } while (!"bye".equals(text));
                       }
                   }
               }
            }
         } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}