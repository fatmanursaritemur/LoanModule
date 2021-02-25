package com.turkcell.loanmodule.exceptions;

public class NotFoundException extends Exception {

  public NotFoundException(String className, String id) {
    super(String.format("%s'li %s bulunamadı", id, className));
  }
}
