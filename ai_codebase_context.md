# Codebase Architecture

## File Structure
- build.gradle.kts
- export_code.py
- settings.gradle.kts
- androidApp/build.gradle.kts
- androidApp/src/main/AndroidManifest.xml
- androidApp/src/main/kotlin/com/apnacart/android/ApnaCartApplication.kt
- androidApp/src/main/kotlin/com/apnacart/android/MainActivity.kt
- androidApp/src/main/res/values/colors.xml
- androidApp/src/main/res/values/themes.xml
- androidApp/src/main/res/xml/network_security_config.xml
- backend/seed_data.py
- backend/app/main.py
- backend/app/core/config.py
- backend/app/routers/products.py
- shared/build.gradle.kts
- shared/src/commonMain/kotlin/com/apnacart/App.kt
- shared/src/commonMain/kotlin/com/apnacart/data/remote/api/SupabaseClient.kt
- shared/src/commonMain/kotlin/com/apnacart/data/remote/dto/BannerDto.kt
- shared/src/commonMain/kotlin/com/apnacart/data/remote/dto/CategoryDto.kt
- shared/src/commonMain/kotlin/com/apnacart/data/remote/dto/ProductDto.kt
- shared/src/commonMain/kotlin/com/apnacart/data/remote/dto/UserDto.kt
- shared/src/commonMain/kotlin/com/apnacart/data/repository/AuthRepositoryImpl.kt
- shared/src/commonMain/kotlin/com/apnacart/data/repository/CartRepositoryImpl.kt
- shared/src/commonMain/kotlin/com/apnacart/data/repository/OrderRepositoryImpl.kt
- shared/src/commonMain/kotlin/com/apnacart/data/repository/PincodeRepositoryImpl.kt
- shared/src/commonMain/kotlin/com/apnacart/data/repository/ProductRepositoryImpl.kt
- shared/src/commonMain/kotlin/com/apnacart/data/repository/WishlistRepositoryImpl.kt
- shared/src/commonMain/kotlin/com/apnacart/di/AppModule.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/model/Address.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/model/Banner.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/model/CartItem.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/model/Category.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/model/Order.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/model/PincodeInfo.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/model/Product.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/model/Review.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/model/User.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/repository/IAuthRepository.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/repository/ICartRepository.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/repository/IOrderRepository.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/repository/IPincodeRepository.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/repository/IProductRepository.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/repository/IWishlistRepository.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/usecase/AddToCartUseCase.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/usecase/CheckPincodeUseCase.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/usecase/GetOrdersUseCase.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/usecase/GetProductDetailUseCase.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/usecase/GetProductsUseCase.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/usecase/LoginUseCase.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/usecase/SearchProductsUseCase.kt
- shared/src/commonMain/kotlin/com/apnacart/domain/usecase/ToggleWishlistUseCase.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/components/CustomTextField.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/components/PrimaryButton.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/components/ProductCard.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/components/TopBar.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/navigation/AppNavigation.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/navigation/Screen.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/screens/auth/LoginScreen.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/screens/auth/LoginViewModel.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/screens/cart/CartScreen.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/screens/cart/CartViewModel.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/screens/home/HomeScreen.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/screens/home/HomeViewModel.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/screens/product_detail/ProductDetailScreen.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/screens/product_detail/ProductDetailViewModel.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/theme/Color.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/theme/Shape.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/theme/Theme.kt
- shared/src/commonMain/kotlin/com/apnacart/presentation/theme/Typography.kt
- shared/src/commonMain/kotlin/com/apnacart/util/Constants.kt
- shared/src/commonMain/kotlin/com/apnacart/util/Extensions.kt
- shared/src/commonMain/kotlin/com/apnacart/util/Resource.kt
- shared/src/commonMain/kotlin/com/apnacart/util/UiState.kt
- shared/src/commonMain/sqldelight/com/apnacart/data/local/db/ApnaCartDatabase.sq

## File Contents

### build.gradle.kts
```kotlin
plugins {
    // This is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.sqldelight) apply false
}
```

### export_code.py
```python
import os
import re

def minify_content(text):
    # Remove consecutive empty lines to save tokens
    text = re.sub(r'\n\s*\n', '\n\n', text)
    # Strip leading/trailing whitespaces from each line (optional, but saves tokens, though it might ruin python formatting)
    # Python relies on indentation, so we CANNOT strip leading spaces for Python, but we can for others or just rely on removing blank lines.
    return text.strip()

def export_codebase(dir_path, exclude_dirs, allowed_exts):
    output_lines = ["# Codebase Architecture\n"]
    file_entries = []

    for root, dirs, files in os.walk(dir_path):
        dirs[:] = [d for d in dirs if not d.startswith('.') and d not in exclude_dirs]
        for f in files:
            if any(f.endswith(ext) for ext in allowed_exts):
                file_entries.append(os.path.join(root, f))

    # 1. Output minimal file list instead of ASCII tree (saves indentation tokens)
    output_lines.append("## File Structure")
    for f in file_entries:
        rel_path = os.path.relpath(f, dir_path).replace("\\", "/")
        output_lines.append(f"- {rel_path}")

    output_lines.append("\n## File Contents")

    # 2. Output file contents with markdown code blocks
    for f in file_entries:
        rel_path = os.path.relpath(f, dir_path).replace("\\", "/")
        ext = rel_path.split('.')[-1] if '.' in rel_path else ""

        # Map extension to markdown language
        lang_map = {"kt": "kotlin", "kts": "kotlin", "py": "python", "sq": "sql"}
        lang = lang_map.get(ext, ext)

        try:
            with open(f, 'r', encoding='utf-8') as file:
                content = minify_content(file.read())
                output_lines.append(f"\n### {rel_path}\n```{lang}\n{content}\n```")
        except Exception:
            pass

    return "\n".join(output_lines)

if __name__ == "__main__":
    root_dir = os.path.dirname(os.path.abspath(__file__))
    output_file = os.path.join(root_dir, "ai_codebase_context.md")

    exclude_dirs = {"build", ".gradle", ".idea", "venv", "__pycache__", "node_modules", "iosApp", "composeResources", "gradle"}
    allowed_exts = {".kt", ".kts", ".py", ".xml", ".sq", ".toml"}

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(export_codebase(root_dir, exclude_dirs, allowed_exts))

    print(f"✅ Token-optimized markdown exported to: {output_file}")
```

### settings.gradle.kts
```kotlin
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ApnaCart"
include(":androidApp")
include(":shared")
```

### androidApp/build.gradle.kts
```kotlin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    sourceSets {
        androidMain.dependencies {
            implementation(project(":shared"))

            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.sqldelight.android)

            // Android specific
            implementation(libs.datastore.preferences)
            implementation(libs.security.crypto)
            implementation(libs.biometric)
            implementation(libs.splashscreen)
            implementation(libs.paging.compose)
            implementation(libs.lottie)

            // Media3 ExoPlayer for Splash Animation
            implementation(libs.media3.exoplayer)
            implementation(libs.media3.ui)
            implementation(libs.media3.common)
        }
    }
}

android {
    namespace = "com.apnacart.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.apnacart.android"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets {
        getByName("main") {
            manifest.srcFile("src/main/AndroidManifest.xml")
            res.srcDirs("src/main/res")
            java.srcDirs("src/main/kotlin")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
```

### androidApp/src/main/AndroidManifest.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />

    <application
        android:name=".ApnaCartApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="ApnaCart"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@android:style/Theme.NoTitleBar"
        android:networkSecurityConfig="@xml/network_security_config">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.ApnaCart.Splash">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

### androidApp/src/main/kotlin/com/apnacart/android/ApnaCartApplication.kt
```kotlin
package com.apnacart.android

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.apnacart.data.local.db.ApnaCartDatabase
import com.apnacart.di.getSharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ApnaCartApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val androidModule = module {
            single<SqlDriver> { AndroidSqliteDriver(ApnaCartDatabase.Schema, get(), "apnacart.db") }
        }

        startKoin {
            androidContext(this@ApnaCartApplication)
            modules(androidModule, getSharedModule())
        }
    }
}
```

### androidApp/src/main/kotlin/com/apnacart/android/MainActivity.kt
```kotlin
package com.apnacart.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.apnacart.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showSplash by remember { mutableStateOf(true) }

            if (showSplash) {
                VideoSplashScreen(
                    onVideoEnded = { showSplash = false }
                )
            } else {
                App()
            }
        }
    }
}

@Composable
fun VideoSplashScreen(onVideoEnded: () -> Unit) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/${R.raw.start_animation}")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        onVideoEnded()
                    }
                }
            })
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
```

### androidApp/src/main/res/values/colors.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="background_dark">#0F0F17</color>
</resources>
```

### androidApp/src/main/res/values/themes.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.ApnaCart.Splash" parent="Theme.SplashScreen">
        <item name="windowSplashScreenBackground">@color/background_dark</item>
        <item name="windowSplashScreenAnimatedIcon">@mipmap/ic_launcher</item>
        <item name="postSplashScreenTheme">@style/Theme.ApnaCart</item>
    </style>

    <style name="Theme.ApnaCart" parent="android:Theme.Material.Light.NoActionBar">
        <item name="android:statusBarColor">@android:color/transparent</item>
        <item name="android:windowLightStatusBar">true</item>
    </style>
</resources>
```

### androidApp/src/main/res/xml/network_security_config.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">supabase.co</domain>
    </domain-config>
</network-security-config>
```

### backend/seed_data.py
```python
import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()

SUPABASE_URL = os.getenv("SUPABASE_URL")
SUPABASE_KEY = os.getenv("SUPABASE_SERVICE_KEY")

if not SUPABASE_URL or not SUPABASE_KEY:
    raise ValueError("Missing Supabase credentials")

supabase: Client = create_client(SUPABASE_URL, SUPABASE_KEY)

categories = [
    {"name": "Electronics", "slug": "electronics", "icon_url": "https://example.com/electronics.png"},
    {"name": "Fashion", "slug": "fashion", "icon_url": "https://example.com/fashion.png"},
]

products = [
    {
        "name": "Sony WH-1000XM4",
        "description": "Industry leading noise canceling headphones.",
        "brand": "Sony",
        "price": 24999.00,
        "original_price": 29990.00,
        "discount_percent": 16,
        "rating": 4.8,
        "review_count": 12500,
        "category_slug": "electronics",
        "images": ["https://m.media-amazon.com/images/I/71o8Q5XJS5L._SL1500_.jpg"]
    },
    {
        "name": "Men's Classic T-Shirt",
        "description": "100% cotton premium t-shirt.",
        "brand": "Polo",
        "price": 899.00,
        "original_price": 1499.00,
        "discount_percent": 40,
        "rating": 4.2,
        "review_count": 850,
        "category_slug": "fashion",
        "images": ["https://m.media-amazon.com/images/I/61NlBxtbA4L._UX679_.jpg"]
    }
]

banners = [
    {
        "title": "Summer Sale",
        "subtitle": "Up to 50% Off on Electronics",
        "image_url": "https://example.com/summer-sale.jpg"
    }
]

print("Seeding categories...")
for c in categories:
    try:
        supabase.table("categories").insert(c).execute()
    except Exception as e:
        print(f"Error inserting category: {e}")

print("Seeding products...")
for p in products:
    try:
        supabase.table("products").insert(p).execute()
    except Exception as e:
        print(f"Error inserting product: {e}")

print("Seeding banners...")
for b in banners:
    try:
        supabase.table("banners").insert(b).execute()
    except Exception as e:
        print(f"Error inserting banner: {e}")

print("Seeding complete!")
```

### backend/app/main.py
```python
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.routers import products

app = FastAPI(
    title="ApnaCart Backend API",
    description="Backend services for ApnaCart E-commerce app",
    version="1.0.0"
)

# Enable CORS for local testing if needed
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(products.router, prefix="/api/v1/products", tags=["Products"])

@app.get("/")
def root():
    return {"message": "Welcome to ApnaCart API. API is running."}
```

### backend/app/core/config.py
```python
import os
from dotenv import load_dotenv
from supabase import create_client, Client

# Load environment variables from .env file
load_dotenv()

SUPABASE_URL = os.getenv("SUPABASE_URL")
SUPABASE_KEY = os.getenv("SUPABASE_SERVICE_ROLE_KEY") or os.getenv("SUPABASE_KEY")

if not SUPABASE_URL or not SUPABASE_KEY:
    raise ValueError("Missing Supabase credentials. Please set SUPABASE_URL and SUPABASE_SERVICE_ROLE_KEY (or SUPABASE_KEY) environment variables.")

supabase: Client = create_client(SUPABASE_URL, SUPABASE_KEY)
```

### backend/app/routers/products.py
```python
from fastapi import APIRouter, HTTPException, Query
from app.core.config import supabase
from typing import Optional

router = APIRouter()

@router.get("/")
def get_products(
    category: Optional[str] = None,
    min_price: Optional[float] = None,
    max_price: Optional[float] = None,
    min_rating: Optional[float] = None
):
    query = supabase.table("products").select("*")

    if category:
        query = query.eq("category_slug", category)
    if min_price:
        query = query.gte("price", min_price)
    if max_price:
        query = query.lte("price", max_price)
    if min_rating:
        query = query.gte("rating", min_rating)

    try:
        response = query.execute()
        return response.data
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@router.get("/{product_id}")
def get_product(product_id: str):
    try:
        response = supabase.table("products").select("*").eq("id", product_id).single().execute()
        return response.data
    except Exception as e:
        raise HTTPException(status_code=404, detail="Product not found")
```

### shared/build.gradle.kts
```kotlin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Navigation
            implementation(libs.navigation.compose)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.neg)
            implementation(libs.ktor.serialization.json)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.logging)

            // Supabase
            implementation(libs.supabase.postgrest)
            implementation(libs.supabase.auth)
            implementation(libs.supabase.storage)
            implementation(libs.supabase.realtime)

            // Kotlinx
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)
            implementation(libs.kotlinx.datetime)

            // Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.network.okhttp)

            // SQLDelight
            implementation(libs.sqldelight.coroutines)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqldelight.android)
            implementation(libs.kotlinx.coroutines.android)
        }
    }
}

android {
    namespace = "com.apnacart.shared"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sqldelight {
    databases {
        create("ApnaCartDatabase") {
            packageName.set("com.apnacart.data.local.db")
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/App.kt
```kotlin
package com.apnacart

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.apnacart.presentation.navigation.AppNavigation
import com.apnacart.presentation.theme.ApnaCartTheme

@Composable
fun App() {
    ApnaCartTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavigation()
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/remote/api/SupabaseClient.kt
```kotlin
package com.apnacart.data.remote.api

import com.apnacart.util.Constants
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

val supabaseClient = createSupabaseClient(
    supabaseUrl = Constants.SUPABASE_URL,
    supabaseKey = Constants.SUPABASE_ANON_KEY
) {
    install(Auth)
    install(Postgrest)
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/remote/dto/BannerDto.kt
```kotlin
package com.apnacart.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.apnacart.domain.model.Banner

@Serializable
data class BannerDto(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String? = null,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("action_url") val actionUrl: String? = null
) {
    fun toDomain(): Banner {
        return Banner(
            id = id,
            title = title,
            subtitle = subtitle,
            imageUrl = imageUrl,
            actionUrl = actionUrl
        )
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/remote/dto/CategoryDto.kt
```kotlin
package com.apnacart.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.apnacart.domain.model.Category

@Serializable
data class CategoryDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("icon_url") val iconUrl: String? = null,
    @SerialName("banner_url") val bannerUrl: String? = null
) {
    fun toDomain(): Category {
        return Category(
            id = id,
            name = name,
            slug = slug,
            iconUrl = iconUrl,
            bannerUrl = bannerUrl
        )
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/remote/dto/ProductDto.kt
```kotlin
package com.apnacart.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.apnacart.domain.model.Product

@Serializable
data class ProductDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("brand") val brand: String,
    @SerialName("price") val price: Double,
    @SerialName("original_price") val originalPrice: Double? = null,
    @SerialName("discount_percent") val discountPercent: Int = 0,
    @SerialName("rating") val rating: Double = 0.0,
    @SerialName("review_count") val reviewCount: Int = 0,
    @SerialName("images") val images: List<String> = emptyList(),
    @SerialName("tags") val tags: List<String> = emptyList()
) {
    fun toDomain(): Product {
        return Product(
            id = id,
            name = name,
            description = description,
            brand = brand,
            price = price,
            originalPrice = originalPrice,
            discountPercent = discountPercent,
            rating = rating,
            reviewCount = reviewCount,
            images = images,
            tags = tags
        )
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/remote/dto/UserDto.kt
```kotlin
package com.apnacart.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.apnacart.domain.model.User

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("email") val email: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null
) {
    fun toDomain(): User {
        return User(
            id = id,
            fullName = fullName,
            email = email,
            phone = phone,
            avatarUrl = avatarUrl
        )
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/repository/AuthRepositoryImpl.kt
```kotlin
package com.apnacart.data.repository

import com.apnacart.data.remote.dto.UserDto
import com.apnacart.domain.model.User
import com.apnacart.domain.repository.IAuthRepository
import com.apnacart.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val client: SupabaseClient
) : IAuthRepository {

    override fun login(email: String, otp: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            client.auth.verifyEmailOtp(
                type = io.github.jan.supabase.auth.OtpType.Email.MAGIC_LINK,
                email = email,
                token = otp
            )
            // Just return a dummy user after successful OTP for now
            emit(Resource.Success(User(id = "dummy", fullName = "User", email = email, phone = null, avatarUrl = null)))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Login failed"))
        }
    }

    override fun requestOtp(email: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            client.auth.signInWith(OTP) {
                this.email = email
            }
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "OTP Request failed"))
        }
    }

    override fun logout(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            client.auth.signOut()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Logout failed"))
        }
    }

    override fun getCurrentUser(): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val user = client.auth.currentUserOrNull()
            if (user != null) {
                emit(Resource.Success(User(id = user.id, fullName = "User", email = user.email, phone = user.phone, avatarUrl = null)))
            } else {
                emit(Resource.Error("No user logged in"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error getting current user"))
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/repository/CartRepositoryImpl.kt
```kotlin
package com.apnacart.data.repository

import com.apnacart.data.local.db.ApnaCartDatabase
import com.apnacart.domain.model.CartItem
import com.apnacart.domain.repository.ICartRepository
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first

class CartRepositoryImpl(
    private val database: ApnaCartDatabase,
    private val productRepository: IProductRepository
) : ICartRepository {

    private val queries = database.apnaCartDatabaseQueries

    override fun getCartItems(): Flow<Resource<List<CartItem>>> = flow {
        emit(Resource.Loading())
        try {
            val entities = queries.selectAllCartItems().executeAsList()
            val cartItems = entities.mapNotNull { entity ->
                val productResource = productRepository.getProductDetail(entity.productId).first()
                if (productResource is Resource.Success && productResource.data != null) {
                    CartItem(
                        id = entity.id,
                        product = productResource.data,
                        quantity = entity.quantity.toInt()
                    )
                } else null
            }
            emit(Resource.Success(cartItems))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error fetching cart items"))
        }
    }

    override fun addToCart(productId: String, quantity: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            // Using productId as id for simplicity in local DB
            queries.insertCartItem(
                id = productId,
                productId = productId,
                quantity = quantity.toLong()
            )
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error adding to cart"))
        }
    }

    override fun updateQuantity(cartItemId: String, quantity: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.updateCartQuantity(quantity.toLong(), cartItemId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error updating cart quantity"))
        }
    }

    override fun removeFromCart(cartItemId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.deleteCartItem(cartItemId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error removing from cart"))
        }
    }

    override fun clearCart(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.clearCart()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error clearing cart"))
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/repository/OrderRepositoryImpl.kt
```kotlin
package com.apnacart.data.repository

import com.apnacart.domain.model.Order
import com.apnacart.domain.repository.IOrderRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OrderRepositoryImpl() : IOrderRepository {
    override fun getOrders(): Flow<Resource<List<Order>>> = flow {
        emit(Resource.Loading())
        emit(Resource.Success(emptyList())) // Dummy implementation
    }

    override fun getOrderDetail(orderId: String): Flow<Resource<Order>> = flow {
        emit(Resource.Loading())
        emit(Resource.Error("Not implemented"))
    }

    override fun placeOrder(addressId: String): Flow<Resource<Order>> = flow {
        emit(Resource.Loading())
        emit(Resource.Error("Not implemented"))
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/repository/PincodeRepositoryImpl.kt
```kotlin
package com.apnacart.data.repository

import com.apnacart.domain.model.PincodeInfo
import com.apnacart.domain.repository.IPincodeRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PincodeRepositoryImpl() : IPincodeRepository {
    override fun checkPincode(pincode: String): Flow<Resource<PincodeInfo>> = flow {
        emit(Resource.Loading())
        // Dummy implementation
        emit(Resource.Success(PincodeInfo(pincode, "City", "State", true, 3, true)))
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/repository/ProductRepositoryImpl.kt
```kotlin
package com.apnacart.data.repository

import com.apnacart.data.remote.dto.BannerDto
import com.apnacart.data.remote.dto.CategoryDto
import com.apnacart.data.remote.dto.ProductDto
import com.apnacart.domain.model.Banner
import com.apnacart.domain.model.Category
import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val client: SupabaseClient
) : IProductRepository {

    override fun getProducts(categorySlug: String?): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val response = if (categorySlug != null) {
                client.postgrest["products"].select {
                    filter {
                        eq("category_slug", categorySlug)
                    }
                }.decodeList<ProductDto>()
            } else {
                client.postgrest["products"].select().decodeList<ProductDto>()
            }
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred fetching products"))
        }
    }

    override fun getProductDetail(productId: String): Flow<Resource<Product>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.postgrest["products"].select {
                filter {
                    eq("id", productId)
                }
            }.decodeSingle<ProductDto>()
            emit(Resource.Success(response.toDomain()))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred fetching product details"))
        }
    }

    override fun searchProducts(query: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.postgrest["products"].select {
                filter {
                    ilike("name", "%$query%")
                }
            }.decodeList<ProductDto>()
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred searching products"))
        }
    }

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.postgrest["categories"].select().decodeList<CategoryDto>()
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred fetching categories"))
        }
    }

    override fun getBanners(): Flow<Resource<List<Banner>>> = flow {
        emit(Resource.Loading())
        try {
            val response = client.postgrest["banners"].select().decodeList<BannerDto>()
            emit(Resource.Success(response.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred fetching banners"))
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/data/repository/WishlistRepositoryImpl.kt
```kotlin
package com.apnacart.data.repository

import com.apnacart.data.local.db.ApnaCartDatabase
import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.domain.repository.IWishlistRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first

class WishlistRepositoryImpl(
    private val database: ApnaCartDatabase,
    private val productRepository: IProductRepository
) : IWishlistRepository {

    private val queries = database.apnaCartDatabaseQueries

    override fun getWishlist(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())
        try {
            val entities = queries.selectAllWishlistItems().executeAsList()
            val products = entities.mapNotNull { productId ->
                val productResource = productRepository.getProductDetail(productId).first()
                if (productResource is Resource.Success && productResource.data != null) {
                    productResource.data
                } else null
            }
            emit(Resource.Success(products))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error fetching wishlist"))
        }
    }

    override fun addToWishlist(productId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.insertWishlistItem(productId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error adding to wishlist"))
        }
    }

    override fun removeFromWishlist(productId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            queries.deleteWishlistItem(productId)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error removing from wishlist"))
        }
    }

    override fun checkWishlistStatus(productId: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())
        try {
            val count = queries.checkWishlistStatus(productId).executeAsOne()
            emit(Resource.Success(count > 0))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error checking wishlist status"))
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/di/AppModule.kt
```kotlin
package com.apnacart.di

import app.cash.sqldelight.db.SqlDriver
import com.apnacart.data.local.db.ApnaCartDatabase
import com.apnacart.data.remote.api.supabaseClient
import com.apnacart.data.repository.*
import com.apnacart.domain.repository.*
import com.apnacart.domain.usecase.*
import com.apnacart.presentation.screens.auth.LoginViewModel
import com.apnacart.presentation.screens.cart.CartViewModel
import com.apnacart.presentation.screens.home.HomeViewModel
import com.apnacart.presentation.screens.product_detail.ProductDetailViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

fun getSharedModule() = module {
    single { supabaseClient }

    // Database
    single { ApnaCartDatabase(get<SqlDriver>()) }

    // Repositories
    single<IProductRepository> { ProductRepositoryImpl(get()) }
    single<IAuthRepository> { AuthRepositoryImpl(get()) }
    single<ICartRepository> { CartRepositoryImpl(get(), get()) }
    single<IWishlistRepository> { WishlistRepositoryImpl(get(), get()) }
    single<IPincodeRepository> { PincodeRepositoryImpl() }
    single<IOrderRepository> { OrderRepositoryImpl() }

    // Use Cases
    factory { GetProductsUseCase(get()) }
    factory { GetProductDetailUseCase(get()) }
    factory { SearchProductsUseCase(get()) }
    factory { AddToCartUseCase(get()) }
    factory { ToggleWishlistUseCase(get()) }
    factory { CheckPincodeUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetOrdersUseCase(get()) }

    // ViewModels
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { ProductDetailViewModel(get(), get(), get()) }
    viewModel { CartViewModel(get()) }
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/model/Address.kt
```kotlin
package com.apnacart.domain.model

data class Address(
    val id: String,
    val label: String,
    val fullName: String,
    val phone: String,
    val line1: String,
    val line2: String?,
    val city: String,
    val state: String,
    val pincode: String,
    val isDefault: Boolean
)
```

### shared/src/commonMain/kotlin/com/apnacart/domain/model/Banner.kt
```kotlin
package com.apnacart.domain.model

data class Banner(
    val id: String,
    val title: String,
    val subtitle: String?,
    val imageUrl: String,
    val actionUrl: String?
)
```

### shared/src/commonMain/kotlin/com/apnacart/domain/model/CartItem.kt
```kotlin
package com.apnacart.domain.model

data class CartItem(
    val id: String,
    val product: Product,
    val quantity: Int
)
```

### shared/src/commonMain/kotlin/com/apnacart/domain/model/Category.kt
```kotlin
package com.apnacart.domain.model

data class Category(
    val id: String,
    val name: String,
    val slug: String,
    val iconUrl: String?,
    val bannerUrl: String?
)
```

### shared/src/commonMain/kotlin/com/apnacart/domain/model/Order.kt
```kotlin
package com.apnacart.domain.model

data class Order(
    val id: String,
    val orderNumber: String,
    val status: String,
    val total: Double,
    val date: String,
    val items: List<OrderItem>
)

data class OrderItem(
    val id: String,
    val productName: String,
    val productImage: String?,
    val quantity: Int,
    val unitPrice: Double
)
```

### shared/src/commonMain/kotlin/com/apnacart/domain/model/PincodeInfo.kt
```kotlin
package com.apnacart.domain.model

data class PincodeInfo(
    val pincode: String,
    val city: String,
    val state: String,
    val isServiceable: Boolean,
    val deliveryDays: Int,
    val isCodAvailable: Boolean
)
```

### shared/src/commonMain/kotlin/com/apnacart/domain/model/Product.kt
```kotlin
package com.apnacart.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String?,
    val brand: String,
    val price: Double,
    val originalPrice: Double?,
    val discountPercent: Int,
    val rating: Double,
    val reviewCount: Int,
    val images: List<String>,
    val tags: List<String>
)
```

### shared/src/commonMain/kotlin/com/apnacart/domain/model/Review.kt
```kotlin
package com.apnacart.domain.model

data class Review(
    val id: String,
    val productId: String,
    val userId: String,
    val rating: Int,
    val title: String?,
    val body: String?,
    val isVerified: Boolean,
    val helpfulCount: Int,
    val createdAt: String
)
```

### shared/src/commonMain/kotlin/com/apnacart/domain/model/User.kt
```kotlin
package com.apnacart.domain.model

data class User(
    val id: String,
    val fullName: String,
    val email: String?,
    val phone: String?,
    val avatarUrl: String?
)
```

### shared/src/commonMain/kotlin/com/apnacart/domain/repository/IAuthRepository.kt
```kotlin
package com.apnacart.domain.repository

import com.apnacart.domain.model.User
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun login(email: String, otp: String): Flow<Resource<User>>
    fun requestOtp(email: String): Flow<Resource<Unit>>
    fun logout(): Flow<Resource<Unit>>
    fun getCurrentUser(): Flow<Resource<User>>
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/repository/ICartRepository.kt
```kotlin
package com.apnacart.domain.repository

import com.apnacart.domain.model.CartItem
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface ICartRepository {
    fun getCartItems(): Flow<Resource<List<CartItem>>>
    fun addToCart(productId: String, quantity: Int): Flow<Resource<Unit>>
    fun updateQuantity(cartItemId: String, quantity: Int): Flow<Resource<Unit>>
    fun removeFromCart(cartItemId: String): Flow<Resource<Unit>>
    fun clearCart(): Flow<Resource<Unit>>
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/repository/IOrderRepository.kt
```kotlin
package com.apnacart.domain.repository

import com.apnacart.domain.model.Order
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IOrderRepository {
    fun getOrders(): Flow<Resource<List<Order>>>
    fun getOrderDetail(orderId: String): Flow<Resource<Order>>
    fun placeOrder(addressId: String): Flow<Resource<Order>>
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/repository/IPincodeRepository.kt
```kotlin
package com.apnacart.domain.repository

import com.apnacart.domain.model.PincodeInfo
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IPincodeRepository {
    fun checkPincode(pincode: String): Flow<Resource<PincodeInfo>>
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/repository/IProductRepository.kt
```kotlin
package com.apnacart.domain.repository

import com.apnacart.domain.model.Product
import com.apnacart.domain.model.Category
import com.apnacart.domain.model.Banner
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IProductRepository {
    fun getProducts(categorySlug: String? = null): Flow<Resource<List<Product>>>
    fun getProductDetail(productId: String): Flow<Resource<Product>>
    fun searchProducts(query: String): Flow<Resource<List<Product>>>
    fun getCategories(): Flow<Resource<List<Category>>>
    fun getBanners(): Flow<Resource<List<Banner>>>
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/repository/IWishlistRepository.kt
```kotlin
package com.apnacart.domain.repository

import com.apnacart.domain.model.Product
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

interface IWishlistRepository {
    fun getWishlist(): Flow<Resource<List<Product>>>
    fun addToWishlist(productId: String): Flow<Resource<Unit>>
    fun removeFromWishlist(productId: String): Flow<Resource<Unit>>
    fun checkWishlistStatus(productId: String): Flow<Resource<Boolean>>
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/usecase/AddToCartUseCase.kt
```kotlin
package com.apnacart.domain.usecase

import com.apnacart.domain.repository.ICartRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class AddToCartUseCase(private val repository: ICartRepository) {
    operator fun invoke(productId: String, quantity: Int = 1): Flow<Resource<Unit>> {
        return repository.addToCart(productId, quantity)
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/usecase/CheckPincodeUseCase.kt
```kotlin
package com.apnacart.domain.usecase

import com.apnacart.domain.model.PincodeInfo
import com.apnacart.domain.repository.IPincodeRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class CheckPincodeUseCase(private val repository: IPincodeRepository) {
    operator fun invoke(pincode: String): Flow<Resource<PincodeInfo>> {
        return repository.checkPincode(pincode)
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/usecase/GetOrdersUseCase.kt
```kotlin
package com.apnacart.domain.usecase

import com.apnacart.domain.model.Order
import com.apnacart.domain.repository.IOrderRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class GetOrdersUseCase(private val repository: IOrderRepository) {
    operator fun invoke(): Flow<Resource<List<Order>>> {
        return repository.getOrders()
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/usecase/GetProductDetailUseCase.kt
```kotlin
package com.apnacart.domain.usecase

import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class GetProductDetailUseCase(private val repository: IProductRepository) {
    operator fun invoke(productId: String): Flow<Resource<Product>> {
        return repository.getProductDetail(productId)
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/usecase/GetProductsUseCase.kt
```kotlin
package com.apnacart.domain.usecase

import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val repository: IProductRepository) {
    operator fun invoke(categorySlug: String? = null): Flow<Resource<List<Product>>> {
        return repository.getProducts(categorySlug)
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/usecase/LoginUseCase.kt
```kotlin
package com.apnacart.domain.usecase

import com.apnacart.domain.model.User
import com.apnacart.domain.repository.IAuthRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val repository: IAuthRepository) {
    operator fun invoke(email: String, otp: String): Flow<Resource<User>> {
        return repository.login(email, otp)
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/usecase/SearchProductsUseCase.kt
```kotlin
package com.apnacart.domain.usecase

import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow

class SearchProductsUseCase(private val repository: IProductRepository) {
    operator fun invoke(query: String): Flow<Resource<List<Product>>> {
        return repository.searchProducts(query)
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/domain/usecase/ToggleWishlistUseCase.kt
```kotlin
package com.apnacart.domain.usecase

import com.apnacart.domain.repository.IWishlistRepository
import com.apnacart.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ToggleWishlistUseCase(private val repository: IWishlistRepository) {
    operator fun invoke(productId: String, isCurrentlyInWishlist: Boolean): Flow<Resource<Unit>> {
        return if (isCurrentlyInWishlist) {
            repository.removeFromWishlist(productId)
        } else {
            repository.addToWishlist(productId)
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/components/CustomTextField.kt
```kotlin
package com.apnacart.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
    errorMessage: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        isError = isError,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            errorBorderColor = MaterialTheme.colorScheme.error,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true
    )
    if (isError && errorMessage != null) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/components/PrimaryButton.kt
```kotlin
package com.apnacart.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/components/ProductCard.kt
```kotlin
package com.apnacart.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apnacart.domain.model.Product
import com.apnacart.util.formatAsCurrency

@Composable
fun ProductCard(
    product: Product,
    isFavorite: Boolean,
    onProductClick: (String) -> Unit,
    onFavoriteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .clickable { onProductClick(product.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = product.images.firstOrNull(),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )

                IconButton(
                    onClick = { onFavoriteClick(product.id) },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }

                if (product.discountPercent > 0) {
                    Surface(
                        color = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "${product.discountPercent}% OFF",
                            color = MaterialTheme.colorScheme.onError,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = product.price.formatAsCurrency(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (product.originalPrice != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = product.originalPrice.formatAsCurrency(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline,
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    }
                }
            }
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/components/TopBar.kt
```kotlin
package com.apnacart.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/navigation/AppNavigation.kt
```kotlin
package com.apnacart.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apnacart.presentation.screens.home.HomeScreen
import com.apnacart.presentation.screens.product_detail.ProductDetailScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onProductClick = { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }

        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/navigation/Screen.kt
```kotlin
package com.apnacart.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main") // Bottom nav container
    object Home : Screen("home")
    object Categories : Screen("categories")
    object Wishlist : Screen("wishlist")
    object Profile : Screen("profile")

    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object OrderHistory : Screen("order_history")
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/screens/auth/LoginScreen.kt
```kotlin
package com.apnacart.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apnacart.presentation.components.CustomTextField
import com.apnacart.presentation.components.PrimaryButton
import com.apnacart.util.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val email by viewModel.email.collectAsState()
    val otp by viewModel.otp.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is UiState.Success) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to ApnaCart", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(
            value = email,
            onValueChange = viewModel::updateEmail,
            label = "Email Address"
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = otp,
            onValueChange = viewModel::updateOtp,
            label = "OTP"
        )
        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = if (loginState is UiState.Loading) "Loading..." else "Login",
            onClick = viewModel::login,
            enabled = loginState !is UiState.Loading && email.isNotEmpty() && otp.isNotEmpty()
        )

        if (loginState is UiState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (loginState as UiState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/screens/auth/LoginViewModel.kt
```kotlin
package com.apnacart.presentation.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnacart.domain.usecase.LoginUseCase
import com.apnacart.util.Resource
import com.apnacart.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _otp = MutableStateFlow("")
    val otp = _otp.asStateFlow()

    private val _loginState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val loginState = _loginState.asStateFlow()

    fun updateEmail(email: String) { _email.value = email }
    fun updateOtp(otp: String) { _otp.value = otp }

    fun login() {
        viewModelScope.launch {
            loginUseCase(email.value, otp.value).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _loginState.value = UiState.Loading
                    is Resource.Success -> _loginState.value = UiState.Success(Unit)
                    is Resource.Error -> _loginState.value = UiState.Error(resource.message ?: "Login Failed")
                }
            }
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/screens/cart/CartScreen.kt
```kotlin
package com.apnacart.presentation.screens.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CartScreen(
    viewModel: CartViewModel = koinViewModel()
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Cart Screen - To Be Implemented")
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/screens/cart/CartViewModel.kt
```kotlin
package com.apnacart.presentation.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnacart.domain.model.CartItem
import com.apnacart.domain.repository.ICartRepository
import com.apnacart.util.Resource
import com.apnacart.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepository: ICartRepository
) : ViewModel() {

    private val _cartState = MutableStateFlow<UiState<List<CartItem>>>(UiState.Idle)
    val cartState = _cartState.asStateFlow()

    fun loadCart() {
        viewModelScope.launch {
            cartRepository.getCartItems().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _cartState.value = UiState.Loading
                    is Resource.Success -> _cartState.value = UiState.Success(resource.data ?: emptyList())
                    is Resource.Error -> _cartState.value = UiState.Error(resource.message ?: "Failed to load cart")
                }
            }
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/screens/home/HomeScreen.kt
```kotlin
package com.apnacart.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apnacart.presentation.components.ProductCard
import com.apnacart.presentation.components.TopBar
import com.apnacart.util.UiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(
    onProductClick: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = "ApnaCart")

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Featured Products Section
            item {
                Text("Featured Products", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                when (val productsState = state.featuredProducts) {
                    is UiState.Loading -> {
                        Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is UiState.Success -> {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(productsState.data) { product ->
                                ProductCard(
                                    product = product,
                                    isFavorite = false,
                                    onProductClick = onProductClick,
                                    onFavoriteClick = {}
                                )
                            }
                        }
                    }
                    is UiState.Error -> {
                        Text("Error: ${productsState.message}", color = MaterialTheme.colorScheme.error)
                    }
                    else -> {}
                }
            }
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/screens/home/HomeViewModel.kt
```kotlin
package com.apnacart.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnacart.domain.model.Banner
import com.apnacart.domain.model.Category
import com.apnacart.domain.model.Product
import com.apnacart.domain.repository.IProductRepository
import com.apnacart.util.Resource
import com.apnacart.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeState(
    val banners: UiState<List<Banner>> = UiState.Idle,
    val categories: UiState<List<Category>> = UiState.Idle,
    val featuredProducts: UiState<List<Product>> = UiState.Idle
)

class HomeViewModel(
    private val productRepository: IProductRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            productRepository.getBanners().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.value = _state.value.copy(banners = UiState.Loading)
                    is Resource.Success -> _state.value = _state.value.copy(banners = UiState.Success(resource.data ?: emptyList()))
                    is Resource.Error -> _state.value = _state.value.copy(banners = UiState.Error(resource.message ?: "Error"))
                }
            }
        }
        viewModelScope.launch {
            productRepository.getCategories().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.value = _state.value.copy(categories = UiState.Loading)
                    is Resource.Success -> _state.value = _state.value.copy(categories = UiState.Success(resource.data ?: emptyList()))
                    is Resource.Error -> _state.value = _state.value.copy(categories = UiState.Error(resource.message ?: "Error"))
                }
            }
        }
        viewModelScope.launch {
            productRepository.getProducts().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _state.value = _state.value.copy(featuredProducts = UiState.Loading)
                    is Resource.Success -> _state.value = _state.value.copy(featuredProducts = UiState.Success(resource.data ?: emptyList()))
                    is Resource.Error -> _state.value = _state.value.copy(featuredProducts = UiState.Error(resource.message ?: "Error"))
                }
            }
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/screens/product_detail/ProductDetailScreen.kt
```kotlin
package com.apnacart.presentation.screens.product_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.apnacart.presentation.components.PrimaryButton
import com.apnacart.presentation.components.TopBar
import com.apnacart.util.UiState
import com.apnacart.util.formatAsCurrency
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    viewModel: ProductDetailViewModel = koinViewModel()
) {
    val productState by viewModel.productState.collectAsState()
    val addToCartState by viewModel.addToCartState.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = "Details", onBackClick = onBackClick)

        when (val state = productState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val product = state.data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = product.images.firstOrNull(),
                        contentDescription = product.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = product.brand, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    Text(text = product.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = product.price.formatAsCurrency(), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = product.description ?: "No description available", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.weight(1f))
                    PrimaryButton(
                        text = if (addToCartState is UiState.Loading) "Adding..." else "Add to Cart",
                        onClick = { viewModel.addToCart(product.id) },
                        enabled = addToCartState !is UiState.Loading
                    )
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
            }
            else -> {}
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/screens/product_detail/ProductDetailViewModel.kt
```kotlin
package com.apnacart.presentation.screens.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apnacart.domain.model.Product
import com.apnacart.domain.usecase.AddToCartUseCase
import com.apnacart.domain.usecase.GetProductDetailUseCase
import com.apnacart.domain.usecase.ToggleWishlistUseCase
import com.apnacart.util.Resource
import com.apnacart.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val toggleWishlistUseCase: ToggleWishlistUseCase
) : ViewModel() {

    private val _productState = MutableStateFlow<UiState<Product>>(UiState.Idle)
    val productState = _productState.asStateFlow()

    private val _addToCartState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val addToCartState = _addToCartState.asStateFlow()

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            getProductDetailUseCase(productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _productState.value = UiState.Loading
                    is Resource.Success -> _productState.value = UiState.Success(resource.data!!)
                    is Resource.Error -> _productState.value = UiState.Error(resource.message ?: "Failed")
                }
            }
        }
    }

    fun addToCart(productId: String) {
        viewModelScope.launch {
            addToCartUseCase(productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _addToCartState.value = UiState.Loading
                    is Resource.Success -> _addToCartState.value = UiState.Success(Unit)
                    is Resource.Error -> _addToCartState.value = UiState.Error(resource.message ?: "Failed")
                }
            }
        }
    }
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/theme/Color.kt
```kotlin
package com.apnacart.presentation.theme

import androidx.compose.ui.graphics.Color

// ── Brand ──────────────────────────────────────────
val SaffronOrange    = Color(0xFFFF6B35)   // Primary CTA, logo, highlights
val SaffronLight     = Color(0xFFFF8C5A)   // Primary dark-mode variant
val DeepNavy         = Color(0xFF1B2B4B)   // Secondary, toolbar, footer
val GoldenYellow     = Color(0xFFFFD700)   // Accent, badges, stars

// ── Neutrals ────────────────────────────────────────
val OffWhite         = Color(0xFFF8F9FA)   // Light background
val LightGray        = Color(0xFFF0F2F5)   // Cards, surfaces
val MidGray          = Color(0xFF9E9E9E)   // Hints, placeholders
val DarkText         = Color(0xFF1A1A1A)   // Primary text
val SubText          = Color(0xFF616161)   // Secondary text

// ── Semantic ────────────────────────────────────────
val ErrorRed         = Color(0xFFE53935)
val SuccessGreen     = Color(0xFF43A047)
val WarningAmber     = Color(0xFFFFA000)
val InfoBlue         = Color(0xFF1565C0)
val DiscountTeal     = Color(0xFF00897B)   // Discount badges
val StarGold         = Color(0xFFFFC107)   // Rating stars

// ── Dark Theme ──────────────────────────────────────
val BackgroundDark   = Color(0xFF0F0F17)
val SurfaceDark      = Color(0xFF1E1E2E)
val CardDark         = Color(0xFF2A2A3E)
val OutlineDark      = Color(0xFF3A3A52)
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/theme/Shape.kt
```kotlin
package com.apnacart.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val ApnaCartShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small      = RoundedCornerShape(8.dp),
    medium     = RoundedCornerShape(12.dp),
    large      = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp),
)
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/theme/Theme.kt
```kotlin
package com.apnacart.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = SaffronOrange,
    onPrimary = OffWhite,
    secondary = DeepNavy,
    onSecondary = OffWhite,
    tertiary = GoldenYellow,
    background = OffWhite,
    surface = OffWhite,
    surfaceVariant = LightGray,
    onBackground = DarkText,
    onSurface = DarkText,
    error = ErrorRed,
    outline = MidGray
)

private val DarkColors = darkColorScheme(
    primary = SaffronLight,
    onPrimary = BackgroundDark,
    secondary = DeepNavy,
    onSecondary = OffWhite,
    tertiary = GoldenYellow,
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = CardDark,
    onBackground = OffWhite,
    onSurface = OffWhite,
    error = ErrorRed,
    outline = OutlineDark
)

@Composable
fun ApnaCartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = getApnaCartTypography(),
        shapes = ApnaCartShapes,
        content = content
    )
}
```

### shared/src/commonMain/kotlin/com/apnacart/presentation/theme/Typography.kt
```kotlin
package com.apnacart.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import apnacart.shared.generated.resources.Res
import apnacart.shared.generated.resources.*

@Composable
fun getPoppinsFamily() = FontFamily(
    Font(Res.font.poppins_regular, FontWeight.Normal),
    Font(Res.font.poppins_medium, FontWeight.Medium),
    Font(Res.font.poppins_semibold, FontWeight.SemiBold),
    Font(Res.font.poppins_bold, FontWeight.Bold),
)

@Composable
fun getInterFamily() = FontFamily(
    Font(Res.font.inter_light, FontWeight.Light),
    Font(Res.font.inter_regular, FontWeight.Normal),
    Font(Res.font.inter_medium, FontWeight.Medium),
    Font(Res.font.inter_semibold, FontWeight.SemiBold),
    Font(Res.font.inter_bold, FontWeight.Bold),
)

@Composable
fun getApnaCartTypography(): Typography {
    val poppins = getPoppinsFamily()
    val inter = getInterFamily()

    return Typography(
        displayLarge  = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Bold, fontSize = 32.sp, letterSpacing = (-0.5).sp),
        displayMedium = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Bold, fontSize = 28.sp),
        headlineLarge = TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 24.sp),
        headlineMedium= TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
        titleLarge    = TextStyle(fontFamily = poppins, fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
        titleMedium   = TextStyle(fontFamily = poppins, fontWeight = FontWeight.Medium, fontSize = 16.sp),
        bodyLarge     = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
        bodyMedium    = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
        bodySmall     = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Light, fontSize = 12.sp, lineHeight = 18.sp),
        labelLarge    = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Medium, fontSize = 14.sp),
        labelMedium   = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Medium, fontSize = 12.sp),
        labelSmall    = TextStyle(fontFamily = inter,   fontWeight = FontWeight.Medium, fontSize = 10.sp, letterSpacing = 0.5.sp),
    )
}
```

### shared/src/commonMain/kotlin/com/apnacart/util/Constants.kt
```kotlin
package com.apnacart.util

object Constants {
    const val SUPABASE_URL = "https://awgljqzxzfzcprgveedc.supabase.co"
    const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImF3Z2xqcXp4emZ6Y3ByZ3ZlZWRjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODE0NDE4OTQsImV4cCI6MjA5NzAxNzg5NH0.tfvyNahM-mJ6u1v5RTSS4jA8KHc-pjFoG48uF0GcDw4"
    const val BASE_URL = "https://apnacart-production.up.railway.app/api/v1/" // Production backend
}
```

### shared/src/commonMain/kotlin/com/apnacart/util/Extensions.kt
```kotlin
package com.apnacart.util

fun Double.formatAsCurrency(): String {
    return "₹ $this" // Basic format for now
}
```

### shared/src/commonMain/kotlin/com/apnacart/util/Resource.kt
```kotlin
package com.apnacart.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
```

### shared/src/commonMain/kotlin/com/apnacart/util/UiState.kt
```kotlin
package com.apnacart.util

sealed class UiState<out T> {
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
```

### shared/src/commonMain/sqldelight/com/apnacart/data/local/db/ApnaCartDatabase.sq
```sql
CREATE TABLE CartEntity (
    id TEXT NOT NULL PRIMARY KEY,
    productId TEXT NOT NULL,
    quantity INTEGER NOT NULL
);

CREATE TABLE WishlistEntity (
    productId TEXT NOT NULL PRIMARY KEY
);

-- Queries for Cart
selectAllCartItems:
SELECT * FROM CartEntity;

insertCartItem:
INSERT OR REPLACE INTO CartEntity(id, productId, quantity)
VALUES (?, ?, ?);

updateCartQuantity:
UPDATE CartEntity SET quantity = ? WHERE id = ?;

deleteCartItem:
DELETE FROM CartEntity WHERE id = ?;

clearCart:
DELETE FROM CartEntity;

-- Queries for Wishlist
selectAllWishlistItems:
SELECT * FROM WishlistEntity;

insertWishlistItem:
INSERT OR REPLACE INTO WishlistEntity(productId)
VALUES (?);

deleteWishlistItem:
DELETE FROM WishlistEntity WHERE productId = ?;

checkWishlistStatus:
SELECT count(*) FROM WishlistEntity WHERE productId = ?;
```