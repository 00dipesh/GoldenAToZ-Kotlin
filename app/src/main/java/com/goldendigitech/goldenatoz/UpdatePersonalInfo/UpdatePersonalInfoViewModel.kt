package com.goldendigitech.goldenatoz.UpdatePersonalInfo

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.InputStream
import android.util.Base64
import android.widget.Toast
import com.goldendigitech.goldenatoz.employee.DocumentResponse

class UpdatePersonalInfoViewModel : ViewModel() {
    private val webServices = Constant.webService

    private val _updatePersonalInfoResponse = MutableLiveData<UpdatePersonalInfoResponse>()
    val updatePersonalInfoResponse: LiveData<UpdatePersonalInfoResponse> = _updatePersonalInfoResponse

    fun updateUserPersonalInfo(updatePersonalInfoModel: UpdatePersonalInfoModel) {
        webServices.updateUserData(updatePersonalInfoModel)
            .enqueue(object : Callback<UpdatePersonalInfoResponse> {
                override fun onResponse(
                    call: Call<UpdatePersonalInfoResponse>,
                    response: Response<UpdatePersonalInfoResponse>
                ) {
                    if (response.isSuccessful) {
                        _updatePersonalInfoResponse.value = response.body()
                        Log.e(
                            "UpdatePersonalInfoViewModel",
                            "Update info Success: ${_updatePersonalInfoResponse.value}"
                        )
                    } else {
                        Log.e(
                            "UpdatePersonalInfoViewModel",
                            "Update info failed: ${response.code()}"
                        )
                    }
                }

                override fun onFailure(call: Call<UpdatePersonalInfoResponse>, t: Throwable) {
                    Log.e("UpdatePersonalInfoViewModel", "Network error: ${t.message}")
                }
            })
    }

    private val _uploadDocumentResponse = MutableLiveData<UploadDocumentResponse>()
    val uploadDocumentResponse: LiveData<UploadDocumentResponse> = _uploadDocumentResponse

     fun userUploadDocument(context: Context, fileData: Any, fileType: String, fileName: String, employeeId: Int)
     {
         var fileContent = ""

         when (fileData) {
             is Bitmap -> {
                 // Image file
                 val imageBitmap = fileData
                 fileContent = convertBitmapToBase64(imageBitmap)
             }
             is Uri -> {
                 // PDF or Word file
                 val fileUri = fileData
                 val contentResolver = context.contentResolver
                 if (contentResolver != null) {
                     fileContent = getFileContentFromUri(contentResolver, fileUri)
                 } else {
                     // Handle the case where contentResolver is null
                     showToast(context,"Error: Could not retrieve content resolver")
                     return
                 }
             }
             is String -> {
                 // Direct file content (e.g., PDF content)
                 fileContent = fileData
             }
         }


         val fileModel = UploadDocumentModel(
             EmployeeId = employeeId ?: 0,
             FileName = fileName,
             FileType = fileType,
             FileContent = fileContent
         )

        webServices.uploadUserDocument(fileModel).enqueue(object :Callback<UploadDocumentResponse>{
            override fun onResponse(
                call: Call<UploadDocumentResponse>,
                response: Response<UploadDocumentResponse>
            ) {
                if (response.isSuccessful) {
                    _uploadDocumentResponse.value = response.body()
                    Log.e(
                        "UpdatePersonalInfoViewModel", "Update document Success: ${_updatePersonalInfoResponse.value}"
                    )
                    showToast(context, "Document uploaded successfully")
                } else {
                    Log.e(
                        "UpdatePersonalInfoViewModel", "Update document failed: ${response.code()}"
                    )
                }
            }

            override fun onFailure(call: Call<UploadDocumentResponse>, t: Throwable) {
                Log.e("UpdatePersonalInfoViewModel", "Network error: ${t.message}")

            }
        })
     }

    // Function to get file content from URI
    private fun getFileContentFromUri(contentResolver: ContentResolver, uri: Uri): String {
        var fileContent = ""
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            if (inputStream != null) {
                val bytes = getBytesFromInputStream(inputStream)
                fileContent = Base64.encodeToString(bytes, Base64.DEFAULT)
                inputStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return fileContent
    }

    private fun getBytesFromInputStream(inputStream: InputStream): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteArrayOutputStream.write(buffer, 0, len)
        }
        return byteArrayOutputStream.toByteArray()
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    val photoExistsLiveData = MutableLiveData<Boolean>()
    val signExistsLiveData = MutableLiveData<Boolean>()
    val resumeExistsLiveData = MutableLiveData<Boolean>()
    val adharcardExistsLiveData = MutableLiveData<Boolean>()
    val pancardExistsLiveData = MutableLiveData<Boolean>()
    val certificateExistsLiveData = MutableLiveData<Boolean>()

    fun checkFileExists(employeeId: Int, fileName: String){
        webServices.showFile(employeeId).enqueue(object :Callback<DocumentResponse>{
            override fun onResponse(
                call: Call<DocumentResponse>,
                response: Response<DocumentResponse>
            ) {
                if (response.isSuccessful) {
                    var fileExists = false
                    // Iterate through the list of files and check if the given fileName exists
                    response.body()?.Data?.forEach { fileData ->
                        if (fileData.fileName == fileName) {
                            fileExists = true
                            return@forEach
                        }
                    }
                    // Set the LiveData value based on the existence of the file
                    when (fileName) {
                        "Photo" -> photoExistsLiveData.value = fileExists
                        "Sign" -> signExistsLiveData.value = fileExists
                        "Resume" -> resumeExistsLiveData.value = fileExists
                        "Adharcard" -> adharcardExistsLiveData.value = fileExists
                        "Pancard" -> pancardExistsLiveData.value = fileExists
                        "Certificate" -> certificateExistsLiveData.value = fileExists
                        else -> {
                            // Handle other cases if needed
                        }
                    }
                } else {
                    // Handle unsuccessful response
                    // You might want to set LiveData values to false or handle the error state appropriately
                    photoExistsLiveData.value = false
                    signExistsLiveData.value = false
                    certificateExistsLiveData.value = false
                    resumeExistsLiveData.value = false
                }            }

            override fun onFailure(call: Call<DocumentResponse>, t: Throwable) {
                photoExistsLiveData.value = false
                signExistsLiveData.value = false
                certificateExistsLiveData.value = false
                resumeExistsLiveData.value = false
            }
        })
    }


}
