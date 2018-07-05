# MOIP Payment System

**Version 1.0.0**

This is a system built to allow customers and clients to perform payments with ease. It allows you to register and maintain: Customers, Clients and Payments and your related informations through a simple API.

---

## Geting Started

This section will show you what resources you need in order to run the project in your local machine and also a quick how-to.

### Requirements

These are the main components you will need in order to run the application properly. Spring tool suite is not mandatory, though it's recommendable for its ease to deal with spring frameworks.

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Spring Tool Suite](https://spring.io/tools)

---

## Running the application locally

* Download the zip file and import it into your local IDE

* There are several ways to run a Spring Boot application on your local machine. The easiest one is to run the 'main method' from  `com.moip.MoipPaymentApplication.java` class from your IDE.

* After that you will be able to access the API through [http://localhost:9000](http://localhost:9000)

---

## Architecture and Design

#### This application was built according to MVC, so you will find three packages at the main folder:
	1.Model Package containing all the model structure and entity definition;
	2.Controller Package containing all control classes that communicate with the front-end;
	3.Repository Package containing all data access interfaces that communicate with the database.
	
#### In addition, you will also find two more packages at 'test folder' with test classes. It contains unit tests and some other tests.

### Accessing the DB

* This project was build with H2 Database, which is configurable directly from project dependencies when creating a new spring boot project.
  To access it click on: [http://localhost:9000/H2](http://localhost:9000/H2)

* All the database configuration can be found at the 'application.properties' file inside 'resources' folder.

---
## The API

* The API's methods consume information via JSON data. You may use the most suitable software/framework for you to generate it.

### Data Structure

* In order to be able to use the API properly, you need to follow the data structure of each entity as per below:

***Payment***

column | type
--- | ---
id | integer *primary key*
amount | double
paymentStatus | PaymentStatus
buyer | Buyer
client | Client
paymentMethod | PaymentMethod

***PaymentMethod***

column | type
--- | ---
id | integer *primary key*
Method | String
boleto | Boleto
creditCard | CreditCard

***CreditCard***

column | type
--- | ---
id | integer *primary key*
brand | String
Holder | Holder
cardNumber | String
expirationDate | String
cvv | String

***Holder***

column | type
--- | ---
id | integer *primary key*
name | String
birthDate | String
documentNumber | String

***Buyer***

column | type
--- | ---
id | integer *primary key*
email | String
cpf | String
nome | String

***Client***

column | type
--- | ---
id | integer *primary key*
name | String
---

### Operations

* The main operations are:

	1. Registering a new Buyer: 'POST (http://localhost:9000/buyer)' Please, notice that for insertion purposes, the id is not required, for it is
	auto-incremented in database.
	
```json
{
    "email": "murilo@email.com",
    "cpf": "123123123123",
    "nome": "buyer"
}
```

	2. Registering a new Client POST '(http://localhost:9000/client)'
	
	```json
{
    "name": "amazon"
}
```

	3. Creating a new Payment with CREDIT_CARD option. 'POST (http://localhost:9000/payment)'
	
{
	"amount": "10000.00",
	"paymentMethod":{
		"method": "CREDIT_CARD",
		"creditCard":{
			"brand": "Master",
			"holder":{
				"name": "Bia",
				"birthDate": "28-10-1994",
				"documentNumber": "38129391293"
			},
			"cardNumber": "1111111111111215",
			"expirationDate": "30-04-2018",
			"cvv": "235"
		}
	},
	"buyer":{
		"id": "1"
	},
	"client":{
		"id": "1"
	}
}

	4. Creating a new Payment with BOLETO option: 'POST (http://localhost:9000/payment'
	
{
	"amount": "10000.00",
	"paymentMethod":{
		"method": "BOLETO"
	},
	"buyer":{
		"id": "1"
	},
	"client":{
		"id": "1"
	}
}

	5. Retrieving information of a payment by its id: 'GET (http://localhost:9000/payment/{id}'
	
	6. Retrieving information of a buyer by its id: 'GET (http://localhost:9000/buyer/{id}'
	
	
* The full list of operations and details of each one, are available at 'PostmanDocument' folder.

---
### Contributors

- Murilo M. Santos <murilommms@gmail.com>

---


## Support

* If you have any query or doubt, please, feel free to contact me by e-mail.