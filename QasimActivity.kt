//Student Number: 301177263
package com.qasim.zaka

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qasim.zaka.ui.theme.MyAppTheme

class QasimActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme() { // Explicitly set darkTheme to true
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background) // Explicit background color
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display logo
            val logo: Painter = painterResource(id = R.drawable.contacts) // add your logo to res/drawable
            Image(
                painter = logo,
                contentDescription = "Logo",
                modifier = Modifier.size(230.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Image button for navigation
            Button(
                onClick = {
                    // Navigate to Second Activity
                    val intent = Intent(this@QasimActivity, ZakaActivity::class.java)
                    startActivity(intent)
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Go to Contacts")
            }
        }
    }
}
