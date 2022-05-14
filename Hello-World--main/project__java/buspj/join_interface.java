package buspj;

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


class Member{ //회원 가입 db연결 클래스
    String id;
    String pw;
    String email;
    String name;
    String phone;
    int point;
    public Member(String id) {
        this.id=id;
        this.pw="pw";
        this.email="email";
        this.name="name";
        this.phone="phone";
        this.point = 0;
    }
    public Member(String id, String pw, String email, String name, String phone, int point){
        this.id=id;
        this.pw=pw;
        this.email=email;
        this.name=name;
        this.phone=phone;
        this.point = point;
    }
    public String get_id(){ return id; }
    public String get_pw(){
        return pw;
    }
    public String get_email(){
        return email;
    }
    public String get_name(){
        return name;
    }
    public String get_phone(){
        return phone;
    }
    public int get_point() { return point; }
    public void set_point(int point) { this.point = point; }
}

//음영 처리
class HintTextField extends JTextField {
    Font gainFont = new Font("휴먼엑스포", Font.PLAIN, 13);
    Font lostFont = new Font("휴먼엑스포", Font.PLAIN, 13);
    public HintTextField(final String hint) {
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
        setBackground(new Color(238, 234, 234));
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setFont(gainFont);
                } else {
                    setText(getText());
                    setFont(gainFont);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(hint)|| getText().length()==0) {
                    setText(hint);
                    setFont(lostFont);
                    setForeground(Color.GRAY);
                } else {
                    setText(getText());
                    setFont(gainFont);
                    setForeground(Color.BLACK);
                }
            }
        });
    }
}
class PwHintTextField extends JTextField {
    Font gainFont = new Font("휴먼엑스포", Font.PLAIN, 13);
    Font lostFont = new Font("휴먼엑스포", Font.PLAIN, 13);
    String pwf;
    public PwHintTextField(final String hint) {
        setText(hint);
        setFont(lostFont);
        setForeground(Color.GRAY);
        setBackground(new Color(238, 234, 234));
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(hint)) {
                    setText("");
                    setFont(gainFont);
                } else if (getText().equals("**********")) {
                    setText(pwf);
                } else {
                    pwf = getText();
                    System.out.println(pwf);
                    setText("**********");
                    setFont(gainFont);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().equals(hint)|| getText().length()==0) {
                    setText(hint);
                    setFont(lostFont);
                    setForeground(Color.GRAY);

                } else {
                    pwf = getText();
                    System.out.println(pwf);
                    setText("**********");
                    setFont(gainFont);
                    setForeground(Color.BLACK);
                }
            }
        });
    }
}
// 회원 가입 인터페이스
public class join_interface extends JFrame {
    JButton btn1;
    JButton btn2;

    private javax.swing.JPanel jPanel1;
    HintTextField namet = new HintTextField("이름");
    HintTextField phonet = new HintTextField("전화번호");
    HintTextField emailt = new HintTextField("이메일");
    HintTextField idt = new HintTextField("아이디(4~10자 이내)");
    PwHintTextField pwt = new PwHintTextField("비밀번호(4~10자 이내)");
    PwHintTextField pwct = new PwHintTextField("비밀번호  확인");

    int x = 300;
    int y = 300;
    JFrame fr = new JFrame();
    String id = idt.getText();
    String pw = pwt.getText();
    String pwc = pwct.getText();
    String email = emailt.getText();
    String phone = phonet.getText();
    String name = namet.getText();
    int point = 0;
    Member new_member = new Member(id, pw, email, name, phone, point);
    DB_connect DB = new DB_connect(); // DB 클래스 불러오기
    public join_interface() {

        btn1 = new JButton();
        btn2 = new JButton();
        fr.setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 닫음 이벤트
        fr.setTitle("회원가입");
        fr.setSize(500, 700);
        fr.setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        fr.setLayout(null);
        fr.getContentPane().setBackground(Color.white);
        namet.setBounds(70, 50, 350, 40);
        fr.add(namet);

        phonet.setBounds(70, 120, 350, 40);
        fr.add(phonet);

        emailt.setBounds(70, 190, 350, 40);
        fr.add(emailt);

        idt.setBounds(70, 260, 350, 40);
        fr.add(idt);


        ImageIcon overlap = new ImageIcon("project__java/buspj/image/overlap1.png");
        Image overlap11 = overlap.getImage();
        Image reoverlap = overlap11.getScaledInstance(80, 30, Image.SCALE_SMOOTH);
        ImageIcon overlapIcon1 = new ImageIcon(reoverlap);
        ImageIcon overlap2 = new ImageIcon("project__java/buspj/image/overlap2.png");
        Image overlap22 = overlap2.getImage();
        Image reoverlap2 = overlap22.getScaledInstance(80, 30, Image.SCALE_SMOOTH);
        ImageIcon overlapIcon2 = new ImageIcon(reoverlap2);
        JButton idck = new JButton(overlapIcon1);
        idck.setBorderPainted(false); // 버튼 테두리 설정해제
        idck.setRolloverIcon(overlapIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        idck.setFocusPainted(false);
        idck.setContentAreaFilled(false);
        idck.setOpaque(false);
        idck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idt.getText();
                Member idckmember = new Member(id);
                int check = DB.idCheck(idckmember);
                id = idt.getText();
                if (id.equals("")) {
                    JOptionPane.showMessageDialog(null, "아이디를 입력하셔야 합니다.");
                } else if (check == 0) {
                    JOptionPane.showMessageDialog(null, "사용 가능");
                } else {
                    JOptionPane.showMessageDialog(null, "사용중인 아이디");
                }
            }
        });

        idck.setBounds(100, 200, 80, 30);

        pwt.setBounds(70, 330, 350, 40);
        fr.add(pwt);

        pwct.setBounds(70, 400, 350, 40);
        fr.add(pwct);



        btn1.setBackground(new Color(65, 98, 166));
        btn1.setFont(new Font("휴먼엑스포", 0, 18)); // NOI18N
        btn1.setForeground(new Color(255, 255, 255));
        btn1.setText("확인");
        //btn1.setBorder(null);
        btn1.setBounds(90, 500, 130, 40);
        fr.add(btn1);

        btn2.setBackground(new java.awt.Color(65, 98, 166));
        btn2.setFont(new java.awt.Font("휴먼엑스포", 0, 18)); // NOI18N
        btn2.setForeground(new java.awt.Color(255, 255, 255));
        btn2.setText("취소");
        btn2.setBounds(250, 500, 130, 40);
        fr.add(btn2);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                String id = idt.getText();
                String pw = pwt.pwf;
                String pwc = pwct.pwf;
                String email = emailt.getText();
                String phone = phonet.getText();
                String name = namet.getText();
                int point = 0;
                Member new_member = new Member(id, pw, email, name, phone, point);
                DB_connect DB = new DB_connect(); // DB 클래스 불러오기
                //등록 이벤트 처리
                if (id.equals("")) {
                    JOptionPane.showMessageDialog(null, "아이디를 입력하셔야 합니다.");
                } else if (pw.equals("")) {
                    JOptionPane.showMessageDialog(null, "비밀번호를 입력하셔야 합니다.");
                } else if (pwc.equals("")) {
                    JOptionPane.showMessageDialog(null, "비밀번호를 입력하셔야 합니다.");
                } else if (email.equals("")) {
                    JOptionPane.showMessageDialog(null, "이메일을 입력하셔야 합니다.");
                } else if (phone.equals("")) {
                    JOptionPane.showMessageDialog(null, "전화번호를 입력하셔야 합니다.");
                } else if (name.equals("")) {
                    JOptionPane.showMessageDialog(null, "이름을 입력하셔야 합니다.");
                } else if (!(pw.equals(pwc))) {
                    JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다.");
                } else {
                    DB.sing_db(new_member);
                    JOptionPane.showMessageDialog(null, "가입 완료");
                    new loading();
                    fr.dispose();
                }
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new loading();
                fr.dispose();
            }
        });

        setSize(500, 700);

        fr.addWindowListener(new JFrameWindowClosingEventHandler());
        fr.setVisible(true);

    }

    public static void main(String[] args) {
        new join_interface();
    }
}

