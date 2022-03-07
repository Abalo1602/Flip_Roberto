package com.iesmurgi.org.flip_roberto

import android.content.Context

class CeldaView (context:Context?=null, x:Int=0, y:Int=0, topElementos:Int=0, index:Int=0, background:Int=0) : androidx.appcompat.widget.AppCompatButton(context!!){
    var x=0
    var y=0
    private var index=0
    private var topElementos=0


     init{
        this.x=x
        this.y=y
        this.index=index
        this.topElementos=topElementos
        this.setBackgroundResource(background)
    }

    fun getNewIndex():Int{
        index++
        if (index==topElementos){
            index=0
        }
        return index
    }
}