package buspj;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;       // 이벤트 처리에 필요한 기본 클래스들의 경로명

//class AdministratorTitle extends JPanel {
//    public AdministratorTitle() {
//        setBackground(Color.LIGHT_GRAY);
//
//        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 16));
//
//        JLabel title = new JLabel("마법의 성이라는 노래를 아십니까? 안다면 당신은 옛날 사람.");
//        title.setFont(new Font("Serif", Font.BOLD, 20));
//        add(title);
//    }
//}

class AdministratorLoginAndSignup extends JPanel {
    AdministratorMain frame;

    public AdministratorLoginAndSignup(AdministratorMain frame) {
        Color mycor=new Color(189,215,238);
        setBackground(mycor);
        this.frame = frame;

        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));


        ImageIcon logout = new ImageIcon("project__java/buspj/image/reout.png");
        Image relogout = logout.getImage();
        Image reUpdate1 = relogout.getScaledInstance(60,50, Image.SCALE_SMOOTH);
        ImageIcon reUpdateIcon2 = new ImageIcon(reUpdate1);

        ImageIcon logout2 = new ImageIcon("project__java/buspj/image/reout1.png");
        Image relogout2 = logout2.getImage();
        Image reUpdate12 = relogout2.getScaledInstance(60,50, Image.SCALE_SMOOTH);
        ImageIcon reUpdateIcon22 = new ImageIcon(reUpdate12);
        JButton logoutButton = new JButton(reUpdateIcon2);
        //logoutButton.setPreferredSize(new Dimension(120, 50));
        //.setFont(new Font("굴림", Font.BOLD, 20));
        logoutButton.setRolloverIcon(reUpdateIcon22);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setOpaque(false);
        add(logoutButton);

        // 로그아웃 버튼 클릭 시 이벤트
        logoutButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?","로그아웃",JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    new login_interface();
                    frame.dispose();
                }
            }
        });
    }
}

class AdministratorNorthPanel extends JPanel {
    AdministratorMain frame;

    public AdministratorNorthPanel(AdministratorMain frame) {
        Color mycor=new Color(189,215,238);
        setBackground(mycor);
        this.frame = frame;

        setLayout(new BorderLayout());
        add(new Title(), BorderLayout.WEST);
        add(new AdministratorLoginAndSignup(this.frame), BorderLayout.EAST);
    }
}

class AdministratorCenterPanel extends JPanel {
    public AdministratorCenterPanel() {
//        setLayout(new FlowLayout(FlowLayout.CENTER, 150, 250));
        setLayout(null);

//        JButton mainButton1 = new JButton("회원관리");
//        mainButton1.setPreferredSize(new Dimension(180, 180));
//        mainButton1.setBounds(50,50,180,100);
//        mainButton1.setFont(new Font("굴림", Font.BOLD, 20));
//        add(mainButton1);

        // 회원관리 글자, 운행정보 관리 글자
        JLabel p1 = new JLabel("회원관리");
        JLabel p2 = new JLabel("운행정보 관리");
        p1.setFont(new Font("휴먼엑스포", Font.PLAIN, 35));
        p2.setFont(new Font("휴먼엑스포", Font.PLAIN, 35));
        p1.setForeground(Color.WHITE);
        p2.setForeground(Color.WHITE);
        p1.setBounds(205,180,200,200);
        p2.setBounds(485,180,300,200);
        add(p1);
        add(p2);

        // 회원관리 버튼 이미지
        ImageIcon mainIcon = new ImageIcon("project__java/buspj/image/icon1.png");
        Image mainImg = mainIcon.getImage();
        Image mainUpdate = mainImg.getScaledInstance(180,180, Image.SCALE_SMOOTH);
        ImageIcon mainUpdateIcon = new ImageIcon(mainUpdate);

        ImageIcon mainIcon2 = new ImageIcon("project__java/buspj/image/blurIcon4.png");
        Image mainImg2 = mainIcon2.getImage();
        Image mainUpdate2 = mainImg2.getScaledInstance(180,180, Image.SCALE_SMOOTH);
        ImageIcon mainUpdateIcon2 = new ImageIcon(mainUpdate2);

        JButton user = new JButton(mainUpdateIcon);
        user.setBounds(180,60,190,190);
        user.setBorderPainted(false); // 버튼 테두리 설정해제
        Color mycor=new Color(189,215,238);
        user.setRolloverIcon(mainUpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        user.setBackground(mycor);
        user.setFocusPainted(false);
        user.setContentAreaFilled(false);
        user.setOpaque(false);
        add(user);
        user.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new UserList("root");
            }
        });

//        JButton mainButton2 = new JButton("운행정보 관리");
//        mainButton2.setPreferredSize(new Dimension(180, 180));
//        mainButton2.setBounds(250,50,180,100);
//        mainButton2.setFont(new Font("굴림", Font.BOLD, 20));
//        add(mainButton2);

        // 운행정보 관리 버튼 이미지
        ImageIcon main2Icon = new ImageIcon("project__java/buspj/image/icon2.png");
        Image main2Img = main2Icon.getImage();
        Image main2Update = main2Img.getScaledInstance(180,180, Image.SCALE_SMOOTH);
        ImageIcon main2UpdateIcon = new ImageIcon(main2Update);

        ImageIcon main2Icon2 = new ImageIcon("project__java/buspj/image/blurIcon3.png");
        Image main2Img2 = main2Icon2.getImage();
        Image main2Update2 = main2Img2.getScaledInstance(180,180, Image.SCALE_SMOOTH);
        ImageIcon main2UpdateIcon2 = new ImageIcon(main2Update2);

        JButton bus = new JButton(main2UpdateIcon);
        bus.setBounds(500,60,190,190);
        bus.setBorderPainted(false); // 버튼 테두리 설정해제
        bus.setRolloverIcon(main2UpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        bus.setBackground(mycor);
        bus.setFocusPainted(false);
        bus.setContentAreaFilled(false);
        bus.setOpaque(false);
        add(bus);
        bus.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new BusList("root");
            }
        });

        ImageIcon background = new ImageIcon("project__java/buspj/image/bg.png");
        JLabel image = new JLabel(background);
        image.setBounds(-1570,-150,4000,1200);
        add(image);
    }
}

public class AdministratorMain extends JFrame {
    public AdministratorMain() {
        setTitle("버스타슈~");
        setSize(900,800);
        setResizable(false);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(new AdministratorNorthPanel(this), BorderLayout.NORTH);
        mainContainer.add(new AdministratorCenterPanel(), BorderLayout.CENTER);

        addWindowListener(new JFrameWindowClosingEventHandler());

        setVisible(true);
    }
}
