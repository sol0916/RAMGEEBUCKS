package ramgee.project.view;

import javax.swing.*;

import ramgee.project.dao.OrderDAO;
import ramgee.project.dao.PayDAO;
import ramgee.project.vo.PayVO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Payswingg extends JFrame {

   private JLabel titleLabel;
   private JLabel totalPayLabel;
   private JTextField cardNumTextField;
   private JTextField cardDateTextField;
   private JTextField cardCsvTextField;
   private JButton cardButton;
   private JButton cashButton;

   private PayDAO payDAO;
   public Payswingg() {
      initializeUI();
      addComponents();
      attachListeners();
   }

   private void initializeUI() {
      setTitle("결제 프로그램");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(350, 300);
      setLayout(new BorderLayout()); // BorderLayout으로 변경
   }

   private void addComponents() {
       titleLabel = new JLabel("지불방법을 선택해주세요.");
       cardButton = new JButton("카드");
       cashButton = new JButton("현금");

       JPanel centerPanel = new JPanel();
       centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS)); // Y축 방향으로 컴포넌트를 배치하는 BoxLayout 사용
      centerPanel.setLayout(new FlowLayout()); 
       
       
       centerPanel.add(titleLabel);
       centerPanel.add(Box.createVerticalStrut(10)); // 수직 간격 추가
       centerPanel.add(cardButton);
       centerPanel.add(Box.createVerticalStrut(10)); // 수직 간격 추가
       centerPanel.add(cashButton);

       add(centerPanel, BorderLayout.CENTER);
   }

   private void attachListeners() {
      cardButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            cardPayment();
         }
      });

      cashButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            cashPayment();
         }
      });
   }


   private void cardPayment() {
      // 카드 결제 로직 구현
      int totalPay = findPayByOrder_no(); // 총 결제금액을 가져옴

      // 패널 생성
      JPanel centerPanel = new JPanel();
      centerPanel.setLayout(null); // 패널의 레이아웃을 null로 설정
      //add(centerPanel, BorderLayout.CENTER); // 프레임의 중앙에 패널 추가

      // 총 결제금액 레이블
      totalPayLabel = new JLabel("총 결제금액은 " + totalPay + "입니다.");
      totalPayLabel.setBounds(10, 10, 260, 20);
      centerPanel.add(totalPayLabel);

      // 총 결제금액 레이블
      //      totalPayLabel = new JLabel("총 결제금액은 " + totalPay + "입니다.");
      //      totalPayLabel.setBounds(10, 10, 260, 20);
      //      centerPanel.add(totalPayLabel);

      // 카드번호 입력 필드
      JLabel cardNumLabel = new JLabel("카드번호 16자리를 입력해주세요:");
      cardNumLabel.setBounds(10, 40, 260, 20);
      centerPanel.add(cardNumLabel);

      cardNumTextField = new JTextField();
      cardNumTextField.setBounds(10, 60, 260, 20);
      centerPanel.add(cardNumTextField);

      // 카드 유효기간 입력 필드
      JLabel cardDateLabel = new JLabel("카드 유효기간을 입력해주세요:");
      cardDateLabel.setBounds(10, 90, 260, 20);
      centerPanel.add(cardDateLabel);

      cardDateTextField = new JTextField();
      cardDateTextField.setBounds(10, 110, 260, 20);
      centerPanel.add(cardDateTextField);

      // 카드 CSV 입력 필드
      JLabel cardCsvLabel = new JLabel("카드 csv 3자리를 입력해주세요:");
      cardCsvLabel.setBounds(10, 140, 260, 20);
      centerPanel.add(cardCsvLabel);

      cardCsvTextField = new JTextField();
      cardCsvTextField.setBounds(10, 160, 260, 20);
      centerPanel.add(cardCsvTextField);

      // 결제 버튼
      JButton paymentButton = new JButton("결제");
      paymentButton.setBounds(110, 190, 80, 30);
      paymentButton.addActionListener(e -> {
         showReceiptDialog();
         dispose(); // 결제 완료 후 프레임 종료
      });
      centerPanel.add(paymentButton);

      // 패널을 프레임에 추가
      add(centerPanel, BorderLayout.CENTER);

      setVisible(true); // 프레임을 보이도록 설정
      //setLocationRelativeTo(null);
   }
   private void showReceiptDialog() {
      String[] options = {"Y", "N"};
      int response = JOptionPane.showOptionDialog(this, "영수증을 확인하시겠습니까?", "결제 완료", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
      if (response == JOptionPane.YES_OPTION) {
         // 영수증 확인 작업 수행
         // 영수증 시각화는 포기.................. 마음의 눈으로 보기!
         // 영수증 확인 로직을 구현하세요
         JOptionPane.showMessageDialog(this, "영수증출력이 완료되었습니다.", "영수증 확인", JOptionPane.INFORMATION_MESSAGE);
      }else if (response == JOptionPane.NO_OPTION) {

         JOptionPane.showMessageDialog(this, "결제가 완료되었습니다.", "영수증 확인", JOptionPane.INFORMATION_MESSAGE);
      }
   }

   private void cashPayment() {
      int totalPay = findPayByOrder_no(); // 총 결제금액을 가져옴

      // 패널 생성
      JPanel centerPanel = new JPanel();
      centerPanel.setLayout(null); // 패널의 레이아웃을 null로 설정

      // 총 결제금액 레이블
      totalPayLabel = new JLabel("총 결제금액은 " + totalPay + "입니다.");
      totalPayLabel.setBounds(10, 10, 260, 20);
      centerPanel.add(totalPayLabel);


      String cashAmountString = JOptionPane.showInputDialog(this, "현금을 투입해주세요.", "현금 결제", JOptionPane.PLAIN_MESSAGE);

      int cashAmount = Integer.parseInt(cashAmountString); //현금입력값


      //int totalPay = (int) findPayByOrder_no(); // 총 결제금액을 가져옴

      if (cashAmount >= totalPay) {
         JOptionPane.showMessageDialog(this, totalPay + "원을 결제하였습니다.", "결제 완료", JOptionPane.INFORMATION_MESSAGE);
         JOptionPane.showMessageDialog(this, "거스름돈 " + (cashAmount - totalPay) + "원 입니다.", "거스름돈", JOptionPane.INFORMATION_MESSAGE);
         dispose(); // 결제 완료 후 프로그램 종료
      } else {
         JOptionPane.showMessageDialog(this, "투입한 현금이 부족합니다.", "결제 오류", JOptionPane.ERROR_MESSAGE);
      }
   }

   private int findPayByOrder_no() {
      
      int Total_price = 1; // 예시로 주문 번호를 1로 설정
      int totalPay = 5000;

      // PayDAO에서 findPayByOrder_no를 호출하여 총 결제금액을 가져옴
      PayDAO payDAO = new PayDAO();
      PayVO payVO = payDAO.findPayByOrder_no(Total_price);

      if (payVO != null) {
         totalPay = (int) payVO.getAmountPaid();
      }

      return totalPay;
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new Payswingg().setVisible(true);
         }
      });
   }
}