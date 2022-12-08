package mx.mauriciogs.consumingwebservice.data

object StudentRemoteRepository {
    const val STUDENT_BASE_URL = "http://192.168.0.236:8080"
    const val ADD_STUDENT_URL = "$STUDENT_BASE_URL/agregarestudiante"
    const val GET_STUDENTS_JSON_URL = "$STUDENT_BASE_URL/estudiantesJSON"
    const val GET_STUDENT_ID_URL = "$STUDENT_BASE_URL/id/"
    const val DELETE_STUDENT_ID_URL = "$STUDENT_BASE_URL/borrarestudiante/"
}