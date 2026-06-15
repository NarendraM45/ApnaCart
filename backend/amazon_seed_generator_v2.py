import random
import uuid

CATEGORIES = [
    {"name": "Mobiles", "slug": "mobiles", "icon_url": "https://cdn.dummyjson.com/products/images/smartphones/iPhone%2013%20Pro/thumbnail.png"},
    {"name": "Electronics", "slug": "electronics", "icon_url": "https://cdn.dummyjson.com/products/images/laptops/MacBook%20Pro%2014-Inch/thumbnail.png"},
    {"name": "Fashion", "slug": "fashion", "icon_url": "https://cdn.dummyjson.com/products/images/mens-shirts/Mens%20Casual%20Slim%20Fit/thumbnail.png"},
    {"name": "Beauty", "slug": "beauty", "icon_url": "https://cdn.dummyjson.com/products/images/skincare/Cosrx%20Advanced%20Snail%2096%20Mucin%20Power%20Essence/thumbnail.png"},
    {"name": "Home & Kitchen", "slug": "home-kitchen", "icon_url": "https://cdn.dummyjson.com/products/images/furniture/Annibale%20Colombo%20Bed/thumbnail.png"},
    {"name": "Appliances", "slug": "appliances", "icon_url": "https://cdn.dummyjson.com/products/images/home-decoration/Decoration%20Wooden%20House/thumbnail.png"},
    {"name": "Books", "slug": "books", "icon_url": "https://covers.openlibrary.org/b/id/10521270-M.jpg"},
    {"name": "Toys & Games", "slug": "toys", "icon_url": "https://cdn.dummyjson.com/products/images/motorcycle/Generic%20Motorcycle/thumbnail.png"}
]

# Image Pools
IMG_MOBILES = ["https://cdn.dummyjson.com/products/images/smartphones/iPhone%2013%20Pro/1.png", "https://cdn.dummyjson.com/products/images/smartphones/iPhone%20X/1.png", "https://cdn.dummyjson.com/products/images/smartphones/Samsung%20Galaxy%20S10/1.png", "https://cdn.dummyjson.com/products/images/smartphones/Samsung%20Galaxy%20S8/1.png"]
IMG_LAPTOPS = ["https://cdn.dummyjson.com/products/images/laptops/MacBook%20Pro%2014-Inch/1.png", "https://cdn.dummyjson.com/products/images/laptops/Asus%20Zenbook%20Pro%20Dual%2015/1.png", "https://cdn.dummyjson.com/products/images/laptops/Huawei%20Matebook%20X%20Pro/1.png"]
IMG_FASHION = ["https://cdn.dummyjson.com/products/images/mens-shirts/Mens%20Casual%20Slim%20Fit/1.png", "https://cdn.dummyjson.com/products/images/womens-dresses/Black%20Women's%20Gown/1.png", "https://cdn.dummyjson.com/products/images/womens-shoes/Women%20Shoes/1.png"]
IMG_BEAUTY = ["https://cdn.dummyjson.com/products/images/skincare/Cosrx%20Advanced%20Snail%2096%20Mucin%20Power%20Essence/1.png", "https://cdn.dummyjson.com/products/images/skincare/Glamglow%20Supermud%20Clearing%20Treatment/1.png", "https://cdn.dummyjson.com/products/images/fragrances/Chanel%20Coco%20Noir%20Eau%20De/1.png", "https://cdn.dummyjson.com/products/images/fragrances/Dior%20J'adore/1.png"]
IMG_HOME = ["https://cdn.dummyjson.com/products/images/furniture/Annibale%20Colombo%20Bed/1.png", "https://cdn.dummyjson.com/products/images/furniture/Knoll%20Saarinen%20Executive%20Conference%20Chair/1.png", "https://cdn.dummyjson.com/products/images/home-decoration/Decoration%20Wooden%20House/1.png"]
IMG_BOOKS = ["https://covers.openlibrary.org/b/id/10521270-L.jpg", "https://covers.openlibrary.org/b/id/12547191-L.jpg", "https://covers.openlibrary.org/b/id/14352824-L.jpg", "https://covers.openlibrary.org/b/id/12967677-L.jpg", "https://covers.openlibrary.org/b/id/13244243-L.jpg"]
IMG_TOYS = ["https://cdn.dummyjson.com/products/images/motorcycle/Generic%20Motorcycle/1.png", "https://cdn.dummyjson.com/products/images/motorcycle/Kawasaki%20Z800/1.png", "https://cdn.dummyjson.com/products/images/vehicle/300%20Touring/1.png"]

def generate_mobiles(count):
    brands = ["Apple", "Samsung", "OnePlus", "Google", "Xiaomi", "Vivo", "Oppo", "Motorola", "Nothing"]
    lines = ["iPhone 15", "iPhone 14", "Galaxy S24", "Galaxy S23", "Pixel 8", "Pixel 7", "Nord CE", "Edge 40", "Phone (2)"]
    suffixes = ["Pro Max", "Ultra", "Plus", "Pro", "Lite", "FE", "5G"]
    storages = ["128GB", "256GB", "512GB", "1TB"]
    colors = ["Midnight Black", "Natural Titanium", "Phantom White", "Emerald Green", "Ocean Blue", "Coral"]
    
    products = []
    for _ in range(count):
        brand = random.choice(brands)
        line = random.choice(lines)
        suffix = random.choice(suffixes)
        storage = random.choice(storages)
        color = random.choice(colors)
        
        name = f"{brand} {line} {suffix} ({color}, {storage} Storage)"
        desc = f"Experience the cutting-edge {name} with an advanced camera system, all-day battery life, and lightning-fast performance. Designed by {brand} for absolute perfection."
        price = random.randint(15000, 150000)
        img = random.choice(IMG_MOBILES)
        
        products.append({"name": name, "brand": brand, "desc": desc, "price": price, "cat": "mobiles", "img": img})
    return products

def generate_electronics(count):
    brands = ["Sony", "Dell", "HP", "Lenovo", "Asus", "Acer", "LG", "Samsung", "Bose", "Sennheiser"]
    products_list = []
    types = [
        ("WH-1000XM5 Wireless Noise Canceling Headphones", "Industry leading noise cancellation, 30 hour battery life, crystal clear hands-free calling."),
        ("Inspiron 15 13th Gen Intel Core i5 Laptop", "15.6 inch FHD display, 16GB RAM, 512GB SSD, Windows 11 Home."),
        ("OLED 55 inch 4K Smart TV", "Self-lit OLED pixels, perfect black, infinite contrast, AI Sound Pro."),
        ("QuietComfort Ultra Earbuds", "Spatial audio, world-class noise cancellation, custom tune technology."),
        ("Predator Helios 300 Gaming Laptop", "Intel Core i7, RTX 4060, 165Hz Display, RGB Keyboard.")
    ]
    for _ in range(count):
        brand = random.choice(brands)
        t_name, t_desc = random.choice(types)
        name = f"{brand} {t_name}"
        price = random.randint(10000, 200000)
        img = random.choice(IMG_LAPTOPS)
        products_list.append({"name": name, "brand": brand, "desc": t_desc, "price": price, "cat": "electronics", "img": img})
    return products_list

def generate_fashion(count):
    brands = ["Puma", "Nike", "Adidas", "Levi's", "U.S. Polo Assn.", "Allen Solly", "Van Heusen", "W for Woman", "Biba"]
    types = [
        ("Men's Regular Fit Cotton Polo Shirt", "Breathable fabric, comfortable fit, perfect for casual outings."),
        ("Women's A-Line Knee Length Dress", "Floral print, soft material, elegant design for evening wear."),
        ("Unisex Adult Running Shoes", "Lightweight, responsive cushioning, durable rubber outsole."),
        ("Men's Slim Fit Stretchable Jeans", "Premium denim, 5-pocket styling, stretchable for all-day comfort."),
        ("Women's Handcrafted Leather Handbag", "Genuine leather, spacious interior, elegant metallic accents.")
    ]
    products = []
    for _ in range(count):
        brand = random.choice(brands)
        t_name, t_desc = random.choice(types)
        name = f"{brand} {t_name}"
        price = random.randint(500, 5000)
        img = random.choice(IMG_FASHION)
        products.append({"name": name, "brand": brand, "desc": t_desc, "price": price, "cat": "fashion", "img": img})
    return products

def generate_beauty(count):
    brands = ["Minimalist", "COSRX", "Plum", "L'Oreal Paris", "Maybelline", "MAC", "The Ordinary", "Cetaphil"]
    types = [
        ("10% Niacinamide Face Serum 30ml", "Reduces blemishes, clears spots, and promotes even skin tone."),
        ("Advanced Snail 96 Mucin Power Essence", "Hydrating serum, repairs skin barrier, gives a natural glow."),
        ("Green Tea Pore Cleansing Face Wash", "Deep cleanses, removes excess oil, prevents acne breakouts."),
        ("Matte Liquid Lipstick, Long Lasting", "Intense color payoff, smudge-proof, 16-hour comfortable wear."),
        ("Gentle Skin Cleanser 250ml", "Dermatologist recommended, soap-free, non-irritating formula.")
    ]
    products = []
    for _ in range(count):
        brand = random.choice(brands)
        t_name, t_desc = random.choice(types)
        name = f"{brand} {t_name}"
        price = random.randint(200, 2500)
        img = random.choice(IMG_BEAUTY)
        products.append({"name": name, "brand": brand, "desc": t_desc, "price": price, "cat": "beauty", "img": img})
    return products

def generate_home(count):
    brands = ["Pigeon", "Prestige", "Wonderchef", "Wakefit", "Bombay Dyeing", "IKEA", "Milton", "Cello"]
    types = [
        ("Non-Stick Cookware Set (3 Pieces)", "PFOA free, scratch resistant, compatible with induction and gas."),
        ("Orthopedic Memory Foam Mattress", "Spine alignment, breathable fabric, zero motion transfer."),
        ("100% Cotton Double Bedsheet with 2 Pillow Covers", "Soft feel, fade resistant, vibrant colors."),
        ("Stainless Steel Insulated Water Bottle 1L", "Keeps water cold/hot for 24 hours, leak-proof, rust-free."),
        ("Wooden Coffee Table with Storage", "Solid wood construction, elegant finish, spacious storage shelf.")
    ]
    products = []
    for _ in range(count):
        brand = random.choice(brands)
        t_name, t_desc = random.choice(types)
        name = f"{brand} {t_name}"
        price = random.randint(500, 15000)
        img = random.choice(IMG_HOME)
        products.append({"name": name, "brand": brand, "desc": t_desc, "price": price, "cat": "home-kitchen", "img": img})
    return products

def generate_appliances(count):
    brands = ["LG", "Samsung", "Whirlpool", "Bosch", "IFB", "Dyson", "Philips", "Voltas"]
    types = [
        ("8.0 Kg Fully-Automatic Front Load Washing Machine", "Inverter technology, hygiene steam, built-in heater."),
        ("256L Frost Free Double Door Refrigerator", "Convertible freezer, digital inverter, stabilizer free operation."),
        ("1.5 Ton 5 Star Split AC", "Copper condenser, anti-dust filter, fast cooling."),
        ("Cordless Vacuum Cleaner", "Powerful suction, up to 60 min runtime, laser dust detection."),
        ("Microwave Oven 28L", "Convection, auto-cook menus, child lock feature.")
    ]
    products = []
    for _ in range(count):
        brand = random.choice(brands)
        t_name, t_desc = random.choice(types)
        name = f"{brand} {t_name}"
        price = random.randint(5000, 80000)
        img = random.choice(IMG_HOME)
        products.append({"name": name, "brand": brand, "desc": t_desc, "price": price, "cat": "appliances", "img": img})
    return products

def generate_books(count):
    authors = ["James Clear", "Morgan Housel", "Robert Kiyosaki", "Paulo Coelho", "Colleen Hoover", "J.K. Rowling", "George Orwell"]
    titles = [
        "Atomic Habits: An Easy & Proven Way to Build Good Habits",
        "The Psychology of Money",
        "Rich Dad Poor Dad",
        "The Alchemist",
        "It Ends With Us",
        "1984: A Novel",
        "Harry Potter and the Sorcerer's Stone"
    ]
    products = []
    for _ in range(count):
        author = random.choice(authors)
        title = random.choice(titles)
        name = f"{title} (Paperback) by {author}"
        desc = f"A phenomenal bestseller by {author}. Dive into this masterpiece that has changed millions of lives worldwide. Printed on high-quality paper."
        price = random.randint(150, 1200)
        img = random.choice(IMG_BOOKS)
        products.append({"name": name, "brand": "Penguin/Scholastic", "desc": desc, "price": price, "cat": "books", "img": img})
    return products

def generate_toys(count):
    brands = ["LEGO", "Hasbro", "Mattel", "Hot Wheels", "Barbie", "Nerf", "Fisher-Price"]
    types = [
        ("Classic Bricks Set 500 Pieces", "Unleash creativity, colorful bricks, perfect for kids aged 4+."),
        ("Die-Cast Cars 5-Pack", "1:64 scale vehicles, authentic details, great for collectors."),
        ("Motorized Blaster with 20 Darts", "Rapid fire, long range, safe foam darts."),
        ("Interactive Learning Robot", "Teaches coding basics, fun games, voice commands."),
        ("Board Game - Family Edition", "Classic fun for the whole family, 2-6 players, strategy and luck.")
    ]
    products = []
    for _ in range(count):
        brand = random.choice(brands)
        t_name, t_desc = random.choice(types)
        name = f"{brand} {t_name}"
        price = random.randint(200, 4000)
        img = random.choice(IMG_TOYS)
        products.append({"name": name, "brand": brand, "desc": t_desc, "price": price, "cat": "toys", "img": img})
    return products

def generate_all_products():
    all_p = []
    all_p.extend(generate_mobiles(50))
    all_p.extend(generate_electronics(50))
    all_p.extend(generate_fashion(50))
    all_p.extend(generate_beauty(50))
    all_p.extend(generate_home(50))
    all_p.extend(generate_appliances(50))
    all_p.extend(generate_books(50))
    all_p.extend(generate_toys(50))
    return all_p

raw_products = generate_all_products()

sql_lines = []
sql_lines.append("-- ApnaCart High-Quality V2 Amazon UI Seed Data")
sql_lines.append("TRUNCATE TABLE products CASCADE;")
sql_lines.append("TRUNCATE TABLE categories CASCADE;")
sql_lines.append("TRUNCATE TABLE banners CASCADE;\n")

# Banners
sql_lines.append("INSERT INTO banners (title, subtitle, image_url) VALUES")
sql_lines.append("('Great Indian Festival', 'Up to 80% Off on Top Brands', 'https://m.media-amazon.com/images/I/81wJ2-B+lXL._SX3000_.jpg'),")
sql_lines.append("('Blockbuster Deals', 'Lowest Prices of the Year', 'https://m.media-amazon.com/images/I/61IwkbVfU4L._SX3000_.jpg');\n")

# Categories
sql_lines.append("INSERT INTO categories (name, slug, icon_url) VALUES")
for i, c in enumerate(CATEGORIES):
    end_char = ";" if i == len(CATEGORIES) - 1 else ","
    sql_lines.append(f"('{c['name']}', '{c['slug']}', '{c['icon_url']}'){end_char}")
sql_lines.append("\n")

# Products
sql_lines.append("INSERT INTO products (id, name, description, brand, price, original_price, discount_percent, rating, review_count, category_slug, images) VALUES")

for i, p in enumerate(raw_products):
    id_str = str(uuid.uuid4())
    rating = round(random.uniform(3.8, 4.9), 1)
    reviews = random.randint(150, 45000)
    discount = random.randint(5, 65)
    orig_price = int(p['price'] / (1 - discount / 100.0))
    
    name_esc = p['name'].replace("'", "''")
    desc_esc = p['desc'].replace("'", "''")
    brand_esc = p['brand'].replace("'", "''")
    img_esc = p['img'].replace("'", "''")
    
    # Generate 3 images for each product based on its primary image
    images_array = f"ARRAY['{img_esc}', '{img_esc}', '{img_esc}']"
    end_char = ";" if i == len(raw_products) - 1 else ","
    
    sql_lines.append(f"('{id_str}', '{name_esc}', '{desc_esc}', '{brand_esc}', {p['price']}, {orig_price}, {discount}, {rating}, {reviews}, '{p['cat']}', {images_array}){end_char}")

with open("amazon_seed.sql", "w", encoding="utf-8") as f:
    f.write("\n".join(sql_lines))

print(f"Created amazon_seed.sql successfully! Total products: {len(raw_products)}")
