package io.lightfeather.springtemplate;

public class Supervisor {

  private int id;
  private String phone;
  private String jurisdiction;
  private String identificationNumber;
  private String firstName;
  private String lastName;

  public Supervisor() {}

  public Supervisor(int id, String phone, String jurisdiction, String identificationNumber, String firstName, String lastName){
    this.id = id;
    this.phone = phone;
    this.jurisdiction = jurisdiction;
    this.identificationNumber = identificationNumber;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public int getId() {
    return this.id;
  }
  public String getPhone() {
    return this.phone;
  }
  public String getJurisdiction() {
    return this.jurisdiction;
  }
  public String getIdentificationNumber() {
    return this.identificationNumber;
  }
  public String getFirstName() {
    return this.firstName;
  }
  public String getLastName() {
    return this.lastName;
  }
}