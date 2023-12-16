package pokemonBattle;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Waitroom extends JFrame {
    private JTextArea textArea;
    private JTextField inputBox;
    private JButton sendButton;
    private JScrollPane scrollPane;
    private JPanel contentPane;


    
    public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Waitroom frame = new Waitroom("test");
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}
    
    private Client client;
    
    public Waitroom(String title, Client client) {
    	
    	this.client = client;
    	
    	setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        int frameWidth = 760;
        int frameHeight = 560;
        setSize(frameWidth, frameHeight);
        
        Color backgroundColor = new Color(1, 49, 100);
        getContentPane().setBackground(backgroundColor);

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/image/waitroom.png"));
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(frameWidth, 365, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(0, 0, frameWidth, 365);
        getContentPane().add(imageLabel);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(1, 49, 100));
        textArea.setForeground(Color.WHITE);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 370, 580, 100);
        scrollPane.setBorder(new LineBorder(Color.YELLOW, 3));
        getContentPane().add(scrollPane);

        inputBox = new JTextField();
        inputBox.setBackground(new Color(1, 49, 100));
        inputBox.setForeground(Color.WHITE);
        inputBox.setBorder(new LineBorder(Color.YELLOW, 3));
        inputBox.setBounds(20, 480, 580, 30);
        getContentPane().add(inputBox);

        sendButton = new JButton("보내기");
        sendButton.setBounds(620, 480, 110, 30);
        sendButton.setBackground(Color.YELLOW);
        sendButton.setFocusPainted(false);
        getContentPane().add(sendButton);
        
        //준비 버튼
        JButton readyBtn = new JButton("READY");
        readyBtn.setBackground(Color.WHITE);
        readyBtn.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 14));
        readyBtn.setBounds(620, 370, 110, 100);
        getContentPane().add(readyBtn);
        
        readyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendReadyStatus();
            }
        });

        setVisible(true);
    }
    

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getInputBox() {
        return inputBox;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void appendText(String text) {
        textArea.append(text);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    public void processServerMessage(String message) {
//        if (message.startsWith("READY_STATUS:")) {
//            // 서버에서 보낸 준비 상태 메시지 처리
//            String[] parts = message.split(":");
//            String playerID = parts[1];
//            boolean isReady = Boolean.parseBoolean(parts[2]);
//            // 플레이어 ID와 준비 상태를 화면에 표시
//            appendText(playerID + (isReady ? " 준비 완료\n" : " 준비 취소\n"));
//        } else if (message.equals("GAME_START")) {
//            // 게임 시작 처리
//            EventQueue.invokeLater(() -> {
//                Selectroom selectRoom = new Selectroom();
//                selectRoom.setVisible(true);
//            });
    
    	if (message.equals("GAME_START")) {
            EventQueue.invokeLater(() -> {
                Selectroom selectRoom = new Selectroom();
                selectRoom.setVisible(true);
            });
        } else {
            // 다른 메시지 처리
        	appendText("상대방: " + message + "\n");
        }
        }
        }

