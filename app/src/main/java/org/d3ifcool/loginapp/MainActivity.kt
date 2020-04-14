package org.d3ifcool.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.signup_activity.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        bt_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        bt_login.setOnClickListener {
            doLogin()
        }
    }

    private fun doLogin() {
        if (inp_email_login.text.toString().isEmpty()) {
            inp_email_login.error = "plase enter email"
            inp_email_login.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(inp_email_login.text.toString()).matches()) {
            inp_email_login.error = "plase enter email"
            inp_email_login.requestFocus()
            return
        }
        if (inp_password.text.toString().isEmpty()) {
            inp_password.error = "please enter password"
            inp_password.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(
            inp_email_login.text.toString(),
            inp_password.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                    finish()
                } else {
                    Toast.makeText(
                        baseContext, "Password / Email anda salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            if (currentUser.isEmailVerified) {
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                Toast.makeText(
                    baseContext, "Lakukan verifikasi email anda",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                baseContext, "Lakukan Login Terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
