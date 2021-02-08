# Jaya-cc [Currency Conversion]

This project aims is to exposes an API that does the conversion between two different currencies.
To do this, we use the Exchange rates API built by the European Central Bank and hosted at https://api.exchangeratesapi.io.

The project has the following endpoints

- POST https://jaya-currency-conversion.herokuapp.com/api/signup
- POST https://jaya-currency-conversion.herokuapp.com/api/convert
- GET https://jaya-currency-conversion.herokuapp.com/api/transactions-by-user

**HOW TO USE**

The signup endpoint should be used to register an new user.

- Path: /api/signup
- HTTP Method: POST
- Accepts: application/json
- Parameters:
  - username (string, required)
  - password (string, required)

Below there is a body data request example.

![](images/signup-json.png)

A succeful call to this end point produces the following output:

![](images/signup-success.png)

When something is missing (username or password), the output will be like this:

![](images/signup-error.png)

The convert endpoint do the conversion between two provided currencies.

- Path: /api/convert
- HTTP Method: POST
- Accepts: application/json
- Parameters:
  - userID (int, required)
  - currencyFrom (string, required)
  - amount (int, required)
  - currencyTo (string, required)

To make a conversion the client must send the _userID_, the original currency providing the
_currencyFrom_ field, the _amount_ to be converted and the final currency provided
in the _currencyTo_ field. All the fields are required.

Below are some example of succeful and fail requests.

Succeful conversion from 53 BRL to USD

Request:

![](images/succeful-brl-to-usd-conversion-req.png)

Response:

![](images/succeful-brl-to-usd-conversion-res.png)
