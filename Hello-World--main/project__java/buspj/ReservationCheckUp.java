package buspj;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// 예매 정보 불러오기
class CheckUp {
    ArrayList<String> date = new ArrayList<String>();
    ArrayList<String> start = new ArrayList<String>();
    ArrayList<String> end = new ArrayList<String>();
    ArrayList<String> starttime = new ArrayList<String>();
    ArrayList<String> company = new ArrayList<String>();
    ArrayList<String> class_ = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> seat = new ArrayList<String>();
    public void insertData(String date, String start, String end, String starttime, String company, String class_, String price, String seat) {
        this.date.add(date);
        this.start.add(start);
        this.end.add(end);
        this.starttime.add(starttime);
        this.company.add(company);
        this.class_.add(class_);
        this.price.add(price);
        this.seat.add(seat);
    }
}

public class ReservationCheckUp extends JFrame implements MouseListener {
    String id;      // 회원 아이디
    String[] arr;
    JPanel northP;
    JPanel southP;
    JButton bt_del;
    JButton bt_close;
    JLabel title;   // 테이블 타이틀
    JTable table;   // 표 내용을 담을 테이블
    JScrollPane scroll;
    DefaultTableModel model;   // 열 이름과 데이터 행을 연결할 model
    String[] colName = {"출발날짜", "출발 터미널", "도착 터미널", "출발 시간", "회사", "등급", "가격", "좌석"};   // 테이블 열 이름
    CheckUp c;
    DB_connect DB = new DB_connect();

    public ReservationCheckUp(String id) {
        this.id = id;    // 회원 아이디 저장
        setTitle("예매내역 조회하기");
        setSize(700,400);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Container mainContainer = getContentPane();

        // x 버튼 눌렀을 시
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent w) {
                ReservationCheckUp.this.dispose();
            }
        });

        String[][] row = new String[0][8];
        model = new DefaultTableModel(row, colName);

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 하나의 행만 선택 가능

        scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add("Center",scroll);

        c = DB.loadUserReservation(id);   // 데이터 저장
        for (int i = 0; i < c.date.size(); i++) {
            String[] data = {c.date.get(i), c.start.get(i), c.end.get(i), c.starttime.get(i), c.company.get(i), c.class_.get(i), c.price.get(i), c.seat.get(i)};
            model.addRow(data);
        }

        title = new JLabel("예매내역");
        northP = new JPanel();
        northP.add(title);
        add("North", northP);


        ImageIcon reservecancel1 = new ImageIcon("project__java/buspj/image/reservecancel1.png");
        Image reservecancel11 = reservecancel1.getImage();
        Image reservecancel111 = reservecancel11.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ImageIcon reservecancelIcon1 = new ImageIcon(reservecancel111);
        ImageIcon reservecancel2 = new ImageIcon("project__java/buspj/image/reservecancel2.png");
        Image reservecancel22 = reservecancel2.getImage();
        Image reservecancel222 = reservecancel22.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ImageIcon reservecancelIcon2 = new ImageIcon(reservecancel222);
        bt_del = new JButton(reservecancelIcon1);
        //btnExit = new JButton(closeIcon1);
        bt_del.setBorderPainted(false); // 버튼 테두리 설정해제
        bt_del.setRolloverIcon(reservecancelIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        bt_del.setFocusPainted(false);
        bt_del.setContentAreaFilled(false);
        bt_del.setOpaque(false);
        // = new JButton("예매취소");
        southP = new JPanel();
        southP.add(bt_del);

        ImageIcon close1 = new ImageIcon("project__java/buspj/image/close1.png");
        Image close11 = close1.getImage();
        Image close111 = close11.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ImageIcon closeIcon1 = new ImageIcon(close111);
        ImageIcon close2 = new ImageIcon("project__java/buspj/image/close2.png");
        Image close22 = close2.getImage();
        Image close222 = close22.getScaledInstance(80, 40, Image.SCALE_SMOOTH);
        ImageIcon closeIcon2 = new ImageIcon(close222);
        bt_close = new JButton(closeIcon1);
        //btnExit = new JButton(closeIcon1);
        bt_close.setBorderPainted(false); // 버튼 테두리 설정해제
        bt_close.setRolloverIcon(closeIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        bt_close.setFocusPainted(false);
        bt_close.setContentAreaFilled(false);
        bt_close.setOpaque(false);
        // = new JButton("예매취소");
        southP.add(bt_close);

        add("South", southP);


        // 테이블 마우스 이벤트
        table.addMouseListener(this);

        // 예매 삭제 누르면 DB에서 삭제
        bt_del.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int answer = JOptionPane.showConfirmDialog(null, "취소 하시겠습니까?","예매취소",JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    int point = DB.mileage(id);
                    point -= 10;
                    DB.set_mileage(id, point);       // 마이너스 된 마일리지 업데이트
                    String[] arr = DB.load_information(id);   // 삭제할 표 정보 가져오기
                    DB.delete_userReservation(id);   // 해당 예매표 삭제
                    DB.plus_seats(arr);             // 삭제한 표의 좌석 정보 업데이트
                    for (int i = 0; i < model.getRowCount(); i++) {
                        model.removeRow(i);
                    }
                    c = DB.loadUserReservation(id);
                    for (int i = 0; i < c.date.size(); i++) {
                        String[] data = {c.date.get(i), c.start.get(i), c.end.get(i), c.starttime.get(i), c.company.get(i), c.class_.get(i),c.price.get(i),c.seat.get(i)};
                        model.addRow(data);
                    }
                }
            }
        });

        bt_close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ReservationCheckUp.this.dispose();
            }
        });

        setVisible(true);
    }

    // 테이블에서 행 선택 시 이벤트 처리
    @Override
    public void mouseClicked(MouseEvent e) {
        String[] info = new String[8];     // 추출한 정보를 담을 배열
        int row = table.getSelectedRow();  // 테이블에서 선택한 행 인덱스 가져오기

        // 선택한 행에 대한 전체 데이터 추출
        for (int i = 0; i < table.getColumnCount(); i++) {
            info[i] = (String) table.getValueAt(row, i);
        }

        // 인스턴스 변수에 정보 전달
        this.arr = info;
    }
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
