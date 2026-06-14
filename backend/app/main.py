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
