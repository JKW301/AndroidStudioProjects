package com.example.laravelapi

import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import android.nfc.NdefRecord
import android.nfc.tech.Ndef
import android.widget.Switch
import java.io.File
import java.nio.charset.Charset
import java.util.Locale


class nfcActivity : AppCompatActivity() {

    private var nfcAdapter: NfcAdapter? = null
    private var pendingIntent: PendingIntent? = null

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: nfcActivity

    private lateinit var startNfcButton: Button
    private lateinit var nfcDataTextView: TextView

    private lateinit var expiryDateEditText: EditText
    private val calendar = Calendar.getInstance()
    private lateinit var featureSwitch: Switch

    fun createAppFolder() {
        val folder = getAppFolder()
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }

    fun getAppFolder(): File {
        return File(getExternalFilesDir(null), "com.example.nfc_from_scratch")
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    private fun enableNfcForegroundDispatch() {
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    private fun disableNfcForegroundDispatch() {
        nfcAdapter?.disableForegroundDispatch(this)
    }



    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                // Mettre à jour le champ de texte avec la date sélectionnée
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth)
                updateDateInView(selectedDate)
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000 // Empêcher de choisir une date passée
        datePickerDialog.show()
    }

    private fun updateDateInView(calendar: Calendar) {
        val myFormat = "yyyy-MM-dd" // Format de date souhaité
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        expiryDateEditText.setText(sdf.format(calendar.time))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        val token = intent.getStringExtra("token")

        featureSwitch = findViewById(R.id.featureSwitch)
        nfcDataTextView = findViewById(R.id.nfcDataTextView)

        val refreshButton = findViewById<Button>(R.id.refreshButton)
        val foodTypeEditText = findViewById<EditText>(R.id.foodTypeEditText)
        expiryDateEditText = findViewById<EditText>(R.id.expiryDateEditText)
        expiryDateEditText.setOnClickListener {
            showDatePickerDialog()
        }

        // Ajouter un écouteur de clic pour le bouton Refresh
        refreshButton.setOnClickListener {
            // Effacer le texte de la TextView
            nfcDataTextView.text = ""
        }

        // Récupérer l'intention (Intent) qui a déclenché onNewIntent
        //val intent = intent
        //processIntent(intent)

        // Initialisation des autres éléments et variables
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        val intent = Intent(this, nfcActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
    }

    private fun processIntent(intent: Intent) {
        // Récupérer le Tag depuis l'intention
        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)

        // Vérifier l'état du Switch
        if (featureSwitch.isChecked) {
            // Le Switch est activé (ON) - Traiter l'écriture
            nfcDataTextView.text = ""
            if (tag != null) {
                writeNfcData(tag)
            }
            readNfcData(tag)
        } else {
            // Le Switch est désactivé (OFF) - Traiter la lecture
            nfcDataTextView.text = ""
            readNfcData(tag)
        }
    }
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Récupérer l'intention (Intent) qui a déclenché onNewIntent
        intent?.let { processIntent(it) }
    }

    private fun readNfcData(tag: Tag?) {
        try {
            val ndef = Ndef.get(tag)
            ndef.connect()
            val ndefMessage = ndef.ndefMessage

            // Lire le contenu du premier enregistrement du NdefMessage
            val record = ndefMessage.records[0]
            val payload = record.payload

            // Convertir le payload en chaîne de caractères lisible
            val textRead = String(payload)

            // Afficher les informations lues dans le TextView
            nfcDataTextView.text = textRead

            ndef.close()
        } catch (e: Exception) {
            // Gérer les erreurs ici
            //Toast.makeText(this, "Erreur lors de la lecture du tag NFC : ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun writeNfcData(tag: Tag) {
        val foodTypeEditText = findViewById<EditText>(R.id.foodTypeEditText)
        val expiryDateEditText = findViewById<EditText>(R.id.expiryDateEditText)

        val foodType = foodTypeEditText.text.toString()
        val expiryDate = expiryDateEditText.text.toString()

        val textToWrite = "$foodType, $expiryDate"

        try {
            val ndef = Ndef.get(tag)
            ndef?.connect()
            val message = NdefMessage(arrayOf(createTextRecord("", textToWrite)))
            ndef?.writeNdefMessage(message)

            // Lire le contenu après l'écriture
            readNfcData(tag)

            ndef?.close()
        } catch (e: Exception) {
            //Toast.makeText(this, "Erreur lors de l'écriture sur le tag NFC : ${e.message}", Toast.LENGTH_SHORT).show()
        }

        foodTypeEditText.text.clear()
        expiryDateEditText.text.clear()
    }

    private fun createTextRecord(language: String, text: String): NdefRecord {
        val langBytes = language.toByteArray(Charset.forName("US-ASCII"))
        val textBytes = text.toByteArray(Charset.forName("UTF-8"))

        val recordPayload = ByteArray(1 + langBytes.size + textBytes.size)

        recordPayload[0] =
            0x02.toByte() // status byte: UTF-8 encoding and length of language code is 2
        System.arraycopy(langBytes, 0, recordPayload, 1, langBytes.size)
        System.arraycopy(textBytes, 0, recordPayload, 1 + langBytes.size, textBytes.size)

        return NdefRecord(
            NdefRecord.TNF_WELL_KNOWN,
            NdefRecord.RTD_TEXT,
            ByteArray(0),
            recordPayload
        )
    }

    private fun writeTag(ndef: Ndef, message: NdefMessage) {
        try {
            ndef.connect()
            if (ndef.isWritable) {
                ndef.writeNdefMessage(message)
            }
            ndef.close()
        } catch (e: Exception) {
            // Gérer les exceptions
        }
    }


    // Vérifier DROIT en ECRITURE-----------------------------------------------------------------------------------------------------------
    fun isTagWritable(tag: Tag): Boolean {
        // Récupère l'instance de Ndef pour le tag NFC
        val ndef = Ndef.get(tag)
        // Vérifie si le tag NFC prend en charge Ndef
        if (ndef != null) {
            try {
                // Connecte le tag NFC
                ndef.connect()
                // Vérifie si le tag NFC est inscriptible
                return ndef.isWritable
            } catch (e: Exception) {
                // Gestion des exceptions
                e.printStackTrace()
            } finally {
                // Déconnecte le tag NFC
                ndef.close()
            }
        }
        return false // Si le tag NFC ne prend pas en charge Ndef ou s'il y a une erreur, retourne false
    }
}