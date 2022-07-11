# Nagarro Assignment

** To run it locally, please correct the path of Ms access DB file in application.properties files


1) To authenticate and get JWT token 

http://localhost:8080/nagarro/authenticate

Method : POST

Body :

{
    "username":"user",
    "password":"user"
}

OR 

{
    "username":"admin",
    "password":"admin"
}


2) To get the account statement 

http://localhost:8080/nagarro/v1/account/statement

Method : GET 

Body :

{
    "accountId":"1",  
    "toAmount":300,
    "fromAmount":100,
    "fromDate":"2020-10-14",
    "toDate":"2020-10-14"
}

accountId is mandatory and rest all parameters are optional.



