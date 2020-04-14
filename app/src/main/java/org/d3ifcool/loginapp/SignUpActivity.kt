package org.d3ifcool.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.signup_activity.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_activity)
        auth = FirebaseAuth.getInstance()

        btn_signup.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser() {
        if (inp_email_signup.text.toString().isEmpty()) {
            inp_email_signup.error = "plase enter email"
            inp_email_signup.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(inp_email_signup.text.toString()).matches()) {
            inp_email_signup.error = "plase enter email"
            inp_email_signup.requestFocus()
            return
        }
        if (inp_password_signup.text.toString().isEmpty()) {
            inp_password_signup.error = "please enter password"
            inp_password_signup.requestFocus()
            return
        }
        auth.createUserWithEmailAndPassword(
            inp_email_signup.text.toString(),
            inp_password_signup.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        }
                } else {
                    Toast.makeText(
                        baseContext, "Sign Up failed.and try again",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}
