package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.dtos.CreditApplianceResultDto;
import java.util.List;

public interface ICreditApplianceResultDtoService {

  CreditApplianceResultDto save(CreditApplianceResultDto creditApplianceResultDto);

  CreditApplianceResultDto saveCreditApplianceResultDtoByCredit(Credit credit, String reason);

  List<CreditApplianceResultDto> getCreditApplianceResultDtoByCustomer(Customer customer);
}
