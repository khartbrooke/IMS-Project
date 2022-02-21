Coverage: 81%
# IMS Project

This program is an inventory management system that could be used by any shop/business. Its main features include CRUD functionality for managing customers, products and orders as well as a function to calculate the cost of a particular order.

### Prerequisites

Please ensure that you have the following installed before attempting to use the program.

```
Java
An IDE for Java, preferably Eclipse.
MySQL Workbench 8.0

```

### Installing

```
1) Install the folder IMS-Project to your eclipse-workspace(or other preferred location)
```
```
2) Open IMS-Project\src\main\resources\db.properties and change db.user and db.password to your username and password for MySQL if necessary.
```
```
3) Open IMS-Project\src\main\resources\sql-schema.sql and run it to initialise the data tables.
```
```
4) (optional) Open IMS-Project\src\main\resources\sql-data.sql and run it to insert some dummy data to the tables.
```
```
5) Run IMS-Project\src\main\java\com\qa\ims\Runner.java to start the program and follow the instructions on the console to use it.
```

## Running the tests

The unit tests can be found in IMS-Project\src\test\java\com\qa\ims. In order to execute the tests, open the respective test file and run it. 

### Unit Tests 

There are three test files each for customers, items and orders:

```
One of them tests the object itelf and these can be found in IMS-Project\src\test\java\com\qa\ims\persistence\domain
```
```
One of them tests the ability to connect to the database and perform operations on it. 
These are found in IMS-Project\src\test\java\com\qa\ims\persistence\dao
```
```
The other ones test the front end of the system and ensure that the user imput performs to correct function. 
These are found in IMS-Project\src\test\java\com\qa\ims\controllers
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **Kieran Hart-Brooke** - *Project* - [khartbrooke](https://github.com/khartbrooke)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* Morgan Walsh & Aswene Sivaraj