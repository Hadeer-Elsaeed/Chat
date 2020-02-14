
package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Hadeer
 */
public class ChatServer extends Application {
    
    ServerSocket myServerSocket;
    Socket s;
    DataInputStream dis;
    PrintStream ps;
    TextArea rcv;
    
    @Override
    public void start(Stage primaryStage) {
        rcv= new TextArea();
        rcv.setEditable(false);
        try {
            myServerSocket = new ServerSocket(5005);

        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
        StackPane root = new StackPane();
        root.getChildren().add(rcv);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    public void run(){
        while(true){
            try {
                s = myServerSocket.accept();
                dis = new DataInputStream(s.getInputStream());
                ps = new PrintStream(s.getOutputStream());  
                
                rcv.setText(dis.readUTF());
            } catch (IOException ex) {
                Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        new ChatServer();
    }
    
}
