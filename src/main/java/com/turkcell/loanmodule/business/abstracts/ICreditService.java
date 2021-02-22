package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.exceptions.CustomerOnBlacklistException;
import com.turkcell.loanmodule.exceptions.MissingDocumentException;

public interface ICreditService {

  Credit apply(Credit credit) throws Exception;

  Credit getCredit(Long id) throws Exception;

  Credit rejectCredit(Credit credit,String reason);

  Customer getCustomer(Long id) throws Exception;

  Credit getCustomerByCredit(Long id) throws Exception;

 // CreditApplianceResultDto saveCreditApplianceResultDtoByCredit(Credit credit, String reason);
}
