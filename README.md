# AccountSecure
Android application. Simulation of a bank account application with multiple security implementations.

![Alt text](https://github.com/Freshm4at/AccountSecure/blob/main/Screenshoots/login_page.jpg)

## Introduction
This application is an example of a secure bank application.  It integrates some security implementation as secure TLS communication with an API, SHA256 encrytation and C++ code for secret key and URL API. Moreover, this application must be available offline. Users data can be found online on the API and offline in an assets folder. External data are encrypted with TLS and Internal data are encrypted with AES.

## APK
You can find the apk-release in the folder AccountSecure/app/release/app-release.apk

### Login
To ensure that the user is right when the application starts. When a user starts the application, the first activity is a login requirement. He enters the id of the wanted account and must click the login button. So, I implement 2 authentification processes to bring the Login activity to the MainActivity that displays the account's information:

- The user can authenticate with his fingerprint.
- The user can authenticate with a password instead.

The required fingerprint is the one saved on the phone. If the user doesn't have a digital sensor, doesn't have saved fingerprints or doesn't want to authenticate with a fingerprint, he or she can enter a secure password. The password is a Base64 encode String. Here the required password is "aGVsbG8=" which can be decoded to "hello". This password is stored in a c++ code file in the folder "cpp". Hackers can't use reverse engeneering to obtain this password because c++ cannot be decompiled.
This password has been given by the bank to the user.

![Alt text](https://github.com/Freshm4at/AccountSecure/blob/main/Screenshoots/fingerprint.jpg)
![Alt text](https://github.com/Freshm4at/AccountSecure/blob/main/Screenshoots/invalid%20fingerprint.jpg)
![Alt text](https://github.com/Freshm4at/AccountSecure/blob/main/Screenshoots/Wrong%20password.jpg)

### Internal data

The application must be available offline. If you try to login when you're offline, the application takes internal data to display the last-known account's information. So internal data must be secure and encrypted in the case of a hacker decompiling the application. Data has been stored in a text file for each account and are used when you are offline.
Data has been encrypted with the AES algorithm and saved in the dedicated txt file. The AES requires a master key build in SHA256 and encode in AES, generated with a secret key. The secret key is the user password stored in the c++ file. Hackers don't have access to the secret key, so they can't generate the master key and decode data.
The application decode data with the generated master key to display offline data.

![Alt text](https://github.com/Freshm4at/AccountSecure/blob/main/Screenshoots/Offline%20information.jpg)

### External data

The application calls an API to get the account's information when the user is online. The connection with the API must be secure and data can't be transferred clearly on the network. To encrypt online data, I used a TLS connection and only accepted the API certification.

![Alt text](https://github.com/Freshm4at/AccountSecure/blob/main/Screenshoots/Online%20information.jpg)

### URL

To communicate with the API, the application must know the API Url. So the url must be safely stored in the application. To be sure that hackers don't have access to the url, it is stored in the c++ file like the secret key. Hackers can't decompile the file and don't have access to the Url clearly. Moreover, the store URL is encoded with a Base64 string and it is decode by the application.
