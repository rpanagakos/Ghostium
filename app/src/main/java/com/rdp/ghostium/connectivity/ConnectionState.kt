package com.rdp.ghostium.connectivity

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable: ConnectionState()
}