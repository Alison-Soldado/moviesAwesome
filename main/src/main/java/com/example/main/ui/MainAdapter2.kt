package com.example.main.ui

import android.content.Context
import android.view.View
import com.example.main.BaseRecyclerAdapter
import com.example.main.ViewBinder

open class MainAdapter2<MODEL, VIEW>(creator: (context: Context) -> VIEW) :
    BaseRecyclerAdapter<MODEL>({ context, _ -> creator.invoke(context) })
        where VIEW : View, VIEW : ViewBinder<MODEL>
