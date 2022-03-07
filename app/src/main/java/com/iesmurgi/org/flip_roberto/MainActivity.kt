package com.iesmurgi.org.flip_roberto

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var sbEjeX : SeekBar
    private lateinit var sbEjeY : SeekBar
    private lateinit var sbTramas : SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tvEjeX : TextView =  findViewById(R.id.tvEjeX)
        var tvEjeY : TextView =  findViewById(R.id.tvEjeY)
        var tvTramas : TextView =  findViewById(R.id.tvTramas)

        sbEjeX = findViewById(R.id.sbEjeX)
        sbEjeY = findViewById(R.id.sbEjeY)
        sbTramas = findViewById(R.id.sbTramas)

        sbEjeX.setOnSeekBarChangeListener( object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                tvEjeX.text = getString(R.string.progreso_ejex)+i
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"start tracking", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })

        sbEjeY.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                tvEjeY.text = getString(R.string.progreso_ejey)+i
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"start tracking", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })
        sbTramas.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                tvTramas.text = getString(R.string.trama)+i
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"start tracking", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Toast.makeText(applicationContext,"stop tracking",Toast.LENGTH_SHORT).show()
            }
        })

        val btn : Button = findViewById(R.id.btn)
        btn.setOnClickListener { startPlay() }

    }

    private fun startPlay(){

        var cbSonido = findViewById<CheckBox>(R.id.cbSonido)
        var cbVibracion = findViewById<CheckBox>(R.id.cbVibracion)
        var rbColores = findViewById<RadioButton>(R.id.rbColores)
        var rbNumeros = findViewById<RadioButton>(R.id.rbNumeros)

        var enviar = Intent(this, GameField::class.java)
        enviar.putExtra("X", sbEjeX.progress)
        enviar.putExtra("Y", sbEjeY.progress)
        enviar.putExtra("Tramas", sbTramas.progress)
        enviar.putExtra("Sonido", cbSonido.isChecked)
        enviar.putExtra("Vibración", cbVibracion.isChecked)
        enviar.putExtra("Colores", rbColores.isChecked)
        enviar.putExtra("Números", rbNumeros.isChecked)

        startActivity(enviar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = this.menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.acercaDe -> {
                mostrarAbout()
            }
            R.id.conf -> {
                mostrarPlayer()
            }
            R.id.reglas -> {
                mostrarHowTo()
            }
        }
        return true
    }

    private fun mostrarPlayer() {
    }

    private fun mostrarHowTo() {
        val intent = Intent(this, HowTo::class.java)
        startActivity(intent)
    }

    private fun mostrarAbout() {
        val intent = Intent(this, About::class.java)
        startActivity(intent)
    }
}
