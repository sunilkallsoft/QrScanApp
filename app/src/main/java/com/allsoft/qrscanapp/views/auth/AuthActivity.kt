package com.allsoft.qrscanapp.views.auth

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.allsoft.qrscanapp.R
import com.allsoft.qrscanapp.databinding.ActivityAuthBinding
import com.allsoft.qrscanapp.databinding.DialogRegistrationSuccessBinding
import com.allsoft.qrscanapp.utils.AppPreferences
import com.allsoft.qrscanapp.views.auth.login.LoginFragment
import com.allsoft.qrscanapp.views.auth.otp.OTPFragment
import com.allsoft.qrscanapp.views.auth.registration.RegistrationFragment
import com.allsoft.qrscanapp.views.auth.viewmodel.AuthViewModel
import com.allsoft.qrscanapp.views.dashboard.MainActivity
import org.koin.android.ext.android.inject

class AuthActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAuthBinding

    private var authViewModel : AuthViewModel?= null
    private val appPreferences : AppPreferences by inject()

//    private val loginViewModel : LoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        binding = ActivityAuthBinding.inflate(layoutInflater)
//        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(binding.root)

        initViewModel()
        setObserver()

        authViewModel?.setLoginFragment(hashMapOf())
    }

    private fun initViewModel(){
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    private fun setObserver(){
        authViewModel?.getBackPressed()?.observe(this){
            onBackPressedDispatcher.onBackPressed()
        }

        authViewModel?.getLoginFragment()?.observe(this){
            addFullFragment(LoginFragment.newInstance("", ""), LoginFragment::class.simpleName!!)
        }

        authViewModel?.getRegistrationFragment()?.observe(this){
            var mobileNo = ""
            if(it.containsKey("mobile_number")){
                mobileNo = it["mobile_number"].toString()
            }
            addFullFragment(RegistrationFragment.newInstance(mobileNo, ""), RegistrationFragment::class.simpleName!!)
        }

        authViewModel?.getOTPFragment()?.observe(this){
            if(it.containsKey("mobile_number")){
                addFullFragment(OTPFragment.newInstance(it["mobile_number"].toString(), ""), OTPFragment::class.simpleName!!)
            }
        }

        authViewModel?.getRegistrationSuccess()?.observe(this){
            registrationSuccessDialog()
        }
    }

    private fun addFullFragment(fragment: Fragment, tag: String){

        val transaction = supportFragmentManager.beginTransaction()
        if(tag != LoginFragment::class.simpleName) setFragmentTransactionAnimation(transaction, tag)
        transaction.setReorderingAllowed(true)
        transaction.add(R.id.fullFrameLayout, fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()

    }

    private fun setFragmentTransactionAnimation(transaction: FragmentTransaction, tag: String) {
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return if(event?.keyCode == KeyEvent.ACTION_UP || keyCode == KeyEvent.KEYCODE_BACK){

            handleBackEvent()

            true
        }
        else super.onKeyUp(keyCode, event)
    }

    override fun onBackPressed() {
        handleBackEvent()
        super.onBackPressed()
    }

    private fun handleBackEvent(){
        if(getVisibleFragmentTag() == LoginFragment::class.simpleName){
            finish()
        }
        else{
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU){
                if (supportFragmentManager.backStackEntryCount > 1)
                    supportFragmentManager.popBackStack()
                else
                    onBackPressedDispatcher.onBackPressed()
            }
            else
                supportFragmentManager.popBackStack()
        }

//        authViewModel?.setStatusBar(hashMapOf())
    }

    private fun getVisibleFragmentTag(): String {
        val fragments = supportFragmentManager.fragments
        var tag = ""
        fragments.forEach {
            if (it != null && it.isVisible) {
                Log.d("Visible Fragment", it::class.java.simpleName)
                tag = it::class.java.simpleName
            }
        }
        return tag
    }

    private fun registrationSuccessDialog(){
        val dialog = Dialog(this, R.style.AppDialogTheme)
        val dialogBinding = DialogRegistrationSuccessBinding.inflate(LayoutInflater.from(this), binding.root, false)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.continueBtn.setOnClickListener {
            dialog.dismiss()
            setToDashboardActivity()
        }

        dialog.show()
    }

    private fun setToDashboardActivity(){
        val intent = Intent(this@AuthActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}