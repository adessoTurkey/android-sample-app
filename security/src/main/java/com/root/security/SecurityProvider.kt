package com.root.security

/**
 * @author haci
 * @version 0.0.1
 * @since 0.0.1
 * Adesso Security Module.
 *
 * created on 19.06.2021
 */
interface SecurityProvider {

    /**
     * Sync play store https provider check
     */
    fun update()

    /**
     * Async play store https provider check
     */
    fun updateAsync(callback: ProviderCallback?)

    interface ProviderCallback {
        /**
         * Called when installing the provider succeeds.
         * This method is always called on the UI thread.
         */
        fun onSuccess()
        /**
         * Called when installing the provider fails. This method is always called on the UI thread.
         * Implementers may use errorCode with the standard UI elements provided by GoogleApiAvailability;
         * or recoveryIntent to implement custom UI.
         */
        fun onFail()
    }
}
