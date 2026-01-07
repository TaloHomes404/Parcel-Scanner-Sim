package wolf.north.parcelscannerapp.mvvm.ViewModel.ScannerViewModel

class PackageScannerViewModel : BaseScannerViewModel() {


    //
    //Methods for package scanner
    //

    fun processPackage(result: String){
        onProcessingFinished(result)
    }

}