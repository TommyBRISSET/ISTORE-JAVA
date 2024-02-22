# JAVA-ISTORE

## Overview
iStore Ltd aims to provide the best experience for companies managing goods. This Java application allows users to access information about stored goods, operate the inventory, and manage user accounts.

## Features

### Basic Authentication
- Users can create accounts with email and password.
- Login with email and password.
- Whitelisting required for account creation.
- Passwords are securely stored (hashed).
- Clear error messages for login and account creation issues.

### User Management
- CRUD operations for users.
- User attributes: id, email, pseudo, password, role.
- Normal users can read information about others (except password).
- Admin functionalities: whitelisting emails, update/delete user, create new store, create/delete item.

### Inventory Management
- Link inventory to a store.
- Items with id, name, price, limited stock.
- Browse inventory.
- Increase/decrease item stock for employees.

### Store Management
- Admin-only creation/deletion of stores.
- Store attributes: id, name.
- Each store has one inventory.
- Admin can add employees to a store.
- Display list of people with access to the store.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) installed.
- Swing library available.

### Installation
1. Dowload the repository (zip).
2. Extract the files.
3. Now, you have to configure the database connection (We use XAMP to made this, but you can choose any other database manager):
    - Go to the `BDD` folder and import the `istore.sql` and `istore_test.sql` file in your database manager.
    - Change the `url`, `username` and `password` variables to match your database or update the variables in the `src/utils/connexionBDD` file.
    - Start your database manager.
4. You have two ways to run the application :
    - Open the project with an IDE (e.g. IntelliJ IDEA).
      - go to the `src` folder then `principal` and run the `Main` class.
    - Or, open an file explorer and go to the `out` folder then `artifacts` and double-click on the `iStore.jar` file.
5. Felicitation, you can now use the application !

## Bonus 

You can find a folder named `setup` in the repository. This folder contains a `setupIstore.exe` file that will install the application on your computer as a Windows application.
Once installed, don't forget to configure the database connection as explained in the installation section.
Be careful, this way of installation is not 100% functional and can cause some issues.

## Contributor
- `Tommy Brisset`


## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
