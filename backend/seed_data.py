import os
from supabase import create_client, Client
from dotenv import load_dotenv

load_dotenv()

SUPABASE_URL = os.getenv("SUPABASE_URL")
SUPABASE_KEY = os.getenv("SUPABASE_SERVICE_ROLE_KEY")

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
