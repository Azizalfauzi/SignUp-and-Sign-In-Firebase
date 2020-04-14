package org.d3ifcool.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        bt_logout.setOnClickListener { private fun signOut() {
            // [START auth_sign_out]
            startActivity(Intent(this, MainActivity::class.java))
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()
            finish()
        }
            signOut()
        }
    }


}
