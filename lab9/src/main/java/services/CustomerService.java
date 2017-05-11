package services;

import java.util.List;
import java.util.stream.Collectors;

import entities.Customer;
import entities.Product;

public class CustomerService implements CustomerServiceInterface {

	private List<Customer> customers;

	public CustomerService(List<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public List<Customer> findByName(final String name) {
		return customers.stream().filter( c -> c.getName().equals(name)).collect(Collectors.toList());
	}

	private boolean getByField(String fieldName, Object value){
		try {
			Customer.class.getDeclaredField(fieldName).setAccessible(true);
			return Customer.class.getDeclaredField(fieldName).get(value) != null;
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public List<Customer> findByField(String fieldName, Object value) {
//		return
		return customers.stream().filter(c -> getByField(fieldName, value)).collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWhoBoughtMoreThan(int number) {
		return customers.stream().filter(c -> c.getBoughtProducts().size() > number).collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWhoSpentMoreThan(double price) {
		return customers.stream().filter(c -> c.getBoughtProducts().stream().mapToDouble(Product::getPrice).sum() > price).collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWithNoOrders() {
		return customers.stream().filter(c -> c.getBoughtProducts().isEmpty()).collect(Collectors.toList());
	}

	@Override
	public void addProductToAllCustomers(Product p) {
		customers.stream().forEach(c -> c.getBoughtProducts().add(p));
	}

	@Override
	public double avgOrders(boolean includeEmpty) {
		//return customers.stream().mapToInt(c -> c.getBoughtProducts().stream().reduce(Integer::))
		return 0;
	}

	@Override
	public boolean wasProductBought(Product p) {
		return customers.stream().anyMatch(c -> c.getBoughtProducts().stream().anyMatch(product -> product.getId() == p.getId()));
	}

	@Override
	public List<Product> mostPopularProduct() {
		// TODO Auto-generated method stub
		//return customers.stream().filter(c -> c.getBoughtProducts().sort(p -> p.))
		return null;
	}

	@Override
	public int countBuys(Product p) {
		return 0; //return customers.stream().map(c -> c.getBoughtProducts().stream().filter(pr -> pr.getId() == p.getId())
	}

	@Override
	public int countCustomersWhoBought(Product p) {
		return 0; // customers.stream().map(c -> c.getBoughtProducts().stream().filter(product -> product.getId() == p.getId()));
	}

}
