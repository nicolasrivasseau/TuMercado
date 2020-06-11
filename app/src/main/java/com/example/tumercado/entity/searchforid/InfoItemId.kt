package com.example.tumercado.entity.searchforid

data class InfoItemId(
    val accepts_mercadopago: Boolean,
    val attributes: List<Attribute>,
    val automatic_relist: Boolean,
    val available_quantity: Int,
    val base_price: Int,
    val buying_mode: String,
    val catalog_listing: Boolean,
    val catalog_product_id: String,
    val category_id: String,
    val condition: String,
    val coverage_areas: List<Any>,
    val currency_id: String,
    val date_created: String,
    val deal_ids: List<Any>,
    val descriptions: List<Description>,
    val differential_pricing: Any,
    val domain_id: String,
    val geolocation: Geolocation,
    val health: Any,
    val id: String,
    val initial_quantity: Int,
    val international_delivery_mode: String,
    val last_updated: String,
    val listing_source: String,
    val listing_type_id: String,
    val location: Location,
    val non_mercado_pago_payment_methods: List<Any>,
    val official_store_id: Int,
    val original_price: Any,
    val parent_item_id: Any,
    val permalink: String,
    val pictures: List<Picture>,
    val price: Int,
    val sale_terms: List<SaleTerm>,
    val secure_thumbnail: String,
    val seller_address: SellerAddress,
    val seller_contact: Any,
    val seller_id: Int,
    val shipping: Shipping,
    val site_id: String,
    val sold_quantity: Int,
    val start_time: String,
    val status: String,
    val stop_time: String,
    val sub_status: List<String>,
    val subtitle: Any,
    val tags: List<String>,
    val thumbnail: String,
    val title: String,
    val variations: List<Any>,
    val video_id: Any,
    val warnings: List<Any>,
    val warranty: String
)