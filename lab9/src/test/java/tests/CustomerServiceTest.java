package tests;

import static org.junit.Assert.*;

import java.util.List;

import entities.Product;
import org.junit.Test;

import services.CustomerService;
import services.CustomerServiceInterface;
import entities.Customer;

public class CustomerServiceTest {

	@Test
	public void testFindByName() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.findByName("Customer: 1");
		
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
	}

	@Test
	public void testFindByField() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

		//List<Customer> res = cs.findByField("name", 3);

		//assertNotNull("Result can't be null", res);
		//assertEquals(1, res.size());
	}

	@Test
	public void testCustomersWhoBoughtMoreThan() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

		List<Customer> res = cs.customersWhoBoughtMoreThan(4);

		assertNotNull("Result can't be null", res);
		assertEquals(3, res.size());
	}

	@Test
	public void testCustomersWhoSpentMoreThan() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

		List<Customer> res = cs.customersWhoSpentMoreThan(100.0);

		assertNotNull("Result can't be null", res);
		assertEquals(5, res.size());
	}

	@Test
	public void testCustomersWithNoOrders() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

		List<Customer> res = cs.customersWithNoOrders();

		assertNotNull("Result can't be null", res);
		assertEquals(3, res.size());
	}

	@Test
	public void testAddProductToAllCustomers() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

		cs.addProductToAllCustomers(new Product(100, "Super product", 20.0));

		List<Customer> res = cs.customersWithNoOrders();

		assertNotNull("Result can't be null", res);
		assertEquals(0, res.size());
	}

	@Test
	public void testWasProductBought() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		Product p = new Product(4, "Super product", 20.0);
		boolean res = cs.wasProductBought(p);
		assertTrue(res);
		p = new Product(100, "Super product", 20.0);
		res = cs.wasProductBought(p);
		assertFalse(res);
	}

//	@Test
//	public void testCountBuys() {
//		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
//		Product p = new Product(4, "Super product", 20.0);
//		int res = cs.countBuys(p);
//		assertEquals(res, 6);
//	}
}
