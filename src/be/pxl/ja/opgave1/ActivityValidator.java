package be.pxl.ja.opgave1;

// de parsers moeten reeds weten of de activity correct is gelinked aan een customer, en logged de lijn, dit op de
// verschillende parser implementaties, dus aparte class om te valideren en te herbruiken
public class ActivityValidator {

	private final CustomerRepository customerRepository;

	ActivityValidator(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public boolean isCustomerValid(Activity activity) {
		return customerRepository.getByCustomerNumber(activity.getCustomerNumber()) != null;
	}

}
