package com.faltro.perch.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.faltro.perch.R
import com.faltro.perch.model.Submission
import kotlinx.android.synthetic.main.activity_submission.*


class SubmissionActivity : AppCompatActivity() {
    private var submission: Submission? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        submission = intent.getSerializableExtra(MainActivity.FIELD_SUBMISSION) as Submission
        submissionTitleText.text = submission!!.displayName
        submissionSubtitleText.text = submission!!.authorName
        submissionDescriptionText.text = submission!!.description
    }

    fun goToSceneformActivity(view: View) {
        val intent = Intent(this, SceneformActivity::class.java)
        intent.putExtra(MainActivity.FIELD_SUBMISSION, submission)
        startActivity(intent)
    }
}
