package com.zemoga.zemogatest.presenter.ui

import com.zemoga.zemogatest.data.model.CommentModel
import com.zemoga.zemogatest.data.model.PostDataModel
import com.zemoga.zemogatest.data.model.PostResponse

sealed class PostState{
    class ShowPostData(
        var response: List<PostDataModel>
    ) : PostState()

    class ShowCommentsData(
        var response: List<CommentModel>
    ) : PostState()

    class highlightIfFavorite(
        var isFavorite: Boolean): PostState()


}
