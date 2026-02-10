package no.sebbe.fnkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val notesVm: NotesViewModel = viewModel()

            NavHost(navController = navController, startDestination = HomeScreen) {
                composable<HomeScreen> {
                    HomeScreenContent(notesVm, navController)
                }
                composable<NewNoteScreen> {
                    NewNoteScreenContent(notesVm, { navController.popBackStack() })
                }
                composable<ViewNoteScreen> {
                    var args = it.toRoute<ViewNoteScreen>()
                    var note = NoteModel(args.title, args.body)
                    ViewNoteScreenContent(note, {navController.popBackStack()})
                }
            }
//            App()
        }
    }

}

@Serializable
object HomeScreen

@Serializable
data class ViewNoteScreen(
    val title: String,
    val body: String
)

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HomeScreenContent(notesVm: NotesViewModel, navController: NavHostController) {

    Scaffold(
        topBar = { TopAppBar(title = { Text("FastNotes! Kotlin!!") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(NewNoteScreen) }) {
                Text("New Note")
            }
        }
    ) { padding ->
        Column(
           modifier = Modifier.padding(padding).fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Column() {
                for (note in notesVm.notes) {
                    NoteCard(note, navController)
                }
            }

        }
    }
}

@Composable
fun NoteCard(note: NoteModel, navController: NavHostController) {
    Column() {
        Card(
            onClick = { navController.navigate(ViewNoteScreen(note.title, note.body)) }) {
            Column(modifier = Modifier.padding(16.dp).fillMaxWidth(0.8f)) {
                Text(note.title, style = TextStyle(fontSize = 30.sp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Serializable
data class NoteModel(
    val title: String,
    val body: String

)

class NotesViewModel : ViewModel() {
    val notes = mutableStateListOf<NoteModel>()

    fun addNote(note: NoteModel) {
        notes.add(note)
    }
}


@Serializable
object NewNoteScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNoteScreenContent(notesVm: NotesViewModel, onGoBack: () -> Unit) {
    val titleState = rememberTextFieldState()
    val bodyState = rememberTextFieldState()
    Scaffold(
        topBar = { TopAppBar(title = { Text("New Note") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (titleState.text.isEmpty()) {
                    return@FloatingActionButton
                }
                if (bodyState.text.isEmpty()) {
                    return@FloatingActionButton
                }
                var note = NoteModel(titleState.text.toString(), bodyState.text.toString())
                notesVm.addNote(note)
                onGoBack()
            })
            { Text("Save Note") }
        },

        ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TextField(

                state = titleState,
                label = { Text("title") },
                textStyle = TextStyle(fontSize = 30.sp),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                state = bodyState,
                label = { Text("body") },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth().weight(1f),
                )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ViewNoteScreenContent(note: NoteModel, onGoBack: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Note Details") }) },

        ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(
                note.title,
                style = TextStyle(fontSize = 40.sp)
            )
            Text(
                note.body,
                style = TextStyle(fontSize = 20.sp)

            )
        }
    }

}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

