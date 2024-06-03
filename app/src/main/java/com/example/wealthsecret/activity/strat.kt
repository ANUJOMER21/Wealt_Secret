package com.example.wealthsecret.activity

import android.graphics.pdf.PdfRenderer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.wealthsecret.R



class strat : AppCompatActivity() {
    private lateinit var pdfRenderer: PdfRenderer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_strat)
        val webView: WebView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        // Load PDF file from assets
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // JavaScript to fit the PDF content to the WebView
                view?.loadUrl("javascript:(function() { " +
                        "document.querySelector('[role=\"toolbar\"]').remove(); " +
                        "})()")
            }
        }
        webView.loadUrl("file:///android_asset/technical.pdf")
    }
}