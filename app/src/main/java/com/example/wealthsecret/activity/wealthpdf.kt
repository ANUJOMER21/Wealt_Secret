package com.example.wealthsecret.activity


import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.wealthsecret.R

import java.io.File
import java.io.IOException


class wealthpdf : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wealthpdf)
//        val pdfView:PDFView=findViewById(R.id.pdfView)
//        pdfView.fromAsset("wealth.pdf")

        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        // Load PDF file from assets
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.loadUrl(
                    "javascript:(function() { " +
                            "document.querySelector('[role=\"toolbar\"]').remove(); " +
                            "})()"
                )
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/pdf_viewer.html")

    }
}


