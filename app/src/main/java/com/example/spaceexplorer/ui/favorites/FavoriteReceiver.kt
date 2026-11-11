package com.example.spaceexplorer.ui.favorites

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.spaceexplorer.R
import com.example.spaceexplorer.domain.repository.FeedArticleDetailRepository
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavoriteReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: FeedArticleDetailRepository

    companion object {
        const val EXTRA_ARTICLE_ID = "EXTRA_ARTICLE_ID"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val ctx: Context = context ?: return
        val data: Intent = intent ?: return

        val articleId = data.getIntExtra(EXTRA_ARTICLE_ID, -1)
        if (articleId == -1) return

        val pendingResult = goAsync()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val nowFavorite = repository.toggleFavorite(articleId)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        ctx,
                        if (nowFavorite) R.string.favorites_added else R.string.favorites_removed,
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        ctx,
                        "Не удалось обновить избранное",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}