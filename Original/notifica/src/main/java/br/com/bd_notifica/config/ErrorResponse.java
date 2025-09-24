package br.com.bd_notifica.config;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String message;

  public ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
    this.timestamp = timestamp;
    this.status = status;
    this.error = error;
    this.message = message;
  }

  public ErrorResponse(String string, String message2) {
    // TODO Auto-generated constructor stub
  }
}