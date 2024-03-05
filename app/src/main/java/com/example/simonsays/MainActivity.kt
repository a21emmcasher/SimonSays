package com.example.simonsays

import android.graphics.Color
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttons: Array<Button>
    private lateinit var sequence: MutableList<Int>
    private var playerSequenceIndex = 0
    private var score = 0
    private var isPlayerTurn = false
    private var level = 1
    private lateinit var soundPool: SoundPool
    private var soundIds = IntArray(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayOf(
            findViewById(R.id.yellowButton),
            findViewById(R.id.greenButton),
            findViewById(R.id.redButton),
            findViewById(R.id.blueButton)
        )

        for ((index, button) in buttons.withIndex()) {
            button.setOnClickListener(this)
            button.setBackgroundColor(Color.WHITE) // Establecer todos los botones en blanco
            button.tag = index // Asignar una etiqueta para identificar cada botón
        }

        sequence = mutableListOf()
        soundPool = SoundPool.Builder()
            .setMaxStreams(4) // Número máximo de sonidos simultáneos
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()

        // Cargar sonidos para cada botón
        soundIds[0] = soundPool.load(this, R.raw.yellow, 1)
        soundIds[1] = soundPool.load(this, R.raw.green, 1)
        soundIds[2] = soundPool.load(this, R.raw.red, 1)
        soundIds[3] = soundPool.load(this, R.raw.blue, 1)

        startGame()
    }

    private fun startGame() {
        sequence.clear()
        score = 0
        playerSequenceIndex = 0
        isPlayerTurn = false
        addToSequence()
        displaySequence()
    }

    private fun addToSequence() {
        val nextButtonIndex = (0 until buttons.size).random()
        sequence.add(nextButtonIndex)
        score++
        findViewById<TextView>(R.id.tvScore).text = "Score: $score"
    }

    private fun displaySequence() {
        val handler = Handler()
        var delay = 1000L
        for (i in 0 until level) {
            val buttonIndex = sequence[i]
            handler.postDelayed({
                highlightButton(buttons[buttonIndex])
                playSound(buttonIndex) // Reproducir sonido correspondiente al botón
            }, delay)
            delay += 1000L
        }
        handler.postDelayed({
            isPlayerTurn = true
        }, delay)
    }

    private fun highlightButton(button: Button) {
        val originalColor = button.backgroundTintList?.defaultColor
        button.setBackgroundColor(getColorForButton(button))
        Handler().postDelayed({
            button.setBackgroundColor(originalColor ?: Color.WHITE)
        }, 500)
    }

    private fun getColorForButton(button: Button): Int {
        return when (button.id) {
            R.id.yellowButton -> Color.YELLOW
            R.id.greenButton -> Color.GREEN
            R.id.redButton -> Color.RED
            R.id.blueButton -> Color.BLUE
            else -> Color.WHITE
        }
    }

    override fun onClick(view: View?) {
        if (isPlayerTurn) {
            val clickedButton = view as Button
            val buttonIndex = clickedButton.tag as Int
            val expectedButtonIndex = sequence[playerSequenceIndex]

            if (buttonIndex == expectedButtonIndex) {
                // Resaltar el botón cuando el usuario lo toca durante su turno
                highlightButton(clickedButton)
                playSound(buttonIndex) // Reproducir sonido correspondiente al botón
                playerSequenceIndex++
                if (playerSequenceIndex == level) {
                    playerSequenceIndex = 0
                    isPlayerTurn = false
                    level++
                    addToSequence()
                    displaySequence()
                }
            } else {
                Toast.makeText(this, "Game Over! Your score is $score", Toast.LENGTH_SHORT).show()
                startGame()
            }
        }
    }

    private fun playSound(buttonIndex: Int) {
        soundPool.play(soundIds[buttonIndex], 1.0f, 1.0f, 1, 0, 1.0f)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}
