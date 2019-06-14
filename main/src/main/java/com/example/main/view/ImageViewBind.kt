package com.example.main.view

interface ImageViewBind<in T> {

    fun bind(model: T)
}