package com.allsoft.qrscanapp.views.auth.otp

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.allsoft.qrscanapp.R
import com.allsoft.qrscanapp.data.viewModel.LoginViewModel
import com.allsoft.qrscanapp.databinding.FragmentOTPBinding
import com.allsoft.qrscanapp.utils.AppConstants
import com.allsoft.qrscanapp.utils.AppPreferences
import com.allsoft.qrscanapp.utils.Resource
import com.allsoft.qrscanapp.utils.Status
import com.allsoft.qrscanapp.views.auth.login.LoginFragment
import com.allsoft.qrscanapp.views.auth.viewmodel.AuthViewModel
import com.otpview.OTPListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OTPFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OTPFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentOTPBinding

    private var authViewModel : AuthViewModel?= null
    private val loginViewModel : LoginViewModel by viewModel()

    private val TAG = OTPFragment::class.simpleName

    private val appPreferences : AppPreferences by inject()

    private val COUNTDOWN_INTERVAL: Long = 1000 // 1 second

    private val COUNTDOWN_TIME: Long = 60000 // 30 seconds

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        handleBackPress()
    }

    override fun onResume() {
        super.onResume()
        handleBackPress()
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if(isEnabled) isEnabled = false

                if(isAdded){
                    if(requireActivity().supportFragmentManager.findFragmentByTag(LoginFragment::class.simpleName) != null){
                        requireActivity().supportFragmentManager.popBackStackImmediate()
                    }

//                    authViewModel?.setStatusBar(hashMapOf())
                }
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOTPBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setListener()
        initViewModel()
        setObserver()
        startCountdown()

//        authViewModel?.setStatusBar(hashMapOf())

    }

    private fun initUI(){

        binding.otpView.requestFocusOTP()

        setOtpMsgTextView()

        binding.verifyOTPBtn.isEnabled = false

    }

    private fun initViewModel(){
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
    }

    private fun setListener(){
        binding.backBtn.setOnClickListener{
            authViewModel?.setBackPressed(true)
        }

        binding.enterYourOTPMsg.setOnClickListener{
            authViewModel?.setBackPressed(true)
        }

        binding.verifyOTPBtn.setOnClickListener {
            if(binding.otpView.otp.isNullOrBlank()){
                binding.inValidOtpAlert.visibility = View.VISIBLE
                binding.inValidOtpAlert.text = getString(R.string.please_enter_otp)
            }
            else{
                binding.verifyOTPBtn.isEnabled = false
                binding.verifyOTPBtn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey))
                binding.verifyOTPBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.rounded_solid_grey_rectangle_bg)
                binding.btnClickProgress.visibility = View.VISIBLE

                binding.inValidOtpAlert.visibility = View.GONE
//                loginViewModel.validateOTP(param1!!, binding.otpView.otp.toString())
                val mapData = hashMapOf<String, Any>()
                mapData["mobile_number"] = param1.toString()
                authViewModel?.setRegistrationFragment(mapData)
            }
        }

        binding.txtResendOtp.setOnClickListener {
            startCountdown()
            loginViewModel.generateOTP(param1.toString())
        }

        binding.otpView.otpListener = object : OTPListener{
            override fun onInteractionListener() {
                binding.inValidOtpAlert.visibility = View.GONE
                binding.verifyOTPBtn.isEnabled = false
                binding.verifyOTPBtn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey))
                binding.verifyOTPBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.rounded_solid_grey_rectangle_bg)
            }

            override fun onOTPComplete(otp: String) {

                binding.verifyOTPBtn.isEnabled = true
                binding.verifyOTPBtn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorSecondary))
                binding.verifyOTPBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.rounded_solid_red_bg)

            }

        }
    }

    private fun setObserver(){
        loginViewModel.generateOTPLiveData.observe(viewLifecycleOwner) {
            when(it.status){
                Status.SUCCESS -> {
                    val response = it.data
                    response?.let {
//                        authViewModel?.setLoadingDialog(false)
                    }
                    loginViewModel.generateOTPLiveData.postValue(Resource.success(null))
                }
                Status.LOADING -> {
//                    authViewModel?.setLoadingDialog(true)
                }
                Status.ERROR -> {
//                    authViewModel?.setLoadingDialog(false)
                }
            }
        }


        loginViewModel.validateOTPLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS -> {
                    val response = it.data
                    response?.let {res->
//                        authViewModel?.setLoadingDialog(false)

                        binding.verifyOTPBtn.isEnabled = true
                        binding.verifyOTPBtn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorSecondary))
                        binding.verifyOTPBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.rounded_solid_red_bg)
                        binding.btnClickProgress.visibility = View.GONE
                        if(res.status){
                            appPreferences.putString(AppConstants.KEY_ACCESS_TOKEN, res.token)
                            if(res.data != null){
//                                appPreferences.putString(AppConstants.PREF_KEY_USERNAME, res.data.userName)
//                                appPreferences.putString(AppConstants.PREF_KEY_MOBILE_NUMBER, res.data.mobileNumber)
//                                appPreferences.putString(AppConstants.PREF_KEY_EMAIL, res.data.email)
//                                appPreferences.putString(AppConstants.PREF_KEY_DATE_OF_BIRTH, res.data.dateOfBirth)


//                                authViewModel?.setDashboard(true)
                            }
                            else{
                                val mapData = hashMapOf<String, Any>()
                                mapData["mobile_number"] = param1.toString()
                                authViewModel?.setRegistrationFragment(mapData)
                            }
                        }
                        else{
                            binding.inValidOtpAlert.visibility = View.VISIBLE
                            binding.inValidOtpAlert.text = res.message
                        }



                    }

                    loginViewModel.validateOTPLiveData.postValue(Resource.success(null))

                }
                Status.LOADING -> {
//                    authViewModel?.setLoadingDialog(true)
                }
                Status.ERROR -> {
//                    authViewModel?.setLoadingDialog(false)
                }
            }
        }
    }

    private fun setOtpMsgTextView() {
        val maskedMobileNum = "XXXXX${param1?.substring(5)}"
        val otpMsg = "${getString(R.string.we_have_sent_a_6_digit_verification_code_to_your_mobile_number)} <b>${maskedMobileNum}</b>  "
        val otpMsgFormatted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            HtmlCompat.fromHtml(otpMsg, HtmlCompat.FROM_HTML_MODE_COMPACT)
        } else {
            HtmlCompat.fromHtml(otpMsg, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        Log.d(TAG, otpMsgFormatted.toString())
        val spannableString = SpannableString(otpMsgFormatted)
        val editIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_edit_24dp) // Replace ic_edit with your icon

        editIcon?.setBounds(0, 0, editIcon.intrinsicWidth, editIcon.intrinsicHeight)
        val imageSpan = ImageSpan(editIcon!!, ImageSpan.ALIGN_BASELINE)
        spannableString.setSpan(
            imageSpan,
            otpMsgFormatted.length-1,
            otpMsgFormatted.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )

        binding.enterYourOTPMsg.setText(spannableString, TextView.BufferType.SPANNABLE)

    }

    private fun startCountdown() {
        val htmlText = ContextCompat.getString(requireActivity(), R.string.didn_t_received_otp_font_color_ea3639_b_resend_otp_b_font)

        val formattedText = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_COMPACT)
        } else {
            HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        binding.txtResendOtp.text = formattedText

        binding.otpTimer.visibility = View.VISIBLE
        countDownTimer = object : CountDownTimer(COUNTDOWN_TIME, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / COUNTDOWN_INTERVAL
                if(secondsLeft != 0L){
                    binding.otpTimer.text = "00:$secondsLeft"
                }

            }

            override fun onFinish() {
                binding.otpTimer.visibility = View.GONE
            }
        }
        countDownTimer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OTPFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OTPFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}