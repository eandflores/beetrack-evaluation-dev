package com.beetrack.evaluation.view;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beetrack.evaluation.R;
import com.beetrack.evaluation.interactor.ArticlesInteractor;
import com.beetrack.evaluation.model.Article;
import com.beetrack.evaluation.network.client.ArticlesClient;
import com.beetrack.evaluation.presenter.ArticlesPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 */
public class ArticlesFragment extends Fragment implements ArticlesPresenter.View, SearchView.OnQueryTextListener {

    @BindView(R.id.recyclerview) RecyclerView recyclerview;
    @BindView(R.id.progressbar) ProgressBar progressbar;
    @BindView(R.id.textview) TextView textview;

    private ArticlesPresenter articlesPresenter;

    //private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticlesFragment() {
        setHasOptionsMenu(true);
    }

    public static ArticlesFragment newInstance() {
        return new ArticlesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        articlesPresenter.getArticles();
    }

    private void setupRecyclerView() {
        ArticlesAdapter adapter = new ArticlesAdapter(context());
        adapter.setItemClickListener((Article article) -> articlesPresenter.launchArticleDetail(article));
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);
    }

    private void setupSearchView(Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
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
    public Context context() {
        return getContext();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //articlesPresenter.getArticles(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void showLoading() {
        progressbar.setVisibility(View.VISIBLE);
        recyclerview.setVisibility(View.GONE);
        textview.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressbar.setVisibility(View.GONE);
        recyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void showArticleNotFoundMessage() {
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
        ArticlesAdapter adapter = (ArticlesAdapter) recyclerview.getAdapter();
        adapter.setArticles(articles);
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

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
    */
}
