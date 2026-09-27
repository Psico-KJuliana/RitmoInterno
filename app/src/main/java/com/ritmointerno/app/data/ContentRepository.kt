package com.ritmointerno.app.data

/**
 * Fuente de datos de la app. Por ahora es una lista fija en memoria
 * (contenido de ejemplo/placeholder) porque el contenido real todavía no
 * está definido. Cuando se tenga el contenido final, solo hay que
 * reemplazar los textos de abajo — no hay que tocar el resto de la app.
 *
 * Cada ítem necesita un id único (ej. "concepto_1", "tip_3") porque ese id
 * es el que se guarda en Favoritos.
 */
object ContentRepository {

    val conceptos = listOf(
        // Ejemplo CON subconcepto: la lista subconceptos es opcional, así
        // que un concepto puede traerla o no.
        ContentItem(
            id = "concepto_1",
            type = ContentType.CONCEPTO,
            titulo = "El baile como salud emocional",
            descripcion = "El baile no solo es una actividad recreativa, también tiene beneficios en la salud emocional, física y mental. La aplicación “Ritmo Interno” busca transmitir estos conceptos de manera sencilla y práctica, ofreciendo información y consejos que cualquier persona pueda aplicar en su vida diaria.",
            subconceptos = listOf(
                SubConcepto(
                    titulo = "1- Estabilizar nuestras emociones",
                    texto = "Bailar funciona como un estimulador natural para la liberación de serotonina, endorfinas y dopamina (los neurotransmisores de la felicidad). Esta actividad disminuye notablemente la cantidad de cortisol en sangre, lo que contribuye a aliviar síntomas de ansiedad y depresión y fomentar un estado de ánimo positivo y constante."
                )
            )
        ),
        ContentItem(
            id = "concepto_2",
            type = ContentType.CONCEPTO,
            titulo = "El baile como forma de expresión",
            descripcion = "Mediante el baile y la expresividad del cuerpo, las personas pueden comunicar sentimientos reprimidos o difíciles de expresar. El movimiento permite exteriorizar estas emociones y facilita su liberación cuando resulta complicado expresarlas mediante palabras.",
            subconceptos = listOf(
                SubConcepto(
                    titulo = "1- Respuesta Corporal Intuitiva",
                    texto = "Seguir el ritmo y escuchar la música tienen un efecto beneficioso para despejar la cabeza. 'Simplemente debes concentrarte en los instrumentos y permitir que el cuerpo responda de manera natural.'"
                ),
                SubConcepto(
                    titulo = "2- Conecta Bailando: Adiós al Aislamiento",
                    texto = "Bailar con otras personas contribuye a la pérdida de vergüenza , fomenta la confianza , produce momentos de diversión y socialización que hacen que te sientas incluido en un grupo, dejando atrás el aislamiento."
                )
            )
        ),


        // Ejemplo SIN subconcepto: la lista subconceptos es opcional
        ContentItem(
            id = "concepto_3",
            type = ContentType.CONCEPTO,
            titulo = "Amor propio",
            descripcion = "al bailar, notas de lo que es capaz de hacer tu cuerpo, cada movimiento que logras refuerza tu confianza y te ayuda a mejorar tu autoestima.",
        )
    )

    val tips = listOf(
        ContentItem(
            id = "tip_1",
            type = ContentType.TIP,
            titulo = "Tip 1",
            descripcion = "No necesitas saber bailar o haber tomado clases de baile, lo importante es disfrutar de los movimientos y la música."
        ),
        ContentItem(
            id = "tip_2",
            type = ContentType.TIP,
            titulo = "Tip 2",
            descripcion = "Cuando estés estresado baila para relajarte."
        ),
        ContentItem(
            id = "tip_3",
            type = ContentType.TIP,
            titulo = "Tip 3",
            descripcion = "reserva en tu día un momento para bailar te sirve para tu estado físico  y metal."
        ),
        ContentItem(
            id = "tip_4",
            type = ContentType.TIP,
            titulo = "Tip 4",
            descripcion = "puedes explorar distintos ritmos,y descubrir cuál es el que más te gusta."
        ),
        ContentItem(
            id = "tip_5",
            type = ContentType.TIP,
            titulo = "Tip 5",
            descripcion = "ponte una meta aprende nuevos pasos, permítete equivocarte y aprender."
        )
    )

    val videos = listOf(
        ContentItem(
            id = "video_1",
            type = ContentType.VIDEO,
            titulo = "The Mental Health Benefits of Dancing",
            descripcion = "Un vistazo científico y práctico a cómo el baile ayuda a combatir el estrés y la ansiedad. A través de la combinación de música y movimiento, esta pieza explica los mecanismos hormonales que reducen el cortisol y liberan las tensiones diarias.",
            autor = "Psicoactiva - Psico. Marc Rodriguez.",
            videoUrl = "https://youtu.be/HPqpXH1L_5w?si=1tr-YvQhuhJjnXKs"
        ),
        ContentItem(
            id = "video_2",
            type = ContentType.VIDEO,
            titulo = "Bailar para sanar: beneficios de la danza en la salud mental",
            descripcion = "Una enriquecedora conversación sobre el impacto cognitivo, emocional y psicológico de la danza. Descubre cómo el movimiento rítmico se convierte en una herramienta cultural y educativa capaz de canalizar el estrés y expresar aquello que las palabras no pueden.",
            autor = "Un Podcast para EmocionarSe - Arancha Clares y Ana Pastor",
            videoUrl = "https://www.youtube.com/watch?v=lBRWFHERFs8"
        )
    )

    val recursos = listOf(
        ContentItem(
            id = "recurso_1",
            type = ContentType.RECURSO,
            titulo = "Revisión Sistemática de la Danza (Roca-Amat & García-Alandete, 2024)",
            descripcion = "Artículo, Explica detalladamente cómo la práctica del baile tiene un impacto positivo a corto y largo plazo reduciendo la ansiedad y mejorando el bienestar independientemente de la edad o el tipo de danza.",
            linkUrl = "https://scielo.isciii.es/scielo.php?script=sci_arttext&pid=S1989-38092024000100003"
        ),
        ContentItem(
            id = "recurso_2",
            type = ContentType.RECURSO,
            titulo = "Duración del Impacto Emocional (Alfredsson-Olsson & Heikkinen, 2019)",
            descripcion = "Artículo, Demuestra que los efectos positivos de una sola sesión de baile duran hasta una semana en el estado de ánimo.",
            linkUrl = "https://pubmed.ncbi.nlm.nih.gov/31761092/"
        ),
        ContentItem(
            id = "recurso_3",
            type = ContentType.VIDEO,
            titulo = "Así te cura el baile",
            descripcion = "El video expone cómo el baile genera efectos medibles en el cerebro y en el cuerpo. Se explica por qué el movimiento rítmico es relevante para comprender la coordinación entre percepción, motricidad y regulación fisiológica.",
            autor = "Neurociencia Sin Filtro.",
            videoUrl = "https://www.youtube.com/watch?v=J8MggxcypGc"
        ),
        ContentItem(
            id = "recurso_4",
            type = ContentType.RECURSO,
            titulo = "Eficacia en Ansiedad y Depresión (Koch et al., 2019)",
            descripcion = "Metaanálisis de 41 estudios clínicos que respalda el baile guiado como tratamiento complementario.",
            linkUrl = "https://pubmed.ncbi.nlm.nih.gov/31481910/"
        ),
        ContentItem(
            id = "recurso_5",
            type = ContentType.VIDEO,
            titulo = "Cinco ritmos para liberar la mente",
            descripcion = "Estos pasos de baile le ayudarán a liberar estrés y meditar en movimiento.",
            autor = "Pulzo",
            videoUrl = "https://www.youtube.com/watch?v=NrWzYYfJzeQ"
        ),
        ContentItem(
            id = "recurso_6",
            type = ContentType.RECURSO,
            titulo = "Modulación Química Cerebral (Jeong et al., 2005)",
            descripcion = "Ensayo clínico que prueba cómo el baile altera los neurotransmisores a nivel biológico.",
            linkUrl = "https://pubmed.ncbi.nlm.nih.gov/16287635/"
        ),
    )

    fun listaPara(type: ContentType): List<ContentItem> = when (type) {
        ContentType.CONCEPTO -> conceptos
        ContentType.TIP -> tips
        ContentType.VIDEO -> videos
        ContentType.RECURSO -> recursos
    }

    /** Todos los ítems de las 4 secciones juntos, usado por Favoritos. */
    fun todos(): List<ContentItem> = conceptos + tips + videos + recursos
}