package org.bananatigeer.customer;

import com.univocity.parsers.annotations.*;
import org.bananatigeer.custom_validators.StringValidator;
import org.bananatigeer.custom_validators.WhiteSpaceValidator;

public class Customer {

    @Parsed(field= "A")
    @Validate(validators = StringValidator.class)
    private String FirstName;

    @Parsed(field= "B")
    @Validate(validators = StringValidator.class)
    private String LastName;

    @Parsed(field= "C")
    @Validate(matches = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String Email;

    @Parsed(field= "D")
    @Validate(validators = StringValidator.class, oneOf = {"Male", "Female"})
    private String Gender;

    @Parsed(field= "E")
    @Validate(validators = WhiteSpaceValidator.class)
    private String Image;

    @Parsed(field= "F")
    @Validate()
    private String Company;

    @Parsed(field= "G")
    @Validate()
    @Replace(expression = "\\$", replacement = "")
    private Double Price;

    @Parsed(field= "H")
    @Validate()
    private Boolean H;

    @Parsed(field= "I")
    @Validate()
    private Boolean I;

    @Parsed(field= "J")
    @Validate()
    private String City;

    @Override
    public String toString(){
        return "[First Name= " +FirstName+" ,LastName= " +LastName+ ", Email=" +Email+ ", Gender=" +Gender+ ", Image= "
                +Image+ ", Company=" +Company+ ", Price=" +Price+ ", H=" +H+ ", I=" +I+ ", City="  +City+ "]";
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Boolean getH() {
        return H;
    }

    public void setH(Boolean h) {
        H = h;
    }

    public Boolean getI() {
        return I;
    }

    public void setI(Boolean i) {
        I = i;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
