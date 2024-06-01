package com.allsoft.qrscanapp.views.auth.registration

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.allsoft.qrscanapp.R
import com.allsoft.qrscanapp.data.viewModel.MasterViewModel
import com.allsoft.qrscanapp.databinding.FragmentRegistrationBinding
import com.allsoft.qrscanapp.model.PlaceDataModel
import com.allsoft.qrscanapp.utils.AppPreferences
import com.allsoft.qrscanapp.utils.Status
import com.allsoft.qrscanapp.views.auth.login.LoginFragment
import com.allsoft.qrscanapp.views.auth.otp.OTPFragment
import com.allsoft.qrscanapp.views.auth.viewmodel.AuthViewModel
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentRegistrationBinding
    private var authViewModel : AuthViewModel?=null

    private val masterViewModel : MasterViewModel by viewModel()

    private val appPreferences : AppPreferences by inject()

    private var districtId = 0
    private var civicBodyID = 0
    private var wardID = 0
    private var blockID = 0
    private var panchayatID = 0
    private var villageID = 0
    private var urbanRuralChoice = "चुनें"


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
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setListener()
        setUI()

//        authViewModel?.setStatusBar(hashMapOf())


        setObserver()
    }

    private fun setUI(){
        binding.mobileNoInput.setText(param1.toString())

        val assemblyArr = resources.getStringArray(R.array.assembly_areas)
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, assemblyArr)
        binding.assemblyArea.threshold = 3
        binding.assemblyArea.setAdapter(adapter)

        binding.assemblyArea.setOnClickListener{
            binding.assemblyArea.showDropDown()
        }

    }

    private fun setListener() {
        binding.backBtn.setOnClickListener {
            authViewModel?.setBackPressed(true)
        }

        binding.district.setOnClickListener {
            masterViewModel.getDistrict()
        }

        val urbanRuralSelection = arrayListOf("चुनें", "शहरी", "ग्रामीण")
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, urbanRuralSelection)
        binding.urbanRural.threshold = 3
        binding.urbanRural.setAdapter(adapter)
        binding.urbanRural.setOnClickListener {
            binding.urbanRural.showDropDown()
        }
        binding.urbanRural.setOnItemClickListener { parent, view, position, id ->
            urbanRuralChoice = urbanRuralSelection[position]
            if(position != 0){
                if(position == 1){
                    masterViewModel.getCivicBodies(districtId)
                    binding.civicBodies.visibility = View.VISIBLE
                    binding.civicBodiesLabel.visibility = View.VISIBLE
                    binding.ward.visibility = View.VISIBLE
                    binding.wardLabel.visibility = View.VISIBLE

                    binding.block.visibility = View.GONE
                    binding.blockLabel.visibility = View.GONE
                    binding.panchayat.visibility = View.GONE
                    binding.panchayatLabel.visibility = View.GONE
                    binding.village.visibility = View.GONE
                    binding.villageLabel.visibility = View.GONE
                }
                else{
                    masterViewModel.getBlock(districtId)
                    binding.civicBodies.visibility = View.GONE
                    binding.civicBodiesLabel.visibility = View.GONE
                    binding.ward.visibility = View.GONE
                    binding.wardLabel.visibility = View.GONE

                    binding.block.visibility = View.VISIBLE
                    binding.blockLabel.visibility = View.VISIBLE
                    binding.panchayat.visibility = View.VISIBLE
                    binding.panchayatLabel.visibility = View.VISIBLE
                    binding.village.visibility = View.VISIBLE
                    binding.villageLabel.visibility = View.VISIBLE
                }
            }

        }

        binding.meetDateInput.setOnClickListener {
            setDatePickerDialog()
        }



        binding.registerBtn.setOnClickListener {
//            if(validate()){
//
//            }
            authViewModel?.setRegistrationSuccess(hashMapOf())
        }
    }


    private fun initViewModel(){
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
    }


    private fun validate() : Boolean{
        var valid = true

        if(binding.visitorWithNoInput.text.toString().isEmpty()){
            binding.visitorWithNoInput.error = ContextCompat.getString(requireActivity(), R.string.please_enter_no_of_persons_with_visitor)
            valid = false
        }
        else{
            binding.visitorWithNoInput.error = null
        }

        if(binding.urbanRural.text.toString().isEmpty()){
            binding.urbanRural.error = ContextCompat.getString(requireActivity(), R.string.please_select_urban_or_rural)
            valid = false
        }
        else{
            binding.urbanRural.error = null
        }


        if(binding.district.text.toString().isEmpty()){
            binding.district.error = ContextCompat.getString(requireActivity(), R.string.please_select_district)
            valid = false
        }
        else{
            binding.district.error = null
        }

        if(binding.assemblyArea.text.toString().isEmpty()){
            binding.assemblyArea.error = ContextCompat.getString(requireActivity(), R.string.please_select_assembly_area)
            valid = false
        }
        else{
            binding.assemblyArea.error = null
        }

        if(binding.meetDateInput.text.toString().isEmpty()){
            binding.meetDateInput.error = ContextCompat.getString(requireActivity(), R.string.please_select_meeting_date)
            valid = false
        }
        else{
            binding.meetDateInput.error = null
        }

        if(binding.objectiveInput.text.toString().isEmpty()){
            binding.objectiveInput.error = ContextCompat.getString(requireActivity(), R.string.please_select_meeting_objective)
            valid = false
        }
        else{
            binding.objectiveInput.error = null
        }

        if(binding.visitorsAgeInput.text.toString().isEmpty()){
            binding.visitorsAgeInput.error = ContextCompat.getString(requireActivity(), R.string.please_enter_age)
            valid = false
        }
        else{
            binding.visitorsAgeInput.error = null
        }


        if(binding.fathersNameInput.text.toString().isEmpty()){
            binding.fathersNameInput.error = ContextCompat.getString(requireActivity(), R.string.please_enter_fathers_name)
            valid = false
        }
        else{
            binding.fathersNameInput.error = null
        }

        if(binding.nameInput.text.toString().isEmpty()){
            binding.nameInput.error = ContextCompat.getString(requireActivity(), R.string.please_enter_your_name)
            valid = false
        }
        else{
            binding.nameInput.error = null
        }
        return valid

    }


    private fun setDatePickerDialog(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(requireActivity(), { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
            binding.meetDateInput.setText(selectedDate)
        }, year, month, day)
        val calForMaxDate = Calendar.getInstance()
        datePickerDialog.datePicker.minDate = calForMaxDate.time.time

        datePickerDialog.show()
    }


    private fun setObserver(){
        masterViewModel.districtLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS ->{
                    val response = it.data
                    response?.let {res->
                        if(res.status){
                            val districtList = ArrayList<PlaceDataModel>()
                            districtList.add(PlaceDataModel(0, "चुनें"))
                            districtList.addAll(res.data)
                            setDistrictAdapter(districtList)
                        }
                        else{
                            Toast.makeText(requireActivity(), res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Status.LOADING ->{

                }
                Status.ERROR ->{

                }
            }
        }

        masterViewModel.civicBodiesLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS ->{
                    val response = it.data
                    response?.let {res->
                        if(res.status){
                            val civicBodiesList = ArrayList<PlaceDataModel>()
                            civicBodiesList.add(PlaceDataModel(0, "चुनें"))
                            civicBodiesList.addAll(res.data)
                            setCivicBodiesAdapter(civicBodiesList)
                        }
                        else{
                            Toast.makeText(requireActivity(), res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Status.LOADING ->{

                }
                Status.ERROR ->{

                }
            }
        }

        masterViewModel.wardLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS ->{
                    val response = it.data
                    response?.let {res->
                        if(res.status){
                            val wardList = ArrayList<PlaceDataModel>()
                            wardList.add(PlaceDataModel(0, "चुनें"))
                            wardList.addAll(res.data)
                            setWardAdapter(wardList)
                        }
                        else{
                            Toast.makeText(requireActivity(), res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Status.LOADING ->{

                }
                Status.ERROR ->{

                }
            }
        }

        masterViewModel.blockLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS ->{
                    val response = it.data
                    response?.let {res->
                        if(res.status){
                            val blockList = ArrayList<PlaceDataModel>()
                            blockList.add(PlaceDataModel(0, "चुनें"))
                            blockList.addAll(res.data)
                            setBlockAdapter(blockList)
                        }
                        else{
                            Toast.makeText(requireActivity(), res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Status.LOADING ->{

                }
                Status.ERROR ->{

                }
            }
        }

        masterViewModel.gpLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS ->{
                    val response = it.data
                    response?.let {res->
                        if(res.status){
                            val gpList = ArrayList<PlaceDataModel>()
                            gpList.add(PlaceDataModel(0, "चुनें"))
                            gpList.addAll(res.data)
                            setGPAdapter(gpList)
                        }
                        else{
                            Toast.makeText(requireActivity(), res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Status.LOADING ->{

                }
                Status.ERROR ->{

                }
            }
        }

        masterViewModel.villageLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS ->{
                    val response = it.data
                    response?.let {res->
                        if(res.status){
                            val villageList = ArrayList<PlaceDataModel>()
                            villageList.add(PlaceDataModel(0, "चुनें"))
                            villageList.addAll(res.data)
                            setVillageAdapter(villageList)
                        }
                        else{
                            Toast.makeText(requireActivity(), res.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Status.LOADING ->{

                }
                Status.ERROR ->{

                }
            }
        }
    }

    private fun setDistrictAdapter(districtList : ArrayList<PlaceDataModel>){
        val districtNames = ArrayList<String>()
        districtList.forEach {
            districtNames.add(it.label)
        }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, districtNames)
        binding.district.threshold = 3
        binding.district.setAdapter(adapter)
        binding.district.showDropDown()

        binding.district.setOnItemClickListener { parent, view, position, id ->
            if(position != 0) districtId = districtList[position].id
        }
    }

    private fun setCivicBodiesAdapter(civicBodiesList : ArrayList<PlaceDataModel>){
        val civicBodies = ArrayList<String>()
        civicBodiesList.forEach {
            civicBodies.add(it.label)
        }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, civicBodies)
        binding.civicBodies.threshold = 3
        binding.civicBodies.setAdapter(adapter)
        binding.civicBodies.setOnClickListener {
            binding.civicBodies.showDropDown()
        }


        binding.civicBodies.setOnItemClickListener{parent, view, position, id ->
            if(position != 0){
                civicBodyID = civicBodiesList[position].id
                masterViewModel.getWard(civicBodyID)
            }
        }
    }

    private fun setWardAdapter(wardList : ArrayList<PlaceDataModel>){
        val wards = ArrayList<String>()
        wardList.forEach {
            wards.add(it.label)
        }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, wards)
        binding.ward.threshold = 3
        binding.ward.setAdapter(adapter)
        binding.ward.setOnClickListener {
            binding.ward.showDropDown()
        }

        binding.ward.setOnItemClickListener { parent, view, position, id ->
            if(position != 0){
                wardID = wardList[position].id
            }
        }
    }

    private fun setBlockAdapter(blockList : ArrayList<PlaceDataModel>){
        val blocks = ArrayList<String>()
        blockList.forEach {
            blocks.add(it.label)
        }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, blocks)
        binding.block.threshold = 3
        binding.block.setAdapter(adapter)
        binding.block.setOnClickListener {
            binding.block.showDropDown()
        }

        binding.block.setOnItemClickListener { parent, view, position, id ->
            if(position != 0){
                blockID = blockList[position].id
                masterViewModel.getGP(blockID)
            }
        }
    }

    private fun setGPAdapter(gpList : ArrayList<PlaceDataModel>){
        val panchayats = ArrayList<String>()
        gpList.forEach {
            panchayats.add(it.label)
        }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, panchayats)
        binding.panchayat.threshold = 3
        binding.panchayat.setAdapter(adapter)
        binding.panchayat.setOnClickListener{
            binding.panchayat.showDropDown()
        }

        binding.panchayat.setOnItemClickListener { parent, view, position, id ->
            if(position != 0){
                panchayatID = gpList[position].id
                masterViewModel.getVillage(panchayatID)
            }
        }
    }

    private fun setVillageAdapter(villageList : ArrayList<PlaceDataModel>){
        val villages = ArrayList<String>()
        villageList.forEach {
            villages.add(it.label)
        }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_dropdown_item, villages)
        binding.village.threshold = 3
        binding.village.setAdapter(adapter)
        binding.village.setOnClickListener {
            binding.village.showDropDown()
        }

        binding.village.setOnItemClickListener { parent, view, position, id ->
            if(position != 0){
                villageID = villageList[0].id
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
         * @return A new instance of fragment RegistrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}