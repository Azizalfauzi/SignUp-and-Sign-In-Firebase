package org.d3ifcool.crudfirebase

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class HeroAdapater(val mCtx: Context, val LayoutId: Int, val heroList: List<Hero>) :
    ArrayAdapter<Hero>(mCtx, LayoutId, heroList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(LayoutId, null)

        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val tvEmail = view.findViewById<TextView>(R.id.tv_email)
        val tvNim = view.findViewById<TextView>(R.id.tv_nim)
        val tvKelas = view.findViewById<TextView>(R.id.tv_kelas)

        val btUpdate = view.findViewById<Button>(R.id.bt_update)


        val hero = heroList[position]
        tvNama.text = hero.nama
        tvEmail.text = hero.email
        tvNim.text = hero.nim
        tvKelas.text = hero.kelas

        btUpdate.setOnClickListener {
            showUpdateDialog(hero)
        }
        return view

    }

    fun showUpdateDialog(hero: Hero) {
        val builder = AlertDialog.Builder(mCtx)

        builder.setTitle("Update Data")

        val inflater = LayoutInflater.from(mCtx)

        val view = inflater.inflate(R.layout.update_hero, null)
        //update
        val inpNama = view.findViewById<EditText>(R.id.inp_nama)
        val inpEmail = view.findViewById<EditText>(R.id.inp_email)
        val inpNim = view.findViewById<EditText>(R.id.inp_nim)
        val inpKelas = view.findViewById<EditText>(R.id.inp_kelas)

        inpNama.setText(hero.nama)
        inpEmail.setText(hero.email)
        inpNim.setText(hero.nim)
        inpKelas.setText(hero.kelas)


        builder.setView(view)

        builder.setPositiveButton(
            "update"
        ) { p0, p1 ->
            val dbHero = FirebaseDatabase.getInstance().getReference("heroes")

            val nama = inpNama.text.toString().trim()
            val email = inpEmail.text.toString().trim()
            val nim = inpNim.text.toString().trim()
            val kelas = inpKelas.text.toString().trim()

            if (nama.isEmpty() && email.isEmpty() && nim.isEmpty() && kelas.isEmpty()) {
                inpNama.error = "Masukan nama"
                inpEmail.error = "Masukan email"
                inpNim.error = "Masukan nim"
                inpKelas.error = "Masukan kelas"

                inpNama.requestFocus()
                inpEmail.requestFocus()
                inpNim.requestFocus()
                inpKelas.requestFocus()

                return@setPositiveButton
            }
            val hero = Hero(hero.id, nama, email, nim, kelas)
            dbHero.child(hero.id).setValue(hero)

            Toast.makeText(mCtx,"Berhasil update!",Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton(
            "no"
        ) { p0, p1 ->
        }
        val alert = builder.create()
        alert.show()
    }
}