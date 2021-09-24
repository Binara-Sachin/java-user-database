# java-user-database

Tasks Given
  1. Write a simple Java command line application.
  2. No UIs required.
  3. Use Maven or Gradle as a build tool.
  4. This would be a simple user storage with - registration, login and list users features.
  5. Plug a relation database as the storage.
  6. Once run users will be prompted to choose an action from - register user, login. list all users
  7. If the user selects registration, then you can take some user details like name, password, email address and store the new user into the database.
  8. If they go with the login option, then you can prompt them for email and password. (No need for maintaining sessions)
  9. If it's a iser list action, check if a login has been succeeded previously and list all users.
  10. You can stick to a simple standalone command line application, no need for webapps.

Functions Breakdown
  1. Create Maven Project <-- DONE
  2. Connect to MySQL Database <-- DONE
  3. Access "User-data" table <-- DONE
  4. Add Functions to Register, Login and View Users <-- DONE
  5. Setup Loop
  6. Handle Exceptions
