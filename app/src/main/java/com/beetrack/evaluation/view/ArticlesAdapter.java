package com.beetrack.evaluation.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beetrack.evaluation.R;
import com.beetrack.evaluation.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article} and makes a call to the
 * specified {@link ArticleClickListener}.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private Context context;
    private List<Article> articles;
    private ArticleClickListener itemClickListener;

    public ArticlesAdapter(Context context) {
        this.context    = context;
        articles        = Collections.emptyList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article item = articles.get(position);
        holder.bind(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageviewArticle) ImageView imageviewArticle;
        @BindView(R.id.imageviewStar) ImageView imageviewStar;
        @BindView(R.id.textviewSource) TextView textviewSource;
        @BindView(R.id.textviewTitle) TextView textviewTitle;
        @BindView(R.id.textviewDescription) TextView textviewDescription;
        @BindView(R.id.textviewAuthor) TextView textviewAuthor;
        @BindView(R.id.textviewPublished) TextView textviewPublished;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Article article) {

            Picasso.with(context)
                    .load(article.getUrlToImage())
                    //.placeholder(R.drawable.user_placeholder)
                    //.error(R.drawable.user_placeholder_error)
                    .into(imageviewArticle);

            if(article.getSource() != null)
                textviewSource.setText(article.getSource().getName());

            textviewTitle.setText(article.getTitle());
            textviewDescription.setText(article.getDescription());
            textviewAuthor.setText(article.getAuthor());

            if(article.getPublishedAt() != null) {
                DateFormat df = new android.text.format.DateFormat();
                textviewPublished.setText(df.format("yyyy-MM-dd HH:mm", article.getPublishedAt()));
            }

            if(article.isFavorite())
                imageviewStar.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_on));
            else
                imageviewStar.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_off));

            imageviewStar.setOnClickListener(v -> {
                if (null != itemClickListener)
                    itemClickListener.onStarClick(article.getTitle(), article.isFavorite());
            });

            itemView.setOnClickListener(v -> {
                if (null != itemClickListener)
                    itemClickListener.onItemClick(article.getUrl());
            });
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void setItemClickListener(ArticleClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ArticleClickListener {
        void onItemClick(String articleUrl);
        void onStarClick(String title, boolean isFavorite);
    }

    public void animateTo(List<Article> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<Article> newModels) {
        for (int i = articles.size() - 1; i >= 0; i--) {
            final Article model = articles.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Article> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Article model = newModels.get(i);
            if (!articles.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Article> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Article model = newModels.get(toPosition);
            final int fromPosition = articles.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Article removeItem(int position) {
        final Article model = articles.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Article model) {
        articles.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Article model = articles.remove(fromPosition);
        articles.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public List<Article> filter(List<Article> models, String query) {
        query = query.toLowerCase();

        final List<Article> filteredModelList = new ArrayList<>();
        for (Article article : models) {
            String text  = "";

            if(article.getTitle() != null)
                text    += article.getTitle().toLowerCase();

            if(article.getAuthor() != null)
                text    += article.getAuthor().toLowerCase();

            if(article.getSource() != null) {
                if (article.getSource().getName() != null)
                    text += article.getSource().getName();
            }

            if(article.getPublishedAt() != null) {
                DateFormat df = new android.text.format.DateFormat();
                text    += df.format("yyyy-MM-dd HH:mm", article.getPublishedAt());
            }

            if (text.contains(query))
                filteredModelList.add(article);

        }
        return filteredModelList;
    }
}
