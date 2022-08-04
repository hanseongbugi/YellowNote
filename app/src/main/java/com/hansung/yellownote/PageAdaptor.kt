package com.hansung.yellownote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.pdfrenderer.PageHolder
import com.example.pdfrenderer.PdfReader

class PageAdaptor: RecyclerView.Adapter<PageHolder>() {

    private var pdfReader: PdfReader? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
//        System.out.println(parent.context)
//        System.out.println(PageHolder(view))
        return PageHolder(view)
    }

    fun setupPdfRenderer(pdfReader: PdfReader) {
        this.pdfReader = pdfReader
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return pdfReader?.pageCount ?: 0
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        pdfReader?.let {
            holder.openPage(position, it)
        }
    }
}