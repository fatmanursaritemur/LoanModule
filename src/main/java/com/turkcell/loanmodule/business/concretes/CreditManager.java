package com.turkcell.loanmodule.business.concretes;

import com.turkcell.loanmodule.business.abstracts.IBlacklistService;
import com.turkcell.loanmodule.business.abstracts.ICreditApplianceResultDtoService;
import com.turkcell.loanmodule.business.abstracts.ICreditService;
import com.turkcell.loanmodule.business.abstracts.ICustomerService;
import com.turkcell.loanmodule.business.abstracts.IFileService;
import com.turkcell.loanmodule.dataAccess.CreditRepository;
import com.turkcell.loanmodule.entities.concretes.Credit;
import com.turkcell.loanmodule.entities.concretes.Customer;
import com.turkcell.loanmodule.entities.enums.CreditStatus;
import com.turkcell.loanmodule.exceptions.CustomerOnBlacklistException;
import com.turkcell.loanmodule.exceptions.MissingDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class CreditManager implements ICreditService {

  @Autowired
  CreditRepository creditRepository;

  @Autowired
  IBlacklistService blacklistService;

 @Autowired
 ICreditApplianceResultDtoService creditApplianceResultDtoService;

  @Autowired
  IFileService fileService;

  @Autowired
  ICustomerService customerService;

  @Transactional
  @Override
  public Credit apply(Credit credit) throws Exception {
   Customer customerChecked=customerService.findById(credit.getCustomer().getId());
   if(!fileService.isExistCustomersIdAndCreditPhotocopies(customerChecked)){
      rejectCredit(credit, "Customer has missing documents");
      throw  new MissingDocumentException();
    }
    if ( Boolean.TRUE.equals(blacklistService.isCustomerOnBlacklist(customerChecked)) ) { // ??
      rejectCredit(credit, "Customer in blacklist");
      throw new CustomerOnBlacklistException();
    }
    //kaydedildi yap
    return creditRepository.save(credit);
  }

  @Override
  public Credit rejectCredit(Credit credit, String reason) {
    credit.setStatus(CreditStatus.DENIED);
    Credit creditt = creditRepository.save(credit);
    creditApplianceResultDtoService.saveCreditApplianceResultDtoByCredit(creditt, reason);
    return creditt;
  }

  @Override
  public Credit getCredit(Long id) throws Exception {
    return creditRepository.findById(id).orElseThrow(Exception::new);
  }


  /****************************** FOR TEST*********************************/
  @Override
  public Customer getCustomer(Long id) throws Exception {
    getCredit(id).getCustomer().getCredit();
    return getCredit(id).getCustomer();
  }

  @Override
  public Credit getCustomerByCredit(Long id) throws Exception {
    return getCredit(id).getCustomer().getCredit();
  }

  /******************* Delete********************************************/

   /* @Override
  public CreditApplianceResultDto saveCreditApplianceResultDtoByCredit(Credit credit, String reason) {
    CreditApplianceResultDto creditApplianceResultDto = modelMapper
        .map(credit, CreditApplianceResultDto.class);
    creditApplianceResultDto.setReason(reason);
    return creditApplianceResultDtoService.save(creditApplianceResultDto);
  }*/
}
