package amu.database;

import amu.model.Cart;
import amu.model.CartItem;
import amu.model.Customer;
import amu.model.Order;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {

	public List<Order> browse(Customer customer) {
		List<Order> orders = new ArrayList<Order>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = Database.getConnection();
			String query = "SELECT * FROM `order` WHERE customer_id=?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, customer.getId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				AddressDAO addressDAO = new AddressDAO();
				Calendar createdDate = Calendar.getInstance();
				createdDate.setTime(resultSet.getDate("created"));
				orders.add(new Order(resultSet.getInt("id"),
						customer, 
						addressDAO.read(resultSet.getInt("address_id")), 
						createdDate, 
						resultSet.getString("value"), 
						resultSet.getInt("status")));
			}
		} catch (SQLException exception) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
		} finally {
			Database.close(connection, statement, resultSet);
		}

		return orders;
	}
	
//	/**
//	 * When an placed order is edited, this method should be used when placing the changes
//	 * @param order
//	 * @param cart
//	 * @return
//	 */
//	public boolean update(Order order, Cart cart){
//		
//		Map<String, CartItem> items = cart.getItems();
//		Iterator<Entry<String, CartItem>> it = items.entrySet().iterator();
//
//		while(it.hasNext()){
//			Map.Entry<String, CartItem> pairs = (Entry<String, CartItem>) it.next();
//			CartItem cartItem = pairs.getValue();
//			cartItem.setQuantity(cartItem.getQuantity()*(-1));
//			it.remove();
//			if(!addItemsToOrder(order, cartItem)){
//				return false;
//			}
//		}
//		return true;
//	}
	
	public boolean add(Order order, Cart cart){
		if(addOrder(order)){
		System.out.println("Order id in change order: " + order.getId());
			Map<String, CartItem> items = cart.getItems();
			Iterator<Entry<String, CartItem>> it = items.entrySet().iterator();

			while(it.hasNext()){
				Map.Entry<String, CartItem> pairs = (Entry<String, CartItem>) it.next();
				CartItem cartItem = pairs.getValue();
				it.remove();
				if(!addItemsToOrder(order, cartItem)){
					return false;
				}
			}

			return true;
		}
		
		return false;

	}

	public boolean addItemsToOrder(Order order, CartItem cartItem){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try{
			connection = Database.getConnection();
			String query = "INSERT INTO `order_items` (order_id, book_id, quantity, price, status) VALUES (?, ?, ?, ?, ?)";

			System.out.println("OrderDAO: " +order);
			System.out.println("OrderDAO: " +cartItem);
			
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, order.getId());
			statement.setInt(2, cartItem.getBook().getId());
			statement.setInt(3, cartItem.getQuantity());
			statement.setFloat(4, cartItem.getBook().getPrice());
			statement.setInt(5, order.getStatus());
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			if(resultSet.next()){
				return true;
			}

		}catch(SQLException e){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
		}finally{
			Database.close(connection, statement, resultSet);
		}

		return false;
	}

	public boolean addOrder(Order order) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = Database.getConnection();

			String query = "INSERT INTO `order` (customer_id, address_id, created, value, status) VALUES (?, ?, CURDATE(), ?, ?)";
			statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, order.getCustomer().getId());
			statement.setInt(2, order.getAddress().getId());
			statement.setBigDecimal(3, new BigDecimal(order.getValue()));
			statement.setInt(4, order.getStatus());
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			if (resultSet.next()) {
				int newID = resultSet.getInt(1);
				order.setId(newID);
				return true;
			}
		} catch (SQLException exception) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, exception);
		} finally {
			Database.close(connection, statement, resultSet);
		}

		return false;
	}

	public Cart getOrderItems(int orderID, int customerID){
		Cart cart = new Cart();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		String query = 
				"SELECT order_items.book_id, order_items.quantity " +
				"FROM `order_items` " +
				"JOIN `order` ON order.id = order_items.order_id " +
				"WHERE order.customer_id = ? " +
				"AND order.id = ?";

		try{
			connection = Database.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, customerID);
			statement.setInt(2,  orderID);
			resultSet = statement.executeQuery();

			while(resultSet.next()){
				CartItem ci = new CartItem(
						new BookDAO().findByISBN(String.valueOf(
								resultSet.getInt("book_id"))), 
								resultSet.getInt("quantity"));
				cart.addItem(ci);
			}

		}catch(SQLException e){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
		}finally{
			Database.close(connection, statement, resultSet);
		}


		return cart;
	}

	public Order findByOrderID(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		String query = "SELECT * from `order` WHERE id = ?";
		Order order = null;

		try {
			connection = Database.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			while(resultSet.next()){
				AddressDAO addressDAO = new AddressDAO();
				Calendar createDate = Calendar.getInstance();
				createDate.setTime(resultSet.getDate("created"));
				order = new Order(resultSet.getInt("id"),
						addressDAO.read(resultSet.getInt("address_id")),
						createDate,
						resultSet.getString("value"),
						resultSet.getInt("status"));
			}

		} catch (SQLException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
		} finally{
			Database.close(connection, statement, resultSet);
		}

		return order;
	}
}
