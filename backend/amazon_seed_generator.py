import random
import uuid
import json

CATEGORIES = [
    {"name": "Electronics", "slug": "electronics", "icon_url": "https://m.media-amazon.com/images/I/41-h0Qj7YGL._AC_SY200_.jpg"},
    {"name": "Mobiles", "slug": "mobiles", "icon_url": "https://m.media-amazon.com/images/I/41z-Lh-eHdL._AC_SY200_.jpg"},
    {"name": "Fashion", "slug": "fashion", "icon_url": "https://m.media-amazon.com/images/I/61NlBxtbA4L._UX679_.jpg"},
    {"name": "Home & Kitchen", "slug": "home-kitchen", "icon_url": "https://m.media-amazon.com/images/I/41-P0y1eJ2L._AC_SY200_.jpg"},
    {"name": "Books", "slug": "books", "icon_url": "https://m.media-amazon.com/images/I/81A-23K5XDL._AC_UY327_FMwebp_QL65_.jpg"},
    {"name": "Beauty", "slug": "beauty", "icon_url": "https://m.media-amazon.com/images/I/51A4Hq4m2tL._AC_SY200_.jpg"},
    {"name": "Toys & Games", "slug": "toys-games", "icon_url": "https://m.media-amazon.com/images/I/41T-lB-vXNL._AC_SY200_.jpg"},
    {"name": "Appliances", "slug": "appliances", "icon_url": "https://m.media-amazon.com/images/I/41sW9uLg2wL._AC_SY200_.jpg"},
]

def generate_product(cat_slug):
    id_str = str(uuid.uuid4())
    rating = round(random.uniform(3.5, 5.0), 1)
    reviews = random.randint(50, 25000)
    discount = random.randint(5, 75)
    
    if cat_slug == "electronics":
        name = f"{random.choice(['Sony', 'Samsung', 'LG', 'Bose', 'Dell'])} {random.choice(['Headphones', 'Smart TV', 'Laptop', 'Soundbar', 'Tablet'])} {random.randint(100, 9999)}"
        base_price = random.randint(5000, 150000)
        img = "https://m.media-amazon.com/images/I/71o8Q5XJS5L._SL1500_.jpg"
    elif cat_slug == "mobiles":
        name = f"{random.choice(['Apple', 'Samsung', 'OnePlus', 'Xiaomi'])} {random.choice(['Pro', 'Max', 'Ultra', 'Lite'])} 5G ({random.choice(['Black', 'Blue', 'Silver'])} {random.choice(['128GB', '256GB', '512GB'])} Storage)"
        base_price = random.randint(15000, 150000)
        img = "https://m.media-amazon.com/images/I/71xb2xkN5qL._SX679_.jpg"
    elif cat_slug == "fashion":
        name = f"{random.choice(['Polo', 'Levis', 'Puma', 'Nike'])} Men's {random.choice(['Classic T-Shirt', 'Jeans', 'Running Shoes', 'Jacket'])}"
        base_price = random.randint(500, 5000)
        img = "https://m.media-amazon.com/images/I/61NlBxtbA4L._UX679_.jpg"
    elif cat_slug == "books":
        name = f"{random.choice(['The Psychology of Money', 'Atomic Habits', 'Deep Work', 'Dune', '1984'])} ({random.choice(['Paperback', 'Hardcover'])})"
        base_price = random.randint(200, 1500)
        img = "https://m.media-amazon.com/images/I/81A-23K5XDL._AC_UY327_FMwebp_QL65_.jpg"
    else:
        name = f"Premium {cat_slug.replace('-', ' ').title()} Product - High Quality"
        base_price = random.randint(1000, 10000)
        img = "https://m.media-amazon.com/images/I/61H3a0d5wEL._AC_UL480_FMwebp_QL65_.jpg"

    original_price = int(base_price / (1 - discount / 100.0))

    return {
        "id": id_str,
        "name": name,
        "description": "This is a premium product available at an unbeatable price on ApnaCart. Get fast delivery and amazing features with this verified purchase. Order now to get it by tomorrow.",
        "brand": name.split(' ')[0],
        "price": float(base_price),
        "original_price": float(original_price),
        "discount_percent": discount,
        "rating": rating,
        "review_count": reviews,
        "category_slug": cat_slug,
        "images": [img, img]
    }

print("Generating SQL script for 500 products...")

sql_lines = []

sql_lines.append("-- ApnaCart Amazon UI Seed Data")
sql_lines.append("TRUNCATE TABLE products CASCADE;")
sql_lines.append("TRUNCATE TABLE categories CASCADE;")
sql_lines.append("TRUNCATE TABLE banners CASCADE;\n")

# Banners
sql_lines.append("INSERT INTO banners (title, subtitle, image_url) VALUES")
sql_lines.append("('Great Indian Festival', 'Up to 80% Off', 'https://m.media-amazon.com/images/I/81wJ2-B+lXL._SX3000_.jpg'),")
sql_lines.append("('Prime Day Deals', 'Exclusive offers for Prime members', 'https://m.media-amazon.com/images/I/61IwkbVfU4L._SX3000_.jpg');\n")

# Categories
sql_lines.append("INSERT INTO categories (name, slug, icon_url) VALUES")
for i, c in enumerate(CATEGORIES):
    end_char = ";" if i == len(CATEGORIES) - 1 else ","
    sql_lines.append(f"('{c['name']}', '{c['slug']}', '{c['icon_url']}'){end_char}")
sql_lines.append("\n")

# Products
sql_lines.append("INSERT INTO products (id, name, description, brand, price, original_price, discount_percent, rating, review_count, category_slug, images) VALUES")

products = []
for _ in range(500):
    cat = random.choice(CATEGORIES)
    products.append(generate_product(cat['slug']))

for i, p in enumerate(products):
    name_esc = p['name'].replace("'", "''")
    desc_esc = p['description'].replace("'", "''")
    brand_esc = p['brand'].replace("'", "''")
    images_array = "ARRAY['" + "', '".join(p['images']) + "']"
    end_char = ";" if i == len(products) - 1 else ","
    
    sql_lines.append(f"('{p['id']}', '{name_esc}', '{desc_esc}', '{brand_esc}', {p['price']}, {p['original_price']}, {p['discount_percent']}, {p['rating']}, {p['review_count']}, '{p['category_slug']}', {images_array}){end_char}")

with open("amazon_seed.sql", "w", encoding="utf-8") as f:
    f.write("\n".join(sql_lines))

print("Created amazon_seed.sql successfully! Total products: 500")
