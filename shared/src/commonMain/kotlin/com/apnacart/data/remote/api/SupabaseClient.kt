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
