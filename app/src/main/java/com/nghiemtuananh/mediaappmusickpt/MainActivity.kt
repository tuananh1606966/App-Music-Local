package com.nghiemtuananh.mediaappmusickpt

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    var arraySong: ArrayList<Song> = arrayListOf()
    var position = 0
    lateinit var mediaPlayer: MediaPlayer
    lateinit var animation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addSong()
        khoiTaoMediaPlayer()
        setTimeTotal()
        updateTimeSong()
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_disc_rotate)

        ibtn_next.setOnClickListener {
            position++
            if (position > arraySong.size - 1) {
                position = 0
            }
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            khoiTaoMediaPlayer()
            mediaPlayer.start()
            ibtn_play.setBackgroundResource(R.drawable.pause)
            setTimeTotal()
            iv_disc.startAnimation(animation)
        }

        ibtn_prev.setOnClickListener {
            position--
            if (position < 0) {
                position = arraySong.size - 1
            }
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            khoiTaoMediaPlayer()
            mediaPlayer.start()
            ibtn_play.setBackgroundResource(R.drawable.pause)
            setTimeTotal()
            iv_disc.startAnimation(animation)
        }

        ibtn_stop.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.release()
            ibtn_play.setBackgroundResource(R.drawable.play)
            khoiTaoMediaPlayer()
            iv_disc.clearAnimation()
        }

        ibtn_play.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                // nếu đang phát -> pause -> đổi hình play
                mediaPlayer.pause()
                iv_disc.clearAnimation()
                ibtn_play.setBackgroundResource(R.drawable.play)
            } else {
                // đang ngừng -> phát -> đổi hình pause
                mediaPlayer.start()
                ibtn_play.setBackgroundResource(R.drawable.pause)
                iv_disc.startAnimation(animation)
            }
            setTimeTotal()
        }

        sb_time.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.seekTo(sb_time.progress)
            }
        })
    }

    private fun updateTimeSong() {
        var mHandler = Handler()
        mHandler.postDelayed(object : Runnable {
            override fun run() {
                var dinhDangGio = SimpleDateFormat("mm:ss")
                tv_time_song.setText(dinhDangGio.format(mediaPlayer.currentPosition))
                sb_time.setProgress(mediaPlayer.currentPosition)
                // kiểm tra thời gian bài hát -> nếu kết thúc -> next
                mediaPlayer.setOnCompletionListener {
                    position++
                    if (position > arraySong.size - 1) {
                        position = 0
                    }
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                    }
                    khoiTaoMediaPlayer()
                    mediaPlayer.start()
                    ibtn_play.setBackgroundResource(R.drawable.pause)
                    setTimeTotal()
                }
                mHandler.postDelayed(this, 500)
            }
        }, 100)
    }

    private fun setTimeTotal() {
        var dinhDangGio = SimpleDateFormat("mm:ss")
        tv_time_total.setText(dinhDangGio.format(mediaPlayer.duration))
        // gán max của sbSong = mediaPlayer.duration
        sb_time.max = mediaPlayer.duration
    }

    private fun khoiTaoMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, arraySong.get(position).file)
        tv_title.setText(arraySong.get(position).title)
    }

    private fun addSong() {
        arraySong.add(Song("Anh cứ đi đi", R.raw.anh_cu_di_di))
        arraySong.add(Song("Cause I Love You", R.raw.cause_i_love_you))
        arraySong.add(Song("Để mị nói cho mà nghe", R.raw.de_mi_noi_cho_ma_nghe))
        arraySong.add(Song("Đếm ngày xa em", R.raw.dem_ngay_xa_em))
        arraySong.add(Song("Điều anh biết", R.raw.dieu_anh_biet))
        arraySong.add(Song("I don't believe you", R.raw.i_dont_believe_you))
        arraySong.add(Song("Mình là gì của nhau", R.raw.minh_la_gi_cua_nhau))
        arraySong.add(Song("Nếu em còn tồn tại", R.raw.neu_em_con_ton_tai))
        arraySong.add(Song("Như phút ban đầu", R.raw.nhu_phut_ban_dau))
        arraySong.add(Song("Phía sau một cô gái", R.raw.phia_sau_mot_co_gai))
        arraySong.add(Song("Sau tất cả", R.raw.sau_tat_ca))
        arraySong.add(Song("Say you do", R.raw.say_you_do))
        arraySong.add(Song("Yêu một người có lẽ", R.raw.yeu_mot_nguoi_co_le))
    }
}