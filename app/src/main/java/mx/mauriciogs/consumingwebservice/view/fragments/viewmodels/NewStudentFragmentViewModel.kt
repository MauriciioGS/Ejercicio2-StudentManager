package mx.mauriciogs.consumingwebservice.view.fragments.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response.Listener
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.JsonArrayRequest
import mx.mauriciogs.consumingwebservice.data.StudentRemoteRepository
import mx.mauriciogs.consumingwebservice.data.VolleyAPI
import mx.mauriciogs.consumingwebservice.data.model.StudentsItem
import org.json.JSONArray
import org.json.JSONObject

class NewStudentFragmentViewModel(
    private val volleyAPI: VolleyAPI
) : ViewModel() {

    private var _addSuccess = MutableLiveData<Boolean>()
    val addSuccess: LiveData<Boolean>
        get() = _addSuccess

    fun addStudent (student: StudentsItem){
        val jsonRequest = object : JsonArrayRequest(
            StudentRemoteRepository.ADD_STUDENT_URL,
            Listener {
                _addSuccess.postValue(true)
            },
            ErrorListener {
                _addSuccess.postValue(false)
                it.printStackTrace()
                Log.e("ERROR:", "${it.message}")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return header
            }

            override fun getBody(): ByteArray {
                val estudiante = JSONObject()
                estudiante.put("cuenta", student.cuenta)
                estudiante.put("nombre", student.nombre)
                estudiante.put("edad", student.edad)

                val materias = JSONArray()
                student.materias.forEach {  materia ->
                    val itemMaterias = JSONObject()
                    itemMaterias.put("id", materia.id)
                    itemMaterias.put("nombre", materia.nombre)
                    itemMaterias.put("creditos", materia.creditos)
                    materias.put(itemMaterias)
                }
                estudiante.put("materias", materias)

                return estudiante.toString().toByteArray(charset = Charsets.UTF_8)
            }

            override fun getMethod(): Int {
                return Method.POST
            }
        }

        volleyAPI.add(jsonRequest)
    }

    class NewStudentFragmentVMFactory(val volleyAPI: VolleyAPI): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(NewStudentFragmentViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return NewStudentFragmentViewModel(volleyAPI) as T
            }
            throw  java.lang.IllegalArgumentException("Clase ViewModel desconocida")
        }
    }
}