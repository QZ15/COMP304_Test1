//Student Number: 301177263
package com.qasim.zaka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.qasim.zaka.ui.theme.MyAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ZakaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme(darkTheme = true) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ContactFormScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ContactFormScreen(viewModel: ContactViewModel = viewModel()) {
        val snackbarHostState = remember { SnackbarHostState() }
        var name by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var isFriend by remember { mutableStateOf(false) }
        var isFamily by remember { mutableStateOf(false) }
        var isWork by remember { mutableStateOf(false) }
        val contactList by viewModel.contacts.collectAsState()

        val coroutineScope = rememberCoroutineScope() // Define coroutine scope

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Name TextField
            CustomTextField(value = name, label = "Name", onValueChange = { name = it })

            // Phone Number TextField
            CustomTextField(value = phoneNumber, label = "Phone Number", onValueChange = { phoneNumber = it })

            // Email TextField
            CustomTextField(value = email, label = "Email", onValueChange = { email = it })

            // Checkbox Row for Contact Type
            Row(verticalAlignment = Alignment.CenterVertically) {
                CheckboxWithLabel("Friend", isFriend, { isFriend = it })
                CheckboxWithLabel("Family", isFamily, { isFamily = it })
                CheckboxWithLabel("Work", isWork, { isWork = it })
            }

            // Button to add contact
            Button(
                onClick = {
                    val contactType = when {
                        isFriend -> "Friend"
                        isFamily -> "Family"
                        isWork -> "Work"
                        else -> "Other"
                    }
                    val newContact = Contact(name, phoneNumber, email, contactType)
                    viewModel.addContact(newContact)

                    // Launch the snackbar in a coroutine
                    coroutineScope.launch {
                        viewModel.showSnackbar(snackbarHostState, "Contact added: $name")
                    }

                    // Clear input fields after adding contact
                    name = ""
                    phoneNumber = ""
                    email = ""
                    isFriend = false
                    isFamily = false
                    isWork = false
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add Contact", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contact list with scrollable LazyColumn and improved styling
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(contactList) { contact ->
                    ContactCard(contact = contact)
                }
            }

            // Display Snackbar for confirmation
            SnackbarHost(hostState = snackbarHostState)
        }
    }

    @Composable
    fun CustomTextField(value: String, label: String, onValueChange: (String) -> Unit) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label, color = Color.White) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }

    @Composable
    fun CheckboxWithLabel(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 16.dp)) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Color.White,
                    uncheckedColor = Color.Gray,
                    checkedColor = Color.Gray
                )
            )
            Text(label, color = Color.White)
        }
    }

    @Composable
    fun ContactCard(contact: Contact) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = contact.name,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Phone: ${contact.phoneNumber}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Email: ${contact.email}",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Type: ${contact.type}",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f), // Slightly lighter color for type
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }

}

// ViewModel for managing contact list and snackbar
class ContactViewModel : ViewModel() {
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    fun addContact(contact: Contact) {
        _contacts.value = _contacts.value + contact
    }

    suspend fun showSnackbar(snackbarHostState: SnackbarHostState, message: String) {
        snackbarHostState.showSnackbar(message)
    }
}

// Data class to represent a Contact
data class Contact(
    val name: String,
    val phoneNumber: String,
    val email: String,
    val type: String
)
