package com.allsoft.qrscanapp.views.dashboard

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.allsoft.qrscanapp.R
import com.allsoft.qrscanapp.data.viewModel.MasterViewModel
import com.allsoft.qrscanapp.databinding.ActivityMainBinding
import com.allsoft.qrscanapp.utils.Status
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode
import org.koin.android.ext.android.inject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val masterViewModel : MasterViewModel by inject()

    private lateinit var scanQrCodeLauncher : ActivityResultLauncher<Nothing?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerScan()
        setListener()

        setObserver()
    }

    private fun registerScan(){
        scanQrCodeLauncher = registerForActivityResult(ScanQRCode()) { result ->
            // handle QRResult
            val text = when (result) {
                is QRResult.QRSuccess -> {
                    result.content.rawValue
                    // decoding with default UTF-8 charset when rawValue is null will not result in meaningful output, demo purpose
                        ?: result.content.rawBytes?.let { String(it) }.orEmpty()
                }

                QRResult.QRUserCanceled -> "User canceled"
                QRResult.QRMissingPermission -> "Missing permission"
                is QRResult.QRError -> "${result.exception.javaClass.simpleName}: ${result.exception.localizedMessage}"
            }

            Log.d("QR Code Result", text)

            binding.qrCodeInfo.text = "Pass Code : $text"

            try {
                masterViewModel.getPassDetail(text.toInt())
            }
            catch (e : Exception){
                e.printStackTrace()
            }
        }
    }

    private fun setListener(){
        binding.scanQRBtn.setOnClickListener {
            scanQrCodeLauncher.launch(null)
            binding.passDate.text = "____________"
            binding.visitorName.text = "____________"
            binding.visitorMobile.text = "____________"
            binding.visitorWithNo.text = "____________"
        }

        binding.qrCodeInfo.setOnClickListener {
            var urlLink = binding.qrCodeInfo.text.toString()
            if(urlLink.isNotEmpty()){
                try {
                    if (!urlLink.startsWith("http://") && !urlLink.startsWith("https://")) {
                        urlLink = "http://$urlLink"
                    }

                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(urlLink))
                    startActivity(browserIntent)
                }
                catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }

    }


    private fun setObserver(){
        masterViewModel.passDetailLiveData.observe(this){
            when(it.status){
                Status.SUCCESS ->{
                    val response = it.data
                    response?.let { res->
                        if(res.status){

                            binding.passDate.text = formatDate(res.data.applicant_visit_datetime)
                            binding.visitorName.text = res.data.applicant_name
                            binding.visitorMobile.text = res.data.applicant_mobile_number
                            binding.visitorWithNo.text = res.data.number_of_visitors.toString()


                        }
                        else{
                            showErrorDialog(res.message)
                        }

                        binding.progressBar.visibility = View.GONE
                    }
                }
                Status.LOADING ->{
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR ->{
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun showErrorDialog(message: String?) {
        val bottomSheetDialog = Dialog(this, R.style.AppDialogTheme)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_error_message)
        val closeBtn = bottomSheetDialog.findViewById<ImageView>(R.id.btnCloseSheet)
        val lblDialogMessage = bottomSheetDialog.findViewById<TextView>(R.id.lblDialogMessage)
        closeBtn?.setOnClickListener { v: View? -> bottomSheetDialog.dismiss() }
        lblDialogMessage?.text = HtmlCompat.fromHtml(message!!, HtmlCompat.FROM_HTML_MODE_COMPACT)
        bottomSheetDialog.show()
    }

    private fun formatDate(inputString : String) : String{
        return try {
            val splitString  = inputString.split("T")
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date: Date = inputFormat.parse(splitString[0])

            val outputFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
            val outputString: String = outputFormat.format(date)
            return outputString
        }
        catch (e : Exception){
            e.printStackTrace()
            ""
        }

    }
}
