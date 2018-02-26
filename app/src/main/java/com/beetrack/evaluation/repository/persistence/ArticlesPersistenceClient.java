package com.beetrack.evaluation.repository.persistence;

import com.beetrack.evaluation.model.Article;
import com.beetrack.evaluation.model.Source;

import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by edgardo on 2/22/18.
 */

public class ArticlesPersistenceClient implements ArticlesPersistenceService {

    private Realm realm = Realm.getDefaultInstance();

    @Override
    public void saveArticles(List<Article> articles) {

        realm.executeTransaction(realm1 -> {
            for (Article article: articles ) {
                if(article.getTitle() != null) {
                    int id                  = article.getTitle().hashCode();
                    Article databaseArticle = getArticle(id);

                    if(databaseArticle == null) {
                        article.setId(id);
                        Article newArticle  = realm.createObject(Article.class, article.getId());

                        Source source = realm.createObject(Source.class);
                        source.setData(article.getSource());
                        newArticle.setSource(source);
                        newArticle.setData(article);
                    } else {
                        Source source = realm.createObject(Source.class);
                        source.setData(article.getSource());
                        databaseArticle.setSource(source);
                        databaseArticle.setData(article);
                        databaseArticle.setDeleted(false);
                    }

                }
            }
        });
    }

    private Article getArticle(int id) {
        Article article = realm.where(Article.class).equalTo("id", id).findFirst();
        return article;
    }

    @Override
    public List<Article> getArticles() {
        List<Article> articles = realm.where(Article.class).equalTo("isDeleted", false).findAllSorted("publishedAt");
        return articles;
    }

    @Override
    public void addArticleToFavorites(int id) {
        Article article = getArticle(id);
        realm.executeTransaction(realm1 -> {
            article.setFavorite(true);
        });
    }

    @Override
    public void deleteArticleFromFavorites(int id) {
        Article article = getArticle(id);
        realm.executeTransaction(realm1 -> {
            article.setFavorite(false);
        });
    }

    @Override
    public List<Article> getFavorites() {
        List<Article> articles = realm.where(Article.class).equalTo("isFavorite", true).findAll();
        return articles;
    }

    @Override
    public void deleteArticles() {
        RealmResults<Article> articles = realm.where(Article.class).findAll();

        for(Article article : articles) {
            realm.executeTransaction(realm1 -> {
                if(article.isFavorite())
                    article.setDeleted(true);
                else
                    article.deleteFromRealm();
            });
        }
    }


}

