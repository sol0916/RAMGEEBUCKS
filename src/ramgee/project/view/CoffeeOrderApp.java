package ramgee.project.view;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ramgee.project.dao.*;
import ramgee.project.vo.*;

public class CoffeeOrderApp {

	public static void main(String[] args) throws SQLException {
		// 주문을 위한 상품 정보 생성
		ProductVO americano = new ProductVO(1, "아메리카노", 3000, "커피 음료", 1);
		ProductVO latte = new ProductVO(2, "카페라떼", 3500, "커피 음료", 1);

		// 장바구니 생성
		CartVO cart = new CartVO(1, 1, new ArrayList<>());

		// 주문 생성
		OrderVO order = new OrderVO(1, cart.getCart_no(), new Date(System.currentTimeMillis()),
				2, 6500, "주문 완료", new ArrayList<>());

		// 상품 추가
		order.getProducts().add(americano);
		order.getProducts().add(latte);

		// 주문을 장바구니에 추가
		cart.getOrders().add(order);

		// OrderDAO와 CartDAO를 활용하여 데이터베이스에 주문 정보와 장바구니 정보를 저장

		// OrderDAO와 CartDAO 객체 생성
		OrderDAO orderDAO = new OrderDAO();
		CartDAO cartDAO = new CartDAO();

		// 주문 정보와 장바구니 정보 저장
		orderDAO.addOrder(order);
		cartDAO.addCart(cart);

		// 주문 정보와 장바구니 정보를 데이터베이스에서 가져오기
		List<OrderVO> orders = orderDAO.findAllOrders();
		List<CartVO> carts = cartDAO.findAllCarts();

		// 가져온 주문 정보와 장바구니 정보 출력
		for (OrderVO o : orders) {
			System.out.println("Order No: " + o.getOrder_no());
			System.out.println("Cart No: " + o.getCart_no());
			System.out.println("Order Date: " + o.getOrderDate());
			System.out.println("Total Amount: " + o.getTotalAmount());
			System.out.println("Total Price: " + o.getTotalPrice());
			System.out.println("Status: " + o.getStatus());

			System.out.println("Products:");
			for (ProductVO p : o.getProducts()) {
				System.out.println(" - " + p.getName() + " (" + p.getAmount() + ")");
			}

			System.out.println();
		}

		for (CartVO c : carts) {
			System.out.println("Cart No: " + c.getCart_no());
			System.out.println("Member No: " + c.getMember_no());

			System.out.println("Orders:");
			for (OrderVO o : c.getOrders()) {
				System.out.println(" - Order No: " + o.getOrder_no());
			}

			System.out.println();
		}
	}
}

