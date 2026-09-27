package com.ritmointerno.app

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ritmointerno.app.data.SessionManager
import com.ritmointerno.app.databinding.ActivitySplashBinding
import com.ritmointerno.app.ui.auth.LoginActivity

/**
 * Pantalla de bienvenida: logo + frase motivadora. Es la puerta de entrada
 * (Launcher) definida en el documento de diseño. Espera un momento y decide
 * a dónde ir según si hay una sesión activa: a MainActivity si ya inició
 * sesión antes, o a LoginActivity si no.
 *
 * Mientras espera, anima el logo (gira como una moneda sobre su eje
 * vertical) y unas estrellas que caen destellando desde arriba.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sessionManager = SessionManager(this)

        animarLogo(binding.imageLogo)
        animarEstrellas(
            listOf(
                binding.starOne to 0L,
                binding.starTwo to 250L,
                binding.starThree to 500L,
                binding.starFour to 150L,
                binding.starFive to 400L
            )
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val destino = if (sessionManager.haySesionActiva()) {
                MainActivity::class.java
            } else {
                LoginActivity::class.java
            }
            startActivity(Intent(this, destino))
            finish()
        }, DURACION_SPLASH_MS)
    }

    /**
     * Efecto "moneda girando": rota el logo sobre su eje vertical (rotationY)
     * de forma continua mientras dura el splash. Con cameraDistance alto se
     * ve como un giro 3D en vez de un simple aplastamiento.
     */
    private fun animarLogo(logo: ImageView) {
        val escala = resources.displayMetrics.density
        logo.cameraDistance = 8000 * escala

        ObjectAnimator.ofFloat(logo, "rotationY", 0f, 360f).apply {
            duration = 3000L
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }
    }

    /**
     * Cada estrella cae (translationY) desde arriba de la pantalla hacia
     * abajo mientras destella (alpha subiendo y bajando en bucle). El
     * startDelay de cada una las desfasa para que no caigan todas juntas.
     */
    private fun animarEstrellas(estrellas: List<Pair<ImageView, Long>>) {
        val alturaCaidaPx = resources.displayMetrics.heightPixels.toFloat()

        estrellas.forEach { (estrella, retraso) ->
            estrella.translationY = -120f
            estrella.alpha = 0f

            val caida = ObjectAnimator.ofFloat(
                estrella, "translationY", -120f, alturaCaidaPx
            ).apply {
                duration = DURACION_SPLASH_MS + 2000L
                interpolator = LinearInterpolator()
            }

            val destello = ObjectAnimator.ofFloat(estrella, "alpha", 0f, 1f, 0f).apply {
                duration = 500L
                repeatCount = ObjectAnimator.INFINITE
            }

            AnimatorSet().apply {
                playTogether(caida, destello)
                startDelay = retraso
                start()
            }
        }
    }

    companion object {
        private const val DURACION_SPLASH_MS = 2200L
    }
}