package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.ICreditApplianceResultDtoService;
import com.turkcell.loanmodule.dataAccess.CreditApplianceResultDtoRepository;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.dtos.CreditApplianceResultDto;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditApplianceResultDtoManager implements ICreditApplianceResultDtoService {

  @Autowired
  CreditApplianceResultDtoRepository creditApplianceResultDtoRepository;

  @Autowired
  ModelMapper modelMapper;

  @Override
  public CreditApplianceResultDto save(CreditApplianceResultDto creditApplianceResultDto) {
    return creditApplianceResultDtoRepository.save(creditApplianceResultDto);
  }

  @Override
  public CreditApplianceResultDto saveCreditApplianceResultDtoByCredit(Credit credit,
      String reason) {
    CreditApplianceResultDto creditApplianceResultDto = modelMapper
        .map(credit, CreditApplianceResultDto.class);
    creditApplianceResultDto.setCredit(credit);
    creditApplianceResultDto.setReason(reason);
    return save(creditApplianceResultDto);
  }

  @Override
  public List<CreditApplianceResultDto> getCreditApplianceResultDtoByCustomer(Customer customer) {
    return creditApplianceResultDtoRepository.findAll()
        .stream()
        .filter(creditApplianceResultDto -> creditApplianceResultDto.getCredit().getCustomer()
            .equals(customer))
        .collect(Collectors.toList());
  }
}
