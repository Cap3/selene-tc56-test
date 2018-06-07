package de.cap3.selenetc56test

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.symbol.emdk.EMDKManager
import com.symbol.emdk.EMDKResults
import com.symbol.emdk.ProfileManager

class EmdkConfigurator(val context: AppCompatActivity) : EMDKManager.EMDKListener {

    fun startEmdkConfiguration() {
        EMDKManager.getEMDKManager(context, this)
    }

    fun setEmdkProfile(profileManager: ProfileManager, releaseManager: () -> Unit?) {

        Log.d(TAG, "EMDK ProfileManager opened")
        try {

            val modifyData = arrayOfNulls<String>(1)
            //Create the Profile Config object

            profileManager.addDataListener { resultData ->

                Log.d(TAG, "EMDK onData")
                Log.d(TAG, "EMDK profileName ${resultData.profileName}")
                Log.d(TAG, "EMDK status string ${resultData.result.statusString}")

                if (resultData.profileFlag == ProfileManager.PROFILE_FLAG.GET) {
                    profileManager.processProfileAsync("EmergencyButton", ProfileManager.PROFILE_FLAG.SET, modifyData)
                }
                if (resultData.profileFlag == ProfileManager.PROFILE_FLAG.CHECK_COMPATIBILITY) {
                    profileManager.processProfileAsync("EmergencyButton", ProfileManager.PROFILE_FLAG.GET, modifyData)
                }
                if (resultData.profileFlag == ProfileManager.PROFILE_FLAG.SET) {
                    releaseManager()
                }

                Log.d(TAG, "EMDK status code ${resultData.result.statusCode} extended: ${resultData.result.extendedStatusCode}")
                Log.d(TAG, "EMDK profileFlag ${resultData.profileFlag}")
                Log.d(TAG, "EMDK profileString ${resultData.profileString}")

                if (resultData.result.statusCode === EMDKResults.STATUS_CODE.FAILURE) {
                    Log.e(TAG, "EMDK failed to process profile")
                    //Failed to set profile
                } else if (resultData.result.statusCode === EMDKResults.STATUS_CODE.SUCCESS) {
                    Log.d(TAG, "EMDK profile successfully read")
                } else {
                    Log.e(TAG, "EMDK some other emdk profile read result status code ${resultData.result.statusCode}")
                }
            }

            profileManager.processProfileAsync("EmergencyButton", ProfileManager.PROFILE_FLAG.GET, modifyData)

        } catch (ex: Exception) {
            // Handle any exception
        }
    }

    override fun onOpened(emdkManager: EMDKManager?) {
        Log.d(TAG, "EmdkManger opened, next feature instance")

        // use values() to iterate over enum, because code and runtime values are not same
        EMDKManager.FEATURE_TYPE.values()
                .filter { feature -> feature.name == "PROFILE" }
                .forEach { feature ->
                    Log.d(TAG, "EMDK feature enum name: ${feature.name}")

                    val releaseManager = {
                        emdkManager?.release()
                    }

                    emdkManager?.getInstanceAsync(feature, ({ statusData, emdkBase ->
                        Log.d(TAG, "EMDK onStatus feature ${feature.name}: resultName ${statusData.result.name}")

                        if (emdkBase is ProfileManager) {
                            setEmdkProfile(emdkBase, releaseManager)
                        } else {
                            if (emdkBase == null) {
                                Snackbar.make(context.findViewById(R.id.baseLayout), "ProfileManager is null", Snackbar.LENGTH_LONG)
                                Log.e(TAG, "emdkBase is null feature ${feature.name} not working")
                            }
                            releaseManager()
                        }
                    }))
                }


    }

    override fun onClosed() {
        Log.d(TAG, "EmdkManager closed")
    }

    companion object {
        private val TAG = EmdkConfigurator::class.java.name
    }
}
