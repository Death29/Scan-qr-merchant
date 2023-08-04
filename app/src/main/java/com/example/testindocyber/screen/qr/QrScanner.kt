package com.example.testindocyber.screen.qr

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testindocyber.R
import com.example.testindocyber.databinding.ActivityQrScannerBinding
import com.example.testindocyber.screen.qr.HomeViewUser.Companion.DATA_SAVING
import com.example.testindocyber.screen.qr.util.QRCodeFoundListener
import com.example.testindocyber.screen.qr.util.QRCodeImageAnalyzer
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class QrScanner : AppCompatActivity() {
    private lateinit var binding: ActivityQrScannerBinding

    private lateinit var cameraManager: CameraManager
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var previewView: PreviewView
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var scanner : BarcodeScanner

    private val dataSaving by lazy {
        intent.getDoubleExtra(DATA_SAVING, 0.0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraExecutor = Executors.newSingleThreadExecutor()
        previewView = binding.scannerView
        scanner = BarcodeScanning.getClient()

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        if (checkRequestCameraPermission())startCamera()
        else ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION)
    }


    private fun checkRequestCameraPermission() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSION){
            if (checkRequestCameraPermission()) startCamera()
            else {
                Toast.makeText(this@QrScanner, "Permission Denied", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                bindCameraPreview(cameraProvider)
            } catch (e: ExecutionException) {
                Toast.makeText(this, "Error starting camera ", Toast.LENGTH_LONG).show()
            } catch (e: InterruptedException) {
                Toast.makeText(this, "Error starting camera ", Toast.LENGTH_LONG).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun stopCamera(camerProvider: ProcessCameraProvider){
        camerProvider.unbindAll()
    }

    private fun bindCameraPreview(cameraProvider: ProcessCameraProvider?) {
        binding.scannerView.implementationMode = PreviewView.ImplementationMode.PERFORMANCE
        val preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val imageAnalysis = ImageAnalysis.Builder()
            .build()

        val scanOptions =
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE,
                )
                .build()

        scanner = BarcodeScanning.getClient(scanOptions)
        imageAnalysis.setAnalyzer(
            cameraExecutor,
            QRCodeImageAnalyzer(object : QRCodeFoundListener {
                override fun onQRCodeFound(qrCode: String?) {}

                override fun qrCodeNotFound() {}

                override fun onFinish(imageProxy: ImageProxy) {
                    imageProxy.close()
                }
            }, scanner) { result ->
                runOnUiThread {
                    intentToDetailPayment(result)
                }
            }
        )
        cameraProvider?.unbindAll()
        cameraProvider!!.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
    }

    private fun intentToDetailPayment(result: String) {
        startActivity(Intent(this@QrScanner, DetailPayment::class.java)
            .putExtra(DATA_SCANNER, result)
            .putExtra(DATA_SAVING, dataSaving))
        stopCamera(cameraProviderFuture.get())
    }

    override fun onResume() {
        super.onResume()
        if (checkRequestCameraPermission()) startCamera()
    }

    companion object{
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSION = 10
        const val DATA_SCANNER = "DATA_SCANNER"
    }
}