package com.turkcell.loanmodule.business.abstracts;

import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.dtos.CreditApplianceResultDto;

public interface ICreditApplianceResultDtoService {

  CreditApplianceResultDto save(CreditApplianceResultDto creditApplianceResultDto);

  CreditApplianceResultDto saveCreditApplianceResultDtoByCredit(Credit credit, String reason);
}
