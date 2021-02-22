package com.turkcell.loanmodule.dataAccess;

import com.turkcell.loanmodule.entities.dtos.CreditApplianceResultDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditApplianceResultDtoRepository  extends JpaRepository<CreditApplianceResultDto, Long> {

}
