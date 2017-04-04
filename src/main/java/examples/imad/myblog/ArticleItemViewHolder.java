package examples.imad.myblog;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by AKASH on 03-04-2017.
 */

public class ArticleItemViewHolder extends RecyclerView.ViewHolder {

    TextView articleName;

    CardView cvMain;

    public ArticleItemViewHolder(View itemView) {
        super(itemView);

        articleName = (TextView) itemView.findViewById(R.id.tvArticleName);
        cvMain = (CardView) itemView.findViewById(R.id.cvMain);
    }
}
