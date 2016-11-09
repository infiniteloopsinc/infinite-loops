package info.infiniteloops.gson;

import java.util.List;

/**
 * Created by asna on 11/6/16.
 */

public class GSONModel {

    /**
     * lastName : doe
     * age : 26
     * phoneNumbers : [{"type":"iPhone","number":"0123-4567-8888"},{"type":"home","number":"0123-4567-8910"}]
     * firstName : John
     * address : {"postalCode":"630-0192","city":"Nara","streetAddress":"naist street"}
     */

    private String lastName;
    private int age;
    private String firstName;
    /**
     * postalCode : 630-0192
     * city : Nara
     * streetAddress : naist street
     */

    private AddressBean address;
    /**
     * type : iPhone
     * number : 0123-4567-8888
     */

    private List<PhoneNumbersBean> phoneNumbers;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public List<PhoneNumbersBean> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumbersBean> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public static class AddressBean {
        private String postalCode;
        private String city;
        private String streetAddress;

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreetAddress() {
            return streetAddress;
        }

        public void setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
        }
    }

    public static class PhoneNumbersBean {
        private String type;
        private String number;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
