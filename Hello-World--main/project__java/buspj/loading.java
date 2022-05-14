package buspj;
import java.awt.*;             // 폰트 등 그래픽 처리를 위한 클래스들의 경로명
import java.awt.event.*;       // 이벤트 처리에 필요한 기본 클래스들의 경로명
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.*;
import javax.swing.JPanel;
import javax.swing.*;          // 스윙 컴포넌트 클래스들 경로명
import javax.imageio.*;

class BoxPanels extends JPanel implements ActionListener {
    //BufferedImage img = null;
    loading frame;
    JTextField id = new JTextField();
    JPasswordField pw = new JPasswordField();

    public BoxPanels(loading frame) {
        setLayout(null);
        this.frame = frame;
        setBackground(Color.white);

        JLabel question = new JLabel("버스타슈");
        question.setBounds(148, 100,250,90);
        question.setFont(new Font("휴먼엑스포", Font.BOLD, 50));
        add(question);

        JLabel signinPage = new JLabel("회원가입");
        signinPage.setBounds(215, 530, 100, 20);
        signinPage.setFont(new Font("휴먼엑스포", Font.BOLD, 15));
        add(signinPage);

        JLabel signinPage1 = new JLabel("로그인");
        signinPage1.setBounds(220, 500, 100, 20);
        signinPage1.setFont(new Font("휴먼엑스포", Font.BOLD, 15));
        add(signinPage1);

        // 메인 버스 이미지
        ImageIcon background = new ImageIcon("project__java/buspj/image/start.png");
        Image img = background.getImage();
        Image updateImg = img.getScaledInstance(500,500,Image.SCALE_DEFAULT);
        ImageIcon updateIcon = new ImageIcon(updateImg);
        JLabel image2 = new JLabel(updateIcon);
        image2.setBounds(-7,70,500,500);
        add(image2);

        signinPage1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new login_interface();
                frame.dispose();
            }
        });
        // 회원가입 글자 클릭 시 이벤트
        signinPage.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new join_interface();
                frame.dispose();
            }
        });
    }

    // '확인' 버튼 클릭시
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String idt = id.getText();
        String pwt = pw.getText();
        DB_connect DB = new DB_connect(); // DB 객체 불러오기
        if (button.getText().equals("")) {
            if (id.getText().equals("") && pw.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 입력하세요.");
            }
            else if (id.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
            }
            else if (pw.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
            }
            else {
                int value = DB.login(idt, pwt);
                System.out.println(value);
                if(value == 1){
                    JOptionPane.showMessageDialog(null, "로그인 성공");
                    new Main(idt);
                    this.frame.dispose();
                }
                else if(value == 2){
                    JOptionPane.showMessageDialog(null, "관리자 로그인 성공");
                    new AdministratorMain();
                    this.frame.dispose();
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
    }
}



public class loading extends JFrame {
    public loading() {
        setTitle("버스타슈~");
        setResizable(false);
        setSize(500,700);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정

        Container mainContainer = getContentPane();

        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(new BoxPanels(this), BorderLayout.CENTER);

        addWindowListener(new JFrameWindowClosingEventHandler());

        setVisible(true);
    }
    public static void main(String[] args) {
        new loading();
        System.out.println("종료");
    }
}