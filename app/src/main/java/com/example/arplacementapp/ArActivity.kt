package com.example.arplacementapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.Color
import com.google.ar.sceneform.rendering.MaterialFactory
import com.google.ar.sceneform.rendering.ShapeFactory
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.example.arplacementapp.databinding.ActivityArBinding

class ArActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArBinding
    private lateinit var arFragment: ArFragment
    private var currentAnchorNode: AnchorNode? = null
    private var currentAnchor: Anchor? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drillName = intent.getStringExtra("DRILL_NAME") ?: "Drill"
        binding.instructionsText.text = "Tap on ground to place $drillName marker"

        arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult, plane, _ ->
            if (plane.type != Plane.Type.HORIZONTAL_UPWARD_FACING) return@setOnTapArPlaneListener

            currentAnchor?.detach()
            currentAnchorNode?.setParent(null)
            currentAnchorNode = null
            currentAnchor = null

            // Create new anchor
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)
            currentAnchorNode = anchorNode
            currentAnchor = anchor

            // Create cube
            MaterialFactory.makeOpaqueWithColor(this, Color(0.0f, 1.0f, 0.0f)).thenAccept { material ->
                val cube = ShapeFactory.makeCube(Vector3(0.1f, 0.1f, 0.1f), Vector3.zero(), material)
                val node = TransformableNode(arFragment.transformationSystem)
                node.setParent(anchorNode)
                node.renderable = cube
                node.select()
            }

            Toast.makeText(this, "$drillName placed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        currentAnchor?.detach()
        currentAnchorNode?.setParent(null)
    }
}