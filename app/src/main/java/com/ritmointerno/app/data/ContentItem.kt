package com.ritmointerno.app.data

/**
 * Representa un ítem de cualquier sección: un concepto, un tip, un video o
 * un recurso externo. No todos los campos aplican a todos los tipos:
 *  - videoUrl solo se usa cuando type == VIDEO (enlace de YouTube)
 *  - linkUrl solo se usa cuando type == RECURSO (artículo, podcast, PDF, etc.)
 *  - subconceptos solo aplica a CONCEPTO, y es opcional: un concepto puede
 *    no tener ninguno (queda null o lista vacía) y la tarjeta se ve igual
 *    que antes, sin nada extra debajo.
 */
data class ContentItem(
    val id: String,
    val type: ContentType,
    val titulo: String,
    val descripcion: String,
    val videoUrl: String? = null,
    val linkUrl: String? = null,
    val autor: String? = null,
    val subconceptos: List<SubConcepto>? = null
)

/** Un sub-punto dentro de un concepto, ej. "Concepto 1.1". */
data class SubConcepto(
    val titulo: String,
    val texto: String
)

