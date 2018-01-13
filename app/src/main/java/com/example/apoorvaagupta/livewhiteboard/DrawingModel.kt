package com.example.apoorvaagupta.livewhiteboard

/**
 * Created by apoorvaagupta on 13/01/18.
 */

data class DrawingModel (
        val id: Int,
        val name: String,
        val drawing: ByteArray
);