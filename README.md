# Permission Manager

An Android application that provides a user-friendly interface for managing app permissions.

# Features Implemented
1. Permission Management interface : Clean UI for managing Camera, SMS, and Location.
2. Real-time Permission status : Toggle indicators showing granted/denied status with color-coded icons.
3. Tabbed Navigation : ViewPager2 based navigation between Profile, Permission and Email sections.
4. Scroll view layout ensuring compatiability across different screen sizes.

# Permission Handling
1. Camera Permission: For photo/video capture functionality.
2. SMS Permission: For sending text messages.
3. Location Permission: For accessing device location.

# Libraries/Packages Used

  1. Core Android Dependencies

androidx.core:core-ktx (1.17.0) - Kotlin extensions for Android core APIs
androidx.appcompat:appcompat (1.7.1) - Backwards compatibility support
com.google.android.material:material (1.13.0) - Material Design components
androidx.constraintlayout:constraintlayout (2.2.1) - Advanced layout management
androidx.activity:activity (1.10.1) - Activity components and extensions

  2. Navigation & Architecture

androidx.navigation:navigation-fragment-ktx (2.7.6) - Navigation component for fragments
androidx.navigation:navigation-ui-ktx (2.7.6) - Navigation UI utilities
androidx.lifecycle:lifecycle-viewmodel-ktx (2.7.0) - ViewModel architecture component
androidx.lifecycle:lifecycle-livedata-ktx (2.7.0) - LiveData architecture component
androidx.fragment:fragment-ktx (1.6.2) - Fragment extensions and utilities
androidx.activity:activity-ktx (1.8.2) - Activity extensions

# Known Limitations
1. Users cannot revoke permissions directly from the app - use system settings.
2. Basic error handling for settings
