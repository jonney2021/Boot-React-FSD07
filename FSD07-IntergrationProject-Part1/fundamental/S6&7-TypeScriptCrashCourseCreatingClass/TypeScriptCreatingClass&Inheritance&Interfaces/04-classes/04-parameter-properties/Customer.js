"use strict";
class Customer {
    constructor(_firstName, _lastName) {
        this._firstName = _firstName;
        this._lastName = _lastName;
    }
    get firstName() {
        return this._firstName;
    }
    set firstName(value) {
        this._firstName = value;
    }
    get lastName() {
        return this._lastName;
    }
    set lastName(value) {
        this._lastName = value;
    }
}
//let's create an instance
let myCustomer = new Customer("Martin", "Dixon");
console.log(myCustomer.firstName);
console.log(myCustomer.lastName);
