package be.pxl.ja.opgave1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepository {
	private Map<String, Customer> customers = new HashMap<>();
	
	public CustomerRepository() {
		for (Customer customer : Customers.customers) {
			customers.put(customer.getCustomerNumber(), customer);
		}
	}
	
	public Customer getByCustomerNumber(String customerNumber) {
		// TODO: voorzie hier de implementatie om een klant te zoeken adhv zijn klantnummer
		return null;
	}
	
	public List<Customer> findAll() {
		// TODO: voorzie hier de implementatie om een lijst me alle klanten te retourneren
		return null;
	}
}
