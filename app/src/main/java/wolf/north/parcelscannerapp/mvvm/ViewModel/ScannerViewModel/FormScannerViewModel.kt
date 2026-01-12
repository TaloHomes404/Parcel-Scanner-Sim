package wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import wolf.north.parcelscannerapp.mvvm.Model.files.Form
import java.io.File

class FormScannerViewModel() : BaseScannerViewModel(){


    //init vals for mlkit recognizer
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    //Context val
    private var appContext: Context? = null


    //
    //Methods for form scanner
    //

    //INIT SCANNER
    fun initScanner(context: Context) {
        appContext = context.applicationContext
    }

    //Method for ml-kit recognizer to connect Screen
    fun onImageCaptured(imageFile: File){
        viewModelScope.launch {
            try {
                val scannedForm = withContext(Dispatchers.IO) {
                    scanForm(imageFile)
                }

                if(scannedForm.getFilledFieldsCount() > 0){
                    onProcessingFinished("SUCCESS: ${scannedForm.getFilledFieldsCount()} fields scanned")
                } else {
                    onProcessingFinished("ERROR: NO FORM FIELDS SCANNED")
                }
            }
            catch (e : Exception){
                onProcessingFinished("ERROR: ${e.message} IN FIELD SCAN")

            }
        }
    }

    //**
    //ML-Kit Form Scanner methods
    //**

    suspend fun scanForm(imageFile: File) : Form {

        val context = appContext ?: return Form()

        return try {
            val image = InputImage.fromFilePath(context, Uri.fromFile(imageFile))
            val visionText = recognizer.process(image).await()
            val fullText = visionText.text
            parseFormData(fullText)
        }
        catch (e : Exception){
            e.printStackTrace()
            Form()
        }
    }


    //**
    //Form parsing method from file
    //**

    private fun parseFormData(text: String): Form {
        //form lines from text to easy parse
        val lines = text.split("\n").map { it.trim() }


        var firstName: String? = null
        var lastName: String? = null
        var date: String? = null
        var id: String? = null
        var address: String? = null
        var phoneNumber: String? = null
        var email: String? = null
        var additionalNotes: String? = null

        for (i in lines.indices) {
            val line = lines[i]
            val nextLine = lines.getOrNull(i + 1)

            when{
                //FirstName
                matchesPattern(line, listOf("imię", "imie", "name", "first name")) -> {
                    firstName = extractValue(line, nextLine)
                }

                //LastName
                matchesPattern(line, listOf("nazwisko", "last name", "surname")) -> {
                    lastName = extractValue(line, nextLine)
                }

                //Data
                matchesPattern(line, listOf("data", "date")) -> {
                    date = extractDate(line, nextLine)
                }

                //Number of ID
                matchesPattern(line, listOf("id", "numer", "number", "nr")) -> {
                    id = extractValue(line, nextLine)
                }

                //Address
                matchesPattern(line, listOf("adres", "address", "ulica", "street")) -> {
                    address = extractValue(line, nextLine)
                }

                //Telefon
                matchesPattern(line, listOf("telefon", "tel", "phone", "mobile")) -> {
                    phoneNumber = extractPhoneNumber(line, nextLine)
                }

                //Email
                matchesPattern(line, listOf("email", "e-mail", "mail")) -> {
                    email = extractEmail(line, nextLine)
                }

                //Additional notes in form
                matchesPattern(line, listOf("uwagi", "notes", "notatki", "comments")) -> {
                    additionalNotes = extractValue(line, nextLine)
                }

            }
        }


            return Form(
                firstName = firstName,
                lastName = lastName,
                date = date,
                id = id,
                address = address,
                phoneNumber = phoneNumber,
                email = email,
                additionalNotes = additionalNotes
            )
        }


    //**
    //Method for checking matching patterns in fields   <fieldName> : <valueSearched>
    //**
    private fun matchesPattern(line: String, patterns: List<String>): Boolean {
        val lowerLine = line.lowercase()
        return patterns.any { pattern ->
            lowerLine.contains(pattern) && lowerLine.contains(":")
        }
    }

    //**
    //Method for getting value after separator <field> : <value>
    //**
    private fun extractValue(line: String, nextLine: String?): String? {
        val afterSeparator = line.substringAfter(":","").trim()

        //If there's something after separator, get this value
        if(afterSeparator.isNotBlank() && afterSeparator.length > 1) return afterSeparator

        //If after separator value is empty, check next line
        return nextLine?.takeIf { it.isNotBlank() && !it.contains(":") }
    }

    //**
    //Method for extracting values (in all forms)
    //**
    private fun extractDate(line: String, nextLine: String?): String? {
        val combinedText = "$line $nextLine"

        //For format
        val dateRegex = Regex("""\d{2}[./-]\d{2}[./-]\d{4}""")
        return dateRegex.find(combinedText)?.value
    }

    private fun extractPhoneNumber(line: String, nextLine: String?): String? {
        val combinedText = "$line $nextLine"

        // Format: +48 123 456 789 or 123-456-789 or 123456789
        val phoneRegex = Regex("""[+]?[\d\s\-()]{9,15}""")
        val match = phoneRegex.find(combinedText)?.value?.trim()

        // Is at least 9 numbers like all phone numbers?
        return match?.takeIf { it.count { c -> c.isDigit() } >= 9 }
    }

    private fun extractEmail(line: String, nextLine: String?): String? {
        val combinedText = "$line $nextLine"

        // Regex for email pattern
        //Can be used android lib value for email pattern later TODO:
        val emailRegex = Regex("""[\w._%+-]+@[\w.-]+\.[a-zA-Z]{2,}""")
        return emailRegex.find(combinedText)?.value
    }

    //Dispatch recognizer after
    fun closeRecognizer() {
        recognizer.close()
    }


    fun processForm(result: String){
        onProcessingFinished(result)
    }

}