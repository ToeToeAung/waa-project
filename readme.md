# CS 545 - Web Application Architecture

This project is an engineering proof of concept aimed at providing hands-on experience in building a full-stack application using Spring and React technologies. The project involves developing a Mini Online Market where different roles (Admin, Seller, and Buyer) interact with the system to manage products, orders, and reviews.

## Project Requirements

| Feature                                                                                                                                                                                                                                                                                   | Score | Done |
| ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- | ---- |
| Admin                                                                                                                                                                                                                                                                                     |       |      |
| a. If the seller registers to the web site, he/she needs to get approval from Admin in order to post products.                                                                                                                                                                            | 2     | ✅   |
| b. The admin can delete reviews that are made from the Buyers. (in case something that violates guidelines is mentioned)                                                                                                                                                                  | 1     | ✅   |
| Seller                                                                                                                                                                                                                                                                                    | --    |      |
| a. Register as Seller                                                                                                                                                                                                                                                                     | 2     | ✅   |
| b. Product (CRUD). If a product has already been purchased (placed an order), it cannot be deleted.                                                                                                                                                                                       | 2     | ✅   |
| c. Seller cannot buy products from the website                                                                                                                                                                                                                                            | 1     | ✅   |
| d. Maintain orders <br/> &nbsp; a. Cancel Order, the status of order on buyer’s part should also changed <br/> &nbsp; b. Change Order status (Shipped-On the way-Delivered)                                                                                                               | 4     | ✅   |
| e. The seller can manage stock quantities, and the system should display "Out of Stock" with a quantity of 0 on the seller's page or when the item is sold out.                                                                                                                           | 2     | ✅   |
| Buyer                                                                                                                                                                                                                                                                                     | --    |      |
| a. Register as Buyer                                                                                                                                                                                                                                                                      | 2     | ✅   |
| b. Cannot sell items on this website                                                                                                                                                                                                                                                      | 1     | ✅   |
| c. Can place an order <br/> &nbsp; a. Maintain Shopping Cart (CRUD)<br/> &nbsp; b. Maintain Shipping and Billing Address <br/> &nbsp; c. Maintain Payment – (create payment assuming a third-party will take care of the payment when info passed correctly) <br/> &nbsp; d. Place order. | 4     | ✅   |
| d. Maintain Orders <br/> &nbsp; a. Check Order History <br/> &nbsp; b. Can cancel order before shipping, after shipping cannot <br/> &nbsp; c. Download/Print receipt as PDF or Excel <br/> &nbsp; d. Write Product Review                                                                | 4     | ✅   |
| General                                                                                                                                                                                                                                                                                   | --    |      |
| Login/Logout                                                                                                                                                                                                                                                                              | 2     | ✅   |
| Security with JWT (Users should not be able to access other pages links) – Auth Server not required, it can be maintained within the project                                                                                                                                              | 3     | ✅   |
| Validation is required for all form submission.                                                                                                                                                                                                                                           | 2     | ✅   |
| Products should be lazy loaded and showing in different pages                                                                                                                                                                                                                             | 2     | ✅   |
| Technical aspects                                                                                                                                                                                                                                                                         | --    |      |
| Neat code and organization                                                                                                                                                                                                                                                                | 2     | 😂   |
| Managed packages, folders, and files                                                                                                                                                                                                                                                      | 2     | ✅   |
| UI                                                                                                                                                                                                                                                                                        | --    |      |
| Buyers can filter the products – examples of filters are mentioned below, select common ones                                                                                                                                                                                              | 2     | ✅   |

## Installation

1. launch postgres database on port 5432 with username "postgres" and pasword "1"
2. create database name "onlinemarketdb"
3. populate data by using data from files in sql directory with following sequent

   - import address.sql
   - import users.sql
   - import category.sql
   - import cart.sql
   - import product.sql
   - import review.sql
   - import cart_item.sql
   - import orders.sql
   - import order_item.sql

#### Test credential from the populated data are as follow

| UserRole | Username | Password  | Status   |
| -------- | -------- | --------- | -------- |
| Admin    | alice    | admin123  | --       |
| Buyer    | john     | buyer123  | --       |
|          | emma     | buyer234  | --       |
|          | liam     | buyer345  | --       |
| Seller   | noah     | seller123 | Approved |
|          | william  | seller345 | Approved |
|          | james    | seller567 | Approved |
|          | olivia   | seller234 | Pending  |
|          | sophia   | seller456 | Pending  |
|          | ava      | seller678 | Pending  |

4. cd to backend directory and run backend on port 8080

```bash
cd backend
mvn spring-boot:run
```

5. cd to frontden directory, install dependencies and run frontend on port 3000

```bash
cd frontend
npm i
npm start
```

# Group member

| Name         | Student ID |
| ------------ | ---------- |
| Hua Zhou     | 618082     |
| Sarun Tapee  | 618056     |
| Toe Toe Aung | 618090     |
