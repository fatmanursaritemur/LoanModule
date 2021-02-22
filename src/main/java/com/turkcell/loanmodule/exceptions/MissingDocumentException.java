package com.turkcell.loanmodule.exceptions;

public class MissingDocumentException extends Exception {
  public MissingDocumentException() {
    super("Kredi isteyen müşterinin eksik dokümanlarları var");
  }

}
