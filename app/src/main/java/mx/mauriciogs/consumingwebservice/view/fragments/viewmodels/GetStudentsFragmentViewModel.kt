package mx.mauriciogs.consumingwebservice.view.fragments.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Response.ErrorListener
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import mx.mauriciogs.consumingwebservice.data.StudentRemoteRepository
import mx.mauriciogs.consumingwebservice.data.StudentRemoteRepository.DELETE_STUDENT_ID_URL
import mx.mauriciogs.consumingwebservice.data.StudentRemoteRepository.GET_STUDENT_ID_URL
import mx.mauriciogs.consumingwebservice.data.VolleyAPI
import mx.mauriciogs.consumingwebservice.data.model.Materia
import mx.mauriciogs.consumingwebservice.data.model.Students
import mx.mauriciogs.consumingwebservice.data.model.StudentsItem
import org.json.JSONArray

class GetStudentsFragmentViewModel(
    private val volleyAPI : VolleyAPI
): ViewModel() {

    private val _allStudentsList = MutableLiveData<Students>()
    val allStudentsList: LiveData<Students>
        get() = _allStudentsList

    private val _allStudentsDeleted = MutableLiveData<Students>()
    val allStudentsDeleted: LiveData<Students>
        get() = _allStudentsDeleted

    private val _student = MutableLiveData<StudentsItem>()
    val student: LiveData<StudentsItem>
        get() = _student

    private val _searchFailure = MutableLiveData<Boolean>()
    val searchFailure: LiveData<Boolean>
        get() = _searchFailure

    private val _deleteFailure = MutableLiveData<Boolean>()
    val deleteFailure: LiveData<Boolean>
        get() = _deleteFailure

    fun getAllStudents() {
        var estudiantes = Students()

        val jsonRequest = object : JsonArrayRequest(
            StudentRemoteRepository.GET_STUDENTS_JSON_URL,
            Listener { response ->
                (0 until response.length()).forEach {
                    val estudiante = response.getJSONObject(it)
                    val cuenta = estudiante.get("cuenta")
                    val nombre = estudiante.get("nombre")
                    val edad = estudiante.get("edad")
                    val materias = getMaterias(estudiante.getJSONArray("materias"))
                    if (materias.isNotEmpty()) {
                        estudiantes.add(
                            StudentsItem(
                                cuenta = "$cuenta",
                                nombre = "$nombre",
                                edad = "$edad",
                                materias = materias
                            )
                        )
                    }
                }
                if (estudiantes.isNotEmpty())
                    _allStudentsList.postValue(estudiantes)
            },
            ErrorListener {
                it.printStackTrace()
                Log.e("ERROR:", "${it.message}")
                _searchFailure.postValue(true)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return header
            }
        }

        volleyAPI.add(jsonRequest)
    }

    fun getAStudent(id: String){
        val jsonRequest = object : JsonObjectRequest(
            Method.GET,
            GET_STUDENT_ID_URL+id,
            null,
            Listener { response ->
                val cuenta = response.get("cuenta")
                val nombre = response.get("nombre")
                val edad = response.get("edad")
                val materias = getMaterias(response.getJSONArray("materias"))
                if (materias.isNotEmpty()) {
                    val estudiante = StudentsItem(
                            cuenta = "$cuenta",
                            nombre = "$nombre",
                            edad = "$edad",
                            materias = materias
                        )
                    _student.postValue(estudiante)
                }
            },
            ErrorListener {
                it.printStackTrace()
                Log.e("ERROR:", "${it.message}")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyAPI.add(jsonRequest)
    }

    fun deleteAStudent(id: String) {
        var estudiantes = Students()
        val jsonRequest = object : JsonArrayRequest(
            DELETE_STUDENT_ID_URL+id,
            Listener { response ->
                (0 until response.length()).forEach {
                    val estudiante = response.getJSONObject(it)
                    val cuenta = estudiante.get("cuenta")
                    val nombre = estudiante.get("nombre")
                    val edad = estudiante.get("edad")
                    val materias = getMaterias(estudiante.getJSONArray("materias"))
                    if (materias.isNotEmpty()) {
                        val estudiante = StudentsItem(
                                cuenta = "$cuenta",
                                nombre = "$nombre",
                                edad = "$edad",
                                materias = materias
                            )

                    }
                }
                if (estudiantes.isNotEmpty())
                    _allStudentsDeleted.postValue(estudiantes)
            },
            ErrorListener { responseError ->
                responseError.printStackTrace()
                Log.e("ERROR:", "${responseError.message}")
                _deleteFailure.postValue(true)
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return header
            }

            override fun getMethod(): Int {
                return Method.DELETE
            }
        }

        volleyAPI.add(jsonRequest)
    }

    private fun getMaterias(jsonArray: JSONArray): List<Materia> {
        var materias = mutableListOf<Materia>()
        (0 until jsonArray.length()).forEach {
            val materia = jsonArray.getJSONObject(it)
            val id = materia.get("id").toString().toInt()
            val nombre = materia.get("nombre").toString()
            val creditos = materia.get("creditos").toString().toInt()
            materias.add(Materia(id = id, nombre = nombre, creditos = creditos))
        }
        return materias
    }

    class GetStudentsFragmentVMFactory(val volleyAPI: VolleyAPI): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(GetStudentsFragmentViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return GetStudentsFragmentViewModel(volleyAPI) as T
            }
            throw  java.lang.IllegalArgumentException("Clase ViewModel desconocida")
        }
    }
}