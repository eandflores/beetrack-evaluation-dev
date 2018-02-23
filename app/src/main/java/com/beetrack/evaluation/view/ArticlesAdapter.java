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

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Article} and makes a call to the
 * specified {@link ItemClickListener}.
 */
public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private Context context;
    private List<Article> articles;
    private ItemClickListener itemClickListener;

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

            if(true)
                imageviewStar.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_on));
            else
                imageviewStar.setImageDrawable(context.getResources().getDrawable(android.R.drawable.btn_star_big_off));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != itemClickListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        itemClickListener.onItemClick(article);
                    }
                }
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

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(Article article);
    }
}
