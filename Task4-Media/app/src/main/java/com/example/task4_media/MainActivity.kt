package com.example.task4_media

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


const val PICK_AUDIO_REQUEST = 1
const val PICK_VIDEO_REQUEST = 2

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var videoPlayer: VideoView? = null
    private var playButton: Button? = null
    private var pauseButton: Button? = null
    private var stopButton: Button? = null
    private var isPlayingAudio: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setOnCompletionListener { stopPlay() }
        videoPlayer = findViewById(R.id.videoPlayer)
        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        findViewById<RadioGroup>(R.id.mediaType).setOnCheckedChangeListener { group, checkedId ->
            onTypeChange(group, checkedId)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data

            mediaPlayer?.reset()

            if (requestCode == PICK_AUDIO_REQUEST) {
                if (uri != null) {
                    mediaPlayer?.setDataSource(this, uri)
                    mediaPlayer?.prepare()
                    setFilename(getFilename(uri))
                    onPlay(null)
                }
            }
            if (requestCode == PICK_VIDEO_REQUEST) {
                if (uri != null) {
                    videoPlayer?.setVideoURI(uri)
                    setFilename(getFilename(uri))
                    onPlay(null)
                }
            }
        }
    }

    private fun getFilename(uri: Uri): String? {
        var result: String? = null
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor.use { cur ->
            cur?.moveToFirst()
            result = cur?.getString(cur.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return result
    }

    private fun stopVideo() {
        videoPlayer?.stopPlayback();
        videoPlayer?.resume();
    }

    private fun stopAudio() {
        mediaPlayer?.stop()
        mediaPlayer?.prepare()
        mediaPlayer?.seekTo(0)
    }

    private fun stopPlay() {
        if (isPlayingAudio) {
            stopAudio()
        } else {
            stopVideo()
        }

        playButton?.isEnabled = true
        pauseButton?.isEnabled = false
        stopButton?.isEnabled = false
    }

    private fun setFilename(filename: String?) {
        findViewById<TextView>(R.id.filename).apply {
            text = filename
        }
    }

    private fun onTypeChange(group: RadioGroup, checkedId: Int) {
        isPlayingAudio = checkedId == R.id.audioType

        setFilename("")
        playButton?.isEnabled = false
        pauseButton?.isEnabled = false
        stopButton?.isEnabled = false
        if (isPlayingAudio) {
            stopVideo()
            videoPlayer?.visibility = View.GONE
        } else {
            videoPlayer?.visibility = View.VISIBLE
            mediaPlayer?.reset()
        }
    }

    fun onPlay(view: View?) {
        if (isPlayingAudio) {
            mediaPlayer?.start()
        } else {
            videoPlayer?.start()
        }
        playButton?.isEnabled = false
        pauseButton?.isEnabled = true
        stopButton?.isEnabled = true
    }

    fun onPause(view: View?) {
        if (isPlayingAudio) {
            mediaPlayer?.pause()
        } else {
            videoPlayer?.pause();
        }
        playButton?.isEnabled = true
        pauseButton?.isEnabled = false
        stopButton?.isEnabled = true
    }

    fun onStop(view: View?) {
        stopPlay()
    }

    fun onBrowse(view: View?) {
        val contentType = if (isPlayingAudio) "audio" else "video"
        val requestType = if (isPlayingAudio) PICK_AUDIO_REQUEST else PICK_VIDEO_REQUEST

        val audioIntent = Intent().apply {
            type = "$contentType/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(audioIntent, requestType)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer!!.isPlaying) {
            stopPlay()
        }
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
