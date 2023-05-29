package com.ics342.labs
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var button:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        editText = findViewById(R.id.editText)
        button.setOnClickListener {
            handleButtonClick()
        }
    }

    private fun handleButtonClick() {
        /** Implement the functionality to display the alert here. **/
        // get editText entered toString
        val text = editText.text.toString()
        // get AlertDialog
        val dialog = AlertDialog.Builder(this)
        // Display the "Okay" button as a positive / close dialog.
        dialog.setPositiveButton(
            "OKAY"
        ) { _, _ ->
            Toast.makeText(this@MainActivity, "Alert dialog closed.", Toast.LENGTH_LONG).show()
        }
        // check for empty field
        if (text.isEmpty()) {
            // Set Alert Title
            dialog.setTitle("Error")
            // show error dialog
            dialog.setMessage("Enter some text in the text field.")
        }else{
            // Set Alert Title
            dialog.setTitle("Entered text")
            // Showing the user input
//            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            dialog.setMessage(text)
        }
        // Create the Alert dialog
        val alertDialog = dialog.create()
        // Show the Alert dialog box
        alertDialog.show()
    }

    private fun showTextInAlert(text: String) {
        AlertDialog
            .Builder(this)
            .setTitle(R.string.entered_text)
            .setMessage(text)
            .setPositiveButton(R.string.okay, null)
            .create()
            .show()
    }

    private fun showErrorAlert() {
        AlertDialog
            .Builder(this)
            .setTitle(R.string.error_title)
            .setMessage(R.string.error_message)
            .setPositiveButton(R.string.okay, null)
            .create()
            .show()
    }
}
