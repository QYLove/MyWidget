package com.sun.mywidget.bean

data class PieBean(
    val name:String,
    val start:Float,
    val sweep:Float,
    val height:Int,
    val color:Int,
    val centerX:Float? =0f,
    val centerY:Float?=0f,
)
