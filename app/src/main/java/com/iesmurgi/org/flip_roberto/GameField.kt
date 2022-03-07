package com.iesmurgi.org.flip_roberto

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class GameField : AppCompatActivity() {
    var topTileX = 0
    var topTileY = 0
    var topElement = 0

    var sonido = false
    var vibracion = false
    var numeros = false
    var colores = false

    var Contador = 0

    lateinit var ids: Array<IntArray>
    lateinit var values: Array<IntArray>

    var imagenes = arrayListOf<Int>()
    lateinit var vibrator : Vibrator


    var imagenesColores = arrayListOf<Int>(
        R.drawable.ic_1c,
        R.drawable.ic_2c,
        R.drawable.ic_3c,
        R.drawable.ic_4c,
        R.drawable.ic_5c,
        R.drawable.ic_6c
    )

    var imagenesNumeros = arrayListOf<Int>(
        R.drawable.ic_1n,
        R.drawable.ic_2n,
        R.drawable.ic_3n,
        R.drawable.ic_4n,
        R.drawable.ic_5n,
        R.drawable.ic_6n
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.celdaview)

        var tablero = findViewById<LinearLayout>(R.id.tablero)

        val crono = findViewById<Chronometer>(R.id.crono)
        crono.start()


        var bundle = intent.extras

        topTileX = bundle?.getInt("X")!!
        topTileY = bundle?.getInt("Y")!!
        topElement = bundle?.getInt("Tramas")!!
        sonido = bundle?.getBoolean("Sonido")!!
        vibracion = bundle?.getBoolean("Vibración")!!
        numeros = bundle?.getBoolean("Números")
        colores = bundle?.getBoolean("Colores")

        ids = Array(topTileX!!) { IntArray(topTileY!!) }
        values = Array(topTileX!!) { IntArray(topTileY!!) }

        val dm : DisplayMetrics = resources.displayMetrics
        val height = (dm.heightPixels-600)/topTileY!!
        val weight = (dm.widthPixels/topTileX!!).toFloat()


        var btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener { onBackPressed() }

        if(colores){
            imagenes = imagenesColores
        }else if (numeros){
            imagenes = imagenesNumeros
        }


        fun miRandom (): Int {
            var randomValue = (0..topElement!!-1).random()
            return randomValue
        }

        var indent =0
        var celda = IntArray(topTileY!!)
        for(i:Int in 0..celda.size-1){
            var l2=LinearLayout(this)
            l2.orientation = LinearLayout.HORIZONTAL
            for(j:Int in 0..ids.size-1){
                val tramaToShow:Int=miRandom()
                values[j][i]=tramaToShow
                val tv= CeldaView(this, j,i,topElement!!,tramaToShow,imagenes[tramaToShow])
                indent++
                tv.id=indent

                ids[j][i]=indent
                tv.layoutParams=LinearLayout.LayoutParams(0,height,weight)

                tv.setOnClickListener(
                    object: View.OnClickListener{
                        override fun onClick(v:View?){
                            hasClick(j,i)
                        }
                    }
                )
                l2.addView(tv)
            }
            tablero.addView(l2)
        }
    }

    private fun hasClick(x: Int, y: Int) {
        var mediaPlayer : MediaPlayer = MediaPlayer.create(this, R.raw.touch)
        var textView:TextView = findViewById(R.id.textView)

        if (sonido) mediaPlayer.start()
        if (vibracion){
            vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
                    )
                } else {
                    vibrator.vibrate(100)
                }
            }
        }

        if(x==0 && y == 0){
            changeView(0,1)
            changeView(1,0)
            changeView(1,1)
        }else if(x==0 && y== topTileY!! -1){
            changeView(0, topTileY!! -2)
            changeView(1, topTileY!! -2)
            changeView(1, topTileY!! -1)
        }else if(x == topTileX!! -1 && y==0){
            changeView(topTileX!! -2,0)
            changeView(topTileX!! -2,1)
            changeView(topTileX!! -1,1)
        }else if(x== topTileX!! -1 && y== topTileY!! -1){
            changeView(topTileX!! -2, topTileY!! -1)
            changeView(topTileX!! -2, topTileY!! -2)
            changeView(topTileX!! -1, topTileY!! -2)
        }else if(x==0){
            changeView(x,y-1)
            changeView(x,y+1)
            changeView(x+1,y)
        }else if(y==0){
            changeView(x-1,y)
            changeView(x+1,y)
            changeView(x,y+1)
        }else if(x== topTileX!! -1){
            changeView(x,y-1)
            changeView(x,y+1)
            changeView(x-1,y)
        }else if(y == topTileY!! -1){
            changeView(x-1,y)
            changeView(x+1,y)
            changeView(x,y-1)
        }else{ //resto
            changeView(x-1,y)
            changeView(x+1,y)
            changeView(x,y-1)
            changeView(x,y+1)
        }

        Contador++
        textView.text = "Pulsaciones: $Contador"
        checkIfFinished()

    }

    private fun checkIfFinished() {
        var valor = values[0][0]
        var ganar = true
        for (i:Int in 0 until topTileY!!){
            for (j:Int in 0 until topTileX!!){
                if (values[j][i] != valor){
                    ganar = false
                }
            }
        }
        if (ganar){
            Toast.makeText(applicationContext, "¡¡¡Lo has conseguido!!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun changeView(x: Int, y: Int) {
        val tt : CeldaView = findViewById(ids[x][y])
        val newIndex = tt.getNewIndex()
        values[x][y]= newIndex
        tt.setBackgroundResource(imagenes[newIndex])
        tt.invalidate()
    }
}