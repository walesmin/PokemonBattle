package pokemonBattle;

import java.io.*;
import java.net.*;

import javax.swing.*;

import game.GameLogic;
import game.Player;
import pokemon.Charmander;
import pokemon.Pikachu;
import pokemon.Pokemon;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Client {
    private JFrame loginFrame;
    private Waitroom waitroom;
    private PrintWriter out;
    private BufferedReader in;
    private String thisPlayerID;

    public Client() {
        loginFrame = new Login("Login", this::createChatScreen);
    }

    private void createChatScreen() {
        Point location = loginFrame.getLocation();
        loginFrame.setVisible(false);

        waitroom = new Waitroom("대기실", this);
        waitroom.setLocation(location);
        waitroom.getSendButton().addActionListener(e -> sendMessage());
        String username = JOptionPane.showInputDialog("유저 이름을 입력하세요:");

        startClient(username);
    }

    private void startClient(String username) {
        try {
            Socket socket = new Socket("localhost", 9999);
            waitroom.appendText(username + "게임에 접속하였습니다..\n\n");

            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(username);
            
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
                @Override
                protected Void doInBackground() throws Exception {
                    String message;
                    while ((message = in.readLine()) != null) {
                        if (message.startsWith("ID:")) {
                            thisPlayerID = message.substring(3);
                        }
                        publish(message);
                        waitroom.processServerMessage(message);
                        
                }
                    return null;
                }

                @Override
                protected void process(List<String> chunks) {
                    for (String message : chunks) {
                        waitroom.appendText("상대방: " + message + "\n");
                    }
                }
            };
            worker.execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    public void sendReadyStatus() {
    	out.println("READY:" + thisPlayerID); //서버에 준비 전송
    }
    public void sendBattleReadyStatus() {
    	out.println("BATTLE:" + thisPlayerID); //배틀 준비 완료 시 전송 
    }
    
    
    private void sendMessage() {
        String message = waitroom.getInputBox().getText();
        if (!message.isEmpty()) {
            waitroom.appendText("나: " + message + "\n");
            out.println(message);
            waitroom.getInputBox().setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
        Pokemon poke2 = new Pikachu();
		Pokemon poke = new Charmander();
		
		Player player1 = new Player(poke);
		Player player2 = new Player(poke2);
		
//		player1.useSkill("1");
//		player2.useSkill("1");
//		
//		poke.useSkill("1");
//		poke2.useSkill("1");
//		poke.useSkill("2");
//		poke2.useSkill("2");
		
		//GameLogic logic = new GameLogic(poke, poke2);
		
		//System.out.println(logic.calculateDamage(poke, poke2));
    }
}