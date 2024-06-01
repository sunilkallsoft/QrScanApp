package com.allsoft.qrscanapp.views.auth.login

import android.os.Bundle
import android.text.TextPaint
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.allsoft.qrscanapp.R
import com.allsoft.qrscanapp.data.viewModel.LoginViewModel
import com.allsoft.qrscanapp.databinding.FragmentLoginBinding
import com.allsoft.qrscanapp.utils.AppPreferences
import com.allsoft.qrscanapp.utils.Resource
import com.allsoft.qrscanapp.utils.Status
import com.allsoft.qrscanapp.views.auth.viewmodel.AuthViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentLoginBinding
    private var authViewModel : AuthViewModel?=null
    private val loginViewModel : LoginViewModel by viewModel()

    private val appPreferences : AppPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setListener()
        setObserver()

//        authViewModel?.setStatusBar(hashMapOf())
    }

    private fun initViewModel(){
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
    }


    private fun setListener(){
        binding.getOTPBtn.setOnClickListener {
            binding.getOTPBtn.isEnabled = false
            binding.getOTPBtn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey))
            binding.getOTPBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.rounded_solid_grey_rectangle_bg)
            binding.btnClickProgress.visibility = View.VISIBLE
            if(validate()){
                loginViewModel.generateOTP(binding.mobileNoInput.text.toString())
                goToVerifyOTP()
            }
        }


        binding.mobileNoInput.doAfterTextChanged {
            if(binding.mobileNoInput.text.toString().length == 10){
                binding.getOTPBtn.isEnabled = true
                binding.getOTPBtn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorSecondary))
                binding.getOTPBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.rounded_solid_color_primary_bg)
            }
            else{
                binding.getOTPBtn.isEnabled = false
                binding.getOTPBtn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey))
                binding.getOTPBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.rounded_solid_grey_rectangle_bg)
            }
        }

    }


    private fun validate() : Boolean{
        var valid = true
        if(binding.mobileNoInput.text.toString().isEmpty()){
            binding.mobileNoInput.error = getString(R.string.please_type_your_mobile_no)
            valid = false
        }
        else if(binding.mobileNoInput.text.toString().length != 10){
            binding.mobileNoInput.error = getString(R.string.please_type_your_mobile_no)
            valid = false
        }
        else{
            binding.mobileNoInput.error = null
        }
        return valid

    }

    private fun goToVerifyOTP(){
        loginViewModel.generateOTPLiveData.postValue(Resource.success(null))
        val mapData = HashMap<String, Any>()
        mapData["mobile_number"] = binding.mobileNoInput.text.toString()
        authViewModel?.setOTPFragment(mapData)

    }

    private fun setObserver(){
        loginViewModel.generateOTPLiveData.observe (viewLifecycleOwner) {
            when(it.status){
                Status.SUCCESS -> {
                    val response = it.data
                    response?.let {res->
                        binding.getOTPBtn.isEnabled = true
                        binding.getOTPBtn.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorSecondary))
                        binding.getOTPBtn.background = ContextCompat.getDrawable(requireActivity(), R.drawable.rounded_solid_red_bg)
                        binding.btnClickProgress.visibility = View.GONE
//                        authViewModel?.setLoadingDialog(false)
                        if(res.status){
                            goToVerifyOTP()
                        }
                        else{
                            Toast.makeText(requireActivity(), res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
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



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}