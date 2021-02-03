# M1_devsec_SA

AppDevSec Android Project: https://github.com/Hexliath/M1_devsec_SA

## TODO

- [x] Read password hash from file
- [ ] Read api url from file
- [x] Allow user to change password
- [x] Allow user to lock the application (logout)

## Questions to answer in this README

- Explain how you ensure user is the right one starting the app

The Application needs a "pin" (which is just a simple numeric password) to unlock, the default pin is "0000" but it is made to be a one time usage pin. The user has to change it's pin.

The pin is stored and never kept in cache or RAM and only it's SHA-1 hash is stored on the phone.

- How do you securely save user's data on your phone ?

The password file only stores a hash so the user can't read the real data

The database files can't be read by any non-root users as it is a Room database

- How did you hide the API url ?
- Screenshots of your application
