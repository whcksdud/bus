package buspj;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ChatThread extends Thread {
    chatroom chat;

    public void run() {
        chat = new chatroom("123");
        chat.setLocationRelativeTo(null);
        chat.setVisible(true);
    }
}


public class chatroom extends javax.swing.JFrame {

    String name = "test1";
    String msg = "test1";
    String time = "test1";
    int type = 0; //1 이면 나의 메시지, 0이면 상대방 메시지

    String roomName="123";
    private StyledDocument doc; // 정렬를 위한 필드
    private SimpleAttributeSet left, right; // 정렬을 위한 필드
    public int stack_height = 0; // 채팅이 쌓인 영역의 y축 크기

    JPanel BASE;
    JPanel center;
    JPanel blankspace;
    JLabel nameLabel;
    JLabel timeLabel;
    JPanel profile;
    JTextPane msgArea;

    Color invisible = new Color(0, 0, 0, 0);

    private int set_x = 400; // 채팅방 x축 크기
    private int set_y = 600; // 채팅방 y축 크기

    public chatroom(String name) {
       // name = id;
        //System.out.println(id);
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    @SuppressWarnings("unchecked")

    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ChattingArea = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        displayroom = new javax.swing.JLabel(roomName);
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        quit = new javax.swing.JButton();
        sendMsg = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        typingArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        setSize(new java.awt.Dimension(415, 610));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setOpaque(false);

        ChattingArea.setBackground(new java.awt.Color(242, 242, 242));
        ChattingArea.setPreferredSize(new Dimension(448,455));

        ChattingArea.setLayout(new FlowLayout(FlowLayout.LEFT));

        jScrollPane1.setViewportView(ChattingArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(196, 216, 242));

        displayroom.setFont(new java.awt.Font("휴먼엑스포", 1, 18)); // NOI18N
        displayroom.setForeground(new java.awt.Color(33, 147, 217));
        displayroom.setText("챗봇");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(displayroom)
                                .addContainerGap(370, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(displayroom)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setBackground(new java.awt.Color(70, 101, 140));

        jPanel5.setBackground(new java.awt.Color(70, 101, 140));
        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(70, 101, 140));

        quit.setBackground(new java.awt.Color(49, 98, 166));
        quit.setFont(new java.awt.Font("휴먼엑스포", 0, 18)); // NOI18N
        quit.setForeground(new java.awt.Color(255, 255, 255));
        quit.setText("나기기");
        //quit.setBorder(null);

        sendMsg.setBackground(new java.awt.Color(49, 98, 166));
        sendMsg.setFont(new java.awt.Font("휴먼엑스포", 0, 18)); // NOI18N
        sendMsg.setForeground(new java.awt.Color(255, 255, 255));
        sendMsg.setText("보내기");
        //sendMsg.setBorder(null);

        sendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = typingArea.getText();
                type = 1;
                name = "나";
                ChatMsgView(name,msg,type);
                typingArea.setText("");
                type = 0;
                name = "챗봇";
                msg = "good!!!!!!";
                ChatMsgView(name,msg,type);
            }
        });

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(quit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sendMsg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(sendMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                .addComponent(quit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.add(jPanel4, java.awt.BorderLayout.CENTER);

        typingArea.setBackground(new java.awt.Color(70, 101, 140));
        typingArea.setColumns(20);
        typingArea.setFont(new java.awt.Font("휴먼엑스포", 0, 18)); // NOI18N
        typingArea.setForeground(new java.awt.Color(255, 255, 255));
        typingArea.setLineWrap(true);
        typingArea.setRows(5);
        typingArea.setWrapStyleWord(true);
        typingArea.setBorder(null);
        jScrollPane2.setViewportView(typingArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(chatroom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(chatroom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(chatroom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(chatroom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        new chatroom("roomName").setVisible(true);
    }

    public void ChatMsgView(String name, String msg, int type) {
        this.type = type;
        BASE = new JPanel(); //기본 블록
        center = new JPanel(); //중간 --> 이름이랑, 시간, 메시지 들어가야함
        blankspace = new JPanel(); //좌우측 여백
        nameLabel = new JLabel(name); //이름 써지는 곳
        profile = new JPanel() {
            public void paintComponent(Graphics g) {
                Dimension d = new Dimension(45, 60);
                g.drawImage(new ImageIcon("project__java/buspj/image/chatbotimt.png").getImage(), 0, 0, d.width,
                        d.height, null);
            }
        }; // 프사

        left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT); // 왼쪽 텍스트 정렬
        right = new SimpleAttributeSet();
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT); // 오른쪽 텍스트 정렬

        msgArea = new JTextPane();

        doc = msgArea.getStyledDocument();

        BASE.setLayout(new BorderLayout());
        center.setLayout(new BorderLayout());

        profile.setBackground(invisible);
        blankspace.setBackground(invisible);
        center.setBackground(invisible);

        profile.setPreferredSize(new Dimension(45, 60));
        blankspace.setPreferredSize(new Dimension(200 - msg.length() * 2, 60));
        System.out.println(200 - msg.length());

        nameLabel.setFont(new Font("휴먼엑스포", 0, 14));
        nameLabel.setForeground(new Color(0, 0, 0));

        //msgArea.setBackground(new Color(144, 0, 0));
        msgArea.setPreferredSize(new Dimension(50, 30));
        msgArea.setEnabled(true);

        BASE.setBackground(invisible);
        BASE.setPreferredSize(new Dimension(420, 60));

        switch (type) {
            case 0: {
                try {
                    // 왼쪽 정렬
                    doc.insertString(doc.getLength(), msg, left);
                    StyleConstants.setLeftIndent(left, 0);
                    doc.setParagraphAttributes(doc.getLength(), 1, left, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                nameLabel.setHorizontalAlignment(JLabel.LEFT);

                BASE.add(profile, BorderLayout.WEST);
                BASE.add(blankspace, BorderLayout.EAST);
                break;
            }
            case 1: {
                try {
                    // 오른쪽 정렬
                    doc.insertString(doc.getLength(), msg, right);
                    StyleConstants.setRightIndent(right, 0);
                    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                nameLabel.setHorizontalAlignment(JLabel.RIGHT); // 오른쪽에 이름 라벨 정렬
                BASE.add(profile, BorderLayout.EAST); //
                BASE.add(blankspace, BorderLayout.WEST);
                break;
            }
        }

        center.add(nameLabel, BorderLayout.NORTH);
        center.add(msgArea, BorderLayout.CENTER);
        BASE.add(center, BorderLayout.CENTER);

        stack_height += BASE.getPreferredSize().height + 5;

        if (stack_height > ChattingArea.getPreferredSize().height) {
            ChattingArea.setPreferredSize(new Dimension(448,
                    ChattingArea.getPreferredSize().height + BASE.getPreferredSize().height + 5));
        }

        ChattingArea.add(BASE);
        ChattingArea.setSize(448, ChattingArea.getHeight() + BASE.getHeight()); // 중간 부분의 사이즈 재 설정
        repaint();
    }

    public void repaint() {
        System.out.println("좀 돼라");
        jScrollPane1.setViewportView(ChattingArea);
        JScrollBar scr = jScrollPane1.getVerticalScrollBar();
        if (stack_height > 448) {
            scr.setValue(stack_height);
        }
    }

    public javax.swing.JPanel ChattingArea;
    public javax.swing.JLabel displayroom;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JButton quit;
    public javax.swing.JButton sendMsg;
    public javax.swing.JTextArea typingArea;


}


