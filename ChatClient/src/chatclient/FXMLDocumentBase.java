package chatclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class FXMLDocumentBase extends BorderPane {

    protected final TextArea textArea;
    protected final SplitPane splitPane;
    protected final AnchorPane anchorPane;
    protected final TextField textField;
    protected final Button send;
    protected final Lighting lighting;

    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;

    public FXMLDocumentBase() {

        textArea = new TextArea();
        splitPane = new SplitPane();
        anchorPane = new AnchorPane();
        textField = new TextField();
        send = new Button();
        lighting = new Lighting();

        try {
            mySocket = new Socket("127.0.0.1", 5005);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(textArea, javafx.geometry.Pos.CENTER);
        textArea.setEditable(false);
        textArea.setPrefHeight(319.0);
        textArea.setPrefWidth(600.0);
        setCenter(textArea);

        BorderPane.setAlignment(splitPane, javafx.geometry.Pos.CENTER);
        splitPane.setPrefHeight(71.0);
        splitPane.setPrefWidth(600.0);

        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(53.0);
        anchorPane.setPrefWidth(598.0);

        textField.setLayoutX(-3.0);
        textField.setLayoutY(-3.0);
        textField.setPrefHeight(73.0);
        textField.setPrefWidth(512.0);

        send.setLayoutX(510.0);
        send.setLayoutY(1.0);
        send.setMnemonicParsing(false);
        send.setPrefHeight(69.0);
        send.setPrefWidth(87.0);
        send.setText("Send");
        send.setTextFill(javafx.scene.paint.Color.valueOf("#282f32"));
        send.setFont(new Font("System Bold", 22.0));

        send.setEffect(lighting);
        setBottom(splitPane);

        anchorPane.getChildren().add(textField);
        anchorPane.getChildren().add(send);
        splitPane.getItems().add(anchorPane);

        send.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

              
                String msg = textField.getText();
               
                ps.println(msg);
                textField.clear();

            }

        });

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (dis != null) {
                            String msg = dis.readLine();
                            textArea.appendText(msg + "\n");                           
                            
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentBase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }.start();

    }
}
