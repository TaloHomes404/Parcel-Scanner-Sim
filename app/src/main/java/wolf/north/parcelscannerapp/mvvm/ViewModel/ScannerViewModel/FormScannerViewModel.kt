package wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel

import androidx.lifecycle.ViewModel

class FormScannerViewModel : BaseScannerViewModel(){

    //
    //Methods for form scanner
    //

    fun processForm(result: String){
        onProcessingFinished(result)
    }

}