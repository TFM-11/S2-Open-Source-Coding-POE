# S2 Open Source Coding POE
Final Semester 2 Open Source Coding


Anytime Car Wash Application
Project Title: Anytime Car Wash

Project Description
Anytime Car Wash is a modern, fully functional Android application designed to provide 
users with a seamless experience for booking car wash services. The application features a 
robust user authentication system, a comprehensive dashboard for service selection and 
booking management, and a personalized settings page. It is built using Kotlin for the 
Android frontend and Flask for the backend API, adhering to best practices in mobile and 
web development.
 
Features
User Authentication
 • Splash Screen: A branded startup screen that leads to authentication or the main app.
 • Sign-up: Allows new users to register with their full name, email, phone number, and a 
secure password.
 • Login: Secure login for registered users with email and password.
 • Session Management: Persistent user sessions using 
SharedPreferences .
 • Input Validation: Real-time validation for all authentication fields to ensure data 
integrity.

 Main Application Flow
 • Home Dashboard: Displays a personalized greeting, a grid of available car wash 
services, and a preview of upcoming bookings.
 • Service Selection: Users can browse and select various car wash services with detailed 
descriptions and pricing.
 • Booking Creation: A dedicated booking interface allows users to select vehicle type, 
preferred date and time, and an optional location for their car wash service.
 • Bookings Management: A tabbed interface to view Upcoming and Past bookings, with 
options to cancel upcoming services.

 User Profile
• Profile Dashboard: Displays user's personal information (name, email, phone number). • Account Settings: Options to navigate to Payment Methods, Edit Profile, Change Password and My Vehicles.
• Edit Profile: Allows users to update their personal details and profile picture.
• Change Password: Securely update account password.
• My Vehicles: Manage a list of user's registered vehicles.
• Payment Methods: Manage saved payment methods.

 Settings and Personalization
 • Theme Selection: Users can switch between Light and Dark themes, with preferences saved persistently.
 • Language Selection: Support for English, Afrikaans, and Sepedi, allowing users to choose their preferred language for key features.
 • Notifications: Toggle to enable/disable app notifications, with sub-options for different notification types.
 • About Section: Provides information about the application, including privacy policy and about us details.
 • Logout: Securely logs out the user from the application. 
 
 Technical Stack 
 Frontend
 • Platform: Android
 • Language: Kotlin
 • IDE: Android Studio (Meerkat)
 • Architecture: MVVM (Model-View-ViewModel)
 • UI Toolkit: Material Design 3
 • Networking: Retrofit, OkHttp
 • Asynchronous Operations: Kotlin Coroutines
 • Data Persistence: SharedPreferences
 • Localization: Multi-language support (English, Afrikaans, Sepedi)

Backend
 • Framework: Flask (Python)
 • Database: SQLite (development database)
 • Authentication: JWT-based with password hashing
 • API Style: RESTful
 • CORS: Enabled for cross-origin requests
 
 Setup and Installation
 1. Backend Setup
 1. Clone the repository:
 2. Create and activate a virtual environment:
 3. Install dependencies:
 4. Run the Flask application:

  
 2. Android Frontend Setup
 1. Open in Android Studio:
 • Open Android Studio.
 • Select Open an existing Android Studio project .
 • Navigate to the AnytimeCarWash directory and open it.
 
 2. Sync Gradle:
 • Android Studio will automatically sync Gradle. If not, click Files .
 
 3. Run the Application:
 • Select an emulator or a physical device.
 • Click the Run button (green triangle) in Android Studio.
 
 Usage
 1. Splash Screen: The app starts with a splash screen.
 2. Authentication: Sign up for a new account or log in with existing credentials.
 3. Main Dashboard: Browse car wash services and view recent bookings.
 4. Book a Service: Select a service, choose vehicle type, date, and time to book.
 5. Bookings: View upcoming and past bookings.
 6. Profile: Manage personal information, vehicles, and payment methods.
 7. Settings: Customize app theme (Light/Dark) and language (English, Afrikaans, Sepedi).
