package buspj;
import java.awt.*;             // 폰트 등 그래픽 처리를 위한 클래스들의 경로명
import java.awt.event.*;       // 이벤트 처리에 필요한 기본 클래스들의 경로명
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import javax.swing.JPanel;
import javax.swing.*;          // 스윙 컴포넌트 클래스들 경로명
import javax.imageio.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.awt.Color;

import java.awt.Font;

import java.awt.event.FocusAdapter;

import java.awt.event.FocusEvent;

import javax.swing.JTextField;


class BoxPanel extends JPanel {
    //BufferedImage img = null;
    login_interface frame;
    HintTextField id = new HintTextField("아이디");

    PwHintTextField pw = new PwHintTextField("비밀번호");
    //JTextField id = new JTextField();
    //JPasswordField pw = new JPasswordField();
    JButton btn1;
    JButton btn2;
    JLabel title;
    public BoxPanel(login_interface frame) {

        setLayout(null);
//       JLabel idText = new JLabel("아이디");
//       idText.setBounds(80,50,50,30);
//       add(idText);

        setBackground(Color.white);
        // '아이디' 글자 설정
        JLabel title = new JLabel("로그인");
        title.setFont(new Font("휴먼엑스포",0,36));
        title.setBounds(180,155,250,50);
        add(title);
        id.setBounds(93,240,290,50);
        add(id);

//       JLabel pwText = new JLabel("비밀번호");
//       pwText.setBounds(80,100,50,30);
//       add(pwText);

        // '비밀번호' 글자 설정
        pw.setBounds(93,330,290,50);
        add(pw);

        btn1 = new JButton();
        btn1.setBackground(new Color(65, 98, 166));
        btn1.setFont(new Font("휴먼엑스포", 0, 18)); // NOI18N
        btn1.setForeground(new Color(255, 255, 255));
        btn1.setText("로그인");
        //btn1.setBorder(null);
        btn1.setBounds(90, 450, 130, 40);
        add(btn1);

        btn2 = new JButton();
        btn2.setBackground(new Color(65, 98, 166));
        btn2.setFont(new Font("휴먼엑스포", 0, 18)); // NOI18N
        btn2.setForeground(new Color(255, 255, 255));
        btn2.setText("취소");
        //btn1.setBorder(null);
        btn2.setBounds(250, 450, 130, 40);
        add(btn2);

        btn1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String idt = id.getText();
                String pwt = pw.pwf;
                System.out.println(idt);
                DB_connect DB = new DB_connect(); // DB 객체 불러오기
                if (btn1.getText().equals("")) {
                    if (id.getText().equals("") && pw.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력하세요.");
                    } else if (id.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
                    } else if (pw.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
                    }
                }
                    else {
                        int value = DB.login(idt, pwt);
                        System.out.println(value);
                        if(value == 1){
                            JOptionPane.showMessageDialog(null, "로그인 성공");
                            new Main(idt);
                            frame.dispose();
                        }
                        else if(value == 2){
                            JOptionPane.showMessageDialog(null, "관리자 로그인 성공");
                            new AdministratorMain();
                            frame.dispose();
                        }
                        else if(value == -1){
                            JOptionPane.showMessageDialog(null, "일치하는 아이디가 없습니다.");
                        }
                        else if(value == 0){
                            JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "시스템 오류");
                        }
                    }
                }
        });
        btn2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new loading();
                frame.dispose();
            }
        });


    }



}

class JFrameWindowClosingEventHandler extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        JFrame frame = (JFrame)e.getWindow();

        if (frame instanceof login_interface) {
            int answer = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?","System", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
        else if (frame instanceof join_interface) {  // 회원가입 창의 X 단추를 누른 경우
            new login_interface(); // 회원가입 창이 꺼지고 로그인 창이 켜져야 하는데 씹힘
            frame.dispose();
        }
        else if (frame instanceof Main || frame instanceof MyPage || frame instanceof AdministratorMain ||
                frame instanceof ReservationMain || frame instanceof SeatsSelect || frame instanceof Payment) {
            int answer = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?","로그아웃",JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                new login_interface();
                frame.dispose();
            }
        }
    }
}

public class login_interface extends JFrame {
    public login_interface() {
        setTitle("버스타슈~");
        setResizable(false);
        setSize(500,700);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Container mainContainer = getContentPane();


        //mainContainer.setLayout(new BorderLayout());

        mainContainer.add(new BoxPanel(this));

        addWindowListener(new JFrameWindowClosingEventHandler());

        setVisible(true);
        mainContainer.setBackground(Color.blue);
    }
    public static void main(String[] args) {
        new login_interface();
        System.out.println("종료");
    }
}