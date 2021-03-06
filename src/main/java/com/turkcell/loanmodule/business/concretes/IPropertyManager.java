package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.ICreditService;
import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.business.abstracts.IEmployeeService;
import com.turkcell.loanmodule.business.abstracts.IPropertyService;
import com.turkcell.loanmodule.dataAccess.PropertyRepository;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.concretes.Employee;
import com.turkcell.loanmodule.entities.concretes.Property;
import com.turkcell.loanmodule.entities.enums.CreditStatus;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPropertyManager implements IPropertyService {

  @Autowired
  PropertyRepository propertyRepository;

  @Autowired
  ICustomerService customerService;

  @Autowired
  ICreditService creditService;

  @Autowired
  IEmployeeService employeeService;

  @Override
  public Customer getCurrentCustomer() throws Exception {
    String customerId = propertyRepository.findPropertyValueByPropertyName("currentCustomer");
    System.out.println(customerId);
    return customerService.findById(Long.valueOf(customerId));
  }

  @Override
  public Employee getCurrentEmployee() throws Exception {
    String employeeId = propertyRepository.findPropertyValueByPropertyName("currentEmployee");
    if ( employeeId == null ) {
      return null;
    }
    return employeeService.findById(Long.valueOf(employeeId));
  }


  @Override
  public Credit getCurrentCredit() throws Exception {
    Long creditId = Long
        .parseLong(propertyRepository.findPropertyValueByPropertyName("currentCredit"));
    return creditService.getCustomerByCredit(creditId).stream()
        .filter(credit -> credit.getStatus() != CreditStatus.DENIED
            || credit.getStatus() != CreditStatus.EXPIRED)
        .findFirst()
        .orElseThrow(Exception::new);
  }

  @Override
  public Property setProperty(String propertyName, Long id) {
    Property propertyUpdated = propertyRepository.findPropertyByPropertyName(propertyName);
    propertyUpdated.setPropertyValue(id.toString());
    return propertyRepository.save(propertyUpdated);
  }

  @Override
  public void updateCurrentCustomerAndCredit(Credit credit) {
    Property creditProperty = propertyRepository.findPropertyByPropertyName("currentCredit");
    creditProperty.setPropertyValue(credit.getId().toString());
    propertyRepository.save(creditProperty);

    Property creditCustomer = propertyRepository.findPropertyByPropertyName("currentCustomer");
    creditCustomer.setPropertyValue(credit.getCustomer().getId().toString());
    propertyRepository.save(creditCustomer);
  }

  @Override
  public void deleteAllProperties() {
    List<Property> propertiesList = propertyRepository.findAll();
    propertiesList.forEach(property -> {
      property.setPropertyValue(null);
      propertyRepository.save(property);
    });
  }
}
