package com.uae_barq.uaebarqtasks.kotlin.main

import android.app.AlertDialog
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.uae_barq.uaebarqtasks.R
import com.uae_barq.uaebarqtasks.kotlin.constants.BarqConstants
import kotlinx.android.synthetic.main.activity_dynamic_link.*

class DynamicLinkActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    private var appLinkData: Uri? = null

    /**
     * manage a handler.post(runnable) by creating an instance member for the handler and runnable,
     * then managing the handler in the Activity Lifecycle methods.
     */
    private var mHandlerShowProgressDlg: Handler? = null
    private val mRunnableShowProgressDlg: Runnable? = Runnable {
        //dismissing the dialog
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            if (appLinkData != null) {
                //then fetching the user data from parameters
                fetchUserProfileData(appLinkData!!)
            }
        }
    }

    //Butterknife also allows to unbind again, via the Unbinder object.
    private var unbinder: Unbinder? = null

    /**
     * Called firstly here as Activity Life Cycle
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_link)

        // Initialize ButterKnife
        //Before using any views, we need to inject ButterKnife
        unbinder = ButterKnife.bind(this)
    }

    /**
     * called after onCreate method as Activity Life Cycle
     * For accessing what i want to access
     *
     * @param savedInstanceState
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        //initialize the progress dialog
        initProgressDialog()

        //initialize handler
        initHandler()

        //Only for app links
        checkForAppDeepLink()

        //for firebase dynamic links
        checkForFirebaseDynamicLinks()
    }

    override fun onStop() {
        super.onStop()
        releaseAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseAll()
    }

    private fun releaseAll() {

        //releasing butterknife injection
        unbindButterKnife()

        //In case of activity finishes its lifecycle before the Handler executes the code.
        removeHandlerCallbacks()
    }

    /**
     * unbindButterKnife
     */
    private fun unbindButterKnife() {
        if (unbinder != null) unbinder!!.unbind()
    }

    /**
     * initialize the progress dialog
     */
    private fun initProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage(getString(R.string.fetching_user_data))
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
    }

    /**
     * checkForAppDeepLink
     */
    private fun checkForAppDeepLink() {
        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent = intent
        val appLinkAction = appLinkIntent.action


        //only for testing, please uncomment this below line for checking the dynamic link
        //appLinkData = createTestingDynamicLink();
//        Log.e(TAG, "checkForAppDeepLink: " + appLinkData.toString());

        //real when app is installed, the data will be filled from the clicked dynamic link
        appLinkData = appLinkIntent.data
        if (appLinkData != null) {
            showProgressBar(appLinkData!!)
        }
    }

    /**
     * createTestingDynamicLink
     *
     * @return
     */
    fun createTestingDynamicLink(): Uri {
        val builder = Uri.Builder()
        builder.scheme("https")
                .authority("example.com")
                .appendPath("appLinks")
                .appendQueryParameter(BarqConstants.USER_FIRST_NAME, "Ahmed")
                .appendQueryParameter(BarqConstants.USER_LAST_NAME, "Hegazi")
                .appendQueryParameter(BarqConstants.USER_AGE, "24")
                .appendQueryParameter(BarqConstants.USER_COUNTRY, "EG")
                .appendQueryParameter(BarqConstants.USER_PHONE_NUMBER, "01156749640")
        //                .fragment("section-name");
        return builder.build()
    }

    /**
     * Showing the progress bar as UX waiting for fetching data from appLink
     *
     * @param appLinkData
     */
    private fun showProgressBar(appLinkData: Uri) {

        //Showing the progress dialog
        progressDialog!!.show()

        //showing the progress dialog for 2 sec
        mHandlerShowProgressDlg!!.postDelayed(mRunnableShowProgressDlg, BarqConstants.MILLI)
    }

    /**
     * Fetching the user profile data from the intent through the QueryParameters.
     *
     * @param appLinkData
     */
    private fun fetchUserProfileData(appLinkData: Uri) {
        val firstName = appLinkData.getQueryParameter(BarqConstants.USER_FIRST_NAME)
        val lastName = appLinkData.getQueryParameter(BarqConstants.USER_LAST_NAME)
        val phoneNumber = appLinkData.getQueryParameter(BarqConstants.USER_PHONE_NUMBER)
        val country = appLinkData.getQueryParameter(BarqConstants.USER_COUNTRY)
        val age = appLinkData.getQueryParameter(BarqConstants.USER_AGE)
        //        String photo = appLinkData.getQueryParameter("photo");
        Log.i(TAG, "onCreate: firstName   $firstName")
        Log.i(TAG, "onCreate: lastName    $lastName")
        Log.i(TAG, "onCreate: phoneNumber $phoneNumber")
        Log.i(TAG, "onCreate: country     $country")
        Log.i(TAG, "onCreate: age         $age")
        //        Log.e(TAG, "onCreate: " + photo);
        if (!notNull(firstName)) {
            tvFirstName!!.text = firstName
        }
        if (!notNull(lastName)) {
            tvLastName!!.text = lastName
        }
        if (!notNull(phoneNumber)) {
            tvPhoneNumber!!.text = phoneNumber
        }
        if (!notNull(country)) {
            tvCountry!!.text = country
        }
        if (!notNull(age)) {
            tvAge!!.text = age
        }

        //fetching the param value
        val welcomeMessage = appLinkData.getQueryParameter(BarqConstants.WELCOME_MESSAGE)

        //sending the param value to alert dialog
        showWelcomeMessageAlertToUser(welcomeMessage)
    }

    /**
     * This method works but it tested in the Prev. mentioned email attached video for
     * the ToDoList app, as this method needs areal published app with firebase dynamic link integration.
     * Wow
     *
     * @param welcomeMessage
     */
    private fun showWelcomeMessageAlertToUser(welcomeMessage: String?) {
        val alertDialog = AlertDialog.Builder(this)
                .create()
        alertDialog.setTitle(getString(R.string.app_name))
        alertDialog.setMessage(welcomeMessage)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_okay)) { dialog, which -> dialog.dismiss() }
        alertDialog.show()
    }

    /**
     * Checking if the passed text not equals null
     *
     * @param firstName
     * @return
     */
    private fun notNull(firstName: String?): Boolean {
        return TextUtils.equals(firstName, null)
    }

    /**
     *
     */
    private fun checkForFirebaseDynamicLinks() {
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent)
                .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                    // Get deep link from result (may be null if no link is found)
                    Log.i(TAG, "onSuccess: Get deep link from result (may be null if no link is found)")
                    var deepLink: Uri? = null
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.link
                        if (deepLink != null) {
                            Log.i(TAG, "onSuccess: $deepLink")
                            fetchUserProfileData(deepLink)
                        }
                    }
                }
                .addOnFailureListener(this) { e: Exception ->
                    Log.e(TAG, "getDynamicLink: onFailure" + e.message)
                    Log.e(TAG, "getDynamicLink: onFailure" + e.localizedMessage)
                }
    }

    /**
     * In case of activity finishes its lifecycle before the Handler executes the code.
     */
    private fun removeHandlerCallbacks() {
        if (mHandlerShowProgressDlg != null && mRunnableShowProgressDlg != null) {
            mHandlerShowProgressDlg!!.removeCallbacks(mRunnableShowProgressDlg)
        }
    }

    /**
     * initHandler
     */
    private fun initHandler() {
        mHandlerShowProgressDlg = Handler()
    }

    companion object {
        private const val TAG = "DynamicLinkActivity"
    }
}