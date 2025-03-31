package il.dee.fashionstore

import android.content.Context
import android.content.Intent

object GeneralFunctions {

    // Function to navigate to the pretended PaymentActivity (only a simulation)
    fun gotoPaymentActivity(context: Context) {
        val intent = Intent(context, PaymentActivity::class.java)
        context.startActivity(intent)
    }

}