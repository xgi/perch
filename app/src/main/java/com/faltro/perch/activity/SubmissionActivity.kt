package com.faltro.perch.activity

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.faltro.perch.R
import com.faltro.perch.model.Permission
import com.faltro.perch.model.Submission
import com.faltro.perch.util.PermissionHandler
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_submission.*


class SubmissionActivity : AppCompatActivity() {
    private var submission: Submission? = null
    private val permissionHandler: PermissionHandler = PermissionHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)
        setSupportActionBar(findViewById(R.id.toolbar))

        submission = intent.getSerializableExtra(MainActivity.FIELD_SUBMISSION) as Submission
        submissionTitleText.text = submission!!.displayName
        submissionSubtitleText.text = submission!!.authorName
        submissionDescriptionText.text = submission!!.description
        Picasso.with(this).load(submission!!.thumbnailUrl).into(submissionThumbnail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = submission!!.displayName
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.submission_actions, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun goToSceneformActivity(view: View) {
        val intent = Intent(this, SceneformActivity::class.java)
        intent.putExtra(MainActivity.FIELD_SUBMISSION, submission)
        startActivity(intent)
    }

    fun shareSubmission(menuItem: MenuItem) {
        val path = submission!!.name.replace("assets/", "view/")

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "https://poly.google.com/$path")
        startActivity(Intent.createChooser(intent, "Share"))
    }

    fun downloadSubmission(menuItem: MenuItem? = null) {
        // First check if we have permission to download. If not, we will request the user to grant
        // permission, and if permission is granted this method will be called again.
        val hasPermission: Boolean = permissionHandler.getPermission(
                this, Permission.WRITE_EXTERNAL_STORAGE)
        if (!hasPermission) return

        val name = submission!!.name.replace("assets/", "")
        val uri = Uri.parse(submission!!.gltf2Url)

        val request = DownloadManager.Request(uri)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        "$name.gltf")
                .setNotificationVisibility(
                        DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.allowScanningByMediaScanner()
        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        val permission: Permission? = permissionHandler.handleRequestResult(
                requestCode, permissions, grantResults)
        if (permission == Permission.WRITE_EXTERNAL_STORAGE) {
            downloadSubmission()
        }
    }

}
