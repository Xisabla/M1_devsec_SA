# M1_devsec_SA

AppDevSec Android Project: https://github.com/Hexliath/M1_devsec_SA

## TODO

- [ ] Read password hash from file
- [ ] Read api url from file
- [ ] Allow user to change password
- [x] Allow user to lock the application (logout)
- [ ] (Optional but better) Cypher the stored data
    - [ ] Password and URL files
    - [ ] Accounts database

## Questions to answer in this README

- Explain how you ensure user is the right one starting the app
- How do you securely save user's data on your phone ?
- How did you hide the API url ?
<br /><span style="color:blue;">The main idea behind our implementation was to store the API URL in a .so file, way harder to decipher than a classic Kotlin class from decompilation. In order to do so, we store the URL in a C++ class and we fetch it by calling a function from our Main Activity.</span>
- Screenshots of your application
