# Smart Pantry Manager

Smart Pantry Manager is an Android application designed to help users track their kitchen pantry inventory, monitor expiration dates, and get smart recipe suggestions based on available ingredients.

## Features

- **Pantry Inventory Management**:
  - Add, edit, update, and remove pantry items with ingredient names and expiration dates.
  - Real-time search and filtering for quick item lookup.
  - Live cloud synchronization powered by Firebase Cloud Firestore.

- **Smart Recipe Matching**:
  - Intelligent ingredient matching algorithm (`RecipeMatcher`) with normalization for plurals and word variations.
  - Instant recipe match alert banners when all required ingredients for a dish are present in your pantry.
  - Recipe suggestions sorted by match percentage and missing ingredient counts.

- **Recipe Details**:
  - Detailed view displaying full ingredient lists and step-by-step cooking instructions.

- **Automatic Recipe Seeding**:
  - Automatically seeds starter recipes (e.g., Tomato Pasta, Grilled Cheese, Pancakes, Scrambled Eggs, Garden Salad) into Firestore on first launch.

- **Settings**:
  - Shared preference settings for notification and alert preferences.

## Tech Stack & Dependencies

- **Language**: Java
- **Android SDK**: Target/Compile SDK 35, Minimum SDK 21
- **UI Components**: AndroidX AppCompat, Material Components (`1.12.0`), ConstraintLayout, RecyclerView, CardView
- **Backend / Database**: Firebase Cloud Firestore (`firebase-bom:33.7.0`)
- **Build System**: Gradle with Android Gradle Plugin `8.7.3` and Google Services Plugin `4.4.2`

## Setup & Installation

### Prerequisites

- Android Studio 2024.1.1 or newer
- JDK 17
- Android SDK 35
- A Firebase project with Cloud Firestore enabled

### Steps

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd SmartPantryManager
   ```

2. **Firebase Setup**:
   - Register an Android app in the [Firebase Console](https://console.firebase.google.com/) with package name `com.smartpantry.manager`.
   - Download `google-services.json` and place it in the `app/` folder.
   - Enable Cloud Firestore in your Firebase project.

3. **Build & Run**:
   - Open the project in Android Studio.
   - Sync project with Gradle files.
   - Launch on an Android device or emulator running API 21 or higher.
