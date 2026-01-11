package wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.tasks.await
import wolf.north.parcelscannerapp.mvvm.Model.files.Courier
import wolf.north.parcelscannerapp.mvvm.Model.files.ShipmentType
import wolf.north.parcelscannerapp.mvvm.Model.files.WeightClass
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import wolf.north.parcelscannerapp.mvvm.Model.files.Package

class PackageScannerViewModel(private val context : Context) : BaseScannerViewModel() {

    //init vals for recognizer
    private val scanner = BarcodeScanning.getClient()


    //**
    //ML-Kit Form Scanner methods
    //**
    suspend fun scanPackage(imageFile: File): Package? {
        return try {
            val image = InputImage.fromFilePath(context, Uri.fromFile(imageFile))
            val barcodes = scanner.process(image).await()
            val packageBarcode = barcodes.firstOrNull {barcode ->
                isValidPackageBarcode(barcode)
            }
            packageBarcode?.let { barcode ->
                createPackageFromBarcode(barcode)
            }
        }
        catch (e : Exception){
            e.printStackTrace()
            null
        }
    }

    //validate barcodes for valid package format
    private fun isValidPackageBarcode(barcode: Barcode) : Boolean {
        val code = barcode.rawValue ?: return false

        //Length check
        if (code.length < 10 ) return false

        //random barcode selection check
        if (isProductBarcode(code)) return false

        //Validate for true courier prefixes defined
        val hasKnownPrefix = Courier.entries.any { courier ->
            courier.prefix.any { prefix ->
                code.startsWith(prefix, ignoreCase = true)
            }
        }

        //Only accept barcode if it's know from couriers and have correct format
        return hasKnownPrefix || (code.length > 15 && code.count() { it.isDigit() } >= code.length * 0.8)
    }

    private fun isProductBarcode(code: String): Boolean {
        if(code.length == 13 && code.all { it.isDigit() }) {
            val prefix = code.take(3)
            if (prefix in listOf("590", "591", "977", "978", "979")) return true
        }

        if (code.length == 8 && code.all { it.isDigit() }) return true
        if (code.length == 12 && code.all { it.isDigit() }) return true

        return false
    }

    private fun createPackageFromBarcode(barcode: Barcode) : Package {
        val trackingNumber = barcode.rawValue ?: "UNKNOWN"
        val courier = Courier.fromTrackingNumber(trackingNumber)

        return Package(
            trackingNumber = trackingNumber,
            courier = courier,
            scanDate = getCurrentDate(),
            weightClass = WeightClass.A,
            shipmentType = ShipmentType.PARCEL
        )
    }

    //**
    //Dispatch scanner after use
    //**
    fun closeScanner() {
        scanner.close()
    }


    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)

    }

    //
    //Methods for package scanner
    //
    fun processPackage(result: String){
        onProcessingFinished(result)
    }

}