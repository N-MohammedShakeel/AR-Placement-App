package com.example.arplacementapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.arplacementapp.databinding.ActivityDrillSelectionBinding

class DrillSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrillSelectionBinding
    private val drills = listOf(
        Drill("Drill 1", "Basic cordless drill", "Use for wood and light metal"),
        Drill("Drill 2", "Hammer drill", "Ideal for masonry and concrete"),
        Drill("Drill 3", "Compact drill", "Best for tight spaces")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrillSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, drills.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.drillSpinner.adapter = adapter

        binding.drillSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDrill = drills[position]
                binding.drillImagePlaceholder.text = "Image: ${selectedDrill.name}"
                binding.drillDescription.text = selectedDrill.description
                binding.drillTips.text = selectedDrill.tips
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.startArButton.setOnClickListener {
            val selectedDrill = drills[binding.drillSpinner.selectedItemPosition]
            val intent = Intent(this, ArActivity::class.java).apply {
                putExtra("DRILL_NAME", selectedDrill.name)
            }
            startActivity(intent)
        }
    }
}