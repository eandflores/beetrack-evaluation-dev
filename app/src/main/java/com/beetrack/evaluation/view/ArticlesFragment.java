package com.beetrack.evaluation.view;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beetrack.evaluation.R;
import com.beetrack.evaluation.interactor.ArticlesInteractor;
import com.beetrack.evaluation.model.Article;
import com.beetrack.evaluation.network.client.ArticlesClient;
import com.beetrack.evaluation.presenter.ArticlesPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 */
public class ArticlesFragment extends Fragment implements ArticlesPresenter.View, SearchView.OnQueryTextListener {

    @BindView(R.id.swiperefreshlayout) SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.progressbar) ProgressBar progressbar;
    @BindView(R.id.textview) TextView textview;

    private ArticlesPresenter articlesPresenter;

    private static final String ARG_IS_FAVORITE = "arg_is_favorite";
    private boolean isFavorite;
    private SearchView searchView;
    private List<Article> articles;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticlesFragment() {

    }

    public static ArticlesFragment newInstance(boolean isFavorite) {
        ArticlesFragment fragment = new ArticlesFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_FAVORITE, isFavorite);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null)
            isFavorite = getArguments().getBoolean(ARG_IS_FAVORITE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_articles, container,   false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        setupRecyclerView();

        articlesPresenter = new ArticlesPresenter(new ArticlesInteractor(new ArticlesClient()));
        articlesPresenter.setView(this);

        articlesPresenter.getArticles(isFavorite);
    }

    private void setupRecyclerView() {
        ArticlesAdapter adapter = new ArticlesAdapter(context());
        adapter.setItemClickListener((Article article) -> articlesPresenter.launchArticleDetail(article));
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);

        swiperefreshlayout.setOnRefreshListener(() -> articlesPresenter.getArticles(isFavorite));
    }

    private void setupSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint(getString(R.string.articles_search_hint));
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onDestroy() {
        articlesPresenter.terminate();
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_artists, menu);
        setupSearchView(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if(recyclerview.getAdapter() != null) {
            if(articles != null) {
                if(articles.size() > 0) {
                    final List<Article> filteredModelList = ((ArticlesAdapter) recyclerview.getAdapter()).filter(articles, query);
                    ((ArticlesAdapter) recyclerview.getAdapter()).animateTo(filteredModelList);
                    recyclerview.scrollToPosition(0);
                }
            }

            return true;
        } else
            return false;
    }

    @Override
    public void showLoading() {
        if(swiperefreshlayout.isRefreshing()) {
            progressbar.setVisibility(View.GONE);
            textview.setVisibility(View.VISIBLE);
            textview.setText(getString(R.string.articles_loading));
        } else {
            progressbar.setVisibility(View.VISIBLE);
            textview.setVisibility(View.GONE);
        }

        recyclerview.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        if(swiperefreshlayout.isRefreshing())
            swiperefreshlayout.setRefreshing(false);

        progressbar.setVisibility(View.GONE);
        recyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void showArticleNotFoundMessage() {
        if(swiperefreshlayout.isRefreshing())
            swiperefreshlayout.setRefreshing(false);

        progressbar.setVisibility(View.GONE);
        textview.setVisibility(View.VISIBLE);
        textview.setText(getString(R.string.articles_empty));
    }

    @Override
    public void showConnectionErrorMessage() {
        progressbar.setVisibility(View.GONE);
        textview.setVisibility(View.VISIBLE);
        textview.setText(getString(R.string.error_internet_connection));
    }

    @Override
    public void showServerError() {
        progressbar.setVisibility(View.GONE);
        textview.setVisibility(View.VISIBLE);
        textview.setText(getString(R.string.error_server_internal));
    }

    @Override
    public void renderArticles(List<Article> articles) {
        textview.setVisibility(View.GONE);
        searchView.setQuery("",true);

        this.articles           = articles;
        ArticlesAdapter adapter = (ArticlesAdapter) recyclerview.getAdapter();

        adapter.setArticles(new ArrayList<>(articles));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void launchArticleDetail(Article article) {
        //Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
        //intent.putExtra(ArticleDetailActivity.EXTRA_REPOSITORY, article);
        //startActivity(intent);
    }

    private void startActivityActionView() {
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/erikcaffrey/Android-Spotify-MVP")));
    }
}
