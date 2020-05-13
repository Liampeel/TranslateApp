# Android Translate Application


## About

This application uses a Django API to validate and handle queries made by the user. Once a user registers an account and logs in they will be issued a unique session
token which communicates with the server to allow users to post data on their specific account.

Once data has been posted a user can view a graph of the languages that were posted and their occurence from the home page of the app. 


## Build

The django API is hosted onto pythonanywhere.com so that the app can be accessible without having to run the server on the computer via localhost.

To build the application git clone this repository and ensure that the local.properties folder contains the correct path to your local install of Kotlin.
Run the project in Android Studio and it should build, it may requre that the app is cleaned and rebuilt before it runs


If there are still errors then be sure to click `file -> Invalidate Caches / Restart... -> Invalidate and Restart`

## Wiki

Check the wiki option on the side view of the project overview for information on each page.