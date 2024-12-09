# Herbamates

Herbamates is a platform designed to help users discover suitable herbs and access detailed information about their benefits and uses. It simplifies the process of exploring herbal remedies for personal health and wellness.


## Installing the Kotlin Android Project

1. **System Requirements:**
    - Ensure you have installed Android Studio.
    - Make sure the latest Android SDK is downloaded via Android Studio.
    - Ensure Kotlin/Native software is installed.

2. **Installation Steps:**

    - Step 1: Clone the repository from GitHub.
      ```
      git clone https://github.com/HerbaMate-Bangkit-Capstone/HerbaMate---MD.git
      ```

    - Step 2: Open the project using Android Studio.
        - Open Android Studio.
        - Select 'Open an existing Android Studio project'.
        - Navigate to the directory of the downloaded project.

    - Step 3: Synchronize and install dependencies.
        - Wait for Android Studio to complete project synchronization.
        - Ensure all dependencies are installed by running `Sync Project with Gradle Files`.

    - Step 4: Ready for development!
        - The project is now ready to be compiled and run on your device or emulator.

## Folder Structure Division

The project's folder architecture is using MVVM Pattern with several design pattern such as Dependency Injection Pattern, Factory Pattern, and Repository Pattern, which divided into distinct sections to separate their respective roles:

1. **data:**
    - Location to access data sources such as local storage, network, or other data providers.
    - In this data section will be store the response object from the API
    - This folder will be devide by 2 kind of folder which is `local` to store the class that connected to the local environment such as room and `remote` to store the class that connected to the network environment

2. **di:**
    - Location to store the injection object while using dependency injection.

3. **model:**
    - Location to store data actual model that will be show in the views.

4. **repository:**
    - Location to all repository class that been used to combine all the related data from `data`

5. **utils:**
    - Location to store all the utility class that been use to help the development phases such as `ViewModelFactory.kt`, `AppExecutors.kt`, and  `Result.kt`

6. **view:**
    - Location to store all the interfaces activity codes 
    - This folder also used to store their related file such as `ViewModel` and `Adapter`


Ensure compliance with this folder structure to facilitate development, maintenance, and team collaboration.

## Tech Stack
This project is using several technology such as

1. **Retrofit:** Library that used to fetch the data from the api

2. **Room:** Library that used to store data to local database storage

3. **Glide:** Library that used to show the network image


## License
This project is licensed under the Bangkit Academy 2024 batch 2