package examples.imad.myblog;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArticleListAdapter articleListAdapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        recyclerView = (RecyclerView) findViewById(R.id.rvMain);

        articleListAdapter = new ArticleListAdapter();

        recyclerView.setAdapter(articleListAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Fetching Articles");

        progressDialog.show();

        ApiManager.getApiInterface().getArticles()
                .enqueue(new Callback<List<article>>() {
                    @Override
                    public void onResponse(Call<List<article>> call, Response<List<article>> response) {

                        progressDialog.dismiss();

                        if(response.isSuccessful()) {

                            articleListAdapter.setData(response.body());

                        } else {

                            Toast.makeText(ArticleListActivity.this, "Failed", Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<List<article>> call, Throwable t) {

                        progressDialog.dismiss();

                        Toast.makeText(ArticleListActivity.this, "Failed", Toast.LENGTH_LONG).show();

                    }
                });

    }

    public class ArticleListAdapter extends RecyclerView.Adapter<ArticleItemViewHolder> {

        /*String[] articleList = {
                "article1",
                "article2",
                "article3",
                "article4",
                "article5",

        }; */

        List<article> articleList = new ArrayList<>();

        @Override
        public ArticleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_article, parent, false);

            return new ArticleItemViewHolder(view);

        }

        @Override
        public void onBindViewHolder(ArticleItemViewHolder holder, final int position) {

            holder.articleName.setText(articleList.get(position).getHeading());
            holder.cvMain.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(ArticleListActivity.this, "Article clicked: " + articleList.get(position).getHeading(), Toast.LENGTH_SHORT)
                            .show();

                }

            });

        }

        @Override
        public int getItemCount() {

            return articleList.size();
        }

        public void setData(List<article> data) {

            this.articleList = data;

            notifyDataSetChanged();

        }

    }

}
