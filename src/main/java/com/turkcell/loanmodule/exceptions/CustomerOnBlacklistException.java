package com.turkcell.loanmodule.exceptions;

public class CustomerOnBlacklistException extends Exception {

  public CustomerOnBlacklistException() {
    super("Credi isteyen müşteri blacklist'te");
  }
}
