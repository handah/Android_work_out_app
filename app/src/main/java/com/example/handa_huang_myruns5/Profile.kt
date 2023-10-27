package com.example.handa_huang_myruns5

import android.R.attr.bitmap
import android.R.attr.data
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream


class Profile:AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var myViewModel: MyViewModel
    private lateinit var imgUri: Uri
    private lateinit var tempUri: Uri
    private var tempBitmap: Bitmap? = null
    private val tempFileName = "tempImage.jpg"
    private  val profileFileName = "profileImage.jpg"
    private lateinit var  cameraResult: ActivityResultLauncher<Intent>
    private lateinit var  galleryResult: ActivityResultLauncher<Intent>
    private var flag:Int =0
    private final var uriKey :String = "image_uri"
    private lateinit var imgFile: File
    private lateinit var tempFile: File


    private val imageChoice = arrayOf(
        "Open Camera", "Select From Gallery"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        imageView =  findViewById(R.id.imageProfile)
        Util.checkPermissions(this)

        load()

        imgFile = File(getExternalFilesDir(null), profileFileName)
        imgUri= FileProvider.getUriForFile(this, "com.example.handa_huang_myruns2", imgFile)

        tempFile= File(getExternalFilesDir(null),tempFileName)
        tempUri = FileProvider.getUriForFile(this, "com.example.handa_huang_myruns2", tempFile)


        if (imgFile.exists()){
            val bitmap = Util.getBitmap(this,imgUri)
            imageView.setImageBitmap(bitmap)
        }


        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        myViewModel.userImage.observe(this){
            val bitmap = Util.getBitmap(this,tempUri)
            imageView.setImageBitmap(bitmap)


        }


        //Start activity for result --- when taking photo from camera
        cameraResult =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                it: ActivityResult ->
            if (it.resultCode == RESULT_OK){

                val bitmap = Util.getBitmap(this, tempUri)
                myViewModel.userImage.value = bitmap



            }
        }

        //start activity for result --- when picking photo from gallery. I rotated the image 180f so it looks normal on my device.
        //If it doesn't look right on your device, please adjust the rotation degree
        galleryResult=  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                it: ActivityResult ->
            if (it.resultCode == RESULT_OK){

                val someuri=it.data?.data!!

                val localImage:File = File(this.getExternalFilesDir(null),tempFileName)
                val stream = FileOutputStream(localImage)
                val newBitmap = Util.getBitmap(this,someuri)
                val matrix=Matrix()


                matrix.setRotate(180f)//try to adjust this if the image is rotated on your device


                Bitmap.createBitmap(newBitmap,0,0, newBitmap.width,newBitmap.height,matrix,true).compress(Bitmap.CompressFormat.PNG,100,stream)
                stream.flush()
                stream.close()

                tempUri = FileProvider.getUriForFile(this, "com.example.handa_huang_myruns2", localImage)
                val bitmap=Util.getBitmap(this,tempUri)
                myViewModel.userImage.value=bitmap



            }
        }


    }



    //load dialog box for choosing: take a photo from camera or select a photo from gallery
    fun onChangePhotoClicked(view: View){

        val changeButton : Button=findViewById(R.id.changeButton)

        val builder=AlertDialog.Builder(this)
        builder.setTitle("Pick Profile Picture")
        builder.setItems(imageChoice)
        {
                dialog, which->
            println("debug: the chosen option is: ${imageChoice[which]}")

            val currentChoice:String=imageChoice[which]
            if (currentChoice=="Open Camera"){
                val intent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri)
                cameraResult.launch(intent)
            }
            else if (currentChoice=="Select From Gallery"){
                println("current choice is selected from galley")

                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                galleryResult.launch(intent)
            }




        }
        val dialog=builder.create()
        dialog.show()

    }



    //when cancel clicked, finish
    fun  onCancelClicked(view: View){
        val tempFile = File(getExternalFilesDir(null), tempFileName)
        if (tempFile.exists()){
            if (tempFile.delete()){
                System.out.println("file Deleted :");
            } else {
                System.out.println("file not Deleted :");
            }
        }
        finish()
    }


    //save image
    fun saveImage(){
        //imgFileName
        var file_name:String=imgFile.name
        println("imgFile name is: ")
        println(file_name)
        tempFile.renameTo(imgFile)
        val tempFile = File(getExternalFilesDir(null), tempFileName)
        if (tempFile.exists()){
            if (tempFile.delete()){
                System.out.println("file Deleted :");
            } else {
                System.out.println("file not Deleted :");
            }
        }

    }

    //when save is clicked, save image and other data, and finish
    fun onSaveClicked(view: View){
        saveImage()
        save()
        Toast.makeText(this,"saved", Toast.LENGTH_SHORT).show()
        finish()
    }
    //load data
    fun load(){
        val name: EditText = findViewById(R.id.nameid)
        val email: EditText = findViewById(R.id.emailId)
        val phone: EditText = findViewById(R.id.phoneId)
        val gender: RadioGroup = findViewById(R.id.genderId)
        val classs: EditText = findViewById(R.id.classId)
        val major: EditText = findViewById(R.id.majorId)

        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val savedName: String? = sharedPreferences.getString("NAME_KEY", null)
        val savedEmail: String? = sharedPreferences.getString("EMAIL_KEY", null)
        val savedPhone : String? = sharedPreferences.getString("PHONE_KEY", null)
        val savedGender : Int = sharedPreferences.getInt("GENDER_KEY", -1)
        val savedClass : String? = sharedPreferences.getString("CLASS_KEY", null)
        val savedMajor : String? = sharedPreferences.getString("MAJOR_KEY", null)
        name.setText(savedName)
        email.setText(savedEmail)
        phone.setText(savedPhone)
        gender.check(savedGender)
        classs.setText(savedClass)
        major.setText(savedMajor)

    }


    //save data
    fun save(){
        val sharedPreferences : SharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val name: EditText = findViewById(R.id.nameid)
        val nameText:String= name.text.toString()
        val email: EditText = findViewById(R.id.emailId)
        val emailText:String = email.text.toString()
        val phone: EditText = findViewById(R.id.phoneId)
        val phoneText:String = phone.text.toString()
        val gender: RadioGroup = findViewById(R.id.genderId)

        val genderId : Int = gender.checkedRadioButtonId;
        val classs: EditText = findViewById(R.id.classId)
        val classText: String = classs.text.toString()
        val major: EditText = findViewById(R.id.majorId)
        val majorText : String = major.text.toString()

        editor.apply {
            putString("NAME_KEY", nameText)
            putString("EMAIL_KEY", emailText)
            putString("PHONE_KEY",phoneText)
            putInt("GENDER_KEY",genderId)
            putString("CLASS_KEY",classText)
            putString("MAJOR_KEY",majorText)

        }.apply()

    }



}