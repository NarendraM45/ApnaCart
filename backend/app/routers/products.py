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
