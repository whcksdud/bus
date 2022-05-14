
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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

//text 입력제한
class BoundDocument extends PlainDocument {
    protected int charLimit;
    protected JTextComponent textComp;
    public BoundDocument(int charLimit) { this.charLimit = charLimit; }
    public BoundDocument(int charLimit, JTextComponent textComp) { this.charLimit = charLimit; this.textComp = textComp; }

    public void insertString (int offs, String str, AttributeSet a) throws BadLocationException
    {
        if (textComp.getText().getBytes().length + str.getBytes().length <= charLimit) { super.insertString(offs, str, a); }
    }
}

public class MyCard extends JFrame {
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
    private JLabel lblNum = null;
    private JLabel lblName = null;
    private JLabel lblAddress = null;
    private String Url = "jdbc:mysql://localhost:3306/bus";
    private String user = "hr";
    private String password = "hr";
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement pstmtAdd = null;
    private PreparedStatement pstmtDel = null;
    private PreparedStatement pstmtUpdate = null;

    public MyCard(final String user) {
        super("카드 관리");
        this.user = user;
        this.setDefaultCloseOperation(0);
        this.preDbTreatment();
        this.data = new Vector();
        this.title = new Vector();
        this.title.add("카드사");
        this.title.add("카드번호");
        this.model = new DefaultTableModel();
        Vector result = this.selectAll();
        this.model.setDataVector(result, this.title);
        this.table = new JTable(this.model);
        JScrollPane sp = new JScrollPane(this.table);
        this.table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = MyCard.this.table.getSelectedRow();
                Vector in = (Vector)MyCard.this.data.get(index);
                String bank = (String)in.get(0);
                String cardnum = (String)in.get(1);
                MyCard.this.tfNum.setText(bank);
                MyCard.this.tfName.setText(cardnum);
                MyCard.this.tfAddress.setText("");
            }
        });
        JPanel panel = new JPanel();
        this.tfNum = new JTextField(8);
        this.tfName = new JTextField(20);
        tfName.setDocument(new BoundDocument(16, tfName));
        this.tfAddress = new JTextField(5);
        tfAddress.setDocument(new BoundDocument(4, tfAddress));
        this.lblNum = new JLabel("카드사");
        this.lblName = new JLabel("카드번호");
        this.lblAddress = new JLabel("비밀번호");


        ImageIcon overlap = new ImageIcon("project__java/buspj/image/add1.png");
        Image overlap11 = overlap.getImage();
        Image reoverlap = overlap11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon overlapIcon1 = new ImageIcon(reoverlap);
        ImageIcon overlap2 = new ImageIcon("project__java/buspj/image/add2.png");
        Image overlap22 = overlap2.getImage();
        Image reoverlap2 = overlap22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon overlapIcon2 = new ImageIcon(reoverlap2);
        this.btnAdd = new JButton(overlapIcon1);
        this.btnAdd.setBorderPainted(false); // 버튼 테두리 설정해제
        this.btnAdd.setRolloverIcon(overlapIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        this.btnAdd.setFocusPainted(false);
        this.btnAdd.setContentAreaFilled(false);
        this.btnAdd.setOpaque(false);


        ImageIcon del1 = new ImageIcon("project__java/buspj/image/del1.png");
        Image del11 = del1.getImage();
        Image del111 = del11.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon1 = new ImageIcon(del111);
        ImageIcon del2 = new ImageIcon("project__java/buspj/image/del2.png");
        Image del22 = del2.getImage();
        Image del222 = del22.getScaledInstance(70, 30, Image.SCALE_SMOOTH);
        ImageIcon delIcon2 = new ImageIcon(del222);
        //this.btnAdd = new JButton(overlapIcon1);
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
        this.btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bank = MyCard.this.tfNum.getText();
                String cardnum = MyCard.this.tfName.getText();
                String pw = MyCard.this.tfAddress.getText();
                MyCard.this.insert(user, bank, cardnum, pw);
                Vector result = MyCard.this.selectAll();
                MyCard.this.model.setDataVector(result, MyCard.this.title);
            }
        });
        this.btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String card = MyCard.this.tfName.getText();
                System.out.println(card);
                MyCard.this.delete(card);
                Vector result = MyCard.this.selectAll();
                MyCard.this.model.setDataVector(result, MyCard.this.title);
            }
        });
        this.btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MyCard.this.dispose();
            }
        });
        panel.add(this.lblNum);
        panel.add(this.tfNum);
        panel.add(this.lblName);
        panel.add(this.tfName);
        panel.add(this.lblAddress);
        panel.add(this.tfAddress);
        panel.add(this.btnAdd);
        panel.add(this.btnDel);
        panel.add(this.btnUpdate);
        Container c = this.getContentPane();
        c.add(new JLabel("나의 카드 리스트", 0), "North");
        c.add(sp, "Center");
        c.add(panel, "South");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                try {
                    MyCard.this.stmt.close();
                    MyCard.this.conn.close();
                    MyCard.this.setVisible(false);
                    MyCard.this.dispose();
                } catch (Exception var3) {
                }

            }
        });
        this.setSize(900, 400);
        this.setLocation(320,190);  // 프레임을 위치 설정
        this.setVisible(true);
    }

    private Vector selectAll() {
        this.data.clear();

        try {
            String sql = "select bank, cardnum from mycard  where id='" + this.user + "'";
            System.out.println(sql);
            ResultSet rs = this.stmt.executeQuery(sql);

            while(rs.next()) {
                Vector in = new Vector();
                String bank = rs.getString(1);
                String cardnum = rs.getString(2);
                in.add(bank);
                in.add(cardnum);
                this.data.add(in);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return this.data;
    }

    private void insert(String user, String bank, String cardnum, String pw) {
        try {
            System.out.println(user + bank + cardnum + pw);
            this.pstmtAdd = this.conn.prepareStatement("insert into mycard values(?,?,?,?)");
            this.pstmtAdd.setString(1, user);
            this.pstmtAdd.setString(2, bank);
            this.pstmtAdd.setString(3, cardnum);
            this.pstmtAdd.setString(4, pw);
            this.pstmtAdd.executeUpdate();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    private void delete(String card) {
        try {
            this.pstmtDel = this.conn.prepareStatement("delete from mycard where cardnum = ?");
            this.pstmtDel.setString(1, card);
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