package com.turkcell.loanmodule.exceptions;

public class AutoRejectCreditException extends Exception {

  public AutoRejectCreditException(String reason, long customerId) {

    super(String.format("%d'li müşterinin kredi isteği  %s sebebiyle reddedildi.", customerId, reason));
  }
}