package com.bertosoft.incidencias.data.funciones

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bertosoft.incidencias.data.database.AdminDbHelper
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Locale
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

private val gKey = SecretKeySpec("U82HuXLNzbX3f6r".toByteArray(), "AES")
private val gIv = IvParameterSpec("epDKqfVtYXhdXgb".toByteArray())

class FuncAux @Inject constructor() {

    fun cifrar(inputText: String): String{
        return inputText
    }

    fun descifrar(cipherText: String): String{
        return cipherText
    }

    fun cifrarOld(inputText: String): String {

        //
        // Obtenemos una iv aleatoria
        //
        val ivRandom = SecureRandom() //not caching previous seeded instance of SecureRandom
        val byteIv = ByteArray(16)
        ivRandom.nextBytes(byteIv)
        val iv = IvParameterSpec(byteIv)

        //
        // Codificamos en base 64 iv
        //
        val strIvHex = Base64.getEncoder().encodeToString(byteIv)

        //
        // Codificamos con aes el texto
        //
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, gKey, iv)
        val cipherText = cipher.doFinal(inputText.toByteArray())
        val strTextoCodHex = Base64.getEncoder().encodeToString(cipherText)

        //
        // Concatenamos ivHex y texto codificado Hex
        //
        val strTextoCodIvHex = strIvHex + strTextoCodHex

        return strTextoCodIvHex
    }

    fun descifrarOld(cipherText: String): String {

        //
        // Obtenemos la byteIv y la iv
        //
        var str = cipherText
        val strIvHex = str.removeRange(24, str.length)
        val byteIv = Base64.getDecoder().decode(strIvHex)
        val iv = IvParameterSpec(byteIv)

        //
        // Obtenemos el texto a decodificar
        //
        str = cipherText
        val strTextoCodHex = str.removeRange(0, 24)
        val strTextoCod = Base64.getDecoder().decode(strTextoCodHex)

        //
        // Decodificamos con aes
        //
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, gKey, iv)
        val plainText = cipher.doFinal(strTextoCod)
        val strTextoDecodificado = String(plainText)

        return strTextoDecodificado
    }

    fun mostrarTeclado(miContexto: Context, miTxt: EditText) {
        val imm =
            miContexto.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        miTxt.postDelayed(
            {
                imm.showSoftInput(miTxt, 0)
            }, 50
        )
    }

    fun ocultarTeclado(miContexto: Context, miTxt: EditText) {
        val imm = miContexto.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(miTxt.getWindowToken(), 0)
    }

    fun existeRegistroFecha(contexto: Context, fechaCod: String): Int {
        var idRegistro = -1
        val adminDbHlper = AdminDbHelper(contexto, null)
        val sqlReadDb = adminDbHlper.readableDatabase
        val cIncidencias = sqlReadDb.rawQuery("SELECT *FROM Incidencias", null)
        val iColId = cIncidencias.getColumnIndex("_id")
        val iColFecha = cIncidencias.getColumnIndex("Fecha")

        if (cIncidencias.moveToFirst()) {
            while (!cIncidencias.isAfterLast) {
                val idDb = cIncidencias.getInt(iColId)
                val fechaDbCod = cIncidencias.getString(iColFecha)
                val fechaDb = descifrar(fechaDbCod)
                val fecha = descifrar(fechaCod)

                if (fechaDb == fecha) {
                    idRegistro = idDb
                }
                cIncidencias.moveToNext()
            }
        }
        sqlReadDb.close()
        adminDbHlper.close()

        return idRegistro
    }

    fun leerVoladurasFromId(contexto: Context, _id: Int): String {
        var voladurasCod = ""
        val adminDbHlper = AdminDbHelper(contexto, null)
        val sqlReadDb = adminDbHlper.readableDatabase
        val cIncidencias = sqlReadDb.rawQuery("SELECT *FROM Incidencias", null)
        val iColId = cIncidencias.getColumnIndex("_id")
        val iColVoladuras = cIncidencias.getColumnIndex("Voladuras")
        if (cIncidencias.moveToFirst()) {
            while (!cIncidencias.isAfterLast) {
                val idDb = cIncidencias.getInt(iColId)
                val voladurasDb = cIncidencias.getString(iColVoladuras)

                if (_id == idDb) {
                    voladurasCod = voladurasDb
                }
                cIncidencias.moveToNext()
            }
        }
        sqlReadDb.close()
        adminDbHlper.close()

        return voladurasCod
    }

    fun strFechaCortaFromCalendar(fecha: Calendar): String {
        val sdfCorta = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdfCorta.format(fecha.time)
    }

    fun strFechaLargaFromCalendar(fecha: Calendar): String {
        val sdfLarga = SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy", Locale.getDefault())
        return sdfLarga.format(fecha.time)
    }

    fun mesStrToInt(mes: String): Int {
        return when (mes) {
            "Enero" -> 0
            "Febrero" -> 1
            "Marzo" -> 2
            "Abril" -> 3
            "Mayo" -> 4
            "Junio" -> 5
            "Julio" -> 6
            "Agosto" -> 7
            "Septiembre" -> 8
            "Octubre" -> 9
            "Noviembre" -> 10
            "Diciembre" -> 11
            else -> {
                -1
            }
        }
    }

    fun mesIntToStr(iMes: Int): String{
        return when(iMes){
            0 -> "Enero"
            1 -> "Febrero"
            2 -> "Marzo"
            3 -> "Abril"
            4 -> "Mayo"
            5 -> "Junio"
            6 -> "Julio"
            7 -> "Agosto"
            8 -> "Septiembre"
            9 -> "Octubre"
            10 -> "Noviembre"
            11 -> "Diciembre"
            else -> {
                "Error"
            }
        }
    }

}