package org.d3ifcool.crudfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import org.d3ifcool.crudfirebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var heroList: MutableList<Hero>
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        ref = FirebaseDatabase.getInstance().getReference("heroes")
        heroList = mutableListOf()
        listView = binding.listData
        binding.btSave.setOnClickListener {
            saveHero()
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    for (h in p0.children) {
                        val hero = h.getValue(Hero::class.java)
                        heroList.add(hero!!)
                    }
                    val adapter = HeroAdapater(this@MainActivity, R.layout.heroes, heroList)
                    listView.adapter = adapter
                }
            }
        })
    }

    fun saveHero() {
        val nama = inp_nama.text.toString().trim()
        val email = inp_email.text.toString().trim()
        val nim = inp_nim.text.toString().trim()
        val kelas = inp_kelas.text.toString().trim()

        if (nama.isEmpty() && email.isEmpty() && nim.isEmpty() && kelas.isEmpty()) {
            inp_nama.error = "Masukan Nama"
            inp_email.error = "Masukan email"
            inp_nim.error = "Masukan nim"
            inp_kelas.error = "Masukan Kelas"
            return
        }


        val heroId = ref.push().key

        val hero = Hero(heroId!!, nama, email, nim, kelas)

        ref.child(heroId).setValue(hero).addOnCompleteListener {
            Toast.makeText(applicationContext, "Data Berhasil disimpan!!", Toast.LENGTH_SHORT)
                .show()
        }
        clear()
    }

    fun clear() {
        inp_nama.setText("")
        inp_kelas.setText("")
        inp_nim.setText("")
        inp_email.setText("")
    }
}
