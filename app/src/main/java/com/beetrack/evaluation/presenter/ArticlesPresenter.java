package com.beetrack.evaluation.presenter;

/**
 * Created by Edgardo Flores on 2/21/18.
 */

import com.beetrack.evaluation.interactor.ArticlesInteractor;
import com.beetrack.evaluation.model.Article;
import com.beetrack.evaluation.repository.network.Constants;
import com.beetrack.evaluation.utils.Utils;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class ArticlesPresenter extends Presenter<ArticlesPresenter.View> {

    private ArticlesInteractor interactor;

    public ArticlesPresenter(ArticlesInteractor interactor) {
        this.interactor = interactor;
    }

    public void getArticles(boolean isFavorite) {
        getView().showLoading();

        if(!isFavorite) {
            Utils utils = new Utils();
            if(utils.isOnline(this.getView().context())) {
                Disposable disposable = interactor.getArticles().subscribe(articles -> {
                    if(articles.getStatus().equals(Constants.SUCCESS_RESPONSE)) {
                        interactor.deleteArticles();
                        interactor.saveArticles(articles.getArticles());
                        renderArticles(interactor.getArticlesCache());
                    } else
                        getView().showArticleNotFoundMessage();

                }, Throwable::printStackTrace);

                addDisposableObserver(disposable);
            } else
                renderArticles(interactor.getArticlesCache());
        } else
            renderArticles(interactor.getFavorites());
    }

    private void renderArticles(List<Article> articles) {
        if (articles.size() > 0) {
            getView().hideLoading();
            getView().renderArticles(articles);
        } else
            getView().showArticleNotFoundMessage();
    }

    public void addArticleToFavorite(int articleId, boolean isFavorite) {
        getView().showLoading();

        interactor.addArticleToFavorites(articleId);

        if(!isFavorite)
            renderArticles(interactor.getArticlesCache());
        else
            renderArticles(interactor.getFavorites());
    }

    public void deleteArticleFromFavorite(int articleId, boolean isFavorite) {
        getView().showLoading();

        interactor.deleteArticleFromFavorites(articleId);

        if(!isFavorite)
            renderArticles(interactor.getArticlesCache());
        else
            renderArticles(interactor.getFavorites());
    }

    @Override
    public void terminate() {
        super.terminate();
        setView(null);
    }

    public interface View extends Presenter.View {

        void showLoading();

        void hideLoading();

        void showArticleNotFoundMessage();

        void showConnectionErrorMessage();

        void showServerError();

        void renderArticles(List<Article> articles);

    }
}
