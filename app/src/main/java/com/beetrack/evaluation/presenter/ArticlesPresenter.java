package com.beetrack.evaluation.presenter;

/**
 * Created by Edgardo Flores on 2/21/18.
 */

import com.beetrack.evaluation.interactor.ArticlesInteractor;
import com.beetrack.evaluation.model.Article;
import com.beetrack.evaluation.network.Constants;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class ArticlesPresenter extends Presenter<ArticlesPresenter.View> {

    private ArticlesInteractor interactor;

    public ArticlesPresenter(ArticlesInteractor interactor) {
        this.interactor = interactor;
    }

    public void getArticles() {
        getView().showLoading();
        Disposable disposable = interactor.getArticles().subscribe(articles -> {
            if(articles.getStatus().equals(Constants.SUCCESS_RESPONSE)) {
                if (articles.getTotalResults() > 0) {
                    getView().hideLoading();
                    getView().renderArticles(articles.getArticles());
                } else {
                    getView().showArticleNotFoundMessage();
                }
            } else
                getView().showArticleNotFoundMessage();

        }, Throwable::printStackTrace);

        addDisposableObserver(disposable);
    }

    public void launchArticleDetail(Article article) {
        getView().launchArticleDetail(article);
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

        void launchArticleDetail(Article article);

    }
}
