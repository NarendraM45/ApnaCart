# ApnaCart 🛒

**Shop Fast, Shop Smart.**

ApnaCart is a production-grade, portfolio-ready e-commerce Android application built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**, powered by a **FastAPI** and **Supabase** backend. 

## 🌟 Features

- **100% Shared UI:** Built entirely with Compose Multiplatform in the `commonMain` module.
- **Clean Architecture:** Strict separation of concerns using the MVVM pattern, Repositories, and UseCases.
- **Dependency Injection:** Powered by Koin 4.0.0.
- **Reactive UI:** Built on Coroutines and StateFlow for robust state management.
- **Local Caching:** Powered by SQLDelight for offline capabilities.
- **Dynamic Animations:** Lottie and Media3 ExoPlayer for splash screens.
- **Secure Backend:** FastAPI server running against a Supabase PostgreSQL database with Row Level Security (RLS).
- **Beautiful Design:** Custom UI components, brand colors, and integrated Google Fonts (Poppins & Inter).

## 🏗️ Architecture

### Android App Module (`androidApp/`)
Contains only the platform-specific entry points:
- `MainActivity.kt`
- `ApnaCartApplication.kt` (Koin Init & AndroidSqliteDriver)
- Platform-specific resources (e.g., App Icons, Splash Video)

### Shared Module (`shared/`)
Contains all business logic and UI:
- **Presentation:** Screens, ViewModels, Theme, UI Components, Navigation.
- **Domain:** Models, Repository Interfaces, UseCases.
- **Data:** DTOs, SupabaseClient, SQLDelight DB, Repository Implementations.

### Backend (`backend/`)
- **FastAPI:** A high-performance Python backend routing API calls.
- **Supabase:** PostgreSQL database with real-time capabilities and built-in auth.

## 🚀 Getting Started

### Prerequisites
1. **Android Studio** (Koala or newer)
2. **JDK 17** (or 11+)
3. **Python 3.10+**

### Backend Setup
1. Navigate to the `backend/` directory.
2. Copy `.env.example` to `.env` and fill in your Supabase credentials:
   ```bash
   cp .env.example .env
   ```
3. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```
4. Run the seed script to populate Supabase (ensure RLS policies are set up using `supabase_schema.sql`):
   ```bash
   python seed_data.py
   ```
5. Start the FastAPI server:
   ```bash
   uvicorn app.main:app --reload
   ```

### Android App Setup
1. Open the project in Android Studio.
2. Wait for Gradle Sync to complete.
3. Select the `androidApp` configuration and hit **Run**.

## 🎨 Design System

- **Primary Colors:** Saffron Orange (`#xFFFF6B35`), Deep Navy (`#xFF1B2B4B`)
- **Typography:** Poppins (Headings) and Inter (Body)
- **Theme:** Dynamic Light and Dark modes supported.

## 📄 License
This project is open-source and available under the MIT License.
