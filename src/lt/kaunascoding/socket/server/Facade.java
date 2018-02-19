package lt.kaunascoding.socket.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lt.kaunascoding.socket.server.model.DBActions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Facade {
    public static final int PORT = 8080;


    public static final String ACTION_0 = "rodyk pazymius\n";

    private DBActions _duombaze;

    public static void main(String[] args) {

        //launch(args);
        Facade app = new Facade();

        app.run();

    }

    public void run() {
        try {
            ServerSocket serveris = new ServerSocket(PORT);
            Socket kanalas = null;
            kanalas = serveris.accept(); // cia kodas sustos ir lauks prisijungimo

            ObjectOutputStream outputStream = new ObjectOutputStream(kanalas.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(kanalas.getInputStream());
            String zinute = "";
            _duombaze = new DBActions(outputStream);
            while ((zinute = inputStream.readUTF()) != null) {
                String[] zodziai = zinute.split(":");
                String antrasZodis = zodziai[1].toLowerCase();
                switch (antrasZodis) {
                    case ACTION_0:

                        _duombaze.rodykPazymius();
                        break;
                    default:
                        System.out.println(zinute);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
