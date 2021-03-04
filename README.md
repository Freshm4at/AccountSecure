# AccountSecure
Android application. Simulation of a bank account application with multiple security implementations

![Alt text](https://github.com/Freshm4at/AccountSecure/blob/main/Screenshoots/login_page.jpg)

## Introduction
This application is an example of a secure bank application.  It integrate some security implementation as secure TLS communication with an API, SHA256 encrytation and C++ code for secret key and URL API. Moreover, this application must be available offline. Users data can be found online on the API and offline in an assets folder. External datas are encrypted with TLS and Internal datas are encrypted with SHA256.

### Login
To ensure that the user is the right on when the application start. When user strating the application, the first activity is a Login requirement. He enters the id of the wanted account and must click on the login Button. So, I implementate 2 authentification processes to passthrought the Login activity to the MainActivity that display account's informations:

- The user can authenticate with his fingerprint.
- The user can authenticate with a password instead.

The required fingerprint is the one save on the phone. If the user doesn't have digital sensor, doesn't have saved fingerprints or don't want to authenticate with fingerprint, he can enter a secure password. The password is an Base64 encode String. Here the required password is "aGVsbG8=" which can be decode to "hello". This password is stored in an c++ code file in the folder "cpp". Hackers can't use reverse engeneering to obtain this password because c++ cannot be decompiled.
This pasword has been given by the bank to the user.

### Internal data

Application must be available offline. If you try to login when you're offline, application takes internal data to display last know account's informations. So internal data must be secure and encrypted in the case of a hacker decompile the application. data has been stored in a text file for each account and are used when you are offline.
Data has been encrypted with the SHA256 algorithm and saved in the dedicated txt file. The SHA256 requires a master key, generate with a secret key. The secret key is the user password stored in the c++ file. Hackers don't have access to the secret key, so they can't generate master key and decode data.
The application decode data with the generated master key to display offline data.

### External data

Application calls an API to get account's information whan the user is online. The connection with the API must be secure and data can't be transfer clearly on the network. To encrypt online data, i used a TLS connection and only accept the API certification.

### URL

To communicate with the API, the application must know the API Url. So the url must be safely store in the application. To be sure that hackers don't have access to the url, it is store in the c++ file like the secret key. Hackers can't decompile the file and don't have access to the Url clearly. More, the store URL is encode has a Base64 string and it is decode by the application.
