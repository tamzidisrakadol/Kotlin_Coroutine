import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CheckNetworkConnectivity(private val connectivityManager: ConnectivityManager) {

    constructor(application: Application) : this(
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    )

    val networkStatusFlow: Flow<Boolean> = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true) // Emit true when network is available
            }

            override fun onLost(network: Network) {
                trySend(false) // Emit false when network is lost
            }
        }

        // Register network callback
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)

        // Ensure proper cleanup when flow is closed
        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }
}