package buspj;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UserList extends JFrame {
    private static final long serialVersionUID = 1L;
    private Vector data = null;
    private Vector title = null;
    private JTable table = null;
    private DefaultTableModel model = null;
    private JButton btnAdd = null;
    private JButton btnDel = null;
    private JButton btnUpdate = null;
    private JButton btnClear = null;
    private JTextField tfNum = null;
    private JTextField tfName = null;
    private JTextField tfAddress = null;
    private JTextField tfphone = null;
    private JLabel lblNum = null;
    private JLabel lblName = null;
    private JLabel lblAddress = null;
    private JLabel phone = null;
    private String Url = "jdbc:mysql://localhost:3306/bus";
    private String user = "hr";
    private String password = "hr";
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmtAdd = null;
    private PreparedStatement pstmtDel = null;
    private PreparedStatement pstmtUpdate = null;

    public UserList(final String user) {
        super("회원 관리");
        this.user = user;
        this.setLocation(370,190);  // 프레임을 위치 설정
        this.setDefaultCloseOperation(0);
        this.preDbTreatment();
        this.data = new Vector();
        this.title = new Vector();
        this.title.add("이름");
        this.title.add("아이디");
        this.title.add("이메일");
        this.title.add("전화번호");
        this.model = new DefaultTableModel();
        Vector result = this.selectAll();
        this.model.setDataVector(result, this.title);
        this.table = new JTable(this.model);
        JScrollPane sp = new JScrollPane(this.table);
        this.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = UserList.this.table.getSelectedRow();
                Vector in = (Vector)UserList.this.data.get(index);
                String name = (String)in.get(0);
                String id = (String)in.get(1);
                String email = (String)in.get(2);
                String phone = (String)in.get(3);
                UserList.this.tfNum.setText(name);
                UserList.this.tfName.setText(id);
                UserList.this.tfAddress.setText(email);
                UserList.this.tfphone.setText(phone);
            }
        });
        JPanel panel = new JPanel();
        this.tfNum = new JTextField(8);
        this.tfName = new JTextField(8);
        this.tfAddress = new JTextField(10);
        this.tfphone = new JTextField(6);
        this.lblNum = new JLabel("이름");
        this.lblName = new JLabel("아이디");
        this.lblAddress = new JLabel("이메일");
        this.phone = new JLabel("전화번호");

        ImageIcon del1 = new ImageIcon("project__java/buspj/image/del1.png");
        Image del11 = del1.getImage();
        Image del111 = del11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon1 = new ImageIcon(del111);
        ImageIcon del2 = new ImageIcon("project__java/buspj/image/del2.png");
        Image del22 = del2.getImage();
        Image del222 = del22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon2 = new ImageIcon(del222);
        this.btnDel = new JButton(delIcon1);
        this.btnDel.setBorderPainted(false); // 버튼 테두리 설정해제
        this.btnDel.setRolloverIcon(delIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        this.btnDel.setFocusPainted(false);
        this.btnDel.setContentAreaFilled(false);
        this.btnDel.setOpaque(false);
        //this.btnDel = new JButton("삭제");

        ImageIcon close1 = new ImageIcon("project__java/buspj/image/close1.png");
        Image close11 = close1.getImage();
        Image close111 = close11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon closeIcon1 = new ImageIcon(close111);
        ImageIcon close2 = new ImageIcon("project__java/buspj/image/close2.png");
        Image close22 = close2.getImage();
        Image close222 = close22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon closeIcon2 = new ImageIcon(close222);
        this.btnUpdate = new JButton(closeIcon1);
        this.btnUpdate.setBorderPainted(false); // 버튼 테두리 설정해제
        this.btnUpdate.setRolloverIcon(closeIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        this.btnUpdate.setFocusPainted(false);
        this.btnUpdate.setContentAreaFilled(false);
        this.btnUpdate.setOpaque(false);

        //this.btnUpdate = new JButton("닫기");
        this.btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String card = UserList.this.tfName.getText();
                System.out.println(card);
                UserList.this.delete(card);
                Vector result = UserList.this.selectAll();
                UserList.this.model.setDataVector(result, UserList.this.title);
            }
        });
        this.btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserList.this.dispose();
            }
        });
        panel.add(this.lblNum);
        panel.add(this.tfNum);
        panel.add(this.lblName);
        panel.add(this.tfName);
        panel.add(this.lblAddress);
        panel.add(this.tfAddress);
        panel.add(this.phone);
        panel.add(this.tfphone);
        panel.add(this.btnDel);
        panel.add(this.btnUpdate);
        Container c = this.getContentPane();
        c.add(new JLabel("회원 리스트", 0), "North");
        c.add(sp, "Center");
        c.add(panel, "South");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                try {
                    UserList.this.stmt.close();
                    UserList.this.conn.close();
                    UserList.this.setVisible(false);
                    UserList.this.dispose();
                } catch (Exception var3) {
                }

            }
        });
        this.setSize(800, 400);
        this.setVisible(true);
    }

    private Vector selectAll() {
        this.data.clear();

        try {
            String sql = "select name, id, email, phone from new_table";
            System.out.println(sql);
            ResultSet rs = this.stmt.executeQuery(sql);

            while(rs.next()) {
                Vector in = new Vector();
                String name = rs.getString(1);
                String id = rs.getString(2);
                String email = rs.getString(3);
                String phone = rs.getString(4);
                in.add(name);
                in.add(id);
                in.add(email);
                in.add(phone);
                this.data.add(in);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        return this.data;
    }

    private void delete(String id) {
        try {
            this.pstmtDel = this.conn.prepareStatement("delete from new_table where id = ?");
            this.pstmtDel.setString(1, id);
            this.pstmtDel.executeUpdate();
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }
    private void preDbTreatment() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bus", "root", "1234");
            System.out.println("DB 연결 완료");
            this.stmt = this.conn.createStatement();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}
