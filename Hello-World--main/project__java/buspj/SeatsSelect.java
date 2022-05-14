package buspj;

import java.awt.*;             // 폰트 등 그래픽 처리를 위한 클래스들의 경로명
import java.awt.event.*;       // 이벤트 처리에 필요한 기본 클래스들의 경로명
import javax.swing.*;          // 스윙 컴포넌트 클래스들 경로명

// 선택한 좌석에 관한 클래스
class SelectSeats {
    int number;
    int check;

    public SelectSeats (int number, int check) {
        this.number = number;
        this.check = check;
    }

    public int get_number() {
        return this.number;
    }

    public int get_phone() {
        return this.check;
    }
}

// 뒤로가기 버튼
class SeatsBack extends JPanel {
    SeatsSelect frame;

    public SeatsBack(SeatsSelect frame, ReservationMain frame2) {
        Color mycor=new Color(189,215,238);
        setBackground(mycor);
        this.frame = frame;

        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        // 뒤로가기 버튼 이미지
        ImageIcon backIcon = new ImageIcon("project__java/buspj/image/back.png");
        Image backImg = backIcon.getImage();
        Image backUpdate = backImg.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon backUpdateIcon = new ImageIcon(backUpdate);

        ImageIcon backIcon2 = new ImageIcon("project__java/buspj/image/back2.png");
        Image backImg2 = backIcon2.getImage();
        Image backUpdate2 = backImg2.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon backUpdateIcon2 = new ImageIcon(backUpdate2);

        JButton back = new JButton(backUpdateIcon);
        back.setPreferredSize(new Dimension(100,50));
        back.setRolloverIcon(backUpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        back.setBorderPainted(false); // 버튼 테두리 설정해제
        back.setFocusPainted(false);
        back.setContentAreaFilled(false);
        back.setOpaque(false);
        add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame2.setVisible(true);
                frame.setVisible(false);
            }
        });
    }
}

// 화면 맨 위 회색 부분
class SeatsNorth extends JPanel {
    SeatsSelect frame;

    public SeatsNorth(SeatsSelect frame, ReservationMain frame2) {
        Color mycor=new Color(189,215,238);
        setBackground(mycor);
        this.frame = frame;

        setLayout(new BorderLayout());
        add(new Title(), BorderLayout.WEST);
        add(new SeatsBack(this.frame, frame2), BorderLayout.EAST);
    }
}

// 화면 가운데 부분
class SeatsCenter extends JPanel implements MouseListener {
    String id;
//    int number = 0;   // 인원
    int price = 0;    // 가격
//    int[][] seatArr = new int[7][5];  // 좌석 번호 배열
//    JLabel personnel; // 인원을 담을 JLabel
    JLabel seatInt;   // 좌석번호를 담을 JLabel
    ImageIcon updateWhiteIcon;
    JLabel[][] img = new JLabel[7][5];  // 좌석 배열
    int seatNum[][] = new int[7][5];      // 선택한 좌석 번호
    int[][] checkNum;    // 해당 좌석이 선택된 좌석인지 알려주는 배열
    String[][] userid;  // 해당 좌석을 선택한 유저들 모음
    int[][] pay;         // 해당 좌석 결제 여부 판단
//    static JLabel[] img2 = new JLabel[29];    // 좌석 정보를 담을 1차원 배열
    int row = 10000;
    int col = 10000;
    int check;
    DB_connect DB = new DB_connect();  // DB

    public SeatsCenter(SeatsSelect frame, String id, String start, String end, String date, String[] info) {
        setLayout(null);
        this.id = id;   // 회원 아이디 정보 저장
        this.price = Integer.valueOf(info[4]);
        Color bgmycor=new Color(166,222,249);
        setBackground(bgmycor);
        Color mycor=new Color(189,215,238);
        //setBackground(Color.WHITE);
        // '예매하기' 글자
        JLabel title = new JLabel("예매하기");
        title.setBounds(60, 3, 150, 100);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        add(title);

        // 좌석 표시 패널
        JPanel seatsTable = new JPanel();
        seatsTable.setBackground(Color.WHITE);
        seatsTable.setLayout(null);
        seatsTable.setBounds(275, 85, 450,550);
        add(seatsTable);

        // 선택좌석, 불가능좌석 표시
        this.create_seats(seatsTable);
        // 좌석 초기화
        this.init_seats(seatsTable);

        // 가격 테이블 생성
        JPanel seatTable = new JPanel();
        seatTable.setBackground(Color.WHITE);
        seatTable.setLayout(new BorderLayout());
        seatTable.setBounds(775, 430,80,70);
        add(seatTable);

        // 가격 테이블 열 이름 공간
        JPanel column = new JPanel();
        column.setBackground(Color.LIGHT_GRAY);
        column.setLayout(new FlowLayout(FlowLayout.LEFT, 20,6));
        column.setSize(100,35);
        seatTable.add(column, BorderLayout.NORTH);

        // 좌석 열 이름 글자
        JLabel colName = new JLabel("좌석");
        colName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        column.add(colName);

        // 인원, 가격 패널
        JPanel text = new JPanel();
        text.setBackground(Color.WHITE);
        text.setLayout(new FlowLayout(FlowLayout.LEFT, 28,6));
        text.setSize(100,35);
        seatTable.add(text, BorderLayout.CENTER);

        // 인원 디폴트 세팅
//        personnel = new JLabel("" + number);
//        personnel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
//        text.add(personnel);

        // 가격 디폴트 세팅
        seatInt = new JLabel("");
        seatInt.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        seatInt.setVisible(false);   // 초기에는 숨기고 있음.
        text.add(seatInt);

//        // 결제진행 버튼 생성
//        JButton payment = new JButton("결제진행");
//        payment.setBounds(820, 550, 100,40);
//        payment.setFont(new Font("맑은 고딕", Font.BOLD, 15));
//        add(payment);

        // 결제진행 버튼 이미지
        ImageIcon paymentIcon = new ImageIcon("project__java/buspj/image/payment.png");
        Image paymentImg = paymentIcon.getImage();
        Image paymentUpdate = paymentImg.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon paymentUpdateIcon = new ImageIcon(paymentUpdate);

        ImageIcon paymentIcon2 = new ImageIcon("project__java/buspj/image/payment2.png");
        Image paymentImg2 = paymentIcon2.getImage();
        Image paymentUpdate2 = paymentImg2.getScaledInstance(100,50, Image.SCALE_SMOOTH);
        ImageIcon paymentUpdateIcon2 = new ImageIcon(paymentUpdate2);

        JButton payment = new JButton(paymentUpdateIcon);
        payment.setBounds(820,550,100,50);
        payment.setRolloverIcon(paymentUpdateIcon2); // 버튼에 마우스가 올라갈떄 이미지 변환
        payment.setBorderPainted(false); // 버튼 테두리 설정해제
        payment.setFocusPainted(false);
        payment.setContentAreaFilled(false);
        payment.setOpaque(false);
        add(payment);

        // 결제진행 버튼 클릭 이벤트
        payment.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (col == 10000) {
                    JOptionPane.showMessageDialog(null, "좌석을 선택하세요.");
                } else {
                    DB.seat_check(seatNum[row][col], check, id);
                    new Payment(frame, id, start, end, date, info, price, seatNum[row][col]);
                    frame.setVisible(false);
                }
            }
        });
        ImageIcon background = new ImageIcon("project__java/buspj/image/test.jpg");
        JLabel image2 = new JLabel(background);
        image2.setBounds(-1800,-200,4500,1200);
        add(image2);
    }

    // 좌석 이미지 생성
    public void create_seats(JPanel p) {
        // 선택 가능 이미지
        ImageIcon possible = new ImageIcon("project__java/buspj/image/white_seats.png");
        Image possibleImage = possible.getImage();
        Image updatePossibleImg = possibleImage.getScaledInstance(40,40,Image.SCALE_SMOOTH);
        ImageIcon updatePossibleIcon = new ImageIcon(updatePossibleImg);

        JLabel possibleSeats = new JLabel(updatePossibleIcon);
        possibleSeats.setBounds(12,210,50,50);
        possibleSeats.setHorizontalAlignment(JLabel.CENTER);
        p.add(possibleSeats);

        // 선택 가능 텍스트
        JLabel possibleText = new JLabel("선택가능");
        possibleText.setFont(new Font("맑은 고딕", Font.BOLD, 10));
        possibleText.setBounds(17, 260, 50, 15);
        p.add(possibleText);

        // 선택 불가능 이미지
        ImageIcon impossible = new ImageIcon("project__java/buspj/image/black_seats.png");
        Image impossibleImage = impossible.getImage();
        Image updateImpossibleImg = impossibleImage.getScaledInstance(40,40,Image.SCALE_SMOOTH);
        ImageIcon updateImpossibleIcon = new ImageIcon(updateImpossibleImg);

        JLabel impossibleSeats = new JLabel(updateImpossibleIcon);
        impossibleSeats.setBounds(12,290,50,50);
        impossibleSeats.setHorizontalAlignment(JLabel.CENTER);
        p.add(impossibleSeats);

        // 선택 불가능 텍스트
        JLabel impossibleText = new JLabel("선택불가능");
        impossibleText.setFont(new Font("맑은 고딕", Font.BOLD, 10));
        impossibleText.setBounds(12, 340, 60, 15);
        p.add(impossibleText);
    }

    // 좌석 초기화 메소드
    public void init_seats(JPanel p) {
        // 아무것도 선택하지 않은 초기 좌석 이미지 생성
        ImageIcon white = new ImageIcon("project__java/buspj/image/white_seats.png");
        Image whiteImage = white.getImage();
        Image updateWhiteImg = whiteImage.getScaledInstance(60,60,Image.SCALE_SMOOTH);
        updateWhiteIcon = new ImageIcon(updateWhiteImg);

        // 좌석 번호 설정
        for (int i = 0; i < seatNum.length; i++) {
            for (int j = 0; j < seatNum[i].length; j++) {
                if (j == 2 && i != 6) {
                    continue;
                }
                else if (j >= 3) {
                    if (i == 6) {
                        seatNum[i][j] = i*10 + j+1;
                    } else {
                        seatNum[i][j] = i*10 + j;
                    }
                } else {
                    seatNum[i][j] = i*10 + j+1;
                }
            }
        }

        // 해당 좌석이 선택된 것인가를 알기위해 DB에서 불어와보기
        checkNum = DB.come_seats();
        // 해당 좌석을 선택한 아이디를 DB에서 불러오기
        userid = DB.come_userid();
        // 해당 좌석 결제했나?
        pay = DB.come_pay();

        // 이미지 저장 과정
        JLabel seat;
        for (int i = 0; i < img.length; i++ ) {
            for (int j = 0; j < img[i].length; j++) {
                if (j == 2 && i != img.length - 1) {
                    continue;
                } else {
                    if (checkNum[i][j] == 0) {
                        // JLabel에 이미지 삽입
                        seat = new JLabel(updateWhiteIcon);
                        seat.setBounds(73 + j*60,30 + i*70,60,70);
                        seat.setHorizontalAlignment(JLabel.CENTER);

                        // 2차원 배열에 좌석 이미지 저장 후 화면에 출력
                        img[i][j] = seat;
                        p.add(img[i][j]);
                    } else {
                        // 선택된 좌석 이미지 생성
                        ImageIcon black = new ImageIcon("project__java/buspj/image/black_seats.png");
                        Image blackImage = black.getImage();
                        Image updateBlackImg = blackImage.getScaledInstance(60,60,Image.SCALE_SMOOTH);
                        ImageIcon updateBlackIcon = new ImageIcon(updateBlackImg);

                        seat = new JLabel(updateBlackIcon);
                        seat.setBounds(73 + j*60,30 + i*70,60,70);
                        seat.setHorizontalAlignment(JLabel.CENTER);

                        // 2차원 배열에 좌석 이미지 저장 후 화면에 출력
                        img[i][j] = seat;
                        p.add(img[i][j]);
                    }

                    // 좌석번호 부여
//                    if (j != 3) {
//                        seatArr[i][j] = 1 + j;
//                    } else {
//                        seatArr[i][j] = 1 + (j - 1);
//                    }

                    img[i][j].addMouseListener(this);
                }
            }
        }
    }

    // 좌석 클릭 이벤트 처리
    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel s = (JLabel) e.getSource();

        if (s.getIcon().equals(updateWhiteIcon)) {
            // 검은색 좌석 이미지로 교체
            ImageIcon black = new ImageIcon("project__java/buspj/image/black_seats.png");
            Image blackImage = black.getImage();
            Image updateBlackImg = blackImage.getScaledInstance(60,60,Image.SCALE_SMOOTH);
            ImageIcon updateBlackIcon = new ImageIcon(updateBlackImg);

//            this.number += 1;  // 인원 수 증가
//            this.personnel.setText("" + this.number);
//            this.price += this.onePrice;  // 가격 증가
//            this.seatInt.setText("" + this.seatNum[row][col]);
//            this.seatInt.setVisible(true);  // 가격 화면에 표현
            for (int i = 0; i < img.length; i++) {
                for (int j = 0; j < img[i].length; j++) {
                    if (s.equals(img[i][j])) {
                        System.out.println("------------------------------------------------------");
                        System.out.println("jlbel 객체값");
                        System.out.println(img[i][j]);
                        System.out.println(i+" "+j);
                        System.out.println("시트 번호");
                        System.out.println(seatNum[i][j]);
                        System.out.println("------------------------------------------------------");

                        row = i;
                        col = j;
                    }
                }
            }
            this.seatInt.setText("" + this.seatNum[row][col]);
            this.seatInt.setVisible(true);  // 가격 화면에 표현
            check = 1;
//            int check = 1;   // 선택되었다는 표시
//            DB.seat_check(seatNum[row][col], check);

            // 새 이미지로 교체
            s.setIcon(updateBlackIcon);
        } else {
            ImageIcon white = new ImageIcon("project__java/buspj/image/white_seats.png");
            Image whiteImage = white.getImage();
            Image updateWhiteImg = whiteImage.getScaledInstance(60,60,Image.SCALE_SMOOTH);
            updateWhiteIcon = new ImageIcon(updateWhiteImg);

//            this.number -= 1;   // 인원 수 감소
//            this.personnel.setText("" + this.number);
//            this.price -= this.onePrice;  // 가격 감소
//            this.seatInt.setText("" + this.seatNum[row][col]);
            for (int i = 0; i < img.length; i++) {
                for (int j = 0; j < img[i].length; j++) {
                    if (s.equals(img[i][j])) {
                        if ((userid[i][j].equals(this.id) || userid[i][j].equals("0")) && pay[i][j] == 0) {
                            row = 10000;
                            col = 10000;
                            this.seatInt.setVisible(false);
                            check = 0;
                            s.setIcon(updateWhiteIcon);
                        } else {
                            JOptionPane.showMessageDialog(null, "선택할 수 없는 좌석입니다.");
                        }
                    }
                }
            }
        }
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

// 좌석 선택 클래스 전체적인 구조
public class SeatsSelect extends JFrame {
    public SeatsSelect(ReservationMain frame, String id, String start, String end, String date, String[] info) {
        setTitle("버스타슈~");
        setSize(1000,800);
        setResizable(false);
        setLocationRelativeTo(null);  // 프레임을 화면 정중앙에 뜨도록 설정
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Container mainContainer = getContentPane();
        mainContainer.setLayout(new BorderLayout());

        mainContainer.add(new SeatsNorth(this, frame), BorderLayout.NORTH);
        mainContainer.add(new SeatsCenter(this, id, start, end, date, info), BorderLayout.CENTER);

        addWindowListener(new JFrameWindowClosingEventHandler());

        setVisible(true);
    }
}
