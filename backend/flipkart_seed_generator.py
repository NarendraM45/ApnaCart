import csv
import json
import uuid
import random

# We will try to map Flipkart categories to our 8 simple categories
CATEGORY_MAPPING = {
    "Clothing": "fashion",
    "Footwear": "fashion",
    "Mobiles & Accessories": "mobiles",
    "Computers": "electronics",
    "Cameras & Accessories": "electronics",
    "Home Decor & Festive Needs": "home-kitchen",
    "Home Furnishing": "home-kitchen",
    "Kitchen & Dining": "home-kitchen",
    "Furniture": "home-kitchen",
    "Beauty and Personal Care": "beauty",
    "Health & Personal Care Appliances": "beauty",
    "Toys & School Supplies": "toys",
    "Baby Care": "toys",
    "Watches": "fashion",
    "Home Entertainment": "appliances",
    "Home & Kitchen": "appliances", # Some overlap, but fine
    "Pens & Stationery": "books", # close enough
}

CATEGORIES_SQL = [
    {"name": "Mobiles", "slug": "mobiles", "icon_url": "https://dummyimage.com/200x200/EAEDED/131921.png&text=Mobiles"},
    {"name": "Electronics", "slug": "electronics", "icon_url": "https://dummyimage.com/200x200/EAEDED/131921.png&text=Electronics"},
    {"name": "Fashion", "slug": "fashion", "icon_url": "https://dummyimage.com/200x200/EAEDED/131921.png&text=Fashion"},
    {"name": "Beauty", "slug": "beauty", "icon_url": "https://dummyimage.com/200x200/EAEDED/131921.png&text=Beauty"},
    {"name": "Home & Kitchen", "slug": "home-kitchen", "icon_url": "https://dummyimage.com/200x200/EAEDED/131921.png&text=Home+Kitchen"},
    {"name": "Appliances", "slug": "appliances", "icon_url": "https://dummyimage.com/200x200/EAEDED/131921.png&text=Appliances"},
    {"name": "Books", "slug": "books", "icon_url": "https://dummyimage.com/200x200/EAEDED/131921.png&text=Books"},
    {"name": "Toys & Games", "slug": "toys", "icon_url": "https://dummyimage.com/200x200/EAEDED/131921.png&text=Toys+Games"}
]

csv_file = "../flipkart_com-ecommerce_sample.csv"

# Store products by mapped category
products_by_category = {k['slug']: [] for k in CATEGORIES_SQL}

print("Parsing Flipkart CSV...")

with open(csv_file, 'r', encoding='utf-8') as f:
    reader = csv.DictReader(f)
    for row in reader:
        cat_tree = row['product_category_tree']
        if not cat_tree: continue
        
        try:
            cats = json.loads(cat_tree)[0].split(' >> ')
            main_cat = cats[0]
        except:
            continue
            
        mapped_cat = CATEGORY_MAPPING.get(main_cat)
        if not mapped_cat: continue
        
        if not row['retail_price'] or not row['discounted_price']:
            continue
            
        try:
            price = float(row['discounted_price'])
            orig_price = float(row['retail_price'])
            if orig_price <= price:
                orig_price = price * 1.2
            discount = int((1 - (price / orig_price)) * 100)
        except:
            continue
            
        name = row['product_name'].strip()
        desc = row['description'].strip()
        brand = row['brand'].strip()
        if not brand:
            brand = "Generic"
            
        if len(desc) > 500:
            desc = desc[:497] + "..."
            
        # Generate dummy image based on the product brand and start of name
        short_name = (brand + " " + name).replace(" ", "+")[:20]
        img_url = f"https://dummyimage.com/400x400/EAEDED/131921.png&text={short_name}"
        images = [img_url, img_url, img_url]
            
        p = {
            "id": str(uuid.uuid4()),
            "name": name,
            "description": desc,
            "brand": brand,
            "price": price,
            "original_price": orig_price,
            "discount_percent": discount,
            "rating": round(random.uniform(3.5, 4.9), 1),
            "review_count": random.randint(10, 15000),
            "category_slug": mapped_cat,
            "images": images
        }
        
        products_by_category[mapped_cat].append(p)

print("Finished parsing. Generating Seed SQL...")

sql_lines = []
sql_lines.append("-- ApnaCart Flipkart Real Data Seed (Dummy Images)")
sql_lines.append("TRUNCATE TABLE products CASCADE;")
sql_lines.append("TRUNCATE TABLE categories CASCADE;")
sql_lines.append("TRUNCATE TABLE banners CASCADE;\n")

# Banners
sql_lines.append("INSERT INTO banners (title, subtitle, image_url) VALUES")
sql_lines.append("('Big Billion Days', 'Unbelievable Offers', 'https://dummyimage.com/1600x270/FF9900/131921.png&text=Big+Billion+Days'),")
sql_lines.append("('Electronics Sale', 'Top Laptops & Mobiles', 'https://dummyimage.com/1600x270/131921/FFFFFF.png&text=Electronics+Sale');\n")

# Categories
sql_lines.append("INSERT INTO categories (name, slug, icon_url) VALUES")
for i, c in enumerate(CATEGORIES_SQL):
    end_char = ";" if i == len(CATEGORIES_SQL) - 1 else ","
    sql_lines.append(f"('{c['name']}', '{c['slug']}', '{c['icon_url']}'){end_char}")
sql_lines.append("\n")

# Select 50 from each category (or max available)
final_products = []
for cat, prods in products_by_category.items():
    random.shuffle(prods)
    final_products.extend(prods[:50])
    
random.shuffle(final_products)

sql_lines.append("INSERT INTO products (id, name, description, brand, price, original_price, discount_percent, rating, review_count, category_slug, images) VALUES")

for i, p in enumerate(final_products):
    name_esc = p['name'].replace("'", "''")
    desc_esc = p['description'].replace("'", "''")
    brand_esc = p['brand'].replace("'", "''")
    
    selected_images = p['images'][:3]
    escaped_urls = [url.replace("'", "''") for url in selected_images]
    images_array = "ARRAY['" + "', '".join(escaped_urls) + "']"
    
    end_char = ";" if i == len(final_products) - 1 else ","
    
    sql_lines.append(f"('{p['id']}', '{name_esc}', '{desc_esc}', '{brand_esc}', {p['price']}, {p['original_price']}, {p['discount_percent']}, {p['rating']}, {p['review_count']}, '{p['category_slug']}', {images_array}){end_char}")

with open("amazon_seed.sql", "w", encoding="utf-8") as f:
    f.write("\n".join(sql_lines))

print(f"Created amazon_seed.sql successfully! Total products: {len(final_products)}")
