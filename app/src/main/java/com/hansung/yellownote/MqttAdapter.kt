package com.hansung.yellownote

import android.util.Log
import com.hansung.yellownote.drawing.PdfActivity
import kotlinx.coroutines.*
import org.eclipse.paho.client.mqttv3.*
import java.io.Serializable

class MqttAdapter(var pdfActivity: PdfActivity) {
    private lateinit var client: MqttClient
    private lateinit var scope: CoroutineScope
    private lateinit var word:String
    init {
        connectClient()
    }
    private fun connectClient(){
        scope= CoroutineScope(Dispatchers.IO).apply {
            launch {
                try {
                    client =
                        MqttClient("tcp://223.194.134.124:1883", MqttClient.generateClientId(), null)
                    if (!client.isConnected) {
                        println("연결")
                        withContext(Dispatchers.Main) {
                            client.connect()
                        }
                    }
                    client.subscribe("result")
                    client.setCallback(object : MqttCallback {
                        override fun connectionLost(cause: Throwable?) {
                            Log.d("MqttService", "Connection Lost")
                        }

                        override fun messageArrived(topic: String?, message: MqttMessage?) {
                            if (topic == "result") {
                                val msg=message
                                word=msg.toString()
                                pdfActivity.drawingView.handWritetoText(word)
                            }
                        }

                        override fun deliveryComplete(token: IMqttDeliveryToken?) {
                            Log.d("MqttService", "Delivery Complete")
                        }
                    })
                } catch (e: MqttException) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun sendHangulPixelMessage(drawings:ByteArray){
        scope= CoroutineScope(Dispatchers.IO).apply {
            launch {
                try {
                    println(client.isConnected)
                    if (!client.isConnected) {
                        withContext(Dispatchers.Main) {
                            client.connect()
                        }
                    }

                    client.publish("easyocr/hangul", MqttMessage(drawings))

                } catch (e: MqttException) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun sendEnglishPixelMessage(drawings:ByteArray){
        scope= CoroutineScope(Dispatchers.IO).apply {
            launch {
                try {
                    if (!client.isConnected) {
                        withContext(Dispatchers.Main) {
                            client.connect()
                        }
                    }

                    client.publish("easyocr/english", MqttMessage(drawings))

                } catch (e: MqttException) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun sendNumberPixelMessage(drawings:ByteArray){
        scope= CoroutineScope(Dispatchers.IO).apply {
            launch {
                try {
                    if (!client.isConnected) {
                        withContext(Dispatchers.Main) {
                            client.connect()
                        }
                    }

                    client.publish("easyocr/number", MqttMessage(drawings))

                } catch (e: MqttException) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun sendImageSizeMessage(width:Int){
        scope= CoroutineScope(Dispatchers.IO).apply {
            launch {
                val message = width.toString()
                try {
                    if (!client.isConnected) {
                        withContext(Dispatchers.Main) {
                            client.connect()
                        }
                    }
                    client.publish("imagesize", MqttMessage(message.toByteArray()))
                }catch (e: MqttException) {
                    e.printStackTrace()
                }
            }
        }
    }

}