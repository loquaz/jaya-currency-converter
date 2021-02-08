# Jaya-cc [Currency Conversion]

This project aims is to exposes an API that does the conversion between two different currencies.
To do this, we use the Exchange rates API built by the European Central Bank and hosted at https://api.exchangeratesapi.io.

The project has the following endpoints

- POST https://jaya-currency-conversion.herokuapp.com/api/signup
- POST https://jaya-currency-conversion.herokuapp.com/api/convert
- GET https://jaya-currency-conversion.herokuapp.com/api/transactions-by-user

**HOW TO USE**

The signup endpoint should be used to register an new user.

- HTTP Method: POST
- Accepts: json
- Parameters:
  - username (string, required)
  - password (string, required)

Below there is an request example
[images/signup-json.png]

A succeful call will present the folloing output
[images/signup-success.png]
