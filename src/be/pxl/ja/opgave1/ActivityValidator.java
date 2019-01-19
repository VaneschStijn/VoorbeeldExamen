package be.pxl.ja.opgave1;

public class ActivityValidator {

	private final CustomerRepository customerRepository;

	ActivityValidator(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public boolean isCustomerValid(Activity activity) {
		return customerRepository.getByCustomerNumber(activity.getCustomerNumber()) != null;
	}

}
